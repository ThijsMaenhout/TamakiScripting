package com.tamakicontrol.modules.scripting;

import org.python.core.PyObject;

import java.util.concurrent.Future;

public interface SystemUtilProvider {

    String getUUID();

    String getStackTrace();

    void addToTaskQueue(PyObject object);

    //Object runAtGateway(PyObject object);

    //TODO get java future.  Will break java6 compatibility
    //Future<Object> getFutureValue(PyObject object);

}