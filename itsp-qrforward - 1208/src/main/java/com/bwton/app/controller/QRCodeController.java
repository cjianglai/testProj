package com.bwton.app.controller;

import com.bwton.Constants;
import com.bwton.PubECDef;
import com.bwton.app.domain.ResultResponse;
import com.bwton.data.domain.qrcode.QrcodeParam;
import com.bwton.data.domain.qrcode.constant.QrcodeContant;
import com.bwton.service.qrcode.QrcodeService;
//import com.bwton.util.NumberUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yanyan.web.utils.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ***********************************************************
 * Copyright © 2017 八维通科技有限公司 Inc.All rights reserved.  *
 * ***********************************************************
 *
 * @Company: www.bwton.com
 * @Generator: IntelliJ IDEA
 * @Project: itsp-parent
 * @Package: com.bwton.app.controller.qrcode
 * @Name: QRCodeController
 * @Author: zhaokaikai@bwton.com
 * @Date: 2017年11月01日 13时32分
 * @Description: QRCodeController
 * 二维码类型：00：不限 01：地铁 02：公交 03：自行车 04：停车场（含咪表） 05：出租车 06：高铁 07：飞机 08：轮船
 */

@Slf4j
@Controller
@RequestMapping("/app/qrcode")
public class QRCodeController {
    @Autowired
    QrcodeService qrcodeService;
    @Autowired
    private Gson gson;

    @RequestMapping("/get")
    @ResponseBody
    public ResultResponse getQRCode(
            @RequestHeader(value = "userId", required = false) String userId,
            @RequestHeader(value = "cityId", required = false, defaultValue = Constants.DefaultCityCode.CITY_WUXI_ID) String cityId,
            @RequestHeader(value = "version", required = false) String appVersion,
            @RequestHeader(value = "bundleId", required = false) String bundleId,
            @RequestBody QrcodeParam param) {
        //############################## 传入参数说明 ########################################
        //参数传入示例：
//         "full_dig_user_id":null,
//         "user_id":"6850049924269056",
//         "user_type":"1",
//         "city_id":"3202",
//         "service_id":"01",
//         "app_version":"1.2.1",
//         "beta_status":null,
//         "bundle_id":"com.bwton.app",
//         "id_no":null,
//         "qr_code":null,
//         "request_date":null,
//         "stationId":0,
//         "lng":0.0,
//         "lat":0.0
        try {
            log.info("【获取二维码-/app/qrcode/get】,接口传入的参数信息为param={},userId={},cityId={},version={},bundleId={}"
                    , gson.toJson(param)
                    , userId
                    , cityId
                    , appVersion
                    , bundleId);

            //region 校验请求二维码传入参数是否有效

            //step-1 判断appVersion的有效 step-2 判断bundleId的有效性 step-3 判断param的有效性 step-4 判断service_id的有效性
            //step-5 判断user_type的有效性
            if (StringUtils.isEmpty(appVersion)||StringUtils.isEmpty(bundleId)||null == param||StringUtils.isEmpty(param.getService_id())
                    ||StringUtils.isEmpty(param.getUser_type())) {
                return new ResultResponse(DataResponse.failure(PubECDef.EC_PARAMS_ERROR, PubECDef.EC_PARAMS_ERROR_DESC));
            }
            //endregion
            //region 定义请求二维码传入参数

            //用户ID
            if (!StringUtils.isEmpty(userId)) {
                param.setUser_id(userId);
            }
            //城市ID app版本号 包id
            param.setCity_id(cityId);
            param.setApp_version(appVersion);
            param.setBundle_id(bundleId);
            //endregion

            DataResponse dataResponse =null;
            dataResponse = qrcodeService.getQrcode(param);

            log.info("【获取二维码-/app/qrcode/get】,接口返回的结果数据信息为：{}", gson.toJson(dataResponse));

            return new ResultResponse(dataResponse);

        } catch (Exception ex) {
            log.error("【获取二维码-/app/qrcode/get】,方法异常", ex);
            ex.printStackTrace();
            return new ResultResponse(DataResponse.failure(PubECDef.EC_FAIL, PubECDef.EC_FAIL_DESC));
        }
    }
}