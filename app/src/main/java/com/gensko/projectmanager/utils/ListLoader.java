package com.gensko.projectmanager.utils;

import android.os.AsyncTask;

import com.gensko.projectmanager.models.Record;

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
public abstract class ListLoader<Model extends Record> extends AsyncTask<Void, Model, String> {
    private static File sourceDir;

    private String modelName;
    private OnLoaderEventsListener<Model> listener;

    public static void init(File sourceDir) {
        ListLoader.sourceDir = sourceDir;
    }

    public void load(String modelName) {
        this.modelName = modelName;
        execute();
    }

    @Override
    protected String doInBackground(Void... nothing) {
        File file = new File(sourceDir, modelName + ".json");

        if (!file.exists())
            return "File not exists " + file.getPath();

        try {
            JSONObject obj = readObjectFrom(file);
            JSONArray data = obj.getJSONArray("data");

            for (int i = 0; i < data.length(); ++ i) {
                Model model = parse(data.getJSONObject(i));
                //noinspection unchecked
                publishProgress(model);
            }

        } catch (IOException | JSONException e) {
            return e.toString();
        }

        return "ok";
    }

    @SafeVarargs
    @Override
    protected final void onProgressUpdate(Model... values) {
        if (listener != null)
            listener.onModelLoaded(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        if (listener != null)
            if ("ok".equals(s))
                listener.onDone();
            else
                listener.onError(s);
    }

    protected abstract Model parse(JSONObject obj) throws JSONException;

    private JSONObject readObjectFrom(File file) throws IOException, JSONException {
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(stream);
            char[] buff = new char[1024];
            StringBuilder builder = new StringBuilder();

            while (reader.read(buff) > 0)
                builder.append(buff);
        return new JSONObject(builder.toString());
    }

    public void setModelLoadedListener(OnLoaderEventsListener<Model> listener) {
        this.listener = listener;
    }

    public interface OnLoaderEventsListener<Model extends Record> {
        void onModelLoaded(Model model);
        void onDone();
        void onError(String error);
    }
}
