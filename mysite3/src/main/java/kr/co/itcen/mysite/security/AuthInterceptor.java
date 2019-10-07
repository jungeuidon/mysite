package kr.co.itcen.mysite.security;

import java.lang.annotation.ElementType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.itcen.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1. Handler 종류 (DefaultServletHttpRequestHandler, HandlerMethod)
		if( handler instanceof HandlerMethod ==false) {
			return true;
		}
		
		//2. casting 
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. @Auth가 없으면 class type에 있을 수 있으므로...
		if(auth == null) {
			
			//과제 : class type 에서 @Auth가 있는지를 확인해 봐야한다.
			if(ElementType.TYPE.getClass() ==null) {
				return false;
			}
			
			
		}
		
		//5. @Auth가 없으면 
		if(auth == null) {
			return true;
		}
		

		//6. @Auth가 class나 method에 붙어있기 때문에 인증여부를 체크한다.
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("authUser") == null) {
			response.sendRedirect(request.getContextPath()+ "/user/login");
			return false;
		}
		
		//8. Mehothod의 @Auth의  Role 가져오기
		String role = auth.value();
		
		//9. Method의 @Auth의 Role이 "USER"인 경우는 인증만 되어 있으면 모두 통과
		if(role == "USER") {
			return true;
		}
		
		
		//10. Method의 @Auth의 Role이 "ADMIN"인 경우 --과제
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser.getRole().equals("ADMIN")){
			return false;
		}
		
		return true;
//		return super.preHandle(request, response, handler);
	}
}
