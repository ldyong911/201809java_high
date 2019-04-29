package mvc.singleton.member.service;

import java.util.List;

import mvc.singleton.member.dao.MemberDaoImpl;
import mvc.singleton.member.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {
	// 사용할 DAO의 객체변수를 선언한다.
	private MemberDaoImpl memDao;
	
	//싱글톤패턴을 이용하여 객체생성
	private static MemberServiceImpl service;
	private MemberServiceImpl() {
//		memDao = new MemberDaoImpl(); // Dao객체 생성
		memDao = MemberDaoImpl.getInstance(); // Dao객체 생성
	}
	public static MemberServiceImpl getInstance() {
		if(service == null) {
			service = new MemberServiceImpl();
		}
		return service;
	}
	
	// 각 메서드에서는 생성된 Dao객체를 이용하여
	// 작업에 맞는 Dao객체의 메서드를 호출한다.
	@Override
	public int insertMember(MemberVO mv) {
		return memDao.insertMember(mv);
	}

	@Override
	public int deleteMember(String memId) {
		return memDao.deleteMember(memId);
	}

	@Override
	public int updateMember(MemberVO mv) {
		return memDao.updateMember(mv);
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		return memDao.getAllMemberList();
	}

	@Override
	public boolean getMember(String memId) {
		return memDao.getMember(memId);
	}

	@Override
	public List<MemberVO> getSearchMember(MemberVO mv) {
		return memDao.getSearchMember(mv);
	}
}