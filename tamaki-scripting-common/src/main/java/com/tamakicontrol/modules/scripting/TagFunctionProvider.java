package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.model.values.QualifiedValue;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;

import java.util.List;

public interface TagFunctionProvider {

    public QualifiedValue getParameterValue(String tagPath, String paramName);

    public List<String> getHistoricalTags(String provider, String tagPath);

}
