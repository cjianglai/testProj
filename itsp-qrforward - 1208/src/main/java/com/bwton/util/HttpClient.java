package com.bwton.util;

import org.apache.commons.httpclient.HttpMethodBase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Title: OrderController
 *
 * @author zhaokaikai@bwton.com
 *         date 2017年07月17日 10:20
 * @version V1.0
 *          用于提供HttpClient请求的过程中的工具类
 */
public class HttpClient {

    /**
     * 用于返回HttpClient请求返回的信息展示
     * 解决异常问题：org.apache.commons.httpclient.HttpMethodBase- Going to buffer response body of large or unknown size. Using getResponseBodyAsStream instead is recommended.
     * Create By zhaokaikai@bwton.com
     *  @param httpMethod
     * @return
     */
    public static String getResponse(HttpMethodBase httpMethod)
    {
        StringBuffer result = new StringBuffer();

        try {
            InputStream inputStream = httpMethod.getResponseBodyAsStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String str = "";
            while ((str = br.readLine()) != null) {
                result.append(str);
            }
        }
        catch (Exception ex)
        {

        }

        return result.toString();
    }
}
