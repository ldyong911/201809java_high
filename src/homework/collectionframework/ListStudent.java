package homework.collectionframework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListStudent {
	public static void main(String[] args) {
		// 문제) 학번, 이름, 국어점수, 영어점수, 수학점수, 총점, 등수를 멤버로 갖는
		//      Student 클래스를 만든다.
		//      생성자는 학번, 이름, 국어, 영어, 수학 점수만 매개변수로 받아서 처리한다.
		//      이 Student객체들은 List에 저장하여 관리한다.
		//      List에 저장된 데이터들을 학번의 오름차순으로 정렬하여 출력하는 부분과
		//      총점의 역순으로 정렬하는 부분을 프로그램 하시오.
		//      (총점이 같으면 학번의 내림차순으로 정렬되도록 한다.)
		//      (학번 정렬기준은 Student클래스 자체에서 제공하도록 하고,
		//       총점 정렬기준은 외부클래스에서 제공하도록 한다.)
		
		ArrayList<Student> stdList = new ArrayList<>();
		
		stdList.add(new Student(1001, "호날두", 90, 95, 85));
		stdList.add(new Student(1005, "메에시", 90, 85, 80));
		stdList.add(new Student(1003, "모들치", 75, 80, 90));
		stdList.add(new Student(1004, "음바페", 85, 75, 85));
		stdList.add(new Student(1002, "넬마르", 80, 80, 90));
		
		/*랭크구하기
		for(int i=0; i<stdList.size(); i++) {
			for(int j=0; j<stdList.size(); j++) {
				if(stdList.get(i).getTotal() < stdList.get(j).getTotal()) {
					stdList.get(i).setRank(stdList.get(i).getRank()+1);
				}
			}
		}*/
		setRanking(stdList);
		
		System.out.println("정렬 전");
		for(Student std : stdList) {
			System.out.println(std);
		}
		System.out.println("===============================");
		
		Collections.sort(stdList);
		System.out.println("학번 오름차순 정렬 후");
		for(Student std : stdList) {
			System.out.println(std);
		}
		System.out.println("===============================");
		
		Collections.sort(stdList, new totalDesc());
		System.out.println("총점 내림차순 정렬 후");
		for(Student std : stdList) {
			System.out.println(std);
		}
		System.out.println("===============================");
	}
	
	public static void setRanking(List<Student> stdList) {
		for(Student std : stdList) {
			int rank = 1;
			for(Student std2 : stdList) {
				if(std.getTotal() < std2.getTotal()) {
					rank++;
				}
			}
			std.setRank(rank);
		}
	}
	
}

class totalDesc implements Comparator<Student>{
	//총점을 기준으로 내림차순
	@Override
	public int compare(Student std1, Student std2) {
		if(std1.getTotal() == std2.getTotal()) {
			//총점이 같으면 학번 내림차순
			return ((Integer)std1.getNum()).compareTo(std2.getNum()) * -1;
		}else {
			//총점이 같지않으면 총점 내림차순
			return Integer.compare(std1.getTotal(), std2.getTotal()) * -1;
		}
	}
}

class Student implements Comparable<Student>{
	private int num;
	private String name;
	private int kor;
	private int eng;
	private int math;
	private int total;
	private int rank;
	
	public Student(int num, String name, int kor, int eng, int math) {
		super();
		this.num = num;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		
		//총점과 랭크 초기화
		this.total = kor + eng + math;
		this.rank = 1;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}

	//학번을 기준으로 오름차순
	@Override
	public int compareTo(Student std) {
		return ((Integer)getNum()).compareTo(std.getNum());
	}
	
	//출력
	@Override
	public String toString() {
		return num + ", " + name + ", " + kor +", " + eng + ", " + math + ", " + total + ", " + rank;
	}
	
}