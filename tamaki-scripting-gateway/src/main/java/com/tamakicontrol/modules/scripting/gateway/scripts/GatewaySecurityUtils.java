package com.tamakicontrol.modules.scripting.gateway.scripts;

import com.tamakicontrol.modules.scripting.SecurityUtilProvider;

import java.util.List;

public class GatewaySecurityUtils implements SecurityUtilProvider {

    @Override
    public boolean hasRole(String role) {
        return false;
    }

    @Override
    public boolean hasRoles(String roles) {
        return false;
    }

    @Override
    public boolean hasRoles(List<String> roles) {
        return false;
    }

}
