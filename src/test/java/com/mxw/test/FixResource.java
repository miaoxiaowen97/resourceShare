package com.mxw.test;

import com.alibaba.fastjson.JSON;
import com.mxw.ResourceShareApplication;
import com.mxw.entity.Resource;
import com.mxw.mapper.ResourceMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ResourceShareApplication.class})
public class FixResource {

    @Autowired
    private ResourceMapper resourceMapper;

    @Test
    public void test(){

        for (int i = 999; i <=1400 ; i++) {
            Resource resource = resourceMapper.selectById(i);
            if (resource == null) {
                continue;
            }
            if (resource.getTitle().contains("大人")){
                System.out.println(resource.getTitle()+"不合法，进行删除"+resource.getId());
                resourceMapper.deleteById(i);
                System.out.println("删除成功");
            }
            if (resource.getTitle().contains("奶子")){
                System.out.println(resource.getTitle()+"不合法，进行删除"+resource.getId());
                resourceMapper.deleteById(i);
                System.out.println("删除成功");
            }
            String content = resource.getContent();
            if (content.contains("19岁")){
                System.out.println(resource.getTitle()+"内容不合法，进行删除"+resource.getId());
                resourceMapper.deleteById(i);
            }

            String resourceUrl = resource.getResourceUrl();
            List<String> resourceUrlList = JSON.parseArray(resourceUrl, String.class);

            if (resourceUrlList.isEmpty()||resourceUrlList.size()==0){
                System.out.println(resource.getTitle()+"资源链接为空，进行删除"+resource.getId());
                resourceMapper.deleteById(i);
                System.out.println("删除成功");
            }

            // 访问主键,防止以后某些数据缺失访问不到
            Integer id = resource.getId();
            resource.setRefId(id);
            resourceMapper.updateById(resource);
            System.out.println("保存成功"+i);
        }

    }

}
