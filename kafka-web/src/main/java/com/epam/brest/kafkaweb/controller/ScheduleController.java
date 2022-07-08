package com.epam.brest.kafkaweb.controller;

import com.epam.brest.Group;
import com.epam.brest.LectorsSchedule;
import com.epam.brest.StudentsSchedule;
import com.epam.brest.kafkaweb.consumer.GroupConsumerServiceWeb;
import com.epam.brest.kafkaweb.consumer.LectorConsumerServiceWeb;
import com.epam.brest.kafkaweb.consumer.ScheduleConsumerServiceWeb;
import com.epam.brest.kafkaweb.producer.GroupProducerServiceWeb;
import com.epam.brest.kafkaweb.producer.LectorProducerServiceWeb;
import com.epam.brest.kafkaweb.producer.ScheduleProducerServiceWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private final ScheduleConsumerServiceWeb scheduleConsumerService;
    @Autowired
    private final ScheduleProducerServiceWeb scheduleProducerService;

    @Autowired
    private final LectorConsumerServiceWeb lectorConsumerService;

    @Autowired
    private final LectorProducerServiceWeb lectorProducerService;

    @Autowired
    private final GroupConsumerServiceWeb groupConsumerService;

    @Autowired
    private final GroupProducerServiceWeb groupProducerService;

    public ScheduleController(ScheduleConsumerServiceWeb scheduleConsumerService
            , ScheduleProducerServiceWeb scheduleProducerService
            , LectorConsumerServiceWeb lectorConsumerService
            , LectorProducerServiceWeb lectorProducerService
            , GroupConsumerServiceWeb groupConsumerService
            , GroupProducerServiceWeb groupProducerService) {

        this.scheduleConsumerService = scheduleConsumerService;
        this.scheduleProducerService = scheduleProducerService;
        this.lectorConsumerService = lectorConsumerService;
        this.lectorProducerService = lectorProducerService;
        this.groupConsumerService = groupConsumerService;
        this.groupProducerService = groupProducerService;
    }


    @GetMapping(value = "/schedule")
    public final String getScheduleForAllGroups (Model model) throws InterruptedException {
        scheduleProducerService.sendGiveScheduleForAllStudents("all groups schedule");
        while (scheduleConsumerService.isAllStudentsScheduleChanged == false) {
            Thread.sleep(500);
        }
        scheduleConsumerService.isAllStudentsScheduleChanged = false;
        List<List<StudentsSchedule>> studentsSchedule = scheduleConsumerService.allGroupSchedule;
        model.addAttribute("studentsSchedule", studentsSchedule);
        return "allgroupsschedule";
    }

    @GetMapping(value = "/createschedule")
    public final String schedule () {
        scheduleProducerService.sendCreateSchedule("create new schedule");
        return "redirect:/lectors";
    }

    @GetMapping(value = "/alllectorsschedule")
    public final String scheduleAllLectors (Model model) throws InterruptedException {
        scheduleProducerService.sendGiveScheduleForAllLectors("schedule all lectors");
        while (scheduleConsumerService.isAllLectorsScheduleChanged == false) {
            Thread.sleep(500);
        }
        scheduleConsumerService.isAllLectorsScheduleChanged = false;
        List<List<LectorsSchedule>> lectorsSchedule =  scheduleConsumerService.allLectorsSchedule;
        model.addAttribute("lectorsSchedule", lectorsSchedule);
        return "alllectorsschedule";
    }

    @GetMapping(value = "/lectorschedule/{id}")
    public final String scheduleLector (@PathVariable Integer id, Model model) throws InterruptedException {
        scheduleProducerService.sendGiveScheduleForLector(id);
        while (scheduleConsumerService.isListLectorScheduleChanged == false) {
            Thread.sleep(500);
        }
        scheduleConsumerService.isListLectorScheduleChanged = false;
        List<LectorsSchedule> lectorsSchedule = scheduleConsumerService.lectorSchedule;
        model.addAttribute("lectorsSchedule", lectorsSchedule);
        lectorProducerService.sendGiveLectorById(id);
        while (lectorConsumerService.isLectorChanged == false) {
            Thread.sleep(500);
        }
        lectorConsumerService.isLectorChanged = false;
        model.addAttribute("lector", lectorConsumerService.lector);
        return "lectorschedule";
    }

    @GetMapping(value = "/studentschedule/{id}")
    public final String scheduleGroup (@PathVariable Integer id, Model model) throws InterruptedException {
        scheduleProducerService.sendGiveScheduleForGroup(id);
        while (scheduleConsumerService.isListStudentScheduleChanged == false) {
            Thread.sleep(500);
        }
        scheduleConsumerService.isListStudentScheduleChanged = false;
        List<StudentsSchedule> studentSchedule = scheduleConsumerService.groupSchedule;
        model.addAttribute("studentsSchedule", studentSchedule);

        groupProducerService.sendGiveAllGroups("give all groups");
        while (groupConsumerService.isListGroupChanged == false) {
            Thread.sleep(500);
        }
        groupConsumerService.isListGroupChanged = false;
        Group group = groupConsumerService.groups.stream()
                                                 .filter(gr -> gr.getIdGroup()==id)
                                                 .findAny()
                                                 .get();
        model.addAttribute("group", group);
        return "studentschedule";
    }

}
