package com.epam.brest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class LectorRestClientImpl implements LectorServiceApi {

    private final Logger logger = LoggerFactory.getLogger(LectorRestClientImpl.class);

    private RestTemplate restTemplate;

    public LectorRestClientImpl (final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public List<Lector> getAllLectorsService() {
        logger.debug("GetAll Lectors ()");
        ResponseEntity responseEntity = restTemplate.getForEntity("/lectors/get-all", List.class);
        return (List<Lector>) responseEntity.getBody();
    }

    @Override
    public Lector getLectorByLectorsNameService(String name) {
        logger.debug("Get Lector by the name () " + name);
        ResponseEntity responseEntity = restTemplate.getForEntity(String.format("/lectors/lector/get-name?name=%s", name),
                                                                       Lector.class);
        return (Lector) responseEntity.getBody();
    }

    @Override
    public Lector getLectorByEmailService(String email) {
        logger.debug("Get Lector by the email () " + email);
        ResponseEntity responseEntity = restTemplate.getForEntity(String.format("/lectors/lector/get-email?email=%s", email),
                                                                  Lector.class);
        return (Lector) responseEntity.getBody();
    }

    @Override
    public Lector getLectorByIdLectorService(Integer id) {
        logger.debug("Get Lector by the email () " + id);
        ResponseEntity responseEntity = restTemplate.getForEntity(String.format("/lectors/lector/%d", id), Lector.class);
        return (Lector) responseEntity.getBody();
    }

    @Override
    public Integer deleteLectorByIdLectorService(Integer id) {
        logger.debug("Delete Lector by id () " + id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Lector> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(String.format("/lectors/lector/%d/delete", id),
                                      HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Lector updateLectorService(Lector lector) {
        logger.debug("Update Lector  () " + lector);
        HttpEntity<Lector> entity = new HttpEntity<>(lector);
        ResponseEntity<Lector> result = restTemplate.exchange("/lectors/lector/update",
                                                                   HttpMethod.PUT, entity, Lector.class);
        return result.getBody();
    }

    @Override
    public Lector createNewLectorService(Lector lector) {
        logger.debug("Create Lector  () " + lector);
        ResponseEntity responseEntity = restTemplate.postForEntity("/lectors/lector/new",
                                                                    lector, Lector.class);
        return (Lector) responseEntity.getBody();
    }

}


