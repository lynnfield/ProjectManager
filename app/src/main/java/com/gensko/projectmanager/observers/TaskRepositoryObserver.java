package com.gensko.projectmanager.observers;

import android.support.v7.widget.RecyclerView;

import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.repositories.RecordRepository;
import com.gensko.projectmanager.repositories.TaskRepository;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskRepositoryObserver implements Observer {
    private RecyclerView.Adapter adapter;

    public TaskRepositoryObserver(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof TaskRepository &&
                o instanceof RecordRepository.Notification) {
            RecordRepository.Notification notification =
                    (RecordRepository.Notification) o;

            adapter.notifyDataSetChanged();
        }
    }
}
