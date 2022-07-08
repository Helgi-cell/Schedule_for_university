package com.epam.brest.kafkaweb.config.consumer;

import com.epam.brest.Group;
import com.epam.brest.Lector;
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
public class LectorKafkaConsumerConfigWeb {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    private String lector = "lector";

    private String listlector = "listlector";

    @Bean
    public ConsumerFactory<String, Lector> lectorConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, lector);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Lector.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.epam.brest.*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Lector.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Lector> lectorKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Lector> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(lectorConsumerFactory());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, List<Lector>> listLectorConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, listlector);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, List.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.epam.brest.*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(List.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<Lector>> listLectorKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, List<Lector>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(listLectorConsumerFactory());
        return factory;
    }
}
