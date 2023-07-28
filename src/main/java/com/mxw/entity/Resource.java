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
@TableName("resource")
public class Resource {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private String author;

    private Long viewCount;

    private Long likeCount;

    private Integer refId;

    private String picUrl;

    private String resourceUrl;

    private String coverUrl;

    private Date createTime;

    private Date modifyTime;
}
