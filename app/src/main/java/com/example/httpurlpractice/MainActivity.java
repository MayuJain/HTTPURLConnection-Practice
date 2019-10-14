package com.example.httpurlpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()){
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    Request req = new Request();
                    req.addParameters("name","Mayuri")
                            .addParameters("age","25")
                            .addParameters("address", "charlotte");
                    new getDataAsync(req).execute("http://api.theappsdr.com/params.php");
                }else{
                    Toast.makeText(MainActivity.this, "noT Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if(ni != null && ni.isConnected()){
            return true;
        }
        return false;
    }

    private class getDataAsync extends AsyncTask<String, Void, String>{
        Request mparams;

        public getDataAsync(Request params){
            mparams = params;
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection conn = null;
            BufferedReader bf = null;
            try {
                URL url = new URL(mparams.getEncodedURL(params[0]));
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                mparams.getPostParam(conn);
                conn.connect();
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line ="";
                    while((line = bf.readLine())!= null){
                        sb.append(line);
                    }
                    return sb.toString();
                }
            return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (bf != null) {
                    try {
                        bf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result != null){
                Log.d("demo", result);
            }
        }
    }
}
