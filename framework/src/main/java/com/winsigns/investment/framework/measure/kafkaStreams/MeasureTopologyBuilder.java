package com.winsigns.investment.framework.measure.kafkaStreams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.winsigns.investment.framework.kafka.KafkaConfiguration;
import com.winsigns.investment.framework.measure.ICalculateFactor;
import com.winsigns.investment.framework.measure.Measure;
import com.winsigns.investment.framework.measure.MeasureRegistry;

public class MeasureTopologyBuilder {

  private static final String SOURCE_SUFFIX = ".source";
  private static final String PROCESS_SUFFIX = ".processor";
  private static final String SINK_SUFFIX = ".sink";

  private Logger log = LoggerFactory.getLogger(MeasureTopologyBuilder.class);

  @Autowired
  KafkaConfiguration kafkaConfiguration;

  private KafkaStreams streams;

  private Serde<ProcessorKey> keySerde;

  private Serde<ProcessorValue> valueSerde;

  public void start() {
    final JsonSerializer<ProcessorKey> keyDeserializer =
        JsonSerializer.defaultConfig(ProcessorKey.class);
    final JsonSerializer<ProcessorKey> keySerializer =
        JsonSerializer.defaultConfig(ProcessorKey.class);
    final JsonSerializer<ProcessorValue> valueDeserializer =
        JsonSerializer.defaultConfig(ProcessorValue.class);
    final JsonSerializer<ProcessorValue> valueSerializer =
        JsonSerializer.defaultConfig(ProcessorValue.class);

    keySerde = new ProcessorKeyJsonSerde();
    valueSerde = new ProcessorValueJsonSerde();

    Properties config = new Properties();
    config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfiguration.getAppId());
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBrokerHost());
    config.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, kafkaConfiguration.getZookeeperHost());
    config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, keySerde.getClass().getName());
    config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, valueSerde.getClass().getName());

    TopologyBuilder builder = new TopologyBuilder();

    // �Ƚ���������
    MeasureRegistry.getInstance().rehash();

    HashSet<String> sourceNames = new HashSet<String>();
    for (Measure measure : MeasureRegistry.getInstance().getMeasures()) {
      // ����ָ������м������ӣ�������ڱ���ָ��⣬��Ϊsource
      List<ICalculateFactor> factors = measure.getCalculateFactors();
      if (factors != null) {
        for (ICalculateFactor facotr : factors) {
          if (!MeasureRegistry.getInstance().contains(facotr.getName())) {
            // ���û�д�������source���򴴽�
            if (!sourceNames.contains(facotr.getName())) {
              log.info(String.format("addSource: name:%s, source:%s",
                  facotr.getName() + SOURCE_SUFFIX, facotr.getName()));
              builder.addSource(facotr.getName() + SOURCE_SUFFIX, keyDeserializer,
                  valueDeserializer, facotr.getName());
              sourceNames.add(facotr.getName());
            }
          }
        }
      }

      // builder.addSource(measure.getName() + SOURCE_SUFFIX, keyDeserializer, valueDeserializer,
      // getSourcesTopic(measure));

      // Ϊ���ص�ָ�꽨��process
      String processorName = measure.getName() + PROCESS_SUFFIX;
      String sinkName = measure.getName() + SINK_SUFFIX;
      log.info(String.format("addProcessor: name:%s, deps:%s", processorName,
          String.join(" / ", getRealDependencies(measure))));
      builder.addProcessor(processorName, () -> new MeasureProcessor(measure),
          getRealDependencies(measure));
      log.info(String.format("addSink: name:%s, deps:%s", sinkName, processorName));
      builder.addSink(sinkName, measure.getName(), keySerializer, valueSerializer, processorName);
    }

    streams = new KafkaStreams(builder, config);
    streams.start();

  }

  public void close() {
    streams.close();
  }

  private String[] getRealDependencies(Measure measure) {
    ArrayList<String> calculateFactors = new ArrayList<String>();

    List<ICalculateFactor> factors = measure.getCalculateFactors();
    if (factors != null) {
      factors.forEach(factor -> {
        if (!MeasureRegistry.getInstance().contains(factor.getName())) {
          calculateFactors.add(factor.getName() + SOURCE_SUFFIX);
        } else {
          calculateFactors.add(factor.getName() + PROCESS_SUFFIX);
        }
      });
    }

    return calculateFactors.toArray(new String[calculateFactors.size()]);
  }
}
