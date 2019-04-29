package ibatis.member.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import ibatis.member.service.MemberServiceImpl;
import ibatis.member.vo.MemberVO;

/*
	MVC패턴에서 Controller와 View역할을 담당하는 클래스
*/
public class MemberMain {
	// Service객체 변수를 선언한다.
	private MemberServiceImpl memService;
	
	private Scanner scan;
	
	//로그출력하기위해 log4j에 있는 Logger객체 생성
	static Logger logger = Logger.getLogger(MemberMain.class);
	
	public MemberMain(){
//		memService = new MemberServiceImpl(); // service객체 생성
		memService = MemberServiceImpl.getInstance(); // service객체 생성
		scan = new Scanner(System.in);
	}

	// 메뉴를 출력하는 메서드
	public void displayMenu(){
		System.out.println();
		System.out.println("----------------------");
		System.out.println("  === 작 업 선 택 ===");
		System.out.println("  1. 자료 입력");
		System.out.println("  2. 자료 삭제");
		System.out.println("  3. 자료 수정");
		System.out.println("  4. 전체 자료 출력");
		System.out.println("  5. 자료 검색");
		System.out.println("  6. 작업 끝.");
		System.out.println("----------------------");
		System.out.print("원하는 작업 선택 >> ");
	}
	
	// 작업을 시작하는 메서드
	public void start(){
		logger.info("Log4j: 작업을 시작하는 메서드 입니다.");
		
		int choice;
		do{
			displayMenu();
			choice = scan.nextInt();
			switch(choice){
				case 1 :  // 자료 입력
					insertMember();
					break;
				case 2 :  // 자료 삭제
					deleteMember();
					break;
				case 3 :  // 자료 수정
					updateMemeber();
					break;
				case 4 :  // 전체 자료 출력
					displayMemberAll();
					break;
				case 5 :  // 자료 검색
					searchMember();
					break;
				case 6 :  // 작업 끝
					System.out.println("작업을 마칩니다.");
					break;
				default :
					System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
			}
		}while(choice!=6);
	}
	
	// 회원 추가하는 메서드
	public void insertMember(){
		boolean chk = false;
		String memId;
		do{
			System.out.println();
			System.out.println("추가할 회원 정보를 입력하세요.");
			System.out.print("회원 ID >> ");
			memId = scan.next();
			
			chk = memService.getMember(memId);
			
			if(chk==true){
				System.out.println("회원ID가 " + memId + "인 회원은 이미 존재합니다.");
				System.out.println("다시입력하세요");
			}
		}while(chk==true);
	
		System.out.print("회원 이름 >> ");
		String memName = scan.next();
		
		System.out.print("회원 전화번호 >> ");
		String memTel = scan.next();
		
		scan.nextLine(); // 입력 버퍼 비우기
		System.out.print("회원 주소 >> ");
		String memAddr = scan.nextLine();
		
		// 입력받은 정보를 VO객체에 넣는다.
		MemberVO mv = new MemberVO();
		mv.setMem_id(memId);
		mv.setMem_name(memName);
		mv.setMem_tel(memTel);
		mv.setMem_addr(memAddr);
		
		int cnt = memService.insertMember(mv);
		
		if(cnt>0){
			System.out.println(memId + "회원 추가 작업 성공");
		}else{
			System.out.println(memId + "회원 추가 작업 실패!!");
		}
	}
	
	// 회원을 삭제하는 메서드
	public void deleteMember(){
		System.out.println();
		System.out.print("삭제할 회원ID를 입력하세요 >> ");
		String memId = scan.next();
		
		int cnt = memService.deleteMember(memId);
		
		if(cnt>0){
			System.out.println(memId + "회원 삭제 성공!!");
		}else{
			System.out.println(memId + "회원 삭제 실패!!");
		}
	}
	
	//회원을 수정하는 메서드
	public void updateMemeber() {
		System.out.println();
		String memId = "";
		boolean chk = true;
		do{
			System.out.print("수정할 회원ID를 입력하세요 >> ");
			memId = scan.next();
			
			chk = memService.getMember(memId);
			if(chk==false){
				System.out.println(memId + "회원은 없는 회원입니다.");
				System.out.println("수정할 자료가 없으니 다시 입력하세요.");
			}
		}while(chk==false);
		
		System.out.println("수정할 내용을 입력하세요.");
		System.out.print("새로운 회원 이름 >> ");
		String memName = scan.next();
		
		System.out.print("새로운 회원 전화번호 >> ");
		String memTel = scan.next();
		
		scan.nextLine();
		System.out.print("새로운 회원 주소 >> ");
		String memAddr = scan.nextLine();
		
		MemberVO mv = new MemberVO();
		mv.setMem_id(memId);
		mv.setMem_name(memName);
		mv.setMem_tel(memTel);
		mv.setMem_addr(memAddr);
		
		int cnt = memService.updateMember(mv);
		if(cnt>0){
			System.out.println(memId + "회원 정보 수정 완료");
		}else{
			System.out.println(memId + "회원 정보 수정 실패!!");
		}
	}
	
	public void displayMemberAll(){
		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println(" ID     이 름       전화번호        주  소");
		System.out.println("----------------------------------------");
		
		List<MemberVO> memList = memService.getAllMemberList();
		if(memList.size()==0){
			System.out.println(" 출력할 회원이 하나도 없습니다.");
		}else{
			for(MemberVO mvo : memList){
				System.out.println(mvo.getMem_id() + "   " + mvo.getMem_name() + "   " 
					+ "  " + mvo.getMem_tel() + "    " + mvo.getMem_addr());
			}
		}
		
		System.out.println("--------------------------------------");
		System.out.println("출력작업 끝..");
	}
	
	// 회원 정보를 검색하는 메서드
	public void searchMember(){
		// 검색할 회원ID, 회원이름, 전화번호, 주소등을 입력하면
		// 입력한 정보만 사용하여 검색하는 기능을 구현하시오.
		// 주소는 입력한 값이 포함만 되어도 검색 되도록 한다.
		// 입력을 하지 않을 자료는 엔터키로 다음 입력으로 넘긴다.
		scan.nextLine();  // 입력버퍼 비우기
		System.out.println();
		System.out.println("검색할 정보를 입력하세요.");
		System.out.print("회원ID >>");
		String memId = scan.nextLine().trim();
		
		System.out.print("회원 이름 >>");
		String memName = scan.nextLine().trim();
		
		System.out.print("회원 전화번호 >>");
		String memTel = scan.nextLine().trim();
		
		System.out.print("회원 주소 >>");
		String memAddr = scan.nextLine().trim();
		
		MemberVO mv = new MemberVO();
		mv.setMem_id(memId);
		mv.setMem_name(memName);
		mv.setMem_tel(memTel);
		mv.setMem_addr(memAddr);
		
		// 입력한 정보로 검색한 내용을 출력하는 부분===================
		ArrayList<MemberVO> memList = (ArrayList<MemberVO>) memService.getSearchMember(mv);
		
		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println(" ID     이 름       전화번호        주  소");
		System.out.println("----------------------------------------");
		
		if(memList==null || memList.size()==0){
			System.out.println("검색한 자료가 하나도 없습니다.");
		}else{
			for(MemberVO memvo : memList){
				System.out.println(memvo.getMem_id() + "    "
						+ memvo.getMem_name() + "     "
						+ memvo.getMem_tel() + "     "
						+ memvo.getMem_addr());
			}
		}
		System.out.println("----------------------------------------");
	}
	
	public static void main(String[] args) {
		new MemberMain().start();
	}
}