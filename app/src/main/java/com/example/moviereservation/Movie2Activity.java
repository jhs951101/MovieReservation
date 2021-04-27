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


public class Movie2Activity extends Activity {

    private String username;
    private String movietitle;

    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextLocation;
    private EditText editTextSeatID;
    private Button buttonReservation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie2);
        Intent intent = getIntent();
        username = intent.getExtras().getString("username");
        movietitle = intent.getExtras().getString("movietitle");

        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTime = (EditText) findViewById(R.id.editTextTime);
        editTextLocation = (EditText) findViewById(R.id.editTextLocation);
        editTextSeatID = (EditText) findViewById(R.id.editTextSeatID);
        buttonReservation = (Button) findViewById(R.id.buttonReservation);

        buttonReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = editTextDate.getText().toString();
                String timeSlot = editTextTime.getText().toString();
                String location = editTextLocation.getText().toString();
                String seatID = editTextSeatID.getText().toString();

                if(date == null || date.equals(""))
                    Toast.makeText(getApplicationContext(), "날짜를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else if(timeSlot == null || timeSlot.equals(""))
                    Toast.makeText(getApplicationContext(), "시간대를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else if(location == null || location.equals(""))
                    Toast.makeText(getApplicationContext(), "장소를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else if(seatID == null || seatID.equals(""))
                    Toast.makeText(getApplicationContext(), "좌석번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                else
                    Client.client.sender.turnOnSignal("reservation " + username + " " + movietitle + " " + date + " " + timeSlot + " " + location + " " + seatID);
            }
        });

        Client.client.receiver.reservationSuccess = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String order = (String) msg.obj;

                if(order.equals("success")){
                    Toast.makeText(getApplicationContext(), "정상적으로 예매 되었습니다", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(getApplicationContext(), Movie1Activity.class);
                    //intent.putExtra("username", username);
                    //startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "예매 실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}