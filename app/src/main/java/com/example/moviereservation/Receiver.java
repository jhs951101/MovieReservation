package com.example.moviereservation;

import android.os.Handler;
import android.os.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Receiver extends Thread  {

    private Socket socket;
    private DataInputStream in;

    public Handler loginSuccess;
    public Handler joinSuccess;
    public Handler reservationSuccess;
    public Handler logoutSuccess;

    public void setSocket(Socket socket){
        try {
            this.socket = socket;
            loginSuccess = null;
            joinSuccess = null;
            reservationSuccess = null;
            logoutSuccess = null;
            in = new DataInputStream(socket.getInputStream());
        } catch(Exception e) {
            e.printStackTrace();
            closeAll();
        }
    }

    public void closeAll() {
        try {
            if(socket != null)
                socket.close();
            if(in != null)
                in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            String[] orderinfo;  // orderinfo[]: 명령문을 띄어쓰기를 기준으로 분리해서 저장하기 위한 배열

            while(in != null) {
                byte[] bytes = new byte[1024];
                in.read(bytes, 0, 1024);
                String str = new String(bytes, "UTF-8");
                str = str.split("\n")[0];
                orderinfo = str.split(" ");

                Message message = new Message();
                message.obj = orderinfo[1];

                if(orderinfo[0].equals("login")){
                    loginSuccess.sendMessage(message);
                }
                else if(orderinfo[0].equals("join")){
                    joinSuccess.sendMessage(message);
                }
                else if(orderinfo[0].equals("reservation")){
                    reservationSuccess.sendMessage(message);
                }
                else if(orderinfo[0].equals("logout")){
                    logoutSuccess.sendMessage(message);
                }
                else if(orderinfo[0].equals("disconnect")){
                    break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }
}
