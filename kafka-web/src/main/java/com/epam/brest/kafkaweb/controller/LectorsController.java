package com.epam.brest.kafkaweb.controller;

import com.epam.brest.Lector;
import com.epam.brest.kafkaweb.consumer.LectorConsumerServiceWeb;
import com.epam.brest.kafkaweb.producer.LectorProducerServiceWeb;
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
public class LectorsController {

    private static final Logger logger = LoggerFactory.getLogger(LectorsController.class);

    @Autowired
    private final LectorProducerServiceWeb lectorProducerService;
    @Autowired
    private final LectorConsumerServiceWeb lectorConsumerService;

    public LectorsController(LectorProducerServiceWeb lectorProducerService
                           , LectorConsumerServiceWeb lectorConsumerService) {
        this.lectorProducerService = lectorProducerService;
        this.lectorConsumerService = lectorConsumerService;
    }

    @GetMapping(value = "/lectors")
    public final String lectors (Model model) throws InterruptedException {
        lectorProducerService.sendGiveAllLectors("alllectors");
        while (lectorConsumerService.isListLectorChanged == false) {
            Thread.sleep(1000);
        }
        logger.info("LectorController Lectors = " + lectorConsumerService.lectors.toString());
        model.addAttribute("lectors", lectorConsumerService.lectors);
        lectorConsumerService.isListLectorChanged = false;
        logger.info("List Lector in controller " + lectorConsumerService.lectors.toString());
        return "lectors";
    }


    @GetMapping(value = "/lector/show/{id}")
    public String showLector(@PathVariable("id") int id, Model model) throws InterruptedException {
        lectorProducerService.sendGiveLectorById(id);
        while (lectorConsumerService.isLectorChanged == false) {
            Thread.sleep(1000);
        }
        logger.info("LectorController Lector = " + lectorConsumerService.lector.toString());
        model.addAttribute("lector", lectorConsumerService.lector);
        lectorConsumerService.isLectorChanged = false;
        logger.info("Lector in controller " + lectorConsumerService.lector.toString());

        return "showlector";
    }


    @GetMapping(value = "/lector/new")
    public String newUser(@ModelAttribute("lector") Lector lector) throws SQLException
    {
        return "newlector";
    }

    @PostMapping(value = "/newlector")
    public String create(@ModelAttribute("lector") @Valid Lector lector,
                         BindingResult result, Model model) throws SQLException, InterruptedException {

        if (result.hasErrors()) {
            return "newlector";
        }
        lectorProducerService.sendNewLector(lector);
        while (lectorConsumerService.isLectorCreated == false) {
            Thread.sleep(1000);
        }
        lectorConsumerService.isLectorCreated = false;
        return "redirect:/lectors";
    }



    @GetMapping(value = "/lector/update/{id}")
    public String update(Model model, @PathVariable("id") int id) throws InterruptedException {
        lectorProducerService.sendGiveLectorById(id);
        while (lectorConsumerService.isLectorChanged == false) {
            Thread.sleep(1000);
        }
        lectorConsumerService.isLectorChanged = false;
        model.addAttribute("lector", lectorConsumerService.lector);
        return "editlector";
    }

    @PostMapping(value = "/updatelector")
    public String update(@ModelAttribute("lector") @Valid Lector lector,
                         BindingResult result) throws InterruptedException {

        if (result.hasErrors()) {
            return "editlector";
        }
        lectorProducerService.sendUpdateLector(lector);
        while (lectorConsumerService.isLectorUpdated == false) {
            Thread.sleep(1000);
        }
        lectorConsumerService.isLectorUpdated = false;
        return "redirect:/lectors";
    }


    @GetMapping(value = "/lector/{id}/delete")
    public String delete(@PathVariable("id") int id) throws InterruptedException {
        lectorProducerService.sendDeleteLectorById(id);
        while (lectorConsumerService.isLectorDeleted == false) {
            Thread.sleep(1000);
        }
        lectorConsumerService.isLectorDeleted = false;
        return "redirect:/lectors";
    }
}
