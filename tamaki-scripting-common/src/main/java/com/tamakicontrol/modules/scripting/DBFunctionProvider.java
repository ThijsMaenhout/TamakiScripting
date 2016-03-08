package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.script.builtin.DatasetUtilities;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by cmwarre on 3/7/16.
 */
public interface DBFunctionProvider {

    public List<List<Object>> runInternalQuery(String query);

    public List<List<Object>> runPrepInternalQuery(String query, Object[] args);

}