package com.el.opu.carsup.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface RetrieverClient {

    @GetMapping(path = "/Search?url={page}", consumes = MediaType.TEXT_HTML_VALUE)
    String getIaaiPage(@PathVariable String page);

    @GetMapping(path = "/{linkToCar}", consumes = MediaType.TEXT_HTML_VALUE)
    String getCar(@PathVariable String linkToCar);
}
