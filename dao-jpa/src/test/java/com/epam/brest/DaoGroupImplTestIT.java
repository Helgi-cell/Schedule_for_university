package com.epam.brest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootApplication
@SpringBootTest (classes= { DaoLectorImpl.class, DaoRequestFromLectorImpl.class, DaoGroupImpl.class})
@ComponentScan("com.epam.brest")
@EntityScan("com.epam.brest")
@Transactional()
public class DaoGroupImplTestIT {

    private final Logger logger = LogManager.getLogger(DaoGroupImplTestIT.class);

    @Autowired
    private DaoGroupApi daoGroup;

    @BeforeEach
    public void setUp() {
        String[] groups = new String[]{"e1", "e2", "e3", "e4", "e5", "e6"};
        List<Group> grup = Arrays.stream(groups)
                .map(gr -> daoGroup.insertNewGroup(gr))
                .collect(Collectors.toList());
    }


    @Test
    public void testGetAllGroups() {
        logger.info("GET ALL GROUPS {}");
        List<Group> groups = (List<Group>) daoGroup.getAllGroups();
        assertTrue(groups.get(0).getGroupName() == "e1");
    }

    @Test
    public void testGetAllGroupsByNames() {
        logger.info("GET ALL GROUP NAMES{}");
        List<String> groups = (List<String>) daoGroup.getAllGroupsNames();
        assertTrue(groups.size() == 6);
    }

    @Test
    public void testGetGroupById() {
        logger.info("GET  GROUP BY ID{}");
        Group group = daoGroup.getGroupByName("e1");
        group = (Group) daoGroup.getGroupByid(group.getIdGroup());
        assertTrue(group.getGroupName().equals("e1"));
    }

    @Test
    public void testAddGroup() {
        logger.info("ADD NEW GROUP {}");
        daoGroup.insertNewGroup("a1");
        List<String> groups = (List<String>) daoGroup.getAllGroupsNames();
        assertTrue(groups.size() == 7);
    }

    @Test
    public void testUpdateGroupName() {
        logger.info("UPDATE GROUP NAME {}");
        Group group = daoGroup.getAllGroups().get(0);
        String oldGroup = group.getGroupName();
        group.setGroupName("a1");
        Group updatedGroup = daoGroup.updateGroup(group);
        group = daoGroup.getGroupByName("a1");
        assertTrue(group.getGroupName().equals("a1")
                           && updatedGroup.getIdGroup() == group.getIdGroup());
        group = daoGroup.getGroupByName(oldGroup);
        assertTrue(group.getGroupName() == "IsEmpty");
    }


    @Test
    public void testDeleteGroupById() {
        logger.info("DELETE GROUP BY ID {}");
        List<Group> groups = (List<Group>) daoGroup.getAllGroups();
        Group group = groups.get(0);
        Integer deletedGroup = daoGroup.deleteGroupById(group.getIdGroup());
        assertTrue(deletedGroup == group.getIdGroup());
        List <Group> groups1 = daoGroup.getAllGroups();
        assertTrue(groups.size() > groups1.size());
    }

}
