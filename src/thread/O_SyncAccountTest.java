package thread;

/*
 * 은행의 입출금을 쓰레드로 처리하는 예제
 * (Synchronized을 이용한 동기화 처리)
 * 
 */

public class O_SyncAccountTest {
	public static void main(String[] args) {
		SyncAccount sAcc = new SyncAccount();
		sAcc.setBalance(10000); // 입금처리
		
		BankThread bth1 = new BankThread(sAcc);
		BankThread bth2 = new BankThread(sAcc);
		
		bth1.start();
		bth2.start();
	}
}

// 은행의 입출금을 관리하는 클래스 정의
class SyncAccount{
	private int balance; // 잔액이 저장될 변수

	public synchronized int getBalance() {
		return balance;
	}
	public synchronized void setBalance(int balance) {
		this.balance = balance;
	}
	
	// 입금 처리를 수행하는 메서드
	public synchronized void deposit(int money) {
		balance += money;
	}
	
	// 출금 처리하는 메서드(출금 성공: true, 출금 실패: false반환)
	// 동기화 영역에서 호출하는 메서드도 동기화 처리를 해 주어야 한다.
	public synchronized boolean withdraw(int money) {
		if(balance >= money) { // 잔액이 출금액 보다 많을 경우...
			for(int i=1; i<=1000000000; i++) {} // 시간 때우기(동기화를 해주지않으면 쓰레드1이 이 문장을 실행할때 쓰레드2는 if문을 통과한다) 
			balance -= money;
			System.out.println("메서드 안에서 balance = " + getBalance());
			return true;
		}else {
			return false;
		}
	}
	
}

// 은행 업무를 처리하는 쓰레드
class BankThread extends Thread{
	private SyncAccount sAcc;
	
	public BankThread(SyncAccount sAcc) {
		this.sAcc = sAcc;
	}
	
	@Override
	public void run() {
		boolean result = sAcc.withdraw(6000); //6000원 출금
		System.out.println("쓰레드 안에서 result = " + result + ", balance = " + sAcc.getBalance());
	}
}