package com.el.opu.carsup.api;

import org.springframework.web.bind.annotation.GetMapping;

public interface RetrieverClient {

    @GetMapping(path = "/getMainPage")
    String getIaaiPage();

    @GetMapping(path = "/getItemPage")
    String getItemPage();
}
