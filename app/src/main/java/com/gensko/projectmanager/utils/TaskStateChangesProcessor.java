package com.gensko.projectmanager.utils;

import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.models.TaskStateChange;
import com.gensko.projectmanager.models.TimedTask;
import com.gensko.projectmanager.repositories.TaskRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Genovich V.V. on 26.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChangesProcessor extends Thread {
    private final Queue<TaskStateChange> queue = new LinkedList<>();
    private boolean halt;

    public void halt() {
        halt = true;
    }

    public void process(TaskStateChange change) {
        synchronized (queue) {
            queue.offer(change);
        }
    }

    @Override
    public void run() {
        while (!halt) {
            if (hasDataToProcess())
                processNextData();
            else
                pause();
        }
    }

    private boolean hasDataToProcess() {
        synchronized (queue) {
            return !queue.isEmpty();
        }
    }

    private void processNextData() {
        TaskStateChange change;
        synchronized (queue) {
            change = queue.poll();
        }
        processData(change);
    }

    private void processData(TaskStateChange change) {
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
    }

    private void pause() {
        try {
            sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    public void process(List<TaskStateChange> data) {
        synchronized (queue) {
            queue.addAll(data);
        }
    }
}
