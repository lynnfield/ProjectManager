package com.gensko.projectmanager.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
public abstract class ListSaver<Model> extends AsyncTask<Model, Void, String> {
    private String modelName;
    private Context context;

    public ListSaver(Context context) {
        this.context = context;
    }

    public void save(String modelName, Model[] data) {
        this.modelName = modelName;
        execute(data);
    }

    @SafeVarargs
    @Override
    protected final String doInBackground(Model... data) {
        JSONArray array = new JSONArray();

        for (Model aData : data) array.put(createJsonFrom(aData));

        JSONObject obj = new JSONObject();

        try {
            obj.put("data", array);

            writeObjectTo(
                    new File(context.getFilesDir(), modelName + ".json"),
                    obj);
        } catch (JSONException | IOException ignored) {}

        return null;
    }

    protected abstract JSONObject createJsonFrom(Model model) throws IllegalAccessException, JSONException;

    private void writeObjectTo(File file, JSONObject obj) throws IOException {
        FileOutputStream stream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        writer.write(obj.toString());
    }
}
