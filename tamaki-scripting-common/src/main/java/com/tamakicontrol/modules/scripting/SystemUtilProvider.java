package com.tamakicontrol.modules.scripting;

import org.python.core.PyObject;

public interface SystemUtilProvider {

    String getUUID();

    String getStackTrace();

    void addToTaskQueue(PyObject object);

    Object runAtGateway(PyObject object);

}