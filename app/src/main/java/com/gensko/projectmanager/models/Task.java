package com.gensko.projectmanager.models;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
public class Task extends Record {
    private String name = "";
    private Status status = Status.NULL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
