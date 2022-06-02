package com.epam.brest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//@Component
@Repository
public interface GroupJpaRepository extends JpaRepository <Group, Integer> {


    default List<String> getAllGroupsNames() {
        return (List<String>) findAll().stream()
                                       .flatMap(groups -> Stream.of(groups.getGroupName()))
                                       .collect(Collectors.toList());
    }


    default List <Group> getAllGroups(){
        List<Group> groups = new ArrayList<>();
                groups = (List<Group>) findAll();
         return groups;
      }



    default Integer deleteGroupByIdGroup(Integer idGroup){
        deleteById(idGroup);
        return (Integer) idGroup;
    }



    default Group insertNewGroup(String nameGroupe) {
          Group group = (Group) save(new Group(nameGroupe));
          return (Group) group;
    }


    default Group getGroupeByGroupName(String name) {
        List<Group> groups = (List<Group>) getAllGroups();
        int  index= (int) groups.stream()
                                .filter(gr -> name.equals(gr.getGroupName()))
                                .collect(Collectors.toList()).stream().count();
          if (index > 0) {
              return (Group) groups.get(index-1);
          } else {
              return (Group) new Group("IsEmpty");
          }
    }


    default Group updateGroupByGroupName(Group group) {
          return (Group) save(group);
    }


    default Group getGroupById (Integer idGroup){
        Optional groupOptional = findById(idGroup);
        Group group;
        if (groupOptional.isPresent()) {
            group = (Group) groupOptional.get();
        } else {
            group = new Group();
        }
        return (Group) group;
    }

}
