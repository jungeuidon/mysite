package kr.co.itcen.mysite.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@RequestMapping("")
	public String board(
			@RequestParam(value="search", required=false) String search,
			@RequestParam(value="selPage", required=true, defaultValue="1") int selPage, Model model) {
			

			
			List<BoardVo> list = boardService.getList(selPage);
			
			if(search != null) {
			list = boardService.searchList(search, selPage);	
			}
			
			//페이지
			int pageSu = boardService.getPage();
			model.addAttribute("pageSu", pageSu);
			model.addAttribute("search", search);
			model.addAttribute("selPage", selPage);
			model.addAttribute("list", list);
			
			
		return "board/list";
	}
	
	@RequestMapping(value="write", method=RequestMethod.GET)
	public String insert() {
		
		return "board/write";
	}
	
	
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String insert(@ModelAttribute BoardVo vo, Model model,
						@RequestParam(value="gNo", required=false, defaultValue="0")int gNo,
						HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");

		vo.setUserNo(authUser.getNo().intValue());
		model.addAttribute("flag", 1);
		System.out.println("controller :  " + gNo);
		boardService.insert(vo, gNo);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(@RequestParam(value="no", required=false)String no,
						@RequestParam(value="uno", required=false)String uno, Model model) {
		
		boardService.delete(no, uno);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="view", method=RequestMethod.GET)
	public String view(@RequestParam(value="no", required=false)String no,
						@RequestParam(value="search", required=false)String search,
						@RequestParam(value="selPage", required=true)int selPage,
						Model model) {

		BoardVo vo = boardService.view(no);
		System.out.println("controller : " + vo);
		
		model.addAttribute("search", search);
		model.addAttribute("selPage", selPage);
		
		model.addAttribute("vo", vo);
		
		return "board/view";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modify(@RequestParam(value="no", required=false)String no,
						Model model) {

		BoardVo vo = boardService.view(no);
		
		model.addAttribute("vo", vo);
		
		return "board/modify";
	}
}
