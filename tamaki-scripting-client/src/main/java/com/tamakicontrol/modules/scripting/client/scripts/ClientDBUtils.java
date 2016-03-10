package com.tamakicontrol.modules.scripting.client.scripts;

import com.inductiveautomation.ignition.client.gateway_interface.ModuleRPCFactory;

import com.tamakicontrol.modules.scripting.AbstractDBUtils;
import com.tamakicontrol.modules.scripting.DBUtilProvider;

import java.util.List;

public class ClientDBUtils extends AbstractDBUtils {

    private final DBUtilProvider rpc;

    public ClientDBUtils(){
       rpc = ModuleRPCFactory.create(
                "com.tamakicontrol.modules.tamaki-scripting",
                DBUtilProvider.class
        );

    }

    @Override
    protected List<List<Object>> runInternalQueryImpl(String query) {
        return rpc.runInternalQuery(query);
    }

    @Override
    protected List<List<Object>> runPrepInternalQueryImpl(String query, Object[] args){ return rpc.runPrepInternalQuery(query, args); }

}
