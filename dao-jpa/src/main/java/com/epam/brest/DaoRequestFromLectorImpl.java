package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@ComponentScan("com.epam.brest")
//@EnableJpaRepositories(basePackages = "com.epam.brest")
public class DaoRequestFromLectorImpl implements DaoRequestFromLectorApi {

    private final Logger logger = LogManager.getLogger(DaoRequestFromLectorImpl.class);

    @Autowired
    private RequestFromLectorJpaRepository requestFromLectorRepository;


    public List<RequestFromLector> getAllRequestsFromLectorByIdLector(Integer idLector) {
        logger.info("GET ALL REQUESTS OF the Lector {}" + idLector);
        return (List<RequestFromLector>) requestFromLectorRepository.findAllByForeignKey(idLector);
    }

    public RequestFromLector getRequestFromLectorByRequestId(Integer idRequest){
        logger.info("GET REQUEST BY IDRequest {}" + idRequest);
        return (RequestFromLector) requestFromLectorRepository.findById(idRequest).get();
    }

    public List<RequestFromLector> createEmptyRequestsForNewLector(Integer idLector, List<String> groupe) {
        logger.info("SAVE Requests for new Lector {} " + idLector);
        return (List<RequestFromLector>) requestFromLectorRepository.createRequestsforNewLector(groupe, idLector);
    }

    public List<RequestFromLector> createRequestsForLectorsWhenCreateNewGroup(String group, List<Integer> lectorsId ){
        logger.info("SAVE Request for new Group {} " + group);
        return (List<RequestFromLector>) requestFromLectorRepository.addNewGroupeInAllLectorRequests(lectorsId, group);
    }

    public RequestFromLector updateRequestFromLector(RequestFromLector requestFromLector){
        logger.info("Update Request after change {} " + requestFromLector);
        return (RequestFromLector) requestFromLectorRepository.saveAndFlush(requestFromLector);
    }

    public List<RequestFromLector> updateAllRequestsForLector(List<RequestFromLector> requestsFromLector){
        logger.info("Update All Requests for Lector after change {} ");
        return (List<RequestFromLector>) requestFromLectorRepository.saveAllAndFlush((Iterable<RequestFromLector>) requestsFromLector);
    }

    public RequestFromLector flushRequestForLector(RequestFromLector requestFromLector){
        logger.info("Flush Request to null position {} " + requestFromLector);
        requestFromLector.setNumberOfPairs("0");
        requestFromLector.setSubjectOfLector("    ");
        requestFromLector.setDate(new Date());
        return (RequestFromLector) requestFromLectorRepository.saveAndFlush(requestFromLector);
    }

    public void deleteAllRequestsFromLector(Integer idLector){
        logger.info("Delete Requests when Lector deleted {idLector} =  " + idLector);
        List <RequestFromLector> requestsFromLector = requestFromLectorRepository.findAllByForeignKey(idLector);
        requestFromLectorRepository.deleteAll((Iterable<? extends RequestFromLector>) requestsFromLector);
    }

    @Override
    public boolean deleteFromAllLectorsRequestsWhenDeletedGroup(String nameGroup) {
        return (boolean) requestFromLectorRepository.deleteRequestsWhenDeletedGroup(nameGroup);
    }

    @Override
    public boolean updateAllLectorsRequestsWhenChangedGroup(String newGroup, String oldGroup) {
        return (boolean) requestFromLectorRepository.updateRequestsWhenChangedNameGroup(newGroup, oldGroup);
    }

}
