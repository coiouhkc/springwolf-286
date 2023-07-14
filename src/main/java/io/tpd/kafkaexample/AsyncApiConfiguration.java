package io.tpd.kafkaexample;

import com.asyncapi.v2.binding.kafka.KafkaOperationBinding;
import com.asyncapi.v2.model.info.Info;
import com.asyncapi.v2.model.server.Server;
import io.github.stavshamir.springwolf.asyncapi.types.ProducerData;
import io.github.stavshamir.springwolf.configuration.AsyncApiDocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AsyncApiConfiguration {

  private final static String BOOTSTRAP_SERVERS = "localhost:9092"; // Change to your actual bootstrap server

  @Bean
  public AsyncApiDocket asyncApiDocket() {
    Info info = Info.builder()
        .version("1.0.0")
        .title("Springwolf example project")
        .build();

    // Producers are not picked up automatically - if you want them to be included in the asyncapi doc and the UI,
    // you will need to build a ProducerData and register it in the docket (line 65)
    ProducerData exampleProducerData = ProducerData.builder()
        .channelName("example-producer-topic")
        .operationBinding(Map.of("kafka", new KafkaOperationBinding()))
        .payloadType(String.class)
        .build();

    return AsyncApiDocket.builder()
        .basePackage("io.tpd.kafkaexample") // Change to your actual base package of listeners
        .info(info)
        .server("kafka", Server.builder().protocol("kafka").url(BOOTSTRAP_SERVERS).build())
        .producer(exampleProducerData)
        .build();
  }

}