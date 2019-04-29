package homework.javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdInfoJdbc {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "pc_11";
	String password = "java";
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public ObservableList<Lprod> lprod_list(){
		ObservableList<Lprod> list = FXCollections.observableArrayList();
		
		try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url, user, password);
			
			String sql = "select * from lprod";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery(sql);
			while(rs.next()) {
				Lprod lprod = new Lprod(rs.getString(1), rs.getString(2), rs.getString(3));
				list.add(lprod);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try {rs.close();} catch (Exception e2) {e2.printStackTrace();}
			if(pstmt != null) try {pstmt.close();} catch (Exception e2) {e2.printStackTrace();}
			if(conn != null) try {conn.close();} catch (Exception e2) {e2.printStackTrace();}
		}
		
		return list;
	}
	
	public ObservableList<Prod> prod_list(String lprod_nm){
		ObservableList<Prod> list = FXCollections.observableArrayList();
		
		try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url, user, password);
			
			String sql = "select lprod_gu from lprod where lprod_nm='"+lprod_nm+"' ";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery(sql);
			while(rs.next()) {
				sql = "select distinct * from lprod a, prod b "
						+ "where a.lprod_gu = b.prod_lgu and a.lprod_gu = '"+rs.getString("lprod_gu")+"'";
				ResultSet rs2 = pstmt.executeQuery(sql);
				while(rs2.next()) {
					Prod prod = new Prod(rs2.getString("prod_id"), rs2.getString("prod_name"), rs2.getString("prod_lgu")
							, rs2.getString("prod_buyer"), rs2.getString("prod_cost"), rs2.getString("prod_price")
							, rs2.getString("prod_sale"), rs2.getString("prod_outline"), rs2.getString("prod_detail"));
					list.add(prod);
				}
				
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try {rs.close();} catch (Exception e2) {e2.printStackTrace();}
			if(pstmt != null) try {pstmt.close();} catch (Exception e2) {e2.printStackTrace();}
			if(conn != null) try {conn.close();} catch (Exception e2) {e2.printStackTrace();}
		}
		
		return list;
	}
	
	public class Lprod{
		private String lprod_id;
		private String lprod_gu;
		private String lprod_nm;
		
		public Lprod(String lprod_id, String lprod_gu, String lprod_nm) {
			super();
			this.lprod_id = lprod_id;
			this.lprod_gu = lprod_gu;
			this.lprod_nm = lprod_nm;
		}
		
		public String getLprod_id() {
			return lprod_id;
		}
		public void setLprod_id(String lprod_id) {
			this.lprod_id = lprod_id;
		}
		public String getLprod_gu() {
			return lprod_gu;
		}
		public void setLprod_gu(String lprod_gu) {
			this.lprod_gu = lprod_gu;
		}
		public String getLprod_nm() {
			return lprod_nm;
		}
		public void setLprod_nm(String lprod_nm) {
			this.lprod_nm = lprod_nm;
		}
	}
	
	public class Prod{
		private String prod_id;
		private String prod_name;
		private String prod_lgu;
		private String prod_buyer;
		private String prod_cost;
		private String prod_price;
		private String prod_sale;
		private String prod_outline;
		private String prod_detail;
		
		public Prod(String prod_id, String prod_name, String prod_lgu, String prod_buyer, String prod_cost,
				String prod_price, String prod_sale, String prod_outline, String prod_detail) {
			super();
			this.prod_id = prod_id;
			this.prod_name = prod_name;
			this.prod_lgu = prod_lgu;
			this.prod_buyer = prod_buyer;
			this.prod_cost = prod_cost;
			this.prod_price = prod_price;
			this.prod_sale = prod_sale;
			this.prod_outline = prod_outline;
			this.prod_detail = prod_detail;
		}
		
		public String getProd_id() {
			return prod_id;
		}
		public void setProd_id(String prod_id) {
			this.prod_id = prod_id;
		}
		public String getProd_name() {
			return prod_name;
		}
		public void setProd_name(String prod_name) {
			this.prod_name = prod_name;
		}
		public String getProd_lgu() {
			return prod_lgu;
		}
		public void setProd_lgu(String prod_lgu) {
			this.prod_lgu = prod_lgu;
		}
		public String getProd_buyer() {
			return prod_buyer;
		}
		public void setProd_buyer(String prod_buyer) {
			this.prod_buyer = prod_buyer;
		}
		public String getProd_cost() {
			return prod_cost;
		}
		public void setProd_cost(String prod_cost) {
			this.prod_cost = prod_cost;
		}
		public String getProd_price() {
			return prod_price;
		}
		public void setProd_price(String prod_price) {
			this.prod_price = prod_price;
		}
		public String getProd_sale() {
			return prod_sale;
		}
		public void setProd_sale(String prod_sale) {
			this.prod_sale = prod_sale;
		}
		public String getProd_outline() {
			return prod_outline;
		}
		public void setProd_outline(String prod_outline) {
			this.prod_outline = prod_outline;
		}
		public String getProd_detail() {
			return prod_detail;
		}
		public void setProd_detail(String prod_detail) {
			this.prod_detail = prod_detail;
		}
	}
}