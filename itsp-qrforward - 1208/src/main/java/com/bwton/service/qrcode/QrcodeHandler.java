package com.bwton.service.qrcode;

import com.bwton.data.domain.qrcode.QrcodeParam;

import java.util.Map;

/**
 * ***********************************************************
 * Copyright © 2017 八维通科技有限公司 Inc.All rights reserved.  *
 * ***********************************************************
 *
 * @Company: www.bwton.com
 * @Generator: IntelliJ IDEA
 * @Project: itsp-parent
 * @Package: com.bwton.service.qrcode.handler
 * @Name: BusQrcodeHandler
 * @Author: zhaokaikai@bwton.com
 * @Date: 2017年11月02日 09时33分
 * @Description: QrcodeHandler
 */

public interface QrcodeHandler {

    /**
     * @param qrcodeParam  二维码请求参数
     * @param tripInfo     行程数据信息
     * @param systemParams 系统参数数据信息
     * @param expandData   扩展信息
     * @param effectTime  二维码有效期（秒）
     * @return
     * @description
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-02 11:25
     */
    String RequestQrcode(QrcodeParam qrcodeParam,
                         Map<String, String> tripInfo,
                         Map<String, String> systemParams,
                         Map<String, Object> expandData,
                         String effectTime);

    /**
     * @param qrcodeParam  请求二维码参数
     * @param systemParams 系统参数
     * @return
     * @description
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-06 11:25
     */
    Map<String, Object> ExpandBusiness(QrcodeParam qrcodeParam, Map<String, String> systemParams);

    /**
     *获取二维码提示信息，显示在App界面上，比如"该码指用于进站"
     * @param ioFlag 进出站标识
     * @param qrcodeParam  请求二维码参数
     * @param systemParams 系统参数
     * @return
     */
    String getInOrOutNotice(int ioFlag, QrcodeParam qrcodeParam, Map<String, String> systemParams);

}
