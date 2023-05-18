package com.mxw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.entity.Blog;
import com.mxw.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author miao
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog>{
}
