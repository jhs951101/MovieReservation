package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

import database.DBOperation;
import model.Member;
import model.Model;
import model.Reservation;

public class MovieReservationServer extends Thread {
	
	private static final int PORTNUMBER = 1234;
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private DBOperation dbService;
	
	public MovieReservationServer(Socket socket) {
		try {
			this.socket = socket;
			dbService = new DBOperation();
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			closeAll();
		}
	}
	
	public void run() {
		try {
			String order = "";
			String[] orderInfo;
			boolean available = true;
			
			while(available) {
				byte[] bytes = new byte[1024];
	        	in.read(bytes, 0, 1024);
	        	String str = new String(bytes, "UTF-8");
	        	str = str.split("\n")[0];
	        	orderInfo = str.split(" ");
	        	
	        	//System.out.println(str);
	        	
	        	if(orderInfo[0].equals("login")) {
	        		Member member = (Member) dbService.select("member", orderInfo[1]);
	        		
	        		if(member == null)
	        			order = orderInfo[0] + " fail" + "\n";
	        		else if(!member.getPassword().equals(orderInfo[2])) 
	        			order = orderInfo[0] + " fail" + "\n";
	        		else 
	        			order = orderInfo[0] + " success" + "\n";
	        	}
				else if(orderInfo[0].equals("join") || orderInfo[0].equals("reservation")) {
					Model model = null;
					
					if(orderInfo[0].equals("join")){  // username password email
						model = new Member(orderInfo[1], orderInfo[2], orderInfo[3]);
					}
					else if(orderInfo[0].equals("reservation")){  // username movietitle date timeslot location seatID
						model = new Reservation(orderInfo[1], orderInfo[2], orderInfo[3], orderInfo[4], orderInfo[5], orderInfo[6]);
					}
					
					if(dbService.insert(model)){
						order = orderInfo[0] + " success" + "\n";

						if(orderInfo[0].equals("reservation")){
							SendEmail sendEmail = new SendEmail();

							Member member = (Member) dbService.select("member", orderInfo[1]);
							String content = "안녕하세요. 영화 예매가 완료되었습니다! \n\n아이디: " + orderInfo[1] + "\n" + "영화제목: " + orderInfo[2]
								+ "\n" + "날짜: " + orderInfo[3] + "\n" + "시간: " + orderInfo[4] + "\n" + "장소: " + orderInfo[2] + "\n"
								+ "좌석번호: " + orderInfo[2] + "\n\n감사합니다! ^0^";

							sendEmail.sendTo(member.getEmail(), "예매가 완료되었습니다!", content);
						}
					}
					else{
						order = orderInfo[0] + " fail" + "\n";
					}
				}
				else if(orderInfo[0].equals("logout") || orderInfo[0].equals("disconnect")) {
	        		order = orderInfo[0] + " \n";
					
					if(orderInfo[0].equals("disconnect"))
						available = false;
	        	}
	        	
	        	out.write(order.getBytes("UTF-8"));
                out.flush();
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			closeAll();
			System.out.println("\nDisconnected");
        }
	}
	
	public void closeAll() {
		try {
			if(socket != null)
				socket.close();
			if(in != null)
				in.close();
			if(out != null)
				out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		ServerSocket serverSocket = null;
		
		try {
			System.out.println("Server Started");
			System.out.println("IP Address: " + Inet4Address.getLocalHost().getHostAddress());
			System.out.println("Port Number: " + PORTNUMBER);
			
			serverSocket = new ServerSocket(MovieReservationServer.PORTNUMBER);
			
			while(true) {
				Socket socket = serverSocket.accept();
				MovieReservationServer m = new MovieReservationServer(socket);
				m.start();
				System.out.println("\nA User Accessed!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null);
					serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
