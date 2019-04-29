package javarmiserver.inter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javarmiserver.vo.FileInfoVO;
import javarmiserver.vo.TestVO;

// RMI용 인터페이스는 Remote를 상속해야 한다.
public interface RemoteInterface extends Remote{
	// 이 인터페이스에서 선언된 모든 메서드는 RemoteException을 throws해야 함.
	
	// 이 곳에서 선언된 메서드의 파라미터 변수는 클라이언트에서 보내오는 데이터가 저장되고,
	// 반환값은 서버에서 클라이언트 쪽으로 보내는 데이터가 저장된다.
	
	public int doRemotePrint(String str) throws RemoteException;
	
	public void doPrintList(ArrayList<String> list) throws RemoteException;
	
	public void doPrintVo(TestVO vo) throws RemoteException;
	
	// 파일 전송을 위한 메서드
	// 여러개의 파일을 전송하고 싶으면 파라미터변수를 배열로
	// 한개의 파일을 전송하고 싶으면 파라미터변수를 단일변수로 설정한다.
	public void setFiles(FileInfoVO[] fInfo) throws RemoteException;
	
}