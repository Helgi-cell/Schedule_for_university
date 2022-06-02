package com.epam.brest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ComponentScan("com.epam.brest")
//@EnableJpaRepositories(basePackages = "com.epam.brest")
public class DaoGroupImpl implements DaoGroupApi {

    private final Logger logger = LogManager.getLogger(DaoGroupImpl.class);

    @Autowired
    private  GroupJpaRepository groupRepository;


    @Override
    public List<String> getAllGroupsNames() {
        logger.info("GET ALL GROUP NAMES {}");
        return (List<String>) groupRepository.getAllGroupsNames();
    }

    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = (List<Group>) groupRepository.getAllGroups();
        logger.info("GET ALL GROUPS  {} " + groups.toString());
        return groups;
    }

    @Override
    public Integer deleteGroupById(Integer idGroup) {
        logger.info("DELETE GROUP BY IdGroup {}");
        return (Integer) groupRepository.deleteGroupByIdGroup(idGroup);
    }

    @Override
    public Group insertNewGroup(String newNameOfGroup) {
        logger.info("CREATE NEW GROUP {}");
        return (Group) groupRepository.insertNewGroup(newNameOfGroup);
    }

    @Override
    public Group getGroupByName(String nameGroup) {
        logger.info("GET GROUP BY the NAME {}");
        return (Group) groupRepository.getGroupeByGroupName(nameGroup);
    }

    @Override
    public Group getGroupByid(Integer idGroup) {
        return (Group) groupRepository.getGroupById(idGroup);
    }

    @Override
    public Group updateGroup(Group group) {
        logger.info("UPDATE GROUPE.NAME BY NEW NAME {}");
        return (Group) groupRepository.updateGroupByGroupName(group);
    }
}
