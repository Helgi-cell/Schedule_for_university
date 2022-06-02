package com.epam.brest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ScheduleDtoRestClientImpl implements ScheduleDtoServiceApi {

    private final Logger logger = LoggerFactory.getLogger(ScheduleDtoRestClientImpl.class);

    private RestTemplate restTemplate;

    public ScheduleDtoRestClientImpl (final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public Integer createScheduleService() {
        logger.debug("Create schedule ()");
        ResponseEntity responseEntity = restTemplate.getForEntity("/schedule/create", Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public List<List<LectorsSchedule>> getScheduleForAllLectorsService() {
        logger.debug("Get schedule for all lectors()");
        ResponseEntity responseEntity = restTemplate.getForEntity("/schedule/alllectors", List.class);
        return (List<List<LectorsSchedule>>) responseEntity.getBody();
    }

    @Override
    public List<LectorsSchedule> getScheduleForLectorService(Integer idlector) {
        logger.debug("Get schedule for the Lector by the name () " + idlector);
        ResponseEntity responseEntity = restTemplate.getForEntity(String.format("/schedule/lector/" + idlector, idlector),
                List.class);
        return (List<LectorsSchedule>) responseEntity.getBody();
    }

    @Override
    public List<List<StudentsSchedule>> getScheduleForAllGroupsService() {
        logger.debug("Get schedule for all groups()");
        ResponseEntity responseEntity = restTemplate.getForEntity("/schedule/allgroups", List.class);
        return (List<List<StudentsSchedule>>) responseEntity.getBody();
    }

    @Override
    public List<StudentsSchedule> getScheduleForGroupService(Integer idGroup) {
        logger.debug("Get schedule for the group by the name () " + idGroup);
        ResponseEntity responseEntity = restTemplate.getForEntity(String.format("/schedule/group/" + idGroup, idGroup),
                List.class);
        return (List<StudentsSchedule>) responseEntity.getBody();
    }

}
