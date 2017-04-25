package com.tamakicontrol.modules.scripting.client.scripts;

import com.inductiveautomation.ignition.client.gateway_interface.ModuleRPCFactory;
import com.tamakicontrol.modules.scripting.AbstractSystemUtils;
import com.tamakicontrol.modules.scripting.DBUtilProvider;
import com.tamakicontrol.modules.scripting.SystemUtilProvider;
import org.python.core.PyObject;

public class ClientSystemUtils extends AbstractSystemUtils {

    private final SystemUtilProvider rpc;

    public ClientSystemUtils(){
        rpc = ModuleRPCFactory.create(
                "com.tamakicontrol.modules.scripting.tamaki-scripting",
                SystemUtilProvider.class
        );
    }

    @Override
    protected Object runAtGatewayImpl(PyObject object) {
        return rpc.runAtGateway(object);
    }
}
