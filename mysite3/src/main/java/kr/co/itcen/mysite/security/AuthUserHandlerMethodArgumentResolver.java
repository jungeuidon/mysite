package kr.co.itcen.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.co.itcen.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	
	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, //webRequest에 HttpRequset가 포함되어있따
			WebDataBinderFactory binderFactory) throws Exception {

		if(supportsParameter(parameter) ==false) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		
		return session.getAttribute("authUser");
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser가 없는경우, 
		if(authUser==null) {
			return false;
		}
		
		//@AuthUser가 있어도 파라미터 타입이 UserVo인지 확인
		if(parameter.getParameterType().equals(UserVo.class) ==false){
			return false;
		}
		
		return true;
	}

}
