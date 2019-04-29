package collectionframework;

import java.util.ArrayList;
import java.util.Scanner;

public class D_ArrayListTest {

	public static void main(String[] args) {
		// 문제1) 5명의 별명을 입력하여 ArrayList에 저장하고
		//       별명의 길이가 제일 긴 별명을 출력하시오.
		//       (단, 각 별명의 길이는 모두 다르게 입력한다.)
		
		// 문제2) 문제 1에서 별명의 길이가 같은 것을 여러개 입력했을때도 처리되도록 하시오.
		
		ArrayList<String> asList = new ArrayList<String>();
		Scanner s = new Scanner(System.in);
		
		for(int i=0; i<5; i++) {
			System.out.print((i+1)+"번째 별명을 입력하세요: ");
			String name = s.next();
			asList.add(name);
		}
		
		int max=asList.get(0).length();
		for(int i=0; i<asList.size(); i++) {
			if(max < asList.get(i).length()) {
				max = asList.get(i).length();
			}
		}
		
		for(int i=0; i<asList.size(); i++) {
			if(max == asList.get(i).length()) {
				System.out.println(asList.get(i));
			}
		}

	}

}