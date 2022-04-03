package com.el.opu.carsup.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
public class CarPageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;
    private Long lastQueriedTimestamp;

    @JsonBackReference
    @OneToOne(mappedBy = "url", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Car car;
}
