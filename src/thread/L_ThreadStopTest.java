package thread;

/*
 * Thread의 stop()메서드를 호출하면 쓰레드가 바로 멈춘다.
 * 이때 사용하던 자원을 정리하지 못하고 프로그램이 종료되어서
 * 나중에 실행되는 프로그램에 영향을 줄 수 있다.
 * 그래서 현재는 stop()메서드는 비추천으로 되어있다.
 */
public class L_ThreadStopTest {
	public static void main(String[] args) {
		ThreadStopEx1 th1 = new ThreadStopEx1();
//		th1.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//방법1
		th1.stop(); // 쓰레드 강제종료(가급적 사용금지)
		
		//방법2
		th1.setStop(true);
		
		// 방법3
		// interrupt()메서드를 이용한 쓰레드 멈추기
		System.out.println("===============================");
		
		ThreadStopEx2 th2 = new ThreadStopEx2();
		th2.start();
		
		try {
			Thread.sleep(1); // 현재 메서드에서 sleep을 호출한 쓰레드를 잠재운다(여기서는 main이 0.001초 잠재워짐)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		th2.interrupt(); // interrupt()메서드 호출
		System.out.println("===============================");
		
	}
}

// 코드로 stop을 구현하여 쓰레드를 멈추게 하는 방법
class ThreadStopEx1 extends Thread{
	private boolean stop;
	
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	@Override
	public void run() {
		while(!stop) {
			System.out.println("쓰레드 stop() 처리중...");
		}
		System.out.println("자원 정리 중...");
		System.out.println("실행 종료.");
	}
}

// interrupt()메서드를 이용하여 쓰레드를 멈추게 하는 방법
class ThreadStopEx2 extends Thread{
	@Override
	public void run() {
		/*// 방법1 => sleep()메서드나 join()메서드 등을 사용했을 때
		//         interrupt()메서드를 호출하면 InterruptException이 발생한다.
		try {
			while(true) {
				System.out.println("쓰레드 interrupt() 방법1 처리중...");
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		// 방법2 => interrupt()메서드가 호출되었는지 검사하기
		while(true) {
			System.out.println("쓰레드 interrupt() 방법2 처리중...");
			
			// 검사방법1 => 쓰레드의 인스턴스 객체용 메서드를 이용하는 방법
			if(this.isInterrupted()) { // interrupt()메서드가 호출되면 true
				System.out.println("인스턴스용 isInterrupted()메서드");
				System.out.println(this.getState());
				break;
			}
			
			// 검사방법2 => 쓰레드의 정적 메서드를 이용하는 방법
			if(Thread.interrupted()) { // interrupt()메서드가 호출되면 true
				System.out.println("정적 메서드 interrupted()메서드");
				System.out.println(this.getState());
				break;
			}
		}
		
		System.out.println("자원 정리 중...");
		System.out.println("실행 종료.");
	}
}