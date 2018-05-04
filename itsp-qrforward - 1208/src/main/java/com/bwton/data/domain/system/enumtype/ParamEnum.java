package com.bwton.data.domain.system.enumtype;

/**
 * ***********************************************************
 * Copyright © 2017 八维通科技有限公司 Inc.All rights reserved.  *
 * ***********************************************************
 *
 * @Company: www.bwton.com
 * @Generator: IntelliJ IDEA
 * @Project: itsp-parent
 * @Package: com.bwton.data.domain.system.enumtype
 * @Name: ParamEnum
 * @Author: zhaokaikai@bwton.com
 * @Date: 2017年10月30日 16时12分
 * @Description: 请描述类的作用
 */
public enum ParamEnum {
    SYS_PARAM_QRCODE("请码系统参数", "SYS_PARAM_QRCODE"),

    SYS_PARAM_COUPON("优惠券配置参数", "SYS_PARAM_COUPON");

    private String name;
    private String key;

    ParamEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

}