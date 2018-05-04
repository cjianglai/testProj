package com.bwton.service.qrcode.impl;

import com.bwton.PubECDef;
import com.bwton.app.property.config.SysParamPropertiesConfig;
import com.bwton.data.domain.qrcode.QrcodeParam;
import com.bwton.data.domain.qrcode.constant.QrcodeContant;
import com.bwton.data.domain.qrcode.enumtype.BetaStatusEnum;
import com.bwton.data.domain.user.AppUserBase;
import com.bwton.service.qrcode.QrcodeHandler;
import com.bwton.service.qrcode.QrcodeService;
import com.bwton.support.CommonUtils;
import com.bwton.util.MD5;
import com.bwton.util.qrcode.QRCodeBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yanyan.web.utils.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
 * @Package: com.bwton.service.qrcode.impl
 * @Name: QrcodeServiceImpl
 * @Author: zhaokaikai@bwton.com
 * @Date: 2017年11月02日 09时33分
 * @Description: QrcodeServiceImpl
 */

@Slf4j
@Service("qrcodeService")
public class QrcodeServiceImpl implements QrcodeService {


    //TODO 1、将请码中的map对象替换为dto对象
    //TODO 2、将请码中传递系统参数的方法进行改造，不应该传递系统参数，而是由本方法内部自己去获取系统参数进行判断
    //TODO 3、将判断余额中的规则校验放到qrcode中进行判断 【已完成】

    private static final String MD5_KEY = "760bdzec6y9goq7ctyx96ezkz78287de";
    private static final String MD5_CHARSET = "utf-8";
    private static final Integer ID_NO_LENGTH = 16;

    @Autowired
    private Map<String,QrcodeHandler> handlerMap;

	@Autowired
	private SysParamPropertiesConfig sysParamPropertiesConfig;


    public void setHandlerMap(Map handlerMap) {
        this.handlerMap = handlerMap;
    }

    public Map getHandlerMap() {
        return handlerMap;
    }

    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();


    //########################### 重构获取二维码方法 ################################

    /**
     * @param qrcodeParam 请求二维码参数
     * @return
     * @description
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-06 10:51
     */
    @Override
    public DataResponse getQrcode(QrcodeParam qrcodeParam) {
        Integer userType = Integer.valueOf(qrcodeParam.getUser_type());

        //将用户ID补全为16位
        String fullDigUserId = CommonUtils.addZeroForNum(qrcodeParam.getUser_id(), ID_NO_LENGTH);

        if (fullDigUserId.length() != ID_NO_LENGTH) {
            log.info("【获取二维码-/app/qrcode/get】，userId = " + qrcodeParam.getUser_id() + ", 用户ID长度必须16位");
            return DataResponse.failure(PubECDef.EC_USER_ID_ERROR, PubECDef.EC_USER_ID_ERROR_DESC);
        }

        //二维码中的用户身份识别码，采用用户ID MD5生成
        String idNo = MD5.sign(fullDigUserId, MD5_KEY, MD5_CHARSET);
        if (idNo.length() > ID_NO_LENGTH) {
            idNo = idNo.substring(0, ID_NO_LENGTH);
        }

        //step-0 判断请求的Handler是否存在 step-1 判断传入的用户ID是否存在 step-2 获取根据传入的城市ID,获取此城市对应的请码系统参数

        //step-0
        //获取请求二维码Handler
        QrcodeHandler qrcodeHandler = this.getQrcodeHandler(qrcodeParam.getCity_id()
                , qrcodeParam.getService_id());

        if (null == qrcodeHandler) {
            log.error("【获取二维码-/app/qrcode/get】, 请码所使用的Handler未配置，cityId={}，serviceId={}"
                    , qrcodeParam.getCity_id()
                    , qrcodeParam.getService_id());

            return DataResponse.failure(PubECDef.EC_QRCODE_ERROR, PubECDef.EC_QRCODE_ERROR_DESC);
        }

      //step-2 获取根据传入的城市ID,获取此城市对应的请码系统参数-new
        Map<String, String> systemParams = new HashMap<String, String>();
		systemParams = sysParamPropertiesConfig.getFz3501();


        //region 组装二维码请求参数信息

        //所处站点id
        qrcodeParam.setStationId(0L);
        //所处位置经度
        qrcodeParam.setLng(0.0f);
        //所处位置纬度
        qrcodeParam.setLat(0.0f);
        //补全userid为16位
        qrcodeParam.setFull_dig_user_id(fullDigUserId);
        //请求日期
        qrcodeParam.setRequest_date(new Date());
        //身份证加密截取16位
        qrcodeParam.setId_no(idNo);
        //用于请求二维码平台二维码数据信息的二维码编码
        qrcodeParam.setQr_code(systemParams.get(QrcodeContant.DEFAULT_GET_QRCODE));

        //endregion

		//码管家请求二维码方法实现
		if(qrcodeParam.getBusiness_source() == 2)
		{
			return this.getSymQrcode(qrcodeParam, systemParams, qrcodeHandler);
		}
		//APP请求二维码方法实现
		else {
			return this.getAppQrcode(qrcodeParam, systemParams, null, qrcodeHandler);
		}
    }

    /**
     * @param qrcodeParam 二维码请求参数
     * @return
     * @description
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-02 17:28
     */
    @Override
    public DataResponse getAppQrcode(QrcodeParam qrcodeParam,
                                     Map<String, String> systemParams,
                                     AppUserBase user, QrcodeHandler qrcodeHandler) {

        Map<String, Object> qrCodeData = new HashedMap();

        //超时时长从配置读取。
        String effectiveTime = "";
//        if (qrcodeParam.getBusiness_source() == 2) {
//            //业务来源是2-小黄机时，表示代打，读取QRCODE_EFFECT_TIME_AGENT
//            effectiveTime = systemParams.get(QrcodeContant.QRCODE_EFFECT_TIME_AGENT);
//            if (StringUtils.isEmpty(effectiveTime)) {
//                log.error("getAppQrcode， SYS_PARAM缺少参数 {}, city_id = {}", QrcodeContant.QRCODE_EFFECT_TIME_AGENT, qrcodeParam.getCity_id());
//                return null;
//            }
//        } else {
            //普通用户App发起的，读取QRCODE_EFFECT_TIME
            effectiveTime = systemParams.get(QrcodeContant.QRCODE_EFFECT_TIME);
            if (StringUtils.isEmpty(effectiveTime)) {
                log.error("getAppQrcode， SYS_PARAM缺少参数 {}, city_id = {}", QrcodeContant.QRCODE_EFFECT_TIME, qrcodeParam.getCity_id());
                return null;
//            }
        }


        //####################  获取二维码业务逻辑判断 start  ##########################

        //step-3 判断此用户是否受白名单控制 N
        //step-4 判断是否有未支付的订单信息 N
        //step-5 判断余额是否充足 N
        //step-6 在处理行程之前需要做的一些特殊操作，可放在此步骤中（像无锡地铁需要获取ACCKey）
        //step-7 获取此用户对应的行程信息，判断行程信息是否存在 N
        //step-8 判断行程的进出站状态以及出站时行程是否过期 N
        //step-9 获取支付顺序列表
        //step-10 请求二维码平台获取二维码
        //step-11 刷新行程

        //step-11 返回请码信息
        //####################  获取二维码业务逻辑判断 end  ##########################


        //region 逻辑具体实现

        //step-3 判断此用户是否受白名单控制,如果是白名单用户则推送1,否则推送0
//        boolean userIsWhite = homeService.AuthUserIsWhite(
//                Boolean.parseBoolean(systemParams.get(QrcodeContant.VERIFY_USER_WHITE_ENABLE)),
//                qrcodeParam.getCity_id(),
//                Long.parseLong(qrcodeParam.getUser_id()),
//                qrcodeParam.getBundle_id()
//        );
//
//        log.info("【获取二维码-/app/qrcode/get】,判断此用户是否受白名单控制,结果为：{}", userIsWhite);
//
//        if (userIsWhite) {
//            qrcodeParam.setBeta_status(BetaStatusEnum.OPEN.getIndex());
//        } else {
//            qrcodeParam.setBeta_status(BetaStatusEnum.CLOSE_BETA.getIndex());
//        }
        qrcodeParam.setBeta_status(BetaStatusEnum.OPEN.getIndex());

        //step-4 判断是否有未支付的订单信息

//        //需要判断用户是否有未支付的订单
//        boolean hasNoPaybills = false;
//        if (Boolean.parseBoolean(systemParams.get(QrcodeContant.VERIFY_USER_HASNOPAYBILLING))) {
//            hasNoPaybills = payDetailService.hasNoPayBilling(Long.parseLong(qrcodeParam.getUser_id()));
//            log.info("【获取二维码-/app/qrcode/get】,判断此用户是否有未支付的订单信息,结果为：{}", hasNoPaybills);
//        }
//
//        if (hasNoPaybills) {
//            return DataResponse.failure(PubECDef.EC_QRCODE_ORDER_ERROR, PubECDef.EC_QRCODE_ORDER_ERROR_DESC);
//        }

        //step-5 判断余额是否充足,并返回余额信息（次卡、现金账户）
        Map<String, String> balanceInfo = new HashMap<>();

//        log.info("【获取二维码-/app/qrcode/get】, 判断用户余额是否充足,userId={}，是否需要校验用户有效银行卡={}，阀值金额是否可以请码={}"
//                , qrcodeParam.getUser_id()
//                , Boolean.parseBoolean(systemParams.get(QrcodeContant.VERIFY_USER_BANK_CARD))
//                , Boolean.parseBoolean(systemParams.get(QrcodeContant.THRESHOLD_GET_QRCODE_ENABLE)));
//
//        //region 判定余额是否充足
        boolean cashOK = false, ckOK = false, hasUserfulBankCard = false;
        int cashBalance = 0, ckBalance = 0;
//        //final int MIN_CASH = 1000; //现金账户最低标准, 单位分
//        log.info("【获取二维码-/app/qrcode/get】, 判断地铁乘车余额, userId = " + qrcodeParam.getUser_id() + ", 开始");
//
//        //step-5.1 先判断次卡
//        AccountEntity accountCK = accountInfoService.getAccountByIdAndType(Long.parseLong(qrcodeParam.getUser_id()), AccountConstants.ACCOUNT_TYPE_CK, Integer.parseInt(qrcodeParam.getCity_id()));
//        if (accountCK == null) {
//            log.warn("【获取二维码-/app/qrcode/get】,判断地铁乘车余额, userId = " + qrcodeParam.getUser_id() + ",cityId = " + qrcodeParam.getCity_id() + ", 次卡账户不存在！！！！！！！！！！！！");
//        } else {
//            log.info("【获取二维码-/app/qrcode/get】,判断地铁乘车余额, userId = " + qrcodeParam.getUser_id() + ",cityId = " + qrcodeParam.getCity_id() + ", 次卡余额= " + accountCK.getBalance());
//            balanceInfo.put("timeCardBalance", accountCK.getBalance().toString());
//            ckOK = accountCK.getBalance() >= 1;
//            ckBalance = accountCK.getBalance();
//        }
      balanceInfo.put("timeCardBalance", "740");
      ckOK = true;
      ckBalance=740;
        //step-5.2 次卡不足, 再判断现金余额(等于金额阀值的时候不能请码)
//        AccountEntity accountXJ = accountInfoService.getAccountByIdAndType(Long.parseLong(qrcodeParam.getUser_id()), AccountConstants.ACCOUNT_TYPE_CASH, Integer.parseInt(qrcodeParam.getCity_id()));
//        if (accountXJ == null) {
//            log.error("【获取二维码-/app/qrcode/get】,判断地铁乘车余额, userId = " + qrcodeParam.getUser_id() + ",cityId = " + qrcodeParam.getCity_id() + ", 现金账户不存在！");
//        } else {
//            log.info("【获取二维码-/app/qrcode/get】,判断地铁乘车余额, userId = " + qrcodeParam.getUser_id() + ",cityId = " + qrcodeParam.getCity_id() + ", 现金余额 = " + accountXJ.getBalance());
//            if (Boolean.parseBoolean(systemParams.get(QrcodeContant.THRESHOLD_GET_QRCODE_ENABLE))) {
//                cashOK = accountXJ.getBalance() >= Integer.parseInt(systemParams.get(QrcodeContant.THRESHOLD_GET_QRCODE));
//            } else {
//                cashOK = accountXJ.getBalance() > Integer.parseInt(systemParams.get(QrcodeContant.THRESHOLD_GET_QRCODE));
//            }
//            cashBalance = accountXJ.getBalance();
//        }
      cashOK=true;
      cashBalance=100;

//        //step-5.2 判断用户是否有有效的银行卡，如果有有效的银行卡（储蓄卡或者信用卡）**********根据城市支持零元请码
//        if (Boolean.parseBoolean(systemParams.get(QrcodeContant.VERIFY_USER_BANK_CARD))) {
//            hasUserfulBankCard = accountExternalInfoService.hasUserfulBankCard(Long.parseLong(qrcodeParam.getUser_id()));
//
//            log.info("【获取二维码-/app/qrcode/get】,判断地铁乘车最低余额,用户是否绑定了有效的银行卡（储蓄卡或者信用卡）, userId = " + qrcodeParam.getUser_id() + ",cityId = " + qrcodeParam.getCity_id() + ",结果：" + hasUserfulBankCard);
//        }
      hasUserfulBankCard=true;

//        if (!ckOK & !cashOK & !hasUserfulBankCard) {
//            log.warn("【获取二维码-/app/qrcode/get】，生成二维码失败， userId={}，用户余额不足，次卡是否充足={}，余额={}，是否有有效的银行卡={}"
//                    , qrcodeParam.getUser_id()
//                    , ckOK
//                    , cashOK
//                    , hasUserfulBankCard);
//
//            String responseMsg = "钱包余额<";
//            if (!Boolean.parseBoolean(systemParams.get(QrcodeContant.THRESHOLD_GET_QRCODE_ENABLE))) {
//                responseMsg += "=";
//            }
//            String yuan = CommonUtils.ConvertFenToYuan(Integer.parseInt(systemParams.get(QrcodeContant.THRESHOLD_GET_QRCODE)));
//            responseMsg += yuan + "元，无法使用二维码乘车，现在去充值吧！";
//
//            return DataResponse.failure(PubECDef.EC_QRCODE_BALANCE_ERROR, responseMsg);
//        }

        //endregion

        //step-6 在处理行程之前需要做的一些特殊操作，可放在此步骤中（像无锡地铁需要获取ACCKey）
        Map<String, Object> expandData = qrcodeHandler.ExpandBusiness(qrcodeParam, systemParams);

        //第7步第8部由行程服务接口提供结果
        //step-7 获取此用户对应的行程信息，判断行程信息是否存在
        //step-8 判断行程的进出站状态以及出站时行程是否过期
        //       传入参数说明：用户ID\城市ID\服务ID
        //       返回的map格式为：
        //       isExist:行程是否存在
        //       isOverTime:是否超期
        //       ioFlag:进出标记(0-未进站 1-已进站未出站)
        //       inSysTime:进站生成时间
        //       tripId:行程Id
        //       tripNo:行程No
//        Map<String, String> tripInfo = tripService.getTravelingTrip(Long.parseLong(qrcodeParam.getUser_id()),
//                Integer.parseInt(qrcodeParam.getCity_id()),
//                qrcodeParam.getService_id());
//
//        log.info("【获取二维码-/app/qrcode/get】, 查询用户行程信息,结果为：{}", gson.toJson(tripInfo));
//
//        if (!Boolean.parseBoolean(tripInfo.get("isExist"))) {
//            log.warn("【获取二维码-/app/qrcode/get】，生成二维码失败，行程信息为空，请重试");
//            return DataResponse.failure(PubECDef.EC_QRCODE_ERROR, PubECDef.EC_QRCODE_ERROR_DESC);
//        }
//
//        if (Boolean.parseBoolean(tripInfo.get("isOverTime"))) {
//            log.warn("【获取二维码-/app/qrcode/get】，二维码生成, userId={}，tripId={}，inSysTime={}，行程过期！"
//                    , qrcodeParam.getUser_id()
//                    , tripInfo.get("tripId")
//                    , tripInfo.get("inSysTime"));
//
//            return DataResponse.failure(PubECDef.EC_TRIP_NOT_OUT_ERROR, PubECDef.EC_TRIP_NOT_OUT_DESC);
//        }


        //进出站标志控制
        String sMETRO_EMERGENCY_STATUS = systemParams.get(QrcodeContant.METRO_EMERGENCY_STATUS);
        if (StringUtils.isEmpty(sMETRO_EMERGENCY_STATUS)) {
            log.warn("【获取二维码-/app/qrcode/get】， SYS_PARAM缺少参数 {}, city_id = {}", QrcodeContant.METRO_EMERGENCY_STATUS, qrcodeParam.getCity_id());
        }
        if (Boolean.parseBoolean(sMETRO_EMERGENCY_STATUS)) {
            //紧急情况，进出站码设置为“无限制”，运行自由进出。
            qrcodeParam.setQr_code(CommonUtils.replaceString(qrcodeParam.getQr_code(), 3, "0"));
            log.warn("【获取二维码-/app/qrcode/get】, userId = {}, 紧急情况，进出站码设置为“无限制”!!!!!!", qrcodeParam.getUser_id());
        }
//        else if (Integer.parseInt(tripInfo.get("ioFlag")) == 0) {
//            qrcodeParam.setQr_code(CommonUtils.replaceString(qrcodeParam.getQr_code(), 3, "1"));
//        } else if (Integer.parseInt(tripInfo.get("ioFlag")) == 1) {
//            qrcodeParam.setQr_code(CommonUtils.replaceString(qrcodeParam.getQr_code(), 3, "2"));
//        }

        //step-10 请求二维码平台获取二维码
        long startMills = System.currentTimeMillis();
        String qrData = qrcodeHandler.RequestQrcode(qrcodeParam,
                null,
                systemParams,
                expandData,
                effectiveTime);
        log.info("【获取二维码-/app/qrcode/get】, 请求二维码平台获取二维码, 耗时 = {}, 结果为：{}", System.currentTimeMillis() - startMills, qrData);

        if (StringUtils.isEmpty(qrData)) {
            log.error("【获取二维码-/app/qrcode/get】,生成二维码失败，获取qrcode为空");
            return DataResponse.failure(PubECDef.EC_QRCODE_ERROR, PubECDef.EC_QRCODE_ERROR_DESC);
        }

        //step-11 刷新行程
//        try {
//            tripService.refreshTravelingTrip(
//                    Integer.parseInt(tripInfo.get("ioFlag")),
//                    Long.parseLong(qrcodeParam.getUser_id()),
//                    Integer.parseInt(qrcodeParam.getCity_id()),
//                    qrcodeParam.getService_id(),
//                    tripInfo.get("tripNo"),
//                    qrcodeParam.getLng(),
//                    qrcodeParam.getLat(),
//                    qrcodeParam.getStationId()
//            );
//        } catch (Exception e) {
//            log.error("【获取二维码-/app/qrcode/get】, userId = {}, tripService.refreshTravelingTrip出错：{}", qrcodeParam.getUser_id(), e.getMessage());
//        }
        //step-12 返回请码信息
        //step-13 获取支付顺序列表
        //       传入参数说明：用户ID\城市ID\是否智能支付标记\APP版本号\Bundle_Id
        //       返回的map格式为：
        //       pay_type:支付类型
        //       charge_sequences:支付顺序
//        Map<String, Object> chargeSequences = new HashMap<String, Object>();
//        try {
//            chargeSequences = accountChargeSequenceService.getChargeSequences(
//                    Long.parseLong(qrcodeParam.getUser_id()),
//                    Integer.parseInt(qrcodeParam.getCity_id()),
//                    user.getIntelligentFee(),
//                    qrcodeParam.getApp_version(),
//                    qrcodeParam.getBundle_id()
//            );
//        } catch(Exception e) {
//            log.error("【获取二维码-/app/qrcode/get】, userId = {}, accountChargeSequenceService.getChargeSequences出错：{}", qrcodeParam.getUser_id(), e.getMessage());
//        }
//        log.info("【获取二维码-/app/qrcode/get】, 获取此用户的扣款顺序,结果为：{}", gson.toJson(chargeSequences));

        //region 组装返参的具体信息

        //次卡余额
//        qrCodeData.put("times", ckBalance);
        //现金余额
//        qrCodeData.put("balance", cashBalance);
        //进标记
//        qrCodeData.put("inorout", Integer.parseInt(tripInfo.get("ioFlag")));
//        qrCodeData.put("inorout", 2);
        //请码提示信息(进站/出站提示信息区别对待)
//        qrCodeData.put("inorout_notice", qrcodeHandler.getInOrOutNotice(Integer.parseInt(tripInfo.get("ioFlag")), qrcodeParam, systemParams));
//        qrCodeData.put("inorout_notice", "该码用于进出站，当即使用请勿泄露");
        //请求二维码返回的信息
        qrCodeData.put("qrcode_data", qrData);
        //智能支付标志 2为智能支付
//        qrCodeData.put("pay_type", chargeSequences.get("pay_type"));
//        qrCodeData.put("pay_type", 2);
        //扣款顺序
//        qrCodeData.put("chargeSequences", chargeSequences.get("chargeSequences"));
//        qrCodeData.put("chargeSequences", 0);
        //二维码刷新时间
        qrCodeData.put("refresh_time", Integer.parseInt(systemParams.get(QrcodeContant.QRCODE_REFRESH_TIME)));
        //二维码正中间图标
        qrCodeData.put("icon_url", systemParams.get(QrcodeContant.QRCODE_ICON_URL));
        //endregion

        return DataResponse.successWithResult(qrCodeData);
        //endregion

    }

    /**
     * @param qrcodeParam 二维码请求参数(巡检）
     * @return
     * @description
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-02 17:28
     */
    @Override
    public DataResponse getSymQrcode(QrcodeParam qrcodeParam,
                                     Map<String, String> systemParams, QrcodeHandler qrcodeHandler) {
        //TODO 稍后改为责任链模式-Handler进行处理
        if("3501".equals(qrcodeParam.getCity_id())) {
            //福州小黄机请求二维码，用于巡检
            return this.getFuZhouSymQrcode(qrcodeParam, systemParams, qrcodeHandler);
        }

        Map<String, Object> result = new HashedMap();
        String qrCode = "";

        //后台用户生成二维码

        Map<String, Object> expandData = qrcodeHandler.ExpandBusiness(qrcodeParam, systemParams);
        byte[] key = (byte[]) expandData.get("key");

        //有效时间阈值 单位秒，
        String sTmpEffectiveTime = systemParams.get("QRCODE_EFFECT_TIME_STAFF");
//                systemParamService.getParamVal(QrcodeContant.QRCODE_EFFECT_TIME_STAFF, Integer.valueOf(qrcodeParam.getCity_id()));
        if (StringUtils.isEmpty(sTmpEffectiveTime)) {
            log.error("getSymQrcode， SYS_PARAM缺少参数 {}, city_id = {}", QrcodeContant.QRCODE_EFFECT_TIME_STAFF, qrcodeParam.getCity_id());
            return null;
        }
        Integer effectiveTime = Integer.valueOf(sTmpEffectiveTime);

        //计费方式 默认金额 0-金额 1-次卡 9-非计费卡
        Byte payMode = 9;
        //授权类型 (0:无限制，1:仅进站，2:仅出站)
        Byte authMode = 0;
        //所处站点ID 暂时没用，先写死
        String stationNo = "0001";
        long qrSerialNo = 1234567890123456L;

        //后台用户，不纳入乘车控制
        try {
            qrCode = QRCodeBuilder.BuildQRCode(qrcodeParam.getFull_dig_user_id(),
                    qrcodeParam.getId_no(),
                    payMode,
                    authMode,
                    qrcodeParam.getRequest_date(),
                    effectiveTime,
                    stationNo,
                    qrSerialNo, key);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("getSymQrcode staffId = " + qrCode + ", 错误：" + e.getMessage());
            return DataResponse.failure("1101", "生成二维码失败，请重试");
        }
        if (qrCode == null || qrCode.length() == 0) {
            return DataResponse.failure("1101", "生成二维码失败，请重试");
        }

        result.put("qrcode_data", qrCode);
        result.put("refresh_time", "30");

        return DataResponse.successWithResult(result);
    }

    /**
     * @param qrcodeParam 二维码请求参数(福州巡检）
     * @return
     * @description
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-02 17:28
     */
    private DataResponse getFuZhouSymQrcode(QrcodeParam qrcodeParam,
                                            Map<String, String> systemParams, QrcodeHandler qrcodeHandler)
    {
        Map<String, Object> qrCodeData = new HashedMap();

        String effectiveTime = "";
        if (qrcodeParam.getBusiness_source() == 2) {
            effectiveTime = systemParams.get(QrcodeContant.QRCODE_EFFECT_TIME_STAFF);
            if (StringUtils.isEmpty(effectiveTime)) {
                log.error("getFuZhouSymQrcode， SYS_PARAM缺少参数 {}, city_id = {}", QrcodeContant.QRCODE_EFFECT_TIME_STAFF, qrcodeParam.getCity_id());
                return null;
            }
        }

        //step-10 请求二维码平台获取二维码
        long startMills = System.currentTimeMillis();
        Map<String, String> tripinfo = new HashMap<>();
        Map<String, Object> expandData = new HashMap<>();
        String qrData = qrcodeHandler.RequestQrcode(qrcodeParam,
                tripinfo,
                systemParams,
                expandData,
                effectiveTime);
        log.info("getFuZhouSymQrcode, 请求二维码平台获取二维码, 耗时 = {}, 结果为：{}", System.currentTimeMillis() - startMills, qrData);

        if (StringUtils.isEmpty(qrData)) {
            log.error("getFuZhouSymQrcode,生成二维码失败，获取qrcode为空");
            return DataResponse.failure(com.bwton.commonEcdef.PubECDef.EC_QRCODE_ERROR, com.bwton.commonEcdef.PubECDef.EC_QRCODE_ERROR_DESC);
        }

        //region 组装返参的具体信息
        //请求二维码返回的信息
        qrCodeData.put("qrcode_data", qrData);
        //二维码刷新时间
        qrCodeData.put("refresh_time", Integer.parseInt(systemParams.get(QrcodeContant.QRCODE_REFRESH_TIME)));

        //endregion

        return DataResponse.successWithResult(qrCodeData);
        //endregion
    }

    /**
     * @param cityId    城市ID
     * @param serviceId 服务ID
     * @return
     * @description 获取请码Handler
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-02 11:39
     */
    protected QrcodeHandler getQrcodeHandler(String cityId, String serviceId) {
        String handlerCode = cityId + "|" + serviceId;
        handlerCode = "metroQrcodeHandler";
        QrcodeHandler qrcodeHandler = (QrcodeHandler) handlerMap.get(handlerCode);

        if (qrcodeHandler == null) {
            log.warn("【二维码服务】，获取请码handler为空,handlerCode = {}，将尝试获取默认的handler", handlerCode);
            qrcodeHandler = (QrcodeHandler) handlerMap.get(serviceId);
            if (qrcodeHandler == null) {
                log.warn("【二维码服务】，获取默认的请码handler为空,serviceId = {}，程序将返回Handler为null", serviceId);
            }
        }
        log.info("【二维码服务】，获取请码handler为空, handler = {}", qrcodeHandler.getClass().getSimpleName());
        return qrcodeHandler;
    }
}

//@Service("qrcodeService")
//public class QrcodeServiceImpl implements QrcodeService {
//
//	@Autowired
//	private PropertiesConfig propertiesConfig;
//
//
//	@Override
//	public DataResponse getQrcode(QrcodeParam qrcodeParam) {
//      //step-2 获取根据传入的城市ID,获取此城市对应的请码系统参数
//
//		 Map<String, String> ids= propertiesConfig.getApp_id();
//		 System.out.println(ids);
//	String ns = "test";
//      //region 组装二维码请求参数信息
//		return DataResponse.successWithResult(ns);
//	}
//
//}

