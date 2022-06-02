package com.epam.brest.kafkarest.consumer;


import com.epam.brest.*;
import com.epam.brest.kafkarest.producer.LectorKafkaProducerServiceRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectorKafkaConsumerServiceRest {

    private final Logger logger =  LogManager.getLogger(LectorKafkaConsumerServiceRest.class);
    @Autowired
    private final ConsumerFactory<String, String> stringLectorKafkaTemplate;
    @Autowired
    private final ConsumerFactory<String, Lector> lectorKafkaTemplate;
    @Autowired
    private final ConsumerFactory<String, List<Lector>> listLectorKafkaTemplate;
    @Autowired
    private final LectorServiceApi lectorService;

    @Autowired
    private final RequestFromLectorServiceApi requestFromLectorService;

    @Autowired
    private final GroupServiceApi groupService;
    @Autowired
    private final LectorKafkaProducerServiceRest lectorProducerService;


    public LectorKafkaConsumerServiceRest(ConsumerFactory<String, String> stringLectorKafkaTemplate
            , ConsumerFactory<String, Lector> lectorKafkaTemplate
            , ConsumerFactory<String, List<Lector>> listLectorKafkaTemplate
            , LectorServiceApi lectorService
            , RequestFromLectorServiceApi requestFromLectorService, GroupServiceApi groupService, LectorKafkaProducerServiceRest lectorProducerService) {
        this.stringLectorKafkaTemplate = stringLectorKafkaTemplate;
        this.lectorKafkaTemplate = lectorKafkaTemplate;
        this.listLectorKafkaTemplate = listLectorKafkaTemplate;
        this.lectorService = lectorService;
        this.requestFromLectorService = requestFromLectorService;
        this.groupService = groupService;
        this.lectorProducerService = lectorProducerService;
    }

    @KafkaListener(topics = "givealllectors", groupId = "string")
    public void listenLectorFoo(String message) {
        logger.info("Received Message in givealllectors : " + message);
        List<Lector> lectors = lectorService.getAllLectorsService();
        if (lectors == null) {
            lectors = new ArrayList<>();
        }
        logger.info("Lectors in consumer" + lectors.toString());
        lectorProducerService.sendGiveAllLectors(lectors);
    }

    @KafkaListener(topics = "newlector", groupId = "string")
    public void newLectorListener(String newLector) throws Exception {
        try {
            logger.info("Received message in string newGroup: " + newLector);
            ObjectMapper mapper = new ObjectMapper();
            Lector lector = mapper.readValue(newLector, Lector.class);
            System.out.println("Received Message in newLector : " + newLector);
            lector = lectorService.createNewLectorService(lector);
            logger.info("Created new Lector " + lector.toString());
            lectorProducerService.sendCreateNewLector(lector);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "updatelector", groupId = "string")
    public void lectorUpdateListener(String lectorToUpdate) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Lector lector = mapper.readValue(lectorToUpdate, Lector.class);
            System.out.println("Received Message in updatelector : " + lector.toString());
            lector = lectorService.updateLectorService(lector);
            lectorProducerService.sendUpdateLector(lector);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "deletelector", groupId = "string")
    public void deleteLectorListener(String idLector) throws Exception {
        try {
            logger.info("Received message for delete Lector id = : " + idLector);
            Integer id = Integer.parseInt(idLector);
            id = lectorService.deleteLectorByIdLectorService(id);
            logger.info("Deleted Lector where id =  " + id);
            lectorProducerService.sendDeleteLector("" + id);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }



    @KafkaListener(topics = "givelectorbyid", groupId = "string")
    public void getLectorByIdListener(String idLector) throws Exception {
        try {
            logger.info("Received message for give Lector id = : " + idLector);
            Integer id = Integer.parseInt(idLector);
            Lector lector = lectorService.getLectorByIdLectorService(id);
            logger.info("Get Lector where id =  " + id);
            lectorProducerService.sendGiveLectorById(lector);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }
}
