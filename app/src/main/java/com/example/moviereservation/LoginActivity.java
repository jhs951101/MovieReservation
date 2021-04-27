package com.example.moviereservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    private Button buttonLogin;
    private Button buttonJoin;
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonJoin = (Button) findViewById(R.id.buttonJoin);
        editTextUsername = (EditText) findViewById(R.id.editTextLoginUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextLoginPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if(username == null || username.equals(""))
                    Toast.makeText(getApplicationContext(), "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else if(password == null || password.equals(""))
                    Toast.makeText(getApplicationContext(), "패스워드를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else
                    Client.client.sender.turnOnSignal("login " + username + " " + password);
            }
        });

        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                //intent.putExtra("Client", client);
                startActivity(intent);
            }
        });

        Client.client.receiver.loginSuccess = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String order = (String) msg.obj;

                if(order.equals("success")){
                    Intent intent = new Intent(getApplicationContext(), Movie1Activity.class);
                    //intent.putExtra("Client", client);
                    intent.putExtra("username", editTextUsername.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 잘못되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        disconnect();
        super.onBackPressed();
    }

    public void disconnect(){
        Client.client.sender.turnOnSignal("disconnect");
        Client.client = new Client();
        Toast.makeText(getApplicationContext(), "접속이 종료되었습니다", Toast.LENGTH_SHORT).show();
    }
}