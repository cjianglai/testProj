package com.bwton.app.property.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "QRCODE", locations="classpath:application-apiclient.properties")
// PropertySource默认取application.properties
//@PropertySource(value = "classpath:application-sysparam.properties")
@Data
public class ApiClientPropertiesConfig {

	public Map<String, String> msxGQ = new HashMap<String, String>();

}
