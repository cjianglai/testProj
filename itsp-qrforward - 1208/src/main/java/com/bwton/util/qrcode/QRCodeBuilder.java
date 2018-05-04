package com.bwton.util.qrcode;

//import com.bwton.support.PropertiesInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author:jhh
 * Date:17/8/13 下午9:32
 */
public class QRCodeBuilder {
    private static Logger logger = LoggerFactory.getLogger(QRCodeBuilder.class);
    /**
     * 授权成功
     */
    public static final String Credit_Success = "0000";
    /**
     * 授权失败
     */
    public static final String Credit_Fail = "9999";
    /**
     * CRC校验失败
     */
    public static final String CRC_Fail = "1001";
    /**
     * 帐户号错误
     */
    public static final String Account_Err = "2001";
    /**
     * 身份识别码错误
     */
    public static final String Identification_Err = "2002";
    /**
     * 网络通讯错误
     */
    public static final String NetWork_Err = "2003";

    /*
     * 未知错误
     */
    public static final String Unknown_Err = "2004";

    /**
     * 日期格式化到YYYYMMDD，熊猫要求格式
     */
    private static SimpleDateFormat sFormats = new SimpleDateFormat("yyyyMMdd");
    /**
     * ACC授权服务器地址 这是测试服务器地址
     */
    //private final static String Host = "10.5.95.5";
    /**
     * ACC授权服务器端口 熊猫说还未指定端口（貌似没开发完？）
     */
    //private final static int Port = 7000;
    /**
     * 网络通讯延迟，单位(ms)
     */
    private final static int TimeOut = 200;
    /**
     * 日期格式化到yyyyMMddHHmmss，熊猫要求格式
     */
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 初始化
     */
    static {

    }

    /**
     * 获取CRC校验值(算法由南京熊猫提供) PS:我怎么看都是CRC16的算法
     *
     * @param buf
     * @param len
     * @return
     */
    private static int CRCCheck(byte[] buf, int len) {
        int crc = 0;
        int tmp;
        for (int i = 0; i < len; i++) {
            tmp = buf[i] & 0xff;
            crc = crc ^ tmp << 8;
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x8000) > 0)
                    crc = (crc << 1) ^ 0x1021;
                else
                    crc = crc << 1;
            }
        }
        return ((crc & 0x00ff) << 8) | ((crc & 0xff00) >> 8);
    }

    /**
     * 内存复制
     *
     * @param dst    目标
     * @param src    源
     * @param srcoff 源偏移
     * @param len    复制长度
     * @throws ArrayIndexOutOfBoundsException 溢出
     */
    private static void memcpy(byte[] dst, byte[] src, int srcoff, int len) {
        while (len-- > 0)
            dst[len] = src[len + srcoff];
    }

    /**
     * 内存复制
     *
     * @param dst 目标
     * @param src 源
     * @param len 复制长度
     * @throws ArrayIndexOutOfBoundsException 溢出
     */
    private static void memcpy(byte[] dst, byte[] src, int len) {
        while (len-- > 0)
            dst[len] = src[len];
    }

    /**
     * 内存复制
     *
     * @param dst    目标
     * @param dstoff 目标偏移
     * @param src    源
     * @param len    复制长度
     * @throws ArrayIndexOutOfBoundsException 溢出
     */
    private static void memcpy(byte[] dst, int dstoff, byte[] src, int len) {
        while (len-- > 0)
            dst[len + dstoff] = src[len];
    }

    /**
     * 内存复制
     *
     * @param dst    目标
     * @param dstoff 目标偏移
     * @param src    源
     * @param srcoff 源偏移
     * @param len    复制长度
     * @throws ArrayIndexOutOfBoundsException 溢出
     */
    private static void memcpy(byte[] dst, int dstoff, byte[] src, int srcoff, int len) {
        while (len-- > 0)
            dst[len + dstoff] = src[len + srcoff];
    }

    /**
     * 内存填值
     *
     * @param dst   目标
     * @param value 值
     * @param len   长度
     * @throws ArrayIndexOutOfBoundsException 溢出
     */
    private static void memset(byte[] dst, byte value, int len) {
        while (len-- > 0)
            dst[len] = value;
    }

    /**
     * 转换用常量
     */
    private static final byte[] code = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F'};

    /**
     * 转换用常量
     */
    private static final byte[] codeL = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    /**
     * 获取二维码基础数据
     *
     * @param userId 账户号，长度必须为16
     * @param idNo   身份证号
     * @return 二维码的处理结果 当返回null时代表参数错误
     */
    public static Object[] connectACCGetKey(String userId, String idNo, Date authDate) {
        long startMills = System.currentTimeMillis();
        logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", idNo = " + idNo + ", authDate = "
                + sFormats.format(authDate));
        byte[] sbuf;
        sbuf = new byte[44];
        Object[] datas = new Object[3];
        memset(sbuf, (byte) '0', 44);
        byte[] tmp;
        try {
            tmp = userId.substring(0, 16).getBytes("UTF-8");
            logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", tmp = " + new String(tmp));
            if (tmp.length != 16) {
                datas[0] = null;
                logger.error("connectACCGetKey, userId = " + Long.valueOf(userId) + ", 账号位数错误");
                return datas;
            }
        } catch (UnsupportedEncodingException e1) {
            datas[0] = Account_Err;
            logger.error("connectACCGetKey, userId = " + Long.valueOf(userId) + ", 账号错误");
            return datas;
        }
        memcpy(sbuf, 0, tmp, tmp.length);
        logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", sbuf = " + new String(sbuf));
        try {
            tmp = idNo.substring(0, 16).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            datas[0] = Account_Err;
            logger.error("connectACCGetKey, userId = " + Long.valueOf(userId) + ", 账号错误");
            return datas;
        }
        memcpy(sbuf, 16 + (16 - tmp.length), tmp, tmp.length);
        logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", sbuf = " + new String(sbuf));
        tmp = sFormats.format(authDate).getBytes();
        memcpy(sbuf, 32, tmp, 8);
        logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", sbuf = " + new String(sbuf));
        int crc = CRCCheck(sbuf, 40);
        tmp = Integer.toHexString(crc).toUpperCase().getBytes();
        memcpy(sbuf, 40 + (4 - tmp.length), tmp, tmp.length);
        logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", sbuf = " + new String(sbuf));
        byte[] rbuf = new byte[106];
        try {
            logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", 准备连接ACC Socket服务");
            Socket socket = new Socket("127.0.0.1", 8082);
//            public static String ACC_SERVER_IP; //ACC密钥接口服务器IP
//            public static int ACC_SERVER_PORT; //ACC密钥接口服务器端口
            socket.setSoTimeout(TimeOut);
            logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", 向ACC发送的内容: " + new String(sbuf));
            socket.getOutputStream().write(sbuf);
            int ret = 0;
            while (ret < 106)
                ret += socket.getInputStream().read(rbuf, ret, 106 - ret);
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("connectACCGetKey, userId = " + Long.valueOf(userId) + ", Socket请求出错：" + e.getMessage().toString());
            datas[0] = NetWork_Err;
            return datas;
        }
        // ------------------------------数据处理------------------------------
        String tmpResult = new String(rbuf, 0, 106);
        logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", Socket返回结果: " + tmpResult);
        String rt;
        rt = new String(rbuf, 0, 4);
        if (!rt.equals(Credit_Success)) {
            datas[0] = Unknown_Err;
            logger.error("connectACCGetKey, userId = " + Long.valueOf(userId) + ", 未知错误2");
            return datas;
        }

        // ---------------------CRC验证，如果性能不足可以去除，因为TCP链路层本身有数据校验-----------------------
        /* 先屏蔽CRC检验，熊猫接口返回的数据有问题，会导致这里报错。YangWY 2017-07-28
        String s = new String(rbuf, 102, 4);
		crc = Integer.parseInt(s, 16);
		if (crc != CRCCheck(rbuf, 102)) {
			datas[0] = CRC_Fail;
			logger.error("connectACCGetKey, userId = " + userId + ", CRC校验错误");
			return datas;
		}*/
        byte[] data = new byte[98];
        // 数据转换
        //memset(data, (byte) '0', 98);
        //memcpy(data, sbuf, 16, 16);
        //memcpy(data, 16, rbuf, 20, 82);
        memcpy(data, rbuf, 4, 98);
        logger.info("connectACCGetKey, userId = " + Long.valueOf(userId) + ", data = " + new String(data));
        //memcpy(data, 16, rbuf, 20, 82);
        //String day = sFormats.format(now);
        datas[0] = Credit_Success;
        datas[1] = data;
        //datas[2] = day;
        logger.info(
                "connectACCGetKey, userId = " + Long.valueOf(userId) + ", 处理完成，耗时 = " + (System.currentTimeMillis() - startMills) + "毫秒");
        return datas;
    }

    /**
     * 组装二维码
     * 账户号	Char(16)	APP后台  *
     * 身份识别码	Char(16)	APP后台  *
     * 授权日期	Char(8)	APP后台/ACC校验  *
     * 预留1	Char(12)	预留（全‘0’）
     * KID1	Char(1)	ACC(1-8)
     * KID2	Char(1)	ACC(1-8)
     * 随机数	Char(16)	ACC
     * 加密值1	Char(32)	ACC
     * 当前时间	Char(14)	APP后台  *
     * 计费方式	Char(1)	终端选择  *
     * 授权类型	Char(1)	预留（填’0’）  (0:无限制，1:仅进站，2:仅出站)
     * 有效时间阈值	Char(4)	单位秒  *
     * 所处站点ID	Char (4)	App后台  *
     * 随机数	Char (6)
     * 加密值2	Char(32)	APP计算
     * 二维码序列号	Char (16)	App后台  *
     */
    public static String BuildQRCode(String userId, String idNo, Byte payMode, Byte authMode, Date authDate,
                                     Integer effectiveTime, String stationNo, Long qrSerialNo, byte[] accKey) throws Exception {
        //Byte payMode = 1; //计费方式 默认金额
        //Byte authMode = 0; //授权类型 (0:无限制，1:仅进站，2:仅出站)
        logger.debug("BuildQRCode, userId = " + userId);
        logger.debug("BuildQRCode, idNo = " + idNo);
        logger.debug("BuildQRCode, authDate = " + authDate);
        logger.debug("BuildQRCode, payMode = " + payMode);
        logger.debug("BuildQRCode, authMode = " + authMode);
        logger.debug("BuildQRCode, effectiveTime = " + effectiveTime);
        logger.debug("BuildQRCode, stationNo = " + stationNo);
        logger.debug("BuildQRCode, qrSerialNo = " + qrSerialNo);

        byte[] buf = new byte[180];
        byte[] bufIdNo = idNo.getBytes("UTF-8");
        memset(buf, (byte) '0', 180);// 初始化

        //将key改为userId modify by zhaokaikai@bwton.com
        memcpy(buf, userId.toString().getBytes("UTF-8"), 16);// 账户号 16位
        //memcpy(buf, accKey, 16);// 账户号 16位

        //byte[] tmp;//中间变量
        //tmp = userId.getBytes("UTF-8");//转换userId到byte[]
        //memcpy(buf, 0, tmp, tmp.length);// 账户号 16位

        memcpy(buf, 16, bufIdNo, 16);// 身份识别码 16位
        memcpy(buf, 32, sFormats.format(authDate).getBytes("UTF-8"), 0, 8);// 授权日期 8位
        memcpy(buf, 52, accKey, 16, 50);// ACC加密域 52位
        //String currTime = sFormat.format(new Date());
        String currTime = getQRCodeCurrTime();
        memcpy(buf, 102, currTime.getBytes("UTF-8"), 0, 14); //当前时间 14位

        memcpy(buf, 116, payMode.toString().getBytes("UTF-8"), 0, 1); //计费方式 1位
        memcpy(buf, 117, authMode.toString().getBytes("UTF-8"), 0, 1); //授权类型 1位

        byte[] bEffectiveTime = Integer.toHexString(effectiveTime).getBytes("UTF-8");
        memcpy(buf, 118 + (4 - bEffectiveTime.length), bEffectiveTime, 0, bEffectiveTime.length); //有效时间阈值 4位

        byte[] bStationNo = stationNo.toString().getBytes("UTF-8");
        memcpy(buf, 122 + (4 - bStationNo.length), bStationNo, 0, bStationNo.length); //所处站点ID 4位

        //Integer rnd = (int) (Math.random() * 999999);
        Integer rnd = 999999;
        byte[] bRnd = rnd.toString().getBytes("UTF-8");
        memcpy(buf, 126 + (6 - bRnd.length), bRnd, 0, bRnd.length); //随机数 6位

        //App加密处理
        byte[] newAccKey = new byte[24];
        for (int i = 0, j = 66; i < 16; i++) {
            newAccKey[i] = (byte) Integer.parseInt(new String(accKey, j, 2), 16);
            j += 2;
        }
        memcpy(newAccKey, 16, newAccKey, 0, 8);
        String sTmp = currTime + "0" + payMode.toString() + "0" + authMode.toString();
        sTmp = sTmp + new String(buf, 118, 14);
        byte[] bTmp = sTmp.getBytes("UTF-8");
        byte[] src = new byte[16];
        for (int i = 0, j = 0; i < 16; i++) {
            src[i] = (byte) Integer.parseInt(new String(bTmp, j, 2), 16);
            j += 2;
        }
        bTmp = encrypt3DES(newAccKey, src);
        for (int i = 0, j = 132; i < 16; i++) { //加密值2 32位
            buf[j++] = code[(bTmp[i] >>> 4) & 0x0F];
            buf[j++] = code[bTmp[i] & 0x0F];
        }
        //App加密处理结束

        byte[] bQRSerialNo = qrSerialNo.toString().getBytes("UTF-8");
        memcpy(buf, 164 + (16 - bQRSerialNo.length), bQRSerialNo, 0, bQRSerialNo.length); //二维码序列号 16位
        return new String(buf, "UTF-8");
    }

    /**
     * 执行3DES加密
     *
     * @param keybyte 内容
     * @param src     密钥
     * @return 加密结果
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static byte[] encrypt3DES(byte[] keybyte, byte[] src) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 生成密钥
        SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
        // 加密
        Cipher c1 = Cipher.getInstance("DESede/ECB/NoPadding");
        c1.init(Cipher.ENCRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    //根据当前时间和偏移量得到二维码串中的“当前时间”
    private static String getQRCodeCurrTime() {
        Date currTime = new Date();
        logger.debug("currTime = " + sFormat.format(currTime));
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(currTime);
        rightNow.add(Calendar.SECOND, -30);
//        public static int QRCODE_CURRTIME_OFFSET = -30; //二维码当前时间偏移值（秒）
        Date newTime = rightNow.getTime();
        logger.debug("currTime offset= " + sFormat.format(newTime));
        return sFormat.format(newTime);
    }
}
