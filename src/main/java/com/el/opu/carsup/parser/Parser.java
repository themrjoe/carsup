package com.el.opu.carsup.parser;

import com.el.opu.carsup.domain.CarPageInfo;
import com.el.opu.carsup.service.CarPageService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Parser {

    private final CarPageService carPageService;

    public void parseMainPage(String page) {
        Document document = Jsoup.parse(page);
        Elements links = document.select("h4.heading-7.rtl-disabled > a");
        List<CarPageInfo> pages =
                links.stream()
                        .map(link -> link.attr("href"))
                        .map(this::mapToCarPageInfo)
                        .collect(Collectors.toList());
        carPageService.savePages(pages);
    }

    public void parseCarPage(String page) {
        Document document = Jsoup.parse(page);

    }

    private CarPageInfo mapToCarPageInfo(String link) {
        return new CarPageInfo().setUrl(link);
    }
}
