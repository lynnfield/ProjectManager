package com.gensko.framework.utils;

/**
 * Created by Genovich V.V. on 27.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class RunnableProcessor extends QueueProcessor<Runnable> {
    @Override
    protected void processData(Runnable runnable) {
        runnable.run();
    }
}
