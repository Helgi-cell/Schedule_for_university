package com.epam.brest;

import com.epam.brest.dto.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootApplication
@SpringBootTest(classes= { DaoLectorImpl.class, DaoRequestFromLectorImpl.class,
                           DaoGroupImpl.class, DaoScheduleDtoImpl.class, Schedule.class})
@ComponentScan("com.epam.brest.*")
@EntityScan("com.epam.brest")
@Transactional()
public class DaoScheduleDtoTestIT {

    @Autowired
    DaoScheduleDtoApi daoScheduleDto;

    @Autowired
    DaoRequestFromLectorApi daoRequest;

    @Autowired
    DaoLectorApi daoLector;

    @Autowired
    Schedule schedule;
    @Autowired
    DaoGroupApi daoGroup;
/*

    @Autowired
    Schedule scheduleTest;
*/

    @BeforeEach
    public void setUp() {
        String[] groups = new String[]{"e1", "e2", "e3", "e4", "e5", "e6"};
        Arrays.stream(groups)
              .map(gr -> daoGroup.insertNewGroup(gr))
              .collect(Collectors.toList());

        Lector lector = daoLector.saveOrUpdateLector(new Lector("Kim", "kim", "1111", "kim@kim.com"));
        daoRequest.createEmptyRequestsForNewLector(lector.getIdLector(), Arrays.stream(groups).collect(Collectors.toList()));
        List<RequestFromLector> requestsFromLector = daoRequest.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        daoRequest.updateAllRequestsForLector(
                                               requestsFromLector.stream()
                                              .peek(requestFromLector -> {requestFromLector.setNumberOfPairs("2");
                                                                          requestFromLector.setSubjectOfLector("math");})
                                              .collect(Collectors.toList()));


        lector = daoLector.saveOrUpdateLector(new Lector("Tom", "tom", "1111", "tom@tom.com"));
        daoRequest.createEmptyRequestsForNewLector(lector.getIdLector(), Arrays.stream(groups).collect(Collectors.toList()));
        requestsFromLector = daoRequest.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        daoRequest.updateAllRequestsForLector(
                requestsFromLector.stream()
                        .peek(requestFromLector -> {requestFromLector.setNumberOfPairs("2");
                            requestFromLector.setSubjectOfLector("chemic");})
                        .collect(Collectors.toList()));

        lector = daoLector.saveOrUpdateLector(new Lector("Mike", "mike", "1111", "mike@mike.com"));
        daoRequest.createEmptyRequestsForNewLector(lector.getIdLector(), Arrays.stream(groups).collect(Collectors.toList()));
        requestsFromLector = daoRequest.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        daoRequest.updateAllRequestsForLector(
                requestsFromLector.stream()
                        .peek(requestFromLector -> {requestFromLector.setNumberOfPairs("2");
                            requestFromLector.setSubjectOfLector("fizo");})
                        .collect(Collectors.toList()));

        lector = daoLector.saveOrUpdateLector(new Lector("John", "john", "1111", "john@john.com"));
        daoRequest.createEmptyRequestsForNewLector(lector.getIdLector(), Arrays.stream(groups).collect(Collectors.toList()));
        requestsFromLector = daoRequest.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        daoRequest.updateAllRequestsForLector(
                requestsFromLector.stream()
                        .peek(requestFromLector -> {requestFromLector.setNumberOfPairs("2");
                            requestFromLector.setSubjectOfLector("electronic");})
                        .collect(Collectors.toList()));

        lector = daoLector.saveOrUpdateLector(new Lector("Nick", "nick", "1111", "nick@aa.com"));
        daoRequest.createEmptyRequestsForNewLector(lector.getIdLector(), Arrays.stream(groups).collect(Collectors.toList()));
        requestsFromLector = daoRequest.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        daoRequest.updateAllRequestsForLector(
                requestsFromLector.stream()
                        .peek(requestFromLector -> {requestFromLector.setNumberOfPairs("2");
                            requestFromLector.setSubjectOfLector("programming");})
                        .collect(Collectors.toList()));

        lector = daoLector.saveOrUpdateLector(new Lector("Denny", "denny", "1111", "denny@aa.com"));
        daoRequest.createEmptyRequestsForNewLector(lector.getIdLector(), Arrays.stream(groups).collect(Collectors.toList()));
        requestsFromLector = daoRequest.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        daoRequest.updateAllRequestsForLector(
                requestsFromLector.stream()
                        .peek(requestFromLector -> {requestFromLector.setNumberOfPairs("2");
                            requestFromLector.setSubjectOfLector("phylosofy");})
                        .collect(Collectors.toList()));

        lector = daoLector.saveOrUpdateLector(new Lector("Kirk", "kirk", "1111", "kirk@aa.com"));
        daoRequest.createEmptyRequestsForNewLector(lector.getIdLector(), Arrays.stream(groups).collect(Collectors.toList()));
        requestsFromLector = daoRequest.getAllRequestsFromLectorByIdLector(lector.getIdLector());
        daoRequest.updateAllRequestsForLector(
                requestsFromLector.stream()
                        .peek(requestFromLector -> {requestFromLector.setNumberOfPairs("2");
                            requestFromLector.setSubjectOfLector("mechanic");})
                        .collect(Collectors.toList()));
    }


    @Test
    public void isSchedule(){

        daoScheduleDto.createSchedule();
        assertTrue(schedule.dayScheduleForAll.size() == 84);
        Integer idLector = daoLector.getLectorByName("Tom").getIdLector();
        Integer idGroup = daoGroup.getGroupByName("e1").getIdGroup();
        assertTrue(daoScheduleDto.getScheduleForLector(idLector).size() == 5);
        assertTrue(daoScheduleDto.getScheduleForGroup(idGroup).size() == 5);
    }

}
