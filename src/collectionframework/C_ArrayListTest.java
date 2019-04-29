package collectionframework;

import java.util.ArrayList;
import java.util.Scanner;

public class C_ArrayListTest {

	public static void main(String[] args) {
		// 문제) 5명의 사람 이름을 입력하여 ArrayList에 저장하고
		//      이 중에 '김'씨 성의 이름을 출력하시오.
		//      (단, 입력은 Scanner를 입력하여 입력받는다)
		
		ArrayList<String> nameList = new ArrayList<String>();
		Scanner s = new Scanner(System.in);
		
		for(int i=0; i<5; i++) {
			System.out.print((i+1)+"번째 이름: ");
			String name = s.next();
			nameList.add(name);
		}
		
		for(int i=0; i<nameList.size(); i++) {
			if(nameList.get(i).charAt(0) == '김') {
				System.out.println(nameList.get(i));
			}
		}
		
		for(int i=0; i<nameList.size(); i++) {
			String name = nameList.get(i);
			if(name.startsWith("김")) {
				System.out.println(name);
			}
			
			if(name.indexOf("김") == 0) {
				System.out.println(name);
			}
		}
		
	}
		
}