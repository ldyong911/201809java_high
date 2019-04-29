package thread;

public class N_SyncThreadTest {
	/*
	 * 쓰레드 동기화란 한 쓰레드가 진행 중인 작업을 다른 쓰레드가 간섭하지 못하도록 막는 것을 말한다.
	 * 
	 * 공유 데이터를 사용하는 코드 영역을 임계 영역으로 지정해놓고,
	 * 공유 데이터(객체)가 가지고 있는 lock을 획득한 단 하나의 쓰레드만 이 영역 내의 코드를 수행할 수 있게 한다.
	 * 그리고 해당 쓰레드가 임계 영역 내의 모든 코드를 수행하고 벗어나서
	 * lock을 반납해야만 다른 쓰레드가 반납된 lock을 획득하여 임계 영역의 코드를 수행할 수 있게 된다.
	 * 
	 */
	
	public static void main(String[] args) {
		ShareObject sObj = new ShareObject();
		
		WorkerThread th1 = new WorkerThread("1번쓰레드", sObj);
		WorkerThread th2 = new WorkerThread("2번쓰레드", sObj);
		
		th1.start();
		th2.start();
	}
}

// 공통으로 사용할 객체
class ShareObject{
	private int sum = 0;
	
	/*// 동기화 하는 방법1: 메서드 자체에 동기화 설정하기(메서드 전체를 임계 영역으로 지정)
	public synchronized void add() {
		int n = sum;
		n += 10; // 10 증가
		sum = n;
		
		System.out.println(Thread.currentThread().getName() + "합계: " + sum);
	}*/
	
	// 동기화 하는 방법2: 동기화 블럭으로 설정하기(특정한 영역을 임계 영역으로 지정)
	public void add() {
		synchronized (this) { // 매개변수는 객체의 참조변수
			int n = sum;
			n += 10; // 10 증가
			sum = n;
			
			System.out.println(Thread.currentThread().getName() + "합계: " + sum);
		}
	}
	
	public int getSum() {
		return sum;
	}
}

// 작업을 수행하는 쓰레드
class WorkerThread extends Thread{
	ShareObject sObj;
	
	public WorkerThread(String name, ShareObject sObj) {
		super(name);
		this.sObj = sObj;
	}

	@Override
	public void run() {
		for(int i=1; i<=10; i++) {
			sObj.add();
		}
	}
}