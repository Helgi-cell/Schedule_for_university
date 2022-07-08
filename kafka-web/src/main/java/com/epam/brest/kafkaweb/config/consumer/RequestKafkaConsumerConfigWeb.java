package com.epam.brest.kafkaweb.config.consumer;

import com.epam.brest.Lector;
import com.epam.brest.RequestFromLector;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableKafka
public class RequestKafkaConsumerConfigWeb {


    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    private String request = "request";

    private String listrequest = "listrequest";

    @Bean
    public ConsumerFactory<String, RequestFromLector> requestFromLectorConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, request);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, RequestFromLector.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.epam.brest.*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer()
                                                , new JsonDeserializer<>(RequestFromLector.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RequestFromLector> requestFrpmLectorKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RequestFromLector> factory =
                                                            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(requestFromLectorConsumerFactory());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, List<RequestFromLector>> listRequestFromLectorConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, listrequest);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, List.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.epam.brest.*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(List.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<RequestFromLector>> listRequestFromLectorKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, List<RequestFromLector>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(listRequestFromLectorConsumerFactory());
        return factory;
    }
}
