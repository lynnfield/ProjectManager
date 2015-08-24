package com.gensko.projectmanager.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.gensko.projectmanager.repositories.RecordRepository;

import java.util.Observable;

/**
 * Created by Genovich V.V. on 24.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class AsyncObservable extends Observable implements Handler.Callback {
    private static final int NO_DATA = 0;
    private static final int WITH_DATA = 1;
    private final Handler handler = new Handler(Looper.getMainLooper(), this);

    @Override
    public void notifyObservers() {
        handler.sendEmptyMessage(NO_DATA);
    }

    @Override
    public void notifyObservers(Object data) {
        handler.sendMessage(handler.obtainMessage(WITH_DATA, data));
    }

    @Override
    public boolean handleMessage(Message message) {
        super.setChanged();
        switch (message.what) {
            case NO_DATA:
                super.notifyObservers();
                return true;
            case WITH_DATA:
                super.notifyObservers(message.obj);
                return true;
        }
        return false;
    }
}
