package com.udacity.jwdnd.course1.cloudstorage.model;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Credential implements Serializable {

    private static final long serialVersionUID = 1L;

    private int credentialid;

    @URL
    @Size(max = 100, message = "Url length must be less than or equal to 100")
    @NotBlank(message = "Url cannot be blank")
    private String url;

    @Size(max = 30, message = "Username length must be less than or equal to 30")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    private String key;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    private int userid;

    public int getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(int credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
