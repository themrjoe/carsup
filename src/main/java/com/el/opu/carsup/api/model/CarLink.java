package com.el.opu.carsup.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CarLink {

    private String htmlCarDataPage;
    private String link;
}
