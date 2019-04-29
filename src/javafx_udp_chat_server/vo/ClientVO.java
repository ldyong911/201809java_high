package javafx_udp_chat_server.vo;
/**
 * 클라이언트 정보를 담기 위한 VO
 * @author HelloJava
 *
 */
public class ClientVO {
	private String ipAddr; 
	private int portNum;
	public ClientVO(String ipAddr, int portNum) {
		super();
		this.ipAddr = ipAddr;
		this.portNum = portNum;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public int getPortNum() {
		return portNum;
	}
	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}
}
