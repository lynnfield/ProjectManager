package com.gensko.projectmanager.repositories;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Genovich V.V. on 31.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class ListRepository<Model> implements IRepository<Model> {

    private List<Model> list = newList();

    protected abstract List<Model> newList();

    @Override
    public void add(Model model, Callback<Model> callback) {
        list.add(model);
        callback.onDone(model);
    }

    @Override
    public void remove(Model model, Callback<Model> callback) {
        if (list.remove(model)) {
            callback.onDone(model);
        } else
            callback.onFail(new Callback.NotFoundException(model));
    }

    @Override
    public void replace(ReplaceData<Model> input, Callback<ReplaceData<Model>> callback) {
        int i = list.indexOf(input.getOld());
        if (i < 0) {
            callback.onFail(new Callback.NotFoundException(input.getOld()));
        } else {
            list.remove(i);
            list.add(i, input.getNew());
            callback.onDone(input);
        }
    }

    @Override
    public void find(Condition<Model> condition, Callback<Model> callback) {
        for (Model model : list)
            if (condition.check(model)) {
                callback.onDone(model);
                return;
            }
        callback.onFail(new Callback.NotFoundException(null));
    }

    @Override
    public void filter(Condition<Model> condition, Callback<List<Model>> callback) {
        List<Model> result = newList();
        for (Model model : list)
            if (condition.check(model))
                result.add(model);
        callback.onDone(result);
    }

    @Override
    public void sort(Comparator<Model> comparator, Callback<List<Model>> callback) {
        List<Model> result = newList();
        result.addAll(list);
        Collections.sort(result, comparator);
        callback.onDone(result);
    }

    @Override
    public void getData(int offset, int length, Callback<List<Model>> callback) {
        length = (length == Interval.LENGTH_MAX ? list.size() - offset : length);
        callback.onDone(list.subList(offset, offset + length));
    }

    @Override
    public void save(Callback<String> callback) {}
}
