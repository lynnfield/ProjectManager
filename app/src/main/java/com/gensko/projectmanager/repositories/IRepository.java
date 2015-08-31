package com.gensko.projectmanager.repositories;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Genovich V.V. on 31.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public interface IRepository<Model> {
    void add(Model model, Callback<Model> callback);
    void remove(Model model, Callback<Model> callback);
    void replace(ReplaceData<Model> input, Callback<ReplaceData<Model>> callback);

    void find(Condition<Model> condition, Callback<Model> callback);
    void filter(Condition<Model> condition, Callback<List<Model>> callback);
    void sort(Comparator<Model> comparator, Callback<List<Model>> callback);

    void getData(Interval interval, Callback<List<Model>> callback);

    void save(Callback<String> callback);

    public static interface Callback<T> {
        public abstract void onDone(T result);
        public void onFail(Exception e);

        public static class CallbackException extends Exception {
            private final Object data;

            public CallbackException(Object data) {
                this.data = data;
            }

            public Object getData() {
                return data;
            }
        }

        public static class NotFoundException extends CallbackException {
            public NotFoundException(Object data) {
                super(data);
            }

            @Override
            public String getMessage() {
                return "not found";
            }
        }
    }

    public static interface ReplaceData<T> {
        T getOld();
        T getNew();
    }

    public static interface Interval {
        int OFFSET_BEGIN = 0;
        int LENGTH_MAX = -1;

        int getOffset();
        int getLength();

        public static class Full implements Interval {
            @Override
            public int getOffset() {
                return OFFSET_BEGIN;
            }

            @Override
            public int getLength() {
                return LENGTH_MAX;
            }
        }
    }

    public static interface Condition<T> {
        boolean check(T item);
    }
}
