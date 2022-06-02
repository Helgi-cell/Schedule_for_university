/**
 * Request class . Fields <b>idR</b> <b>id</b> <b>groupe</b> <b>pairs</b> <b>subject</b> <b></>date</b>
 *
 * @autor Oleg Suhodolsky
 * @version 1.1
 */
package com.epam.brest;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "requests_from_lector")
@Component
public class RequestFromLector implements Serializable {

    /** field idR - request identificator in database*/

    @Column(name = "idRequest", unique=true, nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idRequest;

    /** field groupe - groupe request*/
    @Column(name = "groupe_name", nullable = false, length = 10)
    @NotEmpty(message = "Groupe should be not empty")
    @Size(min = 1, max = 10, message = "Size of groupe should not be 1-10 characters")
    private String group;

    /** field pairs - pairs request*/
    @Column(name = "number_pairs", nullable = false, length = 2)
    @NotEmpty(message = "Pairs should be not empty")
    @Size(min = 1, max = 2, message = "Size of pairs should not be 1-2 characters")
    private String numberOfPairs;

    /** field subject - subject request*/
    @Column(name = "subject_of_lector", nullable = false, length = 100)
    @NotEmpty(message = "Subject should be not empty")
    @Size(min = 3, max = 100, message = "Size of subject should not be 3-100 characters")
    private String subjectOfLector;

    /** field date - date when request was create or update*/
    @Column(name="date")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date date;

    @Column(name = "idLector",  nullable=false)
    private int idLector;

    /**
     * Constructor - create new object
     * @see RequestFromLector#RequestFromLector()
     * @see RequestFromLector#RequestFromLector(int, String, String, String, Date)
     * @see RequestFromLector#RequestFromLector(int, String, String, String)
     * @param idRequest - identificator
     * @param idLector- identificator of Lector -> one to many
     * @param group - groupe name
     * @param numberOfPairs - number pairs
     * @param subjectOfLector - subject of user
     * @param dateToChangeRequest - date of request
     */
    public RequestFromLector(int idRequest, int idLector, String group,
                             String numberOfPairs, String subjectOfLector, Date dateToChangeRequest) {
        this.idRequest = idRequest;
        this.idLector = idLector;
        this.group = group;
        this.numberOfPairs = numberOfPairs;
        this.subjectOfLector = subjectOfLector;
        this.date = dateToChangeRequest;
    }

    /**
     * Constructor - create new object
     * @see RequestFromLector#RequestFromLector()
     * @see RequestFromLector#RequestFromLector(int, String, String, String, Date)
     * @see RequestFromLector#RequestFromLector(int,int, String, String, String, Date)
     * @param idLector - identificator of Lector -> one to many
     * @param groupe - groupe name
     * @param pairs - number pairs
     * @param subject - subject of user
     */
    public RequestFromLector(int idLector, String groupe, String pairs, String subject) {
        //this.idR = idR;
        this.idLector = idLector;
        this.group = groupe;
        this.numberOfPairs = pairs;
        this.subjectOfLector = subject;
        this.date = new Date();
    }


    /**
     * Constructor - create new object
     * @see RequestFromLector#RequestFromLector()
     * @see RequestFromLector#RequestFromLector(int, int, String, String, String, Date)
     * @see RequestFromLector#RequestFromLector(int, String, String, String)
     * @param idLector - identificator of Lector -> one to many
     * @param group - groupe name
     * @param numberOfPairs - number pairs
     * @param subjectOfLector - subject of user
     * @param dateToChangeRequest - date of request
     */
    public RequestFromLector(int idLector, String group, String numberOfPairs, String subjectOfLector, Date dateToChangeRequest) {
        this.idLector = idLector;
        this.group = group;
        this.numberOfPairs = numberOfPairs;
        this.subjectOfLector = subjectOfLector;
        this.date = dateToChangeRequest;
    }

    /**
     * Constructor - create new object
     * @see RequestFromLector#RequestFromLector(int, int, String, String, String, Date)
     * @see RequestFromLector#RequestFromLector(int, String, String, String, Date)
     * @see RequestFromLector#RequestFromLector(int, String, String, String)
     */
    public RequestFromLector() {
    }



    /**
     * Getter - get field date {@link RequestFromLector#date}
     * @return - return date of request
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter - set date field to Request {@link RequestFromLector#date}
     * @param date - date of request
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter - get field idR {@link RequestFromLector#idRequest}
     * @return - return identificator of request in database
     */

    public int getIdRequest() {
        return idRequest;
    }

    /**
     * Setter - set idR field to Request {@link RequestFromLector#idRequest}
     * * @param idR - identificator of request in database
     */
    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    /**
     * Getter - get field groupe {@link RequestFromLector#group}
     * @return - return groupe of request
     */

    public String getGroup() {
        return group;
    }

    /**
     * Setter - set groupe field to Request {@link RequestFromLector#group}
     * @param group - groupe name
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Getter - get field pairs {@link RequestFromLector#numberOfPairs}
     * @return - return pairs of request
     */

    public String getNumberOfPairs() {
        return numberOfPairs;
    }

    /**
     * Setter - set pairs field to Request {@link RequestFromLector#numberOfPairs}
     * @param numberOfPairs - number of pairs
     */
    public void setNumberOfPairs(String numberOfPairs) {
        this.numberOfPairs = numberOfPairs;
    }

    /**
     * Getter - get field subject {@link RequestFromLector#subjectOfLector}
     * @return - return subject of request
     */

    public String getSubjectOfLector() {
        return subjectOfLector;
    }

    /**
     * Setter - set subject field to Request {@link RequestFromLector#subjectOfLector}
     * @param subjectOfLector - subject name
     */
    public void setSubjectOfLector(String subjectOfLector) {
        this.subjectOfLector = subjectOfLector;
    }



    public int getIdLector() {
        return idLector;
    }

    public void setIdLector(int idLector) {
        this.idLector = idLector;
    }

    @Override
    public String toString() {
        return "Request{" +
                "idR=" + idRequest +
                ", groupe='" + group + '\'' +
                ", pairs='" + numberOfPairs + '\'' +
                ", subject='" + subjectOfLector + '\'' +
                ", date=" + date +
                ", id=" + idLector +
                '}';
    }
}
