package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.GuestBookVo;
import kr.co.itcen.mysite.vo.UserVo;


public class BoardDao {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	private int tot; //전체 레코드 수
	private int pList = 5; //페이지 당 보여지는 출력자료 수
	private int pageSu; //전체 페이지 수
	
	
	private Connection getConn() throws SQLException {
		try {

			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.1.40:3306/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "bit1234");
//			System.out.println("연결성공!");

		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}
		return conn;
	}
	
	public void insert(BoardVo vo) {
		
		try {
			
			conn = getConn();

			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?, null)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getG_No());
			pstmt.setInt(4, vo.getO_No());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setInt(6, vo.getUserNo());
			
			pstmt.executeUpdate();		
								
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public int getGno() {

		int maxGno = 0;
		
		try {
			conn = getConn();

			String sql = "select max(g_no) from board";
			pstmt = conn.prepareStatement(sql);						
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				maxGno = rs.getInt(1);		
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return maxGno;
	}
	
	public void modify(BoardVo vo) {

		
		try {
			conn = getConn();

			String sql = "update board set title=?, contents=? where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getNo());
			
			pstmt.executeUpdate();									
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	//-------------------------------------------------------------------------------------------------------------------------
	//페이징
	
	//페이지 수
	public int getPageSu() {
		pageSu = tot / pList;
		if(tot % pList >0) pageSu++;
		return pageSu;
	}
	
	public void totalList() {  //전체 건수 구하기.
		String sql = "select count(*) from board";
		
		try {
			conn = getConn();
			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			tot = rs.getInt(1); //전체 건수
			//System.out.println("전체 건수 : " + tot);
			
		} catch (Exception e) {
			System.out.println("totalList err : " + e); 
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}
	
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public List<BoardVo> getList(int page, String search) { //글 목록 불러오기

		List<BoardVo> list = new ArrayList<BoardVo>();
		String like = " ";
		
		try {	
			conn = getConn();
			
			if(search != null) {
				like= "and (title like '%"+search+"%' or contents like '%"+search+"%')";
			}
			String sql = "select b.title, a.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %H시%i분'),"
								+ " b.no, b.user_no, b.depth, b.g_no, b.status"								
								+ " from user a, board b"
								+ " where a.no = b.user_no "+ like 
								+ " order by b.g_no desc, o_no asc"
								+ " limit ?, 5";   //limit 5,5 이면 6번째부터 5개뽑아낸다.
			
			pstmt = conn.prepareStatement(sql);			
				pstmt.setInt(1, (page-1)*5 );
				
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();	
				
				vo.setTitle(rs.getString(1));
				vo.setUserName(rs.getString(2));
				vo.setHit(rs.getInt(3));
				vo.setRegDate(rs.getString(4));
				vo.setNo(rs.getInt(5));
				vo.setUserNo(rs.getInt(6));
				vo.setDepth(rs.getInt(7));
				vo.setG_No(rs.getInt(8));
				vo.setStatus(rs.getBoolean(9));
				
				list.add(vo);			
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return list;
	}
	
	public void upOderNo(int g_No, int o_No, int depth) {
		
		
		try {
			conn = getConn();

			String sql = "update board set o_no=o_no+1, depth=? where g_no = ? and o_no >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, depth);
			pstmt.setInt(2, g_No);
			pstmt.setInt(3, o_No);
			pstmt.executeQuery();		
						
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void delete(String no, String userNo) { //글 삭제  - 상태를 삭제상태로 변경 

		try {
			conn = getConn();

			String sql = "update board set status = true where no = ? and user_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, userNo);
			pstmt.executeQuery();		
						
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public BoardVo getView(int no) {  //번호당 글 불러오기
		
		BoardVo vo = null;

		try {
			conn = getConn();

			String sql = "select title, contents, user_no, hit"								
								+ " from board"
								+ " where no = ?";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				vo = new BoardVo();
				vo.setTitle(rs.getString(1));
				vo.setContents(rs.getString(2));
				vo.setUserNo(rs.getInt(3));		
				vo.setHit(rs.getInt(4));
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return vo;
	}
	
	public void readCnt(int no) { //조회수 증가
		try {
			
			conn = getConn();

			String sql = "update board set hit=hit+1 where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
						
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public BoardVo getGroup(int no) {
		BoardVo vo = null;

		try {
			conn = getConn();

			String sql = "select g_no, o_no, depth"								
								+ " from board"
								+ " where no = ?";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				vo = new BoardVo();				
				vo.setG_No(rs.getInt(1));
				vo.setO_No(rs.getInt(2));
				vo.setDepth(rs.getInt(3));
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return vo;
	}
	
	public int countGroup(int gNo) {
		
		int countGroup=0;
		
		try {
			conn = getConn();

			String sql = "select count(*)"								
								+ " from board"
								+ " where g_no = ?";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, gNo);
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {
				countGroup = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return countGroup;
	}
}
