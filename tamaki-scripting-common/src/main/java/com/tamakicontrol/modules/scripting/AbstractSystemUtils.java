package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.expressions.Expression;
import com.inductiveautomation.ignition.common.expressions.ExpressionException;
import com.inductiveautomation.ignition.common.expressions.functions.AbstractFunction;
import com.inductiveautomation.ignition.common.model.values.BasicQualifiedValue;
import com.inductiveautomation.ignition.common.model.values.QualifiedValue;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;

import java.util.UUID;

public abstract class AbstractSystemUtils implements SystemUtilProvider {

    static {
        BundleUtil.get().addBundle(
                AbstractSystemUtils.class.getSimpleName(),
                AbstractSystemUtils.class.getClassLoader(),
                AbstractSystemUtils.class.getName().replace('.', '/')
        );
    }

    /**
     * Returns a universally unique identifier from java.util.UUID
     *
     * @return String - Universally unique identifier
     */
    @ScriptFunction(docBundlePrefix = "SystemUtils")
    public String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static class GetUUIDFunction extends AbstractFunction{

        @Override
        protected String getFunctionDisplayName() {
            return "getUUID";
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
            return new BasicQualifiedValue(UUID.randomUUID().toString());
        }

    }

    /**
     * Returns the stack trace for the currently running thread
     *
     * @return String - Java stack trace for the currently running thread
     */
    @ScriptFunction(docBundlePrefix = "SystemUtils")
    public String getStackTrace(){
        return Thread.currentThread().getStackTrace()[0].toString();
    }

    public static class GetStackTraceFunction extends AbstractFunction{

        @Override
        protected String getFunctionDisplayName() {
            return "getUUID";
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
            return new BasicQualifiedValue(Thread.currentThread().getStackTrace()[0].toString());
        }

    }

}

