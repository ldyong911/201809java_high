package homework.thread;

import javax.swing.JOptionPane;

public class ThreadGame {
	/*
	 * 컴퓨터와 가위 바위 보를 진행하는 프로그램을 작성하시오.
	 * 
	 * 컴퓨터의 가위 바위 보는 난수를 이용하여 구하고,
	 * 사용자의 가위 바위 보는 showInputDialog()메서드를 이용하여 입력받는다.
	 * 
	 * 입력시간은 5초로 제한하고 카운트 다운을 진행한다.
	 * 5초안에 입력이 없으면 게임을 진것으로 처리한다.
	 * 
	 * 5초안에 입력이 완료되면 승패를 출력한다.
	 * 
	 * 결과예시)
	 * === 결 과 ===
	 * 컴퓨터 : 가위
	 * 당  신 : 바위
	 * 결  과 : 당신이 이겼습니다.
	 * 
	 */
	
	public static boolean inputCheck = false;
	static String[] com = new String[] {"가위", "바위", "보"};
	
	public static void main(String[] args) {
		Thread th1 = new UserInput();
		Thread th2 = new CountDown2();
		
		th1.start();
		th2.start();
	}
}

class UserInput extends Thread{
	private String com;
	
	public UserInput() {
		super();
		this.com = ThreadGame.com[(int)(Math.random()*3)]; //인덱스는 0부터 2까지
	}

	@Override
	public void run() {
		String user = null;
		do {
			user = JOptionPane.showInputDialog("가위, 바위, 보 중 선택하여 입력하세요.");
		}while(!user.equals("가위") && !user.equals("바위") && !user.equals("보")); // 가위, 바위, 보 외에 다른값을 입력하면 반복
		
		System.out.println("=== 결 과 ===");
		System.out.println("컴퓨터: " + this.com);
		System.out.println("당  신: " + user);
		
		if(this.com.equals(user)) {
			System.out.println("결  과: 서로 비겼습니다.");
		}else if((this.com.equals("가위") && user.equals("보"))
				|| (this.com.equals("바위") && user.equals("가위"))
				|| (this.com.equals("보") && user.equals("바위"))) {
			System.out.println("결  과: 당신이 졌습니다.");
		}else {
			System.out.println("결  과: 당신이 이겼습니다.");
		}
		
		ThreadGame.inputCheck = true;
	}
}

class CountDown2 extends Thread{
	@Override
	public void run() {
		for(int i=5; i>=1; i--) {
			if(ThreadGame.inputCheck == true) {
				return;
			}
			
			System.out.println(i);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("입력시간이 경과되었습니다.");
		System.out.println("당신이 졌습니다.");
		System.exit(0);
	}
}