package com.bwton.data.domain.qrcode;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ***********************************************************
 * Copyright © 2017 八维通科技有限公司 Inc.All rights reserved.  *
 * ***********************************************************
 *
 * @Company: www.bwton.com
 * @Generator: IntelliJ IDEA
 * @Project: itsp-parent
 * @Package: com.bwton.data.domain.qrcode
 * @Name: QrcodeParam
 * @Author: zhaokaikai@bwton.com
 * @Date: 2017年10月30日 17时33分
 * @Description: 请描述类的作用
 */
@Data
public class QrcodeParam implements Serializable {
    String full_dig_user_id;//全位数用户ID(不足补为16位)
    String user_id;
    /**
     * 用户类型 1-个人用户  2-后台管理员
     */
    String user_type;
    String city_id;
    String service_id;
    String app_version;
    String beta_status;
    String bundle_id;
    String id_no;//身份证号
    String qr_code;//用于请求二维码平台二维码数据信息的二维码编码
    Date request_date;//请求二维码时间
    long stationId;//所处站点id
    float lng;//所处位置经度
    float lat;//所处位置纬度
    /**
     *业务来源 1-用户App  2-小黄机
     */
    int business_source = 1;

}
