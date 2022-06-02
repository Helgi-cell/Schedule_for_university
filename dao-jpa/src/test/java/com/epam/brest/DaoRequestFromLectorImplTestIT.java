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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootApplication
@SpringBootTest (classes= { DaoLectorImpl.class, DaoRequestFromLectorImpl.class, DaoGroupImpl.class})
@ComponentScan("com.epam.brest")
@EntityScan("com.epam.brest")
@Transactional()
public class DaoRequestFromLectorImplTestIT {

    private final Logger logger = LogManager.getLogger(DaoRequestFromLectorImplTestIT.class);

    @Autowired
    private DaoRequestFromLectorApi daoRequestFromLector;

    @Autowired
    private DaoLectorApi daoLector;

    @Autowired
    private DaoGroupApi daoGroup;

    @BeforeEach
    public void setUp() {
        List <String> groups = Arrays.asList(new String[]{"e1", "e2", "e3", "e4", "e5"});
        groups.stream().peek(group -> daoGroup.insertNewGroup(group));
        Lector lector = new Lector("JoeFrasier", "joe", "1111", "mail@mail.com");
        daoLector.saveOrUpdateLector(lector);
        lector = daoLector.getLectorByName("JoeFrasier");
        daoRequestFromLector.createEmptyRequestsForNewLector(lector.getIdLector(), groups);
        lector = new Lector("MikeTyson", "mike", "2222", "mike@tyson.com");
        daoLector.saveOrUpdateLector(lector);
        lector = daoLector.getLectorByName("MikeTyson");
        daoRequestFromLector.createEmptyRequestsForNewLector(lector.getIdLector(), groups);
        lector = new Lector("SonnyListon", "sonny", "3333", "sonny@liston.com");
        daoLector.saveOrUpdateLector(lector);
        lector = daoLector.getLectorByName("SonnyListon");
        daoRequestFromLector.createEmptyRequestsForNewLector(lector.getIdLector(), groups);
    }


    @Test
    public void getRequestsByIdlector(){
        logger.info("Get requests from lector by IdLector");
        Lector lector = daoLector.getLectorByName("MikeTyson");
        List<RequestFromLector> requestsFromLector = daoRequestFromLector.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        logger.info("Get requests from lector by IdLector -> " + requestsFromLector.size() + " id= " + lector.getNameLector());
        assertTrue(requestsFromLector.size() == 5);
    }

    @Test
    public void getRequestsByIdRequest(){
        logger.info("Get request from lector by Idrequest");
        Lector lector = daoLector.getLectorByName("MikeTyson");
        List<RequestFromLector> requestsFromLector = daoRequestFromLector.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        RequestFromLector requestFromLector = daoRequestFromLector.getRequestFromLectorByRequestId(requestsFromLector.get(0).getIdRequest());
        logger.info("Get request from lector by IdRequest -> " + requestFromLector);
        assertTrue(requestFromLector.getNumberOfPairs().equals("0"));
    }

    @Test
    public void createRequestsByLectorsWhenCreateNewGroup(){
        logger.info("Create request when new group");
        List<Lector> lectors = (List<Lector>) daoLector.getAllLectors();
        List<Integer> idLectors = new ArrayList<>();
        for(Lector lector : lectors){idLectors.add(lector.getIdLector());}
        daoRequestFromLector.createRequestsForLectorsWhenCreateNewGroup("e11", idLectors );
        Lector lector = daoLector.getLectorByName("MikeTyson");
        List<RequestFromLector> requestsFromLector = daoRequestFromLector.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        assertTrue(requestsFromLector.size() == 6);
        lector = daoLector.getLectorByName("JoeFrasier");
        requestsFromLector = daoRequestFromLector.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        assertTrue(requestsFromLector.size() == 6);
    }

    @Test
    public void updateRequestsByLectorsWhenCreateNewGroup(){
        logger.info("Update request ");
        Lector lector = (Lector) daoLector.getLectorByName("MikeTyson");
        RequestFromLector request = daoRequestFromLector.getAllRequestsFromLectorByIdLector(lector.getIdLector()).get(0);
        request.setNumberOfPairs("2");
        daoRequestFromLector.updateRequestFromLector(request);
        request = daoRequestFromLector.getRequestFromLectorByRequestId(request.getIdRequest());
        assertTrue(request.getNumberOfPairs().equals("2"));
    }

    @Test
    public void flushRequestByLector(){
        logger.info("Flush request ");
        Lector lector = (Lector) daoLector.getLectorByName("MikeTyson");
        RequestFromLector request = daoRequestFromLector.getAllRequestsFromLectorByIdLector(lector.getIdLector()).get(0);
        request.setNumberOfPairs("2");
        daoRequestFromLector.updateRequestFromLector(request);
        request = daoRequestFromLector.getRequestFromLectorByRequestId(request.getIdRequest());
        assertTrue(request.getNumberOfPairs().equals("2"));
        request = daoRequestFromLector.flushRequestForLector(request);
        assertTrue(request.getNumberOfPairs().equals("0"));
    }

    @Test
    public void deleteAllRequestsFromLector(){
        logger.info("Delete all requests ");
        Lector lector = (Lector) daoLector.getLectorByName("MikeTyson");
        daoRequestFromLector.deleteAllRequestsFromLector(lector.getIdLector());
        List<RequestFromLector> requestsFromLector = daoRequestFromLector.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        assertTrue(requestsFromLector.size() == 0);
    }

    @Test
    public void deleteRequestsFromLectorsWhenDeletedGroup(){
        logger.info("Delete requests when deleted group");
        Lector lector = (Lector) daoLector.getLectorByName("MikeTyson");
        daoRequestFromLector.deleteFromAllLectorsRequestsWhenDeletedGroup("e1");
        List<RequestFromLector> requestsFromLector = daoRequestFromLector.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        assertTrue(requestsFromLector.size() == 4);
    }

    @Test
    public void updateRequestsFromLectorsWhenChangedGroup(){
        logger.info("Update requests when changed group");
        daoRequestFromLector.updateAllLectorsRequestsWhenChangedGroup("w1", "e1");
        Lector lector = (Lector) daoLector.getLectorByName("MikeTyson");
        List<RequestFromLector> requestFromLectors = daoRequestFromLector.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        assertTrue(requestFromLectors.size() == 5);
        requestFromLectors = requestFromLectors.stream().filter(request -> request.getGroup().equals("w1")).collect(Collectors.toList());
        logger.info("Update requests when changed group ===> " + requestFromLectors );
        assertTrue(requestFromLectors.size() == 1);
    }

}
