package homework.collectionframework;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class SetLotto {
	/*
	 * 로또를 구매하는 프로그램 작성하기
	 * 
	 * 사용자는 로또를 구매할 때 구매할 금액을 입력하고 입력한 금액에 맞게 로또번호를 출력한다.
	 * (단, 로또 한장의 금액은 1000원이고 거스름돈도 계산하여 출력한다.)
	 * 
	 * ==========================
	 *       Lotto 프로그램
	 * --------------------------
	 * 1. Lotto 구입
	 * 2. 프로그램 종료
	 * ==========================
	 * 메뉴선택 : 1 <-- 입력
	 * 
	 * Lotto 구입 시작
	 * 
	 * (1000원에 로또번호 하나입니다.)
	 * 금액 입력 : 2500 <-- 입력
	 * 
	 * 행운의 로또번호는 아래와 같습니다.
	 * 로또번호1 : 2,3,4,5,6,7
	 * 로또번호2 : 20,21,22,23,24,25
	 * 
	 * 받은 금액은 2500원이고 거스름돈은 500원입니다.
	 * 
	 * ==========================
	 *       Lotto 프로그램
	 * --------------------------
	 * 1. Lotto 구입
	 * 2. 프로그램 종료
	 * ==========================
	 * 메뉴선택 : 2 <-- 입력
	 * 
	 * 감사합니다
	 */
	
	Scanner scan = new Scanner(System.in);
	boolean isContinue = true; //메뉴 while문 제어
	final static int LOTTO_PRICE = 1000; //로또가격
	int buyMoney; //입력할 금액
	int changeMoney; //거스름돈
	int[] lottoNum; //로또번호 배열
	
	// 로또 번호 랜덤으로 중복안되게 생성해서 배열에 넣기
	public void lottoNum() {
		Set<Integer> lotto = new HashSet<>();
		
		while(lotto.size() < 6) {
			lotto.add((int)(Math.random()*45+1));
		}
		
		Iterator<Integer> it = lotto.iterator();
		int i=0; //첨자역할
		lottoNum = new int[6];
		while(it.hasNext()) {
			lottoNum[i++] = it.next();
		}
		
		
		int cnt=0; //첫번째는 ,를 안찍기 위해
		for(int num : lottoNum) {
			if(cnt == 0) {
				System.out.print(num);
			}else {
				System.out.print("," + num);
			}
			cnt++;
		}
	}
	
	//로또 구입하기
	public void buyLotto() {
		buyMoney = 0;
		System.out.println("Lotto 구입 시작");
		System.out.println("1000원에 로또번호 하나입니다.");
		System.out.print("금액 입력: ");
		buyMoney = scan.nextInt(); //금액입력
		changeMoney = buyMoney%LOTTO_PRICE; //거스름돈
		
		//buyMoney/LOTTO_PRICE 몫
		//buyMoney%LOTTO_PRICE 나머지
		
		System.out.println();
		System.out.println("행운의 로또번호는 아래와 같습니다.");
		for(int i=0; i<buyMoney/LOTTO_PRICE; i++) {
			System.out.print("로또번호" + (i+1) + ": ");
			lottoNum();
			System.out.println();
		}
		System.out.println("받은 금액은 " + buyMoney +"원이고"
						 + " 거스름돈은 " + changeMoney + "원입니다.");
	}
	
	//메뉴 시작하기
	public void menuStart() {
		while(isContinue) {
			System.out.println("================");
			System.out.println("Lotto 프로그램");
			System.out.println("----------------");
			System.out.println("1. Lotto 구입");
			System.out.println("2. 프로그램 종료");
			System.out.println("================");
			switch (tryCatch()) {
			case 1:
				buyLotto();
				break;
			case 2:
				System.out.println("감사합니다.");
				isContinue = false;
				break;
			default:
				System.out.println("잘못입력하셨습니다.");
			}
		}
	}
	
	// 숫자입력 예외처리
	public int tryCatch() {
		while(true)
		try {
			System.out.print("메뉴선택: ");
			int num = scan.nextInt();
			return num;
		} catch (InputMismatchException e) {
			System.out.println("숫자만 입력해주세요!");
			scan = new Scanner(System.in); // scanner 초기화
		}
	}

	public static void main(String[] args) {
		SetLotto lottoTest = new SetLotto();
		lottoTest.menuStart();
	}
}