//package com.mxw.test;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpHost;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContexts;
//import org.junit.Test;
//
//import java.io.FileOutputStream;
//import java.io.InputStream;
//
//
///**
// * @Description: https文件链接下载文件
// * @BelongsProject: mhxFileDownload
// * @BelongsPackage: com.mhx.info.service
// * @ClassName: BatchDownloadFile
// * @Author: MHX
// * @CreateTime: 2022/11/25
// */
//public class BatchDownloadFileTest {
//
//    public static void main(String[] args) {
//        String apiHttp = "https://www.ahhhhfs.com/wp-content/uploads/2023/05/Chatbot-UI-开源AI聊天用户界面-免翻使用ChatGPT-支持GPT-4-Featured-Image-300x200.jpg";
//        //        文件命名
//        String[] split = apiHttp.split("/");
//        String s = split[split.length - 1];
//        System.out.println(s);
//
//    }
//    /**
//     * https来获得
//     *
//     * @throws Exception 异常
//     */
//    @Test
//    public void httpsToGet(String apiHttp,String fileName,String ip,Integer port ) throws Exception {
////        文件下载存储路径
//        String savePath = "E:\\code\\mycode\\pic";
////        https文件下载链接
////        String apiHttp = "https://www.ahhhhfs.com/wp-content/uploads/2023/05/Roam-Around-基于-ChatGPT-的AI旅行助手-Featured-Image-300x200.jpg";
////        忽略对服务器端证书的校验
//        SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
//                NoopHostnameVerifier.INSTANCE);
//        HttpHost proxy=new HttpHost(ip,port);
//        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(scsf).build();
//        HttpGet httpget = new HttpGet(apiHttp);
//        httpget.setConfig(config);
//        HttpResponse response = client.execute(httpget);
//        HttpEntity entity = response.getEntity();
//        InputStream is = entity.getContent();
////        对存储空间大小预定义
//        int cache = 10 * 1024;
////        文件输出路径
//        FileOutputStream fileout = new FileOutputStream(savePath + "/" + fileName);
//        byte[] buffer = new byte[cache];
//        int ch = 0;
//        while ((ch = is.read(buffer)) != -1) {
//            fileout.write(buffer, 0, ch);
//        }
//        is.close();
//        fileout.flush();
//        fileout.close();
//    }
//}
