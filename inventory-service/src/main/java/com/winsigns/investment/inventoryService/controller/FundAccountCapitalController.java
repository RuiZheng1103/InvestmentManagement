package com.winsigns.investment.inventoryService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.winsigns.investment.inventoryService.model.FundAccountCapital;
import com.winsigns.investment.inventoryService.service.FundAccountCapitalService;

@RestController
@ExposesResourceFor(FundAccountCapital.class)
@RequestMapping("/fundAccountCapitals")
public class FundAccountCapitalController {

    @Autowired
    FundAccountCapitalService fundAccountCapitalService;

    // // 从资金账户分配资金到产品账户
    // @RequestMapping(value =
    // "/{fundAccountId}/assignFrom/{externalCapitalAccountId}", method =
    // RequestMethod.POST)
    // public FundAccountCapitalResource
    // assignToFundAccountFromCapitalAccount(@PathVariable Long fundAccountId,
    // @PathVariable Long externalCapitalAccountId, @RequestBody
    // TransferAccountCommand capitalChangeCommand) {
    // return new
    // FundAccountCapitalResourceAssembler().toResource(fundAccountCapitalService
    // .assignToFundAccountFromCapitalAccount(fundAccountId,
    // externalCapitalAccountId, capitalChangeCommand));
    // }
    //
    // // 从产品账户归还资金到资金账户
    // @RequestMapping(value =
    // "/{fundAccountId}/assignTo/{externalCapitalAccountId}", method =
    // RequestMethod.POST)
    // public FundAccountCapitalResource
    // assignToCapitalAccountFromFundAccount(@PathVariable Long fundAccountId,
    // @PathVariable Long externalCapitalAccountId, @RequestBody
    // TransferAccountCommand capitalChangeCommand) {
    // return new
    // FundAccountCapitalResourceAssembler().toResource(fundAccountCapitalService
    // .assignToCapitalAccountFromFundAccount(externalCapitalAccountId,
    // fundAccountId, capitalChangeCommand));
    // }
    //
    // // 从产品账户让渡资金到产品账户
    // @RequestMapping(value = "/{fundAccountId}/transfer/{matchfundAccountId}",
    // method = RequestMethod.POST)
    // public FundAccountCapitalResource transfer(@PathVariable Long
    // fundAccountId, @PathVariable Long matchfundAccountId,
    // @RequestBody TransferAccountCommand capitalChangeCommand) {
    // return new FundAccountCapitalResourceAssembler().toResource(
    // fundAccountCapitalService.transfer(fundAccountId, matchfundAccountId,
    // capitalChangeCommand));
    // }
}
