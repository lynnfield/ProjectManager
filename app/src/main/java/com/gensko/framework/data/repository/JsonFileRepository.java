package com.gensko.framework.data.repository;

import com.gensko.framework.data.models.Jsonable;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Genovich V.V. on 31.08.2015.
 */
public class JsonFileRepository<Model extends Jsonable> implements Repository<Model> {
    private Repository<Model> repository;

    public JsonFileRepository(Repository<Model> repository) {
        this.repository = repository;
    }

    @Override
    public void add(Model model, Callback<Model> callback) {
        repository.add(model, callback);
    }

    @Override
    public void remove(Model model, Callback<Model> callback) {
        repository.remove(model, callback);
    }

    @Override
    public void replace(ReplaceData<Model> input, Callback<ReplaceData<Model>> callback) {
        repository.replace(input, callback);
    }

    @Override
    public void find(Condition<Model> condition, Callback<Model> callback) {
        repository.find(condition, callback);
    }

    @Override
    public void filter(Condition<Model> condition, Callback<List<Model>> callback) {
        repository.filter(condition, callback);
    }

    @Override
    public void sort(Comparator<Model> comparator, Callback<List<Model>> callback) {
        repository.sort(comparator, callback);
    }

    @Override
    public void getData(Interval interval, Callback<List<Model>> callback) {
        repository.getData(interval, callback);
    }

    @Override
    public void save(Callback<String> callback) {
        repository.save(callback);
    }
}
