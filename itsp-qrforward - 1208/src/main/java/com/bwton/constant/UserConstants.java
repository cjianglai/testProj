package com.bwton.constant;

/**
 * 用户登陆相关的常量
 * Created by dingyufang on 2017/6/26.
 */
public class UserConstants {
    //用户状态 0-注销 1-正常
    public enum EnumUserStatus {
        UNREG, NORMAL;
    }

    public final static int UP_POS_REAL_NAME_REG = 1; //用户属性中登记的标志位
    public final static int UP_POS_INTELLIGENT_FEE = 2; //用户属性中智能扣费的标志位
    public final static int UP_POS_REAL_NAME_AUTH = 3; //用户属性中认证的标志位
    public final static int UP_POS_REAL_NAME_CHK= 4; //用户属性中实名的标志位
    public final static int UP_POS_REAL_NAME_OPEN= 5; //用户属性中开户的标志位

    //新注册用户（所有城市），智能支付开关默认关闭；（需求提出者：jiangaiqiong@bwton.com）
    //public final static String USER_PROPERTIES_DEFAULT = "0100000000"; //用户默认属性值
    public final static String USER_PROPERTIES_DEFAULT = "0000000000"; //用户默认属性值

    //身份证件枚举值，其中的code为数据库中的存储标识
    public enum EnumUserIDType {
        ID_CARD(1);

        private int code;

        private EnumUserIDType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
    /**
     * 验证零和非零开头的数字的正则表达式
     */
    public static final String NUM_REGEX = "^(0|[1-9][0-9]*)$";
    /**
     *身份证的正则表达式
     */
    public static final String IDNUM_REGEX = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
    /**
     * 验证手机号的正则表达式
     */
    public static final String PHONE_REGEX = "^((13[0-9])|(15[012356789])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$";
    /**
     * yyyy-MM-dd日期格式的正则表达式
     */
    public static final String DATE_REGEX = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";

    /**
     * Redis 用户的TOKEN KEY
     */
    public static final String REDIS_CACHE_TOKEN = "USER_TOKEN_CACHE";

}
