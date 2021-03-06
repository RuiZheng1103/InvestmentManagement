package com.winsigns.investment.tradeService.command;

import com.winsigns.investment.framework.constant.CurrencyCode;
import com.winsigns.investment.tradeService.constant.InstructionVolumeType;

import lombok.Data;

@Data
public class CommitInstructionCommand {

  // 指令序号
  Long instructionId;

  // 投资组合
  Long portfolioId;

  // 投资标的
  Long securityId;

  // 投资服务
  String investService;

  // 投资类型
  String investType;

  // 币种
  CurrencyCode currency;

  // 成本价
  Double costPrice;

  // 数量类型
  InstructionVolumeType volumeType;

  // 指令数量
  Long quantity;

  // 指令金额
  Double amount;
}
