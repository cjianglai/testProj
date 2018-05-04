package com.bwton.service.qrcode.handler;

import com.bwton.data.domain.qrcode.QrcodeParam;
import com.bwton.data.domain.qrcode.constant.QrcodeContant;
import com.bwton.service.qrcode.QrcodeHandler;
import com.bwton.support.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ***********************************************************
 * Copyright © 2017 八维通科技有限公司 Inc.All rights reserved.  *
 * ***********************************************************
 *
 * @Company: www.bwton.com
 * @Generator: IntelliJ IDEA
 * @Project: itsp-parent
 * @Package: com.bwton.service.qrcode.handler
 * @Name: BusQrcodeHandler
 * @Author: zhaokaikai@bwton.com
 * @Date: 2017年11月02日 09时33分
 * @Description: BusQrcodeHandler
 */
@Slf4j
@Component("busQrcodeHandler")
public class BusQrcodeHandler implements QrcodeHandler {
    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    /**
     * @description
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-01 17:07
     * @param qrcodeParam
     * @param tripInfo
     * @param systemParams
     * @return
     */
    @Override
    public String RequestQrcode(QrcodeParam qrcodeParam,
                                Map<String,String> tripInfo,
                                Map<String,String> systemParams,
                                Map<String,Object> expandData,
                                String effectTime
                                ){

        String qrcodeRequestUrl = systemParams.get(QrcodeContant.QRCODE_REQUEST_URL);//二维码请求地址
        String userId = qrcodeParam.getUser_id();//用户ID
        String appId = systemParams.get(QrcodeContant.APP_ID);//App_Id
        String appSecret = systemParams.get(QrcodeContant.APP_SECRET);//App_Secret
        String serviceId = qrcodeParam.getService_id();//Service_id
        String cityId = qrcodeParam.getCity_id();//City_id
        String tripNo = tripInfo.get("tripNo");//tripNo
        String busi_data = qrcodeParam.getQr_code();
        String reserved = effectTime;

        try {
            log.info("【获取二维码-/app/qrcode/get】，二维码请求传入参数为：请求地址=" + qrcodeRequestUrl
                    +"，user_Id=" + userId
                    +"，app_Id=" + appId
                    +"，app_Secret=" + appSecret
                    +"，service_Id=" + serviceId
                    +"，city_Id=" + cityId
                    +"，tripNo=" + tripNo
                    +"，busi_data=" + busi_data
                    + ", reserved="+reserved);

            Map<String, Object> req = new HashMap<String, Object>();
            req.put( "user_id", userId );
            req.put( "service_id", serviceId );
            req.put( "location", cityId );
            req.put( "lng_lat", "" );
            req.put( "reserved", reserved );
            req.put( "trip_no", tripNo );
            req.put( "busi_data", qrcodeParam.getQr_code());


            String random = RandomStringUtils.random( 8, true, true );
            log.debug( "【获取二维码-/app/qrcode/get】，二维码请求参数 qrCodeUrl=" + qrcodeRequestUrl + "userId=" + userId + ", Json=" + gson.toJson( req ) );
            Map httpResult = CommonUtils.httpRequestQrcodePlatform(qrcodeRequestUrl, req, random, Map.class, appId, appSecret );
            log.debug( "【获取二维码-/app/qrcode/get】，二维码返回结果 userId=" + userId + ", Json=" + gson.toJson( httpResult ) );

            if ( httpResult == null ){
                log.error( "【获取二维码-/app/qrcode/get】，http请求的二维码 httpResult == null"  );
                return null;
            }

            Map resultData = (Map) httpResult.get("result");
            if (resultData == null){
                log.error( "【获取二维码-/app/qrcode/get】，http请求的二维码map中 result为空"  );
                return null;
            }

            String qrcode_data = (String)resultData.get("qrcode_data");
            if (StringUtils.isEmpty( qrcode_data )){
                log.error( "【获取二维码-/app/qrcode/get】，http请求的二维码map中 result中qrcode_data为空"  );
                return null;
            }

            return qrcode_data;
        } catch (Exception e) {
            log.info( "【获取二维码-/app/qrcode/get】，二维码生成, userId = " + userId + ", 二维码生成异常：" + e );
        }

        return null;
    }

    /**
     * @description
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-06 11:25
     * @param qrcodeParam  请求二维码参数
     * @param systemParams 系统参数
     * @return
     */
    @Override
    public Map<String,Object> ExpandBusiness(QrcodeParam qrcodeParam, Map<String,String> systemParams)
    {
        return null;
    }


    @Override
    public String getInOrOutNotice(int ioFlag, QrcodeParam qrcodeParam, Map<String, String> systemParams) {
        switch (ioFlag) {
            case 0: return systemParams.get(QrcodeContant.QRCODE_BUS_TIP_IN);
            case 1: return systemParams.get(QrcodeContant.QRCODE_BUS_TIP_OUT);
            default: return "";
        }
    }
}
