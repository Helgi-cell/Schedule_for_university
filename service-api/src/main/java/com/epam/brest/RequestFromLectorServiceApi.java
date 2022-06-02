package com.epam.brest;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RequestFromLectorServiceApi {

    List<RequestFromLector> getAllRequestsFromLectorService(Integer id);

    RequestFromLector getRequestOfLectorByIdRequestService(Integer idR);

    RequestFromLector updateRequestFromLectorService(RequestFromLector request);

    RequestFromLector flushRequestFromLectorService(RequestFromLector request);

}
