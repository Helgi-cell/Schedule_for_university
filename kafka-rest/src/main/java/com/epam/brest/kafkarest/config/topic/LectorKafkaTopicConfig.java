package com.epam.brest.kafkarest.config.topic;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LectorKafkaTopicConfig {


    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdminLector() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic sendAllLectorsTopic() {
        return new NewTopic("sendalllectors", 1, (short) 1);
    }

    @Bean
    public NewTopic sendLectorByIdTopic() {
        return new NewTopic("sendlectorbyid", 1, (short) 1);
    }

    @Bean
    public NewTopic sendNewLectorCreated() {
        return new NewTopic("newlectorcreated", 1, (short) 1);
    }

    @Bean
    public NewTopic sendLectorUpdatedTopic() { return new NewTopic("updatedlector", 1, (short) 1);
    }
    @Bean
    public NewTopic sendDeletedLectorTopic() {
        return new NewTopic("deletedlector", 1, (short) 1);
    }

    @Bean
    public NewTopic giveAllLectorsTopic() {
        return new NewTopic("givealllectors", 1, (short) 1);
    }

    @Bean
    public NewTopic giveLectorByIdTopic() {
        return new NewTopic("givelectorbyid", 1, (short) 1);
    }

    @Bean
    public NewTopic giveCreateNewLectorTopic() {
        return new NewTopic("newlector", 1, (short) 1);
    }

    @Bean
    public NewTopic giveUpdateLectorTopic() {
        return new NewTopic("updatelector", 1, (short) 1);
    }

    @Bean
    public NewTopic giveDeleteLectorTopic() { return new NewTopic("deletelector", 1, (short) 1);
    }


}
