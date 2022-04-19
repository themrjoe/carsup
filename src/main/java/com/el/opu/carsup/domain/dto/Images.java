package com.el.opu.carsup.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Images {

    List<Img> values;
}
