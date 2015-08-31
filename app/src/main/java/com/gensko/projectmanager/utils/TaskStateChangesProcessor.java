package com.gensko.projectmanager.utils;

import com.gensko.framework.processing.QueueProcessor;
import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.models.TaskStateChange;
import com.gensko.projectmanager.models.TimedTask;
import com.gensko.projectmanager.repositories.TaskRepository;

/**
 * Created by Genovich V.V. on 26.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChangesProcessor extends QueueProcessor<TaskStateChange> {

    @Override
    protected void processData(TaskStateChange change) {
        TimedTask timedTask;
        Task task = TaskRepository.getInstance().findBy(change);

        if (task == null)
            return;

        if (task instanceof TimedTask)
            timedTask = (TimedTask) task;
        else
            timedTask = new TimedTask(task);

        timedTask.addChange(change);

        TaskRepository.getInstance().replace(task, timedTask);
        TaskRepository.getInstance().save(null);
    }
}
