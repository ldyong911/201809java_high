package javaio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * 기본타입 입출력 보조 스트림 예제
 */
public class M_DataIOStreamTest {
	public static void main(String[] args) {
		try {
			FileOutputStream fout = new FileOutputStream("d:/D_Other/test.dat");
			
			// DataOutputStream은 출력용 데이터를 자료형에 맞게 출력해 준다.
			DataOutputStream dout = new DataOutputStream(fout);
			
			dout.writeInt(18); // 정수형으로 데이터 출력
			dout.writeFloat(3.14f); // 실수형(Float)으로 데이터 출력
			dout.writeDouble(3.14); // 실수형(Double)으로 데이터 출력
			dout.writeBoolean(true); // 논리형으로 출력
			
			System.out.println("출력 완료...");
			
			dout.close();
			
			//======================================================
			
			// 출력한 자료 읽어오기
			FileInputStream fin = new FileInputStream("d:/D_Other/test.dat");
			DataInputStream din = new DataInputStream(fin);
			
			System.out.println("정수형 자료: " + din.readInt());
			System.out.println("실수형(Float) 자료: " + din.readFloat());
			System.out.println("실수형(Double) 자료: " + din.readDouble());
			System.out.println("논리형 자료: " + din.readBoolean());
			
			din.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}