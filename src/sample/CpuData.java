package sample;

import org.jsoup.select.Elements;

public class CpuData {
    private String name;
    private Elements elements;

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
