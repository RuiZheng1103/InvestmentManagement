package com.winsigns.investment.inventoryService.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Relation(value = "fa-capital", collectionRelation = "fa-capitals")
public class FundAccountCapital extends AbstractEntity {

  /*
   * ��Ʒ�˻����
   */
  @Getter
  @Setter
  private Long fundAccountId;

  /*
   * �ⲿ�ʽ��˻�����
   */
  @Getter
  @Setter
  private String externalCapitalAccountType;

  /*
   * ����
   */
  @Getter
  @Setter
  private Currency currency;

  /*
   * Ͷ���޶�
   */
  @Getter
  @Setter
  private Double investmentLimit;

  @OneToMany(mappedBy = "fundAccountCapital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  @Getter
  @Setter
  private List<FundAccountCapitalDetail> fundAccountCapitalDetails =
      new ArrayList<FundAccountCapitalDetail>();

}
