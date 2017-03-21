package com.winsigns.investment.framework.measure;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class MeasureValue {

  @JsonIgnore
  private MeasureHost measureHost;

  private Measure measure;

  private Double value;
}
