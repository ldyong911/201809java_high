package javafx_udp_chat_client.application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx_udp_chat_client.thread.ChatClientRunnable;

public class MainController implements Initializable {

	@FXML
	private TextArea textArea_chatList;
	@FXML
	private TextField textField_chat;

	private DatagramSocket socket = null;
	private byte[] msg = new byte[100];

	InetAddress serverAddress = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(500);
			serverAddress = InetAddress.getByName("127.0.0.1");
			DatagramPacket outPacket = new DatagramPacket(msg, 1, serverAddress, 7777);
			socket.send(outPacket);

			textArea_chatList.setEditable(false); // 읽기 전용 속성설정

			ChatClientRunnable chatClientRunnable = new ChatClientRunnable(textArea_chatList, socket);
			Thread thread = new Thread(chatClientRunnable);
			thread.start();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void sendMessage(ActionEvent event) {
		String message = textField_chat.getText();
		DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, serverAddress, 7777);
		try {
			socket.send(outPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		textField_chat.setText("");
	}

}
