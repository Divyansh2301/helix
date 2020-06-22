package tech.codingclub.helix.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tech.codingclub.helix.global.HttpURLConnectionExample;

import java.util.Date;

public class WikipediaDownloader{
    private String keyword;

    public WikipediaDownloader() {

    }

    public WikipediaDownloader(String keyword) {
        this.keyword = keyword;
    }

    public WikiResult getResult() {
        //1.Get clean keyword
        //2.Get the url of wikipedia
        //3.Make a GET request to wikipedia
        //4.Parse the useful results using jsoup
        //5.Showing results
        if (this.keyword == null || this.keyword.length() == 0) {
            return null;
        }
        //STEP1
        this.keyword = this.keyword.trim().replaceAll("[ ]", "_");//trim removes spaces from beginning and end os string & if want to remove all continuous spaces so put it in[]

        //STEP2
        String wikiUrl = getWikipediaUrlForQuery(this.keyword);

        String response = "";
        String imageUrl = null;
        try {
            //STEP3
            String wikipediaResponseHTML = HttpURLConnectionExample.sendGet(wikiUrl);
            //System.out.println(wikipediaResponseHTML);

            //STEP4
            Document document = Jsoup.parse(wikipediaResponseHTML, "https://en.wikipedia.org/wiki/");

            //STEP5
            Elements childElements = document.body().select(".mw-parser-output > *");
            int state = 0;
            for (Element childelement : childElements) {
                if (state == 0) {
                    if (childelement.tagName().equals("table")) {
                        state = 1;
                    }

                } else if (state == 1) {
                    if (childelement.tagName().equals("p")) {
                        state = 2;
                        response = childelement.text();
                        break;
                    }
                }

                try {
                    imageUrl = document.body().select(".infobox img").get(0).attr("src");//img tag me 2 images aayegi get(index) unme se ek ko get krega attr() use images at source link laayega
                } catch (Exception ex) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        WikiResult wikiResult = new WikiResult(this.keyword, response, imageUrl);
        //Push result into database

        return wikiResult;
    }

    private String getWikipediaUrlForQuery(String cleanKeyword) {
        return "https://en.wikipedia.org/wiki/" + cleanKeyword;
    }
}