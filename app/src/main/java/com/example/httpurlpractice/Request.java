package com.example.httpurlpractice;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class Request {

    private HashMap<String, String> params;
    private StringBuilder stringBuilder;

    public Request(){
        params = new HashMap<>();
        stringBuilder = new StringBuilder();
    }

    public Request addParameters(String key, String value){
        try {
            params.put(key, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getEncodedParameters(){
        for (String key:params.keySet()) {
            if(stringBuilder.length() == 0){
                stringBuilder.append(key+"="+params.get(key));
            }else{
                stringBuilder.append("&"+key+"="+params.get(key));
            }

        }
        return stringBuilder.toString();
    }

    public String getEncodedURL(String url){
        return url+"?"+getEncodedParameters();
    }

    public void getPostParam(HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(getEncodedParameters());
        writer.flush();
    }
}
