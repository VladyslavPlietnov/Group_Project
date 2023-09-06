package org.example.Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CityParser {
    private static final String[] URLS = {
            "https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%84%D0%B2%D1%80%D0%BE%D0%BF%D0%B8_%D0%B7_%D0%BD%D0%B0%D1%81%D0%B5%D0%BB%D0%B5%D0%BD%D0%BD%D1%8F%D0%BC_%D0%BF%D0%BE%D0%BD%D0%B0%D0%B4_500_%D1%82%D0%B8%D1%81%D1%8F%D1%87_%D0%BE%D1%81%D1%96%D0%B1",
            "https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B8_(%D1%81%D0%BF%D0%B8%D1%81%D0%BE%D0%BA)",
            "https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0-%D0%BC%D1%96%D0%BB%D1%8C%D0%B9%D0%BE%D0%BD%D0%BD%D0%B8%D0%BA%D0%B8_%D1%81%D0%B2%D1%96%D1%82%D1%83"
    };

    public Set<String> parseCities() {
        Set<String> cities = new HashSet<>();
        for (String url : URLS) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements rows = document.select("table.wikitable tbody tr");
                for (Element row : rows) {
                    Elements tds = row.select("td");
                    if (tds.size() > 1) {
                        String cityName = tds.get(1).text();
                        cityName = cityName.replaceAll("[^а-яА-ЯїЇіІєЄ]+", "");  // очищаємо назву від всіх символів, крім українських букв
                        cities.add(cityName);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cities;
    }
}

