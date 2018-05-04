package com.bwton.app.controller;

import com.bwton.app.property.config.SysParamPropertiesConfig;
import com.bwton.commonEcdef.PubECDef;
import com.bwton.data.domain.qrcode.QrcodeParam;
import com.bwton.service.qrcode.QrcodeService;
import com.bwton.util.DateUtil;
import com.google.gson.Gson;
import com.yanyan.web.utils.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huayq on 2017/4/26.
 */
@Slf4j
@Controller
@RequestMapping("/app/sym/trip")
public class AppTripController {

    @Autowired
    private Gson gson;
    @Autowired
    private QrcodeService qrcodeService;

    @Autowired
    private SysParamPropertiesConfig sysParamPropertiesConfig;

    public static void main(String[] args) throws Exception {
        String sysStartTime = "03:00:00";
        String sysEndTime = "04:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS");
        Date start = sdf.parse(sysStartTime);
        Date end = sdf.parse(sysEndTime);
        Date cur = new Date();
        //String curDate  = DateUtil.getTime(cur);
        String curDate = "03:01:00";
        if (sysStartTime.compareTo(curDate) > 0 || sysEndTime.compareTo(curDate) < 0) {
            System.out.print("0102" + "时间必须在:" + sysStartTime + "---" + sysEndTime);
        }

    }


    /**
     * 运营终端代打/巡检码
     *
     * @param baseData
     * @param request
     * @return
     */
    @RequestMapping("/qrcode")
    @ResponseBody
    public Model qrcode(@RequestBody QrcodeParam baseData, HttpServletRequest request) {
        String sysStartTime;
        String sysEndTime;

        log.info("运营终端代打/巡检码，传入参数：" + gson.toJson(baseData));
        String cityId = request.getHeader("cityId");
        if (StringUtils.isEmpty(cityId)) {
            log.error("运营终端代打/巡检码，城市id为空");
            return DataResponse.failure(PubECDef.EC_CITY_ID_ERROR, PubECDef.EC_CITY_ID_ERROR_DESC);
        }
        log.info("运营终端代打/巡检码,cityId=" + cityId);
        baseData.setCity_id(cityId);
        baseData.setBeta_status("0");
        //业务来源 小黄机
        baseData.setBusiness_source(2);

        try {
            //巡检，需要进行时间范围校验
            if (baseData.getUser_type().trim().equals("2")) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS");
                Date cur = new Date();
                String curDate = DateUtil.getTime(cur);
                Map<String, String> systemParams = new HashMap<String, String>();
                systemParams = sysParamPropertiesConfig.getFz3501();
//                sysStartTime = systemParamService.getParamVal(QrcodeContant.GATE_MACHINE_CHECK_START_TIME, Integer.valueOf(cityId));
//                sysEndTime = systemParamService.getParamVal(QrcodeContant.GATE_MACHINE_CHECK_END_TIME, Integer.valueOf(cityId));
                sysStartTime = systemParams.get("GATE_MACHINE_CHECK_START_TIME");
                sysEndTime = systemParams.get("GATE_MACHINE_CHECK_END_TIME");

                if (sysStartTime == null || sysStartTime.length() == 0 || sysEndTime == null || sysEndTime.length() == 0) {
                    return DataResponse.failure("0102", "巡检维护时间没有配置，不可打印测试凭证");
                }
                if (sysStartTime.compareTo(curDate) > 0 || sysEndTime.compareTo(curDate) < 0) {
                    log.info("运营终端代打/巡检码,非巡检维护时间不可打印测试凭证。巡检时间段（" + sysStartTime + "-" + sysEndTime + ")");
                    return DataResponse.failure("0102", "非巡检维护时间不可打印测试凭证。巡检时间段（" + sysStartTime + "-" + sysEndTime + ")");
                }
            }
            DataResponse dataResponse = qrcodeService.getQrcode(baseData);
            if (dataResponse.isSuccess()) {
                log.info("运营终端代打/巡检码成功");
                Map<String, Object> result = (Map<String, Object>) dataResponse.get("result");
                String qrcodeString = (String) result.get("qrcode_data");
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("QRCodeString", qrcodeString);
                return DataResponse.successWithResult(resultMap);
            } else {
                log.info("运营终端代打/巡检码失败: {}", dataResponse.getMessage());
                return DataResponse.failure(PubECDef.EC_FAIL, PubECDef.EC_FAIL_DESC);
            }

        } catch (Exception e) {
            log.error("运营终端代打/巡检码失败，传入参数：" + gson.toJson(baseData), e);
            return DataResponse.failure(PubECDef.EC_FAIL, PubECDef.EC_FAIL_DESC);
        }

    }


//    @RequestMapping("/list")
//    @ResponseBody
//    public Model find(@RequestBody TripQuery query, HttpServletRequest request) {
//        String cityId = request.getHeader("cityId");
//        try {
//            if (StringUtils.isEmpty(cityId)) {
//                log.error("【运营终端】查询行程，城市id为空");
//                return DataResponse.failure(PubECDef.EC_CITY_ID_ERROR, PubECDef.EC_CITY_ID_ERROR_DESC);
//            }
//            if (StringUtils.isEmpty(query.getService_id())) {
//                log.error("【运营终端】查询行程，服务id为空");
//                return DataResponse.failure(PubECDef.EC_SERVICE_ID_ERROR, PubECDef.EC_SERVICE_ID_DESC);
//            }
//            query.setCity_id(Integer.parseInt(cityId));
//            log.info("【运营终端】查询行程，查询条件：" + gson.toJson(query));
//            Map<String, Object> result = new HashMap<String, Object>();
//            result.put("trips", tripService.findSymTripList(query));
//            return DataResponse.successWithResult(result);
//        } catch (Exception e) {
//            log.error("【运营终端】查询行程失败，查询条件：" + gson.toJson(query), e);
//            return DataResponse.failure(PubECDef.EC_FAIL, PubECDef.EC_FAIL_DESC);
//        }
//    }

//    @RequestMapping("/clerk/confirm")
//    @ResponseBody
//    public Model find(@RequestBody TripData data, HttpServletRequest request) {
//        String cityId = request.getHeader("cityId");
//        try {
//            if (StringUtils.isEmpty(cityId)) {
//                log.error("【运营终端】运营人员确认，城市id为空");
//                return DataResponse.failure(PubECDef.EC_CITY_ID_ERROR, PubECDef.EC_CITY_ID_ERROR_DESC);
//            }
//            data.setCityId(cityId);
//            log.info("【运营终端】运营人员确认,传入参数：" + gson.toJson(data));
//            if (StringUtils.isEmpty(data.getUser_id())) {
//                log.error("【运营终端】运营人员确认，userId为空");
//                return DataResponse.failure(PubECDef.EC_USER_ID_ERROR, PubECDef.EC_USER_ID_ERROR_DESC);
//            }
//            if (StringUtils.isEmpty(data.getTrip_no())) {
//                log.error("【运营终端】运营人员确认，tripno为空");
//                return DataResponse.failure(PubECDef.EC_TRIP_NO_NULL_ERROR, PubECDef.EC_TRIP_NO_NULL_DESC);
//            }
//            if (StringUtils.isEmpty(data.getDirection())) {
//                log.error("【运营终端】运营人员确认，进出站标识为空");
//                return DataResponse.failure(PubECDef.EC_DIRECTION_ERROR, PubECDef.EC_DIRECTION_DESC);
//            }
//            if (StringUtils.isEmpty(data.getScan_time())) {
//                log.error("【运营终端】运营人员确认，扫码时间为空");
//                return DataResponse.failure(PubECDef.EC_SCAN_TIME_ERROR, PubECDef.EC_SCAN_TIME_DESC);
//            }
//            if (StringUtils.isEmpty(data.getStation_id())) {
//                log.error("【运营终端】运营人员确认，站点信息为空");
//                return DataResponse.failure(PubECDef.EC_STATIOND_ID_ERROR, PubECDef.EC_STATIOND_ID_DESC);
//            }
//            if (StringUtils.isEmpty(data.getConfirm_status())) {
//                log.error("【运营终端】运营人员确认，确认状态为空");
//                return DataResponse.failure(PubECDef.EC_CONFIRM_STATUS_ERROR, PubECDef.EC_CONFIRM_STATUS_DESC);
//            }
//            if (StringUtils.isEmpty(data.getClerk_staff_id())) {
//                log.error("【运营终端】运营人员确认，操作员工id为空");
//                return DataResponse.failure(PubECDef.EC_CLEAL_STAFF_ID_ERROR, PubECDef.EC_CLEAL_STAFF_ID_DESC);
//            }
//            if (StringUtils.isEmpty(data.getService_id())) {
//                log.error("【运营终端】运营人员确认，服务类型id为空");
//                return DataResponse.failure(PubECDef.EC_SERVICE_ID_ERROR, PubECDef.EC_SERVICE_ID_DESC);
//                //data.setService_id("01");
//            }
//            int result = tripService.cssConfirmTicket(Long.parseLong(data.getUser_id()), Long.parseLong(data.getCityId())
//                    , data.getService_id(), data.getTrip_no(), Integer.parseInt(data.getDirection()), Long.parseLong(data.getStation_id()),
//                    DateUtils.parseDate(data.getScan_time(), "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss"), Integer.parseInt(data.getConfirm_status()), Long.parseLong(data.getClerk_staff_id()));
//            if (result == 1) {
//                log.info("【运营终端】运营人员确认成功，传入参数：" + gson.toJson(data));
//                return DataResponse.successWithResult(result);
//            } else {
//                log.error("【运营终端】运营人员确认失败，传入参数：" + gson.toJson(data));
//                DataResponse dataResponse = DataResponse.failure(PubECDef.EC_FAIL, PubECDef.EC_FAIL_DESC);
//                dataResponse.addAttribute("result", result);
//                return dataResponse;
//            }
//        } catch (Exception e) {
//            log.error("【运营终端】运营人员确认失败，传入参数：" + gson.toJson(data), e);
//            return DataResponse.failure(PubECDef.EC_FAIL, PubECDef.EC_FAIL_DESC);
//        }
//    }

//    @RequestMapping("/detail")
//    @ResponseBody
//    public Model tripdetail(@RequestBody BaseDataQuery query) {
//        try {
//            return DataResponse.successWithResult(tripService.getTirpTicketConfirm(query));
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("", e);
//            return DataResponse.failure(Constants.ERR_MSG);
//        }
//    }

    static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);//左补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

}
