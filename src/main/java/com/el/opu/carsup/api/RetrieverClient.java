package com.el.opu.carsup.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface RetrieverClient {

    @GetMapping(path = "/Search?url=%2fphPQHAwgKKUMQESmxpuQ98DIa4rE6zo0cNZ06cVu%2fA%3d", consumes = MediaType.TEXT_HTML_VALUE)
    String getIaaiPage();

    @GetMapping(path = "/{linkToCar}", consumes = MediaType.TEXT_HTML_VALUE)
    String getCar(@PathVariable String linkToCar);
}
