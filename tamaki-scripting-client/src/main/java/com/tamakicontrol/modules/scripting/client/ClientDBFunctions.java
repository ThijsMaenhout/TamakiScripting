package com.tamakicontrol.modules.scripting.client;

import com.inductiveautomation.ignition.client.gateway_interface.ModuleRPCFactory;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;
import com.tamakicontrol.modules.scripting.AbstractDBUtilities;
import com.tamakicontrol.modules.scripting.DBFunctionProvider;

import java.util.List;

/**
 * Created by cmwarre on 3/8/16.
 */
public class ClientDBFunctions extends AbstractDBUtilities{

    private final DBFunctionProvider rpc = ModuleRPCFactory.create(
            "com.tamakicontrol.modules.scripting",
            DBFunctionProvider.class
    );

    @Override
    @ScriptFunction(docBundlePrefix = "DBUtils")
    public List<List<Object>> runInternalQueryImpl(String query) {
        return rpc.runInternalQuery(query);
    }

}
