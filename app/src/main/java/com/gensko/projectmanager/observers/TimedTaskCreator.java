package com.gensko.projectmanager.observers;

import android.os.Handler;
import android.os.Looper;

import com.gensko.projectmanager.models.TimedTask;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.models.domain.TaskStateChange;
import com.gensko.projectmanager.repositories.RecordRepository;
import com.gensko.projectmanager.repositories.TaskRepository;
import com.gensko.projectmanager.repositories.TaskStateChangeRepository;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Genovich V.V. on 21.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTaskCreator implements Observer {
    private static final Handler handler = new Handler(Looper.getMainLooper());
    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(1, 4, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private boolean taskRepositoryLoaded;
    private boolean taskStateChangeRepositoryLoaded;
    private boolean timedTasksCreated;

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof RecordRepository.Notification) {
            RecordRepository.Notification notification = (RecordRepository.Notification) o;
            onNotification(observable, notification);
        }
    }

    private void onNotification(Observable observable, RecordRepository.Notification notification) {
        if (isLoadedNotification(notification))
            onLoaded(observable);

        if (!timedTasksCreated && isAllLoaded())
            createTimedTaskAsync();
        else if (isAllLoaded() && isStateRepository(observable) && isAddNotification(notification))
            onTaskStateChanged((TaskStateChange) notification.getData());
    }

    private void createTimedTaskAsync() {
        if (TaskStateChangeRepository.getInstance().size() != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < TaskStateChangeRepository.getInstance().size(); ++i)
                        onTaskStateChanged(TaskStateChangeRepository.getInstance().get(i));
                    timedTasksCreated = true;
                }
            }).start();
        } else
            timedTasksCreated = true;
    }

    private void onTaskStateChanged(final TaskStateChange data) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final TimedTask timedTask;
                final Task task = TaskRepository.getInstance().findBy(data);

                if (task instanceof TimedTask)
                    timedTask = (TimedTask) task;
                else
                    timedTask = new TimedTask(task);

                timedTask.addChange(data);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TaskRepository.getInstance().replace(task, timedTask);
                    }
                });
            }
        });
    }

    private boolean isAddNotification(RecordRepository.Notification notification) {
        return checkNotificationState(notification, RecordRepository.Notification.State.ADD);
    }

    private boolean isLoadedNotification(RecordRepository.Notification notification) {
        return checkNotificationState(notification, RecordRepository.Notification.State.LOADED);
    }

    private boolean checkNotificationState(
            RecordRepository.Notification notification,
            RecordRepository.Notification.State expected) {
        return expected.equals(notification.getState());
    }

    private void onLoaded(Observable observable) {
        if (isTaskRepository(observable))
            taskRepositoryLoaded = true;
        else if (isStateRepository(observable))
            taskStateChangeRepositoryLoaded = true;
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
