package kr.co.itcen.mysite.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@RequestMapping("")
	public String board(
			@RequestParam(value="search", required=false) String search,
			@RequestParam(value="selPage", required=true, defaultValue="1") int selPage, Model model) {
			
			System.out.println(selPage);
			selPage = (selPage-1)*5;
			System.out.println("selPage - 1 :" + selPage);
			
			List<BoardVo> list = boardService.getList(selPage);
			
			if(search != null) {
			list = boardService.searchList(search, selPage);	
			}
			
			//페이지
			int pageSu = boardService.getPage();
			model.addAttribute("pageSu", pageSu);
			
			model.addAttribute("list", list);
			
			
		return "board/list";
	}
	
	@RequestMapping(value="write", method=RequestMethod.GET)
	public String insert() {
		return "board/write";
	}
	
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String insert(@ModelAttribute BoardVo vo, Model model) {
		
		model.addAttribute("flag", 1);
		boardService.insert(vo);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(@RequestParam(value="no", required=false)String no,
						@RequestParam(value="uno", required=false)String uno, Model model) {
		
		boardService.delete(no, uno);
		
		return "redirect:/board";
	}
}
