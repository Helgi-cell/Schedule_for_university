package com.epam.brest.kafkarest.producer;

import com.epam.brest.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupKafkaProducerServiceRest {
    @Autowired
    private KafkaTemplate<String, String> stringGroupKafkaTemplate;
    @Autowired
    private KafkaTemplate <String, Group> groupKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, List<Group>> listGroupKafkaTemplate;


    private String sendallgroups = "sendallgroups";
    private String newgroupcreated = "newgroupcreated";
    private String updatedgroup = "updatedgroup";
    private String deletedgroup = "deletedgroup";


    public void sendGiveAllGroups(List<Group> groups) { listGroupKafkaTemplate.send(sendallgroups, groups); }
    public void sendCreateNewGroup(Group group) {
        groupKafkaTemplate.send(newgroupcreated, group);
    }
    public void sendUpdateGroup(Group group) {
      groupKafkaTemplate.send(updatedgroup, group);
    }
    public void sendDeleteGroup(String id) {
        stringGroupKafkaTemplate.send(deletedgroup, id);
    }

}
