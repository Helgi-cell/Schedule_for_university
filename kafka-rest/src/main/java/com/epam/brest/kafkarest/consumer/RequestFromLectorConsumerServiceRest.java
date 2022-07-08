package com.epam.brest.kafkarest.consumer;

import com.epam.brest.Lector;
import com.epam.brest.RequestFromLector;
import com.epam.brest.RequestFromLectorServiceApi;
import com.epam.brest.kafkarest.producer.RequestFromLectorKafkaProducerServiceRest;
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
public class RequestFromLectorConsumerServiceRest {

    private final Logger logger =  LogManager.getLogger(RequestFromLectorConsumerServiceRest.class);

    @Autowired
    private final ConsumerFactory<String, String> stringLectorKafkaTemplate;

    @Autowired
    private final ConsumerFactory<String, RequestFromLector> requestFromLectorKafkaTemplate;
    @Autowired
    private final ConsumerFactory<String, List<RequestFromLector>> listRequestFromLectorKafkaTemplate;

    @Autowired
    private final RequestFromLectorKafkaProducerServiceRest requestFromLectorKafkaProducerService;

    @Autowired
    private final RequestFromLectorServiceApi requestFromLectorService;

    public RequestFromLectorConsumerServiceRest(ConsumerFactory<String, String> stringLectorKafkaTemplate
            , ConsumerFactory<String, RequestFromLector> requestFromLectorKafkaTemplate
            , ConsumerFactory<String, List<RequestFromLector>> listRequestFromLectorKafkaTemplate
            , RequestFromLectorKafkaProducerServiceRest requestFromLectorKafkaProducerService
            , RequestFromLectorServiceApi requestFromLectorService) {

        this.stringLectorKafkaTemplate = stringLectorKafkaTemplate;
        this.requestFromLectorKafkaTemplate = requestFromLectorKafkaTemplate;
        this.listRequestFromLectorKafkaTemplate = listRequestFromLectorKafkaTemplate;
        this.requestFromLectorKafkaProducerService = requestFromLectorKafkaProducerService;
        this.requestFromLectorService = requestFromLectorService;
    }


    @KafkaListener(topics = "giveallrequestsfromlector", groupId = "string")
    public void listenLectorFoo(String id) {
        logger.info("Received Message in giveallrequestsfromlector : " + id);
        Integer idLector = Integer.parseInt(id);
        List<RequestFromLector> requestsFromLectors = requestFromLectorService.getAllRequestsFromLectorService(idLector);
        if (requestsFromLectors == null) {
            requestsFromLectors = new ArrayList<>();
        }
        logger.info("RequestsFromLectors in consumer" + requestsFromLectors.toString());
        requestFromLectorKafkaProducerService.sendGiveAllRequestFromLector(requestsFromLectors);
    }


    @KafkaListener(topics = "updaterequestfromlector", groupId = "string")
    public void lectorUpdateListener(String requestFromLectorToUpdate) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            RequestFromLector requestFromLector = mapper.readValue(requestFromLectorToUpdate, RequestFromLector.class);
            System.out.println("Received Message in updateRequestFromLector : " + requestFromLector.toString());
            requestFromLector = requestFromLectorService.updateRequestFromLectorService(requestFromLector);
            requestFromLectorKafkaProducerService.sendUpdatedRequestFromLector(requestFromLector);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "deleterequestfromlector", groupId = "string")
    public void deleteLectorListener(String requestFromLector) throws Exception {
        try {
            logger.info("Received message for delete RequestFromLector  = : " + requestFromLector);
            ObjectMapper mapper = new ObjectMapper();
            RequestFromLector request = mapper.readValue(requestFromLector, RequestFromLector.class);

            request = requestFromLectorService.flushRequestFromLectorService(request);
            logger.info("Deleted RequestFromLector  =  " + request);
            requestFromLectorKafkaProducerService.sendDeleteRequestFromLector("" + request.getIdRequest());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "giverequestfromlectorbyid", groupId = "string")
    public void getLectorByIdListener(String idLector) throws Exception {
        try {
            logger.info("Received message for give RequestFromLector by id = : " + idLector);
            Integer id = Integer.parseInt(idLector);
            RequestFromLector requestFromLector = requestFromLectorService.getRequestOfLectorByIdRequestService(id);
            logger.info("Get RequestFromLector where id =  " + id);
            requestFromLectorKafkaProducerService.sendRequestFromLectorById(requestFromLector);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }


}
