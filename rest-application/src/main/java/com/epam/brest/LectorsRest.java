package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LectorsRest {

    private final Logger logger = LogManager.getLogger(LectorsRest.class);

    @Autowired
    private LectorServiceApi lectorService;

    @GetMapping("/lectors/get-all")
    @Transactional(readOnly = true)
    public List<Lector> getAlllectors() {
        logger.debug("getAllLectors({})");
        return (List<Lector>) lectorService.getAllLectorsService();
    }

    @GetMapping("/lectors/lector/get-name")
    @Transactional(readOnly = true)
    public Lector getLectorByName(@RequestParam String name) {
        logger.debug("getLectorByName({})", name);
        return (Lector) lectorService.getLectorByLectorsNameService(name);
    }

    @GetMapping("/lectors/lector/get-email")
    @Transactional(readOnly = true)
    public Lector getLectorByEmail(@RequestParam String email) {
        logger.debug("getLectorByEmail({})", email);
        return (Lector) lectorService.getLectorByEmailService(email);
    }

    @GetMapping("/lectors/lector/{id}")
    @Transactional(readOnly = true)
    public Lector getLectorById(@PathVariable Integer id) {
        logger.debug("getLectorById({})", id);
        return (Lector) lectorService.getLectorByIdLectorService(id);
    }

    @PostMapping(path = "/lectors/lector/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Lector> createNewLector(@RequestBody Lector lector) {
        logger.debug("createLector({})", lector);
        lector = lectorService.createNewLectorService(lector);
        return new ResponseEntity<Lector>(lector, HttpStatus.OK);
    }

    @PutMapping(path = "/lectors/lector/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Lector> updateLector(@RequestBody Lector lector) {
        logger.debug("updateLector({})", lector);
        lector = lectorService.updateLectorService(lector);
        return new ResponseEntity<Lector>(lector, HttpStatus.OK);
    }

    @DeleteMapping(value = "/lectors/lector/{id}/delete", produces = {"application/json"})
    public ResponseEntity <Integer> deleteLectorById(@PathVariable Integer id) {
        logger.debug("deleteLector by id({})", id);
        lectorService.deleteLectorByIdLectorService(id);
        return new ResponseEntity(id, HttpStatus.OK);
    }

}
