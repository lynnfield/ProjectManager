package com.gensko.projectmanager.models;

import java.util.Date;

/**
 * Created by Lynnfield on 15.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Task extends Record {
    private static Task NULL = new Task();

    private String customer = "";
    private String project = "";
    private String name = "";
    private Task parent = NULL;
    private Date begin = null;
    private Date end = null;
    private Status status = Status.NULL;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Task getParent() {
        return parent;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean hasParent() {
        return !NULL.equals(parent);
    }
}
