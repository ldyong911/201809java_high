package javaio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class N_ObjectStreamTest {
	public static void main(String[] args) {
		// Member 인스턴스 생성
		Member mem1 = new Member("김도현", 20, "대구시 수성구");
		Member mem2 = new Member("이대용", 25, "부산시 사하구");
		Member mem3 = new Member("강현욱", 30, "서울시 강남구 대치동");
		Member mem4 = new Member("김현지", 18, "대전시 중구 대흥동");
		
		try {
			// 객체를 파일에 저장하기
			
			// 출력용 스트림 객체 생성
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("d:/D_Other/memObj.bin")));
			
			// 쓰기 작업
			oos.writeObject(mem1);
			oos.writeObject(mem2);
			oos.writeObject(mem3);
			oos.writeObject(mem4);
			
			System.out.println("쓰기 작업 끝...");
			oos.close();
			
			//=================================
			
			// 저장한 객체를 읽어와 출력하기
			
			// 입력용 스트림 객체 생성
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("d:/D_Other/memObj.bin")));
			
			Object obj = null;
			
			while((obj = ois.readObject()) != null) {
				Member mem = (Member)obj;
				System.out.println("이름: " + mem.getName());
				System.out.println("나이: " + mem.getAge());
				System.out.println("주소: " + mem.getAddr());
				System.out.println("=======================");
			}
			ois.close();
		} catch (IOException e) {
			// 더이상 읽어올 객체가 없으면 예외 발생함.
			System.out.println("더이상 읽어올 객체가 없어요...");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

/*
 * 직렬화(Serialization)
 * - 데이터를 파일에 쓰거나, 네트워크를 타고 다른 곳에 전송할 때는 데이터를 바이트 단위로 분해하여
 *   순차적으로 보내야 하며 이러한 과정을 수행하는게 직렬화.
 * - 기본 자료형(boolean, char, byte, short, int ,long, float, double)은 정해진 바이트의 변수이기 때문에
 *   바이트 단위로 분해하여 전송한 후 다시 조립하는데 문제가 없다.
 *   하지만 객체의 크기는 가변적이며, 객체를 구성하는 자료형들의 종류와 수에 따라 객체의 크기는 다양하게 바뀔 수 있다.
 *   이런 객체를 직렬화 하기 위해서 Serializable 인터페이스를 구현하게 된다.
 * 
 */

class Member implements Serializable{ // 자바는 Serializable 인터페이스를 구현한 클래스만 직렬화 가능
	/*
	 * serialVersionUID 는 직렬화에 사용되는 고유 아이디인데, 선언하지 않으면 JVM에서 디폴트로 자동 생성된다.
	 * 따라서 선언하지 않아도 동작하는데 문제는 없지만, 불안하기 때문에 JAVA에서는 명시적으로 serialVersionUID를 선언할 것을 적극 권장하고 있다.
	 * 
	 * JVM에 의한 디폴트 serialVersionUID 계산은 클래스의 세부 사항을 매우 민감하게 반영하기 때문에
	 * 컴파일러 구현체에 따라서 달라질 수 있어 deserialization 과정에서 예상하지 못한 InvalidClassException을 유발할 수 있다.
	 * 
	 */
	
	/*
	 * transient => 직렬화가 되지 않을 멤버변수에 지정한다.
	 *              (* static 필드도 직렬화가 되지 않는다.)
	 *              직렬화가 되지 않는 멤버변수는 기본값으로 저장된다.
	 *              (참조형 변수 : null, 숫자형 변수 : 0)
	 *                 
	 *              객체의 데이터 중 일부의 데이터는(패스워드와 같은 보안) 여러가지 이유로 전송을 하고 싶지 않을 수 있다.
	 *              이러한 변수는 직렬화에서 제외해야 되며, 이를 위해서 변수에 transient를 선언한다.
	 */

	private String name;
	private transient int age;
	private String addr;
	
	public Member(String name, int age, String addr) {
		super();
		this.name = name;
		this.age = age;
		this.addr = addr;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
}