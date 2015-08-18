package com.gensko.projectmanager.repositories;

import android.content.Context;

import com.gensko.projectmanager.models.Status;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.models.domain.TaskList;
import com.gensko.projectmanager.utils.ListSaver;
import com.gensko.projectmanager.utils.TaskListSaver;

import org.json.JSONObject;

import java.util.Observable;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TasksRepository {
    private static final TasksRepository instance = new TasksRepository();
    private static long ID = 0;

    public static TasksRepository getInstance() {
        return instance;
    }

    private static long nextId() {
        return ++ID;
    }

    private TaskList tasks = new TaskList();

    private TasksRepository() {}

    public TaskList getTasks() {
        return tasks;
    }

    public long addTask(Task task) {
        task.setId(nextId());
        tasks.add(task);
        return task.getId();
    }

    public void makeDone(Task task) {
        task.setStatus(Status.DONE);
    }

    public void save(Context context) {
        if (tasks.size() > 0)
                new TaskListSaver(context)
                        .save(
                                tasks.toArray(
                                        new Task[tasks.size()]));
    }
}
