package com.winsigns.investment.framework.measure;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winsigns.investment.framework.model.AbstractEntity;

/**
 * Created by colin on 2017/3/2.
 */
public abstract class MeasureHost extends AbstractEntity {

  public TradingMeasureValue getMeasureValue(Measure measure, boolean isFloat, String version) {
    return measure.getValue(this, isFloat, version);
  }

  public TradingMeasureValue getMeasureValue(String measureName, boolean isFloat, String version) {
    for (Measure measure : getMeasures()) {
      if (measure.getName().equals(measureName)) {
        return measure.getValue(this, isFloat, version);
      }
    }
    return null;
  }

  @JsonIgnore
  public List<Measure> getMeasures() {
    return MeasureRegistry.getInstance().getMeasures(this.getType());
  }

  public List<MeasureValue> getMeasureValues() {
    List<MeasureValue> measureValues = new ArrayList<MeasureValue>();

    List<Measure> measures = getMeasures();
    for (Measure measure : measures) {
      MeasureValue measureValue = measure.getValue(this, false);
      if (measureValue != null) {
        measureValues.add(measure.getValue(this, false));
      }
    }
    return measureValues;
  }

  public TradingMeasureValue calculate(Measure mesaure, boolean isFloat, String version) {
    TradingMeasureValue measureValue = mesaure.calculate(getId(), isFloat, version);
    return measureValue;
  }

  public abstract MeasureHostType getType();

  @JsonIgnore
  public String getName() {
    return this.getClass().getSimpleName();
  }
}
