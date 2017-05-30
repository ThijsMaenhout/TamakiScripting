package com.tamakicontrol.modules.scripting.gateway.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.inductiveautomation.ignition.common.script.builtin.AbstractDBUtilities;
import com.inductiveautomation.ignition.common.script.builtin.DatasetUtilities;
import com.inductiveautomation.ignition.common.sqltags.model.TagPath;
import com.inductiveautomation.ignition.common.sqltags.model.types.TagValue;
import com.inductiveautomation.ignition.common.sqltags.parser.TagPathParser;
import com.inductiveautomation.ignition.gateway.datasource.BasicStreamingDataset;
import com.inductiveautomation.ignition.gateway.util.DBUtilities;
import com.tamakicontrol.modules.scripting.AbstractDatasetUtils;
import com.tamakicontrol.modules.utils.ArgumentMap;
import com.tamakicontrol.modules.utils.JsonHistoryQueryParams;

import org.apache.commons.io.IOUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.GZIPOutputStream;


public class ScriptingResource extends BaseServlet {

    @Override
    public String getUriBase() {
        return "/main/system/api";
    }

    @Override
    public String getAuthSource() {
        return "default";
    }

    @Override
    public void init() throws ServletException {
        super.init();

        addResource("/hello", METHOD_GET, helloWorldResource);
        addResource("/echo", METHOD_POST, echoResource);
        addResource("/tag/queryTagHistory", METHOD_POST, queryTagHistoryResource);
        addResource("/tag/test", METHOD_GET, testResource);
        addResource("/tag/read", METHOD_GET, getReadTagResource);
        addResource("/tag/read", METHOD_POST, postReadTagResource);
        addResource("/tag/readAll", METHOD_POST, readAllTagResource);
        addResource("/tag/write", METHOD_POST, writeTagResource);
        addResource("/tag/writeAll", METHOD_POST, writeAllTagResource);
    }

    private String getSystemName(){
        return getContext().getSystemProperties().getSystemName();
    }

    private ServletResource helloWorldResource = new ServletResource() {
        @Override
        public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {
            resp.setContentType("text/plain");
            resp.getWriter().print("Hello World");
        }
    };

    private ServletResource echoResource = new ServletResource() {
        @Override
        public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {
            resp.setContentType(req.getContentType());
            IOUtils.copy(req.getReader(), resp.getOutputStream());
        }
    };


    private ServletResource testResource = new ServletResource() {
        @Override
        public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {

        }
    };


    //TODO Require fully qualified tag paths or find a way to provide defaults?
    private ServletResource queryTagHistoryResource = new ServletResource() {
        @Override
        public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {
            BufferedReader reader = req.getReader();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            JsonHistoryQueryParams queryParams = gson.fromJson(reader, JsonHistoryQueryParams.class);

            queryParams.setDefaultSystemName(getSystemName());
            queryParams.build();

            logger.debug(queryParams.toString());

            try {
                BasicStreamingDataset data = new BasicStreamingDataset();

                logger.info(data.toString());
                getContext().getTagManager().queryHistory(queryParams, data);


                if(queryParams.getDataFormat().equalsIgnoreCase("json")) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(AbstractDatasetUtils.toJSON(data));
                }else if(queryParams.getDataFormat().equalsIgnoreCase("csv")) {
                    resp.setContentType("text/csv");


                    DatasetUtilities.toCSVJavaStreaming(data, true, false, resp.getWriter(), true);

                    //TODO gzip output?
                    /*
                    GZIPOutputStream gzipOutputStream = new GZIPOutputStream(resp.getOutputStream());
                    Writer writer = new OutputStreamWriter(gzipOutputStream);
                    DatasetUtilities.toCSVJavaStreaming(data, true, false, writer, true);
                    */
                }

            }catch (Exception e){
                logger.error("Error while querying tag history", e);
                resp.sendError(400);
            }

        }
    };

    /*
    *
    * readTagResource
    *
    * Returns a qualified value for a provided tagpath
    *
    * */
    private ServletResource postReadTagResource = new ServletResource() {

        class TagResourceParameters {
            private String tagPath = "";
            private transient TagPath _tagPath = null;

            private void build() throws IOException{
                _tagPath = TagPathParser.parse("default", "Ignition157", tagPath);
            }

            private void setTagPath(String tagPath){
                this.tagPath = tagPath;
            }

            private String getTagPath(){
                return tagPath;
            }

            private TagPath getQualifiedTagPath(){
                return _tagPath;
            }

            @Override
            public String toString() {
                Gson gson = new Gson();
                return gson.toJson(this);
            }
        }

        @Override
        public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {

            if(!validateSecurity(req)){
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Gson gson = new GsonBuilder().create();
            TagResourceParameters params = gson.fromJson(req.getReader(), TagResourceParameters.class);

            try {
                logger.debug(params.toString());
                params.build();

                resp.setContentType("application/json");
                TagValue value = getContext().getTagManager().getTag(params.getQualifiedTagPath()).getValue();
                resp.getWriter().write(gson.toJson(value));
            }catch (IOException e){
                logger.warn("IO Exception while parsing tag path in resource", e);
                resp.sendError(400);
            }

        }
    };


    private ServletResource getReadTagResource = new ServletResource() {
        @Override
        public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {
            ArgumentMap args = getRequestParams(req.getQueryString());
            String tagPath = args.getStringArg("tagPath");
            TagPath path = TagPathParser.parse("default", getSystemName(), tagPath);

            TagValue value = getContext().getTagManager().getTag(path).getValue();

            if (args.getStringArg("dataFormat", "json").equalsIgnoreCase("csv")) {
                resp.setContentType("text/csv");
                resp.getWriter().write(
                        String.format(
                                "_value,_quality,_timestamp\n%s,%s,%s",
                                value.getValue().toString(),
                                value.getQuality().toString(),
                                value.getTimestamp().toString())
                );

                //TODO gzip output?
                //GZIPOutputStream gzipStream = new GZIPOutputStream(resp.getOutputStream());

            } else {
                Gson gson = new Gson();
                resp.setContentType("application/json");
                resp.getWriter().write(gson.toJson(value));
            }

        }
    };

    private ServletResource readAllTagResource = new ServletResource() {
        @Override
        public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {

        }
    };

    private ServletResource writeTagResource = new ServletResource() {
        @Override
        public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {

        }
    };

    private ServletResource writeAllTagResource = new ServletResource() {
        @Override
        public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {

        }
    };

}

