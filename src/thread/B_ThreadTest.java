package thread;

public class B_ThreadTest {
	public static void main(String[] args) {
		/*
		 * 싱글 쓰레드 프로그램
		 * => main에도 쓰레드가 존재함
		 */
		
		for(int i=1; i<=100; i++) {
			System.out.print("*");
		}
		
		System.out.println();
		
		for(int i=1; i<=100; i++) {
			System.out.print("$");
		}
	}
}