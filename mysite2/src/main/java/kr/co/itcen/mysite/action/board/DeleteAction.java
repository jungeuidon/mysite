package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		String userNo = request.getParameter("uno");
		String contNo = request.getParameter("no");
		
		String sessionNum = authUser.getNo();
		
		
		if(sessionNum.equals(userNo)) {
			new BoardDao().delete(contNo, userNo);
//			System.out.println("삭제합니당");
		}else {
			System.out.println("authUser.getNo()" + authUser.getNo());
			System.out.println("userNo" + userNo);
			System.out.println("다르자나 사람이2 ");
		}
		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}

}
