package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.GuestBookVo;

public class GuestBookDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
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
	
	public boolean insert(GuestBookVo vo) {
		boolean b = false;
		
		try {
			conn = getConn();
			
			String sql = "insert into guestbook values(null,?,?,?,now())";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt !=1) {
//				System.out.println("insert 실패");
			}else {
//				System.out.println("insert 성공");
				b = true;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return b;
	}

	public List<GuestBookVo> select() {
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		try {
			conn = getConn();
			
			String sql = "select no, name, password, contents, date_format(reg_date, '%Y년%m월%d일 %h시%m분%s초') from guestbook";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				GuestBookVo vo=new GuestBookVo(); 
				
				 
				vo.setNo(rs.getString(1));
				vo.setName(rs.getString(2));
				vo.setPassword(rs.getString(3));
				vo.setContents(rs.getString(4));
				vo.setReg_date(rs.getString(5));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}

	public boolean delete(GuestBookVo vo) {
		boolean b =false;
		
		try {
			conn = getConn();
			
			String sql = "delete from guestbook where no=? and password=?";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt !=1) {
//				System.out.println("del 실패");
				
			}else {
//				System.out.println("del 성공");
				b = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return b;
		
	}

	
	
	
}
