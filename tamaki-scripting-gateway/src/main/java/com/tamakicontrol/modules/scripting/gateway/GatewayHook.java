package com.tamakicontrol.modules.scripting.gateway;

import com.inductiveautomation.ignition.common.expressions.ExpressionFunctionManager;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.gateway.clientcomm.ClientReqSession;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.tamakicontrol.modules.scripting.AbstractSystemUtils;
import com.tamakicontrol.modules.scripting.TamakiTaskQueue;
import com.tamakicontrol.modules.scripting.gateway.scripts.GatewayDBUtils;
import com.tamakicontrol.modules.scripting.gateway.scripts.GatewayPDFUtils;
import com.tamakicontrol.modules.scripting.gateway.scripts.GatewaySecurityUtils;
import com.tamakicontrol.modules.scripting.gateway.scripts.GatewaySystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayHook extends AbstractGatewayModuleHook {

    private final Logger logger = LoggerFactory.getLogger("Tamaki Scripts");

    private GatewayDBUtils dbUtils;
    private GatewaySecurityUtils securityUtils;
    private GatewaySystemUtils systemUtils;
    private GatewayPDFUtils pdfUtils;

    @Override
    public void setup(GatewayContext gatewayContext) {
        dbUtils = new GatewayDBUtils(gatewayContext);
        securityUtils = new GatewaySecurityUtils();
        systemUtils = new GatewaySystemUtils();
        pdfUtils = new GatewayPDFUtils();

        TamakiTaskQueue.initialize();
    }

    @Override
    public void startup(LicenseState licenseState) {
        logger.info("Loading Tamaki Scripts Module");
    }

    @Override
    public void shutdown() {
        logger.info("Stopping Tamaki Scripts Module");
    }

    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);
        manager.addScriptModule("system.util", systemUtils, new PropertiesFileDocProvider());
        manager.addScriptModule("system.user", securityUtils, new PropertiesFileDocProvider());
        manager.addScriptModule("system.db", dbUtils, new PropertiesFileDocProvider());
        manager.addScriptModule("system.pdf", pdfUtils, new PropertiesFileDocProvider());
    }

    @Override
    public void configureFunctionFactory(ExpressionFunctionManager factory) {
        super.configureFunctionFactory(factory);
        factory.addFunction("getUUID","Strings", new AbstractSystemUtils.GetUUIDFunction());
        factory.addFunction("getStackTrace","Strings", new AbstractSystemUtils.GetStackTraceFunction());
    }

    @Override
    public Object getRPCHandler(ClientReqSession session, Long projectId) {
        return dbUtils;
    }
}
