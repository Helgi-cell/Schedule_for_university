package com.epam.brest.kafkarest.producer;

import com.epam.brest.Lector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectorKafkaProducerServiceRest {

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;
    @Autowired
    private KafkaTemplate <String, Lector> lectorKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, List<Lector>> listLectorKafkaTemplate;


    private String sendalllectors = "sendalllectors";
    private String newlectorcreated = "newlectorcreated";
    private String updatedlector = "updatedlector";
    private String deletedlector = "deletedlector";

    private String givelectorbyid = "givelectorbyid";

    public void sendGiveAllLectors(List<Lector> lectors) { listLectorKafkaTemplate.send(sendalllectors, lectors); }
    public void sendCreateNewLector(Lector lector) {
        lectorKafkaTemplate.send(newlectorcreated, lector);
    }

    public void sendUpdateLector(Lector lector) {
        lectorKafkaTemplate.send(updatedlector, lector);
    }

    public void sendDeleteLector(String id) {
        stringKafkaTemplate.send(deletedlector, id);
    }

    public void sendGiveLectorById(Lector lector) {
        lectorKafkaTemplate.send(givelectorbyid, lector);
    }
}
