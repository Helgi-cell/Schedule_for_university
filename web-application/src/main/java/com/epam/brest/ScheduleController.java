package com.epam.brest;

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
    private ScheduleDtoServiceApi scheduleDtoService;

    @Autowired
    private LectorServiceApi lectorService;

    @Autowired
    private GroupServiceApi groupService;




    @GetMapping(value = "/schedule")
    public final String groups (Model model) {
        List<List<StudentsSchedule>> studentsSchedule = scheduleDtoService.getScheduleForAllGroupsService();
        model.addAttribute("studentsSchedule", scheduleDtoService.getScheduleForAllGroupsService());
        return "allgroupsschedule";
    }

    @GetMapping(value = "/createschedule")
    public final String schedule () {
        scheduleDtoService.createScheduleService();
        return "redirect:/lectors";
    }

    @GetMapping(value = "/alllectorsschedule")
    public final String scheduleAllLectors (Model model) {
        List<List<LectorsSchedule>> lectorsSchedule =  scheduleDtoService.getScheduleForAllLectorsService();
        model.addAttribute("lectorsSchedule", lectorsSchedule);
        return "alllectorsschedule";
    }

    @GetMapping(value = "/lectorschedule/{id}")
    public final String scheduleLector (@PathVariable Integer id, Model model) {
        List<LectorsSchedule> lectorSchedule = scheduleDtoService.getScheduleForLectorService(id);
        model.addAttribute("lectorsSchedule", lectorSchedule);
        model.addAttribute("lector", lectorService.getLectorByIdLectorService(id));
        return "lectorschedule";
    }

    @GetMapping(value = "/studentschedule/{id}")
    public final String scheduleGroup (@PathVariable Integer id, Model model) {
        List<StudentsSchedule> studentSchedule = scheduleDtoService.getScheduleForGroupService(id);
        model.addAttribute("studentsSchedule", studentSchedule);
        model.addAttribute("group", groupService.getGroupById(id));
        return "studentschedule";
    }

}
