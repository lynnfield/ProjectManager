package com.gensko.projectmanager.utils;

import android.os.Handler;
import android.os.Looper;

import com.gensko.framework.processing.RunnableProcessor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class ListLoader<Model> {
    private static File sourceDir;
    private static RunnableProcessor processor = new RunnableProcessor();

    public static void init(File sourceDir) {
        ListLoader.sourceDir = sourceDir;
        processor.start();
    }

    public void load(String modelName, OnLoaderEventsListener<Model> listener) {
        processor.process(new Task<>(this, modelName, listener));
    }

    protected abstract Model parse(JSONObject obj) throws JSONException;

    public interface OnLoaderEventsListener<Model> {
        void onModelLoaded(Model model);
        void onDone();
        void onError(String error);
    }

    private static class Task<DataType> implements Runnable, OnLoaderEventsListener<DataType> {
        private static final Handler handler = new Handler(Looper.getMainLooper());
        private ListLoader<DataType> loader;
        private String modelName;
        private OnLoaderEventsListener<DataType> listener;

        private Task(ListLoader<DataType> loader, String modelName, OnLoaderEventsListener<DataType> listener) {
            this.loader = loader;
            this.modelName = modelName;
            this.listener = listener;
        }

        @Override
        public void run() {
            File file = new File(sourceDir, modelName + ".json");

            if (!file.exists()) {
                onError("File not exists " + file.getPath());
                return;
            }

            try {
                JSONObject obj = readObjectFrom(file);
                JSONArray data = obj.getJSONArray("data");

                for (int i = 0; i < data.length(); ++ i) {
                    DataType model = loader.parse(data.getJSONObject(i));
                    onModelLoaded(model);
                }

            } catch (IOException | JSONException e) {
                onError(e.toString());
                return;
            }

            onDone();
        }

        @Override
        public void onModelLoaded(final DataType model) {
            if (listener != null)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onModelLoaded(model);
                    }
                });
        }

        @Override
        public void onDone() {
            if (listener != null)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDone();
                    }
                });
        }

        public void onError(final String message) {
            if (listener != null)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(message);
                    }
                });
        }

        private JSONObject readObjectFrom(File file) throws IOException, JSONException {
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(stream);
            char[] buff = new char[1024];
            StringBuilder builder = new StringBuilder();

            while (reader.read(buff) > 0)
                builder.append(buff);
            return new JSONObject(builder.toString());
        }
    }
}
