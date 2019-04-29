package javanetwork;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class B_URLTest {
	public static void main(String[] args) {
		// URL클래스 => 인터넷에 존재하는 서버들의 자원에 접근할 수 있는 주소를 관리하는 클래스
		
		try {
			//전체주소 http://ddit.or.kr/community02?bc_seq=2
			URL url = new URL("http", "ddit.or.kr", 80, "community02?bc_seq=2");
			System.out.println("protocol: " + url.getProtocol());
			System.out.println("host: " + url.getHost());
			System.out.println("file: " + url.getFile());
			System.out.println("query: " + url.getQuery());
			System.out.println("path: " + url.getPath());
			System.out.println("port: " + url.getPort());
			System.out.println("ref: " + url.getRef());
			System.out.println("");
			
			System.out.println(url.toExternalForm());
			System.out.println(url.toString());
			System.out.println(url.toURI().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}