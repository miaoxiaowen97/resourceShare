//package com.mxw.test;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.Proxy;
//
//public class okhttpTest {
//
//    @Test
//    public void testOkHttp() throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("18.184.108.149", 9812)))
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://www.ahhhhfs.com/page/49/")
//                .method("GET", null)
//                .addHeader("authority", "www.ahhhhfs.com")
//                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
//                .addHeader("accept-language", "zh-CN,zh;q=0.9")
//                .addHeader("cache-control", "max-age=0")
//                 .addHeader("if-modified-since", "Wed, 24 May 2023 01:57:11 GMT")
//                .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"113\", \"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24\"")
//                .addHeader("sec-ch-ua-mobile", "?0")
//                .addHeader("sec-ch-ua-platform", "\"Windows\"")
//                .addHeader("sec-fetch-dest", "document")
//                .addHeader("sec-fetch-mode", "navigate")
//                .addHeader("sec-fetch-site", "none")
//                .addHeader("sec-fetch-user", "?1")
//                .addHeader("upgrade-insecure-requests", "1")
//                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
//                .build();
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body());
//    }
//}
