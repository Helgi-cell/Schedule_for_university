package com.epam.brest;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LectorServiceApi {

    List<Lector> getAllLectorsService();

    Lector getLectorByLectorsNameService(String name);

    Lector getLectorByEmailService(String email);

    Lector getLectorByIdLectorService(Integer id);

    Lector createNewLectorService(Lector lector);

    Lector updateLectorService(Lector lector);

    Integer deleteLectorByIdLectorService(Integer id);

}
