package javanetwork.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient {
	
	private String userName;
	
	// 비지니스 로직 처리
	public void clientStart() {
		Scanner scan = new Scanner(System.in);
		System.out.print("대화명: " );
		userName = scan.next();
		
		Socket socket = null;
		try {
			String serverIp = "127.0.0.1";
			socket = new Socket(serverIp, 7777);
			
			System.out.println("서버에 연결되었습니다.");
			
			// 송신용 쓰레드 생성
			ClientSender sender = new ClientSender(socket, userName);
			
			// 수신용 쓰레드 생성
			ClientReceiver receiver = new ClientReceiver(socket);
			
			// 쓰레드 실행
			sender.start();
			receiver.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MultiChatClient().clientStart();
	}
	
	// 송신용 Thread 클래스 정의
	class ClientSender extends Thread{
		Socket socket;
		DataOutputStream dout;
		String name;
		Scanner scan = new Scanner(System.in);
		
		public ClientSender(Socket socket, String name) {
			this.name = name;
			this.socket = socket;
			try {
				dout = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				// 시작하자 마자 자신의 대화명을 서버로 전송
				if(dout!=null) {
					dout.writeUTF(name);
				}
				while(dout != null) {
					// 키보드로 입력받은 메시지를 서버로 전송
					dout.writeUTF("[" + name + "] " +  scan.nextLine());
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 수신용 Thread 클래스 정의
	class ClientReceiver extends Thread{
		Socket socket;
		DataInputStream din;
		
		// 생성자
		public ClientReceiver(Socket socket) {
			this.socket = socket;
			try {
				din = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			while(din!=null) {
				try {
					// 서버로부터 수신한 메시지 출력하기
					System.out.println(din.readUTF());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}