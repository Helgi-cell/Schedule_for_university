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

@Controller
public class RequestsController {
    private static final Logger logger = LoggerFactory.getLogger(RequestsController.class);

    @Autowired
    private RequestFromLectorServiceApi requestFromLectorService;

    @Autowired
    private LectorServiceApi lectorService;


    @GetMapping(value = "/lector/requests/{id}")
    public final String lectorsRequests (@PathVariable Integer id, Model model) {
        model.addAttribute("lector", lectorService.getLectorByIdLectorService(id));
        model.addAttribute("requests",
                            requestFromLectorService.getAllRequestsFromLectorService(id));
        return "lectorsrequest";
    }

    @GetMapping(value = "/lector/request/{id}/delete")
    public String deleteRequest(@PathVariable("id") int id) {
        RequestFromLector requestFromLector = requestFromLectorService.getRequestOfLectorByIdRequestService(id);
        requestFromLectorService.flushRequestFromLectorService(requestFromLector);
        return "redirect:/lector/requests/" + requestFromLector.getIdLector();
    }

    @GetMapping(value = "/lector/request/update/{id}")
    public String update(Model model, @PathVariable("id") int id) {
        model.addAttribute("request", requestFromLectorService.getRequestOfLectorByIdRequestService(id));
        return "editrequest";
    }

    @PostMapping(value = "/updateRequest")
    public String update(@ModelAttribute("request") @Valid RequestFromLector requestFromLector,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "editrequest";
        }

        requestFromLector = requestFromLectorService.updateRequestFromLectorService(requestFromLector);
        return "redirect:/lector/requests/" + requestFromLector.getIdLector();
    }

}
