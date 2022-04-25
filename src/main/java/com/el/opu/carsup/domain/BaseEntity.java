package com.el.opu.carsup.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @LastModifiedDate
    private Date updated;
    @CreatedDate
    @Column(updatable = false)
    private Date created;
    @Enumerated(EnumType.STRING)
    private Status status;
}
