package com.bwton.service.qrcode;

import com.bwton.app.domain.ResultResponse;
import com.bwton.data.domain.qrcode.QrcodeParam;
import com.bwton.data.domain.user.AppUserBase;
//import com.bwton.data.vo.qr.traffic.QrTrafficBaseData;
import com.yanyan.web.utils.DataResponse;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Created by huayq on 2017/8/16.
 */

@Component
public interface QrcodeService {

    /**
     * 获取二维码
     *
     * @param qrcodeParam
     * @return
     */
	DataResponse getQrcode(QrcodeParam qrcodeParam);

    /**
     * App客户端用户获取二维码数据信息
     *
     * @param qrcodeParam   请求二维码参数
     * @param systemParams  系统参数
     * @param user          用户信息
     * @param qrcodeHandler 乘车扫码handler
     * @return
     */
    DataResponse getAppQrcode(QrcodeParam qrcodeParam,
                              Map<String, String> systemParams,
                              AppUserBase user, QrcodeHandler qrcodeHandler);
//
//    /**
//     * 小黄机获取二维码数据信息
//     *
//     * @param qrcodeParam   请求二维码参数
//     * @param systemParams  系统参数
//     * @param qrcodeHandler 乘车扫码handler
//     * @return
//     */
    DataResponse getSymQrcode(QrcodeParam qrcodeParam,
                              Map<String, String> systemParams, QrcodeHandler qrcodeHandler);
//
}
