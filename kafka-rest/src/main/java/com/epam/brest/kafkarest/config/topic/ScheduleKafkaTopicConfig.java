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
public class ScheduleKafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdminSchedule() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicschedule1() {
        return new NewTopic("givescheduleforallstudents", 1, (short) 1);
    }

    @Bean
    public NewTopic topicschedule11() {
        return new NewTopic("givescheduleforalllectors", 1, (short) 1);
    }

    @Bean
    public NewTopic topicschedule2() {
        return new NewTopic("sendscheduleforallstudents", 1, (short) 1);
    }

    @Bean
    public NewTopic topicschedule22() {
        return new NewTopic("sendscheduleforalllectors", 1, (short) 1);
    }

    @Bean
    public NewTopic topicschedule3() {
        return new NewTopic("createschedule", 1, (short) 1);
    }

    @Bean
    public NewTopic topicschedule4() {
        return new NewTopic("givescheduleforlector", 1, (short) 1);
    }

    @Bean
    public NewTopic topicschedule5() {
        return new NewTopic("sendscheduleforlector", 1, (short) 1);
    }

    @Bean
    public NewTopic topicschedule6() {
        return new NewTopic("givescheduleforgroup", 1, (short) 1);
    }

    @Bean
    public NewTopic topicschedule7() {
        return new NewTopic("sendscheduleforgroup", 1, (short) 1);
    }

}
