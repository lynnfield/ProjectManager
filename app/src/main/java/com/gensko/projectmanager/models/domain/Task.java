package com.gensko.projectmanager.models.domain;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
public class Task extends Record {
    public static final String NAME_FIELD = "Name";
    public static final String STATUS_FIELD = "Status";

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

    @Override
    public boolean equals(Object o) {
        return o instanceof Task && ((Task)o).getId() == getId();
    }
}
