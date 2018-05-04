package com.bwton.data.domain.qrcode;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QrcodeKey implements Serializable {
    private Long key_id;

    private Date auth_date;

    private Long user_id;

    private byte[] acc_key;

}