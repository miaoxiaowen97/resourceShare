package com.mxw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author miao
 */
@Data
@TableName("user_info")
public class UserInfo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private  Long userId;

    private  String userName;

    private  String password;

    private Date createTime;

    private Date modifyTime;

}
