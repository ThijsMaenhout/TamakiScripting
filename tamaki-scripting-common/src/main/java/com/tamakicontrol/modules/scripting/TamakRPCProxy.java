package com.tamakicontrol.modules.scripting;

import org.python.core.PyObject;

import java.util.List;

/**
 * Created by cwarren on 2/14/17.
 */
public interface TamakRPCProxy {
    Object runAtGateway(PyObject object);

    List<List<Object>> runInternalQuery(String query);

    List<List<Object>> runPrepInternalQuery(String query, Object[] args);

    int runInternalUpdateQuery(String query);

    int runPrepInternalUpdateQuery(String query, Object[] args);

}
