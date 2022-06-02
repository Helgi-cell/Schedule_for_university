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
public class RequestFromLectorTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdminRequestFromLector() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }


    @Bean
    public NewTopic sendAllRequestsFromLectorTopic() {
        return new NewTopic("sendallrequestsfromlector", 1, (short) 1);
    }

    @Bean
    public NewTopic giveAllRequestsFromLectorTopic() {
        return new NewTopic("giveallrequestsfromlector", 1, (short) 1);
    }

    @Bean
    public NewTopic giveRequestFromLectorByIdTopic() {
        return new NewTopic("giverequestfromlectorbyid", 1, (short) 1);
    }

    @Bean
    public NewTopic sendRequestFromLectorByIdTopic() {
        return new NewTopic("sendrequestfromlectorbyid", 1, (short) 1);
    }

    @Bean
    public NewTopic updateRequestFromLectorTopic() {
        return new NewTopic("updaterequestfromlector", 1, (short) 1);
    }

    @Bean
    public NewTopic updatedRequestFromLectorTopic() {
        return new NewTopic("updatedrequestfromlector", 1, (short) 1);
    }

    @Bean
    public NewTopic deleteRequestFromLectorTopic() {
        return new NewTopic("deleterequestfromlector", 1, (short) 1);
    }

    @Bean
    public NewTopic deletedRequestFromLectorTopic() {
        return new NewTopic("deletedrequestfromlector", 1, (short) 1);
    }
}
