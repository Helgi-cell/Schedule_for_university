package com.epam.brest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RequestFromLectorRestClientImpl implements RequestFromLectorServiceApi {

    private final Logger logger = LoggerFactory.getLogger(LectorRestClientImpl.class);

    private RestTemplate restTemplate;

    public RequestFromLectorRestClientImpl (final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public List<RequestFromLector> getAllRequestsFromLectorService(Integer id) {
        logger.debug("Get all requests from Lector by idLector () " + id);
        ResponseEntity responseEntity = restTemplate.getForEntity(String.format("/lectors/lector/%d/requests/all", id),
                                                                                List.class);
        return (List<RequestFromLector>) responseEntity.getBody();
    }

    @Override
    public RequestFromLector getRequestOfLectorByIdRequestService(Integer idR) {
        logger.debug("Get request by the idRequest () " + idR);
        ResponseEntity responseEntity = restTemplate.getForEntity(String.format("/lectors/lector/request/%d", idR),
                                                                                RequestFromLector.class);
        return (RequestFromLector) responseEntity.getBody();
    }

    @Override
    public RequestFromLector updateRequestFromLectorService(RequestFromLector request) {
        logger.debug("Update request  () " + request);
        HttpEntity<RequestFromLector> entity = new HttpEntity<>(request);
        ResponseEntity<RequestFromLector> result = restTemplate.exchange("/lectors/lector/request/update",
                HttpMethod.PUT, entity, RequestFromLector.class);
        return result.getBody();
    }

    @Override
    public RequestFromLector flushRequestFromLectorService(RequestFromLector request) {
        HttpEntity<RequestFromLector> entity = new HttpEntity<>(request);
        ResponseEntity<RequestFromLector> result = restTemplate.exchange("/lectors/lector/request/delete",
                HttpMethod.PUT, entity, RequestFromLector.class);
        return result.getBody();
    }

}
