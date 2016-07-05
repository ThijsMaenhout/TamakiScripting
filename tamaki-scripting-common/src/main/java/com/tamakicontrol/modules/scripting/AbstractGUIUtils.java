package com.tamakicontrol.modules.scripting;


import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;

import java.awt.event.ActionEvent;

public abstract class AbstractGUIUtils implements GUIUtilProvider{

    static {
        BundleUtil.get().addBundle(
                AbstractGUIUtils.class.getSimpleName(),
                AbstractGUIUtils.class.getClassLoader(),
                AbstractGUIUtils.class.getName().replace('.', '/')
        );
    }

    @Override
    @ScriptFunction(docBundlePrefix = "GUIUtils")
    public String openImageChooser(ActionEvent event) {
        return openImageChooserImpl(event);
    }

    protected abstract String openImageChooserImpl(ActionEvent event);

}
