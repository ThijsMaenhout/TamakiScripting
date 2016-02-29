package com.tamakicontrol.modules.scripting.designer;

import com.inductiveautomation.ignition.common.expressions.ExpressionFunctionManager;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.tamakicontrol.modules.scripting.UtilFunctions;
import com.tamakicontrol.modules.scripting.client.TagFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesignerHook extends AbstractDesignerModuleHook {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private TagFunctions tagFunctions;

    @Override
    public void startup(DesignerContext context, LicenseState activationState) throws Exception {
        super.startup(context, activationState);
        tagFunctions = new TagFunctions(context);
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);
        manager.addScriptModule("system.util", new UtilFunctions(), new PropertiesFileDocProvider());
        manager.addScriptModule("system.tag", tagFunctions, new PropertiesFileDocProvider());
    }

    @Override
    public void configureFunctionFactory(ExpressionFunctionManager factory) {
        super.configureFunctionFactory(factory);
        factory.addFunction("getUUID","Strings", new UtilFunctions.GetUUIDFunction());
        factory.addFunction("getStackTrace","Strings", new UtilFunctions.GetStackTraceFunction());
        factory.addFunction("getParamValue","Advanced", new TagFunctions.GetParameterValueFunction());
    }

}
