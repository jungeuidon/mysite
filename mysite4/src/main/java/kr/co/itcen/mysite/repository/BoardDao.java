package kr.co.itcen.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> getList(int selPage) {
		selPage = (selPage-1)*5;
		if(selPage < 0) {
			selPage = 0;
		}
		List<BoardVo> result =  sqlSession.selectList("board.getList", selPage);
		return result;
	}

	public List<BoardVo> searchList(String search, int selPage) {
		
		search = "%" + search + "%";
		
		selPage = (selPage-1)*5;
		if(selPage < 0) {
			selPage = 0;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("search", search);
		map.put("selPage", selPage);
		
		List<BoardVo> result =  sqlSession.selectList("board.searchList", map);
		return result;
	}

	public Boolean insert(BoardVo vo, int gno) {
		
		
		int maxGno = sqlSession.selectOne("board.maxGno");
		int gNo =  maxGno + 1;
		
		if(vo.getgNo()!=0 && vo.getgNo()==gno) {
			vo.setoNo(vo.getoNo()+1);
			System.out.println("BoardDao vo.getoNo() + 1 : " +vo.getoNo());
			vo.setDepth(vo.getoNo());
		}
		
		vo.setgNo(gNo);	
		
		int cnt = sqlSession.insert("board.insert", vo);
		
		return cnt == 1;
	}
	
//	public Boolean delete(BoardVo vo) {
//		int cnt = sqlSession.update("board.delete", vo);
//		return cnt ==1;
//	}

	public Boolean delete(String no, String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("userNo", userNo);
		int cnt = sqlSession.update("board.delete", map);
		return cnt ==1;
	}

	public int getPage() {
		int tot = 0; //전체 레코드 수
		int pList = 5; //페이지 당 보여지는 출력자료 수
		int pageSu; //전체 페이지 수
		
		tot = sqlSession.selectOne("board.pageSu"); 
		
		pageSu = tot / pList;
		if(tot % pList >0) pageSu++;
			
		return pageSu;
	}

	public BoardVo view(String no) {
		
		 BoardVo result = sqlSession.selectOne("board.view", no);
		 System.out.println(result);
		return result;
	}
}
