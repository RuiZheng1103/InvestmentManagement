package com.winsigns.investment.fundService.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.frame.model.AbstractEntity;
import com.winsigns.investment.fundService.constant.ExternalTradeAccountType;

@Entity
public class ExternalTradeAccount extends AbstractEntity {

	@Enumerated(EnumType.STRING)
	private ExternalTradeAccountType externalTradeAccountType;

	private String externalTradeAccount;

	@ManyToOne
	@JsonIgnore
	private ExternalCapitalAccount externalCapitalAccount;

	public ExternalTradeAccountType getExternalTradeAccountType() {
		return externalTradeAccountType;
	}

	public void setExternalTradeAccountType(ExternalTradeAccountType externalTradeAccountType) {
		this.externalTradeAccountType = externalTradeAccountType;
	}

	public String getExternalTradeAccount() {
		return externalTradeAccount;
	}

	public void setExternalTradeAccount(String externalTradeAccount) {
		this.externalTradeAccount = externalTradeAccount;
	}

	public ExternalCapitalAccount getExternalCapitalAccount() {
		return externalCapitalAccount;
	}

	public void setExternalCapitalAccount(ExternalCapitalAccount externalCapitalAccount) {
		this.externalCapitalAccount = externalCapitalAccount;
	}

}
