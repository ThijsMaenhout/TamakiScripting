package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.execution.impl.BasicExecutionEngine;
import org.python.core.PyObject;

import java.util.concurrent.Future;

public class TamakiTaskQueue {

    private static TamakiTaskQueue instance;
    private final BasicExecutionEngine taskQueue;

    public TamakiTaskQueue(int numThreads){
        taskQueue = new BasicExecutionEngine(numThreads, "Tamaki Execution Engine");
    }

    public static synchronized void initialize(int numThreads){
        instance = new TamakiTaskQueue(numThreads);
    }

    public static TamakiTaskQueue getInstance(){
        return instance;
    }

    public void addTaskToQueue(final PyObject task){
        taskQueue.executeOnce(new Runnable() {
            @Override
            public void run() {
                task.__call__();
            }
        });
    }

    //TODO implement this god damnit.  Will need a conventional java execution engine instead of inductives
    public Future<Object> execute(final PyObject task){
        return null;
    }

}
