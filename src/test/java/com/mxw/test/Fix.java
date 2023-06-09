//package com.mxw.test;
//
//import com.alibaba.fastjson.JSON;
//import com.mxw.ResourceShareApplication;
//import com.mxw.entity.Resource;
//import com.mxw.mapper.ResourceMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {ResourceShareApplication.class})
//public class Fix {
//
//    @Autowired
//    private ResourceMapper resourceMapper;
//
//    @Test
//    public void test(){
//
//        for (int i = 1000; i <=1400 ; i++) {
//            Resource resource = resourceMapper.selectById(i);
//            if (resource == null) {
//                continue;
//            }
//            String resourceUrl = resource.getResourceUrl();
//            List<String> resourceUrlList = JSON.parseArray(resourceUrl, String.class);
//
//            if (resourceUrlList.isEmpty()||resourceUrlList.size()==0){
//                System.out.println(resource.getTitle()+"资源链接为空，进行删除"+resource.getId());
//                resourceMapper.deleteById(i);
//                System.out.println("删除成功");
//            }
//        }
//    }
//
//    @Test
//    public void deleteTitleIllegal(){
//
//        for (int i = 1000; i <=1400 ; i++) {
//            Resource resource = resourceMapper.selectById(i);
//            if (resource == null) {
//                continue;
//            }
//            if (resource.getTitle().contains("大人")){
//                System.out.println(resource.getTitle()+"不合法，进行删除"+resource.getId());
//                resourceMapper.deleteById(i);
//                System.out.println("删除成功");
//            }
//            if (resource.getTitle().contains("奶子")){
//                System.out.println(resource.getTitle()+"不合法，进行删除"+resource.getId());
//                resourceMapper.deleteById(i);
//                System.out.println("删除成功");
//            }
//            String content = resource.getContent();
//            if (content.contains("19岁")){
//                System.out.println(resource.getTitle()+"内容不合法，进行删除"+resource.getId());
//                resourceMapper.deleteById(i);
//            }
//        }
//    }
//
//    @Test
//    public void updateRefId(){
//
//        for (int i = 1; i <=1000 ; i++) {
//            Resource resource = resourceMapper.selectById(i);
//            if (resource == null) {
//                continue;
//            }
//            resource.setRefId(Long.valueOf(i+""));
//            resourceMapper.updateById(resource);
//            System.out.println("更新成功==》"+i);
//        }
//    }
//}
