package com.gensko.projectmanager.models;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.json.JSONObject;

/**
 * Created by Lynnfield on 28.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class RecordTest extends TestCase {
    private static final long ID =1;
    JSONObject obj;
    Record rec;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        rec = new Record();
        rec.setId(ID);
        obj = new JSONObject("{\"Id\":" + ID + "}");
    }

    public void testId() throws Exception {
        Assert.assertEquals(ID, rec.getId());
    }

    public void testFillFrom() throws Exception {
        rec.fillFrom(obj);
        Assert.assertEquals(ID, rec.getId());
    }

    public void testToJson() throws Exception {
        Assert.assertEquals(obj, rec.toJson());
    }
}