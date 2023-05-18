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
@TableName("blog")
public class Blog {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private String author;

    private String authorUrl;

    private Long viewCount;

    private Long likeCount;

    private String picUrl;

    private Date createTime;

    private Date modifyTime;

}
