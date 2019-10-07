package kr.co.itcen.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.security.Auth;
import kr.co.itcen.mysite.security.AuthUser;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo vo,
			BindingResult result,
			Model model) {
			
			
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			
			/* = model.addAllAttributes(result.getModel());와 같은 내용 
			Map<String, Object> m = result.getModel();
			for(String key : m.keySet()) {
				model.addAttribute("", m.get(key));				
			}*/
			return "user/join";
		}
		
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	
	//Interceptor로 빠짐
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(@ModelAttribute UserVo vo, HttpSession session, Model model) {
//		
//		UserVo userVo = userService.getUser(vo);
//		if(userVo == null) {
//			model.addAttribute("result", "fail");
//			return "user/login";
//		}
//		
//		// 로그인 처리
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}

	
	//Interceptor로 빠짐
//	@RequestMapping(value="/logout", method=RequestMethod.GET)
//	public String logout(HttpSession session) {
//		//접근 제어(ACL)
//		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser != null) {
//			session.removeAttribute("authUser");
//			session.invalidate();
//		}
//
//		return "redirect:/";
//	}
	
//	@Auth(role=Auth.Role.ADMIN)
	@Auth("USER")
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@ModelAttribute @AuthUser UserVo authUser, Model model/*, HttpSession session, @ModelAttribute UserVo vo*/) {
		
		Long no = authUser.getNo();
		authUser = userService.getUser(authUser.getNo());
		System.out.println(no);
		System.out.println(authUser);
		
		model.addAttribute("userVo", authUser);
		
//		UserVo authUser = (UserVo)session.getAttribute("authUser");   //HttpSession 사용시
//		if(authUser == null) {
//			return "redirect:/";
//		}
//		System.out.println(authUser.getEmail());
		
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, @ModelAttribute UserVo vo) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		//접근 제어
		if(authUser == null) {
			return "redirect:/";
		}
		
		vo.setNo(authUser.getNo());
		userService.update(vo); 
		session.setAttribute("authUser", vo);
		return "redirect:/";
	}
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handlerException() {
//		return "error/exception";
//	}
}
