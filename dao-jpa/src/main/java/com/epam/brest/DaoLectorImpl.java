package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@ComponentScan("com.epam.brest")
//@EnableJpaRepositories(basePackages = "com.epam.brest")
public class DaoLectorImpl implements DaoLectorApi {

    private final Logger logger = LogManager.getLogger(DaoLectorImpl.class);

    @Autowired
    private LectorsJpaRepository lectorsRepository;


    public List<Lector> getAllLectors() {
        logger.info("GET ALL LECTORS {}");
        return (List<Lector>) lectorsRepository.findAllLectors();
    }

    public Lector getLectorByName(String name) {
        logger.info("GET LECTOR BY the NAME{} " + name);
        return (Lector) lectorsRepository.findLectorByLectorsName(name);
    }

    public Lector getLectorByEmail(String emailLector) {
        logger.info("GET LECTOR BY the EMAIL{} " + emailLector);
        return (Lector) lectorsRepository.findLectorByEmail(emailLector);
    }

    public Lector getLectorById(Integer idLector) {
        logger.info("GET Lector BY ID{} " + idLector);
        return (Lector) lectorsRepository.findLectorByLectorsId(idLector);
    }

    public Lector saveOrUpdateLector(Lector lector) {
        logger.info("SAVE Lector {} " + lector);
        return (Lector) lectorsRepository.saveOrUpdateLector(lector);
    }

    public void deleteLectorById(Integer idLector) {
        logger.info("DELETE Lector id = " + idLector );
        lectorsRepository.deleteLectorByLectorsId(idLector);
    }

}
