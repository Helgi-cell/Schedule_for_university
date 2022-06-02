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
public class RequestFromLectorServiceImpl implements RequestFromLectorServiceApi {

    private final Logger logger = LogManager.getLogger(RequestFromLectorServiceImpl.class);

    @Autowired
    private DaoRequestFromLectorApi daoRequestFromLector;

    @Autowired
    private DaoGroupApi daoGroup;

    @Autowired
    private DaoLectorApi daoLector;


    @Override
    public List<RequestFromLector> getAllRequestsFromLectorService(Integer idLector) {
        logger.info("Get all requests from lector service " + idLector);
        return (List<RequestFromLector>) daoRequestFromLector.getAllRequestsFromLectorByIdLector(idLector);
    }

    @Override
    public RequestFromLector getRequestOfLectorByIdRequestService(Integer idRequest) {
        logger.info("Get request from lector service " + idRequest);
        return (RequestFromLector) daoRequestFromLector.getRequestFromLectorByRequestId(idRequest);
    }

    @Override
    public RequestFromLector updateRequestFromLectorService(RequestFromLector requestFromLector) {
        logger.info("Update request from lector service " + requestFromLector);
        return (RequestFromLector) daoRequestFromLector.updateRequestFromLector(requestFromLector);
    }

    @Override
    public RequestFromLector flushRequestFromLectorService(RequestFromLector request) {
        logger.info("Flush request from lector service Flushed :  " + request);
        return (RequestFromLector) daoRequestFromLector.flushRequestForLector(request);
    }

}
