package com.bwton.app.property.config;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "QRCODE", locations="classpath:application-sysparam.properties")
// PropertySource默认取application.properties
//@PropertySource(value = "classpath:application-sysparam.properties")
@Data
public class SysParamPropertiesConfig {

	public String title3;
	public Map<String, String> app_id = new HashMap<String, String>();
	public Map<String, String> app_secret = new HashMap<String, String>();
	public Map<String, String> default_get_qrcode = new HashMap<String, String>();
	public Map<String, String> wx3202 = new HashMap<String, String>();
	public Map<String, String> fz3501 = new HashMap<String, String>();
}
