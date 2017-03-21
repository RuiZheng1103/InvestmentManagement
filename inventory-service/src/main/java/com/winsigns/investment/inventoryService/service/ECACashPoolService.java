package com.winsigns.investment.inventoryService.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.command.CreateCashPoolCommand;
import com.winsigns.investment.inventoryService.command.TransferAccountCommand;
import com.winsigns.investment.inventoryService.model.ECACashPool;
import com.winsigns.investment.inventoryService.model.ECACashSerial;
import com.winsigns.investment.inventoryService.repository.ECACashPoolRepository;
import com.winsigns.investment.inventoryService.repository.ECACashSerialRepository;

@Service
public class ECACashPoolService {

  @Autowired
  ECACashPoolRepository ecaCashPoolRepository;

  @Autowired
  ECACashSerialRepository ecaCashSerialRepository;

  /*
   * �����ض��ⲿ�ʽ��˻��µ������ʽ��
   */
  public Collection<ECACashPool> findByExternalCapitalAccountId(Long externalCapitalAccountId) {

    return ecaCashPoolRepository.findByExternalCapitalAccountId(externalCapitalAccountId);
  }

  /*
   * �������е��ʽ��
   */
  public Collection<ECACashPool> findAll() {
    return ecaCashPoolRepository.findAll();
  }

  /*
   * �����ض����ʽ��
   */
  public ECACashPool findOne(Long eCACashPoolId) {

    return ecaCashPoolRepository.findOne(eCACashPoolId);
  }

  /*
   * ���һ���ʽ��
   */
  public ECACashPool addECACashPool(CreateCashPoolCommand createCashPoolCommand) {
    ECACashPool ecaCashPool = ecaCashPoolRepository.findByExternalCapitalAccountIdAndCurrency(
        createCashPoolCommand.getExternalCapitalAccountId(), createCashPoolCommand.getCurrency());

    if (ecaCashPool == null) {
      ecaCashPool = new ECACashPool();

      ecaCashPool.setExternalCapitalAccountId(createCashPoolCommand.getExternalCapitalAccountId());
      ecaCashPool.setCurrency(createCashPoolCommand.getCurrency());
      ecaCashPool.setUnassignedCapital(0.0);
      ecaCashPool = ecaCashPoolRepository.save(ecaCashPool);
    }
    return ecaCashPool;
  }

  /*
   * ���ʽ���Ȧ�������ʽ�
   */
  @Transactional
  public ECACashPool transferTo(Long ecaCashPoolId, TransferAccountCommand transferAccountCommand) {

    ECACashPool ecaCashPool = ecaCashPoolRepository.findOne(ecaCashPoolId);

    if (ecaCashPool == null)
      return null;

    ecaCashPool.setUnassignedCapital(
        ecaCashPool.getUnassignedCapital() + transferAccountCommand.getChangedCapital());

    ECACashSerial ecaCashSerial = new ECACashSerial();
    ecaCashSerial.setEcaCashPool(ecaCashPool);
    ecaCashSerial.setAssignedDate(new Date());
    ecaCashSerial.setAssignedCash(Math.abs(transferAccountCommand.getChangedCapital()));
    ecaCashSerial.operator(ecaCashPool, false);
    ecaCashSerial = ecaCashSerialRepository.save(ecaCashSerial);

    return ecaCashPoolRepository.save(ecaCashPool);
  }

  /*
   * ���ʽ���Ȧ��ת���ʽ�
   */
  @Transactional
  public ECACashPool transferFrom(Long ecaCashPoolId,
      TransferAccountCommand transferAccountCommand) {

    ECACashPool ecaCashPool = ecaCashPoolRepository.findOne(ecaCashPoolId);

    if (ecaCashPool == null)
      return null;

    ecaCashPool.setUnassignedCapital(
        ecaCashPool.getUnassignedCapital() - transferAccountCommand.getChangedCapital());

    ECACashSerial ecaCashSerial = new ECACashSerial();
    ecaCashSerial.setEcaCashPool(ecaCashPool);
    ecaCashSerial.setAssignedDate(new Date());
    ecaCashSerial.setAssignedCash(-Math.abs(transferAccountCommand.getChangedCapital()));
    ecaCashSerial.operator(ecaCashPool, false);
    ecaCashSerial = ecaCashSerialRepository.save(ecaCashSerial);

    return ecaCashPoolRepository.save(ecaCashPool);

  }

  /*
   * �����ʽ��֮��Ļ�ת
   */
  @Transactional
  public Collection<ECACashPool> allot(Long dstEcaCashPoolId, Long srcEcaCashPoolId,
      Double changedCapital) {

    List<ECACashPool> result = new ArrayList<ECACashPool>();

    ECACashPool dstEcaCashPool = ecaCashPoolRepository.findOne(dstEcaCashPoolId);

    if (dstEcaCashPool == null)
      return null;

    dstEcaCashPool
        .setUnassignedCapital(dstEcaCashPool.getUnassignedCapital() + Math.abs(changedCapital));
    result.add(ecaCashPoolRepository.save(dstEcaCashPool));

    ECACashSerial dstECACashSerial = new ECACashSerial();
    dstECACashSerial.setEcaCashPool(dstEcaCashPool);
    dstECACashSerial.setAssignedDate(new Date());
    dstECACashSerial.setAssignedCash(Math.abs(changedCapital));
    dstECACashSerial.operator(dstEcaCashPool, false);
    dstECACashSerial = ecaCashSerialRepository.save(dstECACashSerial);

    ECACashPool srcEcaCashPool = ecaCashPoolRepository.findOne(srcEcaCashPoolId);
    if (srcEcaCashPool == null)
      return null;
    srcEcaCashPool
        .setUnassignedCapital(srcEcaCashPool.getUnassignedCapital() - Math.abs(changedCapital));

    result.add(ecaCashPoolRepository.save(srcEcaCashPool));

    ECACashSerial srcECACashSerial = new ECACashSerial();
    srcECACashSerial.setEcaCashPool(srcEcaCashPool);
    srcECACashSerial.setAssignedDate(new Date());
    srcECACashSerial.setAssignedCash(-Math.abs(changedCapital));
    srcECACashSerial.operator(srcEcaCashPool, false);
    srcECACashSerial = ecaCashSerialRepository.save(srcECACashSerial);

    return result;
  }

}
