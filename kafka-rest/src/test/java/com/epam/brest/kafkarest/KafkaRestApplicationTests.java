package com.epam.brest.kafkarest;

import com.epam.brest.*;
import com.epam.brest.kafkarest.config.producer.GroupKafkaProducerConfigRest;
import com.epam.brest.kafkarest.config.producer.LectorKafkaProducerConfigRest;
import com.epam.brest.kafkarest.producer.GroupKafkaProducerServiceRest;
import com.epam.brest.kafkarest.producer.LectorKafkaProducerServiceRest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {GroupServiceImpl.class, DaoLectorImpl.class, DaoScheduleDtoImpl.class
        , DaoGroupImpl.class, LectorServiceImpl.class, ScheduleDtoServiceImpl.class
        , GroupKafkaProducerConfigRest.class, LectorKafkaProducerConfigRest.class})

@Transactional()
@DirtiesContext
public class KafkaRestApplicationTests {

    @Autowired
    GroupServiceApi groupService;

    @Autowired
    LectorServiceApi lectorService;

    @Autowired
    RequestFromLectorServiceApi requestFromLectorService;

    @Autowired
    ScheduleDtoServiceApi scheduleDtoService;

    @Autowired
    GroupKafkaProducerServiceRest groupKafkaProducerServiceRest;

    @Autowired
    LectorKafkaProducerServiceRest lectorKafkaProducerServiceRest;



    @Test
    void contextLoads() {
    }

    @Test
    void isGroups() {
        Group group = groupService.createNewGroupService("eeee");
        assertTrue(group.getGroupName().equals("eeee"));
        List<Group> groups = groupService.getAllGroupsService();
        assertTrue(groups.size() > 0);

    }


}
