package com.epam.brest;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootApplication
@SpringBootTest (classes= { LectorServiceImpl.class, RequestFromLectorServiceImpl.class, GroupServiceImpl.class
                          , DaoLectorImpl.class, DaoRequestFromLectorImpl.class, DaoGroupImpl.class})
@ComponentScan("com.epam.brest")
@EntityScan("com.epam.brest")
@Transactional()
public class LectorServiceImplTestIT {

    private final Logger logger = LogManager.getLogger(LectorServiceImplTestIT.class);

    @Autowired
    private LectorServiceApi lectorService;

    @Autowired
    private RequestFromLectorServiceApi requestFromLectorService;

    @Autowired
    private GroupServiceApi groupService;

    @BeforeEach
    public void setUp() {
        String[] groups = new String[]{"e1", "e2", "e3", "e4", "e5", "e6"};
        List<Group> grup = Arrays.stream(groups)
                .map(gr -> groupService.createNewGroupService(gr))
                .collect(Collectors.toList());
        Lector lector = lectorService.createNewLectorService(new Lector("TOMMY", "tom", "1111", "iuy@aa.com"));
    }


    @Test
    public void isNewLectorServiceImpl() {
        logger.info("GET ALL Lectors {}");
        List<Lector> lectors = (List<Lector>) lectorService.getAllLectorsService();
        logger.info("GET ALL Lectors {} SIZE = " + lectors.size());
        assertTrue(lectors.size() == 1);
        assertTrue(lectors.get(lectors.size() - 1).getNameLector().equals("TOMMY"));
        Lector lector = lectors.get(lectors.size() - 1);
        List<RequestFromLector> requestsFromLectorService = requestFromLectorService.getAllRequestsFromLectorService(lector.getIdLector());
        assertTrue(requestsFromLectorService.size() == 6);
    }

    @Test
    public void isGetLectorByNameServiceImpl() {
        logger.info("GET Lector BY Name {}");
        Lector lector = (Lector) lectorService.getLectorByLectorsNameService("TOMMY");
        assertTrue(lector.getNameLector().equals("TOMMY"));
        List<RequestFromLector> requestsFromLectorService = requestFromLectorService.getAllRequestsFromLectorService(lector.getIdLector());
        assertTrue(requestsFromLectorService.size() == 6);
    }

    @Test
    public void isGetLectorByEmailServiceAndIdImpl() {
        logger.info("GET Lector BY Email {}");
        Lector lector = (Lector) lectorService.getLectorByEmailService("iuy@aa.com");
        assertTrue(lector.getEmailLector().equals("iuy@aa.com"));
        List<RequestFromLector> requestsFromLectorService = requestFromLectorService.getAllRequestsFromLectorService(lector.getIdLector());
        assertTrue(requestsFromLectorService.size() == 6);
        lector = (Lector) lectorService.getLectorByEmailService("qqiuy@aa.com");
        assertFalse(lector.getEmailLector().equals("qqiuy@aa.com"));
        Lector lector1 = lectorService.getLectorByLectorsNameService("TOMMY");
        Lector lector2 = (Lector) lectorService.getLectorByIdLectorService(lector1.getIdLector());
        assertTrue(lector2.getNameLector().equals("TOMMY"));
    }

    @Test
    public void isCreateLectorService() {
       logger.info("CREATE NEW Lector {}");
       Lector lector = lectorService.createNewLectorService(new Lector("Mike", "mike", "1111", "isocrol@aa.com"));
       assertTrue(lector.getEmailLector().equals("isocrol@aa.com"));
       assertTrue(lector.getNameLector().equals("Mike"));
       Lector lector1 = lectorService.getLectorByLectorsNameService("Mike");
       assertTrue(lector1.getNameLector().equals("Mike") && lector1.getIdLector() == lector.getIdLector());
       List<RequestFromLector> requestsFromLectorService = requestFromLectorService.getAllRequestsFromLectorService(lector.getIdLector());
       assertTrue(requestsFromLectorService.size() == 6);
    }

    @Test
    public void isUpdateLectorService() {
        logger.info("UPDATE Lector {}");
        Lector lector = lectorService.createNewLectorService(new Lector("Mike", "mike", "1111", "isocrol@aa.com"));
        assertTrue(lector.getEmailLector().equals("isocrol@aa.com"));
        assertTrue(lector.getNameLector().equals("Mike"));
        Lector lector1 = lectorService.getLectorByLectorsNameService("Mike");
        assertTrue(lector1.getNameLector().equals("Mike") && lector1.getIdLector() == lector.getIdLector());
        lector.setNameLector("Kim");
        lector =  lectorService.updateLectorService(lector);
        assertTrue(lector.getNameLector().equals("Kim"));
        lector1 = lectorService.getLectorByLectorsNameService("Kim");
        assertTrue(lector1.getNameLector().equals("Kim") && lector1.getIdLector() == lector.getIdLector());
        List<RequestFromLector> requestsFromLector = requestFromLectorService.getAllRequestsFromLectorService(lector1.getIdLector());
        assertTrue(requestsFromLector.size() == 6);
    }

    @Test
    public void isDeleteLectorService() {
        logger.info("DELETE Lector {}");
        Lector lector = lectorService.createNewLectorService(new Lector("Mike", "mike", "1111", "isocrol@aa.com"));
        assertTrue(lector.getNameLector().equals("Mike"));
        assertTrue(lectorService.getAllLectorsService().size() == 2);
        lectorService.deleteLectorByIdLectorService(lector.getIdLector());
        assertTrue(lectorService.getAllLectorsService().size() == 1);

        lector = lectorService.createNewLectorService(new Lector("Mike", "mike", "1111", "isocrol@aa.com"));
        assertTrue(lectorService.getAllLectorsService().size() == 2);
        lectorService.deleteLectorByIdLectorService(lector.getIdLector());
        assertTrue(lectorService.getAllLectorsService().size() == 1);
        lector = lectorService.getLectorByIdLectorService(lector.getIdLector());
        assertTrue(lector.getNameLector() == null);
    }

    @Test
    public void isGetLectorByIdService() {
        logger.info("GET Lector BY ID{}");
        Lector lector = lectorService.createNewLectorService(new Lector("Mike", "mike", "1111", "isocrol@aa.com"));
        assertTrue(lector.getNameLector().equals("Mike"));
        assertTrue(lectorService.getAllLectorsService().size() == 2);
        Lector user1 = lectorService.getLectorByIdLectorService(lector.getIdLector());
        assertTrue(user1.getNameLector().equals("Mike"));
        assertTrue(user1.getNameLector().equals(lector.getNameLector()));
    }

}
