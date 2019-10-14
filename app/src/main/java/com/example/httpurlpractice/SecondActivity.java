package com.example.httpurlpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetAsyncData(SecondActivity.this).execute("http://dev.theappsdr.com/apis/photos/keywords.php");
            }
        });
    }

    public void handleData(LinkedList<String> data){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Options")
                .setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
    }
}
