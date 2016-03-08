package com.tamakicontrol.modules.scripting.designer;

import com.inductiveautomation.ignition.common.expressions.ExpressionFunctionManager;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.tamakicontrol.modules.scripting.SystemUtils;
import com.tamakicontrol.modules.scripting.client.scripts.ClientTagUtils;
import com.tamakicontrol.modules.scripting.client.scripts.ClientDBUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesignerHook extends AbstractDesignerModuleHook {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ClientTagUtils clientTagUtils;

    @Override
    public void startup(DesignerContext context, LicenseState activationState) throws Exception {
        super.startup(context, activationState);
        clientTagUtils = new ClientTagUtils(context);
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);
        manager.addScriptModule("system.util", new SystemUtils(), new PropertiesFileDocProvider());
        manager.addScriptModule("system.tag", clientTagUtils, new PropertiesFileDocProvider());
        manager.addScriptModule("system.db", new ClientDBUtilities(), new PropertiesFileDocProvider());
    }

    @Override
    public void configureFunctionFactory(ExpressionFunctionManager factory) {
        super.configureFunctionFactory(factory);
        factory.addFunction("getUUID","Strings", new SystemUtils.GetUUIDFunction());
        factory.addFunction("getStackTrace","Strings", new SystemUtils.GetStackTraceFunction());
        factory.addFunction("getParamValue","Advanced", new ClientTagUtils.GetParameterValueFunction());
    }

}
