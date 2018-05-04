package com.bwton.support;

import com.bwton.constant.Constants;
import com.bwton.constant.UserConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yanyan.core.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hp on 2017/4/18.
 *
 * @author dingyufang
 */
@Slf4j
public class CommonUtils {

    private static Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    //获取日期字符串
    public static String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }

    //生成服务单号
    public static String getSheetNO(long idWorkerId) {
        //生成服务单号
        StringBuffer sheetno = new StringBuffer("");
        sheetno.append(Constants.CITY_ID).append(getDateString()).
                append(Constants.DEFAULT).append(idWorkerId);
        return sheetno.toString();
    }

    /**
     * 生成uuid，没有-
     *
     * @return String uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取账户类型加卡号名称
     *
     * @param externalTypeName 账户类型名称
     * @param accountNo        账户卡号
     * @return String 返回账户加卡号
     */
    public static String getExternalAccountName(String externalTypeName, String accountNo) {
        if (StringUtils.isEmpty(accountNo)) {
            return "";
        }
        if (StringUtils.isEmpty(externalTypeName)) {
            externalTypeName = "其他银行";
        }
        int length = accountNo.length();
        String newAccountNo = length > 4 ? accountNo.substring(length - 4, length) : accountNo;
        StringBuffer sb = new StringBuffer();
        String accountName = sb.append(externalTypeName).
                append("(").append(newAccountNo).append(")").toString();
        return accountName;
    }

    /**
     * 获取银行卡号，格式为4587
     *
     * @param accountNo 账户卡号
     * @return String 返回账户加卡号
     */
    public static String getAfterFourthExternalAccountNo(String accountNo) {
        if (StringUtils.isAnyEmpty(accountNo)) {
            return "";
        }
        int length = accountNo.length();
        String newAccountNo = length > 4 ? accountNo.substring(length - 4, length) : accountNo;
        return newAccountNo;
    }

    /**
     * 获取银行卡号，格式为**** **** **** 4587
     *
     * @param accountNo 账户卡号
     * @return String 返回账户加卡号
     */
    public static String getExternalAccountNo(String accountNo) {
        if (StringUtils.isAnyEmpty(accountNo)) {
            return "";
        }
        int length = accountNo.length();
        String newAccountNo = length > 4 ? accountNo.substring(length - 4, length) : accountNo;
        StringBuffer sb = new StringBuffer();
        String accountName = sb.append("**** **** **** ").append(newAccountNo).toString();
        return accountName;
    }

    /**
     * 获取支付流水号   流水号规则：YYYYMMDDHHMMSS+idworker+00+失败次数
     *
     * @param failureRetryTimes 失败重试次数
     * @return Long 支付流水号
     */
    public static String getPayTradeNo(Integer failureRetryTimes, long idWorkerId) {
//        String curDateStr = DateUtil.DateToString(new Date(), DateStyle.YYYYMMDDHHMMSS);
        StringBuffer payTradeNoSb = new StringBuffer();
        if (failureRetryTimes == null) {
            failureRetryTimes = 0;
        }
        return payTradeNoSb./*append(curDateStr).*/append(idWorkerId).append("10").
                append(failureRetryTimes).toString();
    }

    /**
     * 是否通过登记
     *
     * @param userProperties 用户属性
     * @return boolean true已认证 false 未认证
     */
    public static boolean realNameRegValid(String userProperties) {
        if (userProperties == null || userProperties.length() == 0) {
            return false;
        }
        if (userProperties.length() < UserConstants.UP_POS_REAL_NAME_REG) {
            return false;
        }
        return userProperties.substring(UserConstants.UP_POS_REAL_NAME_REG - 1,
                UserConstants.UP_POS_REAL_NAME_REG).equals("1");
    }

    /**
     * 给计费返回一个智能计费的属性（boolean）
     *
     * @param userProperties 用户属性
     * @return boolean true 开启 false 关闭
     */
    public static boolean intelligentFeeValid(String userProperties) {
        if (userProperties == null || userProperties.length() == 0) {
            return false;
        }
        if (userProperties.length() < UserConstants.UP_POS_INTELLIGENT_FEE) {
            return false;
        }
        return userProperties.substring(UserConstants.UP_POS_INTELLIGENT_FEE - 1, UserConstants.UP_POS_INTELLIGENT_FEE).equals("1");
    }

    /**
     * 替换属性值
     *
     * @param userProperties 用户属性值
     * @param position       当前位置
     * @param newValue       新的值
     * @return String 修改过的用户属性值
     */
    public static String replaceUserProperties(String userProperties, int position, String newValue) {
        userProperties = userProperties.substring(0, position - 1) + newValue + userProperties.substring(position, userProperties.length());
        return userProperties;
    }

    public static String createCardnum() {
        String result = "";
        for (int i = 1; i <= 10; i++) {// 前3个，随即数1-7
            result = result + Integer.toString((int) (Math.random() * 8));
        }
        DecimalFormat a = new DecimalFormat("000000000000");// 随机到非7位数时前面加0
        result = result + a.format((int) (Math.random() * 472000100));// 随机数0-4720000
        return result;
    }

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("1").append(str);//左补1
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    public static void main(String[] args) {

        //362330198908270735
//        StringBuffer stringBuffer = new StringBuffer();
//        String identityNo = "362202199002127621";
//        System.out.println("原来的长度" + identityNo + ":" + identityNo.length());
//        System.out.println(identityNo.substring(identityNo.length() - 6, identityNo.length()));
//        String suffix = identityNo.substring(identityNo.length() - 6, identityNo.length());
//        for (int i = 0; i < identityNo.length() - 6; i++) {
//            stringBuffer.append("0");
//        }
//        stringBuffer.append(suffix);
//        System.out.println("补零后的长度" + stringBuffer + ":" + stringBuffer.length());
//        System.out.println(stringBuffer);
    }

    /**
     * 判断版本号之间的大小
     *
     * @param ver1
     * @param ver2
     * @return
     */
    public static boolean compareVersion(String ver1, String ver2) {
        if (StringUtils.isEmpty(ver2)) {
            ver2 = "1.0.3";
        }

        String ver1FillZero = "";
        String[] ver1Array = ver1.split("\\.");
        int ver1FillZeroLength = (8 - ver1Array.length);
        for (int i = 0; i < ver1FillZeroLength; i++) {
            ver1FillZero += "0";
        }

        String ver2FillZero = "";
        int ver2FillZeroLength = (8 - ver2.split("\\.").length);
        for (int i = 0; i < ver2FillZeroLength; i++) {
            ver2FillZero += "0";
        }

        ver1 = ver1.replace(".", "") + ver1FillZero;
        ver2 = ver2.replace(".", "") + ver2FillZero;

        if (Long.valueOf(ver1) > Long.valueOf(ver2) || Long.valueOf(ver1).equals(Long.valueOf(ver2))) {
            return true;
        } else {
            return false;
        }
    }

    public static String ConvertFenToYuan(Integer Fen) {
        BigDecimal cashFen = new BigDecimal(Fen);
        BigDecimal divideNum = new BigDecimal("100");
        BigDecimal cashYuan = cashFen.divide(divideNum, 2, BigDecimal.ROUND_HALF_EVEN);

        return String.valueOf(cashYuan);
    }

    public static String replaceString(String targetStr, int position, String newValue) {
        return targetStr.substring(0, position - 1) + newValue + targetStr.substring(position, targetStr.length());
    }

    /**
     * @param uri
     * @param request
     * @param random
     * @param typeOfT
     * @param appId
     * @param appSecret
     * @return
     * @description
     * @company www.bwton.com
     * @author zhaokaikai@bwton.com
     * @date 2017-11-01 17:07
     */
    public static <T> T httpRequestQrcodePlatform(String uri, Object request, String random, Type typeOfT, String appId
            , String appSecret) throws Exception {
        PostMethod post = new PostMethod(uri);
        post.setRequestHeader("Content-Type", "application/json");
        post.setRequestHeader("Accept", "application/json");

        log.debug("httpRequestQrcodePlatform, 发送HTTP请求二维码, URI = " + uri);

        String message = gson.toJson(request);

        post.setRequestHeader("AppId", appId);
        post.setRequestHeader("Sequence", DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
        post.setRequestHeader("Version", "0100");
        if (random != null) {
            post.setRequestHeader("Random", RSAUtils.encryptByPublicKey(random, appSecret));
        }
        //签名
        post.setRequestHeader("Signature", RSAUtils.encryptByPublicKey(DigestUtils.md5Hex(message.getBytes("utf-8")), appSecret));
        //请求报文
        post.setRequestEntity(new StringRequestEntity(message, "application/json", "utf-8"));

        //以下设置http连接参数
        HttpClient http = new HttpClient();
        //连接超时时长3秒
        http.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        //请求超时时长5秒
        http.getHttpConnectionManager().getParams().setSoTimeout(5000);
        try {
            int resultCode = http.executeMethod(post);
            log.info(new String(post.getResponseBody(), "utf-8"));
            if (resultCode != 200) {
                log.debug("httpRequestQrcodePlatform, HTTP STATUS = {}", resultCode);
                return null;
            }
            String response = new String(post.getResponseBody(), "utf-8");
            //验证返回的签名
            log.debug("httpRequestQrcodePlatform, sequence=" + post.getResponseHeader("Sequence").getValue());
            String signature = post.getResponseHeader("Signature").getValue();
            log.debug("httpRequestQrcodePlatform, signature=" + signature);
            String decryptSignature = RSAUtils.decryptByPublicKey(signature, appSecret);
            log.debug("httpRequestQrcodePlatform, decryptSignature=" + decryptSignature);
            String md5HexResp = DigestUtils.md5Hex(response.getBytes("utf-8"));
            log.debug("httpRequestQrcodePlatform, md5HexResp=" + md5HexResp);
            if (!StringUtils.equalsIgnoreCase(decryptSignature, md5HexResp)) {
                throw new Exception("验签失败");
            }
            log.debug("httpRequestQrcodePlatform, SUCCESS!!");
            return gson.fromJson(response, typeOfT);
        } catch (Exception e) {
            log.error("httpRequestQrcodePlatform, error: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            //不要忘记释放，尽量通过该方法实现，
            if (post != null) {
                post.releaseConnection();
            }
        }
        return null;
    }
}
