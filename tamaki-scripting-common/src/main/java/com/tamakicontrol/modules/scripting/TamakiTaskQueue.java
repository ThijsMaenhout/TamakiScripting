package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.execution.impl.BasicExecutionEngine;
import org.python.core.PyObject;

public class TamakiTaskQueue {

    private static TamakiTaskQueue instance;
    private final BasicExecutionEngine taskQueue = new BasicExecutionEngine(4, "Tamaki Execution Engine");

    public TamakiTaskQueue(){
    }

    public static void initialize(){
        synchronized (instance){
            instance = new TamakiTaskQueue();
        }
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

}
