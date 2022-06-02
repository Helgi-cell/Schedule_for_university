package com.epam.brest;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GroupServiceApi {

    List<String> getAllGroupNamesService();

    List<Group> getAllGroupsService();

    Integer deletegroupByIdService(Integer idGroup);

    Group createNewGroupService(String newName);

    Group getGroupByGroupNameService(String name);

    Group updateGroupNameService(Group group);

    Group getGroupById (Integer idGroup);

}
