package com.bwton.data.domain.qrcode.constant;

import lombok.Data;

/**
 * ***********************************************************
 * Copyright © 2017 八维通科技有限公司 Inc.All rights reserved.  *
 * ***********************************************************
 *
 * @Company: www.bwton.com
 * @Generator: IntelliJ IDEA
 * @Project: itsp-parent
 * @Package: com.bwton.data.domain.qrcode.constant
 * @Name: QrcodeContant
 * @Author: zhaokaikai@bwton.com
 * @Date: 2017年10月31日 10时38分
 * @Description: 请描述类的作用
 */
@Data
public class QrcodeContant {

    /**
     * 是否校验用户有未支付的订单
     */
    public static final String VERIFY_USER_HASNOPAYBILLING = "VERIFY_USER_HASNOPAYBILLING";

    /**
     * 是否校验用户的白名单授权
     */
    public static final String VERIFY_USER_WHITE_ENABLE = "VERIFY_USER_WHITE_ENABLE";

    /**
     * 是否校验用户绑定有效的银行卡
     */
    public static final String VERIFY_USER_BANK_CARD = "VERIFY_USER_BANK_CARD";

    /**
     * 阀值金额是否可以请码
     */
    public static final String THRESHOLD_GET_QRCODE_ENABLE = "THRESHOLD_GET_QRCODE_ENABLE";

    /**
     * 阀值金额
     */
    public static final String THRESHOLD_GET_QRCODE = "THRESHOLD_GET_QRCODE";

    /**
     * 请码默认CODE
     */
    public static final String DEFAULT_GET_QRCODE = "DEFAULT_GET_QRCODE";

    /**
     * 二维码图标
     */
    public static final String QRCODE_ICON_URL="QRCODE_ICON_URL";

    /**
     * 二维码进站提示(地铁)
     */
    public static final String QRCODE_TIP_IN="QRCODE_TIP_IN";

    /**
     * 二维码出站提示(地铁)
     */
    public static final String QRCODE_TIP_OUT="QRCODE_TIP_OUT";

    /**
     * 二维码进站提示(公交)
     */
    public static final String QRCODE_BUS_TIP_IN="QRCODE_BUS_TIP_IN";

    /**
     * 二维码出站提示(公交)
     */
    public static final String QRCODE_BUS_TIP_OUT="QRCODE_BUS_TIP_OUT";


    /**
     * 二维码请求地址
     */
    public static final String QRCODE_REQUEST_URL="QRCODE_REQUEST_URL";

    /**
     * 二维码请求AppId
     */
    public static final String APP_ID="APP_ID";

    /**
     * 二维码请求AppSecret
     */
    public static final String APP_SECRET="APP_SECRET";

    /**
     * 二维码刷新时间
     */

    public static final String QRCODE_REFRESH_TIME="QRCODE_REFRESH_TIME";

    /**
     * 二维码有效期（秒）
     */
    public static final String QRCODE_EFFECT_TIME = "QRCODE_EFFECT_TIME";
    /**
     * 二维码有效期（秒）,站厅客服代打
     */
    public static final String QRCODE_EFFECT_TIME_AGENT = "QRCODE_EFFECT_TIME_AGENT";
    /**
     * 二维码有效期（秒），巡检码
     */
    public static final String QRCODE_EFFECT_TIME_STAFF = "QRCODE_EFFECT_TIME_STAFF";

    /**
     * 地铁紧急情况，将生成通用进出站码
     */
    public static final String METRO_EMERGENCY_STATUS = "METRO_EMERGENCY_STATUS";

    /**
     * 闸机检修开始时间（巡检码代打时间范围），格式HH:MM:SS
     */
    public static final String GATE_MACHINE_CHECK_START_TIME = "GATE_MACHINE_CHECK_START_TIME";
    /**
     * 闸机检修结束时间（巡检码代打时间范围），格式HH:MM:SS
     */
    public static final String GATE_MACHINE_CHECK_END_TIME = "GATE_MACHINE_CHECK_END_TIME";

}
