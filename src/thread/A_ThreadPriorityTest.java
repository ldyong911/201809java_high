package thread;

public class A_ThreadPriorityTest {
	public static void main(String[] args) {
		/*
		 * 기본적으로 쓰레드 우선순위는 5
		 * 최대 우선순위는 10
		 * 최소 우선순위는 1
		 * 
		 * 쓰레드에 높은 우선순위를 주면 더 많은 실행시간과 실행기회를 갖게 될 것이라고 기대할 수 없다.
		 * 
		 * 멀티코어에서는 쓰레드의 우선순위에 따른 차이가 거의 전혀 없다.
		 * 1코어당 쓰레드가 여러개인 경우에는 우선순위에 따른 차이가 있을 것이다.
		 * 
		 */
		
		
		ThreadTest1 th1 = new ThreadTest1();
		ThreadTest2 th2 = new ThreadTest2();
		
		// 우선 순위는 start()메서드를 호출하기 전에 설정해야 한다.
		th1.setPriority(6);
		th2.setPriority(3);
		
		System.out.println("th1의 우선순위: " + th1.getPriority());
		System.out.println("th2의 우선순위: " + th2.getPriority());
		
		th1.start();
		th2.start();
		
		try {
			th1.join();
			th2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("최대 우선순위: " + Thread.MAX_PRIORITY); // 10
		System.out.println("최소 우선순위: " + Thread.MIN_PRIORITY); // 1
		System.out.println("보통 우선순위: " + Thread.NORM_PRIORITY); // 5
	}
}

// 대문자를 출력하는 쓰레드
class ThreadTest1 extends Thread{
	@Override
	public void run() {
		for(char ch='A'; ch<='Z'; ch++) {
			System.out.println(ch);
			
			// 아무것도 하지 않는 반복문(시간때우기용) 없으면 너무빨리 끝남
			for(long i=1; i<=1000000000L; i++) {}
		}
		
	}
}

// 소문자를 출력하는 쓰레드
class ThreadTest2 extends Thread{
	@Override
	public void run() {
		for(char ch='a'; ch<='z'; ch++) {
			System.out.println(ch);
			
			// 아무것도 하지 않는 반복문(시간때우기용) 없으면 너무빨리 끝남
			for(long i=1; i<=1000000000L; i++) {}
		}
		
	}
}