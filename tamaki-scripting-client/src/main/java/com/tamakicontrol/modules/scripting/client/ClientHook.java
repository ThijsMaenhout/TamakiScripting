package com.tamakicontrol.modules.scripting.client;

import com.inductiveautomation.ignition.client.model.ClientContext;
import com.inductiveautomation.ignition.client.script.ClientTagUtilities;
import com.inductiveautomation.ignition.common.expressions.ExpressionFunctionManager;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.vision.api.client.AbstractClientModuleHook;
import com.tamakicontrol.modules.scripting.AbstractSystemUtils;

import com.tamakicontrol.modules.scripting.TamakiTaskQueue;
import com.tamakicontrol.modules.scripting.client.scripts.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHook extends AbstractClientModuleHook {

    private final Logger logger = LoggerFactory.getLogger("Tamaki Scripting");

    private ClientDBUtils dbUtils;
    private ClientSecurityUtils securityUtils;
    private ClientSystemUtils systemUtils;
    private ClientTagUtils tagUtils;
    private ClientGUIUtils guiUtils;
    private ClientPDFUtils pdfUtils;

    @Override
    public void startup(ClientContext context, LicenseState activationState) throws Exception {
        super.startup(context, activationState);
        logger.info("Initializing Tamaki Scripting Module");

        dbUtils = new ClientDBUtils();
        securityUtils = new ClientSecurityUtils();
        systemUtils = new ClientSystemUtils();
        tagUtils = new ClientTagUtils(context);
        guiUtils = new ClientGUIUtils();
        pdfUtils = new ClientPDFUtils();

        TamakiTaskQueue.initialize();
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);
        manager.addScriptModule("system.util", systemUtils, new PropertiesFileDocProvider());
        manager.addScriptModule("system.security", securityUtils, new PropertiesFileDocProvider());
        manager.addScriptModule("system.tag", tagUtils, new PropertiesFileDocProvider());
        manager.addScriptModule("system.db", dbUtils, new PropertiesFileDocProvider());
        manager.addScriptModule("system.gui", guiUtils, new PropertiesFileDocProvider());
        manager.addScriptModule("system.pdf", pdfUtils, new PropertiesFileDocProvider());
    }

    @Override
    public void configureFunctionFactory(ExpressionFunctionManager factory) {
        super.configureFunctionFactory(factory);
        factory.addFunction("getUUID","Strings", new AbstractSystemUtils.GetUUIDFunction());
        factory.addFunction("getStackTrace","Strings", new AbstractSystemUtils.GetStackTraceFunction());
        factory.addFunction("getParamValue","Advanced", new ClientTagUtils.GetParameterValueFunction());
    }

}
