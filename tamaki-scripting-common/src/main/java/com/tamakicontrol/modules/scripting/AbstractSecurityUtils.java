package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.BundleUtil;

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
    public abstract boolean hasRole(String role);

    @Override
    public abstract boolean hasRoles(String roles);

    @Override
    public abstract boolean hasRoles(List<String> roles);

}
