package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.BundleUtil;

import java.util.List;

public abstract class AbstractDBUtilities implements DBUtilProvider {

    static {
        BundleUtil.get().addBundle(
                AbstractDBUtilities.class.getSimpleName(),
                AbstractDBUtilities.class.getClassLoader(),
                AbstractDBUtilities.class.getName().replace('.', '/')
        );
    }

    public List<List<Object>> runInternalQuery(String query){
        return runInternalQueryImpl(query);
    }

    protected abstract List<List<Object>> runInternalQueryImpl(String query);

    @Override
    public List<List<Object>> runPrepInternalQuery(String query, Object[] args) {
        return null;
    }

}
