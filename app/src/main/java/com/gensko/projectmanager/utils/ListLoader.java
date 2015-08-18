package com.gensko.projectmanager.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

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
public abstract class ListLoader<Model> extends AsyncTask<Void, Model, String> {
    private String modelName;
    private Context context;

    public ListLoader(Context context) {
        this.context = context;
    }

    public void load(String modelName) {
        this.modelName = modelName;
        execute();
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected String doInBackground(Void... nothing) {
        File file = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),
                "ProjectManager");

        file = new File(file, modelName + ".json");

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

        } catch (IOException | JSONException ignored) {}

        return "ok";
    }

    @SafeVarargs
    @Override
    protected final void onProgressUpdate(Model... values) {
        modelLoaded(values[0]);
    }

    protected abstract void modelLoaded(Model model);

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
}
