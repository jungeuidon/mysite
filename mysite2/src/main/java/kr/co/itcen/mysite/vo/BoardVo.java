package kr.co.itcen.mysite.vo;

public class BoardVo {
	private int no;
	private int userNo;
	private String userName;
	private String title;
	private String contents;
	private int hit;
	private String regDate;
	private int g_No;
	private int o_No;
	private int depth;
	private boolean status;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getG_No() {
		return g_No;
	}
	public void setG_No(int g_No) {
		this.g_No = g_No;
	}
	public int getO_No() {
		return o_No;
	}
	public void setO_No(int o_No) {
		this.o_No = o_No;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}