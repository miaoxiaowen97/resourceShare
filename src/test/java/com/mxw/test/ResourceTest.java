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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketAddress;
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
    public void testSaveOne() {
        Resource resource = new Resource();
        resource.setTitle("老照片修复 图片无损放大工具-SwinIR");
        resource.setAuthor("牛奶喵");
        String content = "SwinIR是一个使用 Swin Transformer 进行图像恢复的存储库，这个存储库是 SwinIR 的官方 PyTorch 实现：使用移位窗口变换器的图像恢复（arxiv、supp、预训练模型、视觉结果）。SwinIR在以下方面实现了最先进的性能，双三次/轻量级/真实世界图像 SR、灰度/彩色图像去噪、灰度/彩色 JPEG 压缩伪影减少，而有个网站平台使用Nvidia A100 (40GB) GPU 硬件上运行这个模型存储库，可以进行老照片修复和当作图片无损放大工具，恢复修复处理照片预测通常会在 14 秒内完成，直接上传照片即可。";
        resource.setContent(content);

        resourceService.saveOne(resource);
    }

    @Test
    public void testModifyOne() {
        Resource resource = new Resource();
        resource.setId(3);
        resource.setTitle("一个强强的在线视频学习网站-WEBP");

        resource.setAuthor("牛奶喵");
        String content = "WEBP是一个强强的在线视频学习网站，网站界面UI非常干净整洁，美元丝毫多余的设计，打开网站就能在线学习，而且网站的学习视频非常丰富，涉及到日本课程和一些国内的一些列在线课程，感兴趣的19岁同学可以到网站学习。";
        resource.setContent(content);

        resourceService.modifyOne(resource);
    }

    @Test
    public void testList() {
        // 爬取封面

    }

    @Autowired
    private ResourceMapper resourceMapper;

    @Test
    public void index2() throws Exception {
        String[] ua = {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36 OPR/37.0.2178.32",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0)",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.277.400 QQBrowser/9.4.7658.400",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 UBrowser/5.6.12150.8 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7",
                "Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/60.0"};
        Random r = new Random();
        for (int j = 49; j > 0; ) {
            String url = "https://www.ahhhhfs.com/page/" + j + "/";
            System.out.println(url);
            // 获取带有 href 属性的 a 元素
            int indexInt = r.nextInt(14);
            try {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("authority", "www.ahhhhfs.com");
                headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                headers.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
                headers.put("cache-control", "max-age=0");
                headers.put("if-modified-since", "Wed, 24 May 2023 01:57:11 GMT");
                headers.put("sec-ch-ua", "\"Microsoft Edge\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"");
                headers.put("sec-ch-ua-mobile", "?0");
                headers.put("sec-ch-ua-platform", "\"Windows\"");
                headers.put("sec-fetch-dest", "document");
                headers.put("sec-fetch-mode", "navigate");
                headers.put("sec-fetch-site", "none");
                headers.put("sec-fetch-user", "?1");
                headers.put("upgrade-insecure-requests", "1");
                headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.54");

                Document doc = Jsoup.connect(url)
                        .timeout(5000)
                        .ignoreContentType(true)
                        .userAgent(ua[indexInt])
                        .headers(headers).get();
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
                        urlMap.put(href, title);
                    }
                }
                BatchDownloadFileTest test = new BatchDownloadFileTest();
                for (String s : urlList) {
                    String title = urlMap.get(s);
                    Resource resource = new Resource();
                    resource.setTitle(title);
                    LambdaQueryWrapper<Resource> resourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    resourceLambdaQueryWrapper.eq(Resource::getTitle, resource.getTitle());
                    Resource resource1 = resourceMapper.selectOne(resourceLambdaQueryWrapper);
                    String coverUrl = coverUrlMap.getOrDefault(title, "");
                    resource.setAuthor("牛奶喵");
                    // 详情
                    info2(s, resource, title, test);
                    System.out.println(title + "==>保存成功");

                    // 文件命名
                    String[] split = coverUrl.split("/");
                    String fileName = split[split.length - 1];
                    fileName = fileName.replace(" ", "");
                    resource.setCoverUrl(fileName);
                    resource.setViewCount(1L);
                    resource.setLikeCount((long) (Math.random() * 100));
                    if (Objects.nonNull(resource1)) {
                        System.out.println("已存在直接跳过");
                        continue;
                    } else {
                        resourceMapper.insert(resource);
                    }
                    System.out.println("开始下载封面图=》" + fileName);
                    test.httpsToGet(coverUrl, fileName);
                    System.out.println("下载成功");
                }
                j--;
            } catch (Exception e) {
                j = j;
                Thread.sleep(1000);
                System.out.println(e);
                System.out.println("出错了，重试");
            }
        }
    }

    public void info2(String url, Resource resource, String title, BatchDownloadFileTest test) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.54");
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
            if (!href.contains("www.ahhhhfs.com") && href.contains("http")) {
                reUrlList.add(href);
            }
        }

        for (Element element1 : p) {
            String text = element1.text();
            if (StringUtils.isNotBlank(text) && !text.contains("http")) {
                if (text.contains("A姐")) {
                    text = text.replace("A姐", "小编");
                }
                builder.append(text);
            }
            if (text.contains("http") && !text.contains("www.ahhhhfs.com")) {
                try {
                    String s = text.split("：")[1];
                    reUrlList.add(s);
                } catch (Exception e) {
                    System.out.println(e);
                    reUrlList.add("出错了");
                }

            }
        }

        // 下载资源图
        Set<String> picNameList = new HashSet<>();
        int i = 0;
        for (String pic : picUrlList) {

            String fileName = title + i + ".jpg";
            fileName = fileName.replace(" ", "").replace("/", "");
            System.out.println("开始下载资源图=》" + fileName);
            picNameList.add(fileName);
            test.httpsToGet(pic, fileName);
            i = i + 1;
        }

        resource.setResourceUrl(JSON.toJSONString(reUrlList));
        resource.setContent(builder.toString());
        resource.setPicUrl(JSON.toJSONString(picNameList));
    }

    @Test
    public void index() throws Exception {
        for (int j = 50; j > 0; ) {
            String url = "https://www.ahhhhfs.com/page/" + j + "/";
            System.out.println(url);
            try {
                HttpHeaders headers = new HttpHeaders();
                String cookie = "__snaker__id=4JBoZN3DrrPRG0d1; _9755xjdesxxd_=32; YD00551021681182%3AWM_TID=9SaTpW7jfY5BVBBEBBbRSFA0q0%2BRk2VK; XYHandicap=0; multiSelect=False; CurrentSkin=gone-t013; ValidateToken=602607b7ddb63d61b0bf68bc124e4380; ASP.NET_SessionId=ypjvwdpwaspqxkiimughj4pc; kangle_runat=3; gdxidpyhxdE=cwSLxOggLIsn44znmQpjVUoAYPe8%2BHlR43AS9Hb9X9%2FhzjIOEnbok%5Ct4%2FpanOt3sCUNviKtY%5C2KCYu1A462cY27%2BTnKeswBSxPwB%2F4ujcoW4TcqoG7vMNz%2FkOn6tc78qyf%2BSSgycG6MzpO624J3CM00gt4A5QxQEOUweAqXz0V9IGym1%3A1658041648256; YD00551021681182%3AWM_NI=K%2BOsrzFWRJ%2FnlVoPI6ZVCyV3ESwl7lqfrTAON0xQ8Zhf0FqQZHgU3RadrMNwqdT50j5%2B5kgBj0iyctR4Br4BJywz%2FwAvbdq5eVkU9qydLJaToix1p6VUa%2BIuZ5X%2FZoRadEc%3D; YD00551021681182%3AWM_NIKE=9ca17ae2e6ffcda170e2e6eeafb44a98999699b567949a8ea3d84b969e8e87c15d938d8796e56781b081b6ee2af0fea7c3b92a89f58194f17286899eabae66b08aa1b1cc3e9a8a8d9ad539878db98db668b3babeb5c4458ef0b8b2bb52acba00a7f45b9a98a9b6b25aa3bcbeaecd7db3bc9cd6b362f7f5fb8ce64aaaf0a79ae28092ec8e93b74a8aa7b99bae7d83aaae85f43fa78aa5b6bc7bbbbc84abe460adb7b8d2f33e968bafcccc7f9aeb98d0d96592bfaca6e237e2a3; LoginSessionID=9289ac65bbe0bcc812a2d72d7c5700af; say=28974a24ae0fda20c1158778e3f7c38f; GAMEID=256";
                headers.set("Cookie", cookie);
                headers.set("Host", "fc036.cc");
                headers.set("Referer", "http://fc036.cc/");
                headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
                headers.set("X-Requested-With", "XMLHttpRequest");

                HttpEntity httpEntity = new HttpEntity(headers);
                RestTemplate restTemplate = new RestTemplate();

                ResponseEntity<String> body = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        httpEntity,
                        String.class
                );

                Document doc = Jsoup.parse(body.getBody());
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
                        urlMap.put(href, title);
                    }
                }
                BatchDownloadFileTest test = new BatchDownloadFileTest();
                for (String s : urlList) {
                    String title = urlMap.get(s);
                    Resource resource = new Resource();
                    resource.setTitle(title);
                    LambdaQueryWrapper<Resource> resourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    resourceLambdaQueryWrapper.eq(Resource::getTitle, resource.getTitle());
                    Resource resource1 = resourceMapper.selectOne(resourceLambdaQueryWrapper);
                    String coverUrl = coverUrlMap.getOrDefault(title, "");
                    resource.setAuthor("牛奶喵");
                    // 详情
                    info(s, resource, title, test);
                    System.out.println(title + "==>保存成功");

                    // 文件命名
                    String[] split = coverUrl.split("/");
                    String fileName = split[split.length - 1];
                    fileName = fileName.replace(" ", "");
                    resource.setCoverUrl(fileName);
                    resource.setViewCount(1L);
                    resource.setLikeCount((long) (Math.random() * 100));
                    System.out.println("开始下载封面图=》" + fileName);
                    test.httpsToGet(coverUrl, fileName);
                    if (Objects.nonNull(resource1)) {
                        System.out.println("已存在直接跳过");
                        continue;
                    } else {
                        resourceMapper.insert(resource);
                    }
                    System.out.println("下载成功");
                }
                j--;
            } catch (Exception e) {
                j = j;
                Thread.sleep(1000);
                System.out.println("出错了，重试");
            }
        }
    }

    public void info(String url, Resource resource, String title, BatchDownloadFileTest test) throws Exception {
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.54");
//
//        Document infoDoc = Jsoup.connect(url).headers(headers).get();

        HttpHeaders headers = new HttpHeaders();
        String cookie = "__snaker__id=4JBoZN3DrrPRG0d1; _9755xjdesxxd_=32; YD00551021681182%3AWM_TID=9SaTpW7jfY5BVBBEBBbRSFA0q0%2BRk2VK; XYHandicap=0; multiSelect=False; CurrentSkin=gone-t013; ValidateToken=602607b7ddb63d61b0bf68bc124e4380; ASP.NET_SessionId=ypjvwdpwaspqxkiimughj4pc; kangle_runat=3; gdxidpyhxdE=cwSLxOggLIsn44znmQpjVUoAYPe8%2BHlR43AS9Hb9X9%2FhzjIOEnbok%5Ct4%2FpanOt3sCUNviKtY%5C2KCYu1A462cY27%2BTnKeswBSxPwB%2F4ujcoW4TcqoG7vMNz%2FkOn6tc78qyf%2BSSgycG6MzpO624J3CM00gt4A5QxQEOUweAqXz0V9IGym1%3A1658041648256; YD00551021681182%3AWM_NI=K%2BOsrzFWRJ%2FnlVoPI6ZVCyV3ESwl7lqfrTAON0xQ8Zhf0FqQZHgU3RadrMNwqdT50j5%2B5kgBj0iyctR4Br4BJywz%2FwAvbdq5eVkU9qydLJaToix1p6VUa%2BIuZ5X%2FZoRadEc%3D; YD00551021681182%3AWM_NIKE=9ca17ae2e6ffcda170e2e6eeafb44a98999699b567949a8ea3d84b969e8e87c15d938d8796e56781b081b6ee2af0fea7c3b92a89f58194f17286899eabae66b08aa1b1cc3e9a8a8d9ad539878db98db668b3babeb5c4458ef0b8b2bb52acba00a7f45b9a98a9b6b25aa3bcbeaecd7db3bc9cd6b362f7f5fb8ce64aaaf0a79ae28092ec8e93b74a8aa7b99bae7d83aaae85f43fa78aa5b6bc7bbbbc84abe460adb7b8d2f33e968bafcccc7f9aeb98d0d96592bfaca6e237e2a3; LoginSessionID=9289ac65bbe0bcc812a2d72d7c5700af; say=28974a24ae0fda20c1158778e3f7c38f; GAMEID=256";
        headers.set("Cookie", cookie);
        headers.set("Host", "fc036.cc");
        headers.set("Referer", "http://fc036.cc/");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        headers.set("X-Requested-With", "XMLHttpRequest");

        HttpEntity httpEntity = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> body = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        Document infoDoc = Jsoup.parse(body.getBody());

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
            if (!href.contains("www.ahhhhfs.com") && href.contains("http")) {
                reUrlList.add(href);
            }
        }

        for (Element element1 : p) {
            String text = element1.text();
            if (StringUtils.isNotBlank(text) && !text.contains("http")) {
                if (text.contains("A姐")) {
                    text = text.replace("A姐", "小编");
                }
                builder.append(text);
            }
            if (text.contains("http") && !text.contains("www.ahhhhfs.com")) {
                try {
                    String s = text.split("：")[1];
                    reUrlList.add(s);
                } catch (Exception e) {
                    System.out.println(e);
                    reUrlList.add("出错了");
                }

            }
        }

        // 下载资源图
        Set<String> picNameList = new HashSet<>();
        int i = 0;
        for (String pic : picUrlList) {

            String fileName = title + i + ".jpg";
            fileName = fileName.replace(" ", "").replace("/", "");
            System.out.println("开始下载资源图=》" + fileName);
            picNameList.add(fileName);
            test.httpsToGet(pic, fileName);
            i = i + 1;
        }

        resource.setResourceUrl(JSON.toJSONString(reUrlList));
        resource.setContent(builder.toString());
        resource.setPicUrl(JSON.toJSONString(picNameList));
    }

    public static void main(String[] args) throws Exception {
        BatchDownloadFileTest test = new BatchDownloadFileTest();
        // 下载资源图
        Set<String> picNameList = new HashSet<>();
        String s = "[\"https://www.ahhhhfs.com/wp-content/uploads/2023/05/%E5%9C%A8%E7%BA%BF%E7%9F%AD%E4%BF%A1%E5%8E%8B%E5%8A%9B%E6%B5%8B%E8%AF%95-%E5%A4%9A%E6%8E%A5%E5%8F%A3-dxhz-%E4%B8%BB%E9%A1%B5%E8%8F%9C%E5%8D%95.jpg\",\"https://www.ahhhhfs.com/wp-content/uploads/2023/05/%E5%9C%A8%E7%BA%BF%E7%9F%AD%E4%BF%A1%E5%8E%8B%E5%8A%9B%E6%B5%8B%E8%AF%95-%E5%A4%9A%E6%8E%A5%E5%8F%A3-dxhz-%E5%90%8E%E5%8F%B0.jpg\",\"https://www.ahhhhfs.com/wp-content/uploads/2023/05/%E5%9C%A8%E7%BA%BF%E7%9F%AD%E4%BF%A1%E5%8E%8B%E5%8A%9B%E6%B5%8B%E8%AF%95-%E5%A4%9A%E6%8E%A5%E5%8F%A3-dxhz-%E6%95%88%E6%9E%9C.jpg\"]";
        List<String> picUrlList = JSON.parseArray(s, String.class);
        String title = "miaoxiaowen";
        int i = 0;
        for (String pic : picUrlList) {
            String end = pic.substring(pic.length() - 4, pic.length());
            String fileName = title + i + end;
            System.out.println("开始下载资源图=》" + fileName);
            picNameList.add(fileName);
            test.httpsToGet(pic, fileName);
            i = i + 1;
        }
    }
}
