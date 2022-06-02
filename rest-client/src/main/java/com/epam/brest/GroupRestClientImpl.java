package com.epam.brest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GroupRestClientImpl implements GroupServiceApi {

    private final Logger logger = LoggerFactory.getLogger(GroupRestClientImpl.class);

    private RestTemplate restTemplate;

    public GroupRestClientImpl (final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public List<String> getAllGroupNamesService() {
        logger.debug("GetAll Groups names ()");
        ResponseEntity responseEntity = restTemplate.getForEntity("/groups/get-all-names", List.class);
        return (List<String>) responseEntity.getBody();
    }

    @Override
    public List<Group> getAllGroupsService() {
        logger.debug("GetAll Groups ()");
        ResponseEntity responseEntity = restTemplate.getForEntity("/groups/get-all", List.class);
        return (List<Group>) responseEntity.getBody();
    }

    @Override
    public Group getGroupById(Integer idGroup) {
        logger.debug("Get Group by ID ()");
        ResponseEntity responseEntity = restTemplate.getForEntity("/groups/group/" + idGroup, Group.class);
        return (Group) responseEntity.getBody();
    }

    @Override
    public Integer deletegroupByIdService(Integer idGroup) {
        logger.debug("Delete group by ID () " + idGroup);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Integer> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange("/groups/group/delete/" + idGroup, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Group createNewGroupService(String newName) {
        logger.debug("Create group  () " + newName);
        ResponseEntity responseEntity = restTemplate.postForEntity("/groups/group/new", newName, Group.class);
        return (Group) responseEntity.getBody();
    }

    @Override
    public Group getGroupByGroupNameService(String name) {
        logger.debug("Get Group ()");
        ResponseEntity responseEntity = restTemplate.getForEntity(String.format("/groups/group/get-name?name=%s", name),
                                                                                Group.class);
        return (Group) responseEntity.getBody();    }

    @Override
    public Group updateGroupNameService(Group group) {
        logger.debug("Update group  () " + group);
        HttpEntity<Group> entity = new HttpEntity<>(group);
        ResponseEntity<Group> result = restTemplate.exchange("/groups/group/update",
                HttpMethod.PUT, entity, Group.class);
        return result.getBody();
    }


}
