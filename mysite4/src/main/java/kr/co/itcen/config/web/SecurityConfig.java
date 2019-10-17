package kr.co.itcen.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kr.co.itcen.mysite.security.AuthInterceptor;
import kr.co.itcen.mysite.security.AuthUserHandlerMethodArgumentResolver;
import kr.co.itcen.mysite.security.LoginInterceptor;
import kr.co.itcen.mysite.security.LogoutInterceptor;


/*
 * 	1. Interceptor 설정
 *  2. Argument Resolver 설정
 *
 *  3. Controller에서 RequestMapping 해주기
 */


@Configuration
@EnableWebMvc
public class SecurityConfig extends WebMvcConfigurerAdapter {

	
	@Bean
	AuthUserHandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}
	
	//ArgumentResolver
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(authUserHandlerMethodArgumentResolver());
	}
	
	
	//Interceptor
	@Bean
	public LoginInterceptor loginInterceptor() {
		
		return new LoginInterceptor();
	}
	
	@Bean
	public LogoutInterceptor logoutInterceptor() {
		
		return new LogoutInterceptor();
	}
	
	@Bean
	public AuthInterceptor authInterceptor() {
		
		return new AuthInterceptor();
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(loginInterceptor())
			.addPathPatterns("/user/auth");
		registry
			.addInterceptor(logoutInterceptor())
			.addPathPatterns("/user/logout");
		registry
			.addInterceptor(authInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/user/auth")
			.excludePathPatterns("/assets/**");
	}
//	<!-- Interceptor -->
//	<mvc:interceptors>
//		<!-- 
//		<mvc:interceptor>
//			<mvc:mapping path="/user/**"/>
//			<bean class="kr.co.itcen.mysite.interceptor.MyInterceptor02"/>
//		</mvc:interceptor>
//		-->
//		<mvc:interceptor>
//			<mvc:mapping path="/user/auth"/>
//			<bean class="kr.co.itcen.mysite.security.LoginInterceptor"/>
//		</mvc:interceptor>
//		<mvc:interceptor>
//			<mvc:mapping path="/user/logout"/>
//			<bean class="kr.co.itcen.mysite.security.LogoutInterceptor"/>
//		</mvc:interceptor>
//		<mvc:interceptor>
//			<mvc:mapping path="/**"/>
//			<mvc:exclude-mapping path="/user/auth"/>
//			<mvc:exclude-mapping path="/user/logout"/>
//			<mvc:exclude-mapping path="/assets/**"/>
//			<bean class="kr.co.itcen.mysite.security.AuthInterceptor"/>
//		</mvc:interceptor>
//	</mvc:interceptors>
}
