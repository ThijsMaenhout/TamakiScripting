package com.tamakicontrol.modules.scripting;

import java.util.List;

public interface DBUtilProvider {

    List<List<Object>> runInternalQuery(String query);

    List<List<Object>> runPrepInternalQuery(String query, Object[] args);

}