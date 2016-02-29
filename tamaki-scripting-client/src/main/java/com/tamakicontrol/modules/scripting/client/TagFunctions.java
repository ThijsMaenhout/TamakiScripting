package com.tamakicontrol.modules.scripting.client;

import com.inductiveautomation.ignition.client.model.ClientContext;
import com.inductiveautomation.ignition.client.script.ClientTagUtilities;
import com.inductiveautomation.ignition.common.config.ExtendedPropertySet;
import com.inductiveautomation.ignition.common.config.PropertyValue;
import com.inductiveautomation.ignition.common.expressions.Expression;
import com.inductiveautomation.ignition.common.expressions.ExpressionException;
import com.inductiveautomation.ignition.common.expressions.functions.AbstractFunction;
import com.inductiveautomation.ignition.common.model.values.BasicQualifiedValue;
import com.inductiveautomation.ignition.common.model.values.QualifiedValue;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;
import com.tamakicontrol.modules.scripting.TagFunctionProvider;

import java.util.List;

public class TagFunctions implements TagFunctionProvider {

    private static ClientTagUtilities clientTagUtilities;

    public TagFunctions(ClientContext c){
        if(clientTagUtilities == null)
            clientTagUtilities = new ClientTagUtilities(c.getTagManager());
    }

    @Override
    @ScriptFunction(docBundlePrefix = "TagUtils")
    public QualifiedValue getParameterValue(String tagPath, String paramName) {
        ExtendedPropertySet parameters = (ExtendedPropertySet)clientTagUtilities.read(tagPath + ".ExtendedProperties").getValue();
        System.out.println(parameters.getCount() + " Parameters Found");
        for(PropertyValue<?> param : parameters){
            System.out.println(param.getProperty().getName());
            if(param.getProperty().getName().equals(paramName))
                return new BasicQualifiedValue(param.getValue());
        }

        return null;
    }

    @Override
    public List<String> getHistoricalTags(String provider, String tagPath) {
        return null;
    }

    public static class GetParameterValueFunction extends AbstractFunction {

        @Override
        protected String getFunctionDisplayName() {
            return "getParamValue";
        }

        @Override
        public String getArgDocString() {
            return "";
        }

        @Override
        public Class<?> getType() {
            return Object.class;
        }

        @Override
        public QualifiedValue execute(Expression[] expressions) throws ExpressionException {
            ExtendedPropertySet parameters = (ExtendedPropertySet)clientTagUtilities.read(expressions[0].execute().getValue() + ".ExtendedProperties").getValue();
            System.out.println(parameters.getCount() + " Parameters Found");
            for(PropertyValue<?> param : parameters){
                System.out.println(param.getProperty().getName());
                if(param.getProperty().getName().equals(expressions[1].execute().getValue()))
                    return new BasicQualifiedValue(param.getValue());
            }

            return null;
        }

        @Override
        protected boolean validateNumArgs(int num) {
            return num == 2;
        }

        @Override
        protected boolean validateArgType(int argNum, Class<?> type) {

            switch (argNum){
                case 0:
                    return type.equals(String.class);
                case 1:
                    return type.equals(String.class);
                default:
                    return false;
            }

        }
    }

}
