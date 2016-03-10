package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.script.hints.ScriptArg;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;

public abstract class AbstractTagUtils implements TagUtilProvider{

    static {
        BundleUtil.get().addBundle(
                AbstractSystemUtils.class.getSimpleName(),
                AbstractSystemUtils.class.getClassLoader(),
                AbstractSystemUtils.class.getName().replace('.', '/')
        );
    }

    @Override
    @ScriptFunction(docBundlePrefix = "TagUtils")
    public Object getParameterValue(
            @ScriptArg("tagPath") String tagPath,
            @ScriptArg("paramName") String paramName
    ){
        return getParameterValueImpl(tagPath, paramName);
    }

    protected abstract Object getParameterValueImpl(String tagPath, String paramName);

}
