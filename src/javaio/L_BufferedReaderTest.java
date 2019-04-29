package javaio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class L_BufferedReaderTest {
	public static void main(String[] args) {
		// 문자기반의 Buffered스트림 사용 예제
		
		try {
			// 이클립스에서 만든 자바프로그램이 실행되는 기본 위치는 해당 '프로젝트폴더'가 기본 위치가 된다.
			FileReader fr = new FileReader("./src/basic/L_BufferedReaderTest.java");
			
			BufferedReader br = new BufferedReader(fr);
			
			// 출력방법1
			String temp = "";
			for(int i=1; (temp = br.readLine()) != null; i++) {
				System.out.printf("%4d : %s\n", i, temp);
			}
			
			// 출력방법2
//			int i=0;
//			while(true) {
//				String temp = br.readLine();
//				if(temp == null) {
//					break;
//				}
//				System.out.printf("%4d : %s\n", i++, temp);
//			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}