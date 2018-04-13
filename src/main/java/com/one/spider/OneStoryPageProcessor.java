package com.one.spider;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * one spider
 * @author https://github.com/zhdh
 */
public class OneStoryPageProcessor implements PageProcessor{

    private Logger log = LoggerFactory.getLogger(OneStoryPageProcessor.class);

    private Site site = Site.me().setTimeOut(20000).setRetryTimes(3).setSleepTime(3000).setRetrySleepTime(3000);

    @Override
    public void process(Page page) {
        log.info("content: " + page.getHtml().toString());
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
