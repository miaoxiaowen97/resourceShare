package com.mxw.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResourceItemDTO {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private String author;

    private List<String> picUrlList;

    private List<String> resourceUrlList;

    private String coverUrl;

    private Long viewCount;

    private Long likeCount;

    private String createTime;

    private String modifyTime;
}
