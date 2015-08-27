package com.gensko.projectmanager.observers;

import android.support.v7.widget.RecyclerView;

import com.gensko.projectmanager.repositories.RecordRepository;

import java.util.Observable;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskRepositoryObserver extends NotificationObserver {
    private RecyclerView.Adapter adapter;

    public TaskRepositoryObserver(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void onNotification(Observable observable, RecordRepository.Notification notification) {
        adapter.notifyDataSetChanged();
    }
}
