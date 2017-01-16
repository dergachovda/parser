package ua.org.ecity;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class parser {
    private static final String URL = "https://ru.wikipedia.org/wiki/";

    private static final String REGION = "Область";
    private static final String ESTABLISHMENT = "Основан";
    private static final String POPULATION = "Население";
    private static final String GPS = "Координаты";

    public static void main(String[] args) throws IOException {

        String region;
        String establishment;
        String population;
        String gps;

        List<String> cities = Arrays.asList(
                "Авдеевка",
                "Александрия_(Кировоградская_область)",
                "Александровск",
                "Алёшки",
                "Алмазная"
        );

        for (String city : cities) {

            region = "";
            establishment = "";
            population = "";
            gps = "";

            Document doc = Jsoup.connect(URL + city).get();

            String title = doc.title();
            System.out.println(title);

            Elements vcard = doc.select("table.infobox.vcard");
            Elements tr = vcard.select("tr");
            for (Element el : tr) {
                String[] strs = el.text().split(" ", 2);

                if (strs[0].equals(REGION)) {
//                    System.out.println(Arrays.toString(strs));
                    region = strs[1].split(" ")[0];
                    int index = region.indexOf(region.substring(0, 2), 1);
                    if (index > 0) {
                        region = region.substring(0, index);
                    }
                    System.out.println(REGION + ": " + region);
                }
                if (strs[0].equals(ESTABLISHMENT)) {
                    establishment = strs[1];
                    System.out.println(ESTABLISHMENT + ": " + establishment);
                }
                if (strs[0].equals(POPULATION)) {
                    String[] s = strs[1].split("\\[")[0].split("\\D+");
                    for (String ss : s) {
                        population += ss;
                    }
                    System.out.println(POPULATION + ": " + population);
                }
            }
        }
    }
}
