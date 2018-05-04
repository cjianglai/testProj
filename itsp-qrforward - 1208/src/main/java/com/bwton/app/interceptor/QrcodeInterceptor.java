package com.bwton.app.interceptor;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.bwton.app.domain.ResultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义拦截器QrcodeInterceptor
 *
 * @author   lcj
 * @create    2017年12月4日
 */

@Component
public class QrcodeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	System.out.println("request.getRemoteAddr()"+ request.getRemoteAddr());
    	System.out.println("request.getRequestURI()"+ request.getRequestURI());
        System.out.println("request.getRequestURI()"+ handler.getClass().getName());
//    	request.getHeader(name)
    	String requestURI = request.getRequestURI();

    	if(requestURI.equalsIgnoreCase("/app/qrcode/get")|| requestURI.equalsIgnoreCase("/app/sym/trip/qrcode")) {
    		System.out.println("this is get requestQCode");
    		return true;
    	}else {

    		returnErrorMessage(response, "路上有点堵！系统忙，请稍后再试。");
//    		response.sendError(20,"error msg");
    		System.out.println("this is else mapping");
    		return false;
    	}
    			
//        System.out.println(">>>MyInterceptor1>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");


//        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
//        modelAndView.addObject("msg", "拦截器修改的modelAndView");
//        response.setStatus(200);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }
    
    
    private void returnErrorMessage(HttpServletResponse response, String errorMessage) throws IOException {
        ResultResponse rst = new ResultResponse();
        rst.setErrcode("0010");
        rst.setErrmsg(errorMessage);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
//Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
//Assuming your json object is **jsonObject**, perform the following, it will return your json object
        ObjectMapper mapper = new ObjectMapper();
        String jsonOfRST =  mapper.writeValueAsString(rst) ;
        out.print(jsonOfRST);
        out.flush();
        out.close();
    }

}
