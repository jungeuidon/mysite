** 부모프로젝트안에 자식프로젝트 넣기 **
  https://blog.naver.com/mantopuma/221661092655

# mysite
  mysite2(Servlet/JSP, Model2방식, MVC)
  
  mysite3(Spring MVC, XML Config)
  1) applicationContext.xml
  2) dispatcher-servlet.xml
  
  mysite4(Spring MVC, Java Config1)
  Java Config1 : 1 ) applicationContext.xml 
                 2 ) dispatcher-servlet.xml  의 정보를 Java 파일로 옮겼다.
                 -kr.co.itcen.config.app
                 -kr.co.itcen.config.web
                 -kr.co.itcen.mysite.config
  
  mysite5(Spring MVC, WebInitializer)
  ** WebInitializer : web.xml에 있는 정보를 kr.co.itcen.mysite.initializer - MysiteApplicationInitializer 로 옮겼다.
  
  mysite6(Spring MVC, Spring Boot)
  kr.co.itcen.mysite
  kr.co.itcen.mysite.config 로 설정해주었다.
  
