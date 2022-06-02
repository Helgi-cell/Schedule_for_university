package com.epam.brest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootApplication
@SpringBootTest (classes= { DaoLectorImpl.class, DaoRequestFromLectorImpl.class, DaoGroupImpl.class})
@ComponentScan("com.epam.brest")
@EntityScan("com.epam.brest")
@Transactional()
public class DaoLectorImplTestIT {

    private final Logger logger = LogManager.getLogger(DaoLectorImplTestIT.class);

    @Autowired
    private DaoLectorApi daoLector;

    @Autowired
    private DaoRequestFromLectorApi daoRequestFromLector;

    @Test
    public void testGetAll() {
        logger.info("GET ALL Lectors {}");
        daoLector.saveOrUpdateLector(new Lector("Monya", "monya", "1111", "email@mail.com"));
        List<Lector> lectors = (List<Lector>) daoLector.getAllLectors();
        assertTrue(lectors.size() == 1);
        assertTrue(lectors.get(0).getNameLector().equals("Monya"));
    }

    @Test
    public void testGetLectorByName() {
        logger.info("GET Lector by name {}");
        daoLector.saveOrUpdateLector(new Lector("Monya", "monya", "1111", "email@mail.com"));
        Lector lector = (Lector) daoLector.getLectorByName("Monya");
        assertTrue(lector.getNameLector().equals("Monya"));
    }

    @Test
    public void testGetLectorByEmail() {
        logger.info("GET Lector by email {}");
        daoLector.saveOrUpdateLector(new Lector("Monya", "monya", "1111", "email@mail.com"));
        Lector lector = (Lector) daoLector.getLectorByEmail("email@mail.com");
        assertTrue(lector.getEmailLector().equals("email@mail.com"));
    }

    @Test
    public void testGetLectorById() {
        logger.info("GET Lector by email {}");
        daoLector.saveOrUpdateLector(new Lector("Monya", "monya", "1111", "email@mail.com"));
        Lector lector = (Lector) daoLector.getLectorByEmail("email@mail.com");
        lector = (Lector) daoLector.getLectorById(lector.getIdLector());
        assertTrue(lector.getEmailLector().equals("email@mail.com"));
    }

    @Test
    public void testSaveAndGet() {
        logger.info("SAVE Lector {}");
        daoLector.saveOrUpdateLector(new Lector("Monya", "monya", "1111", "email@mail.com"));
        logger.info("Lector SAVED SUCCESS{}");
        logger.info("FIND Lector BY NAME {}");
        Lector lector = (Lector) daoLector.getLectorByName("Monya");
        assertTrue(lector.getNameLector().equals("Monya"));
        lector = (Lector) daoLector.getLectorByEmail("email@mail.com");
        assertTrue(lector.getEmailLector().equals("email@mail.com"));
        logger.info("FOUND Lector BY NAME SUCCESS{}");
    }

    @Test
    public void testSaveAndUpdate() {
        logger.info("SAVE Lector {}");
        daoLector.saveOrUpdateLector(new Lector("Monya", "monya", "1111", "email@mail.com"));
        Lector lector = (Lector) daoLector.getLectorByName("Monya");
        assertTrue(lector.getNameLector().equals("Monya"));
        logger.info("Lector SAVED AND READ BY NAME SUCCESS{}");
        Lector lectorUpdated = lector;
        assertTrue(lectorUpdated.getNameLector().equals("Monya") && lectorUpdated.getLoginLector().equals("monya"));
        lectorUpdated.setNameLector("Tony");
        logger.info("Lector UPDATE {}" + lectorUpdated);
        daoLector.saveOrUpdateLector(lectorUpdated);
        Lector lectorNew = daoLector.getLectorById(lectorUpdated.getIdLector());
        assertTrue(lectorNew.getNameLector().equals("Tony") && lectorNew.getLoginLector().equals("monya"));
        logger.info("Lector UPDATED AND READ SUCCESS{}");
    }


    @Test
    public void testDeleteByUser() {
        logger.info("DELETE Lector BY USER{}");
        daoLector.saveOrUpdateLector(new Lector("Monya", "monya", "1111", "email@mail.com"));
        List<Lector> lectors = (List<Lector>) daoLector.getAllLectors();
        assertTrue(lectors.size() == 1);
        Lector lector = lectors.get(0);
        daoRequestFromLector.deleteAllRequestsFromLector(lector.getIdLector());
        daoLector.deleteLectorById(lector.getIdLector());
        lector = (Lector) daoLector.getLectorByName(lector.getNameLector());
        assertTrue(lector.getIdLector() == 0);
        lector = (Lector) daoLector.getLectorByEmail(lector.getEmailLector());
        assertTrue(lector.getIdLector() == 0);
        logger.info("DELETE Lector BY USER SUCCESS {}");
    }


    @Test
    public void testDeleteById() {
        logger.info("DELETE Lector BY ID{}");
        daoLector.saveOrUpdateLector(new Lector("Monya", "monya", "1111", "email@mail.com"));
        List<Lector> lectors = (List<Lector>) daoLector.getAllLectors();
        assertTrue(lectors.size() == 1);
        Lector lector = lectors.get(0);
        daoRequestFromLector.deleteAllRequestsFromLector(lector.getIdLector());
        daoLector.deleteLectorById(lector.getIdLector());
        lector = (Lector) daoLector.getLectorByName(lector.getNameLector());
        assertTrue(lector.getIdLector() == 0);
        lector = (Lector) daoLector.getLectorByEmail(lector.getEmailLector());
        assertTrue(lector.getIdLector() == 0);
        logger.info("DELETE Lector BY USER SUCCESS {}");

    }


}
