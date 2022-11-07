package com.example.poiprj.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@NoArgsConstructor
public class Librarys {

    @Id
    private Long libcode;

    @Column
    private String libName;

    @Column
    private String address;

    @Column
    private String tel;

    @Column
    private String fax;

    @Column
    private float latitude;

    @Column
    private float longitude;

    @Column
    private String homepage;

    @Column
    private String closed;

    @Column
    private Long BookCount;

    @Column
    private Date operatingTime;

    @Builder
    public Librarys(Long libcode, String libName, String address, String tel, String fax, float latitude, float longitude, String homepage, String closed, Long bookCount, Date operatingTime) {
        this.libcode = libcode;
        this.libName = libName;
        this.address = address;
        this.tel = tel;
        this.fax = fax;
        this.latitude = latitude;
        this.longitude = longitude;
        this.homepage = homepage;
        this.closed = closed;
        BookCount = bookCount;
        this.operatingTime = operatingTime;
    }
}
