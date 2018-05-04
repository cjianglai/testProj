package com.bwton.constant;

/**
 * Created by hp on 2017/4/18.
 *
 * @author dingyufang
 * @desc 常量类
 */
public interface Constants {
    //信息来源
    int MSX_APP = 1;                        //码上行APP
    int CALL_CENTER = 2;                   //呼叫中心
    int METRO_CLIENT = 3;                  //地铁工作人员客户端（小黄机）

    //城市ID
    int CITY_ID = 3202;                    //无锡城市ID

    //
    String DEFAULT = "0000";                  //补全字符

    //取消预约服务
    int USER_CANCEL = 1;                    //用户取消
    int CLERK_CANCEL = 2;                   //客服取消

    //找到失物用户类型
    int USER_FOUND = 1;                     //用户找到
    int CLERK_FOUND = 2;                    //客服找到

    //完成预约服务
    int USER_FINISH = 1;                    //用户完成
    int CLERK_FINISH = 2;                   //客服完成

    //公益借伞应还天数
    int LEND_DAY = 7;                       //公益借伞应还天数

    //雨披当月最多借用几次
    int LEND_MAX_BORROW = 3;                //雨披每月最多借用几次

    //爱心借用类型
    int LEND_UMBRELLA = 1;                   //借伞
    int LEND_RAINCLOTH = 2;                  //借雨批

    //爱心借伞
    int LEND_BORROW = 1;                     //未归还
    int LEND_RETURN = 2;                     //已归还
    int LEND_OVERTIME = 3;                     //已逾期

    //爱心借伞
    int LEND_NOT_OVERTIME = 0;              //没有逾期记录
    int LEND_OVERTIMEING = 1;                   //有逾期记录
    int LEND_BORROWING = 2;                     //已归还

    //爱心借伞
    int LEND_BORROW_RAINCLOTH = 0;              //能借雨披
    int LEND_NOT_BORROW_RAINCLOTH = 1;                   //不能借雨披


    String LEND_DESC = "雨伞未归还，暂无法继续借用！";
    String ERR_MSG = "系统错误";

    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;

    String DEFAULT_CITY = "3202";//默认城市：无锡

    String REDIS_PRE_FIX_V3 = "ITSP-V3.0:";//REDIS缓存前缀

}
