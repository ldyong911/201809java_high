package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class B_JdbcTest {
	/*
	 * 문제1) 사용자로부터 lprod_id값을 입력받아 입력한 값보다 lprod_id가 큰 자료들을 출력하시오.
	 * 
	 * 문제2) lprod_id값을 2개 입력받아서 두 값 중 작은 값부터 큰 값 사이의 자료를 출력하시오.
	 * 
	 */
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
//		//lprod_id값 입력받기(1번문제)
//		Scanner scan = new Scanner(System.in);
//		System.out.print("lprod_id값을 입력하세요: ");
//		int lprod_id = scan.nextInt();
		
		//lprod_id값 입력받기(2번문제)
		Scanner scan = new Scanner(System.in);
		System.out.println("lprod_id값을 2개 입력하세요: ");
		int lprod_id1 = scan.nextInt();
		int lprod_id2 = scan.nextInt();
		int max = Math.max(lprod_id1, lprod_id2);
		int min = Math.min(lprod_id1, lprod_id2);
		
		try {
			//드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Connection 객체
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String userId = "pc_11";
			String password = "java";
			conn = DriverManager.getConnection(url, userId, password);
			
			//Statement 객체
			stmt = conn.createStatement();
			
//			//1번문제
//			//ResultSet 객체
//			String sql = "select * from lprod where lprod_id > "+lprod_id+" ";
//			rs = stmt.executeQuery(sql);
//			while(rs.next()) {
//				System.out.println("lprod_id: " + rs.getInt("lprod_id"));
//				System.out.println("lprod_gu: " + rs.getString("lprod_gu"));
//				System.out.println("lprod_nm: " + rs.getString("lprod_nm"));
//			}
			
			//2번문제
			//ResultSet 객체
			String sql = "select * from lprod where lprod_id between "+min+" and "+max+" ";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.println("lprod_id: " + rs.getInt("lprod_id"));
				System.out.println("lprod_gu: " + rs.getString("lprod_gu"));
				System.out.println("lprod_nm: " + rs.getString("lprod_nm"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			if(rs != null) try {rs.close();} catch (SQLException e2) {e2.printStackTrace();}
			if(stmt != null) try {stmt.close();} catch (SQLException e2) {e2.printStackTrace();}			
			if(conn != null) try {conn.close();} catch (SQLException e2) {e2.printStackTrace();}
		}
	}
}