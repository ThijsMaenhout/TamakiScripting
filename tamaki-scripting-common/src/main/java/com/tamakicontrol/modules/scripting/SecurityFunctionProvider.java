package com.tamakicontrol.modules.scripting;

import java.util.List;

public interface SecurityFunctionProvider {

    public boolean hasRole(String role);

    public boolean hasRoles(String roles);

    public boolean hasRoles(List<String> roles);

    //public void resetPassword(String username);

}
