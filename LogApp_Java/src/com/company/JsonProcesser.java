package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonProcesser implements HttpHandler {


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        //read the request
        InputStream inputStream = httpExchange.getRequestBody();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer stringBuffer = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            stringBuffer.append(inputLine);
        }

        JSONArray json = parseJson(stringBuffer.toString());
        if(json == null)
        {
            sendErrorCode(httpExchange, "Syntax error");
            return;
        }

        //convert json array from user to GPB
        List<UserObject> userObjects = getListOfJSONObjects(json);
        GPBProcesser gpbProcesser = new GPBProcesser();
        boolean success = gpbProcesser.convertJSONToGPB(userObjects);

        String response = "OK";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());

        in.close();
        os.close();

        //notify C++ code in case a new JSON object comes
        if(success)
        {
            gpbProcesser.sendEventsToCpp();
        }
        else
        {
            sendErrorCode(httpExchange, "GPB did not succeed");
        }
    }

    private List<UserObject> getListOfJSONObjects(JSONArray jsonArray) {
        List<UserObject> userObjects = new ArrayList<>();

        for (Object object :
                jsonArray) {
            JSONObject json = (JSONObject) object;
            //we already have a correct syntax
            UserObject userObject = new UserObject((long) json.get("timestamp"), (long) json.get("userid"), (String) json.get("message"));
            userObjects.add(userObject);
        }

        return  userObjects;
    }

    private void sendErrorCode(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(400, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
    }

    private JSONArray parseJson(String toParse) {
        JSONArray json = null;
        JSONParser parser = new JSONParser();
        try {
            json = (JSONArray) parser.parse(toParse);
            return json;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static List<UserObject> getDummyElements()
    {
        List<UserObject> dummyObjects = new ArrayList<>();

        for(int i = 0; i < 10;++i)
        {
            UserObject userObject = new UserObject(System.currentTimeMillis(), 1, "Error reported: " + i);
            dummyObjects.add(userObject);
        }

        return dummyObjects;
    }

    //Demo to call POST calls
    // HTTP POST request
    public static void demoSendPost() {

        //create dummy request posts
        List<UserObject> jsonObjects = getDummyElements();

        JSONArray jsonArray = new JSONArray();
        jsonObjects.stream().forEach(element ->
        {
            JSONObject json = new JSONObject();
            json.put("timestamp", element.getTimestamp());
            json.put("userid", element.getUserId());
            json.put("message", element.getMessage());
            jsonArray.add(json);
        });

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            StringEntity entity = new StringEntity(jsonArray.toJSONString());
            entity.setContentType("application/json");
            HttpPost request = new HttpPost("http://localhost:8081/requests");
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept","application/json");
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);

            //handle response here...
            System.out.println(response.getStatusLine().getStatusCode());

        } catch (Exception ex) {
            // handle exception here
            ex.printStackTrace();
        } finally {
        }
    }
}
