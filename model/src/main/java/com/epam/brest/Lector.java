
/**
 * User class . Fields <b>id</b> <b>name</b> <b>login</b> <b>password</b> <b>email</b>
 *
 * @autor Oleg Suhodolsky
 * @version 1.0
 */
package com.epam.brest;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


// This tells Hibernate to make a table out of this class

/**
 * Entity class for lectors registration
 */
@Entity
@Table(name = "lector")
@Component
public class Lector {
    /** field id - user's identificator in database*/
    @Column(name = "idLector", unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idLector;
    /** field name - user's fullname*/

    @Column(name = "nameLector", nullable = false, length = 50)
    @NotEmpty(message = "Name should be not empty")
    @Size(min = 2, max = 50, message = "Size of name should not be 2-50 characters")
    private String nameLector;

    /** field name - user's login in system*/
    @Column(name = "loginLector", nullable = false, length = 50)
    @NotEmpty(message = "Login should be not empty")
    @Size(min = 2, max = 50, message = "Size of login should not be 2-50 characters")
    private String loginLector;

    /** field name - user's password*/
    @Column(name = "passwordLector", nullable = false, length = 50)
    @NotEmpty(message = "Password should be not empty")
    @Size(min = 4, max = 50, message = "Size of password should not be 4-50 characters")
    private String passwordLector;

    @Column(name = "emailLector", nullable = false)
    @NotEmpty(message = "Email should  be not empty")
    @Email(message = "Email should  be valid")
    /** field name - user's e-mail*/
    private String emailLector;



    /**
     * Constructor - create new object
     * @see Lector#Lector()
     * @see Lector#Lector(String, String, String, String)
     * @param id - identificator
     * @param name - fullname od user
     * @param login - login of user
     * @param password - user's password
     * @param email - user's email
     */
    public Lector(int id, String name, String login, String password, String email) {
        this.idLector = id;
        this.nameLector = name;
        this.loginLector = login;
        this.passwordLector = password;
        this.emailLector = email;
    }

    /**
     * Constructor - create new object
     * @see Lector#Lector(int, String, String, String, String)
     * @see Lector#Lector(String, String, String, String)
     */
    public Lector() {
    }


    /**
     * Constructor - create new object
     * @see Lector#Lector()
     * @see Lector#Lector(int, String, String, String, String)
     * @param name - fullname od user
     * @param login - login of user
     * @param password - user's password
     * @param email - user's email
     */
    public Lector(String name, String login, String password, String email) {
        this.nameLector = name;
        this.loginLector = login;
        this.passwordLector = password;
        this.emailLector = email;
    }


    /**
     * Getter - get field id {@link Lector#idLector}
     * @return - return id of user
     */
    public int getIdLector() {
        return idLector;
    }

    /**
     * Setter - set id field to User {@link Lector#idLector}
     * @param idLector - identificator
     */
    public void setIdLector(int idLector) {
        this.idLector = idLector;
    }

    /**
     * Getter - get field name {@link Lector#nameLector}
     * @return - return fullname of user
     */
    public String getNameLector() {
        return nameLector;
    }

    /**
     * Setter - set name field to User {@link Lector#nameLector}
     * @param nameLector - identificator
     * @return
     */
    public void setNameLector(String nameLector) {
        this.nameLector = nameLector;
    }

    /**
     * Getter - get field login {@link Lector#loginLector}
     * @return - return login of user
     */
    public String getLoginLector() {
        return loginLector;
    }

    /**
     * Setter - set login field to User {@link Lector#loginLector}
     * @param loginLector - identificator
     */
    public void setLoginLector(String loginLector) {
        this.loginLector = loginLector;
    }

    /**
     * Getter - get field password {@link Lector#passwordLector}
     * @return - return password of user
     */
    public String getPasswordLector() {
        return passwordLector;
    }

    /**
     * Setter - set password field to User {@link Lector#passwordLector}
     * @param passwordLector - identificator
     */
    public void setPasswordLector(String passwordLector) {
        this.passwordLector = passwordLector;
    }

    /**
     * Getter - get field id {@link Lector#emailLector}
     * @return - return e-mail of user
     */
    public String getEmailLector() {
        return emailLector;
    }

    /**
     * Setter - set email field to User {@link Lector#emailLector}
     * @param emailLector - identificator
     */
    public void setEmailLector(String emailLector) {
        this.emailLector = emailLector;
    }



    /**
     * METHOD toString()
     * @return object to string
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + idLector +
                ", name='" + nameLector + '\'' +
                ", login='" + loginLector + '\'' +
                ", password='" + passwordLector + '\'' +
                ", email='" + emailLector + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lector user = (Lector) o;
        return nameLector.equals(user.nameLector) && loginLector.equals(user.loginLector) && passwordLector.equals(user.passwordLector) && emailLector.equals(user.emailLector);
    }

}
