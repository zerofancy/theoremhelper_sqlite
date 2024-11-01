package com.zerofancy.theoremhelper;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zerofancy.theoremhelper.utils.CommandUtil;
import com.zerofancy.theoremhelper.utils.PropertyConstants;

@SpringBootApplication
public class TheoremhelperApplication {
	private static final String SERVER_PORT = PropertyConstants.getPropertiesKey("server.port");
	private static final String spring_datasource_url = PropertyConstants.getPropertiesKey("spring.datasource.url");
	private static final String spring_datasource_username = PropertyConstants
			.getPropertiesKey("spring.datasource.username");
	private static final String spring_datasource_password = PropertyConstants
			.getPropertiesKey("spring.datasource.password");
	private static final String spring_datasource_driver_classname = PropertyConstants
			.getPropertiesKey("spring.datasource.driver-class-name");

	public static void main(String[] args) {
		System.out.println("公式定理查询系统已经启动。");
		System.out.println("正在检查application.properties");
		System.out.println();
		System.out.println("端口号：" + SERVER_PORT);
		System.out.println("数据库地址：" + spring_datasource_url);
		System.out.println("数据库用户名（长度）：" + spring_datasource_username.length());
		System.out.println("数据库密码（长度）：" + spring_datasource_password.length());
		System.out.println("数据库驱动类名：" + spring_datasource_driver_classname);

		SpringApplication.run(TheoremhelperApplication.class, args);
		try {
			CommandUtil.browse(new URI("http://localhost:"+SERVER_PORT+"/help.html"));
		} catch (URISyntaxException e) {
			// 
			e.printStackTrace();
		}
	}
}
