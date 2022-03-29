package com.el.opu.carsup.service.resolver;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldResolver {

    public String resolveBrandName(String title) {
        return title.substring(5);
    }

    public String resolveCarYear(String title) {
        return title.subSequence(0, 4).toString();
    }

    public String resolveField(Elements uls, String field) {
        Elements li = uls.select("li.data-list__item");
        return Optional.ofNullable(li).stream()
                .flatMap(Elements::stream)
                .filter(element -> element.select("span.data-list__label").first().text().equals(field))
                .map(element -> element.select("span.data-list__value").first().text())
                .findFirst()
                .orElse(null);
    }

    public String resolveAuctionDateTime(Elements uls) {
        Elements li = uls.select("li.data-list__item");
        Element href = li.select("a.data-list__value.link").first();
        return href.text();
    }

    public String resolvePrice(String price) {
        return price.substring(1).replace(",", "");
    }

    public String resolveOdometerValue(String odometer) {
        return odometer.replaceAll("[^\\d]","");
    }
}
