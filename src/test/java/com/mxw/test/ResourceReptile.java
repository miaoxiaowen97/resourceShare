package com.mxw.test;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mxw.ResourceShareApplication;
import com.mxw.entity.Resource;
import com.mxw.mapper.ResourceMapper;
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

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ResourceShareApplication.class})
public class ResourceReptile {

    @Autowired
    private ResourceMapper resourceMapper;

    @Test
    public void getResource() throws Exception {
        // 请求头封装
        HashMap<String, String> headers = getHeaders();

        BatchDownloadFileTest downloadFile = new BatchDownloadFileTest();
        Proxy proxy = new Proxy( Proxy.Type.HTTP, new InetSocketAddress( "112.17.173.55",9091 ) );
        for (int j = 1; j > 0; j--) {
            // 发送请求
            String url = "https://www.ahhhhfs.com/page/" + j + "/";
            // 标题-封面集合
            Map<String, String> coverUrlMap = new HashMap<>();
            // 标题-地址集合
            Map<String, String> urlMap = new HashMap<>();
            System.out.println("开始爬取="+url);
            Document doc = Jsoup.connect(url)
                    .headers(headers).get();
            Elements elementsByClass = doc.getElementsByClass("module posts-wrapper list");
            Node node = elementsByClass.get(0).childNode(1);
            List<Node> nodes = node.childNodes();

            // 获取详情地址
            List<String> urlList = getInfoUrl(coverUrlMap, urlMap, nodes);
            for (int i = urlList.size()-1; i >= 0; i--) {
                System.out.println(i);
                String infoUrl=urlList.get(i);
                saveResource(downloadFile, coverUrlMap, urlMap, infoUrl);

            }
//            for (String infoUrl : urlList) {
//                saveResource(downloadFile, coverUrlMap, urlMap, infoUrl);
//            }
        }
    }

    private void saveResource(BatchDownloadFileTest downloadFile, Map<String, String> coverUrlMap, Map<String, String> urlMap, String infoUrl) throws Exception {
        String title = urlMap.get(infoUrl);
        Resource resource = new Resource();
        resource.setTitle(title);
        LambdaQueryWrapper<Resource> resourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        resourceLambdaQueryWrapper.eq(Resource::getTitle, resource.getTitle());
        Resource oldResource = resourceMapper.selectOne(resourceLambdaQueryWrapper);
        if (oldResource!=null){
            System.out.println(title+"==数据库存在，不下载图片，跳过");
            return;
        }
        String coverUrl = coverUrlMap.getOrDefault(title, "");
        resource.setViewCount(1L);
        resource.setLikeCount((long) (Math.random() * 100));
        resource.setAuthor("牛奶喵");

        // 下载封面图

        String[] split = coverUrl.split("/");
        String fileName = split[split.length - 1];
        fileName = fileName.replace(" ", "").replace(".webp", ".jpg");
        resource.setCoverUrl(fileName);
        Thread.sleep(1000);
        System.out.println("开始下载封面图=》" + fileName);
        downloadFile.httpsToGet(coverUrl, fileName);
        // 详情
        getResourceInfo(infoUrl, resource, title, downloadFile);

        if (Objects.nonNull(oldResource)) {
            System.out.println("已存在进行更新");
            Integer id = oldResource.getId();
            resource.setId(id);
            resourceMapper.updateById(resource);
        } else {
            resourceMapper.insert(resource);
            System.out.println("保存成功");
        }
    }

    private void getResourceInfo(String infoUrl, Resource resource, String title, BatchDownloadFileTest downloadFile) throws Exception {
        HashMap<String, String> headers = getHeaders();
        Thread.sleep(1000);
        Document infoDoc = Jsoup.connect(infoUrl).headers(headers).get();
        Elements infoDocElementsByClass = infoDoc.getElementsByClass("entry-content u-text-format u-clearfix");
        StringBuilder builder = new StringBuilder();
        Elements p = infoDocElementsByClass.first().getElementsByTag("p");
        Elements a = infoDocElementsByClass.first().getElementsByTag("a");
        Set<String> picUrlList = new HashSet<>();
        Set<String> reUrlList = new HashSet<>();
        System.out.println("=====获取详情====");
        // 获取资源详情图片
        for (Element element1 : a) {
            String href = element1.attr("href");
            if (href.endsWith(".jpg")) {
                picUrlList.add(href);
            }
            if (!href.contains("www.ahhhhfs.com") && href.contains("http")) {
                reUrlList.add(href);
            }
        }

        // 获取资源详情介绍
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
                    System.out.println("获取资源详情地址出错：" + e);
                }
            }
        }

        // 只下载3资源图
        Set<String> picNameList = new HashSet<>();
        int i = 0;
        for (String pic : picUrlList) {
            if (i >= 3) {
                break;
            }
            try {
                String fileName = title + i + ".jpg";
                fileName = fileName.replace(" ", "").replace("/", "");
                System.out.println("开始下载资源图=》" + fileName);
                picNameList.add(fileName);
                Thread.sleep(1000);
                downloadFile.httpsToGet(pic, fileName);
            }catch (Exception e){
                System.out.println("开始下载资源图出错了。跳过");
            }
            i = i + 1;
        }

        resource.setResourceUrl(JSON.toJSONString(reUrlList));
        resource.setContent(builder.toString());
        resource.setPicUrl(JSON.toJSONString(picNameList));

    }

    private List<String> getInfoUrl(Map<String, String> coverUrlMap, Map<String, String> urlMap, List<Node> nodes) {
        List<String> urlList = new ArrayList<>();
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
        return urlList;
    }

    private HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("authority", "www.ahhhhfs.com");
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        headers.put("cache-control", "max-age=0");
        headers.put("cookie", "__gads=ID=1cad5bb8f0895bb2-2269acb8ddd90077:T=1676629952:RT=1676629952:S=ALNI_MY-0XnQD3BadPSO18RWoZNQdosVLw; _ga_9GWG1G07RZ=deleted; _ga_9GWG1G07RZ=deleted; _gid=GA1.2.255719158.1684836434; __gpi=UID=000009485253eace:T=1676629952:RT=1684892555:S=ALNI_MbfYOGWWdjlofIY4TdIlHtuRS5Rwg; _ga_9GWG1G07RZ=GS1.1.1684899661.156.1.1684899775.9.0.0; _ga=GA1.2.890746533.1676629947");
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
        return headers;
    }
}
