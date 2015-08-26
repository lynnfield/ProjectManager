package com.gensko.projectmanager.utils;

import com.gensko.projectmanager.models.TaskStateChange;

/**
 * Created by Genovich V.V. on 21.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChangeComparator implements java.util.Comparator<TaskStateChange> {
    private static TaskStateChangeComparator instance = new TaskStateChangeComparator();

    public static TaskStateChangeComparator getInstance() {
        return instance;
    }

    @Override
    public int compare(TaskStateChange lhs, TaskStateChange rhs) {
        return lhs.getTime().compareTo(rhs.getTime());
    }
}
