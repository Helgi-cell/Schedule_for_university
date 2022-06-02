package com.epam.brest;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ScheduleDtoServiceApi {

    public Integer createScheduleService ();

    public List<List<LectorsSchedule>> getScheduleForAllLectorsService();

    public List<LectorsSchedule> getScheduleForLectorService(Integer idLector);

    public List<List<StudentsSchedule>> getScheduleForAllGroupsService();

    public List<StudentsSchedule> getScheduleForGroupService(Integer idGroup);

}
