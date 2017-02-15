package com.winsigns.investment.inventory.capital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable  
public class FundAccountCapitalDetailId implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "fund_account_id", length = 32)
	private String fundAccountId;
	
	@Column(name = "ext_capital_account_id", length = 32)
	private String extCapitalAccountId;
	
	@Column(name = "currency", length = 20)
	private String currency;
	
	public String getFundAccountId(){
		return this.fundAccountId;
	}
	
	public void setFundAccountId(String fundAccountId){
		this.fundAccountId = fundAccountId;
	}
	
	public String getExtCapitalAccountId(){
		return this.extCapitalAccountId;
	}
	
	public void setExtCapitalAccountId(String extCapitalAccountId){
		this.extCapitalAccountId = extCapitalAccountId;
	}
	
	public String getCurrency(){
		return this.currency;
	}
	
	public void setCurrency(String currency){
		this.currency = currency;
	}
}
