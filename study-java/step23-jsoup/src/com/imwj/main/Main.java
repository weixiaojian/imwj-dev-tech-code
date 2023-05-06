package com.imwj.main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author wj
 * @create 2023-05-06 15:40
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String html = "<html><body><p>Hello HTML</p></body></html>";
        Document doc = Jsoup.parse(html);

        Elements as= doc.getElementsByTag("p");
        for (Element e : as) {
            System.out.println(e.text());
        }


        String url = "http://www.baidu.com";
        Document doc3 = Jsoup.parse(new URL(url),5000); //超过5秒就报错
        System.out.println("基于URL方式得到的 Document:\r\n"+ doc3);
    }
}
