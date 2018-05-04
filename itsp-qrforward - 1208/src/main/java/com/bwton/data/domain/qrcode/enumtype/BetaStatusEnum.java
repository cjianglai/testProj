package com.bwton.data.domain.qrcode.enumtype;

/**
 * ***********************************************************
 * Copyright © 2017 八维通科技有限公司 Inc.All rights reserved.  *
 * ***********************************************************
 *
 * @Company: www.bwton.com
 * @Generator: IntelliJ IDEA
 * @Project: itsp-parent
 * @Package: com.bwton.data.domain.qrcode.enumtype
 * @Name: BetaStatusEnum
 * @Author: zhaokaikai@bwton.com
 * @Date: 2017年11月02日 16时57分
 * @Description: BetaStatusEnum
 *               用于在请码的时候标记此用户是内测还是开放的
 */
public enum BetaStatusEnum {

    CLOSE_BETA("内测","0"),
    OPEN("公开","1");

    private String name ;
    private String index ;

    BetaStatusEnum( String name , String index ){
        this.name = name ;
        this.index = index ;
    }

    public String getName() {
        return name;
    }
    public String getIndex() {
        return index;
    }

}
