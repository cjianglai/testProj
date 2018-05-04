//package com.bwton.service.qrcode.handler;
//
//import com.bwton.PubECDef;
//import com.bwton.data.domain.qrcode.QrcodeKey;
//import com.bwton.data.domain.qrcode.QrcodeParam;
//import com.bwton.data.domain.qrcode.constant.QrcodeContant;
//import com.bwton.data.domain.trip.Trip;
//import com.bwton.data.domain.user.AppUserBase;
//import com.bwton.service.qrcode.QrcodeHandler;
//import com.bwton.service.qrcode.QrcodeKeyService;
//import com.bwton.service.qrcode.QrcodeParamsService;
//import com.bwton.service.system.SystemParamService;
//import com.bwton.service.trip.TripService;
//import com.bwton.service.user.AppUserService;
//import com.bwton.support.CommonUtils;
//import com.bwton.support.PropertiesInfo;
//import com.bwton.util.MD5;
//import com.bwton.util.qrcode.QRCodeBuilder;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.yanyan.web.utils.DataResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.apache.commons.lang3.time.DateUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by huayq on 2017/8/16.
// * 无锡地铁二维码获取
// */
//@Slf4j
//@Component("wxMetroQrcodeHandler")
//public class WxMetroQrcodeHandler implements QrcodeHandler {
//
//    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
//
//    @Autowired
//    private QrcodeKeyService qrcodeKeyService;
//    @Autowired
//    private SystemParamService systemParamService;
//
//    @Override
//    public String RequestQrcode(QrcodeParam qrcodeParam,
//                                Map<String, String> tripInfo,
//                                Map<String, String> systemParams,
//                                Map<String, Object> expandData,
//                                String effectTime) {
//        Integer effectiveTime = Integer.valueOf(effectTime);
//        Byte payMode = 0; //计费方式 默认金额 0-金额 1-次卡
//        Byte authMode = 1; //授权类型 (0:无限制，1:仅进站，2:仅出站)
//        String stationNo = "0001"; //所处站点ID 暂时没用，先写死
//        String qrCode = null;
//
//        long qrSerialNo;
//
//        //使用已有的行程单
//        qrSerialNo = NumberUtils.toLong(tripInfo.get("tripNo"));
//
//        //进出站标志控制
//        String sMETRO_EMERGENCY_STATUS = systemParamService.getParamVal(QrcodeContant.METRO_EMERGENCY_STATUS, Integer.valueOf(qrcodeParam.getCity_id()));
//        if (StringUtils.isEmpty(sMETRO_EMERGENCY_STATUS)) {
//            log.warn("【获取二维码-/app/qrcode/get】， SYS_PARAM缺少参数 {}, city_id = {}", QrcodeContant.METRO_EMERGENCY_STATUS, qrcodeParam.getCity_id());
//        }
//        if (Boolean.parseBoolean(sMETRO_EMERGENCY_STATUS)) {
//            //紧急情况，进出站码设置为“无限制”，运行自由进出。
//            authMode = 0;
//            log.warn("【无锡地铁-乘车扫码】 userId = {}, 紧急情况，进出站码设置为“无限制”!!!!!!", qrcodeParam.getUser_id());
//        } else {
//            if (Integer.parseInt(tripInfo.get("ioFlag")) == 0) {//未进站
//                authMode = 1;
//            } else {//未出站
//                authMode = 2;
//            }
//        }
//
//        try {
//            qrCode = QRCodeBuilder.BuildQRCode(qrcodeParam.getUser_id(),
//                    qrcodeParam.getId_no(),
//                    payMode,
//                    authMode,
//                    qrcodeParam.getRequest_date(),
//                    effectiveTime,
//                    stationNo,
//                    qrSerialNo,
//                    (byte[]) expandData.get("key"));
//        } catch (Exception e) {
//            log.error("QRCodeBuilder.BuildQRCode, userId = {}, 错误：{}", qrcodeParam.getUser_id(), e.getMessage());
//        }
//        return qrCode;
//    }
//
//    /**
//     * @param qrcodeParam  请求二维码参数
//     * @param systemParams 系统参数
//     * @return
//     * @description
//     * @company www.bwton.com
//     * @author zhaokaikai@bwton.com
//     * @date 2017-11-06 11:25
//     */
//    @Override
//    public Map<String, Object> ExpandBusiness(QrcodeParam qrcodeParam, Map<String, String> systemParams) {
//        byte[] key = null;
//
//        Map<String, Object> resultMap = new HashMap<>();
//
//        QrcodeKey accKey = qrcodeKeyService.getDBACCKey(Long.parseLong(qrcodeParam.getUser_id()),
//                qrcodeParam.getRequest_date());
//        if (accKey == null) {
//
//            log.info("【无锡地铁-获取二维码-/app/qrcode/get】 userId = " + qrcodeParam.getUser_id() + ", 数据库没有密钥，准备从ACC获取");
//
//            Object[] datas = QRCodeBuilder.connectACCGetKey(qrcodeParam.getFull_dig_user_id()
//                    , qrcodeParam.getId_no()
//                    , qrcodeParam.getRequest_date());
//
//            if (datas != null && datas.length > 0 && datas[0].equals("0000")) {
//                log.info("【无锡地铁-获取二维码-/app/qrcode/get】userId = " + qrcodeParam.getUser_id() + ", ACC密钥获取成功");
//
//                key = (byte[]) datas[1];
//
//                String aa = new String(key);
//
//                log.info("【无锡地铁-获取二维码-/app/qrcode/get userId = " + qrcodeParam.getUser_id() + ", key = " + aa);
//
//                //保存到DB中;
//                qrcodeKeyService.addQRCodeKey(Long.parseLong(qrcodeParam.getUser_id()),
//                        qrcodeParam.getRequest_date(),
//                        key);
//
//                log.info("【无锡地铁-获取二维码-/app/qrcode/get】, userId = " + qrcodeParam.getUser_id() + ", 密钥已经保存到DB中");//，耗时 = " + (System.currentTimeMillis() - startMills));
//
//            } else {
//                if (PropertiesInfo.ACC_ANY_KEY) {
//                    QrcodeKey anyKey = qrcodeKeyService.getAnyKey();
//                    if (anyKey != null) {
//                        log.error("【无锡地铁-获取二维码-/app/qrcode/get】注意！！！！！ 这里的ACC Key是随机从数据库获取的，不能用于生产环境！！！！！");
//                        key = anyKey.getAcc_key();
//                    } else {
//                        log.info("【无锡地铁-获取二维码-/app/qrcode/get】 userId = " + qrcodeParam.getUser_id() + ", 连接ACC获取密钥失败！");
//                        return DataResponse.failure("1101", "生成二维码失败，请重试");
//                    }
//                } else {
//                    log.info("【无锡地铁-获取二维码-/app/qrcode/get】 userId = " + qrcodeParam.getUser_id() + ", 连接ACC获取密钥失败！");
//                    return DataResponse.failure("1101", "生成二维码失败，请重试");
//                }
//            }
//        } else {
//            key = accKey.getAcc_key();
//        }
//
//        resultMap.put("key", key);
//
//        return resultMap;
//    }
//
//    @Override
//    public String getInOrOutNotice(int ioFlag, QrcodeParam qrcodeParam, Map<String, String> systemParams) {
//        switch (ioFlag) {
//            case 0: return systemParams.get(QrcodeContant.QRCODE_TIP_IN);
//            case 1: return systemParams.get(QrcodeContant.QRCODE_TIP_OUT);
//            default: return "";
//        }
//    }
//}
