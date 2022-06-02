package com.epam.brest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
public class LectorsController {

    private static final Logger logger = LoggerFactory.getLogger(LectorsController.class);

    @Autowired
    private LectorServiceApi lectorService;

    @GetMapping(value = "/lectors")
    public final String lectors (Model model) {
        model.addAttribute("lectors", lectorService.getAllLectorsService());
        return "lectors";
    }

    @GetMapping(value = "/lector/show/{id}")
    public String showLector(@PathVariable("id") int id, Model model) {
        model.addAttribute("lector", lectorService.getLectorByIdLectorService(id));
        return "showlector";
    }

    @GetMapping(value = "/lector/new")
    public String newUser(@ModelAttribute("lector") Lector lector) throws SQLException
    {
        return "newlector";
    }

    @PostMapping(value = "/newlector")
    public String create(@ModelAttribute("lector") @Valid Lector lector,
                         BindingResult result, Model model) throws SQLException {

        if (result.hasErrors()) {
            return "newlector";
        }
        lector = lectorService.createNewLectorService(lector);
        model.addAttribute("lector", lector);
        return "redirect:/lectors";
    }

    @GetMapping(value = "/lector/update/{id}")
    public String update(Model model, @PathVariable("id") int id) {
        model.addAttribute("lector", lectorService.getLectorByIdLectorService(id));
        return "editlector";
    }

    @PostMapping(value = "/updatelector")
    public String update(@ModelAttribute("lector") @Valid Lector lector,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "editlector";
        }

        lector = lectorService.updateLectorService(lector);
        return "redirect:/lectors";
    }

    @GetMapping(value = "/lector/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        lectorService.deleteLectorByIdLectorService(id);
        return "redirect:/lectors";
    }

}
