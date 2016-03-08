package com.tamakicontrol.modules.scripting.client.scripts;

import com.inductiveautomation.ignition.client.gateway_interface.ModuleRPCFactory;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;
import com.tamakicontrol.modules.scripting.AbstractDBUtilities;
import com.tamakicontrol.modules.scripting.DBUtilProvider;

import java.util.List;

public class ClientDBUtilities extends AbstractDBUtilities{

    private final DBUtilProvider rpc = ModuleRPCFactory.create(
            "com.tamakicontrol.modules.scripting",
            AbstractDBUtilities.class
    );

    @Override
    @ScriptFunction(docBundlePrefix = "DBUtils")
    public List<List<Object>> runInternalQueryImpl(String query) {
        return rpc.runInternalQuery(query);
    }

}
