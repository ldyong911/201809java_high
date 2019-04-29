package javanetwork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class G_UdpServer {
	public void start() {
		try {
			DatagramSocket socket = new DatagramSocket(8888);
			DatagramPacket inPacket, outPacket;
			
			byte[] inMsg = new byte[10];
			byte[] outMsg;
			
			while(true) {
				// 데이터를 수신하기 위한 패킷을 생성한다.
				inPacket = new DatagramPacket(inMsg, inMsg.length);
				
				// 패킷을 통해 데이터를 수신(receive)한다.
				socket.receive(inPacket);
				
				// 수신한 패킷으로부터 client의 IP주소와 Port를 얻는다.
				InetAddress address = inPacket.getAddress();
				int port = inPacket.getPort();
				
				// 서버의 현재 시간을 시분초 형태([hh:mm:ss])로 반환한다.
				SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
				String time = sdf.format(new Date());
				outMsg = time.getBytes(); // time을 byte배열로 변환한다.
				
				// 패킷을 생성해서 client에게 전송(send)한다.
				outPacket = new DatagramPacket(outMsg, outMsg.length, address, port);
				socket.send(outPacket);
				
				System.out.println(address + " : " + port);
			}
			
 		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) {
		new G_UdpServer().start(); // UDP서버를 실행시킨다.
	}
}