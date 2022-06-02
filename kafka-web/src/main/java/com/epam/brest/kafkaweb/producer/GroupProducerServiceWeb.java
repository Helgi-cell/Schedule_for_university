package com.epam.brest.kafkaweb.producer;

import com.epam.brest.Group;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupProducerServiceWeb {
    private static final Logger logger = LoggerFactory.getLogger(GroupProducerServiceWeb.class);
    @Autowired
    private KafkaTemplate<String, String> stringGroupKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, Group> groupKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, List<Group>> listGroupKafkaTemplate;


    private String givallgroups = "giveallgroups";
    private String newgroup = "newgroup";
    private String updategroup = "updategroup";
    private String deletegroup = "deletegroup";

    public void sendGiveAllGroups(String message) {
        logger.info("GroupProducerServiceWeb send giveallgroups");
        stringGroupKafkaTemplate.send(givallgroups, message);
    }

    public void sendCreateNewGroup(Group group) throws JsonProcessingException {
        logger.info("GroupproducerServiceWeb send create new group : " + group);
        groupKafkaTemplate.send(newgroup, group);
    }

    public void sendUpdateGroup(Group group) throws JsonProcessingException {
        logger.info("GroupproducerServiceWeb send update group : " + group);
        groupKafkaTemplate.send(updategroup, group);
    }

    public void sendDeleteGroup(String id) {
        logger.info("GroupproducerServiceWeb send delete group by is  : " + id);
        stringGroupKafkaTemplate.send(deletegroup, id);
    }

}
