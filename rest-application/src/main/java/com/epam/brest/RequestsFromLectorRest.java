package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
public class RequestsFromLectorRest {

    private final Logger logger = LogManager.getLogger(RequestsFromLectorRest.class);

    @Autowired
    RequestFromLectorServiceApi requestFromLectorService;


    @GetMapping ("/lectors/lector/{id}/requests/all")
    @Transactional(readOnly = true)
    public List<RequestFromLector> getAllRequests(@PathVariable int id) {
        logger.debug("get all requests from lector where foreign key id = ", id);
        return (List<RequestFromLector>) requestFromLectorService.getAllRequestsFromLectorService(id);
    }

    @GetMapping ("/lectors/lector/request/{id}")
    @Transactional(readOnly = true)
    public RequestFromLector getRequestByIdr(@PathVariable int id) {
        logger.debug("get request from lector where id = ", id);
        return (RequestFromLector) requestFromLectorService.getRequestOfLectorByIdRequestService(id);
    }

    @PutMapping(path = "/lectors/lector/request/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RequestFromLector> updateRequest(@RequestBody RequestFromLector request) {
        logger.debug("update request from lector ", request);
        request= requestFromLectorService.updateRequestFromLectorService(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PutMapping(path = "/lectors/lector/request/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity <RequestFromLector> deleteRequest(@RequestBody RequestFromLector request) {
        logger.debug("delete request from lector = ", request);
        requestFromLectorService.flushRequestFromLectorService(request);
        return new ResponseEntity(request, HttpStatus.OK);
    }

}
