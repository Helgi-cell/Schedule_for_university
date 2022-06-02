package com.epam.brest;

import org.springframework.stereotype.Component;

/**
 * Entity class for  the schedule results
 */
@Component
public class DaySchedule {
    /**
     * field idS - identificator schedule entity in database
     */
    private int idS;

    /**
     * field day - day is in the schedule request
     */
    private int day;

    /**
     * field lectorName - Lectors name is in the schedule request
     */
    private String lectorName;

    /**
     * field groupeName - Name of the groupe is in the schedule request
     */
    private String groupeName;

    /**
     * field subject - The subject is in the schedule request
     */
    private String subject;

    /**
     * field numberPair - The pair  is in the schedule request
     */
    private int numberPair;

    /**
     * Constructor - create new object
     * @see DaySchedule#DaySchedule(String, String ,int, String, int)
     * @see DaySchedule#DaySchedule(int, String, String ,int, String, int)
     */
    public DaySchedule() {}

    /**
     * Constructor - create new object
     * @see DaySchedule#DaySchedule(int, String, String ,int, String, int)
     * @see DaySchedule#DaySchedule()
     *   @param lectorName - lectors name
     *   @param groupeName - groupes name
     *   @param numberPair - pair in the day
     *   @param subject - subject
     *   @param day - The day when will be a pair
     */
    public DaySchedule(String lectorName, String groupeName, int numberPair, String subject, int day) {
        this.day = day;
        this.lectorName = lectorName;
        this.groupeName = groupeName;
        this.subject = subject;
        this.numberPair = numberPair;
    }

    /**
     * Constructor - create new object
     * @see DaySchedule#DaySchedule(String, String ,int, String, int)
     * @see DaySchedule#DaySchedule()
     *   @param idS - identificator of the schedule request in the database
     *   @param lectorName - lectors name
     *   @param groupeName - groupes name
     *   @param numberPair - pair in the day
     *   @param subject - subject
     *   @param day - The day when will be a pair
     */
    public DaySchedule(int idS, String lectorName, String groupeName, int numberPair, String subject, int day) {
        this.idS = idS;
        this.day = day;
        this.lectorName = lectorName;
        this.groupeName = groupeName;
        this.subject = subject;
        this.numberPair = numberPair;
    }

    public Integer getIdS() {
        return idS;
    }

    public void setIdS(Integer idS) {
        this.idS = idS;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getLectorName() {
        return lectorName;
    }

    public void setLectorName(String lectorName) {
        this.lectorName = lectorName;
    }

    public String getGroupeName() {
        return groupeName;
    }

    public void setGroupeName(String groupeName) {
        this.groupeName = groupeName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getNumberPair() {
        return numberPair;
    }

    public void setNumberPair(Integer numberPair) {
        this.numberPair = numberPair;
    }

    @Override
    public String toString() {
        return "DaySchedule{" +
                "idS=" + idS +
                ", day=" + day +
                ", lectorName='" + lectorName + '\'' +
                ", groupeName='" + groupeName + '\'' +
                ", subject='" + subject + '\'' +
                ", numberPair=" + numberPair +
                '}';
    }
}
