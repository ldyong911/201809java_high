package homework.thread;

import java.util.ArrayList;
import java.util.Collections;

public class ThreadHorse {
	/*
	    10마리의 말들이 경주하는 경마 프로그램 작성하기

		말은 Horse라는 이름의 클래스로 구성하고,
		이 클래스는 말이름(String), 등수(int)를 멤버변수로 갖는다.
		그리고, 이 클래스에는 등수를 오름차순으로 처리할 수 있는
		기능이 있다.(Comparable 인터페이스 구현)
		
		경기 구간은 1~50구간으로 되어 있다.
		
		경기 중 중간중간에 각 말들의 위치를 나타내시오.
		예)
		1번말 --->------------------------------------
		2번말 ----->----------------------------------
		...
		
		경기가 끝나면 등수 순으로 출력한다.
		
	 */
	public static void main(String[] args) {
		//말 이름 배열
		String[] name = new String[] {"호날두", "넬마르", "메에시", "모들치", "음바페",
									  "더억배", "사알라", "카앙테", "아자르", "글즈만"};
		//말 리스트 생성
		ArrayList<Horse> horseList = new ArrayList<Horse>();
		
		//10마리 말 생성
		for(int i=0; i<name.length; i++) {
			horseList.add(new Horse(name[i]));
		}
		
		//메인쓰레드 잠시 늦추기
		System.out.println("■■■■■ RACE START ■■■■■");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//말 쓰레드
		for(Horse horse : horseList) {
			horse.start();
		}
		
		//말 위치, 말 순위 프린트 쓰레드
		DisplayHorse displayHorse = new DisplayHorse(horseList);
		displayHorse.start();
	}
}

//말 클래스
class Horse extends Thread implements Comparable<Horse>{
	private String horseName; //말 이름
	private int rank; //말 순위
	private int location; //말 위치
	private boolean goal; //결승선 통과 체크
	
	public Horse(String horseName) {
		super();
		this.horseName = horseName; //말 객체 생성할때 이름 매개변수로 세팅
		this.rank = 0; //순위 0으로 초기화
		this.location = 0; //위치 0으로 초기화
		this.goal = false; //결승선 통과 false로 초기화
	}

	public String getHorseName() {
		return horseName;
	}
	public void setHorseName(String horseName) {
		this.horseName = horseName;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public boolean isGoal() {
		return goal;
	}
	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	//말 순위 오름차순
	@Override
	public int compareTo(Horse horse) {
//		return Integer.compare(getRank(), horse.getRank());
		return ((Integer)getRank()).compareTo(horse.getRank());
	}
	
	//이름, 순위, 위치 출력
	@Override
	public String toString() {
		return horseName + " " + rank + "등 "+ location;
	}
	
	//말 위치 쓰레드
	@Override
	public void run() {
		int count = 0; //말 위치 증가 변수
		while(true) { //말이 결승선을 통과 안했으면 반복
			if(location == DisplayHorse.MAX_COURSE) { //말의 위치가 50이되면 결승선 통과했으므로 while문 탈출
				break;
			}

			location += count; //말 위치를 계속 1씩 증가
			count = 1;
			try {
				Thread.sleep((int)(Math.random()*1000)); //말 위치를 변경하고 쓰레드를 랜덤시간으로 잠재운다
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

//말의 위치와 순위 출력
class DisplayHorse extends Thread{
	private ArrayList<Horse> horseList; //말 리스트 변수
	final static int MAX_COURSE = 50; //코스 길이
	
	//말 리스트 받아오기
	public DisplayHorse(ArrayList<Horse> horseList) {
		super();
		this.horseList = horseList;
	}

	@Override
	public void run() {
		ArrayList<String> course = new ArrayList<String>(); //코스 생성
		int rank = 1; //말 순위 변수
		
		while(true) {
			//화면 지우기
			for(int i=0; i<30; i++) {
				System.out.println();
			}
			
			//10마리 말 프린트
			for(int i=0; i<horseList.size(); i++) {
				//말이 결승선 통과했는지 체크
				if(horseList.get(i).isGoal() == true) { //다시 i번째가 되었을때 랭크가 세팅이 안되었는데 이미 위치가 50이 될수 있기 때문에
					System.out.println(horseList.get(i).toString());
					continue; //말이 골인했으면 다음말로 넘어감
				}
				
				//코스와 말위치 지정
				for(int j=0; j<MAX_COURSE; j++) {
					course.add(j, "ㅡ"); //코스 표시
					if(horseList.get(i).getLocation() == j) { //말 위치가 j와 같다면
						course.add(j, "▶"); //말 위치 표시
					}
				}
				
				//말이름과 코스, 말위치 프린트
				System.out.print(horseList.get(i).getHorseName() + " ");
				for(int j=0; j<MAX_COURSE; j++) {
					System.out.print(course.get(j));
				}
				System.out.println();
				
				//말이 결승선 통과했는지 체크해서 말순위 저장
				if(horseList.get(i).getLocation() == MAX_COURSE) { //말 위치가 결승선을 통과했으면
					horseList.get(i).setRank(rank); //해당 말의 순위 저장
					rank++; //다음 말의 순위를 한단계 증가해서 지정하기위해
					horseList.get(i).setGoal(true); //말의 통과 true로 지정
				}
			}
			
			//Thread를 sleep 안하면 너무 빨리 출력됨(편하게 보기위해)
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//말이 모두 통과했는지 체크
			if(rank > horseList.size()) { //말의 수보다 rank가 높으면
				break; //while문 탈출
			}
		}
		
		
		//말 순위 출력
		Collections.sort(horseList); //말 순위를 오름차순으로 정렬
		System.out.println("********************************************************************************");
		System.out.println("■■■순 위■■■");
		for(Horse h : horseList) {
			System.out.print(h.getRank() + "등: " + h.getHorseName() + "\n");
		}
		System.exit(0); //순위를 출력했으면 프로그램을 정상적으로 종료
	}
}