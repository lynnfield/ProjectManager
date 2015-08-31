package com.gensko.projectmanager.repositories;

import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.models.TaskList;
import com.gensko.projectmanager.models.TaskStateChange;
import com.gensko.framework.utils.ListLoader;
import com.gensko.framework.utils.ListSaver;
import com.gensko.projectmanager.utils.TaskListLoader;
import com.gensko.projectmanager.utils.TaskListSaver;
import com.gensko.projectmanager.utils.TimedTaskListLoader;

import java.util.List;

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
    protected void save(ListSaver<Task> saver, List<Task> data, ListSaver.OnListSaverEventsListener<Task> listener) {
        TaskListSaver listSaver = (TaskListSaver) saver;
        listSaver.save(data, listener);
    }

    @Override
    protected ListSaver<Task> createNewListSaver() {
        return new TaskListSaver();
    }

    @Override
    protected void load(ListLoader<Task> loader, ListLoader.OnLoaderEventsListener<Task> listener) {
        TaskListLoader listLoader = (TaskListLoader) loader;
        listLoader.load(listener);
    }

    @Override
    protected ListLoader<Task> createNewListLoader() {
        return new TimedTaskListLoader();
    }


}
