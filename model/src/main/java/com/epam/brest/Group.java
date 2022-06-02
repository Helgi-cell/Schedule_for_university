package com.epam.brest;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "groups")
@Component
public class Group {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "idG", unique=true, nullable=false)
    private int idGroup;

    /** field groupe - groupe request*/
    @Column(name = "group_name", nullable=false, length = 10)
    @NotEmpty(message = "Groupe should be not empty")
    @Size(min = 1, max = 10, message = "Size of groupe should not be 1-10 characters")
    private String groupName;


    public Group(int idG, String groupName) {
        this.idGroup = idG;
        this.groupName = groupName;
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group() {
        this.groupName = "    ";
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Groupe{" +
                "idG=" + idGroup +
                ", groupe='" + groupName + '\'' +
                '}';
    }
}
