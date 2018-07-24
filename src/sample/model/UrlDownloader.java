package sample.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sample.CpuData;

import java.io.*;
import java.util.Arrays;

public class UrlDownloader {
    public String getByUrl(String url) throws IOException {
        Elements elements = Jsoup
                .connect(url)
                .get()
                .getElementsByClass("cpuname");
        //.select("td");

        return Arrays.toString(elements.eachText().toArray());
    }

    public CpuData getCpuByUrl(String url) throws IOException {
        CpuData cpu = new CpuData();

        Document doc = Jsoup
                .connect(url)
                .get();

        cpu.setElements(doc.getElementsByClass("details").select("td"));
        cpu.setName(doc.getElementsByClass("cpuname").first().html());
        return cpu;
    }
}
