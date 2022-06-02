package com.epam.brest;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EntityScan("com.epam.brest")
@ComponentScan("com.epam.brest.*")
public interface DaoRequestFromLectorApi {

    public List<RequestFromLector> getAllRequestsFromLectorByIdLector(Integer id);

    public RequestFromLector getRequestFromLectorByRequestId(Integer idR);

    public List<RequestFromLector> createEmptyRequestsForNewLector(Integer id, List<String> groupe);

    public List<RequestFromLector> createRequestsForLectorsWhenCreateNewGroup(String groupe, List<Integer> usersId );

    public RequestFromLector updateRequestFromLector(RequestFromLector request);

    public List<RequestFromLector> updateAllRequestsForLector(List<RequestFromLector> requests);

    public RequestFromLector flushRequestForLector(RequestFromLector request);

    public void deleteAllRequestsFromLector(Integer id);

    public boolean deleteFromAllLectorsRequestsWhenDeletedGroup(String nameGroup);

    public boolean updateAllLectorsRequestsWhenChangedGroup(String newGroup, String oldGroup);
}
