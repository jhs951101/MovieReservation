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


public class MainActivity extends Activity {

    private Button buttonConnect;
    private EditText editTextIPAddress;
    private EditText editTextPortNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonConnect = (Button) findViewById(R.id.buttonConnect);
        editTextIPAddress = (EditText) findViewById(R.id.editTextIPAddress);
        editTextPortNumber = (EditText) findViewById(R.id.editTextPortNumber);

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IPAddress = editTextIPAddress.getText().toString();
                String portNumber = editTextPortNumber.getText().toString();

                if(IPAddress == null || IPAddress.equals("")){
                    Toast.makeText(getApplicationContext(), "IP주소를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else if(portNumber == null || portNumber.equals("")){
                    Toast.makeText(getApplicationContext(), "포트번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Client.client.setIPPort(IPAddress, Integer.parseInt(portNumber));
                    Client.client.start();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        /* <Activity 1>
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] array = {"홍길순","김춘추","김유신"};
                Option option = new Option("010xxxxxxxx","서울특별시xxxx");

                Intent intent = new Intent(getApplicationContext(), SubActivity.class);

                intent.putExtra("name","홍길동");
                intent.putExtra("age",20);
                intent.putExtra("array",array);
                intent.putExtra("class",option);

                startActivity(intent);
            }
        });
        */

        /* <Activity 2>
        Intent intent = getIntent();

        String name = intent.getExtras().getString("name");

        int age = intent.getExtras().getInt("age");

        String array[] = intent.getExtras().getStringArray("array");
        String add_array="";
        for(int i=0;i<array.length;i++){
            add_array+=array[i]+",";
        }

        Option option = (Option)intent.getSerializableExtra("class");
        */
    }
}
