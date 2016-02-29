package com.tamakicontrol.modules.scripting.client;

import com.inductiveautomation.factorypmi.application.script.builtin.ClientUserUtilities;
import com.inductiveautomation.ignition.client.model.ClientContext;
import com.inductiveautomation.ignition.common.user.User;
import com.tamakicontrol.modules.scripting.SecurityFunctionProvider;

import java.util.Collection;
import java.util.List;

public class SecurityFunctions implements SecurityFunctionProvider {

    private final ClientUserUtilities userUtilities = new ClientUserUtilities();

    @Override
    public boolean hasRole(String role) {

        try {

            User user = userUtilities.getUser("", "");
            for(String userRole : user.getRoles()){
                if(role.equals(userRole))
                    return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean hasRoles(String roles) {

        try{
            User user = userUtilities.getUser("","");

            for(String role : roles.split(",")){
                if(!user.getRoles().contains(role)){
                    return false;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean hasRoles(List<String> roles) {

        try{
            User user = userUtilities.getUser("","");

            for(String role : roles){
                if(!user.getRoles().contains(role)){
                    return false;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
