package com.epam.brest;

import com.epam.brest.dto.Schedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@ComponentScan ("com.epam.brest.*")
public class DaoScheduleDtoImpl implements DaoScheduleDtoApi {

    private final Logger logger = LogManager.getLogger(DaoScheduleDtoImpl.class);

    @Autowired
    private DaoGroupApi daoGroup;

    @Autowired
    private DaoLectorApi daoLector;

    @Autowired
    private DaoRequestFromLectorApi daoRequest;

    @Autowired
    private Schedule schedule;

    public List<DaySchedule> scheduleForAll = new ArrayList<>();
    public List<LectorsSchedule> lectorSchedule = new ArrayList<>();
    public List<StudentsSchedule> studentSchedule = new ArrayList<>();

    @Override
    public void createSchedule() {
        logger.info("Create schedule from the Lectors requests {}");

        List<String> groups = daoGroup.getAllGroupsNames();
        List<Lector> lectors = daoLector.getAllLectors();
        List<String> lectorNames = schedule.getLectorsNamesList(daoLector.getAllLectors());

        List<RequestsForGroupe> requestsForGroupes = new ArrayList<>();
        for (Lector lector : lectors) {
            List<RequestFromLector> listRequest = daoRequest.getAllRequestsFromLectorByIdLector(lector.getIdLector());
            for (RequestFromLector request : listRequest) {
                requestsForGroupes.add(new RequestsForGroupe(lector.getNameLector(), request.getGroup(), request.getSubjectOfLector(),
                                                             Integer.parseInt(request.getNumberOfPairs())));
            }
        }

        scheduleForAll = schedule.createSchedule(groups, lectorNames, requestsForGroupes);
        studentSchedule = schedule.createScheduleForGroupe(groups);
        lectorSchedule = schedule.createScheduleForLectors(lectorNames);

       // return (List<DaySchedule>) scheduleForAll;
    }

    @Override
    public List<List<LectorsSchedule>> getScheduleForAllLectors() {
                logger.info("Get schedule to the all Lectors {}");
        List<List<LectorsSchedule>> lectorsScheduleAll = new ArrayList<>();
        List <Lector> lectors = daoLector.getAllLectors();
        List<String> lectorNames = new ArrayList<>();
        for (Lector lector : lectors){lectorNames.add(lector.getNameLector());}
        for (String lectorName : lectorNames){
            List<LectorsSchedule> lectorsScheduleList = lectorSchedule.stream()
                    .filter(lectorsSchedule1 -> lectorsSchedule1.getLector().equals(lectorName))
                    .collect(Collectors.toList());
            lectorsScheduleAll.add(lectorsScheduleList);
        }
                return (List<List<LectorsSchedule>>) lectorsScheduleAll;
    }

    @Override
    public List<LectorsSchedule> getScheduleForLector(Integer idLector) {
        logger.info("Get schedule for the Lector {}" + idLector);
        String lectorName = daoLector.getLectorById(idLector).getNameLector();
        return (List<LectorsSchedule>) lectorSchedule.stream()
                              .filter(lectorsSchedule -> lectorsSchedule.getLector().equals(lectorName))
                              .collect(Collectors.toList());
    }

    @Override
    public List<List<StudentsSchedule>> getScheduleForAllStudents() {
        logger.info("Get schedule to the all groups {}");
        List<List<StudentsSchedule>> studentsScheduleAll = new ArrayList<>();
        List <String> groups = daoGroup.getAllGroupsNames();
        for (String group : groups){
            List<StudentsSchedule> studentsScheduleList = studentSchedule.stream()
                    .filter(studentsSchedule1 -> studentsSchedule1.getGroupe().equals(group))
                    .collect(Collectors.toList());
            studentsScheduleAll.add(studentsScheduleList);
        }
        return (List<List<StudentsSchedule>>) studentsScheduleAll ;
    }

    @Override
    public List<StudentsSchedule> getScheduleForGroup(Integer idGroup) {
        logger.info("Get schedule for the group {}" + idGroup);
        String groupName = daoGroup.getGroupByid(idGroup).getGroupName();
        return (List<StudentsSchedule>) studentSchedule.stream()
                               .filter(studentsSchedule -> studentsSchedule.getGroupe().equals(groupName))
                               .collect(Collectors.toList());
    }
}
