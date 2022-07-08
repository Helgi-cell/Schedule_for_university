package com.epam.brest.kafkaweb.producer;

import com.epam.brest.Lector;
import com.epam.brest.RequestFromLector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestFromLectorProducerServiceWeb {

    private static final Logger logger = LoggerFactory.getLogger(RequestFromLectorProducerServiceWeb.class);

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, RequestFromLector> requestFromLectorKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, List<RequestFromLector>> listRequestFromLectorKafkaTemplate;


    private String giveallrequestsfromlector = "giveallrequestsfromlector";
    private String giverequestfromlectorbyid = "giverequestfromlectorbyid";
    private String updaterequestfromlector = "updaterequestfromlector";
    private String deleterequestfromlector = "deleterequestfromlector";

    public void sendGiveAllRequestsFromLector(Integer idLector) {
        logger.info("RequestFromLectorProducerServiceWeb send giveallrequestsfromlector");
        stringKafkaTemplate.send(giveallrequestsfromlector, "" + idLector);
    }

    public void sendGiveRequestFromLectorByIdRequest(Integer idRequest) {
        logger.info("RequestFromLectorProducerServiceWeb send giverequestfromlectorbyid");
        stringKafkaTemplate.send(giverequestfromlectorbyid, "" + idRequest);
    }

    public void sendUpdateRequestFromLectorByIdRequest(RequestFromLector requestFromLector) {
        logger.info("RequestFromLectorProducerServiceWeb send updaterequestfromlectorbyid");
        requestFromLectorKafkaTemplate.send(updaterequestfromlector, requestFromLector);
    }

    public void sendDeleteRequestFromLector(RequestFromLector requestFromLector) {
        logger.info("RequestFromLectorProducerServiceWeb send deleterequestfromlector");
        requestFromLectorKafkaTemplate.send(deleterequestfromlector, requestFromLector);
    }
}
