package thread;

public class H_ThreadDeamonTest {
	public static void main(String[] args) {
		/*
		 * 데몬 쓰레드는 다른 일반 쓰레드(데몬 쓰레드가 아닌 쓰레드)의 작업을 돕는 보조적인 역할을 수행하는 쓰레드이다.
		 * 일반 쓰레드가 모두 종료되면 데몬 쓰레드는 강제적으로 자동 종료되는데,
		 * 그 이유는 데몬 쓰레드는 일반 쓰레드의 보조역할을 수행하므로 일반 쓰레드가 종료되면 존재의미가 없기 때문이다.
		 * 
		 * 데몬 쓰레드의 예로는 가비지 컬렉터, 워드프로세서의 자동저장, 화면 자동갱신 등이 있다.
		 * 
		 */
		
		AutoSaveThread autoSave = new AutoSaveThread();
		
		// 데몬쓰레드로 설정하기 (start()메서드 호출하기 전에 설정한다.)
		autoSave.setDaemon(true);
		autoSave.start();
		
		try {
			for(int i=1; i<=20; i++) {
				System.out.println("작업 " + i);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("메인 쓰레드 종료...");
	}
}

// 자동 저장하는 쓰레드(3초에 한번씩 저장하기)
class AutoSaveThread extends Thread{
	public void save() {
		System.out.println("작업 내용을 저장합니다...");
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			save();
		}
	}
}