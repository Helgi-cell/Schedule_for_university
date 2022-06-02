package com.epam.brest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ComponentScan("com.epam.brest.*")
public interface DaoScheduleDtoApi {

    public List<DaySchedule> createSchedule ();

    public List<List<LectorsSchedule>> getScheduleForAllLectors();

    public List<LectorsSchedule> getScheduleForLector(Integer idLector);

    public List<List<StudentsSchedule>> getScheduleForAllStudents();

    public List<StudentsSchedule> getScheduleForGroup(Integer idGroup);

}
