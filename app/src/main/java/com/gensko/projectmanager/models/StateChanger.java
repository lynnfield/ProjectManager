package com.gensko.projectmanager.models;

/**
 * Created by Genovich V.V. on 25.08.2015.
 */
@SuppressWarnings({"DefaultFileTemplate", "UnusedDeclaration"})
public interface StateChanger {
    boolean start();
    boolean pause();
    boolean done();

    boolean isStartAvailable();
    boolean isPauseAvailable();
    boolean isDoneAvailable();
}
