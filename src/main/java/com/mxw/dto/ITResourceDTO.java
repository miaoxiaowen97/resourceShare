package com.mxw.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author miao
 */
@Data
public class ITResourceDTO {

    private Integer id;

    private String title;

    private String content;

    private String author;

    private Long viewCount;

    private Long likeCount;

    private String resourceUrl;

    private String resourcePassword;

    private String createTime;

    private String modifyTime;
}
