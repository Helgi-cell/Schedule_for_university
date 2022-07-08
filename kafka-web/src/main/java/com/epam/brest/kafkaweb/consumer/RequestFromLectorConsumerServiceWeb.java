package com.epam.brest.kafkaweb.consumer;

import com.epam.brest.Group;
import com.epam.brest.RequestFromLector;
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
public class RequestFromLectorConsumerServiceWeb {

    private static final Logger logger = LoggerFactory.getLogger(RequestFromLectorConsumerServiceWeb.class);

    @Autowired
    private final ConsumerFactory<String, String> stringKafkaTemplate;

    @Autowired
    private final ConsumerFactory<String, RequestFromLector> requestFromLectorKafkaTemplate;

    @Autowired
    private final ConsumerFactory<String, List<RequestFromLector>> listRequestFromLectorKafkaTemplate;

    public Boolean isListRequestsFromLectorChanged = false;
    public Boolean isRequestFromLectorChanged = false;
    public Boolean isRequestFromLectorUpdated = false;
    public Boolean isRequestFromLectorDeleted = false;

    public RequestFromLector requestFromLector = new RequestFromLector();
    public List<RequestFromLector> listRequestFromLector = new ArrayList<>();

    public RequestFromLectorConsumerServiceWeb(ConsumerFactory<String, String> stringKafkaTemplate
            , ConsumerFactory<String, RequestFromLector> requestFromLectorKafkaTemplate
            , ConsumerFactory<String, List<RequestFromLector>> listRequestFromLectorKafkaTemplate) {

        this.stringKafkaTemplate = stringKafkaTemplate;
        this.requestFromLectorKafkaTemplate = requestFromLectorKafkaTemplate;
        this.listRequestFromLectorKafkaTemplate = listRequestFromLectorKafkaTemplate;
    }

    @KafkaListener(topics = "sendallrequestsfromlector", groupId = "listrequest")
    public void allRequestsFromLector(String allRequests) {
        logger.info("Received Message from senallrequestsfromlector : " + allRequests);
        try {
            ObjectMapper mapper = new ObjectMapper();
            listRequestFromLector = mapper.readValue(allRequests, new TypeReference<List<RequestFromLector>>() {
            });
            isListRequestsFromLectorChanged = true;
            logger.info("Parsed message from sendallrequestsfromlector : " + listRequestFromLector.toString());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "sendrequestfromlectorbyid", groupId = "request")
    public void requestsFromLectorById(String requestFromLectorJSON) {
        logger.info("Received Message from senrequestfromlector : " + requestFromLector);
        try {
            ObjectMapper mapper = new ObjectMapper();
            requestFromLector = mapper.readValue(requestFromLectorJSON, new TypeReference<RequestFromLector>() {
            });

            isRequestFromLectorChanged = true;
            logger.info("Parsed message from sendrequestfromlector : " + listRequestFromLector.toString());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "updatedrequestfromlector", groupId = "request")
    public void updateRequestsFromLectorById(String updatedRequestFromLector) {
        logger.info("Received Message from updaterequestfromlector : " + updatedRequestFromLector);
        try {
            ObjectMapper mapper = new ObjectMapper();
            requestFromLector = mapper.readValue(updatedRequestFromLector, new TypeReference<RequestFromLector>() {
            });
            isRequestFromLectorUpdated = true;
            logger.info("Parsed message from updaterequestfromlector : " + listRequestFromLector.toString());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "deletedrequestfromlector", groupId = "request")
    public void deleteRequestsFromLectorById(String idRequestFromLector) {
        logger.info("Received Message from deletedrequestfromlector : " + idRequestFromLector);
        try {
            ObjectMapper mapper = new ObjectMapper();
            Integer idRequest = mapper.readValue(idRequestFromLector, new TypeReference<Integer>() {
            });
            isRequestFromLectorDeleted = true;
            logger.info("Parsed message from deletedrequestfromlector : " + idRequest);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }
}
