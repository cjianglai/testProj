package com.bwton;

/*
 * 错误代码定义规则：
 * 前2位的定义：
    00 表示系统通用的。
    01 用户相关
    02 账户相关。
    03 黑白名单
    04 优惠券
    后续新的模块，请自行添加定义
 */
public class PubECDef {

    //00 通用参数
    public final static String EC_SUCC = "0000";
    public final static String EC_SUCC_DESC = "处理成功";

    public final static String EC_FAIL = "0010";
    public final static String EC_FAIL_DESC = "处理失败";

    public final static String EC_PARAMS_ERROR = "0012";
    public final static String EC_PARAMS_ERROR_DESC = "输入参数不正确";


    //0131	用户id为空
    public final static String EC_USER_ID_ERROR = "0131";
    public final static String EC_USER_ID_ERROR_DESC = "用户id为空";

    //二维码提示
    public final static String EC_QRCODE_ERROR = "1101";
    public final static String EC_QRCODE_ERROR_DESC = "二维码生成失败，请重试";


}
