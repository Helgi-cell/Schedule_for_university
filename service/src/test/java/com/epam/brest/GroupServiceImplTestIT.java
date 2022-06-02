package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
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


@SpringBootApplication
@SpringBootTest (classes= { LectorServiceImpl.class, RequestFromLectorServiceImpl.class, GroupServiceImpl.class
                          , DaoLectorImpl.class, DaoRequestFromLectorImpl.class, DaoGroupImpl.class})
@ComponentScan("com.epam.brest")
@EntityScan("com.epam.brest")
@Transactional()
public class GroupServiceImplTestIT {

    private final Logger logger = LogManager.getLogger(GroupServiceImplTestIT.class);

    @Autowired
    private GroupServiceApi groupService;

    @Autowired
    private LectorServiceApi lectorService;

    @Autowired
    private RequestFromLectorServiceApi requestFromLectorService;


    @BeforeEach
    public void setUp() {
        String[] groups = new String[]{"e1", "e2", "e3", "e4", "e5", "e6"};
        List<Group> grup = Arrays.stream(groups)
                .map(gr -> groupService.createNewGroupService(gr))
                .collect(Collectors.toList());
        Lector lector = lectorService.createNewLectorService(new Lector("TOMMY", "tom", "1111", "iuy@aa.com"));
    }

    @Test
    public void isGroupsTest() {
        logger.info("GET ALL GROUPS {}");
        List<String> groups = groupService.getAllGroupNamesService();
        Assertions.assertTrue(groups.size() == 6);
        List<Group> group = groupService.getAllGroupsService();
        Assertions.assertTrue(group.size() == 6);
    }

    @Test
    public void isGroupByIdTest() {
        logger.info("GET  GROUP BY ID {}");
        Group group = groupService.getGroupByGroupNameService("e1");
        group = groupService.getGroupById(group.getIdGroup());
        Assertions.assertTrue(group.getGroupName().equals("e1"));
    }


    @Test
    public void isInsertNewGroupTest() {
        logger.info("INSERT NEW GROUP {}");
        Lector lector = lectorService.getLectorByLectorsNameService("TOMMY");
        List<RequestFromLector> requestsFromLectorService = requestFromLectorService.getAllRequestsFromLectorService(lector.getIdLector());
        Assertions.assertTrue(requestsFromLectorService.size() == 6);
        Group group = groupService.createNewGroupService("e7");
        Assertions.assertTrue(group.getGroupName().equals("e7"));
        List<Group> groups = groupService.getAllGroupsService();
        Assertions.assertTrue(groups.size() == 7);
        lector = lectorService.getLectorByLectorsNameService("TOMMY");
        requestsFromLectorService = requestFromLectorService.getAllRequestsFromLectorService(lector.getIdLector());
        Assertions.assertTrue(requestsFromLectorService.size() == 7);
    }

    @Test
    public void isUpdateGroupNameTest() {
        logger.info("UPDATE GROUP {}");

        Lector lector = lectorService.getLectorByLectorsNameService("TOMMY");
        List<RequestFromLector> requestFromLectors = requestFromLectorService.getAllRequestsFromLectorService(lector.getIdLector());
        logger.info("Number requests {}" + requestFromLectors.size());
        Assertions.assertTrue(requestFromLectors.size() == 6);
        Group group = groupService.getAllGroupsService().get(0);
        group.setGroupName("w1");
        group = groupService.updateGroupNameService(group);
        Assertions.assertTrue(group.getGroupName().equals("w1"));
        lector = lectorService.getLectorByLectorsNameService("TOMMY");
        requestFromLectors = requestFromLectorService.getAllRequestsFromLectorService(lector.getIdLector());
        logger.info("Number requests {}" + requestFromLectors.size());
        Assertions.assertTrue(requestFromLectors.size() == 6);
        group = groupService.getGroupByGroupNameService("w1");
        Assertions.assertTrue(group.getGroupName().equals("w1"));
        List<Group> groups = groupService.getAllGroupsService();
        Assertions.assertTrue(groups.size() == 6);

    }

    @Test
    public void isDeleteGroupTest() {
        logger.info("DELETE GROUP {}");
        List<Group> groups = groupService.getAllGroupsService();
        Integer idGroup = groupService.deletegroupByIdService(groups.get(0).getIdGroup());
        Assertions.assertTrue(idGroup == groups.get(0).getIdGroup());
        List<Group> groups1 = groupService.getAllGroupsService();
        Assertions.assertTrue(groups.size() - groups1.size() == 1);
    }

}