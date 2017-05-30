package com.tamakicontrol.modules.scripting;

import com.inductiveautomation.ignition.common.Dataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AbstractDatasetUtils {

    private static final Logger logger = LoggerFactory.getLogger("Tamaki Dataset Functions");

    public static String toJSON(Dataset data){

        List<String> columns = data.getColumnNames();

        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < data.getRowCount(); i++) {
                JSONObject jsonObject = new JSONObject();

                for (String column : columns) {
                    jsonObject.put(column, data.getValueAt(i, column));
                }

                jsonArray.put(jsonObject);
            }

            return jsonArray.toString();

        }catch (JSONException e){
            logger.error("Error while exporting json data", e);
        }

        return "{[]}";
    }

}
