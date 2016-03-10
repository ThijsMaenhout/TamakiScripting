package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;

import java.util.List;

public abstract class AbstractDBUtils implements DBUtilProvider {

    static {
        BundleUtil.get().addBundle(
                AbstractDBUtils.class.getSimpleName(),
                AbstractDBUtils.class.getClassLoader(),
                AbstractDBUtils.class.getName().replace('.', '/')
        );

    }

    @Override
    @ScriptFunction(docBundlePrefix = "DBUtils")
    public List<List<Object>> runInternalQuery(String query){
        return runInternalQueryImpl(query);
    }

    protected abstract List<List<Object>> runInternalQueryImpl(String query);

    @Override
    @ScriptFunction(docBundlePrefix = "DBUtils")
    public List<List<Object>> runPrepInternalQuery(String query, Object[] args) {
        return runPrepInternalQueryImpl(query, args);
    }

    protected abstract List<List<Object>> runPrepInternalQueryImpl(String query, Object[] args);

}
