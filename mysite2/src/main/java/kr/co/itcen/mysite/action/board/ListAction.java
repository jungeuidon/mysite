package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		BoardDao dao = new BoardDao();
		
		int selPage=1;
		
		if(request.getParameter("selpage")!= null) {
			selPage = Integer.parseInt(request.getParameter("selpage"));
		}
		String search = request.getParameter("search");
		
		List<BoardVo> list =  dao.getList(selPage, search);				///////new BoardDao
		int countGroup = 0;
		request.setAttribute("list", list);
		request.setAttribute("countGroup", countGroup);
		
		//페이징
		dao.totalList();								//new BoardDao
		int pageSu = dao.getPageSu();
		request.setAttribute("pageSu", pageSu);
//		System.out.println(pageSu);
		
		
		//답글 처리 
		request.setAttribute("list", list);
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
//		if(session.getAttribute("authUser")==null) {
//			WebUtils.redirect(request, response, request.getContextPath()+"/user?a=loginform");
//		} else {
//		}
	}
}
