///**
// *
// */
//package com.bwton.support;
//
//
//import com.yanyan.service.system.DictionaryService;
//import org.redisson.api.listener.MessageListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
///**
// * 获取配置属性
// *
// * @author Administrator
// */
//@Component("propertiesInfo")
//public class PropertiesInfo implements ApplicationListener<ContextRefreshedEvent> {
//    private Logger logger = LoggerFactory.getLogger(PropertiesInfo.class);
//    @Autowired
//    DictionaryService sDictionaryService;
//
//    @Autowired
//    RedissonUtil util;
//
//    public static String ACC_SERVER_IP; //ACC密钥接口服务器IP
//    public static int ACC_SERVER_PORT; //ACC密钥接口服务器端口
//    public static boolean ACC_ANY_KEY; //随便生成一个ACC密钥;
//    public static int QRCODE_CURRTIME_OFFSET = -30; //二维码当前时间偏移值（秒）
//    //public static int QRCODE_EFFECT_TIME = 30; //二维码有效期（秒）
//    //public static int QRCODE_EFFECT_TIME_STAFF = 30; //二维码有效期（秒）后台用户
//
//    public static String FEE_SERVER_IP;//计费服务器IP
//    public static int FEE_SERVER_PORT;//计费服务器端口
//
//    public static String ZYT_ADDR_PREFIX = "http://210.22.91.77:18080/CXF-webservice/services/";//中银通接口地址前缀
//    public static boolean ZYT_ACCOUNT_CONNECT; //中银通账户是否对接
//    public static String ZYT_MERCHANT_ID; //中银通商户IDzyt.merchant.id
//    public static String ZYT_PUBLIC_KEY; //中银通报文公钥zyt.public.key
//
//    public static boolean SERVER_ENV_PRODUCT = false; //是否生成环境；
//    public static String IOS_APNS_ENV = "PRODUCT"; //IOS消息推送环境变量，DEV 或 PRODUCT
//    public static String[] APP_VER_LIST_IOS; //接口版本列表；
//    public static String[] APP_VER_LIST_ANDROID; //接口版本列表；
//
//    //用户相关
//    public static boolean USER_TOKEN_LIMIT = false; //是否限制同一用户不能在不同终端登录
//    public static boolean USER_AUTH_CHECK =false;//用户认证开关是否开启 false-关闭 true-开启
//
//    //账户支付限制相关配置
//    public static int PAY_EXPIRES_SECOND = 300;//支付订单失效秒数，单位为秒
//    public static int PAY_FAILURE_RETRY_TIMES = 3;//失败重试次数
//    public static int UNION_SINGLE_CARD_DAY_CUMULATIVE_LIMIT = 200000;//单卡单日累计限额，单位为分
//
//    public static String[] ZYT_ERROR_CODE; //中银通错误代码；
//    public static String TOKEN_MAXTIME = "30";
//
//    //自行车APP服务相关
//    public static String BICYLE_SERVICE_KEY = "";
//    public static String BICYLE_SERVER_IP = "";//自行车RestFul服务器地址
//    public static String BICYLE_SERVER_PORT = "";//自行车RestFul服务器地址端口号
//    public static String BICYLE_SERVICE_SUGGESTION_COMMIT = "";//自行车建议上报服务
//    public static String BICYLE_SERVICE_SUGGESTION_CANCEL = "";//自行车建议取消服务
//    public static String BICYLE_SERVICE_ORDER_CALLBACK = "/api/sys/bm/trade/feedback";//自行车消费成功通知服务
//    public static boolean BICYLE_PAY_CHANNEL_UNION_ENABLE = false;//自行车支付渠道-银联是否可用
//    public static boolean BICYLE_PAY_CHANNEL_ALI_ENABLE = false;//自行车支付渠道-支付宝是否可用
//    public static boolean BICYLE_PAY_CHANNEL_BANK_ENABLE = false;//自行车支付渠道-银行卡是否可用
//
//    //模拟中银通连接状态仿真
////    public static boolean SIMULATE_ZYT_ENABLE_CHECK = false;
////    public static int SIMULATE_ZYT_CONSUME_CALLBACK_STATUS=101;   // 101 - 模拟中银通返回成功  102 - 模拟中银通返回失败  103 - 模拟中银通返回超时
////    public static int SIMULATE_ZYT_QUERY_CALLBACK_STATUS=101;//101 - 模拟中银通返回查询成功  102 - 模拟中银通返回查询无交易记录  102 - 模拟中银通返回提示支付失败  103 - 模拟中银通返回提示查询异常
//
//    //鸿联95短信发送相关
//    public static String HL95_SEND_URL = ""; //鸿联95短信网关地址
//    public static String HL95_EPID = ""; //鸿联95短信网关企业ID
//    public static String HL95_USERNAME = ""; //鸿联95短信网关用户名
//    public static String HL95_PASSWORD = ""; //鸿联95短信网关密码
//
//    //八维通付
//    public static String BWTON_PAY_REALNAME_AUTH_URL = "";//八维通付用户实名认证地址
//
//    //停车场相关
//    public static boolean PARK_LOT_CONSUME_ENABLE = false;//停车场功能开关 false-关闭  true-打开
//    public static boolean PARK_LOT_CONSUME_KETUO_ENABLE = false;//停车场功能-科拓公司-开关 false-关闭  true-打开
//    public static String PARK_LOT_CONSUME_KETUO_URL = "";//停车场功能-科拓公司访问地址
//    public static String PARK_LOT_CONSUME_APPID_BWT_TO_KETUO = "";//停车场功能-码上行提供给科拓公司APPID
//    public static String PARK_LOT_CONSUME_APPID_KETUO_TO_BWT = "";//停车场功能-科拓公司提供给码上行APPID
//    public static String PARK_LOT_SECRET_KEY_KETUO_TO_BWT = "";//停车场功能-科拓公司提供给码上行的加密秘钥
//    public static String PARK_LOT_SECRET_KEY_BWT_TO_KETUO = "";//停车场功能-码上行提供科拓公司给的加密秘钥
//
//    public static int PARK_LOT_CONSUME_NOTICE_RETRY_TIMES = 10;//停车场订单通知失败次数
//    public static String PARK_LOT_ORDER_PRODUCT_NAME = "";//停车场订单产品名称
//    public static int PARK_LOT_LINE_OF_CREDIT = 0;//停车场用户授信额度
//    public static int PARK_LOT_LIMIT_USER_BALANCE = 0;//停车场使用限制用户余额
//    public static String PARK_LOT_ORDER_TIMEOUT_EXPRESS = "3000";//停车场订单过期时间，单位秒
//    public static String PARK_LOT_TEST_URL = "";//码上行停车场内部测试URL
//    public static boolean PARK_LOT_SERVER_ENV_PRODUCT = false;
//    public static int PARK_LOT_TEST_CONSUME_MONEY = 0;//码上行停车场内部测试金额
//
//
//    //是否需要更新系统
//    public static boolean SERVER_REBOOT_CHECK = false;
//
//    void initMethod() {
//        //从数据读取所有app的配置
//        readSysParamFromDB();
//
//        //初始化监听
////        initListener();
//    }
//
//    public void initListener() {
//        logger.info("系统配置监听初始化....");
//        // 发布消息
//        util.addEventListener("PropertiesTopic", new MessageListener<String>() {
//            @Override
//            public void onMessage(String channel, String message) {
//                try {
//                    readSysParamFromDB();
//                } catch (Exception e) {
//                    logger.error("系统配置监听初始化异常", e);
//                }
//            }
//        });
//    }
//
//
//    public void readSysParamFromDB() {
//        try {
////          Map<String, String> dic = sDictionaryService.getGroup( 2 );
//            Class clazz = this.getClass();   // Class.forName("com.bwton.support.PropertiesInfo");
//            Field[] fields = clazz.getFields();
//            Object propertiesBean = this.getClass();// getBean("com.bwton.support.PropertiesInfo");
//            for (Field field : fields) {
//                //对应的数据库检索出来的值
//                String argValue = null;
//                try {
//                    argValue = sDictionaryService.getValue(field.getName());
//                } catch (Exception e) {
//                    logger.info("调用sDictionaryService异常", e);
//                }
//                if (argValue == null) {
//                    continue;
//                }
//
//                //更新属性
//                if (field.getType().getSimpleName().equals("String[]")) {
//                    String[] split = argValue.split(",");
//                    field.set(propertiesBean, split);
//                } else if (field.getType().getSimpleName().equals("String")) {
//                    field.set(propertiesBean, argValue);
//                } else if (field.getType().getSimpleName().equals("boolean")) {
//                    Boolean boolVal = argValue.toLowerCase().equals("true");
//                    field.set(propertiesBean, boolVal);
//                } else if (field.getType().getSimpleName().equals("int")) {
//                    Boolean boolVal = argValue.toLowerCase().equals("true");
//                    field.set(propertiesBean, Integer.valueOf(argValue));
//                }
//                logger.info("Field Name:" + field.getName() + ", Type:" + field.getType().getSimpleName() + ",Val:" + field.get(propertiesBean));
//            }
//
//            logger.info("####################################################################");
//            logger.info("#     服务加载完成-SUCCESS                                           #");
//            logger.info("#     版本号：V3.0.0                                                 #");
//            logger.info("#     Copyright © 2017 八维通科技有限公司 Inc.All rights reserved.   #");
//            logger.info("#####################################################################");
//
//
//
//        } catch (Exception e) {
//            logger.info("PropertiesInfo init error!!!!", e);
//        }
//    }
//
//
//    //判断是否支持某个版本号
//    public static boolean appVerCompatible(int termType, String currVer) {
//        if (termType == 0) { //Android
//            for (String s : APP_VER_LIST_ANDROID) {
//                if (s.equals(currVer))
//                    return true;
//            }
//        } else { //IOS
//            for (String s : APP_VER_LIST_IOS) {
//                if (s.equals(currVer))
//                    return true;
//            }
//        }
//        return false;
//    }
//
//
//    public static void setProperty(Object bean, String propertyName, Object propertyValue) throws Exception {
//        Class cls = bean.getClass();
//        Method[] methods = cls.getMethods();//得到所有方法
//        //cls.getFields();//所有公开字段属性
//        for (Method m : methods) {
//            if (m.getName().equalsIgnoreCase("set" + propertyName)) {
//                m.invoke(bean, propertyValue);
//                break;
//            }
//        }
//    }
//
//
//    public static Object getBean(String className) throws Exception {
//        Class cls = null;
//        try {
//            cls = Class.forName(className);//对应Spring ->bean -->class
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            throw new Exception("类错误！");
//        }
//
//        Constructor[] cons = null;//得到所有构造器
//        try {
//            cons = cls.getConstructors();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("构造器错误！");
//        }
//        if (cons == null || cons.length < 1) {
//            throw new Exception("没有默认构造方法！");
//        }
//        //如果上面没错，就有构造方法
//
//        Constructor defCon = cons[0];//得到默认构造器,第0个是默认构造器，无参构造方法
//        Object obj = defCon.newInstance();//实例化，得到一个对象 //Spring - bean -id
//        return obj;
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
//            initMethod();
//        }
//    }
//}
