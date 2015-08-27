package com.gensko.projectmanager;

import android.os.Environment;
import android.widget.Toast;

import com.gensko.projectmanager.observers.TimedTaskCreator;
import com.gensko.projectmanager.repositories.TaskRepository;
import com.gensko.projectmanager.repositories.TaskStateChangeRepository;
import com.gensko.projectmanager.utils.ListLoader;
import com.gensko.projectmanager.utils.ListSaver;

import java.io.File;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        File dataDir =
                new File(
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS),
                        "ProjectManager");

        if (!dataDir.exists())
            if (!dataDir.mkdirs()) {
                Toast.makeText(this, R.string.can_not_create_data_dir, Toast.LENGTH_SHORT).show();
                return;
            }

        ListSaver.init(dataDir);
        ListLoader.init(dataDir);

        TimedTaskCreator creator = new TimedTaskCreator();

        TaskRepository.getInstance().addObserver(creator);
        TaskStateChangeRepository.getInstance().addObserver(creator);

        TaskRepository.getInstance().load();
        TaskStateChangeRepository.getInstance().load();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
