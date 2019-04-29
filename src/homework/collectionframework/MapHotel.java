package homework.collectionframework;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class MapHotel {
	/*
	 * 문제) 호텔 운영을 관리하는 프로그램 작성.(Map이용)
			 - 키값은 방번호 
			 
			실행 예시)
			
			**************************
			호텔 문을 열었습니다.
			**************************
			
			*******************************************
			어떤 업무를 하시겠습니까?
			1.체크인  2.체크아웃 3.객실상태 4.업무종료
			*******************************************
			메뉴선택 => 1 <-- 입력
			
			어느방에 체크인 하시겠습니까?
			방번호 입력 => 101 <-- 입력
			
			누구를 체크인 하시겠습니까?
			이름 입력 => 홍길동 <-- 입력
			체크인 되었습니다.
			
			*******************************************
			어떤 업무를 하시겠습니까?
			1.체크인  2.체크아웃 3.객실상태 4.업무종료
			*******************************************
			메뉴선택 => 1 <-- 입력
			
			어느방에 체크인 하시겠습니까?
			방번호 입력 => 102 <-- 입력
			
			누구를 체크인 하시겠습니까?
			이름 입력 => 성춘향 <-- 입력
			체크인 되었습니다
			
			*******************************************
			어떤 업무를 하시겠습니까?
			1.체크인  2.체크아웃 3.객실상태 4.업무종료
			*******************************************
			메뉴선택 => 3 <-- 입력
			
			방번호 : 102, 투숙객 : 성춘향
			방번호 : 101, 투숙객 : 홍길동
			
			*******************************************
			어떤 업무를 하시겠습니까?
			1.체크인  2.체크아웃 3.객실상태 4.업무종료
			*******************************************
			메뉴선택 => 2 <-- 입력
			
			어느방을 체크아웃 하시겠습니까?
			방번호 입력 => 101 <-- 입력
			체크아웃 되었습니다.
			
			*******************************************
			어떤 업무를 하시겠습니까?
			1.체크인  2.체크아웃 3.객실상태 4.업무종료
			*******************************************
			메뉴선택 => 1 <-- 입력
			
			어느방에 체크인 하시겠습니까?
			방번호 입력 => 102 <-- 입력
			
			누구를 체크인 하시겠습니까?
			이름 입력 => 허준 <-- 입력
			102방에는 이미 사람이 있습니다.
			
			*******************************************
			어떤 업무를 하시겠습니까?
			1.체크인  2.체크아웃 3.객실상태 4.업무종료
			*******************************************
			메뉴선택 => 2 <-- 입력
			
			어느방을 체크아웃 하시겠습니까?
			방번호 입력 => 101 <-- 입력
			101방에는 체크인한 사람이 없습니다.
			
			*******************************************
			어떤 업무를 하시겠습니까?
			1.체크인  2.체크아웃 3.객실상태 4.업무종료
			*******************************************
			메뉴선택 => 3 <-- 입력
			
			방번호 : 102, 투숙객 : 성춘향
			
			*******************************************
			어떤 업무를 하시겠습니까?
			1.체크인  2.체크아웃 3.객실상태 4.업무종료
			*******************************************
			메뉴선택 => 4 <-- 입력
			
			**************************
			호텔 문을 닫았습니다.
			**************************
	 */
	
	HashMap<Integer, String> map = new HashMap<Integer, String>();
	Scanner iscan = new Scanner(System.in);
	Scanner sscan = new Scanner(System.in);
	
	//체크인
	public void checkIn() {
		System.out.println();
		System.out.println("어느방에 체크인 하시겠습니까?");
		System.out.print("방번호 입력 => ");
		int room = iscan.nextInt();
		
		System.out.println("누구를 체크인 하시겠습니까?");
		System.out.print("이름 입력 => ");
		String name = sscan.next();
		
		if(map.containsKey(room)) { //입력한 방이 체크인 되어있는지 검사
			System.out.println(room + "방에는 이미 사람이 있습니다.");
			return;
		}
		
		map.put(room, name); //map에 방번호와 이름 데이터삽입
		System.out.println("체크인 되었습니다.");
	}
	
	//체크아웃
	public void checkOut() {
		System.out.println();
		System.out.println("어느방을 체크아웃 하시겠습니까?");
		System.out.print("방번호 입력 => ");
		int room = iscan.nextInt();
		
		if(!map.containsKey(room)) { //입력한 방 체크인 검사
			System.out.println(room+"방에는 체크인한 사람이 없습니다.");
			return;
		}
		
		map.remove(room); //map에 key값인 방번호로 데이터삭제
		System.out.println("체크아웃 되었습니다.");
	}
	
	//객실상태
	public void infoRoom() {
		System.out.println();
		Set<Integer> keySet = map.keySet();
		Iterator<Integer> it = keySet.iterator();
		
		while(it.hasNext()) {
			int key = it.next();
			System.out.println("방번호 : " + key + ", 투숙객 : " + map.get(key));
		}
	}
	
	public void mainView() {
		boolean isContinue = true;
		
		System.out.println("********************");
		System.out.println("호텔 문을 열었습니다.");
		System.out.println("********************");
		while(isContinue) {
			System.out.println();
			System.out.println("*************************************");
			System.out.println("1.체크인  2.체크아웃  3.객실상태  4.업무종료");
			System.out.println("*************************************");
			System.out.print("메뉴선택 => ");
			int menu = iscan.nextInt();
			
			switch (menu) {
			case 1:
				checkIn();
				break;
			case 2:
				checkOut();
				break;
			case 3:
				infoRoom();
				break;
			case 4:
				System.out.println("********************");
				System.out.println("호텔 문을 닫았습니다.");
				System.out.println("********************");
				isContinue = false;
				break;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}
	
	public static void main(String[] args) {
		MapHotel hotel = new MapHotel();
		hotel.mainView();
	}
}

class Hotel{
	private int roomNum;
	private String name;
}