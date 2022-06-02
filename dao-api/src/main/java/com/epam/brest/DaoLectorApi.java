package com.epam.brest;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EntityScan("com.epam.brest")
@ComponentScan("com.epam.brest.*")
public interface DaoLectorApi {

    public List<Lector> getAllLectors();

    public Lector getLectorByName(String name);

    public Lector getLectorByEmail(String email);

    public Lector getLectorById(Integer id);

    public Lector saveOrUpdateLector(Lector user);

    public void deleteLectorById(Integer id);

}
