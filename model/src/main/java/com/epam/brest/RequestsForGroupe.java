package com.epam.brest;

import org.springframework.stereotype.Component;

@Component
public class RequestsForGroupe {

    private String groupe;
    private String lector;
    private String subject;
    private int pairs;

    public RequestsForGroupe() {
    }

    public RequestsForGroupe(String lector, String groupe, String subject, int pairs) {
        this.groupe = groupe;
        this.lector = lector;
        this.subject = subject;
        this.pairs = pairs;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getLector() {
        return lector;
    }

    public void setLector(String lector) {
        this.lector = lector;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getPairs() {
        return pairs;
    }

    public void setPairs(Integer pairs) {
        this.pairs = pairs;
    }

    @Override
    public String toString() {
        return (this.lector + " " + this.groupe + " " + this.subject + " " + this.pairs);
    }
}
