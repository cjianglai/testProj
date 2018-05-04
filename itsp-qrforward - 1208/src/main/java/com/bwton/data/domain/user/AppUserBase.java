package com.bwton.data.domain.user;

import com.bwton.constant.UserConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create by dingyufang 2017/06/26
 */
@Data
public class AppUserBase implements Serializable {
    private Long user_id;

    private String mobile_phone;

    private Integer user_status;

    private Date reg_date;

    private String user_name;

    private Integer sex;

    private Date birthday;

    private Integer id_type;

    private String id_no;

    private Integer term_type;

    private String term_token;

    private String push_token;

    private String password;

    private Integer city_id;

    private String nickname;

    private String profession;

    private Date last_token_time;

    private String user_properties;

    private String user_image_path;

    private String remark;

    private Integer real_name_reg;//登记

    private Integer real_name_auth;//认证

    private Integer real_name_chk;//实名

    private Integer real_name_open;//开户

    private String city_name;

    public Integer getReal_name_reg() {
        return realNameRegValid();
    }

    public Integer getReal_name_auth() {
        return realNameAuthValid();
    }

    public Integer getReal_name_open() {
        return realNameOpenValid();
    }

    public Integer getReal_name_chk() {
        return realNameChkValid();
    }

    public Long getUserId() {
        return user_id;
    }

    public String getMobilePhone() {
        return mobile_phone;
    }


    /**
     * 判断用户是否正常可用（状态为正常）
     *
     * @return
     */
    public boolean availableUser() {
        return user_status == UserConstants.EnumUserStatus.NORMAL.ordinal();
    }

    //是否进行了登记
    public Integer realNameRegValid() {
        if (user_properties == null || user_properties.length() == 0)
            return 0;
        if (user_properties.length() < UserConstants.UP_POS_REAL_NAME_REG)
            return 0;
        return Integer.parseInt(user_properties.substring(UserConstants.UP_POS_REAL_NAME_REG - 1, UserConstants.UP_POS_REAL_NAME_REG));
    }

    //是否进行了认证
    public Integer realNameAuthValid() {
        if (user_properties == null || user_properties.length() == 0)
            return 0;
        if (user_properties.length() < UserConstants.UP_POS_REAL_NAME_AUTH)
            return 0;
        return Integer.parseInt(user_properties.substring(UserConstants.UP_POS_REAL_NAME_AUTH - 1, UserConstants.UP_POS_REAL_NAME_AUTH));
    }

    //是否进行了实名
    public Integer realNameChkValid() {
        if (user_properties == null || user_properties.length() == 0)
            return 0;
        if (user_properties.length() < UserConstants.UP_POS_REAL_NAME_CHK)
            return 0;
        return Integer.parseInt(user_properties.substring(UserConstants.UP_POS_REAL_NAME_CHK - 1, UserConstants.UP_POS_REAL_NAME_CHK));
    }

    //是否进行了开户
    public Integer realNameOpenValid() {
        if (user_properties == null || user_properties.length() == 0)
            return 0;
        if (user_properties.length() < UserConstants.UP_POS_REAL_NAME_OPEN)
            return 0;
        return Integer.parseInt(user_properties.substring(UserConstants.UP_POS_REAL_NAME_OPEN - 1, UserConstants.UP_POS_REAL_NAME_OPEN));
    }

    //是否智能扣费
    public boolean getIntelligentFee() {
        return intelligentFeeValid();
    }

    public boolean intelligentFeeValid() {
        if (user_properties == null || user_properties.length() == 0)
            return false;
        if (user_properties.length() < UserConstants.UP_POS_INTELLIGENT_FEE)
            return false;
        return user_properties.substring(UserConstants.UP_POS_INTELLIGENT_FEE - 1, UserConstants.UP_POS_INTELLIGENT_FEE).equals("1");
    }

    //替换属性值
    public void replaceuser_properties(int position, String newValue) {
        user_properties = user_properties.substring(0, position - 1) + newValue + user_properties.substring(position, user_properties.length());
    }

    //对身份证号码做屏蔽
    private String getMaskid_no() {
        if (id_no == null || id_no.length() == 0)
            return id_no;
        if (id_no.length() == 15)
            return id_no.substring(0, 3) + "*********" + id_no.substring(12);
        else if (id_no.length() == 18)
            return id_no.substring(0, 3) + "************" + id_no.substring(15);
        else
            return id_no;
    }


    /**
     * 判断用户是否在线用户（非注销状态）
     *
     * @return
     */
    public boolean onlineUser() {
        return user_status != UserConstants.EnumUserStatus.UNREG.ordinal();
    }

//    //分享ID 用户信息输出时增加shareId参数，兼容客户端的需求。下个版本可以删除。
//    public String getShareId() {
//        return UserIdWrapperUtil.encryptUserId(userId);
//    }
}