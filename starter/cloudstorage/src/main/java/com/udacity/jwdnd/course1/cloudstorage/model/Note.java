package com.udacity.jwdnd.course1.cloudstorage.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    private int noteid;

    @Size(min = 3, max = 20, message = "Note title length must be grater than or equal to 3 and less than or equal to 20")
    private String notetitle;

    @Size(max = 2000 , message = "Description length must be less than 2000")
    private String notedescription;

    private int userid;

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
