package com.mxw.dto;

import lombok.Data;

/**
 * @author miao
 */
@Data
public class PageInfo {

    /**
     * 每页数固定
     */
    private Long pageSize;

    /**
     * 当前页面
     */
    private Long pageIndex;

    /**
     * 总页数
     */
    private Long pageCount;

    /**
     * 总页数
     */
    private Integer type;
}
