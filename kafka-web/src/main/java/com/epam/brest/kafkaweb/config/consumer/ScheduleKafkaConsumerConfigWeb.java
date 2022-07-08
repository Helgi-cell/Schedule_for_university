package com.epam.brest.kafkaweb.config.consumer;

import com.epam.brest.Group;
import com.epam.brest.LectorsSchedule;
import com.epam.brest.StudentsSchedule;
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
public class ScheduleKafkaConsumerConfigWeb {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;


    private String students = "students";

    private String lectors = "lectors";
    private String allstudents = "allstudents";

    private String alllectors = "alllectors";

    @Bean
    public ConsumerFactory<String, List<StudentsSchedule>> listStudentsScheduleConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, students);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, List.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.epam.brest.*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(List.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<StudentsSchedule>>
    listStudentsScheduleKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, List<StudentsSchedule>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(listStudentsScheduleConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, List<List<StudentsSchedule>>> listAllStudentsScheduleConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, allstudents);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, List.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.epam.brest.*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(List.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<List<StudentsSchedule>>>
    listAllStudentsScheduleKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, List<List<StudentsSchedule>>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(listAllStudentsScheduleConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, List<LectorsSchedule>> listLectorsScheduleConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, lectors);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, List.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.epam.brest.*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(List.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<LectorsSchedule>>
    listLectorsScheduleKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, List<LectorsSchedule>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(listLectorsScheduleConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, List<List<LectorsSchedule>>> listAllLectorsScheduleConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, alllectors);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, List.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.epam.brest.*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(List.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<List<LectorsSchedule>>>
    listAllLectorsScheduleKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, List<List<LectorsSchedule>>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(listAllLectorsScheduleConsumerFactory());
        return factory;
    }

}
