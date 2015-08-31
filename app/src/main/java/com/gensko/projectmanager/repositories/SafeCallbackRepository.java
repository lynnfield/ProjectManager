package com.gensko.projectmanager.repositories;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Genovich V.V. on 31.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class SafeCallbackRepository<Model> implements IRepository<Model> {
    private IRepository<Model> repository;

    public SafeCallbackRepository(IRepository<Model> repository) {
        this.repository = repository;
    }

    @Override
    public void add(Model model, Callback<Model> callback) {
        repository.add(model, new SafeCallback<>(callback));
    }

    @Override
    public void remove(Model model, Callback<Model> callback) {
        repository.remove(model, new SafeCallback<>(callback));
    }

    @Override
    public void replace(ReplaceData<Model> input, Callback<ReplaceData<Model>> callback) {
        repository.replace(input, new SafeCallback<>(callback));
    }

    @Override
    public void find(Condition<Model> condition, Callback<Model> callback) {
        repository.find(condition, new SafeCallback<>(callback));
    }

    @Override
    public void filter(Condition<Model> condition, Callback<List<Model>> callback) {
        repository.filter(condition, new SafeCallback<>(callback));
    }

    @Override
    public void sort(Comparator<Model> comparator, Callback<List<Model>> callback) {
        repository.sort(comparator, new SafeCallback<>(callback));
    }

    @Override
    public void getData(int offset, int length, Callback<List<Model>> callback) {
        repository.getData(offset, length, new SafeCallback<>(callback));
    }

    @Override
    public void save(Callback<String> callback) {
        repository.save(new SafeCallback<>(callback));
    }

    private static class SafeCallback<T> implements Callback<T> {
        private Callback<T> callback;

        private SafeCallback(Callback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onDone(T result) {
            if (callback != null)
                callback.onDone(result);
        }

        @Override
        public void onFail(Exception e) {
            if (callback != null)
                callback.onFail(e);
        }
    }
}
