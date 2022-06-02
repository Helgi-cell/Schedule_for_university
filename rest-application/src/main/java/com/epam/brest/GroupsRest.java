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
public class GroupsRest {

    private final Logger logger = LogManager.getLogger(GroupsRest.class);

    @Autowired
    private GroupServiceApi groupService;


    @GetMapping("/groups/get-all")
    @Transactional(readOnly = true)
    public List<Group> allGroups() {
        logger.debug("get all groups({})");
        return (List<Group>) groupService.getAllGroupsService();
    }

    @GetMapping("/groups/get-all-names")
    @Transactional(readOnly = true)
    public List<String> allGroupsByNames() {
        logger.debug("get ll groups names({})");
        return (List<String>) groupService.getAllGroupNamesService();
    }

    @GetMapping("/groups/group/get-name")
    @Transactional(readOnly = true)
    public Group getGroupByName(@RequestParam String name) {
        logger.debug("get group by name group({})");
        return (Group) groupService.getGroupByGroupNameService(name);
    }

    @GetMapping("/groups/group/{id}")
    @Transactional(readOnly = true)
    public Group getGroupById(@PathVariable Integer id) {
        logger.debug("get group by ID group({})");
        return (Group) groupService.getGroupById(id);
    }

    @PostMapping(path = "/groups/group/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Group> createGroup(@RequestBody String newName) {
        logger.debug("create new group({}) name = ", newName);
        Group groupe = groupService.createNewGroupService(newName);
        return new ResponseEntity<>(groupe, HttpStatus.OK);
    }

    @PutMapping(path = "/groups/group/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Group> updateGroup(@RequestBody Group group) {
        logger.debug("update group({})", group);
        group = groupService.updateGroupNameService(group);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @DeleteMapping(value = "/groups/group/delete/{id}", produces = {"application/json"})
    public ResponseEntity <Integer> deleteGroup(@PathVariable Integer id) {
        logger.debug("delete group by ID group = ", id);
        id = groupService.deletegroupByIdService(id);
        return new ResponseEntity(id, HttpStatus.OK);
    }

}
