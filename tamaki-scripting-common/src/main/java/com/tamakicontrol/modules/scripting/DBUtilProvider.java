package com.tamakicontrol.modules.scripting;

import java.util.List;

public interface DBUtilProvider {

    public List<List<Object>> runInternalQuery(String query);

    public List<List<Object>> runPrepInternalQuery(String query, Object[] args);

}