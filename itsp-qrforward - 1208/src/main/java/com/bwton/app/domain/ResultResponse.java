package com.bwton.app.domain;

import java.util.Collection;
import java.util.Map;

import com.yanyan.web.utils.DataResponse;
import lombok.Data;
import org.springframework.ui.Model;

@Data
public class ResultResponse   {
	String errcode;
	String errmsg;
	boolean success;
	Object result;

	public ResultResponse(){}

	public ResultResponse(DataResponse dataResponse){
		this.errcode=dataResponse.getErrcode();
		this.errmsg=dataResponse.getMessage();
		this.success=dataResponse.isSuccess();
		if(dataResponse.isSuccess()){
			this.result=dataResponse.getAttribute("result");
		}
	}


}
