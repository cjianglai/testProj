package com.bwton;

/**
 * 常量
 * User: Saintcy
 * Date: 2017/1/11
 * Time: 0:10
 */
public class Constants {
    /**
     * 门户
     */
    public class Portal {
        /**
         * 超级管理台
         */
        public static final String SUPERVISOR = "supervisor";
        /**
         * 运营商门户
         */
        public static final String OPERATOR = "operator";
    }

    public class SysParam{

        public static final String FIRST_RECHARGE_CASH_TIP = "FIRST_RECHARGE_CASH_TIP";//用户首次余额充值提示
    }

    /**
     * 来源
     */
    public class Source {
        private static final int LOCAL = 0;
    }

    /**
     * 支付类型
     */
    public class PayBusinessType {
        /**
         * 计费业务 收银台消费
         */
        public static final String PAY_BUSINESS_CHECKOUT = "3";
        /**
         * 计费业务 钱包充值
         */
        public static final String PAY_BUSINESS_CHARGING = "2";
        /**
         * 计数业务 次卡购买
         */
        public static final String PAY_BUSINESS_COUNT = "1";
    }

    public class SystemParamCode {
        /**
         * 次卡套餐
         */
        public static final String FREQUENCY_CARDS = "frequencyCards";
        /**
         * 余额套餐
         */
        public static final String RECHARGE_LIST = "rechargeList";

        /**
         * 其他余额套餐标识符
         */
        public static final String OTHER_BALANCE_FLAG = "-99";

        /**
         * 其他金额描述
         */
        public static final String OTHER_BALANCE_DESC = "其他金额";

    }

    public class AppModuleCode {
        /**
         * 计次卡充值
         */
        public static final String FREQUENCY_CODE = "20000000403";
        /**
         * 智能支付code
         */
        public static final String INTELLIGENT_FEE_CODE = "20001010603";

        /**
         * 扣款顺序绑卡信息隐藏code
         */
        public static final String CHARGE_SEQUENCE_BANK_CODE = "20002010603";
    }

    public class DefaultCityCode {
        /**
         * 默认城市为无锡
         */
        public static final String CITY_WUXI_ID = "3202";
    }

    public class DefaultAppVersionCode
    {
        public static final String APP_VERSION_0100 = "1.0.0";
    }

    public class OrderConfig
    {
        /**
         * 订单充值最大金额，单位为分
         */
        public static final int MAX_AMOUNT = 200000;

        /**
         * 通用支付方式倒计时，单位为秒
         */
        public static final int COMMON_PAY_COUNTDOWN = 15;

        /**
         * 通用支付方式倒计时描述
         */
        public static final String COMMON_PAY_COUNTDOWN_DESC = "订单处理中";

    }

}
