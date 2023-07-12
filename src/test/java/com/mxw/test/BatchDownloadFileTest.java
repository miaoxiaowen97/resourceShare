package com.mxw.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description: https文件链接下载文件
 * @BelongsProject: mhxFileDownload
 * @BelongsPackage: com.mhx.info.service
 * @ClassName: BatchDownloadFile
 * @Author: MHX
 * @CreateTime: 2022/11/25
 */
public class BatchDownloadFileTest {

    public static void main(String[] args) throws Exception {
        String doc="<div class=\"row row-cols-2 row-cols-sm-3 row-cols-md-4 row-cols-lg-6\">\n" +
                "                <div class=\"col pc\"><a href=\"https://weibo.com/newlogin?tabtype=search&amp;url=\" target=\"_blank\">微博热搜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/wb.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://top.baidu.com/board\" target=\"_blank\" class=\"b\">百度风云榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bd.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.sogou.com/web?query=%E4%BB%8A%E6%97%A5%E7%83%AD%E6%90%9C\" target=\"_blank\" class=\"c\">搜狗热搜榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/sg.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://trends.so.com/hot\" target=\"_blank\" class=\"d\">360实时热点<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/360.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.douyin.com/hot\" target=\"_blank\" class=\"e\">抖音热点<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/dy.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://weibo.com/newlogin?tabtype=topic&amp;url=\" target=\"_blank\" class=\"f\">微博话题榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/wb.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.weibo.cn/p/106003type=25&amp;t=3&amp;disable_hot=1&amp;filter_type=realtimehot\" target=\"_blank\">微博热搜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/wb.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://top.baidu.com/board?tab=realtime\" target=\"_blank\" class=\"b\">百度热搜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bd.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://api.toutiaoapi.com/feoffline/hotspot_and_local/html/hot_list/index.html\" target=\"_blank\" class=\"f\">头条热点<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tt.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.sogou.com/web/searchList.jsp?keyword=%E4%BB%8A%E6%97%A5%E7%83%AD%E7%82%B9\" target=\"_blank\" class=\"c\">搜狗热搜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/sg.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.so.com/s?q=%E5%AE%9E%E6%97%B6%E7%83%AD%E7%82%B9\" target=\"_blank\" class=\"d\">360热搜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/360.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://pages.uc.cn/r/uc-hotcomment/UcHotcommentNewPageHotSearchRank\" target=\"_blank\" class=\"e\">UC热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/uc.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.zhihu.com/billboard\" target=\"_blank\" class=\"g\">知乎热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zh.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"http://c.tieba.baidu.com/hottopic/browse/topicList?res_type=1\" target=\"_blank\" class=\"h\">贴吧热议榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tb.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://bbs.hupu.com/\" target=\"_blank\" class=\"m\">虎扑社区热帖<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/hp.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.hupu.com/gambia\" target=\"_blank\" class=\"m\">虎扑热帖<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/hp.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.ithome.com/rankm/\" target=\"_blank\" class=\"n\">IT之家热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/itzj.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://view.inews.qq.com/ranking\" target=\"_blank\" class=\"g\">腾讯热点榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/txw.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.sohu.com/xtopic/TURBd01ERTJNRE13\" target=\"_blank\" class=\"g\">搜狐24小时<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/souhu.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://ishare.ifeng.com/hotNewsRank\" target=\"_blank\" class=\"q\">凤凰新闻榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/fh.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://user.guancha.cn/main/index?click=24-hot-list\" target=\"_blank\" class=\"n\">观察者热评<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/gcz.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://36kr.com/hot-list/catalog\" target=\"_blank\" class=\"g\">36Kr资讯热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/36kr.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.36kr.com/hot-list-m\" target=\"_blank\" class=\"g\">36Kr资讯热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/36kr.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.huxiu.com/article/\" target=\"_blank\" class=\"q\">虎嗅热文推荐<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/hx.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.huxiu.com/channel/107.html\" target=\"_blank\" class=\"j\">虎嗅热点<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/hx.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.tmtpost.com/hot\" target=\"_blank\" class=\"m\">钛媒体热文<div class=\"slogo\"><img src=\"https://www.tmtpost.com/favicon.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.chinaz.com/hotnode/\" target=\"_blank\" class=\"g\">站长之家热点<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zzzj.ico\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://www.pingwest.com/status\" target=\"_blank\" class=\"q\">品玩实时快讯<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/pw.jpg\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://dig.chouti.com/zone/news\" target=\"_blank\" class=\"j\">抽屉新热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ct.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.chouti.com/all/hot\" target=\"_blank\" class=\"j\">抽屉新热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ct.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.douban.com/group/explore\" target=\"_blank\" class=\"k\">豆瓣热门讨论<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/db.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://jandan.net/top\" target=\"_blank\" class=\"p\">煎蛋热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/jiandan.jpg\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://3g.163.com/touch/tie\" target=\"_blank\" class=\"p\">网易热门跟帖<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/wy.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.pearvideo.com/popular\" target=\"_blank\" class=\"m\">梨视频热门资讯<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/lsp.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.acfun.cn/v/list63/index.htm\" target=\"_blank\" class=\"g\">A站话题热议<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/az.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.bilibili.com/read/ranking#type=3\" target=\"_blank\" class=\"j\">B站专栏排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bz.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://post.smzdm.com/hot_1/\" target=\"_blank\" class=\"h\">今日文章头条<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zdm.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.zhihu.com/topic/19776749/top-answers\" target=\"_blank\" class=\"p\">知乎话题榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zh.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://xueqiu.com/\" target=\"_blank\" class=\"k\">雪球财经热议<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/xq.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://tech.ifeng.com/\" target=\"_blank\" class=\"n\">凤凰科技热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/fh.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.hurun.net/zh-CN/Rank/HsRankDetails?pagetype=rich\" target=\"_blank\" class=\"r\">胡润百富榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/hr.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://www.fortunechina.com/rankings/index.htm\" target=\"_blank\" class=\"s\">财富榜单<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/cf.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.forbeschina.com/lists\" target=\"_blank\" class=\"t\">福布斯榜单<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/fbs.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://v.qq.com/biu/ranks/?t=hotsearch\" target=\"_blank\" class=\"u\">全网影视排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tx.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.iqiyi.com/ranks/hotplay\" target=\"_blank\" class=\"v\">爱奇艺风云榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/iqy.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://acz.youku.com/wow/ykpage/act/top_hot\" target=\"_blank\" class=\"w\">优酷热搜榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/yk.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://tv.sohu.com/hothdtv/\" target=\"_blank\" class=\"x\">搜狐视频榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/sh.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://piaofang.maoyan.com/dashboard\" target=\"_blank\" class=\"y\">电影票房榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/my.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://piaofang.maoyan.com/box-office?ver=normal\" target=\"_blank\" class=\"y\">电影票房榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/my.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://movie.douban.com/chart\" target=\"_blank\" class=\"z\">豆瓣电影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/db.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.douban.com/movie/\" target=\"_blank\" class=\"z\">豆瓣电影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/db.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.imdb.com/chart/top\" target=\"_blank\" class=\"l\">IMDb Top 250<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/imdb.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.maoyan.com/\" target=\"_blank\" class=\"u\">正在热映榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/my.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://i.maoyan.com/asgard/board/aggregation\" target=\"_blank\" class=\"u\">猫眼电影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/my.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://list.mtime.com/listIndex\" target=\"_blank\" class=\"v\">时光电影热门<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/sg.jpg\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.kuaishou.com/brilliant\" target=\"_blank\" class=\"y\">快手短视频热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ks.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.ixigua.com/\" target=\"_blank\" class=\"y\">西瓜热门视频<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/xg.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.bilibili.com/v/popular/rank/all\" target=\"_blank\" class=\"w\">B站热门视频<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bz.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.bilibili.com/ranking\" target=\"_blank\" class=\"w\">B站热门视频<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bz.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.acfun.cn/rank/list/#cid=-1;range=1\" target=\"_blank\" class=\"x\">A站排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/az.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://movie.douban.com/tv/#!type=tv&amp;tag=%E7%83%AD%E9%97%A8&amp;sort=recommend&amp;page_limit=20&amp;page_start=0\" target=\"_blank\" class=\"y\">热门电视剧<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/db.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.xinpianchang.com/discover/article\" target=\"_blank\" class=\"z\">热门短视频<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ck.jpg\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://www.xinpianchang.com/discover/article\" target=\"_blank\" class=\"z\">热门短视频<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ck.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://y.qq.com/n/yqq/toplist/4.html\" target=\"_blank\">QQ音乐流行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/qqyy.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://music.163.com/#/discover/toplist\" target=\"_blank\" class=\"d\">云音乐飙升榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/wyy.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.kugou.com/yy/rank/home/1-8888.html?from=rank\" target=\"_blank\" class=\"e\">酷狗TOP500<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/kg.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"http://music.migu.cn/v3/music/top/jianjiao_newsong\" target=\"_blank\" class=\"b\">咪咕新歌榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/mg.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.music.migu.cn/v3/music/top\" target=\"_blank\" class=\"d\">咪咕音乐榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/mg.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://music.douban.com/chart\" target=\"_blank\" class=\"c\">豆瓣音乐榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/db.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"http://www.poco.cn/works/works_list?classify_type=0&amp;works_type=week\" target=\"_blank\" class=\"g\">POCO人气摄影<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/poco.ico\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://wap.poco.cn/works/works_list\" target=\"_blank\" class=\"g\">POCO人气摄影<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/poco.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.skypixel.com/explore\" target=\"_blank\" class=\"h\">航拍摄影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/sky.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://tu.heiguang.com/works\" target=\"_blank\" class=\"j\">热门摄影作品<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/hg.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://www.cnu.cc/discoveryPage/hot-0\" target=\"_blank\" class=\"n\">CNU原创摄影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/cnu.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://tuchong.com/explore/\" target=\"_blank\" class=\"k\">图虫摄影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tc.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://unsplash.com/\" target=\"_blank\" class=\"m\">Unsplash<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/us.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://photo.fengniao.com/\" target=\"_blank\" class=\"m\">蜂鸟摄影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/fn.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.pexels.com/\" target=\"_blank\" class=\"p\">Pexels热图<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/pxs.svg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://tu.fengniao.com/\" target=\"_blank\" class=\"q\">热门人像摄影<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/fn.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://500px.com.cn/community/discover?t=rating\" target=\"_blank\" class=\"g\">500px摄影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/500px.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://h.bilibili.com/p\" target=\"_blank\" class=\"h\">B站摄影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bz.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://h.bilibili.com/ywh/h5/home#/draw\" target=\"_blank\" class=\"h\">B站画友榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bz.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://bcy.net/illust/toppost100\" target=\"_blank\" class=\"j\">半次元绘画榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bcy.jpg\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.zcool.com.cn/discover?cate=33&amp;subCate=0&amp;page=1\" target=\"_blank\" class=\"k\">站酷摄影精选<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zk.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://huaban.com/discovery/photography/\" target=\"_blank\" class=\"m\">花瓣摄影榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/hb.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.zcool.com.cn/top/index.do\" target=\"_blank\" class=\"r\">站酷榜单<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zk.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://top10.ui.cn/\" target=\"_blank\" class=\"s\">UI中国TOP10<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ui.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.ycyui.com/ulist.html\" target=\"_blank\" class=\"t\">创意设计榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ycy.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.ycyui.com/cool.html\" target=\"_blank\" class=\"r\">前沿网页设计<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ycy.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.zhisheji.com/paihang/zan/\" target=\"_blank\" class=\"s\">致设计榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zsj.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.ycyui.com/works.html\" target=\"_blank\" class=\"t\">创意设计<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ycy.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://gracg.com/works?type=hot\" target=\"_blank\" class=\"s\">热门创意插画<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ty.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://uiiiuiii.com/inspiration\" target=\"_blank\" class=\"r\">优设灵感<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/yslg.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://product.pconline.com.cn/hot/\" target=\"_blank\" class=\"v\">热门IT产品榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tpy.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://top.zol.com.cn/\" target=\"_blank\" class=\"x\">ZOL产品指数<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zol.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://top.zol.com.cn/compositor/cell_phone.html\" target=\"_blank\" class=\"y\">手机排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zol.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://bang.dangdang.com/books/bestsellers/01.00.00.00.00.00-24hours-0-0-1-1\" target=\"_blank\" class=\"z\">图书畅销榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/dd.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://top.17173.com/\" target=\"_blank\">游戏排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/17173.ico\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.ali213.net/paihb.html\" target=\"_blank\" class=\"b\">单机游戏排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/yx.ico\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://3g.ali213.net/paihb.html\" target=\"_blank\" class=\"b\">单机游戏排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/yx.ico\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"http://top.sina.com.cn/\" target=\"_blank\" class=\"c\">新浪游戏榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/xl.jpg\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://www.taptap.com/top/ios/played\" target=\"_blank\" class=\"c\">IOS游戏榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tp.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.taptap.com/top/played\" target=\"_blank\" class=\"d\">Android游戏榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tp.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://www.4399.com/flash/ph.htm\" target=\"_blank\" class=\"e\">小游戏排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/4399.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://top.baidu.com/board?tab=game\" target=\"_blank\" class=\"f\">游戏热搜榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bd.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.wegame.com.cn/rail/rank_detail.html?ranktype=good_comment\" target=\"_blank\" class=\"c\">WeGame游戏榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/wegame.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.qidian.com/rank\" target=\"_blank\" class=\"g\">热门小说榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/qd.ico\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.qidian.com/rank/male\" target=\"_blank\" class=\"g\">热门小说榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/qd.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://weread.qq.com/web/category/all\" target=\"_blank\" class=\"h\">微信读书榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/wxds.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.kuaikanmanhua.com/ranking/\" target=\"_blank\" class=\"j\">快看漫画榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/kk.ico\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"http://m.kuaikanmanhua.com/mob/rank\" target=\"_blank\" class=\"j\">快看漫画榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/kk.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.dm5.com/manhua-rank/\" target=\"_blank\" class=\"k\">漫画排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/d5.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://manga.bilibili.com/ranking/ninnki\" target=\"_blank\" class=\"m\">哔哩漫画榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/blmh.jpg\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.ximalaya.com/top\" target=\"_blank\" class=\"n\">热门电台榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/xmly.png\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.ximalaya.com/top/\" target=\"_blank\" class=\"n\">热门电台榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/xmly.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.iimedia.cn/c880\" target=\"_blank\" class=\"r\">艾媒金榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/am.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://index.baidu.com/v2/rank/index.html?#/industryrank\" target=\"_blank\" class=\"s\">百度行业指数<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bd.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.newrank.cn/ranklist?platform=6&amp;dateType=day&amp;source=8040\" target=\"_blank\" class=\"t\">B站UP主排名<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/bz.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://appgallery1.huawei.com/#/Top\" target=\"_blank\" class=\"s\">安卓应用榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/hw.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.aldzs.com/toplist\" target=\"_blank\" class=\"r\">小程序排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ald.jpg\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://top.chinaz.com/\" target=\"_blank\" class=\"s\">网站排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/zzzj.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://trendinsight.oceanengine.com/arithmetic-index\" target=\"_blank\" class=\"t\">抖音大数据<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/dy.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.newrank.cn/ranklist?platform=4&amp;source=8028\" target=\"_blank\" class=\"r\">快手排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ks.png\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.newrank.cn/ranklist?platform=0&amp;source=8027\" target=\"_blank\" class=\"s\">自媒体排名<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/xb.jpg\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://www.newrank.cn/m/rank\" target=\"_blank\" class=\"s\">自媒体排名<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/xb.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://index.iresearch.com.cn/new/#/\" target=\"_blank\" class=\"r\">艾瑞指数榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ar.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://mi.talkingdata.com/app-rank.html\" target=\"_blank\" class=\"t\">移动APP指数<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/yg.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://top.weiboyi.com/hwranklist/short-video\" target=\"_blank\" class=\"r\">短视频商业价值榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/wby.jpg\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.autohome.com.cn/newbrand/\" target=\"_blank\" class=\"n\">上市新车榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/qczj.ico\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://car.m.autohome.com.cn/carseriesrank/list/?typeid=1\" target=\"_blank\" class=\"n\">汽车销量榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/qczj.ico\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://price.pcauto.com.cn/top/r45/\" target=\"_blank\" class=\"m\">汽车排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tpyqc.ico\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.pcauto.com.cn/auto/top/sales.html\" target=\"_blank\" class=\"m\">汽车排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tpyqc.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://auto.sina.com.cn/zhishu/\" target=\"_blank\" class=\"k\">新浪汽车指数<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/xlqc.jpg\"></div></a></div>\n" +
                "                <div class=\"col pc\"><a href=\"https://www.dongchedi.com/sales\" target=\"_blank\" class=\"n\">懂车帝排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/dcd.jpg\"></div></a></div>\n" +
                "                <div class=\"col mobs\"><a href=\"https://m.dcdapp.com/motor/m/car_series/rank\" target=\"_blank\" class=\"n\">懂车帝排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/dcd.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://mydown.yesky.com/allupdate/\" target=\"_blank\" class=\"u\">软件下载排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/tjw.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.onlinedown.net/hits/\" target=\"_blank\" class=\"w\">热门软件下载榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/hj.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://juejin.cn/hot/articles/1\" target=\"_blank\" class=\"v\">掘金热榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/jj.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://blog.csdn.net/rank/list\" target=\"_blank\" class=\"x\">CSDN排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/csdn.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.dxsbb.com/news/list_75.html\" target=\"_blank\" class=\"z\">中国大学排名<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/dxs.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.dxsbb.com/news/list_142.html\" target=\"_blank\" class=\"v\">世界大学排名<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/dxs.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.shanghairanking.cn/\" target=\"_blank\" class=\"l\">大学及学科排名<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/dxpm.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://report.amap.com/index.do\" target=\"_blank\" class=\"u\">城市拥堵榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/gd.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://www.iyiou.com/company\" target=\"_blank\" class=\"w\">亿欧企业排名<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/eo.ico\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://www.xiachufang.com/explore/\" target=\"_blank\" class=\"c\">美食菜谱榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/xcf.png\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://eye.kuyun.com/\" target=\"_blank\" class=\"b\">电视直播关注榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ky.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"http://www.tvtv.hk/\" target=\"_blank\" class=\"c\">收视率排行榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/ssl.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://t.vip.com/BVBGjIBlt28\" target=\"_blank\" class=\"e\">唯品会特卖<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/vip.jpg\"></div></a></div>\n" +
                "                <div class=\"col\"><a href=\"https://union-click.jd.com/jdc?e=&amp;p=AyIGZRprFDJWWA1FBCVbV0IUWVALHE5fBUUZTFINXAAECUteIkFVZwlPZ1JhNkklT0ATejNPA09icVFZF2sQAxMGVx9YHAoVN1YaWhwGGgBRHVklMhIGZVA1FDIQBlQYUhULEQNWK1sRChoBVBxSEwYRAVErXBULItD6to2WocWo%2BMzbpQMQN2UrWCUyIgZlG2tKRk9aZRlaFAYb\" target=\"_blank\" class=\"f\">京东秒杀榜<div class=\"slogo\"><img src=\"http://guozhivip.com/rank/images/jd.png\"></div></a></div>\n" +
                "            </div>";
        Document document = Jsoup.parse(doc);
        List<Node> nodes = document.childNodes();
        Node node = nodes.get(0).childNode(1).childNode(0);
//        httpsToGet("http://guozhivip.com/rank/images/wb.png","wb.png");
        List<String> url=new ArrayList<String>();
        List<Node> childNodes = node.childNodes();
        for (Node childNode : childNodes) {
            String s = childNode.toString();
            if (StringUtils.isNotBlank(s)){
                String src = childNode.childNode(0).childNode(1).childNode(0).attributes().get("src");
                url.add(src);
            }
        }

        for (String s : url) {
            System.out.println(s);
            String[] split = s.split("/");
            String name = split[split.length - 1];
            httpsToGet(s, name);
            System.out.println(name);
        }
    }
    /**
     * https来获得
     *
     * @throws Exception 异常
     */
    @Test
    public static void httpsToGet(String apiHttp, String fileName) throws Exception {
//        文件下载存储路径
        String savePath = "E:\\code\\mycode\\pic";
//        https文件下载链接

//        忽略对服务器端证书的校验
        SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                NoopHostnameVerifier.INSTANCE);

        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(scsf).build();
        HttpGet httpget = new HttpGet(apiHttp);

        HttpResponse response = client.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
//        对存储空间大小预定义
        int cache = 10 * 1024;
//        文件输出路径
        FileOutputStream fileout = new FileOutputStream(savePath + "/" + fileName);
        byte[] buffer = new byte[cache];
        int ch = 0;
        while ((ch = is.read(buffer)) != -1) {
            fileout.write(buffer, 0, ch);
        }
        is.close();
        fileout.flush();
        fileout.close();
    }

    @Test
    public void test() throws Exception {
        httpsToGet("http://guozhivip.com/rank/images/wb.png","wb.png");
    }
}
