package javafx_udp_chat_client.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

import javafx.scene.control.TextArea;

public class ChatClientRunnable implements Runnable {

	private TextArea textArea_chatList;
	private DatagramSocket socket = null;
	private byte[] msg = new byte[100];

	public ChatClientRunnable(TextArea textArea_chatList, DatagramSocket socket) {
		this.textArea_chatList = textArea_chatList;
		this.socket = socket;
	}

	@Override
	public void run() {
		while (true) {
			DatagramPacket inPacket = new DatagramPacket(msg, msg.length);
			try {
				socket.receive(inPacket);
				System.out.println(new String(inPacket.getData()));
				textArea_chatList.appendText(new String(inPacket.getData()) + "\n");
			} catch (SocketTimeoutException e) {
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
