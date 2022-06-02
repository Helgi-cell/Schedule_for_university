package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@ComponentScan("com.epam.brest")
@EntityScan("com.epam.brest")
@Service
public class LectorServiceImpl implements LectorServiceApi {

    private final Logger logger = LogManager.getLogger(LectorServiceImpl.class);

    @Autowired
    private DaoLectorApi daoLector;

    @Autowired
    private DaoRequestFromLectorApi daoRequestFromLector;

    @Autowired
    private DaoGroupApi daoGroup;


    @Override
    public List<Lector> getAllLectorsService() {
        logger.info("Get all lectors service");
        return (List<Lector>) daoLector.getAllLectors();
    }

    @Override
    public Lector getLectorByLectorsNameService(String name) {
        logger.info("Get lector by lector name service " + name);
        return (Lector) daoLector.getLectorByName(name);
    }

    @Override
    public Lector getLectorByEmailService(String email) {
        logger.info("Get lector by the email service " + email);
        return (Lector) daoLector.getLectorByEmail(email);
    }

    @Override
    public Lector getLectorByIdLectorService(Integer id) {
        logger.info("Get lector by idLector service " + id);
        return (Lector) daoLector.getLectorById(id);
    }

    @Override
    public Integer deleteLectorByIdLectorService(Integer id) {
        logger.info("Delete lector by idLector service " + id);
        daoRequestFromLector.deleteAllRequestsFromLector(id);
        daoLector.deleteLectorById(id);
        return (Integer) id;
    }

    @Override
    public Lector createNewLectorService(Lector lector) {
        logger.info("Create new lector service " + lector);
        lector = daoLector.saveOrUpdateLector(lector);
        List <String> groups = daoGroup.getAllGroupsNames();
        daoRequestFromLector.createEmptyRequestsForNewLector(lector.getIdLector(), groups);
        return (Lector) lector;
    }

    @Override
    public Lector updateLectorService(Lector lector) {
        logger.info("Update lector service " + lector);
        return (Lector) daoLector.saveOrUpdateLector(lector);
    }

}
