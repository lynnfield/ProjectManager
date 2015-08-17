package com.gensko.projectmanager.repositories;

import com.gensko.projectmanager.models.Status;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.models.domain.TaskList;

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
}
