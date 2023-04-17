package com.udacity.jwdnd.course1.cloudstorage.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int userid;
    @Size(min = 0, max = 20, message = "First Name length must be less than or equal to 20")
    private String firstname;

    @Size(min = 0, max = 20, message = "Last Name length must be less than or equal to 20")
    private String lastname;

    @Size(min = 1, max = 20, message = "Username length must be grater than or equal to 1 and less than or equal to 20")
    private String username;

    @NotBlank(message = "Password required")
    private String password;

    private String salt;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
