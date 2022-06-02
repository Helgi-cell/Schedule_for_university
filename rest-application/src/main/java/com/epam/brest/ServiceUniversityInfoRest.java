package com.epam.brest;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ServiceUniversityInfoRest {

    @GetMapping("/version")
    public String appversion() {
        return "version-0.0.1-SNAPSHOT";
    }

}
