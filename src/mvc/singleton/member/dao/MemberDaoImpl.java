package mvc.singleton.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mvc.singleton.member.vo.MemberVO;
import mvc.util.C_DBUtil;

public class MemberDaoImpl implements IMemberDao{
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//싱글톤패턴을 이용하여 객체생성
	private static MemberDaoImpl dao;
	private MemberDaoImpl() {} //생성자 private
	public static MemberDaoImpl getInstance() {
		if (dao == null) {
			dao = new MemberDaoImpl();
		}
		return dao;
	}
	
	/**
	 * 사용한 자원을 반납하는 메서드
	 */
	public void disConnect(){
		if(rs!=null)try{ rs.close(); }catch(SQLException ee){}
		if(stmt!=null)try{ stmt.close(); }catch(SQLException ee){}
		if(pstmt!=null)try{ pstmt.close(); }catch(SQLException ee){}
		if(conn!=null)try{ conn.close(); }catch(SQLException ee){}
	}
	
	@Override
	public int insertMember(MemberVO mv) {
		int cnt  = 0;
		try {
			conn = C_DBUtil.getConnection();
			String sql = "insert into mymember(mem_id, mem_name, mem_tel, mem_addr) values (?,?,?,?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getMem_id());
			pstmt.setString(2, mv.getMem_name());
			pstmt.setString(3, mv.getMem_tel());
			pstmt.setString(4, mv.getMem_addr());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} finally {
			disConnect();
		}
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		int cnt = 0;
		try {
			conn = C_DBUtil.getConnection();
			String sql = "delete from mymember where mem_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} finally {
			disConnect();
		}
		return cnt;
	}

	@Override
	public int updateMember(MemberVO mv) {
		int cnt = 0;
		try {
			conn = C_DBUtil.getConnection();
			String sql = "update mymember set mem_name = ?, mem_tel = ?, mem_addr = ? where mem_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mv.getMem_name());
			pstmt.setString(2, mv.getMem_tel());
			pstmt.setString(3, mv.getMem_addr());
			pstmt.setString(4, mv.getMem_id());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		} finally{
			disConnect();
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		ArrayList<MemberVO> memList = new ArrayList<MemberVO>();
		try {
			conn = C_DBUtil.getConnection();
			String sql = "select * from mymember ";
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			// 반복문 안에서는 가져온 레코드 하나 하나를
			// VO에 맵핑하고 이 VO를 List에 추가한다.
			while(rs.next()){
				MemberVO mvo = new MemberVO();
				mvo.setMem_id(rs.getString("mem_id"));
				mvo.setMem_name(rs.getString("mem_name"));
				mvo.setMem_tel(rs.getString("mem_tel"));
				mvo.setMem_addr(rs.getString("mem_addr"));
				
				memList.add(mvo);
			}
			
		} catch (SQLException e) {
			memList = null;
			e.printStackTrace();
		} finally {
			disConnect();
		}
		
		return memList;
	}

	@Override
	public boolean getMember(String memId) {
		boolean check = false;
		try {
			conn = C_DBUtil.getConnection();
			String sql = "select count(*) cnt from mymember where mem_id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			rs = pstmt.executeQuery();
			int count = 0;
			if(rs.next()){
				count = rs.getInt("cnt");
			}
			if(count>0){
				check = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			check = false;
		} finally{
			disConnect();
		}
		return check;
	}

	@Override
	public List<MemberVO> getSearchMember(MemberVO mv) {
		ArrayList<MemberVO> memList = new ArrayList<MemberVO>();
		try {
			conn = C_DBUtil.getConnection();
			String sql = "select * from mymember where 1=1 "; //밑에 항목들을 추가하기위해 항상 참인 조건 where 1=1를 추가함(검색에 영향을 미치지않음)
			if(mv.getMem_id()!=null && !mv.getMem_id().equals("")){
				sql += " and mem_id = ? "; 
			}
			if(mv.getMem_name()!=null && !mv.getMem_name().equals("")){
				sql += " and mem_name = ? ";
			}
			if(mv.getMem_tel()!=null && !mv.getMem_tel().equals("")){
				sql += " and mem_tel = ? ";
			}
			if(mv.getMem_addr()!=null && !mv.getMem_addr().equals("")){
				sql += " and mem_addr like '%' || ? || '%' ";
			}
			
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			if(mv.getMem_id()!=null && !mv.getMem_id().equals("")){
				 pstmt.setString(index++, mv.getMem_id());
			}
			if(mv.getMem_name()!=null && !mv.getMem_name().equals("")){
				pstmt.setString(index++, mv.getMem_name());
			}
			if(mv.getMem_tel()!=null && !mv.getMem_tel().equals("")){
				pstmt.setString(index++, mv.getMem_tel());
			}
			if(mv.getMem_addr()!=null && !mv.getMem_addr().equals("")){
				pstmt.setString(index, mv.getMem_addr());
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				MemberVO memVo = new MemberVO();
				memVo.setMem_id(rs.getString("mem_id"));
				memVo.setMem_name(rs.getString("mem_name"));
				memVo.setMem_tel(rs.getString("mem_tel"));
				memVo.setMem_addr(rs.getString("mem_addr"));
				
				memList.add(memVo);
			}
			
		} catch (SQLException e) {
			memList = null;
			e.printStackTrace();
		} finally {
			disConnect();
		}
		
		return memList;
	}
}