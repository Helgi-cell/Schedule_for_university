package com.epam.brest;

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

@Controller
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupServiceApi groupService;


    @GetMapping(value = "/groups")
    public final String groups (Model model) {
        model.addAttribute("groups", groupService.getAllGroupsService());
        return "groups";
    }

    @GetMapping(value = "/group/new")
    public String newGroup(@ModelAttribute("group") Group group) throws SQLException
    {
        return "newgroup";
    }

    @PostMapping(value = "/newgroup")
    public String create(@ModelAttribute("group") @Valid Group group,
                         BindingResult result, Model model) throws SQLException {

        if (result.hasErrors()) {
            return "newgroup";
        }
        group = groupService.createNewGroupService(group.getGroupName());
        return "redirect:/groups";
    }

    @GetMapping(value = "/group/update/{id}")
    public String update(Model model, @ModelAttribute("id") Integer id) {
        Group group = groupService.getGroupById(id);
        model.addAttribute("group", group);
        return "updategroup";
    }

    @PostMapping(value = "/updategroup")
    public String update(@ModelAttribute("group") @Valid Group group,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "editgroup";
        }
        group = groupService.updateGroupNameService(group);
        return "redirect:/groups";
    }

    @GetMapping(value = "/group/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        groupService.deletegroupByIdService(id);
        return "redirect:/groups";
    }

}
