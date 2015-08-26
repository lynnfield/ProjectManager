package com.gensko.projectmanager.observers;

import com.gensko.projectmanager.models.Record;
import com.gensko.projectmanager.models.TaskStateChange;
import com.gensko.projectmanager.repositories.TaskRepository;
import com.gensko.projectmanager.repositories.TaskStateChangeRepository;
import com.gensko.projectmanager.utils.TaskStateChangesProcessor;

import java.util.Observable;

/**
 * Created by Genovich V.V. on 21.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTaskCreator extends NotificationObserver {
    private final Runnable createTimerTaskRunnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < TaskStateChangeRepository.getInstance().size(); ++i)
                onTaskStateChanged(TaskStateChangeRepository.getInstance().get(i));
            timedTasksCreated = true;
        }
    };
    private final TaskStateChangesProcessor taskStateChangesProcessor =
            new TaskStateChangesProcessor();

    private boolean taskRepositoryLoaded;
    private boolean taskStateChangeRepositoryLoaded;
    private boolean timedTasksCreated;

    public TimedTaskCreator() {
        taskStateChangesProcessor.start();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        taskStateChangesProcessor.halt();
    }

    @Override
    protected void onLoaded(Observable observable) {
        if (isTaskRepository(observable))
            taskRepositoryLoaded = true;
        else if (isStateRepository(observable))
            taskStateChangeRepositoryLoaded = true;

        if (!timedTasksCreated && isAllLoaded())
            createTimedTaskAsync();
    }

    @Override
    protected void onAdd(Observable observable, Record data) {
        if (timedTasksCreated && isStateRepository(observable) && isAllLoaded())
            onTaskStateChanged((TaskStateChange) data);
    }

    private void createTimedTaskAsync() {
        if (TaskStateChangeRepository.getInstance().size() != 0) {
            new Thread(createTimerTaskRunnable).start();
        } else
            timedTasksCreated = true;
    }

    private void onTaskStateChanged(final TaskStateChange change) {
        taskStateChangesProcessor.process(change);
    }

    private boolean isTaskRepository(Observable observable) {
        return observable instanceof TaskRepository;
    }

    private boolean isStateRepository(Observable observable) {
        return observable instanceof TaskStateChangeRepository;
    }

    public boolean isAllLoaded() {
        return taskRepositoryLoaded && taskStateChangeRepositoryLoaded;
    }
}
