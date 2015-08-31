package com.gensko.projectmanager.utils;

import android.os.Handler;
import android.os.Looper;

import com.gensko.framework.processing.RunnableProcessor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class ListSaver<Model> {
    private static File destinationDir;
    private static RunnableProcessor processor = new RunnableProcessor();

    public static void init(File destinationDir) {
        ListSaver.destinationDir = destinationDir;
        processor.start();
    }

    public void save(String modelName, List<Model> data, OnListSaverEventsListener<Model> listener) {
        processor.process(new Task<>(this, modelName, data, listener));
    }

    protected abstract JSONObject createJsonFrom(Model model) throws JSONException;

    public interface OnListSaverEventsListener<Model> {
        void onSaved(Model model);
        void onError(String message);
        void onDone();
    }

    private static class Task<DataType> implements Runnable {
        private static final Handler handler = new Handler(Looper.getMainLooper());
        private ListSaver<DataType> saver;
        private String modelName;
        private List<DataType> data = new ArrayList<>();
        private OnListSaverEventsListener<DataType> listener;

        public Task(
                ListSaver<DataType> saver, String modelName,
                List<DataType> data,
                OnListSaverEventsListener<DataType> listener) {
            this.saver = saver;
            this.modelName = modelName;
            this.data.addAll(data);
            this.listener = listener;
        }

        @Override
        public void run() {
            JSONArray array = new JSONArray();

            for (DataType model : data) {
                try {
                    array.put(saver.createJsonFrom(model));
                    onSaved(model);
                } catch (JSONException e) {
                    onError(e.toString());
                    return;
                }
            }

            JSONObject obj = new JSONObject();

            try {
                obj.put("data", array);

                File file = new File(destinationDir, modelName + ".json");

                writeObjectTo(file, obj);
            } catch (JSONException | IOException e) {
                onError(e.toString());
                return;
            }

            onDone();
        }

        private void writeObjectTo(File file, JSONObject obj) throws IOException, JSONException {
            FileOutputStream stream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(obj.toString());
            writer.flush();
            writer.close();
        }

        private void onDone() {
            if (listener != null)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDone();
                    }
                });
        }

        private void onError(final String message) {
            if (listener != null)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(message);
                    }
                });
        }

        private void onSaved(final DataType model) {
            if (listener != null)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSaved(model);
                    }
                });
        }
    }
}
