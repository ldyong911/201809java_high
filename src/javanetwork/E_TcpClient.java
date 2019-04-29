package javanetwork;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class E_TcpClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.168.206.26", 7777);
			System.out.println("서버에 연결되었습니다.");
			
			Sender sender = new Sender(socket);
			Receiver receiver = new Receiver(socket);
			
			sender.start();
			receiver.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}