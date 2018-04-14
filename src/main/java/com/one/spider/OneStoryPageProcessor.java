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
        String day = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[1]/p[2]/text()").toString().trim();
        log.info("type: " + type + " day :" + day);
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
