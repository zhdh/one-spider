package com.one.spider;

import com.one.utils.DBUtils;
import com.one.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

    private String type = null;
    private String date = null;
    private String word = null;
    private String articleTitle = null;
    private String articleAbstract = null;
    private String articleAuthor = null;
    private String articleContent = null;
    private String articleURL = null;
    private String questionURL = null;
    private String questionTitle = null;
    private String outputPath = null;

    @Override
    public void process(Page page) {
        Connection connection = DBUtils.getConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (page.getUrl().toString().equals(HOME_PAGE)) {
            String body = page.getHtml().regex("<body[^>]*>([\\s\\S]*)<\\/body>").toString();
            Html html = Html.create(body);
            type = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[1]/text()").toString().trim();
            date = html.xpath("////*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[1]/p[3]/text()").toString().trim();
            String day = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[1]/p[2]/text()").toString().trim();
            word = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/div[2]/div[2]/a/text()").toString().trim();
            String imageURL = html.xpath("//*[@id=\"carousel-one\"]/div/div[1]/a").$("img", "src").toString().trim();
            articleTitle = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[1]/div/p[2]/a/text()").toString().trim();
            articleURL = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[1]/div/p[2]/").$("a", "href").toString().trim();
            questionURL = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[2]/div/p[2]/").$("a", "href").toString().trim();
            questionTitle = html.xpath("//*[@id=\"main-container\"]/div[1]/div[2]/div/div/div[2]/div/p[2]/a/text()").toString().trim();
            String imageName = imageURL.split("/")[3] + ".jpg";
            outputPath = System.getProperty("user.dir").trim() + "\\image\\" + imageName;
            ImageUtils.downLoadImg(imageURL, outputPath);
            ARTICLE_PAGE = articleURL;
            QUESTION_PAGE = questionURL;
            log.info("type: " + type + " date: " + date + " " + day + " word: " + word);
            log.info("article: " + articleTitle + " " + articleURL);
            log.info("questionURL: " + questionTitle + " " + questionURL);
        }
        // add article page
        page.addTargetRequest(articleURL);
        // article
        if (ARTICLE_PAGE.equals(page.getUrl().toString())) {
            String articleBody = page.getHtml().regex("<body[^>]*>([\\s\\S]*)<\\/body>").toString();
            Html html = Html.create(articleBody);
            articleAbstract = html.xpath("//*[@id=\"main-container\"]/div/div/div/div/div[1]/div/div/text()").toString().trim();
            articleTitle = html.xpath("//*[@id=\"main-container\"]/div/div/div/div/h2/text()").toString().trim();
            articleAuthor = html.xpath("//*[@id=\"main-container\"]/div/div/div/div/p[1]/text()").toString().trim();
            articleContent = html.xpath("//*[@id=\"main-container\"]/div/div/div/div/div[2]").toString().trim().replace("\"", "").replace("\n", "");
        }
        // add question page
        page.addTargetRequest(questionURL);
        // question
        if (QUESTION_PAGE.equals(page.getUrl().toString())) {
            String questionBody = page.getHtml().regex("<body[^>]*>([\\s\\S]*)<\\/body>").toString();
            Html html = Html.create(questionBody);
            String title = html.xpath("//*[@id=\"main-container\"]/div/div/div/div/h4[1]/text()").toString().trim();
            String questionAbstract = html.xpath("//*[@id=\"main-container\"]/div/div/div/div/h4[1]/text()").toString().trim();
            String questionContent = html.xpath("//*[@id=\"main-container\"]/div/div/div/div/div[4]").toString().trim().replace("\"", "").replace("\n", "");
            String sql = "INSERT INTO t_one VALUES (NULL,\"" + type + "\", \"" + date + "\", \"" + word + "\", \"" + outputPath + "\",\"" + articleTitle + "\",\"" + articleAbstract + "\",\"" + articleAuthor + "\",\"" + articleContent + "\",\"" + questionTitle + "\",\"" + questionAbstract + "\",\"" + questionContent + "\",CURRENT_TIMESTAMP);";
            int count = 0;
            try {
                assert stmt != null;
                count = stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (count == 1) {
                log.info("insert successful: " + sql);
            } else {
                log.info("insert failure: " + sql);
            }

        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBUtils.createTable();
        String url = "http://wufazhuce.com/";
        Spider.create(new OneStoryPageProcessor()).addUrl(url).thread(1).run();
    }
}
