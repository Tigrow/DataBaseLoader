package sample.model;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sample.CpuData;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class UrlDownloader {
    public String getByUrl(String url) {
        try (Scanner scanner = new Scanner(new URL(url).openStream(),
                StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (Exception e) {
            return "";
        }
    }

    public Flowable<CpuData> getCpuByUrl(List<String> urls) {
        return Flowable.fromIterable(urls)
                .flatMap(url -> Flowable.just(url)
                        .subscribeOn(Schedulers.newThread())
                        .map(url1 -> {
                            CpuData cpu = new CpuData();
                            Document doc = Jsoup
                                    .connect(url1)
                                    .ignoreHttpErrors(true)
                                    .timeout(0)
                                    .get();
                            try {
                                cpu.setElements(doc.getElementsByClass("details").select("td"));
                                cpu.setName(doc.getElementsByClass("cpuname").first().html());
                            } catch (NullPointerException e) {
                                cpu.setElements(doc.getAllElements());
                                cpu.setName(url1);
                            }

                            return cpu;
                        }));
    }
}
