package sample.model;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import java.io.*;

public class UrlDownloader {
    public String getByUrl(String url) throws IOException {
        String str;
        Elements elements = Jsoup
                .connect(url)
                .get()
                .getElementsByClass("details")
                .select("th");

        return elements.toString();
    }
}
