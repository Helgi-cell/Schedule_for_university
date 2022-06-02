package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@ComponentScan("com.epam.brest")
@Service
public class ScheduleDtoServiceImpl implements ScheduleDtoServiceApi {

    private final Logger logger = LogManager.getLogger(ScheduleDtoServiceImpl.class);

    @Autowired
    DaoScheduleDtoApi daoScheduleDto;

    @Override
    public Integer createScheduleService() {
        logger.info("Create schedule {}");
        return (Integer) daoScheduleDto.createSchedule().size();
    }

    @Override
    public List<List<LectorsSchedule>> getScheduleForAllLectorsService() {
        logger.info("Get schedule for all Lectors {}");
        return (List<List<LectorsSchedule>>) daoScheduleDto.getScheduleForAllLectors();
    }

    @Override
    public List<LectorsSchedule> getScheduleForLectorService(Integer idLector) {
        logger.info("Get schedule for Lector {} " + idLector);
        return (List<LectorsSchedule>) daoScheduleDto.getScheduleForLector(idLector);
    }

    @Override
    public List<List<StudentsSchedule>> getScheduleForAllGroupsService() {
        logger.info("Get schedule for all groups {}");
        return (List<List<StudentsSchedule>>) daoScheduleDto.getScheduleForAllStudents();
    }

    @Override
    public List<StudentsSchedule> getScheduleForGroupService(Integer idGroup) {
        logger.info("Get schedule for the group {} " + idGroup);
        return (List<StudentsSchedule>) daoScheduleDto.getScheduleForGroup(idGroup);
    }

}
