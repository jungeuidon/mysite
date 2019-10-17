package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.GusetbookService;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	GusetbookService guestbookService;
	
	@RequestMapping("")
	public String guest(Model model) {
		List<GuestbookVo> list = guestbookService.getList();
		
		model.addAttribute("list", list);
//		System.out.println(list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute GuestbookVo vo) {
		guestbookService.insert(vo);
		
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete() {
		return "guestbook/delete";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo) {
		guestbookService.delete(vo);
		
		return "redirect:/guestbook";
	}
}