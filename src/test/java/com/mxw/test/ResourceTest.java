package com.mxw.test;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mxw.ResourceShareApplication;
import com.mxw.dto.PageInfo;
import com.mxw.dto.ResourceItemDTO;
import com.mxw.entity.Resource;
import com.mxw.mapper.ResourceMapper;
import com.mxw.service.ResourceService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ResourceShareApplication.class})
public class ResourceTest {

    @Autowired
    private ResourceService resourceService;

    @Test
    public void testGetOne() {
        resourceService.getOne();
    }

    @Test
    public void testSaveOne(){
        Resource resource =new Resource();
        resource.setTitle("老照片修复 图片无损放大工具-SwinIR");
        resource.setAuthor("牛奶喵");
        String content="SwinIR是一个使用 Swin Transformer 进行图像恢复的存储库，这个存储库是 SwinIR 的官方 PyTorch 实现：使用移位窗口变换器的图像恢复（arxiv、supp、预训练模型、视觉结果）。SwinIR在以下方面实现了最先进的性能，双三次/轻量级/真实世界图像 SR、灰度/彩色图像去噪、灰度/彩色 JPEG 压缩伪影减少，而有个网站平台使用Nvidia A100 (40GB) GPU 硬件上运行这个模型存储库，可以进行老照片修复和当作图片无损放大工具，恢复修复处理照片预测通常会在 14 秒内完成，直接上传照片即可。";
        resource.setContent(content);

        resourceService.saveOne(resource);
    }

    @Test
    public void testModifyOne(){
        Resource resource =new Resource();
        resource.setId(3);
        resource.setTitle("一个强强的在线视频学习网站-WEBP");

        resource.setAuthor("牛奶喵");
        String content="WEBP是一个强强的在线视频学习网站，网站界面UI非常干净整洁，美元丝毫多余的设计，打开网站就能在线学习，而且网站的学习视频非常丰富，涉及到日本课程和一些国内的一些列在线课程，感兴趣的19岁同学可以到网站学习。";
        resource.setContent(content);

        resourceService.modifyOne(resource);
    }

    @Test
    public void testList(){
        // 爬取封面

    }
    @Autowired
    private ResourceMapper resourceMapper;

    @Test
    public  void index() throws Exception {
        for (int j = 28; j<30 ; j++) {
            String url="https://www.ahhhhfs.com/page/"+j+"/";
            System.out.println(url);
            HashMap<String, String> headers = new HashMap<>();
            headers.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.54");
            Document doc = Jsoup.connect(url).headers(headers).get();
            // 获取带有 href 属性的 a 元素
            Elements elementsByClass = doc.getElementsByClass("module posts-wrapper list");
            Element element = elementsByClass.get(0);
            Node node = element.childNode(1);
            List<Node> nodes = node.childNodes();
            List<String> urlList = new ArrayList<>();

            // 标题-封面集合
            Map<String, String> coverUrlMap = new HashMap<>();
            // 标题-地址集合
            Map<String, String> urlMap = new HashMap<>();

            for (Node node1 : nodes) {
                int i = node1.childNodeSize();
                if (i > 0) {
                    Node node2 = node1.childNode(1);
                    List<Node> nodes1 = node2.childNodes();
                    Node node3 = node2.childNode(1);
                    Node node4 = node3.childNode(0);
                    Node node5 = node4.childNode(0);
                    Attributes attributes = node5.attributes();
                    String href = attributes.get("href");
                    urlList.add(href);
                    String title = attributes.get("title");
                    Node node6 = node5.childNode(0);
                    Attributes attributes1 = node6.attributes();
                    String src = attributes1.get("data-src");
                    coverUrlMap.put(title, src);
                    urlMap.put(href,title);
                }
            }
            BatchDownloadFileTest test=new BatchDownloadFileTest();
            for (String s : urlList) {
                String title = urlMap.get(s);
                Resource resource = new Resource();
                resource.setTitle(title);
                LambdaQueryWrapper<Resource> resourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
                resourceLambdaQueryWrapper.eq(Resource::getTitle,resource.getTitle());
                Resource resource1 = resourceMapper.selectOne(resourceLambdaQueryWrapper);
                if (Objects.nonNull(resource1)){
                    System.out.println("已存在直接跳过");
                    continue;
                }
                String coverUrl = coverUrlMap.getOrDefault(title, "");
                resource.setAuthor("牛奶喵");
                // 详情
                info(s,resource,title,test);
                System.out.println(title+"==>保存成功");

                // 文件命名
                String[] split = coverUrl.split("/");
                String fileName = split[split.length - 1];
                fileName = fileName.replace(" ", "");
                resource.setCoverUrl(fileName);
                resource.setViewCount(1L);
                resource.setLikeCount((long) (Math.random()*100));
                resourceMapper.insert(resource);
                System.out.println("开始下载封面图=》"+fileName);
                test.httpsToGet(coverUrl,fileName);
                System.out.println("下载成功");
            }
        }
    }

    public void  info(String url,Resource resource,String title,BatchDownloadFileTest test) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.54");

        Document infoDoc = Jsoup.connect(url).headers(headers).get();
        Elements infoDocElementsByClass = infoDoc.getElementsByClass("entry-content u-text-format u-clearfix");
        Element element = infoDocElementsByClass.get(0);
        StringBuilder builder = new StringBuilder();
        Elements p = infoDocElementsByClass.first().getElementsByTag("p");
        Elements a = infoDocElementsByClass.first().getElementsByTag("a");


        Set<String> picUrlList = new HashSet<>();
        Set<String> reUrlList = new HashSet<>();

        for (Element element1 : a) {
            String href = element1.attr("href");
            if (href.endsWith(".jpg")) {
                picUrlList.add(href);
            }
            if (!href.contains("www.ahhhhfs.com")&&href.contains("http")){
                reUrlList.add(href);
            }
        }

        for (Element element1 : p) {
            String text = element1.text();
            if (StringUtils.isNotBlank(text)&&!text.contains("http")){
                if (text.contains("A姐")){
                    text = text.replace("A姐", "小编");
                }
                builder.append(text);
            }
            if (text.contains("http")&&!text.contains("www.ahhhhfs.com")){
                try{
                    String s = text.split("：")[1];
                    reUrlList.add(s);
                }catch (Exception e){
                    System.out.println(e);
                    reUrlList.add("出错了");
                }

            }
        }

        // 下载资源图
        Set<String> picNameList = new HashSet<>();
        int i=0;
        for (String pic : picUrlList) {

            String fileName = title+i+".jpg";
            fileName = fileName.replace(" ", "");
            System.out.println("开始下载资源图=》"+fileName);
            picNameList.add(fileName);
            test.httpsToGet(pic,fileName);
            i=i+1;
        }

        resource.setResourceUrl(JSON.toJSONString(reUrlList));
        resource.setContent(builder.toString());
        resource.setPicUrl(JSON.toJSONString(picNameList));
    }

    public static void main(String[] args) throws Exception {
        BatchDownloadFileTest test =new BatchDownloadFileTest();
        // 下载资源图
        Set<String> picNameList = new HashSet<>();
        String s="[\"https://www.ahhhhfs.com/wp-content/uploads/2023/05/%E5%9C%A8%E7%BA%BF%E7%9F%AD%E4%BF%A1%E5%8E%8B%E5%8A%9B%E6%B5%8B%E8%AF%95-%E5%A4%9A%E6%8E%A5%E5%8F%A3-dxhz-%E4%B8%BB%E9%A1%B5%E8%8F%9C%E5%8D%95.jpg\",\"https://www.ahhhhfs.com/wp-content/uploads/2023/05/%E5%9C%A8%E7%BA%BF%E7%9F%AD%E4%BF%A1%E5%8E%8B%E5%8A%9B%E6%B5%8B%E8%AF%95-%E5%A4%9A%E6%8E%A5%E5%8F%A3-dxhz-%E5%90%8E%E5%8F%B0.jpg\",\"https://www.ahhhhfs.com/wp-content/uploads/2023/05/%E5%9C%A8%E7%BA%BF%E7%9F%AD%E4%BF%A1%E5%8E%8B%E5%8A%9B%E6%B5%8B%E8%AF%95-%E5%A4%9A%E6%8E%A5%E5%8F%A3-dxhz-%E6%95%88%E6%9E%9C.jpg\"]";
        List<String> picUrlList = JSON.parseArray(s, String.class);
        String title="miaoxiaowen";
        int i=0;
        for (String pic : picUrlList) {
            String end = pic.substring(pic.length() - 4, pic.length());
            String fileName = title+i+end;
            System.out.println("开始下载资源图=》"+fileName);
            picNameList.add(fileName);
            test.httpsToGet(pic,fileName);
            i=i+1;
        }
    }
}
