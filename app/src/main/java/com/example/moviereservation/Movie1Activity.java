package com.example.moviereservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Movie1Activity extends Activity {

    private String username;

    private Button buttonSelect1;
    private Button buttonSelect2;
    private Button buttonSelect3;
    private Button buttonSelect4;
    private Button buttonLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie1);
        Intent intent = getIntent();
        username = intent.getExtras().getString("username");

        buttonSelect1 = (Button) findViewById(R.id.buttonSelect1);
        buttonSelect2 = (Button) findViewById(R.id.buttonSelect2);
        buttonSelect3 = (Button) findViewById(R.id.buttonSelect3);
        buttonSelect4 = (Button) findViewById(R.id.buttonSelect4);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Movie2Activity.class);
                //intent.putExtra("Client", client);
                intent.putExtra("username", username);
                intent.putExtra("movietitle", "러브라이브 더 무비");
                startActivity(intent);
            }
        });

        buttonSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Movie2Activity.class);
                //intent.putExtra("Client", client);
                intent.putExtra("username", username);
                intent.putExtra("movietitle", "봉오동 전투");
                startActivity(intent);
            }
        });

        buttonSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Movie2Activity.class);
                //intent.putExtra("Client", client);
                intent.putExtra("username", username);
                intent.putExtra("movietitle", "명량");
                startActivity(intent);
            }
        });

        buttonSelect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Movie2Activity.class);
                //intent.putExtra("Client", client);
                intent.putExtra("username", username);
                intent.putExtra("movietitle", "어벤져스 엔드게임");
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client.client.sender.turnOnSignal("logout");
            }
        });

        Client.client.receiver.logoutSuccess = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String order = (String) msg.obj;

                if(order.equals("success")){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    //intent.putExtra("Client", client);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}