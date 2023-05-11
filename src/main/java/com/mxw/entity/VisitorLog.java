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
@TableName("visitor_log")
public class VisitorLog {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String ip;

    private Date createTime;

}
