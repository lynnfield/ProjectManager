package com.gensko.projectmanager;

import com.gensko.projectmanager.repositories.TasksRepository;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TasksRepository.getInstance().load(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
