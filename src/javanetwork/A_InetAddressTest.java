package javanetwork;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class A_InetAddressTest {
	public static void main(String[] args) {
		// InetAddress클래스 => IP주소를 다루기 위한 클래스
		
		try {
			// NAVER사이트의 IP정보 가져오기
			InetAddress naverIp = InetAddress.getByName("www.naver.com");
			System.out.println("Host Name=> " + naverIp.getHostName());
			System.out.println("Host Address=> " + naverIp.getHostAddress());
			System.out.println();
			
			// 자기 자신 컴퓨터의 IP주소 가져오기
			InetAddress localIp = InetAddress.getLocalHost();
			System.out.println("내 컴퓨터의 Host Name=> " + localIp.getHostName());
			System.out.println("내 컴퓨터의 Host Address=> " + localIp.getHostAddress());
			System.out.println();
			
			// IP주소가 여러 개인 호스트의 정보 가져오기
			InetAddress[] naverIps = InetAddress.getAllByName("www.naver.com");
			for(InetAddress nIp : naverIps) {
				System.out.println(nIp.toString());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
}