package kr.co.itcen.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@PropertySource("classpath:kr/co/itcen/mysite/config/web/properties/multipart.properties")
public class FileuploadConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;
	
	
	//MultipartResolver
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		multipartResolver.setMaxUploadSize(env.getProperty("maxUploadSize", Integer.class));
		multipartResolver.setMaxInMemorySize(env.getProperty("maxInMemorySize", Integer.class));
		multipartResolver.setDefaultEncoding(env.getProperty("defaultEncoding"));
		
		
		return multipartResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(env.getProperty("resourceMapping") + "/**")
			.addResourceLocations("file:" + env.getProperty("uploadsLocation"));  
		
//		 FileuploadService 에 @Autowired
//							 Environmetn env; 를 해주면 자동으로 찾아간다.
		
		/* <mvc:resources location="file:/uploads/" mapping="/images/**"/> */
	}
}
