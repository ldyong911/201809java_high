package mvc;

public class B_SingletonTest {
	public static void main(String[] args) {
		//MySingleton test1 = new MySingleton(); // new 명령사용불가
		
		// getInstance()메서드 이용하여 객체 생성
		A_MySingleton test2 = A_MySingleton.getInstance();
		
		test2.displayTest();
		
		A_MySingleton test3 = A_MySingleton.getInstance();
		System.out.println("test2 => " + test2);
		System.out.println("test3 => " + test3);
	}
}