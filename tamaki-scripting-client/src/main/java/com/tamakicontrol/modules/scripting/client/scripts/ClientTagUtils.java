package com.tamakicontrol.modules.scripting.client.scripts;

import com.inductiveautomation.ignition.client.model.ClientContext;
import com.inductiveautomation.ignition.client.script.ClientTagUtilities;
import com.inductiveautomation.ignition.common.config.ExtendedPropertySet;
import com.inductiveautomation.ignition.common.config.PropertyValue;
import com.inductiveautomation.ignition.common.expressions.Expression;
import com.inductiveautomation.ignition.common.expressions.ExpressionException;
import com.inductiveautomation.ignition.common.expressions.functions.AbstractFunction;
import com.inductiveautomation.ignition.common.model.values.BasicQualifiedValue;
import com.inductiveautomation.ignition.common.model.values.BasicQuality;
import com.inductiveautomation.ignition.common.model.values.QualifiedValue;
import com.inductiveautomation.ignition.common.model.values.Quality;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataQuality;
import com.inductiveautomation.ignition.common.sqltags.model.types.TagQuality;
import com.tamakicontrol.modules.scripting.AbstractTagUtils;
import com.tamakicontrol.modules.scripting.TagUtilProvider;

import static com.inductiveautomation.ignition.common.sqltags.model.TagProp.Quality;

public class ClientTagUtils extends AbstractTagUtils {

    // Get inductive client tag utilities
    private static ClientTagUtilities clientTagUtilities;

    public ClientTagUtils(){}

    public static synchronized void initialize(ClientContext c){
        if(clientTagUtilities == null)
            clientTagUtilities = new ClientTagUtilities(c);
    }

    @Override
    protected Object getParameterValueImpl(String tagPath, String paramName) {
        ExtendedPropertySet parameters = (ExtendedPropertySet)clientTagUtilities.read(tagPath + ".ExtendedProperties").getValue();

        try {
            for (PropertyValue<?> param : parameters) {
                if (param.getProperty().getName().equals(paramName))
                    return param.getValue();
            }
        }catch(Exception e){
            return null;
        }

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
            try {
                ExtendedPropertySet parameters = (ExtendedPropertySet)clientTagUtilities.read(expressions[0].execute().getValue() + ".ExtendedProperties").getValue();

                for (PropertyValue<?> param : parameters) {
                    if (param.getProperty().getName().equals(expressions[1].execute().getValue()))
                        return new BasicQualifiedValue(param.getValue());
                }
            }catch(Exception e){
                BasicQualifiedValue retValue = new BasicQualifiedValue(-1);
                retValue.setQuality(TagQuality.BAD);
                return retValue;
            }

            return new BasicQualifiedValue(-1);
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
