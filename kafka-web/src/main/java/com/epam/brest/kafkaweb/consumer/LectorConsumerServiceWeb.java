package com.epam.brest.kafkaweb.consumer;

import com.epam.brest.Group;
import com.epam.brest.Lector;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
@EnableJpaRepositories(basePackages = "com.epam.brest")
public class LectorConsumerServiceWeb {

    private static final Logger logger = LoggerFactory.getLogger(LectorConsumerServiceWeb.class);

    @Autowired
    private final ConsumerFactory<String, String> stringKafkaTemplate;
    @Autowired
    private final ConsumerFactory<String, Lector> lectorKafkaTemplate;
    @Autowired
    private final ConsumerFactory<String, List<Lector>> listLectorKafkaTemplate;


    public List<Lector> lectors = new ArrayList<>();

    public Lector lector = new Lector();

    public Boolean isListLectorChanged = false;
    public Boolean isLectorChanged = false;
    public Boolean isLectorCreated = false;
    public Boolean isLectorUpdated = false;
    public Boolean isLectorDeleted = false;

    public LectorConsumerServiceWeb(ConsumerFactory<String, String> stringKafkaTemplate
            , ConsumerFactory<String, Lector> lectorKafkaTemplate
            , ConsumerFactory<String, List<Lector>> listLectorKafkaTemplate) {

        this.stringKafkaTemplate = stringKafkaTemplate;
        this.lectorKafkaTemplate = lectorKafkaTemplate;
        this.listLectorKafkaTemplate = listLectorKafkaTemplate;
    }

    @KafkaListener(topics = "sendalllectors", groupId = "listlector")
    public void getAllLectors(String message) {
        logger.info("Received Message from senalllectors : " + message);
        try {
            ObjectMapper mapper = new ObjectMapper();
            lectors = mapper.readValue(message, new TypeReference<List<Lector>>() {
            });
            isListLectorChanged = true;
            logger.info("Parsed message from sendalllectors : " + lectors.toString());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "sendlectorbyid", groupId = "lector")
    public void getLectorById(String message) {
        logger.info("Received Message from sendlectorbyid : " + message);
        try {
            ObjectMapper mapper = new ObjectMapper();
            lector = mapper.readValue(message, new TypeReference <Lector>() {
            });
            isLectorChanged = true;
            logger.info("Parsed message from sendlectorbyid : " + lector.toString());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "newlectorcreated", groupId = "lector")
    public void newLector(String newLector) {
        logger.info("Received Message from newlectorcreated : " + lector);
        try {
            ObjectMapper mapper = new ObjectMapper();
            lector = mapper.readValue(newLector, Lector.class);
            logger.info("Parsed message from newlectorcreated : " + lector.toString());
            isLectorCreated = true;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "updatedlector", groupId = "group")
    public void updateLector(String updatedLector) {
        logger.info("Received Message from updatedgroup : " + updatedLector);
        try {
            ObjectMapper mapper = new ObjectMapper();
            lector = mapper.readValue(updatedLector, Lector.class);
            logger.info("Parsed message from updatedgroup : " + lector.toString());
            isLectorUpdated = true;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "deletedlector", groupId = "lector")
    public void deleteLector(String id) {
        logger.info("Received Message from deletedlector : " + id);
        try {
            logger.info("Parsed message from deletedlector : " + id);
            isLectorDeleted = true;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }
}
