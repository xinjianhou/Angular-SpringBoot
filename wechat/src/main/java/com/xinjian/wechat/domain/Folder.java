package com.xinjian.wechat.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "FOLDER")
public class Folder implements Serializable {

    @Id
    @Column(name = "ID")
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FOLDER_SEQ")
    @SequenceGenerator(name = "FOLDER_SEQ", sequenceName = "FOLDER_SEQ", initialValue = 1)
    private Long id;


    @Column(name="NAME",length=30,unique = true)
    private String fileName;


    @Column(name="CONTENT",length=300)
    private String content;


    @Column(name="LOCATION", length=100)
    @NotNull
    private String location;

}
