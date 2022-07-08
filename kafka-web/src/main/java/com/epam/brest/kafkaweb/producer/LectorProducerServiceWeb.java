package com.epam.brest.kafkaweb.producer;

import com.epam.brest.Group;
import com.epam.brest.Lector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectorProducerServiceWeb {

    private static final Logger logger = LoggerFactory.getLogger(LectorProducerServiceWeb.class);
    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, Lector> lectorKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, List<Lector>> listLectorKafkaTemplate;


    private String givalllectors = "givealllectors";
    private String givelectorbyid = "givelectorbyid";
    private String newlector = "newlector";
    private String updatelector = "updatelector";

    private String deletelector = "deletelector";


    public void sendGiveAllLectors(String message) {
        logger.info("LectorProducerServiceWeb send givealllectors");
        stringKafkaTemplate.send(givalllectors, message);
    }

    public void sendGiveLectorById(Integer id) {
        logger.info("LectorProducerServiceWeb send giveLectorById" + id);
        stringKafkaTemplate.send(givelectorbyid, ("" + id));
    }

    public void sendNewLector(Lector newLector) {
        logger.info("LectorProducerServiceWeb send NewLector" + newLector.toString());
        lectorKafkaTemplate.send(newlector, newLector);
    }

    public void sendUpdateLector(Lector updatedLector) {
        logger.info("LectorProducerServiceWeb send UpdateLector" + updatedLector.toString());
        lectorKafkaTemplate.send(updatelector, updatedLector);
    }

    public void sendDeleteLectorById(Integer id) {
        logger.info("LectorProducerServiceWeb send deletelector " + id);
        stringKafkaTemplate.send(deletelector, ("" + id));
    }

}
