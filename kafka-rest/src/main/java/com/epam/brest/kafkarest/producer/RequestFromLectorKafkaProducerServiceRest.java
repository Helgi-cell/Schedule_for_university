package com.epam.brest.kafkarest.producer;

import com.epam.brest.RequestFromLector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestFromLectorKafkaProducerServiceRest {

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @Autowired
    private KafkaTemplate <String, RequestFromLector> requestFromLectorKafkaTemplate;

    @Autowired
    private KafkaTemplate <String, List<RequestFromLector>> listRequestFromLectorKafkaTemplate;

    private String sendallrequestsfromlector = "sendallrequestsfromlector";

    private String updatedrequestfromlector = "updatedrequestfromlector";
    private String deletedrequestfromlector = "deletedrequestfromlector";
    private String sendrequestfromlectorbyid = "sendrequestfromlectorbyid";


    public void sendGiveAllRequestFromLector(List<RequestFromLector> requestsFromLector) {
        listRequestFromLectorKafkaTemplate.send(sendallrequestsfromlector, requestsFromLector);
    }

    public void sendRequestFromLectorById(RequestFromLector requestFromLector) {
        requestFromLectorKafkaTemplate.send(sendrequestfromlectorbyid, requestFromLector);
    }

    public void sendUpdatedRequestFromLector(RequestFromLector requestFromLector) {
        requestFromLectorKafkaTemplate.send(updatedrequestfromlector, requestFromLector);
    }

    public void sendDeleteRequestFromLector(String idRequestFromLector) {
        stringKafkaTemplate.send(deletedrequestfromlector, idRequestFromLector);
    }

}
