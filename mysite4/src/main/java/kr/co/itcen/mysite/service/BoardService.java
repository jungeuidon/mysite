package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	public List<BoardVo> getList(int selPage) {
		return boardDao.getList(selPage);
	}
	
	public List<BoardVo> searchList(String search, int selPage) {
		return boardDao.searchList(search, selPage);
	}

	public void insert(BoardVo vo, int gno) {
		boardDao.insert(vo, gno);
	}

	public void delete(String no, String userNo) {
		boardDao.delete(no, userNo);
	}

	public int getPage() {
		int pageSu = boardDao.getPage();
		return pageSu;
	}

	public BoardVo view(String no) {
		return boardDao.view(no);
	}
	
}
