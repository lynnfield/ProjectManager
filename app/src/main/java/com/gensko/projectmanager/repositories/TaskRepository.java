package com.gensko.projectmanager.repositories;

import android.content.Context;

import com.gensko.projectmanager.models.TimedTask;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.models.TaskList;
import com.gensko.projectmanager.models.domain.TaskStateChange;
import com.gensko.projectmanager.utils.ListLoader;
import com.gensko.projectmanager.utils.ListSaver;
import com.gensko.projectmanager.utils.TaskListLoader;
import com.gensko.projectmanager.utils.TaskListSaver;

import java.util.List;
import java.util.Observable;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskRepository extends RecordRepository<Task> {
    private static final TaskRepository instance = new TaskRepository();

    public static TaskRepository getInstance() {
        return instance;
    }

    private TaskRepository() {}

    @Override
    protected boolean isSame(Task item, Object object) {
        if (object instanceof TaskStateChange)
            return ((TaskStateChange)object).getTaskId() == item.getId();

        return item.equals(object);
    }

    @Override
    protected List<Task> createNewList() {
        return new TaskList();
    }

    @Override
    protected Task[] createNewArray(int size) {
        return new Task[size];
    }

    @Override
    protected void save(ListSaver<Task> saver, Task[] data) {
        TaskListSaver listSaver = (TaskListSaver) saver;
        listSaver.save(data);
    }

    @Override
    protected ListSaver<Task> createNewListSaver() {
        return new TaskListSaver();
    }

    @Override
    protected void load(ListLoader<Task> loader) {
        TaskListLoader listLoader = (TaskListLoader) loader;
        listLoader.load();
    }

    @Override
    protected ListLoader<Task> createNewListLoader() {
        return new TaskListLoader();
    }
}
