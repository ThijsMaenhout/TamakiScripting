package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;

import java.util.List;

public abstract class AbstractSecurityUtils implements SecurityUtilProvider {

    static {
        BundleUtil.get().addBundle(
                AbstractSecurityUtils.class.getSimpleName(),
                AbstractSecurityUtils.class.getClassLoader(),
                AbstractSecurityUtils.class.getName().replace('.', '/')
        );
    }

    @Override
    @ScriptFunction(docBundlePrefix = "SecurityUtils")
    public abstract boolean hasRole(String role);

    @Override
    @ScriptFunction(docBundlePrefix = "SecurityUtils")
    public abstract boolean hasRoles(String roles);

    @Override
    @ScriptFunction(docBundlePrefix = "SecurityUtils")
    public abstract boolean hasRoles(List<String> roles);

}
