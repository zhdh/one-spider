package com.one.spider;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * one spider
 * @author https://github.com/zhdh
 */
public class OneStoryPageProcessor implements PageProcessor{

    private Logger log = LoggerFactory.getLogger(OneStoryPageProcessor.class);

    private Site site = Site.me().setTimeOut(20000).setRetryTimes(3).setSleepTime(3000).setRetrySleepTime(3000);

    @Override
    public void process(Page page) {
        String body = page.getHtml().regex("<body[^>]*>([\\s\\S]*)<\\/body>").toString();
        Html html = Html.create(body);
        String type = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[1]/text()").toString().trim();
        String date = html.xpath("////*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[1]/p[3]/text()").toString().trim();
        String day = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[1]/p[2]/text()").toString().trim();
        String word = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[2]/a/text()").toString().trim();
        String imageUrl = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/a").$("img","src").toString().trim();
        String articleTitle = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[1]/div/p[2]/a/text()").toString().trim();
        String articleURL = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[1]/div/p[2]/").$("a","href").toString().trim();
        String questionURL = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[2]/div/p[2]/").$("a","href").toString().trim();
        String questionTitle = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[2]/div/p[2]/a/text()").toString().trim();
        String imageName = imageUrl.split("/")[3] + ".jpg";
        String outputPath = System.getProperty("user.dir") + "\\image";
        log.info("type: " + type + " date: "+ date + " " + day + " word: " + word );
        log.info("article: " + articleTitle + " " + articleURL);
        log.info("questionURL: " + questionTitle + " " + questionURL);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args){
        String url = "http://wufazhuce.com/";
        Spider.create(new OneStoryPageProcessor()).addUrl(url).thread(1).run();
    }
}
