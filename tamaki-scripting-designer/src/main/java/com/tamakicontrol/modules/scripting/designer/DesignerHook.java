package com.tamakicontrol.modules.scripting.designer;

import com.inductiveautomation.ignition.common.expressions.ExpressionFunctionManager;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;

import com.tamakicontrol.modules.scripting.AbstractSystemUtils;
import com.tamakicontrol.modules.scripting.client.scripts.ClientTagUtils;
import com.tamakicontrol.modules.scripting.client.scripts.ClientDBUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesignerHook extends AbstractDesignerModuleHook {

    private final Logger logger = LoggerFactory.getLogger("Tamaki Scripting");
    private DesignerContext designerContext;

    @Override
    public void startup(DesignerContext context, LicenseState activationState) throws Exception {
        super.startup(context, activationState);
        designerContext = context;
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);
        manager.addScriptModule("system.util", new AbstractSystemUtils(), new PropertiesFileDocProvider());
        manager.addScriptModule("system.tag",  new ClientTagUtils(designerContext), new PropertiesFileDocProvider());
        manager.addScriptModule("system.db", new ClientDBUtils(), new PropertiesFileDocProvider());
    }

    @Override
    public void configureFunctionFactory(ExpressionFunctionManager factory) {
        super.configureFunctionFactory(factory);
        factory.addFunction("getUUID","Strings", new AbstractSystemUtils.GetUUIDFunction());
        factory.addFunction("getStackTrace","Strings", new AbstractSystemUtils.GetStackTraceFunction());
        factory.addFunction("getParamValue","Advanced", new ClientTagUtils.GetParameterValueFunction());
    }

}
