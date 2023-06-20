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
@TableName("it_resource")
public class ITResource {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private Integer type;

    private String author;

    private Long viewCount;

    private Long likeCount;

    private String resourceUrl;

    private String resourcePassword;

    private Date createTime;

    private Date modifyTime;
}
