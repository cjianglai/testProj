package com.bwton.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import com.bwton.app.property.config.SysParamPropertiesConfig;
import com.bwton.app.property.config.ApiClientPropertiesConfig;

@SpringBootApplication(exclude = {
DataSourceAutoConfiguration.class,
DataSourceTransactionManagerAutoConfiguration.class,
HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages={"com.bwton"})  
@EnableConfigurationProperties({SysParamPropertiesConfig.class,ApiClientPropertiesConfig.class})
@ServletComponentScan
public class ItspQrforwardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItspQrforwardApplication.class, args);
	}
}
