package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ScheduleRest {

    private final Logger logger = LogManager.getLogger(ScheduleRest.class);


    @Autowired
    ScheduleDtoServiceApi scheduleDtoService;

    @GetMapping("/schedule/create")
    @Transactional(readOnly = true)
    public Integer createSchedule() {
        logger.debug("Create schedule({})");
        return (Integer) scheduleDtoService.createScheduleService();
    }

    @GetMapping("/schedule/alllectors")
    @Transactional(readOnly = true)
    public List<List<LectorsSchedule>> getScheduleForAllLectors() {
        logger.debug("Get schedule for all lectors({})");
        return (List<List<LectorsSchedule>>) scheduleDtoService.getScheduleForAllLectorsService();
    }

    @GetMapping("/schedule/allgroups")
    @Transactional(readOnly = true)
    public List<List<StudentsSchedule>> getScheduleForAllGroups() {
        logger.debug("Get schedule for all groups({})");
        return (List<List<StudentsSchedule>>) scheduleDtoService.getScheduleForAllGroupsService();
    }

    @GetMapping("/schedule/lector/{id}")
    @Transactional(readOnly = true)
    public List<LectorsSchedule> getScheduleForLector(@PathVariable Integer id) {
        logger.debug("Get schedule for lector({})" + id);
        return (List<LectorsSchedule>) scheduleDtoService.getScheduleForLectorService(id);
    }

    @GetMapping("/schedule/group/{id}")
    @Transactional(readOnly = true)
    public List<StudentsSchedule> getScheduleForGroup(@PathVariable Integer id) {
        logger.debug("Get schedule for group({})" + id);
        return (List<StudentsSchedule>) scheduleDtoService.getScheduleForGroupService(id);
    }

}
