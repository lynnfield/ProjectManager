package com.gensko.projectmanager.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 25.08.2015.
 */
@SuppressWarnings({"DefaultFileTemplate", "UnusedDeclaration"})
public interface Jsonable {
    void fillFrom(JSONObject obj) throws JSONException;
    JSONObject toJson() throws JSONException;
}
