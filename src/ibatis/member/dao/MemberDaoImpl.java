package ibatis.member.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import ibatis.member.vo.MemberVO;


public class MemberDaoImpl implements IMemberDao{
	private SqlMapClient smc;
	
	//싱글톤패턴을 이용하여 객체생성
	private static MemberDaoImpl dao;
	private MemberDaoImpl() {
		Reader rd;
		try {
			rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			System.out.println("SqlMapClient객체 생성 실패!");
			e.printStackTrace();
		}
	} //생성자 private
	public static MemberDaoImpl getInstance() {
		if (dao == null) {
			dao = new MemberDaoImpl();
		}
		return dao;
	}
	
	@Override
	public int insertMember(MemberVO mv) {
		int cnt  = 0;
		try {
			Object obj = smc.insert("memberTest.insertMember", mv);
			
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		int cnt = 0;
		try {
			cnt = smc.delete("memberTest.deleteMember", memId);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updateMember(MemberVO mv) {
		int cnt = 0;
		try {
			cnt = smc.update("memberTest.updateMember", mv);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		List<MemberVO> memList = new ArrayList<MemberVO>();
		try {
			memList = smc.queryForList("memberTest.getMemberAll", memList);
		} catch (SQLException e) {
			memList = null;
			e.printStackTrace();
		}
		
		return memList;
	}

	@Override
	public boolean getMember(String memId) {
		boolean check = false;
		try {
			int count = (int)smc.queryForObject("memberTest.getMember", memId);
			
			if(count>0){
				check = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			check = false;
		}
		return check;
	}

	@Override
	public List<MemberVO> getSearchMember(MemberVO mv) {
		List<MemberVO> memList = new ArrayList<MemberVO>();
		try {
			memList = smc.queryForList("memberTest.getSearchMember", mv);
		} catch (SQLException e) {
			memList = null;
			e.printStackTrace();
		}
		return memList;
	}
}