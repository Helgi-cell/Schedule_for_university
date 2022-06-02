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
public class GroupKafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdminGroup() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("sendallgroups", 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("newgroupcreated", 1, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic("updatedgroup", 1, (short) 1);
    }
    @Bean
    public NewTopic topic4() {
        return new NewTopic("deletedgroup", 1, (short) 1);
    }

    @Bean
    public NewTopic topic11() {
        return new NewTopic("giveallgroups", 1, (short) 1);
    }

    @Bean
    public NewTopic topic22() {
        return new NewTopic("newgroup", 1, (short) 1);
    }

    @Bean
    public NewTopic topic33() {
        return new NewTopic("updategroup", 1, (short) 1);
    }
    @Bean
    public NewTopic topic44() {
        return new NewTopic("deletegroup", 1, (short) 1);
    }


}

