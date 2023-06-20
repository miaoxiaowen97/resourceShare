package com.mxw.test;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mxw.ResourceShareApplication;
import com.mxw.entity.ITResource;
import com.mxw.entity.Resource;
import com.mxw.mapper.ITResourceMapper;
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
public class ItjcReptile {


    @Autowired
    private ITResourceMapper itResourceMapper;

    @Test
    public void getResource() throws Exception {
        // 请求头封装
        HashMap<String, String> headers = getHeaders();
        Integer type=7;
        String url = "https://www.itjc8.com/forum-75-2.html";
        // 标题-封面集合
        List<String> urlList = new ArrayList<>();
        // 标题-地址集合
        Map<String, String> urlTitle = new HashMap<>();

        System.out.println("开始爬取=" + url);
        Document doc = Jsoup.connect(url)
                .headers(headers).get();
        Element element = doc.getElementById("threadlisttableid");
        Elements tbody = element.getElementsByTag("tbody");
        for (Element e : tbody) {
            if (e.childNodeSize()<2){
                continue;
            }
            Node node = e.childNode(1);
            Node node1 = node.childNode(3);
            if (node1.childNodeSize() > 1) {
                Node node2 = node1.childNode(1);
                Attributes attributes = node2.attributes();
                String href = attributes.get("href");
                if (href.startsWith("https://") || href.startsWith("http://")) {
                    urlList.add(href);
                    String title = node2.childNode(0).toString();
                    urlTitle.put(href, title);
                }
            }
        }
        for (String infoUrl : urlList) {
            String title = urlTitle.get(infoUrl);
            ITResource resource = new ITResource();
            resource.setTitle(title);
            LambdaQueryWrapper<ITResource> resourceLambdaQueryWrapper = new LambdaQueryWrapper<>();
            resourceLambdaQueryWrapper.eq(ITResource::getTitle, resource.getTitle());
            ITResource oldResource = itResourceMapper.selectOne(resourceLambdaQueryWrapper);
            if (Objects.nonNull(oldResource)){
                // 存在
                System.out.println("已存在==>" + title);
                continue;
            }
            String content = getInfo(infoUrl);
            ITResource itResource=new ITResource();
            itResource.setType(type);
            itResource.setTitle(title);
            itResource.setResourceUrl(infoUrl);
            itResource.setAuthor("牛奶喵");
            itResource.setContent(content);
            itResource.setViewCount(1L);
            itResource.setLikeCount((long) (Math.random() * 100));
            System.out.println("保存成功=" + title);
            itResourceMapper.insert(itResource);
        }


    }

    public String getInfo(String url) throws Exception {
        // 请求头封装
        HashMap<String, String> headers = getHeaders();

        // 标题-封面集合
        List<String> urlList = new ArrayList<>();

        System.out.println("开始爬取=" + url);
        Document doc = Jsoup.connect(url)
                .headers(headers).get();
        Elements tF = doc.getElementsByClass("t_f");
        Element today = tF.get(0);
        List<Node> nodes = today.childNodes();
        StringBuilder content = new StringBuilder();
        for (int i = 4; i < nodes.size() - 4; i++) {
            Node node = nodes.get(i);
            String nodeName = node.nodeName();
            if ("span".endsWith(nodeName) || "font".equals(nodeName)) {
                continue;
            }
            String string = node.toString();
            if (string.contains("下载地址")){
                continue;
            }
            content.append(string);
        }

        return content.toString();

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
