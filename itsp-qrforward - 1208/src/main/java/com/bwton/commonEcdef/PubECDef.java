package com.bwton.commonEcdef;

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

    public final static String EC_APP_BUNDLEID_ERROR = "0009";
    public final static String EC_APP_BUNDLEID_DESC = "App唯一标识为空";

    public final static String EC_FAIL = "0010";
    public final static String EC_FAIL_DESC = "处理失败";

    public final static String EC_NEED_PARAMS = "0011";
    public final static String EC_NEED_PARAMS_DESC = "必填参数不能为空";

    public final static String EC_PARAMS_ERROR = "0012";
    public final static String EC_PARAMS_ERROR_DESC = "输入参数不正确";

    public final static String EC_TERMTOKEN_ERROR = "0013";
    public final static String EC_TERMTOKEN_ERROR_DESC = "终端识别码错误";

    public final static String EC_CITY_ERROR = "0014";
    public final static String EC_CITY_ERROR_DESC = "城市选择错误";

    public final static String EC_APPVER_NO_WHITECITY = "0015";
    public final static String EC_APPVER_NO_WHITECITY_DESC = "该城市仅向内测用户开放，请选择其他城市";

    public final static String EC_APPVER_NO_FUNCTION = "0016";
    public final static String EC_APPVER_NO_FUNCTION_DESC = "该功能仅向内测用户开放";

    public final static String EC_APPVER_NO_COMPATIBLE = "0088";
    public final static String EC_APPVER_NO_COMPATIBLE_DESC = "请更新至最新版本，否则将影响您的使用";

    public final static String EC_BICYLE_ERROR = "0008";
    public final static String EC_BICYLE_SERVICE_KEY_ERROR_DESC = "租车系统Key为空。";
    public final static String EC_BICYLE_SUGGESTION_COMMIT_ERROR_DESC = "建议提交到租车系统失败";
    public final static String EC_BICYLE_SUGGESTION_CANCEL_ERROR_DESC = "租车系统取消建议失败";

    //*******01 用户相关*********//
    //0100	用户不存在
    public final static String EC_USER_NOT_EXISTS = "0100";
    public final static String EC_USER_NOT_EXISTS_DESC = "您还不是会员，现在去注册吧";

    //0101	同一帐号每天只能同一个手机上使用
    public final static String EC_DIFF_TERM = "0101";
    public final static String EC_DIFF_TERM_DESC = "为确保您的账户安全，您的帐号每天只能在同一个手机上使用";

    //0102	黑名单用户，不允许使用
    public final static String EC_BLACK_USER = "0102";
    public final static String EC_BLACK_USER_DESC = "您的账户存在风险，暂时无法使用，请与客服联系";

    //0103	用户状态不正确（注销或停用等）
    public final static String EC_USER_STATUS_ERROR = "0103";
    public final static String EC_USER_STATUS_ERROR_DESC = "您的账户状态异常，暂时无法使用，请与客服联系";

    public final static String EC_PHONE_ERROR = "0104";
    public final static String EC_PHONE_ERROR_DESC = "手机号码错误";

    //0105	短信验证码校验错误
    public final static String EC_SMSCODE_ERROR = "0105";
    public final static String EC_SMSCODE_ERROR_DESC = "验证码无效，请核对后重新验证";

    //0106	密码错误
    public final static String EC_PWD_ERROR = "0106";
    public final static String EC_PWD_ERROR_DESC = "账号或密码错误";

    //0106	密码错误
    public final static String EC_PW_MAX_ERROR = "0108";
    public final static String EC_PWD_MAX_ERROR_DESC = "密码错误次数超过限制，请重置密码";

    //0107	原密码为空
    public final static String EC_OLDPWD_EMPTY = "0107";
    public final static String EC_OLDPWD_EMPTY_DESC = "您还没有设置登录密码，请使用短信验证码登录";

    //0120	重复注册
    public final static String EC_DUPLICATE_USER = "0120";
    public final static String EC_DUPLICATE_USER_DESC = "手机号码已经被其它用户绑定";

    //0121	实名认证后不能修改
    public final static String EC_ALREADY_AUTH = "0121";
    public final static String EC_ALREADY_AUTH_DESC = "实名认证后不能修改";

    //0122	只支持身份证
    public final static String EC_IDCARD_ONLY = "0122";
    public final static String EC_IDCARD_ONLY_DESC = "只支持身份证";

    //0123	用户证件号已被注册，不能继续认证
    public final static String EC_IDCARD_EXISTS = "0123";
    public final static String EC_IDCARD_EXISTS_DESC = "用户证件号已被注册，不能继续认证";

    //0124	非白名单用户，不能注册
    public final static String EC_NOT_WHITE_LIST_USER = "0124";
    public final static String EC_NOT_WHITE_LIST_USER_DESC = "内部封测期间，暂时无法使用，敬请期待";

    //0125 用户修改手机号码，新旧号码相同
    public final static String EC_MOBILEPHONE_REPEAT_ERROR = "0125";
    public final static String EC_MOBILEPHONE_REPEAT_ERROR_DESC = "新号码与旧号码相同，不能修改";

    //0127 用户注册失败
    public final static String EC_AUTH_REGISTER_ERROR = "0127";
    public final static String EC_AUTH_REGISTER_ERROR_DESC = "用户注册失败";

    //0127 登录失败
    public final static String EC_AUTH_LOGIN_ERROR = "0128";
    public final static String EC_AUTH_LOGIN_ERROR_DESC = "登录失败";

    //0126 用户没有实名认证，不能绑定银行卡
    public final static String EC_NOT_AUTH_VALID_ERROR = "0126";
    public final static String EC_NOT_AUTH_VALID_ERROR_DESC = "您还未实名，请先完成实名登记";

    //0129	验证码错误
    public final static String EC_CODE_MAX_ERROR = "0129";
    public final static String EC_CODE_MAX_ERROR_DESC = "验证码输入错误次数超过限制，请稍后再试";

    //0130	密码长度不符合规则
    public final static String EC_PWD_RULE_ERROR = "0130";
    public final static String EC_PWD_RULE_MIN_ERROR_DESC = "密码长度不能少于6位";
    public final static String EC_PWD_RULE_MAX_ERROR_DESC = "密码长度不能超过32位";

    //0131	用户id为空
    public final static String EC_USER_ID_ERROR = "0131";
    public final static String EC_USER_ID_ERROR_DESC = "用户id为空";

    //0132	密码为空
    public final static String EC_PASSWORD_ERROR = "0132";
    public final static String EC_PASSWORD_ERROR_DESC = "密码为空";


    //0133	用户性别输入错误
    public final static String EC_SEX_ERROR = "0133";
    public final static String EC_SEX_ERROR_DESC = "性别输入错误";

    //0134	用户生日格式输入错误
    public final static String EC_BIRTHDAY_ERROR = "0134";
    public final static String EC_BIRTHDAY_ERROR_DESC = "生日格式输入错误";

    //0135用户姓名为空
    public final static String EC_USER_NAME_ERROR = "0135";
    public final static String EC_USER_NAME_ERROR_DESC = "姓名为空";

    //0136证件类型为空
    public final static String EC_ID_TYPE_ERROR = "0136";
    public final static String EC_ID_TYPE_ERROR_DESC = "证件类型为空";

    //0137支付密码为空
    public final static String EC_PAY_PWD_ERROR = "0137";
    public final static String EC_PAY_PWD_ERROR_DESC = "支付密码为空";

    //0138支付密码为空
    public final static String EC_ID_NO_ERROR = "0138";
    public final static String EC_ID_NO_ERROR_DESC = "证件号码为空";

    //0139userId格式错误
    public final static String EC_USER_ID_REG_ERROR = "0139";
    public final static String EC_USER_ID_REG_ERROR_DESC = "userId格式错误";

    //0140cityId错误
    public final static String EC_CITY_ID_ERROR = "0140";
    public final static String EC_CITY_ID_ERROR_DESC = "城市Id为空";

    //0141原始密码错误
    public final static String EC_SYMPASS_ERROR = "0141";
    public final static String EC_SYMPASS_ERROR_DESC = "原始密码错误";

    //0142用户不存在
    public final static String EC_USER_NOT_EXIST_ERROR = "0142";
    public final static String EC_USER_NOT_EXIST_DESC = "用户不存在";

    //0143非本人卡，禁止使用
    public final static String EC_USER_NOT_SELF_CARD_ERROR = "0143";
    public final static String EC_USER_NOT_SELF_CARD_DESC = "非本人卡，禁止使用";

    //0144为了您的账户安全，请先完成身份认证。
    public final static String EC_USER_NOT_REAL_NAME_AUTH_ERROR = "0144";
    public final static String EC_USER_NOT_REAL_NAME_AUTH_DESC = "为了您的账户安全，请先完成身份认证";

    //0145身份认证失败，请输入正确的身份信息
    public final static String EC_USER_INFO_ERROR = "0145";
    public final static String EC_USER_INFO_ERROR_DESC = "身份认证失败，请输入正确的身份信息";

    //0146用户开户失败，请重新开户
    public final static String EC_USER_OPEN_ERROR = "0146";
    public final static String EC_USER_OPEN_ERROR_DESC = "用户开户失败，请重新开户";

    //0147用户开户失败，请重新开户
    public final static String EC_USER_NEW_OPEN_ERROR = "0147";
    public final static String EC_USER_NEW_OPEN_ERROR_DESC = "用户未开户，请先进行开户";

    //0148，请输入正确的身份信息
    public final static String EC_USER_INFO_AUTH_REP_ERROR = "0148";
    public final static String EC_USER_INFO_AUTH_REP_DESC = "身份信息不能用于多个账号认证，请重新输入";

    //0149，请输入正确的身份信息
    public final static String EC_USER_INFO_AUTH_MUTI_ERROR = "0149";
    public final static String EC_USER_INFO_AUTH_MUTI_DESC = "身份信息已经认证，不能重复认证";

    //0150，不支持城市
    public final static String EC_CITY_NOT_SUPPORT = "0150";
    public final static String EC_CITY_NOT_SUPPORT_DESC = "不支持当前城市";
    //0151，用户状态异常
    public final static String EC_USER_STATUS_EXP_ERROR = "0151";
    public final static String EC_USER_STATUS_EXP_DESC = "用户状态异常";

    //0152，暂不支持15位的身份证实名认证
    public final static String EC_USER_AUTH_IDNO_ERROR = "0152";
    public final static String EC_USER_AUTH_IDNO_DESC = "暂不支持15位的身份证";

    //*******02 账户相关*********
    //0200	账户不存在
    public final static String EC_ACCOUNT_NOT_EXISTS = "0200";
    public final static String EC_ACCOUNT_NOT_EXISTS_DESC = "账户不存在";
    //0201	余额不足
    public final static String EC_BALANCE_INSUFFICIENT = "0201";
    public final static String EC_BALANCE_INSUFFICIENT_DESC = "余额不足";

    //0202	账户已经存在
    public final static String EC_ACCOUNT_EXISTS = "0202";
    public final static String EC_ACCOUNT_EXISTS_DESC = "用户账户异常！";

    //0203	开卡失败
    public final static String EC_ACCOUNT_OPEN_ERROR = "0203";
    public final static String EC_ACCOUNT_OPEN_ERROR_DESC = "支付密码设置失败，请稍后再试";


    //充值失败
    public final static String EC_ACCOUNT_CHARGE_ERROR = "0205";
    public final static String EC_ACCOUNT_CHARGE_ERROR_DESC = "充值失败";

    //创建账户失败
    public final static String EC_ACCOUNT_CREAT_ERROR = "0206";
    public final static String EC_ACCOUNT_CREAT_ERROR_DESC = "用户账户创建失败";

    public final static String EC_BALANCE_INSUFFICIENT_ERROR = "0207";
    public final static String EC_BALANCE_INSUFFICIENT_ERROR_DESC = "您的账户余额不足，请及时充值";


    //*******05 订单支付相关*********//
    //钱包余额充值次卡提示给用户中间状态
    public final static String EC_ORDER_CHARGEING_DESC = "充值业务已受理，请稍后查看账户余额，若有疑问请联系客服";

    //绑卡提示给用户中间状态
    public final static String EC_CARD_BINDING_DESC = "绑卡处理中";
    //解绑成功
    public final static String EC_UNBIND_CARD_DESC = "解绑成功";
    //无跳转支付-不支持描述
    public final static String EC_NOT_SUPPORT_DESC = "温馨提示：信用卡不可用于余额充值";
    //去绑卡已启用信息描述  这边必须要包含去绑卡关键字
    public final static String EC_GO_BIND_TIP_DESC = "绑卡快捷支付已上线，立即去绑卡";
    //去绑卡弹出信息描述
    public final static String EC_GO_BIND_ALERT_DESC = "绑定银行卡后，支付更便捷。";

    //充值订单信息生成失败
    public final static String EC_ORDER_CHARGE_ERROR = "0501";
    public final static String EC_ORDER_CHARGE_ERROR_DESC = "充值订单信息生成失败";

    //错误的交易历史记录
    public final static String EC_ACCOUNT_CHGLOG_ERROR = "0502";
    public final static String EC_ACCOUNT_CHGLOG_ERROR_DESC = "交易历史记录错误";

    //交易撤销失败
    public final static String EC_ORDER_CANCEL_ERROR = "0503";
    public final static String EC_ORDER_CANCEL_ERROR_DESC = "撤销失败";

    //订单金额有误
    public final static String EC_CHARGING_PARAMS_ERROR = "0504";
    public final static String EC_CHARGING_PARAMS_ERROR_DESC = "金额有误";

    //中银通支付通道异常，请稍后再试！
    public final static String EC_ACCOUNT_CHANNEL_ERROR = "0505";
    public final static String EC_ACCOUNT_CHANNEL_ERROR_DESC = "支付通道异常，请稍后再试";

    //输入银行卡错误
    public final static String EC_BANK_CARD_ERROR = "0506";
    public final static String EC_BANK_CARD_ERROR_DESC = "输入银行卡错误";

    //银行卡已经被绑定
    public final static String EC_BANK_CARD_EXIST_ERROR = "0507";
    public final static String EC_BANK_CARD_EXIST_ERROR_DESC = "银行卡已经被绑定";

    //验证码发送失败
    public final static String EC_BANK_SMS_CODE_ERROR = "0508";
    public final static String EC_BANK_SMS_CODE_ERROR_DESC = "验证码发送失败";

    //充值余额不能使用信用卡
    public final static String EC_BALANCE_NOT_CREDIT_ERROR = "0509";
    public final static String EC_BALANCE_NOT_CREDIT_ERROR_DESC = "充值余额不能使用信用卡";

    //充值订单已经生成
    public final static String EC_ORDER_EXIST_ERROR = "0510";
    public final static String EC_ORDER_EXIST_ERROR_DESC = "充值订单已经生成";

    //操作过于频繁，请稍后重试！
    public final static String EC_SMS_OPERATING_FREQUENTLY_ERROR = "0511";
    public final static String EC_SMS_OPERATING_FREQUENTLY_ERROR_DESC = "操作过于频繁，请稍后重试";

    //您存在未完成的银行卡支付订单，请完成支付后再进行解绑操作
    public final static String EC_CARD_WAIT_PAY_ERROR = "0512";
    public final static String EC_CARD_WAIT_PAY_ERROR_DESC = "请完成支付后再进行解绑操作";

    public final static String EC_REFUND_INFO_SOURCE_PARAMS_ERROR = "0513";
    public final static String EC_REFUND_INFO_SOURCE_PARAMS_DESC = "退款申请来源不能为空";

    public final static String EC_REFUND_AMOUNT_PARAMS_ERROR = "0514";
    public final static String EC_REFUND_AMOUNT_PARAMS_DESC = "退款金额不能为空";

    public final static String EC_REFUND_AMOUNT_ZERO_PARAMS_ERROR = "0515";
    public final static String EC_REFUND_AMOUNT_ZERO_PARAMS_DESC = "退款金额为0，请查看余额是否正确";

    public final static String EC_REFUND_CARD_ZERO_PARAMS_ERROR = "0516";
    public final static String EC_REFUND_CARD_ZERO_PARAMS_DESC = "退款计次卡次数为0，请查看计次卡是否正确";

    public final static String EC_REFUND_CHANNEL_PARAMS_ERROR = "0517";
    public final static String EC_REFUND_CHANNEL_PARAMS_DESC = "退款通道不能为空";

    public final static String EC_REFUND_ACCOUNT_PARAMS_ERROR = "0518";
    public final static String EC_REFUND_ACCOUNT_PARAMS_DESC = "收款账号不能为空";

    public final static String EC_REFUND_BANK_PARAMS_ERROR = "0519";
    public final static String EC_REFUND_BANK_PARAMS_DESC = "收款账号开户行不能为空";

    public final static String EC_REFUND_TYPE_PARAMS_ERROR = "0520";
    public final static String EC_REFUND_TYPE_PARAMS_DESC = "退款类型不能为空";

    public final static String EC_REFUND_ACCOUNT_NAME_PARAMS_ERROR = "0521";
    public final static String EC_REFUND_ACCOUNT_NAME_PARAMS_DESC = "收款账户姓名不能为空";

    public final static String EC_REFUND_TYPE_WRONG_PARAMS_ERROR = "0522";
    public final static String EC_REFUND_TYPE_WRONG_PARAMS_DESC = "退款类型错误";

    public final static String EC_REFUND_CLERK_PARAMS_ERROR = "0523";
    public final static String EC_REFUND_CLERK_PARAMS_DESC = "客服人员id不能为空";

    public final static String EC_REFUND_ORDER_PARAMS_ERROR = "0524";
    public final static String EC_REFUND_ORDER_PARAMS_DESC = "原始订单id为空";

    public final static String EC_REFUND_ORDER_NOT_EXISTS_ERROR = "0525";
    public final static String EC_REFUND_ORDER_NOT_EXISTS_DESC = "原始订单不存在";

    public final static String EC_REFUND_AMOUNT_WRONG_ERROR = "0526";
    public final static String EC_REFUND_AMOUNT_WRONG_DESC = "账户余额大于20元，暂不可退款";

    public final static String EC_REFUND_PARAMS_ERROR = "0527";
    public final static String EC_REFUND_PARAMS_DESC = "退款参数检查失败";

    public final static String EC_REFUND_BALANCE_WRONG_ERROR = "0528";
    public final static String EC_REFUND_BALANCE_WRONG_DESC = "账户余额有误，请退出后重新提交";

    public final static String EC_REFUND_DUPLICATE_ERROR = "0529";
    public final static String EC_REFUND_DUPLICATE_DESC = "重复退款的订单";

    public final static String EC_REFUND_SAVE_ERROR = "0530";

    public final static String EC_REFUND_NOT_EXISTS_ERROR = "0531";
    public final static String EC_REFUND_NOT_EXISTS_DESC = "退款记录不存在";

    public final static String EC_REFUND_NOT_FINISH_ERROR = "0532";
    public final static String EC_REFUND_NOT_FINISH_DESC = "您有未完成的余额退款，暂不能退款";

    public final static String EC_REFUND_CHANNEL_PARAMS_ERROR_CODE = "0533";
    public final static String EC_REFUND_CHANNEL_PARAMS_ERROR_DESC = "退款通道类型错误";

    public final static String EC_REFUND_UNFINISHED_TRIP_ERROR_CODE = "0534";
    public final static String EC_REFUND_UNFINISHED_TRIP_ERROR_DESC = "您有未完成的行程，暂不能退款，请先将行程完成，再退款";

    public final static String EC_REFUND_UNFINISHED_ORDER_ERROR_CODE = "0535";
    public final static String EC_REFUND_UNFINISHED_ORDER_ERROR_DESC = "您有未完成的订单，暂不能退款，请先将订单完成，再退款";

    public final static String EC_ACCOUNT_CK_ERROR = "0536";
    public final static String EC_ACCOUNT_CK_ERROR_DESC = "优惠券次数错误";

    public final static String EC_CREATE_COMMON_ORDER_ERROR_CODE = "0536";
    public final static String EC_CREATE_COMMON_ORDER_ERROR_CODE_DESC = "创建订单失败";

    public final static String EC_CLOSE_ORDER_ERROR_CODE = "0537";
    public final static String EC_CLOSE_ORDER_ERROR_CODE_DESC = "订单已经关闭";

    //订单已经支付成功
    public final static String EC_ORDER_PAY_SUCCESS_ERROR = "0538";
    public final static String EC_ORDER_PAY_SUCCESS_ERROR_DESC = "订单已经支付成功";

    //验证码不正确
    public final static String EC_BANK_SMS_NOT_INCORRECT_ERROR = "0539";
    public final static String EC_BANK_SMS_NOT_INCORRECT_ERROR_DESC = "验证码不正确";

    //没有中银通账户，不允许使用
    public final static String EC_ACCOUNT_DENY = "0211";
    public final static String EC_ACCOUNT_DENY_DESC = "请先实名认证后开通现金账户";

    //*******03 黑白名单*********
    //白名单不存在
    public final static String EC_USERWHITE_NOTEXIST_ERROR = "0301";
    public final static String EC_USERWHITE_NOTEXIST_ERROR_DESC = "白名单不存在";

    //白名单已存在
    public final static String EC_USERWHITE_EXIST_ERROR = "0302";
    public final static String EC_USERWHITE_EXIST_ERROR_DESC = "白名单已经存在";

    //*******04 优惠券相关*********
    public final static String EC_COUNPON_ERROR = "0401";
    public final static String EC_COUNPON_ERROR_DESC = "无可领优惠劵";

    public final static String EC_COUNPON_TYPE_ERROR = "0402";
    public final static String EC_COUNPON_TYPE_ERROR_DESC = "优惠劵类型错误";

    public final static String EC_COUNPON_ONLY_NEWER_ERROR = "0403";
    public final static String EC_COUNPON_ONLY_NEWER_ERROR_DESC = "只支持新用户";

    public final static String EC_COUNPON_NEWER_REPEAT_ERROR = "0404";
    public final static String EC_COUNPON_NEWER_REPEAT_ERROR_DESC = "您之前重复注册并且领取过";

    //错误的交易历史记录
    public final static String EC_ACCOUNT_CHGLOG_CANCEL_ERROR = "0405";
    public final static String EC_ACCOUNT_CHGLOG_CANCEL_ERROR_DESC = "重复的交易撤销！";

    //错误的交易历史记录
    public final static String EC_ACCOUNT_CHGLOG_OPER_ERROR = "0406";
    public final static String EC_ACCOUNT_CHGLOG_OPER_ERROR_DESC = "操作频繁！";

    //订单金额有误
    public final static String EC_CHARGING_PARAMS_ZERO_ERROR = "0407";
    public final static String EC_CHARGING_PARAMS_ZERO_ERROR_DESC = "充值金额为0";

    //订单金额有误
    public final static String EC_ACCOUT_PARAMS_ZERO_ERROR = "0408";
    public final static String EC_ACCOUT_PARAMS_ZERO_ERROR_DESC = "消费金额为0";

    //交易查询
    public final static String EC_ACCOUT_PARAMS_QUERY_ERROR = "0409";
    public final static String EC_ACCOUT_PARAMS_QUERY_DESC = "暂无交易记录";

    //交易查询
    public final static String EC_ACCOUT_PARAMS_QUERY_FAIL_ERROR = "0410";
    public final static String EC_ACCOUT_PARAMS_QUERY_FAIL_DESC = "查询失败";

    public final static String EC_ACCOUNT_CHGLOG_DUP_ERROR = "0411";
    public final static String EC_ACCOUNT_CHGLOG_DUP_DESC = "重复的交易";

    public final static String EC_ACCOUNT_OPEN_CARD_ERROR = "0412";
    public final static String EC_ACCOUNT_OPEN_CARD_ERROR_DESC = "用户已绑定了银行卡";

    //*******08 行程相关*********
    public final static String EC_TRIP_NOT_EXIST_ERROR = "0801";
    public final static String EC_TRIP_NOT_EXIST_DESC = "行程不存在";

    public final static String EC_TRIP_STATUS_EXP_ERROR = "0802";
    public final static String EC_TRIP_STATUS_EXP_DESC = "行程不存在或状态异常";

    public final static String EC_TRIP_AREA_ERROR = "0803";
    public final static String EC_TRIP_AREA_DESC = "行程地区错误";

    public final static String EC_SERVICE_ID_ERROR = "0804";
    public final static String EC_SERVICE_ID_DESC = "服务id为空";

    public final static String EC_TRIP_NO_NULL_ERROR = "0805";
    public final static String EC_TRIP_NO_NULL_DESC = "行程单号为空";

    public final static String EC_DIRECTION_ERROR = "0806";
    public final static String EC_DIRECTION_DESC = "进出站标识为空";

    public final static String EC_STATIOND_ID_ERROR = "0807";
    public final static String EC_STATIOND_ID_DESC = "站点信息为空";

    public final static String EC_CONFIRM_STATUS_ERROR = "0808";
    public final static String EC_CONFIRM_STATUS_DESC = "确认状态为空";

    public final static String EC_CLEAL_STAFF_ID_ERROR = "0809";
    public final static String EC_CLEAL_STAFF_ID_DESC = "确认员工Id为空";

    public final static String EC_SCAN_TIME_ERROR = "0810";
    public final static String EC_SCAN_TIME_DESC = "扫码时间为空";

    //二维码提示
    public final static String EC_QRCODE_ERROR = "1101";
    public final static String EC_QRCODE_ERROR_DESC = "二维码生成失败，请重试";

    public final static String EC_QRCODE_BALANCE_ERROR = "1102";
    public final static String EC_QRCODE_BALANCE_ERROR_DESC = "二维码获取失败，钱包余额不足";

    public final static String EC_QRCODE_ORDER_ERROR = "1103";
    public final static String EC_QRCODE_ORDER_ERROR_DESC = "二维码获取失败，存在未支付订单";

    public final static String EC_TRIP_NOT_PAY_ERROR = "1103";
    public final static String EC_TRIP_NOT_PAY_DESC = "您有一条未支付的异常行程，为避免影响您的出行，请及时完成行程支付";

    public final static String EC_TRIP_NOT_OUT_ERROR = "1104";
    public final static String EC_TRIP_NOT_OUT_DESC = "您有一条行程记录信息不全，请至我的行程进行补登处理";

    public final static String EC_TRIP_DATA_ERROR = "1105";
    public final static String EC_TRIP_DATA_DESC = "行程异常";


    public final static String EC_SYSTEM_ERROR = "0901";
    public final static String EC_SYSTEM_DESC = "系统异常";
    public final static String EC_CONNECT_FAIL_ERROR = "0902";
    public final static String EC_CONNECT_FAIL_DESC = "数据为空";
    public final static String EC_DYNAMIC_ERROR = "0907";
    public final static String EC_DYNAMIC_DESC = "处理动态参数失败";

    public final static String LEND_RAINCLOTH_FAIL_CODE = "0903";
    public final static String LEND_RAINCLOTH_FAIL_MSG = "你当月已借用雨披三次，达到借用上限，无法再次借用，给你带来的不便请多包涵，谢谢理解与支持";

    public final static String LEND_UMBRELLA_BORROW_CODE = "0904";     //当前有借伞记录
    public final static String LEND_UMBRELLA_BORROW_MSG = "你于%s借用的雨伞尚未归还，归还到期日为%s，请先归还";

    public final static String APPOINT_NOT = "0905";

    public final static String APPOINT_CODE = "0906";
    public final static String APPOINT_MSG = "可预约的时间段为每日7:00至22:00，且必须提前30分钟预约";

    public final static String LOG_FILENAME_ERROR = "0907";
    public final static String LOG_FILENAME_DESC = "日志文件为空";


    /**
     * 停车场业务提示
     */

    public final static String EC_PARKLOT_YES_BIND_ERROR = "1201";
    public final static String EC_PARKLOT_YES_BIND_ERROR_DESC = "车牌号已绑定";

    public final static String EC_PARKLOT_NO_BIND_ERROR = "1202";
    public final static String EC_PARKLOT_NO_BIND_ERROR_DESC = "车牌号未绑定";

    public final static String EC_PARKLOT_FORM_ERROR = "1203";
    public final static String EC_PARKLOT_FORM_ERROR_DESC = "车牌格式错误";

    public final static String EC_PARKLOT_CREATE_ORDER_ERROR = "1204";
    public final static String EC_PARKLOT_CREATE_ORDER_DESC = "创建订单失败";

    public final static String EC_PARKLOT_CONSUME_CHANGE_ERROR = "1205";
    public final static String EC_PARKLOT_CONSUME_CHANGE_DESC = "金额发生改变，请重新刷新页面";

    public final static String EC_PARKLOT_SEARCH_CONSUME_ERROR = "1206";
    public final static String EC_PARKLOT_SEARCH_CONSUME_DESC = "查询实时消费错误，请重试";

    public final static String EC_PARKLOT_CAR_NUM_ENTRY_ERROR = "1207";
    public final static String EC_PARKLOT_CAR_NUM_ENTRY_DESC = "该用户有车辆入场记录，无法操作";

    public final static String EC_PARKLOT_CAR_NUM_NOT_ENTRY_ERROR = "1208";
    public final static String EC_PARKLOT_CAR_NUM_NOT_ENTRY_DESC = "该车辆未入场";

    public final static String EC_PARKLOT_DATA_VALIDATOR_ERROR = "1209";
    public final static String EC_PARKLOT_DATA_VALIDATOR_DESC = "数据校验失败";

    public final static String EC_PARKLOT_NOT_EXISTS_ERROR = "1210";
    public final static String EC_PARKLOT_NOT_EXISTS_DESC = "停车场不存在";

    public final static String EC_PARKLOT_CAR_NUM_NOT_OUT_ERROR = "1211";
    public final static String EC_PARKLOT_CAR_NUM_NOT_OUT_DESC = "该车辆未出场";

    public final static String EC_PARKLOT_ORDER_NOT_EXISTS_ERROR = "1212";
    public final static String EC_PARKLOT_ORDER_NOT_EXISTS_DESC = "停车场订单不存在";

    public final static String EC_PARKLOT_CAR_NUM_IN_ERROR = "1213";
    public final static String EC_PARKLOT_CAR_NUM_IN_DESC = "车辆入场失败";

    public final static String EC_PARKLOT_CAR_NUM_OUT_ERROR = "1214";
    public final static String EC_PARKLOT_CAR_NUM_OUT_DESC = "车辆出场失败";

    public final static String EC_PARKLOT_CAR_NUM_OUT_SYN_ERROR = "1215";
    public final static String EC_PARKLOT_CAR_NUM_OUT_SYN_DESC = "出场数据同步失败";

    public final static String EC_PARKLOT_QUERY_USER_CREDIT_ERROR = "1216";
    public final static String EC_PARKLOT_QUERY_USER_CREDIT_DESC = "查询用户授权失败";

    public final static String EC_PARKLOT_ORDER_PAYED_ERROR = "1217";
    public final static String EC_PARKLOT_ORDER_PAYED_DESC = "订单已支付，请刷新页面";

    public final static String EC_PARKLOT_ORDER_PAYE_ERROR = "1218";
    public final static String EC_PARKLOT_ORDER_PAYE_DESC = "用户有未支付的订单";

    /**
     * * 注销用户提示 管理后台用
     */
    public final static String EC_CANCELL_USER_SUCCESS_DESC = "用户注销成功！";
    public final static String EC_CANCELL_USER_FAIL_DESC = "注销账户异常，注销失败！";

}
