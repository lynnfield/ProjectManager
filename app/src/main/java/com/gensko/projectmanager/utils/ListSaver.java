package com.gensko.projectmanager.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

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
@SuppressWarnings("DefaultFileTemplate")
public abstract class ListSaver<Model> extends AsyncTask<Model, Void, String> {
    private static File destinationDir;
    private String modelName;

    public static void init(File destinationDir) {
        ListSaver.destinationDir = destinationDir;
    }

    public void save(String modelName, Model[] data) {
        this.modelName = modelName;
        execute(data);
    }

    @SafeVarargs
    @Override
    protected final String doInBackground(Model... data) {
        JSONArray array = new JSONArray();

        for (Model model : data)
            try {
                array.put(createJsonFrom(model));
            } catch (JSONException e) {
                return e.toString();}

        JSONObject obj = new JSONObject();

        try {
            obj.put("data", array);

            File file = new File(destinationDir, modelName + ".json");

            writeObjectTo(file, obj);
        } catch (JSONException | IOException e) {
            return e.toString();
        }

        return obj.toString();
    }

    protected abstract JSONObject createJsonFrom(Model model) throws JSONException;

    private void writeObjectTo(File file, JSONObject obj) throws IOException {
        FileOutputStream stream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        writer.write(obj.toString());
        writer.flush();
        writer.close();
    }
}
