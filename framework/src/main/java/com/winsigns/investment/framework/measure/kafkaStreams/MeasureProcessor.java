package com.winsigns.investment.framework.measure.kafkaStreams;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.ProcessorContext;

import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.TradingMeasureValue;

public class MeasureProcessor extends AbstractProcessor<ProcessorKey, ProcessorValue> {

  private Measure measure;

  public MeasureProcessor(Measure measure) {
    this.measure = measure;
  }

  @Override
  public void init(ProcessorContext context) {
    super.init(context);
  }

  @Override
  public void process(ProcessorKey key, ProcessorValue value) {

    /*
     * �����ָ����ĸò���
     */
    if ((key.getOperatorName() != null && measure.isConcerned(key.getOperatorName()))
        || key.getOperatorName() == null) {

      TradingMeasureValue tradingMeasureValue =
          measure.calculate(key.getMeasureHostId(), key.isFloat(), key.getVersion());

      if (tradingMeasureValue != null) {
        key.setMeasureHostId(tradingMeasureValue.getMeasureHost().getId());
        key.setOperatorName(null); // һ�������һ��֮�󣬾Ͳ��ٹ�����
        value.setName(tradingMeasureValue.getMeasure().getName());
        context().forward(key, value);
      }
    }
  }
}
