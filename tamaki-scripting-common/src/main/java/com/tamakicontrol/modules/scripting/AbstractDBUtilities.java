package com.tamakicontrol.modules.scripting;

import java.util.List;

/**
 * Created by cmwarre on 3/8/16.
 */
public abstract class AbstractDBUtilities implements DBFunctionProvider{

    public List<List<Object>> runInternalQuery(String query){
        return runInternalQueryImpl(query);
    }

    protected abstract List<List<Object>> runInternalQueryImpl(String query);

    @Override
    public List<List<Object>> runPrepInternalQuery(String query, Object[] args) {
        return null;
    }
}
