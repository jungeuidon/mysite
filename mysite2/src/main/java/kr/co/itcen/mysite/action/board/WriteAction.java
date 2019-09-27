package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ParseConversionEvent;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		int userNo = Integer.parseInt(authUser.getNo());
		
		BoardDao boardDao = new BoardDao();
		int flag = Integer.parseInt(request.getParameter("flag"));
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		System.out.println("내용 " + contents);
		
		BoardVo vo = new BoardVo();
		vo.setUserNo(userNo);
		vo.setTitle(title);
		vo.setContents(contents);
		
		if(flag==1) {
			int gNo = Integer.parseInt(request.getParameter("g_no"));
			int oNo = Integer.parseInt(request.getParameter("o_no")) + 1;
			int depth = oNo;
			
			System.out.println(gNo + "  " + oNo + " " + depth);
			vo.setG_No(gNo);
			vo.setO_No(oNo);
			vo.setDepth(depth);
			
			boardDao.upOderNo(gNo, oNo, depth);
			
		} else {
			int groupNo = boardDao.getGno();
			vo.setG_No(groupNo+1);
			vo.setO_No(0);
			vo.setDepth(0);
		}
		new BoardDao().insert(vo);

		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}
}
