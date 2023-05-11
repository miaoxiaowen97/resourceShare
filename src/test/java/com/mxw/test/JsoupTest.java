//package com.mxw.test;
//
//import com.alibaba.fastjson.JSON;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.*;
//import org.jsoup.select.Elements;
//import org.junit.Test;
//import org.junit.platform.commons.util.StringUtils;
//
//import java.io.IOException;
//import java.util.*;
//
//public class JsoupTest {
//
//    @Test
//    public void test() throws IOException {
//        Document doc = Jsoup.connect("https://www.ahhhhfs.com/page/2/").get();
//        // 获取带有 href 属性的 a 元素
//        Elements elementsByClass = doc.getElementsByClass("module posts-wrapper list");
//        Element element = elementsByClass.get(0);
//        Node node = element.childNode(1);
//        List<Node> nodes = node.childNodes();
//        List<String> titleList = new ArrayList<>();
//        List<String> urlList = new ArrayList<>();
//        Map<String, String> coverUrlMap = new HashMap<>();
//        for (Node node1 : nodes) {
//            int i = node1.childNodeSize();
//            if (i > 0) {
//                Node node2 = node1.childNode(1);
//                List<Node> nodes1 = node2.childNodes();
//                Node node3 = node2.childNode(1);
//                Node node4 = node3.childNode(0);
//                Node node5 = node4.childNode(0);
//                Attributes attributes = node5.attributes();
//                String href = attributes.get("href");
//                urlList.add(href);
//                String title = attributes.get("title");
//                titleList.add(title);
//                Node node6 = node5.childNode(0);
//                Attributes attributes1 = node6.attributes();
//                String src = attributes1.get("data-src");
//                coverUrlMap.put(title, src);
//            }
//        }
//
//        for (String s : urlList) {
//            Document infoDoc = Jsoup.connect(s).get();
//            Elements infoDocElementsByClass = infoDoc.getElementsByClass("entry-content u-text-format u-clearfix");
//            for (Element infoDocElement : infoDocElementsByClass) {
//                System.out.println(infoDocElement);
//            }
//        }
//    }
//
//    @Test
//    public void testInfo() throws IOException {
//        String url = "https://www.ahhhhfs.com/41577/";
//        Document infoDoc = Jsoup.connect(url).get();
//        Elements infoDocElementsByClass = infoDoc.getElementsByClass("entry-content u-text-format u-clearfix");
//        Element element = infoDocElementsByClass.get(0);
//        StringBuilder builder = new StringBuilder();
//        Elements p = infoDocElementsByClass.first().getElementsByTag("p");
//        Elements a = infoDocElementsByClass.first().getElementsByTag("a");
//
//
//        List<String> picUrlList = new ArrayList<>();
//        List<String> reUrlList = new ArrayList<>();
//
//        for (Element element1 : a) {
//            String href = element1.attr("href");
//            if (href.endsWith(".jpg")) {
//                picUrlList.add(href);
//            }
//           if (!href.contains("www.ahhhhfs.com")&&href.contains("http")){
//               reUrlList.add(href);
//           }
//        }
//
//        for (Element element1 : p) {
//            String text = element1.text();
//            if (StringUtils.isNotBlank(text)&&!text.contains("http")){
//                if (text.contains("A姐")){
//                    text = text.replace("A姐", "小编");
//                }
//                builder.append(text);
//            }
//            if (text.contains("http")&&!text.contains("www.ahhhhfs.com")){
//                String s = text.split("：")[1];
//                reUrlList.add(s);
//            }
//        }
//
//        System.out.println(builder);
//        System.out.println(JSON.toJSONString(picUrlList));
//        System.out.println(JSON.toJSONString(reUrlList));
//    }
//
//    public void info(String url) throws IOException {
//
//    }
//}
