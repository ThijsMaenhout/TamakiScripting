package com.tamakicontrol.modules.scripting.gateway;

import com.inductiveautomation.ignition.common.expressions.ExpressionFunctionManager;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.gateway.clientcomm.ClientReqSession;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.tamakicontrol.modules.scripting.SystemUtils;
import com.tamakicontrol.modules.scripting.gateway.scripts.GatewayDBFunctions;
import com.tamakicontrol.modules.scripting.gateway.scripts.GatewaySecurityFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayHook extends AbstractGatewayModuleHook {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    GatewayContext gatewayContext;
    GatewayDBFunctions dbFunctions;

    @Override
    public void setup(GatewayContext gatewayContext) {
        this.gatewayContext = gatewayContext;
        dbFunctions = new GatewayDBFunctions(gatewayContext);
    }

    @Override
    public void startup(LicenseState licenseState) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);
        manager.addScriptModule("system.util", new SystemUtils(), new PropertiesFileDocProvider());
        manager.addScriptModule("system.user", new GatewaySecurityFunctions(), new PropertiesFileDocProvider());
        manager.addScriptModule("system.db", dbFunctions, new PropertiesFileDocProvider());
    }

    @Override
    public void configureFunctionFactory(ExpressionFunctionManager factory) {
        super.configureFunctionFactory(factory);
        factory.addFunction("getUUID","Strings", new SystemUtils.GetUUIDFunction());
        factory.addFunction("getStackTrace","Strings", new SystemUtils.GetStackTraceFunction());
    }

    @Override
    public Object getRPCHandler(ClientReqSession session, Long projectId) {
        return dbFunctions;
    }
}
