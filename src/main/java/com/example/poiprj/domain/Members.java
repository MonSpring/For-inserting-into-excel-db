//package com.example.poiprj.domain;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.cfg.AvailableSettings;
//import org.hibernate.id.enhanced.SequenceStyleGenerator;
//import org.hibernate.annotations.Parameter;
//
//import javax.persistence.*;
//
//@Getter
//@Entity
//@NoArgsConstructor
//public class Members {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String email;
//
//    @Builder
//    public Members(String name, String email) {
//        this.name = name;
//        this.email = email;
//    }
//}
