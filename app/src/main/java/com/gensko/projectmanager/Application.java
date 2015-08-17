package com.gensko.projectmanager;

import com.gensko.projectmanager.models.Status;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.repositories.TasksRepository;
import com.gensko.projectmanager.utils.ListLoader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new ListLoader<Task>(this) {
            @Override
            protected void modelLoaded(Task task) {
                TasksRepository.getInstance().addTask(task);
            }

            @Override
            protected Task parse(JSONObject obj) throws JSONException {
                Task task = new Task();
                task.setId(obj.getLong("Id"));
                task.setName(obj.getString("Name"));
                task.setStatus(com.gensko.projectmanager.models.Status.valueOf(obj.getString("Status")));
                return task;
            }
        }.load(Task.class.getName());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
