package sample.model;

import io.reactivex.Flowable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sample.CpuData;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class UrlDownloader {
    public String getByUrl(String url) throws IOException {
        Elements elements = Jsoup
                .connect(url)
                .get()
                .getElementsByClass("cpuname");
        //.select("td");

        return Arrays.toString(elements.eachText().toArray());
    }

    public Flowable<CpuData> getCpuByUrl(List<String> url){
        return Flowable.fromArray(url)
                .flatMap(url1->Flowable.fromIterable(url1).map(url2->{
                    CpuData cpu = new CpuData();

                    Document doc = Jsoup
                            .connect(url2)
                            .ignoreHttpErrors(true)
                            .get();
                    try {
                        cpu.setElements(doc.getElementsByClass("details").select("td"));
                        cpu.setName(doc.getElementsByClass("cpuname").first().html());
                    }catch (NullPointerException e){
                        cpu.setElements(doc.getAllElements());
                        cpu.setName(url2);
                    }

                    return cpu;
                }));
    }
}
