package com.epam.brest.kafkarest.consumer;

import com.epam.brest.Group;
import com.epam.brest.GroupServiceApi;
import com.epam.brest.kafkarest.producer.GroupKafkaProducerServiceRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableJpaRepositories(basePackages = "com.epam.brest")
public class GroupKafkaConsumerServiceRest {

    private final Logger logger = LogManager.getLogger(GroupKafkaConsumerServiceRest.class);
    @Autowired
    private final ConsumerFactory<String, String> stringKafkaTemplate;
    @Autowired
    private final ConsumerFactory<String, Group> groupKafkaTemplate;
    @Autowired
    private final ConsumerFactory<String, List<Group>> listGroupKafkaTemplate;
    @Autowired
    private final GroupServiceApi groupService;

    @Autowired
    private final GroupKafkaProducerServiceRest groupProducerService;

    public GroupKafkaConsumerServiceRest(ConsumerFactory<String, String> stringKafkaTemplate
            , ConsumerFactory<String, Group> groupKafkaTemplate
            , ConsumerFactory<String, List<Group>> listGroupKafkaTemplate
            , GroupServiceApi groupService
            , GroupKafkaProducerServiceRest groupProducerService) {
        this.stringKafkaTemplate = stringKafkaTemplate;
        this.groupKafkaTemplate = groupKafkaTemplate;
        this.listGroupKafkaTemplate = listGroupKafkaTemplate;
        this.groupService = groupService;
        this.groupProducerService = groupProducerService;
    }

    @KafkaListener(topics = "giveallgroups", groupId = "string")
    public void listenGroupFoo(String message) {
        logger.info("Received Message in string : " + message);
        List<Group> groups = groupService.getAllGroupsService();
        if (groups == null) {
            groups = new ArrayList<>();
        }
        logger.info("Groups in consumer" + groups.toString());
        groupProducerService.sendGiveAllGroups(groups);
    }

    @KafkaListener(topics = "newgroup", groupId = "string")
    public void newGroupListener(String newNameGroup) throws Exception {
        try {
            logger.info("Received message in string newGroup: " + newNameGroup);
            ObjectMapper mapper = new ObjectMapper();
            Group group = mapper.readValue(newNameGroup, Group.class);
            System.out.println("Received Message in newNameGroup : " + newNameGroup);
            group = groupService.createNewGroupService(group.getGroupName());
            logger.info("Created new Group " + group.toString());
            groupProducerService.sendCreateNewGroup(group);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "updategroup", groupId = "string")
    public void groupUpdateListener(String groupToUpdate) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Group group = mapper.readValue(groupToUpdate, Group.class);
            System.out.println("Received Message in greeting : " + group.toString());
            group = groupService.updateGroupNameService(group);
            groupProducerService.sendUpdateGroup(group);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "deletegroup", groupId = "string")
    public void deleteGroupListener(String idGroup) throws Exception {
        try {
            logger.info("Received message for delete Group id = : " + idGroup);
            Integer id = Integer.parseInt(idGroup);
            id = groupService.deletegroupByIdService(id);
            logger.info("Deleted Group where id =  " + id);
            groupProducerService.sendDeleteGroup("" + id);
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

}
