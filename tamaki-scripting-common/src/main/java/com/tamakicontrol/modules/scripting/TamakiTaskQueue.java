package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.execution.TaskStats;
import com.inductiveautomation.ignition.common.execution.impl.BasicExecutionEngine;
import org.python.core.PyObject;

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

    public int getQueueLength(){

        int taskCount = 0;
        for(String owner : taskQueue.getTaskOwners()){
            for(TaskStats taskStats : taskQueue.getTasks(owner)){
                taskCount++;
            }
        }

        return taskCount;
    }

    public void clear(){
        for(String owner : taskQueue.getTaskOwners()){
            for(TaskStats taskStats : taskQueue.getTasks(owner)){
                int nop = 0;
            }
        }
    }


}
