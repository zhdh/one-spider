package com.one.spider;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * one spider
 * @author https://github.com/zhdh
 */
public class OneStoryPageProcessor implements PageProcessor{

    Logger log = LoggerFactory.getLogger(OneStoryPageProcessor.class);

    @Override
    public void process(Page page) {
        log.info(page.getHtml().toString());
    }

    @Override
    public Site getSite() {
        return null;
    }

    public static void main(String[] args){

    }
}
