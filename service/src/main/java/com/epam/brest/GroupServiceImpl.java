package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Component
@ComponentScan("com.epam.brest.*")
@EntityScan("com.epam.brest")
@Service
public class GroupServiceImpl implements GroupServiceApi {

    private final Logger logger = LogManager.getLogger(GroupServiceImpl.class);

    @Autowired
    private DaoGroupApi daoGroup;

    @Autowired
    private DaoLectorApi daoLector;

    @Autowired
    private DaoRequestFromLectorApi daoRequestFromLector;


    @Override
    public List<String> getAllGroupNamesService() {
        logger.info("Get all groups by the nameGroup service");
        return (List<String>) daoGroup.getAllGroupsNames();
    }

    @Override
    public List<Group> getAllGroupsService() {
        List <Group> groups = (List<Group>) daoGroup.getAllGroups();
        logger.info("Get all groups service = " + groups.toString());
        return groups;
    }

    @Override
    public Integer deletegroupByIdService(Integer idGroup) {
        logger.info("Delete group by Id service");
        Group group = daoGroup.getGroupByid(idGroup);
        daoRequestFromLector.deleteFromAllLectorsRequestsWhenDeletedGroup(group.getGroupName());
        return (Integer) daoGroup.deleteGroupById(idGroup);
    }

    @Override
    public Group createNewGroupService(String newName) {
        logger.info("Create new group service");
        List<Lector> lectors = daoLector.getAllLectors();
        List<Integer> idLectors = new ArrayList<>();
        for (Lector lector : lectors){ idLectors.add(lector.getIdLector()); }
        daoRequestFromLector.createRequestsForLectorsWhenCreateNewGroup(newName, idLectors);
        return (Group) daoGroup.insertNewGroup(newName);
    }

    @Override
    public Group getGroupByGroupNameService(String name) {
        logger.info("Get group by the nameGroup service " + name);
        daoRequestFromLector.deleteFromAllLectorsRequestsWhenDeletedGroup(name);
        return (Group) daoGroup.getGroupByName(name);
    }

    @Override
    public Group updateGroupNameService(Group group) {
        logger.info("Update group service " + group);
        String oldName = daoGroup.getGroupByid(group.getIdGroup()).getGroupName();
        String newName = group.getGroupName();
        group = daoGroup.updateGroup(group);
        daoRequestFromLector.updateAllLectorsRequestsWhenChangedGroup(newName, oldName);
        return (Group) group;
    }

    @Override
    public Group getGroupById(Integer idGroup) {
        return (Group) daoGroup.getGroupByid(idGroup);
    }

}
