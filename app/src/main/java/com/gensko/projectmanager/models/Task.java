package com.gensko.projectmanager.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Task extends Record implements StateChanger, State.IState {
    private static final String NAME_FIELD = "Name";
    public static final String STATE_FIELD = "Status";

    private String name = "";
    private State state = new State.Created(this);

    public Task() {
    }

    public Task(Task task) {
        super(task);
        name = task.name;
        state = task.state.copyFor(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Task && ((Task)o).getId() == getId();
    }

    @Override
    public void fillFrom(JSONObject obj) throws JSONException {
        super.fillFrom(obj);
        name = obj.getString(NAME_FIELD);
        state = State.valueOf(obj.getString(STATE_FIELD), this);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return super.toJson()
                .put(NAME_FIELD, name)
                .put(STATE_FIELD, state.toString());
    }

    @Override
    public boolean start() {
        return state.start();
    }

    @Override
    public boolean pause() {
        return state.pause();
    }

    @Override
    public boolean done() {
        return state.done();
    }

    @Override
    public boolean isStartAvailable() {
        return state.isStartAvailable();
    }

    @Override
    public boolean isPauseAvailable() {
        return state.isPauseAvailable();
    }

    @Override
    public boolean isDoneAvailable() {
        return state.isDoneAvailable();
    }
}
