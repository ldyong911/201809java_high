package javanetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class C_URLConnectionTest {
	public static void main(String[] args) {
		// URLConnection => 어플리케이션과 URL간의 통신 연결을 위한 추상 클래스
		
		// 특정 서버(예:NAVER서버)의 정보와 파일 내용을 출력하는 예제
		try {
			URL url = new URL("https://www.naver.com/index.html");
			
			// Header 정보 가져오기
			
			// URLConnection 객체 구하기
			URLConnection urlConn = url.openConnection();
			
			System.out.println("Content-Type: " + urlConn.getContentType());
			System.out.println("Encoding: " + urlConn.getContentEncoding());
			System.out.println("Content: " + urlConn.getContent());
			System.out.println();
			
			// 전체 Header정보 출력하기
			Map<String, List<String>> headerMap = urlConn.getHeaderFields();
			
			// Header의 Key값 구하기
			Iterator<String> iterator = headerMap.keySet().iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				System.out.println(key + " : " + headerMap.get(key));
			}
			System.out.println("===================================");
			
			// 해당호스트의 페이지 내용 가져오기
			
			// 방법1 => URLConnection의 getInputStream메서드 이용하기
			InputStream is = urlConn.getInputStream();
			
			// 방법2 => URL객체의 openStream()메서드 이용하기
//			InputStream is = url.openStream();
			
			// 파일을 읽어오기 위한 스트림 생성
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			
			// 내용 출력하기
			while(true) {
				String str = br.readLine();
				if(str == null) {
					break;
				}
				System.out.println(str);
			}
			
			// 스트림 닫기
			br.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}