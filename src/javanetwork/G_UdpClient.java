package javanetwork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class G_UdpClient {
	public void start() {
		try {
			DatagramSocket datagramSocket = new DatagramSocket();
			InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
			
			// 데이터가 저장될 공간으로 byte배열을 생성한다.
			byte[] msg = new byte[100];
			
			DatagramPacket outPacket = new DatagramPacket(msg, 1, serverAddress, 8888);
			DatagramPacket inPacket = new DatagramPacket(msg, msg.length);
			
			datagramSocket.send(outPacket); // DatagramPacket을 전송한다.
			datagramSocket.receive(inPacket); // DatagramPacket을 수신한다.
			
			System.out.println("현재 서버 시간: " + new String(inPacket.getData()));
			
			datagramSocket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new G_UdpClient().start();
	}
}