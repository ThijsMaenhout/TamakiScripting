package com.tamakicontrol.modules.scripting;

import java.util.List;

public interface SecurityUtilProvider {

    boolean hasRole(String role);

    boolean hasRoles(String roles);

    boolean hasRoles(List<String> roles);

}
