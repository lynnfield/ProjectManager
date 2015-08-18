package com.gensko.projectmanager;

import com.gensko.projectmanager.repositories.TaskRepository;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TaskRepository.getInstance().load(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
