package com.gensko.projectmanager.repositories;

import com.gensko.projectmanager.models.TaskStateChangeList;
import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.models.TaskStateChange;
import com.gensko.projectmanager.utils.ListLoader;
import com.gensko.projectmanager.utils.ListSaver;
import com.gensko.projectmanager.utils.TaskStateChangeListLoader;
import com.gensko.projectmanager.utils.TaskStateChangeListSaver;

import java.util.List;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChangeRepository extends RecordRepository<TaskStateChange> {
    private static final TaskStateChangeRepository instance = new TaskStateChangeRepository();

    public static TaskStateChangeRepository getInstance() {
        return instance;
    }

    private TaskStateChangeRepository() {}

    @Override
    public long add(TaskStateChange taskStateChange) {
        return super.add(taskStateChange);
    }

    @Override
    protected boolean isSame(TaskStateChange item, Object o) {
        return o instanceof Task && item.getTaskId() == ((Task) o).getId();
    }

    @Override
    protected List<TaskStateChange> createNewList() {
        return new TaskStateChangeList();
    }

    @Override
    protected void save(ListSaver<TaskStateChange> saver, List<TaskStateChange> data, ListSaver.OnListSaverEventsListener<TaskStateChange> listener) {
        TaskStateChangeListSaver listSaver = (TaskStateChangeListSaver) saver;
        listSaver.save(data, listener);
    }

    @Override
    protected ListSaver<TaskStateChange> createNewListSaver() {
        return new TaskStateChangeListSaver();
    }

    @Override
    protected void load(ListLoader<TaskStateChange> loader, ListLoader.OnLoaderEventsListener<TaskStateChange> listener) {
        TaskStateChangeListLoader listLoader = (TaskStateChangeListLoader) loader;
        listLoader.load(listener);
    }

    @Override
    protected ListLoader<TaskStateChange> createNewListLoader() {
        return new TaskStateChangeListLoader();
    }

    public void onTaskRemoved(Task task) {
        List<TaskStateChange> list = findAllBy(task);
        removeAll(list);
    }
}
