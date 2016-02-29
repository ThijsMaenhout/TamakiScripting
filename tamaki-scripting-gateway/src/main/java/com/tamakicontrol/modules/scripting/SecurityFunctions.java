package com.tamakicontrol.modules.scripting;

import java.util.List;

public class SecurityFunctions implements SecurityFunctionProvider {

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
