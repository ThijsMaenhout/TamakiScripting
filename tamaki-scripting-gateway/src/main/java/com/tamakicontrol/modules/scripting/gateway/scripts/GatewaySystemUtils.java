package com.tamakicontrol.modules.scripting.gateway.scripts;

import com.tamakicontrol.modules.scripting.AbstractSystemUtils;
import org.python.core.PyObject;


public class GatewaySystemUtils extends AbstractSystemUtils {
    @Override
    protected Object runAtGatewayImpl(PyObject object) {
        return object.__call__();
    }
}
