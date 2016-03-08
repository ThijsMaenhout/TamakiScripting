package com.tamakicontrol.modules.scripting.gateway.scripts;

import com.inductiveautomation.ignition.gateway.localdb.DBInterface;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;

import com.tamakicontrol.modules.scripting.AbstractDBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class GatewayDBUtils extends AbstractDBUtils {

    private final Logger logger = LoggerFactory.getLogger("Plant Replay");
    private GatewayContext context;

    public GatewayDBUtils(GatewayContext context) {
        this.context = context;
    }

    @Override
    public List<List<Object>> runInternalQueryImpl(String query) {
        DBInterface internalDBInterface = context.getPersistenceInterface().getSession().getDBInterface();
        List<List<Object>> jData;

        try {
            jData = internalDBInterface.runQuery(query);
        }catch (SQLException e){
            logger.error(e.getStackTrace().toString());
            return null;
        }

        for(int i=0; i < jData.size(); i++){
            for(int j=0; j < jData.size(); j++){
                logger.info(jData.get(i).get(j).toString());
            }
        }

        return jData;
    }

    @Override
    public List<List<Object>> runPrepInternalQueryImpl(String query, Object[] args){
        return null;
    }

}
