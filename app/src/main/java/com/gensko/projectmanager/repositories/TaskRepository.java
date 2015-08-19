package com.gensko.projectmanager.repositories;

import android.content.Context;

import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.models.TaskList;
import com.gensko.projectmanager.utils.TaskListLoader;
import com.gensko.projectmanager.utils.TaskListSaver;

import java.util.Observable;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskRepository extends Observable {
    private static final TaskRepository instance = new TaskRepository();
    private static long ID = 0;

    public static TaskRepository getInstance() {
        return instance;
    }

    private static long nextId() {
        return ++ID;
    }

    private TaskList tasks = new TaskList();

    private TaskRepository() {}

    public TaskList getTasks() {
        return tasks;
    }

    public long addTask(Task task) {
        task.setId(nextId());
        tasks.add(task);
        return task.getId();
    }

    public void save(Context context) {
        if (tasks.size() > 0)
            new TaskListSaver(context)
                    .save(tasks.toArray(new Task[tasks.size()]));
    }

    public void load(Context context) {
        new TaskListLoader(context) {
            @Override
            protected void modelLoaded(Task task) {
                tasks.add(task);
                setChanged();
                notifyObservers();
            }
        }.load();
    }

    public void remove(Task task) {
        tasks.remove(task);
    }
}
