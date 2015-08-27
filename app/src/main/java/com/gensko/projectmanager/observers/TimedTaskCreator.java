package com.gensko.projectmanager.observers;

import com.gensko.projectmanager.models.Record;
import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.models.TaskStateChange;
import com.gensko.projectmanager.models.TimedTask;
import com.gensko.projectmanager.repositories.TaskRepository;
import com.gensko.projectmanager.repositories.TaskStateChangeRepository;
import com.gensko.projectmanager.utils.TaskStateChangesProcessor;

import java.util.Observable;

/**
 * Created by Genovich V.V. on 21.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTaskCreator extends NotificationObserver {
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
    protected void onLoadingError(Observable observable, String message) {
        onLoaded(observable);
    }

    @Override
    protected void onAdd(Observable observable, Record data) {
        if (timedTasksCreated && isStateRepository(observable) && isAllLoaded())
            onTaskStateChanged((TaskStateChange) data);
    }

    private void createTimedTaskAsync() {
        if (TaskStateChangeRepository.getInstance().size() != 0)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < TaskStateChangeRepository.getInstance().size(); ++ i) {
                        TaskStateChange change = TaskStateChangeRepository.getInstance().get(i);
                        TimedTask task = (TimedTask) TaskRepository.getInstance().findBy(change);
                        if (task == null) {
                            TaskStateChangeRepository.getInstance().remove(change);
                            --i;
                        }
                        else
                            task.linkChange(change);
                    }
                    timedTasksCreated = true;
                }
            }).start();
        else
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
