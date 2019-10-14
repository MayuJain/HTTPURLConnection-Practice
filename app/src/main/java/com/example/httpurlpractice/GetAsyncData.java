package com.example.httpurlpractice;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class GetAsyncData extends AsyncTask<String, Void, LinkedList<String>> {
    SecondActivity secondActivity;

    public GetAsyncData(SecondActivity activity){
        this.secondActivity = activity;
    }

    @Override
    protected LinkedList<String> doInBackground(String... strings) {

        StringBuilder stringBuilder;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            String data = null;
            LinkedList<String> list = new LinkedList<>();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                data = IOUtils.toString(conn.getInputStream(),"UTF-8");
            }

            for(String x: data.split(";")){
                list.add(x);
            }
            return list;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<String> list) {
        secondActivity.handleData(list);
    }

}
