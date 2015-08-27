package com.gensko.projectmanager.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Genovich V.V. on 25.08.2015.
 */
@SuppressWarnings({"DefaultFileTemplate", "UnusedDeclaration"})
public interface Jsonable {
    public static DateFormat FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());

    void fillFrom(JSONObject obj) throws JSONException;
    JSONObject toJson() throws JSONException;
}
