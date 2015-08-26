package com.gensko.projectmanager.observers;

import com.gensko.projectmanager.models.Record;
import com.gensko.projectmanager.repositories.RecordRepository;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Genovich V.V. on 25.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class NotificationObserver implements Observer {
    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof RecordRepository.Notification) {
            RecordRepository.Notification notification = (RecordRepository.Notification) o;
            onNotification(observable, notification);
        }
    }

    protected void onNotification(Observable observable, RecordRepository.Notification notification) {
        switch (notification.getState()) {
            case ADD:
                onAdd(observable, notification.getData());
                break;
            case REMOVE:
                onRemove(observable, notification.getData());
                break;
            case REMOVE_SUBLIST:
                onRemoveSublist(observable);
                break;
            case LOADING_ERROR:
                onLoadingError(observable, notification.getMessage());
                break;
            case REPLACE:
                onReplace(observable, notification.getOld(), notification.getData());
                break;
            case LOADED:
                onLoaded(observable);
                break;
        }
    }

    protected void onAdd(Observable observable, Record data) {}
    protected void onRemove(Observable observable, Record data) {}
    protected void onRemoveSublist(Observable observable) {}
    protected void onLoadingError(Observable observable, String message) {}
    protected void onReplace(Observable observable, Record old, Record data) {}
    protected void onLoaded(Observable observable) {}
}
