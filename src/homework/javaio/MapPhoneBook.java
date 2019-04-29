package homework.javaio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class MapPhoneBook {
	/*
	 * 문제) 이름, 주소, 전화번호 속성을 갖는 Phone클래스를 만들고, 이 Phone클래스를 이용하여
	 *      전화번호 정보를 관리하는 프로그램을 완성하시오.
	 *      이 프로그램에는 전화번호를 등록, 수정, 삭제, 검색, 전체출력하는 기능이 있다.
	 *      
	 *      전체의 전화번호 정보는 Map을 이용하여 관리한다.
	 *      (key는 '이름'으로 하고 value는 'Phone클래스의 인스턴스'로 한다.)
	 *      
	 *      실행예시)
			===============================================
			   전화번호 관리 프로그램(파일로 저장되지 않음)
			===============================================
			
			  메뉴를 선택하세요.
			  1. 전화번호 등록
			  2. 전화번호 수정
			  3. 전화번호 삭제
			  4. 전화번호 검색
			  5. 전화번호 전체 출력
			  0. 프로그램 종료
			  번호입력 >> 1  <-- 직접 입력
			  
			  새롭게 등록할 전화번호 정보를 입력하세요.
			  이름 >> 홍길동  <-- 직접 입력
			  전화번호 >> 010-1234-5678  <-- 직접 입력
			  주소 >> 대전시 중구 대흥동 111  <-- 직접 입력
			  
			  메뉴를 선택하세요.
			  1. 전화번호 등록
			  2. 전화번호 수정
			  3. 전화번호 삭제
			  4. 전화번호 검색
			  5. 전화번호 전체 출력
			  0. 프로그램 종료
			  번호입력 >> 5  <-- 직접 입력
			  
			  =======================================
			  번호   이름       전화번호         주소
			  =======================================
			   1    홍길동   010-1234-5678    대전시
			   ~~~~~
			   
			  =======================================
			  출력완료...
			  
			  메뉴를 선택하세요.
			  1. 전화번호 등록
			  2. 전화번호 수정
			  3. 전화번호 삭제
			  4. 전화번호 검색
			  5. 전화번호 전체 출력
			  0. 프로그램 종료
			  번호입력 >> 0  <-- 직접 입력
			  
			  프로그램을 종료합니다...
	 */
	
	Scanner iscan = new Scanner(System.in); //정수입력
	Scanner sscan = new Scanner(System.in); //문자입력
	HashMap<String, Phone> map = new HashMap<String, Phone>();
	
	//전화번호 등록
	public void phoneReg() {
		Phone p = new Phone(); //한사람의 정보를 등록할때마다 객체 생성
		System.out.println("새롭게 등록할 전화번호 정보를 입력하세요.");
		System.out.print("이름 >> ");
		String name = sscan.next(); //이름
		System.out.print("전화번호 >> ");
		String phone = sscan.next(); //전화번호
		System.out.print("주소 >> ");
		sscan.nextLine(); //scanner버그
		String addr = sscan.nextLine(); //주소
		
		p.setName(name);
		p.setPhone(phone);
		p.setAddr(addr);
		
		map.put(name, p);
	}
	
	//전화번호 수정
	public void phoneMod() {
		System.out.println("전화번호를 수정하고싶으시면 이름을 입력하세요");
		System.out.print("이름 >> ");
		String name = sscan.next();
		
		Set<String> keySet = map.keySet();
		
		for(String key : keySet) {
			if(key.equals(name)) {
				Phone p = new Phone();
				System.out.println("수정할 전화번호를 입력하세요");
				String phone = sscan.next();
				p.setName(map.get(name).getName());
				p.setPhone(phone);
				p.setAddr(map.get(name).getAddr());
				map.put(name, p);
				break;
			}
		}
	}
	
	//전화번호 삭제
	public void phoneDel() {
		System.out.println("전화번호를 삭제하고싶으시면 이름을 입력하세요");
		System.out.print("이름 >> ");
		String name = sscan.next();
		
		Set<String> keySet = map.keySet();
		for(String key : keySet) {
			if(key.equals(name)) {
				map.remove(name); //삭제할 전화번호의 이름 넣기
				break;
			}
		}
	}
	
	//전화번호 검색
	public void phoneSurf() {
		System.out.println("검색하려면 이름을 입력하세요.");
		String name = sscan.next();
		Set<String> keySet = map.keySet();
		for(String key : keySet) {
			if(key.equals(name)) {
				System.out.print("전화번호는 >> ");
				System.out.println(map.get(key).getPhone());
				break;
			}
		}
	}
	
	//전화번호 전체 출력
	public void phoneAllPrint() {
		System.out.println("===================================");
		System.out.println("번호\t이름\t전화번호\t\t주소");
		int i=0; //번호첨자
		for(Phone p : map.values()) {
			System.out.print((++i) +"\t"+ p.getName() +"\t"+ p.getPhone() +"\t"+ p.getAddr() + "\n");
		}
		System.out.println("===================================");
	}
	
	//전화번호 파일로 저장
	public void phoneSave() {
		try {
			//오브젝트 출력용 스트림 객체 생성
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("d:/D_Other/phoneBook.bin")));
			
			//Phone클래스 쓰기 작업
			for(Phone p : map.values()) {
				oos.writeObject(p);
			}
			System.out.println("쓰기 작업 끝...");
			oos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	//전화번호 파일 불러오기
	public void phoneLoad() {
		//오브젝트 입력용 스트림 객체 생성
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("d:/D_Other/phoneBook.bin")));
			
			Object obj = null;
			
			while((obj = ois.readObject()) != null) {
				Phone p = (Phone)obj;
				map.put(p.getName(), p);
			}
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("파일이 없습니다.");
		} catch (IOException e) {
			System.out.println("더이상 불러올 객체가 없습니다.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//메인메뉴
	public void mainView() {
		phoneLoad();
		
		boolean isContinue = true;
		while(isContinue) {
			System.out.println("===================================");
			System.out.println("전화번호 관리 프로그램(파일로 저장되지 않음)");
			System.out.println("===================================");
			System.out.println("메뉴를 선택하세요.");
			System.out.println("1. 전화번호 등록");
			System.out.println("2. 전화번호 수정");
			System.out.println("3. 전화번호 삭제");
			System.out.println("4. 전화번호 검색");
			System.out.println("5. 전화번호 전체 출력");
			System.out.println("6. 전화번호 파일로 저장");
			System.out.println("0. 프로그램 종료");
			System.out.print("번호입력 >> ");
			int menu = iscan.nextInt();
			
			switch (menu) {
			case 1:
				phoneReg();
				break;
			case 2:
				phoneMod();
				break;
			case 3:
				phoneDel();
				break;
			case 4:
				phoneSurf();
				break;
			case 5:
				phoneAllPrint();
				break;
			case 6:
				phoneSave();
				break;
			case 0:
				System.out.println("프로그램을 종료합니다...");
				isContinue = false;
				break;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}
	
	public static void main(String[] args) {
		MapPhoneBook phoneBook = new MapPhoneBook();
		phoneBook.mainView();
	}
}

class Phone implements Serializable{
	private String name;
	private String phone;
	private String addr;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
}