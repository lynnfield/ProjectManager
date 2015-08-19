package com.gensko.projectmanager.repositories;

import android.content.Context;

import com.gensko.projectmanager.models.TaskStateChangeList;
import com.gensko.projectmanager.models.domain.TaskStateChange;
import com.gensko.projectmanager.utils.ListLoader;
import com.gensko.projectmanager.utils.ListSaver;

import java.util.List;
import java.util.Observable;

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
    protected List<TaskStateChange> createNewList() {
        return new TaskStateChangeList();
    }

    @Override
    protected TaskStateChange[] createNewArray(int size) {
        return new TaskStateChange[size];
    }

    @Override
    protected void save(ListSaver<TaskStateChange> saver, TaskStateChange[] data) {

    }

    @Override
    protected ListSaver<TaskStateChange> createNewListSaver(Context context) {
        return null;
    }

    @Override
    protected void load(ListLoader<TaskStateChange> loader) {

    }

    @Override
    protected ListLoader<TaskStateChange> createNewListLoader(Context context) {
        return null;
    }
}
