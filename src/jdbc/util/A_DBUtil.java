package jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class A_DBUtil {
	/*
	 * JDBC 드라이버를 로딩하고 Connection객체를 생성하는 메서드로 구성된 클래스
	 * 
	 */
	
	static { //DBUtil클래스가 메모리에 올라가면 한번실행됨
		try {
			// 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!");
			e.printStackTrace();
		}
	}
	
	// Connection객체를 생성하는 메서드
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "pc_11", "java");
		} catch (SQLException e) {
			System.out.println("DB연결 실패!");
			e.printStackTrace();
			return null;
		}
	}
}