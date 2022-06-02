package com.epam.brest.kafkaweb.controller;

import com.epam.brest.Group;
import com.epam.brest.kafkaweb.consumer.GroupConsumerServiceWeb;
import com.epam.brest.kafkaweb.producer.GroupProducerServiceWeb;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private final GroupProducerServiceWeb groupProducerService;
    @Autowired
    private final GroupConsumerServiceWeb groupConsumerService;


    public GroupController(GroupProducerServiceWeb groupProducerService
            , GroupConsumerServiceWeb groupConsumerService) {
        this.groupProducerService = groupProducerService;
        this.groupConsumerService = groupConsumerService;
    }

    @GetMapping(value = "/groups")
    public final String groups(Model model) throws InterruptedException {
        groupProducerService.sendGiveAllGroups("allgroups");
        while (groupConsumerService.isListGroupChanged == false) {
            Thread.sleep(1000);
        }
        logger.info("GroupController GROUPS = " + groupConsumerService.groups.toString());
        model.addAttribute("groups", groupConsumerService.groups);
        groupConsumerService.isListGroupChanged = false;
        logger.info("List Group in controller " + groupConsumerService.groups.toString());
        return "groups";
    }

    @GetMapping(value = "/group/new")
    public String newGroup(@ModelAttribute("group") Group group) throws SQLException {
        return "newgroup";
    }

    @PostMapping(value = "/newgroup")
    public String create(@ModelAttribute("group") @Valid Group group
                        , BindingResult result
                        , Model model) throws SQLException, JsonProcessingException, InterruptedException {
        if (result.hasErrors()) {
            return "newgroup";
        }
        groupProducerService.sendCreateNewGroup(group);
        while (groupConsumerService.isGroupCreated == false) {
            Thread.sleep(1000);
        }
        groupConsumerService.isGroupCreated = false;
        return "redirect:/groups";
    }

    @GetMapping(value = "/group/update/{id}")
    public String update(Model model, @PathVariable("id") int id) {
        logger.info("GroupController Update id group " + id);
        logger.info("GroupController Update groupsList " + groupConsumerService.groups);
        List<Group> groups = (List<Group>) groupConsumerService.groups;
        Group group = new Group();
        for (Group gr : groups) {
            if (gr.getIdGroup() == id) {
                group = gr;
            }
        }
        logger.info("GroupController Update group " + id);
        model.addAttribute("group", group);
        return "updategroup";
    }

    @PostMapping(value = "/updategroup")
    public String update(@ModelAttribute("group") @Valid Group group
                        , BindingResult result) throws JsonProcessingException, InterruptedException {
        logger.info("GroupController to Kafka " + group);
        if (result.hasErrors()) {
            return "updategroup";
        }
        groupProducerService.sendUpdateGroup(group);
        while (groupConsumerService.isGroupUpdated == false) {
            Thread.sleep(1000);
        }
        groupConsumerService.isGroupUpdated = false;
        return "redirect:/groups";
    }

    @GetMapping(value = "/group/delete/{id}")
    public String delete(@PathVariable("id") int id) throws InterruptedException {
        groupProducerService.sendDeleteGroup(String.valueOf(id));
        while (groupConsumerService.isGroupDeleted == false) {
            Thread.sleep(1000);
        }
        groupConsumerService.isGroupDeleted = false;
        return "redirect:/groups";
    }

}
