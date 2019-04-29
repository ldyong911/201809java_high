package thread;

public class C_ThreadTest {
	public static void main(String[] args) {
		/*
		 * 멀티 쓰레드 프로그램 방식
		 * => main 쓰레드가 종료된다고 해서 다른 멀티쓰레드가 종료되는 것은 아니다.
		 * => 실행중인 사용자 쓰레드가 하나도 없을 때 프로그램은 종료된다.
		 * => 스케줄러가 우선순위를 고려하여 정한 순서에 의해서 번갈아 가면서 쓰레드를 실행한다.
		 * 
		 */
		
		// 방법1: Thread클래스를 상속한 class의 인스턴스를 생성한 후
		//       이 인스턴스의 start()메서드를 호출한다.
		MyThread1 th1 = new MyThread1();
		th1.start();
		
		// 방법2: Runnable인터페이스를 구현한 class의 인스턴스를 생성한 후
		//       이 인스턴스를 Thread객체의 인스턴스를 생성할 때 생성자의 매개변수로 넘겨준다.
		//       이 때 생성된 Thread객체의 인스턴스의 start()메서드를 호출한다.
		MyThread2 r1 = new MyThread2();
		Thread th2 = new Thread(r1);
		th2.start();
		
		// 방법3: 익명클래스를 이용하는 방법
		// Runnable인터페이스를 구현한 익명클래스를 Thread인스턴스를 생성할 때 매개변수로 넘겨준다
		Thread th3 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=1; i<=100; i++) {
					System.out.println("@");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		th3.start();
		System.out.println("메인 메서드 작업 끝...");
	}
}

class MyThread1 extends Thread{
	@Override
	public void run() {
		for(int i=1; i<=100; i++) {
			System.out.println("*");
			try {
				// Thread.sleep(시간) ==> 주어진 시간동안 작업을 잠시 멈춘다. 
				// 시간은 밀리세컨드 단위를 사용한다.
				// 즉, 1000은 1초를 의미한다.
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class MyThread2 implements Runnable{
	@Override
	public void run() {
		for(int i=1; i<=100; i++) {
			System.out.println("$");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}