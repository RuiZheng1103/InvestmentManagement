package com.winsigns.investment.inventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.winsigns.investment.inventoryService.model.Currency;

@Transactional
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
