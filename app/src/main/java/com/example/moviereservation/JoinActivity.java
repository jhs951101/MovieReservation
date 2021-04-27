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

public class JoinActivity extends Activity {

    private Button buttonJoining;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Intent intent = getIntent();

        buttonJoining = (Button) findViewById(R.id.buttonJoining);
        editTextUsername = (EditText) findViewById(R.id.editTextJoinUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextJoinPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextJoinEmail);

        buttonJoining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String email = editTextEmail.getText().toString();

                if(username == null || username.equals(""))
                    Toast.makeText(getApplicationContext(), "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else if(password == null || password.equals(""))
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else if(email == null || email.equals(""))
                    Toast.makeText(getApplicationContext(), "이메일을 입력해 주세요", Toast.LENGTH_SHORT).show();
                else
                    Client.client.sender.turnOnSignal("join " + username + " " + password + " " + email);
            }
        });

        Client.client.receiver.joinSuccess = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String order = (String) msg.obj;

                if(order.equals("success")){
                    Toast.makeText(getApplicationContext(), "정상적으로 가입 되었습니다", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    //startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}