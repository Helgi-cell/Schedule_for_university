package com.epam.brest.kafkaweb.controller;

import com.epam.brest.RequestFromLector;
import com.epam.brest.kafkaweb.consumer.LectorConsumerServiceWeb;
import com.epam.brest.kafkaweb.consumer.RequestFromLectorConsumerServiceWeb;
import com.epam.brest.kafkaweb.producer.LectorProducerServiceWeb;
import com.epam.brest.kafkaweb.producer.RequestFromLectorProducerServiceWeb;
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

@Controller
public class RequestsController {

    private static final Logger logger = LoggerFactory.getLogger(RequestsController.class);

    @Autowired
    private final RequestFromLectorProducerServiceWeb requestProducerService;
    @Autowired
    private final RequestFromLectorConsumerServiceWeb requestConsumerService;

    @Autowired
    private final LectorProducerServiceWeb lectorProducerService;
    @Autowired
    private final LectorConsumerServiceWeb lectorConsumerService;

    public RequestsController(RequestFromLectorProducerServiceWeb requestProducerService
            , RequestFromLectorConsumerServiceWeb requestConsumerService
            , LectorProducerServiceWeb lectorProducerService
            , LectorConsumerServiceWeb lectorConsumerService) {

        this.requestProducerService = requestProducerService;
        this.requestConsumerService = requestConsumerService;
        this.lectorProducerService = lectorProducerService;
        this.lectorConsumerService = lectorConsumerService;
    }

    @GetMapping(value = "/lector/requests/{id}")
    public final String lectorsRequests (@PathVariable Integer id, Model model) throws InterruptedException {

        lectorProducerService.sendGiveLectorById(id);
        while (lectorConsumerService.isLectorChanged == false) {
            Thread.sleep(1000);
        }
        lectorConsumerService.isLectorChanged = false;
        model.addAttribute("lector", lectorConsumerService.lector);

        requestProducerService.sendGiveAllRequestsFromLector(lectorConsumerService.lector.getIdLector());
        while (requestConsumerService.isListRequestsFromLectorChanged == false) {
            Thread.sleep(1000);
        }
        requestConsumerService.isListRequestsFromLectorChanged = false;
        model.addAttribute("requests",
                requestConsumerService.listRequestFromLector);
        return "lectorsrequest";
    }

    @GetMapping(value = "/lector/request/{id}/delete")
    public String deleteRequest(@PathVariable("id") int id) throws InterruptedException {
        requestProducerService.sendGiveRequestFromLectorByIdRequest(id);
        while (requestConsumerService.isRequestFromLectorChanged == false) {
            Thread.sleep(1000);
        }
        requestConsumerService.isRequestFromLectorChanged = false;
        requestProducerService.sendDeleteRequestFromLector(requestConsumerService.requestFromLector);
        while (requestConsumerService.isRequestFromLectorDeleted == false) {
            Thread.sleep(1000);
        }
        requestConsumerService.isRequestFromLectorDeleted = false;
        return "redirect:/lector/requests/" + requestConsumerService.requestFromLector.getIdLector();
    }


    @GetMapping(value = "/lector/request/update/{id}")
    public String update(Model model, @PathVariable("id") int id) throws InterruptedException {
        requestProducerService.sendGiveRequestFromLectorByIdRequest(id);
        while (requestConsumerService.isRequestFromLectorChanged == false) {
            Thread.sleep(1000);
        }
        requestConsumerService.isRequestFromLectorChanged = false;

        model.addAttribute("request", requestConsumerService.requestFromLector);
        return "editrequest";
    }

    @PostMapping(value = "/updateRequest")
    public String update(@ModelAttribute("request") @Valid RequestFromLector requestFromLector,
                         BindingResult result) throws InterruptedException {

        logger.info("request update -> " + requestFromLector);

        if (result.hasErrors()) {
            return "editrequest";
        }

        requestProducerService.sendUpdateRequestFromLectorByIdRequest(requestFromLector);
        while (requestConsumerService.isRequestFromLectorUpdated == false) {
            Thread.sleep(1000);
        }
        requestConsumerService.isRequestFromLectorUpdated = false;
        return "redirect:/lector/requests/" + requestConsumerService.requestFromLector.getIdLector();
    }


}
