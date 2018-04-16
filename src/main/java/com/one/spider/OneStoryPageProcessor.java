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
 *
 * @author https://github.com/zhdh
 */
public class OneStoryPageProcessor implements PageProcessor {

    private Logger log = LoggerFactory.getLogger(OneStoryPageProcessor.class);

    private Site site = Site.me().setTimeOut(20000).setRetryTimes(3).setSleepTime(3000).setRetrySleepTime(3000);

    private static final String HOME_PAGE = "http://wufazhuce.com/";

    private static String ARTICLE_PAGE = null;

    private static String QUESTION_PAGE = null;

    @Override
    public void process(Page page) {
        String type, date, day, word, imageURL, articleTitle, articleURL = "", questionURL = "", questionTitle, imageName, outputPath;
        if (page.getUrl().toString().equals(HOME_PAGE)) {
            String body = page.getHtml().regex("<body[^>]*>([\\s\\S]*)<\\/body>").toString();
            Html html = Html.create(body);
            type = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[1]/text()").toString().trim();
            date = html.xpath("////*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[1]/p[3]/text()").toString().trim();
            day = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[1]/p[2]/text()").toString().trim();
            word = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[2]/a/text()").toString().trim();
            imageURL = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/a").$("img", "src").toString().trim();
            articleTitle = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[1]/div/p[2]/a/text()").toString().trim();
            articleURL = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[1]/div/p[2]/").$("a", "href").toString().trim();
            questionURL = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[2]/div/p[2]/").$("a", "href").toString().trim();
            questionTitle = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[2]/div/p[2]/a/text()").toString().trim();
            imageName = imageURL.split("/")[3] + ".jpg";
            outputPath = System.getProperty("user.dir") + "\\image";
            ARTICLE_PAGE = articleURL;
            QUESTION_PAGE = questionURL;
            log.info("type: " + type + " date: " + date + " " + day + " word: " + word);
            log.info("article: " + articleTitle + " " + articleURL);
            log.info("questionURL: " + questionTitle + " " + questionURL);
        }
        // add article page
        page.addTargetRequest(articleURL);

        if (ARTICLE_PAGE.equals(page.getUrl().toString())) {
            String articleBody = page.getHtml().regex("<body[^>]*>([\\s\\S]*)<\\/body>").toString();
            Html html = Html.create(articleBody);
            String abstractContent = html.xpath("//*[@id=\"main-container\"]/div/div/div/div/div[1]/div/div/text()").toString();
            log.info("Article abstract: " + abstractContent);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String url = "http://wufazhuce.com/";
        Spider.create(new OneStoryPageProcessor()).addUrl(url).thread(1).run();
    }
}
