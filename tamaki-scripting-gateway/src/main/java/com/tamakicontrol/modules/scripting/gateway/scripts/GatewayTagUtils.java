package com.tamakicontrol.modules.scripting.gateway.scripts;

import com.inductiveautomation.ignition.common.config.ExtendedPropertySet;
import com.inductiveautomation.ignition.common.config.PropertyValue;
import com.inductiveautomation.ignition.common.model.values.QualifiedValue;
import com.inductiveautomation.ignition.common.sqltags.model.TagPath;
import com.inductiveautomation.ignition.common.sqltags.parser.TagPathParser;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.tamakicontrol.modules.scripting.AbstractTagUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GatewayTagUtils extends AbstractTagUtils {

    private GatewayContext context;
    private final Logger logger = LoggerFactory.getLogger("Tamaki Scripting");

    public GatewayTagUtils(GatewayContext gatewayContext){
        this.context = gatewayContext;
    }

    @Override
    protected Object getParameterValueImpl(String tagPath, String paramName) {
        List<TagPath> tagPaths = new ArrayList<>();
        tagPath = String.format("%s.ExtendedProperties", tagPath );
        try {
            tagPaths.add(TagPathParser.parse(tagPath));
            List<QualifiedValue> values = context.getTagManager().read(tagPaths);

            if(values.size() > 1) {
                ExtendedPropertySet parameters = (ExtendedPropertySet)values.get(0).getValue();
                for (PropertyValue<?> param : parameters) {
                    if (param.getProperty().getName().equals(paramName))
                        return param.getValue();
                }
            }

        }catch (IOException e){
            logger.error(String.format("Error while parsing tagpath: %s", tagPath ), e);
        }catch (ClassCastException e){
            logger.error(String.format("Error while casting properties for tagpath: %s", tagPath ), e);
        }

        return null;
    }

}
