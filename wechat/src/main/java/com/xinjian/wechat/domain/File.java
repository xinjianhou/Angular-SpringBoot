package com.xinjian.wechat.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "FILES")
public class File implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILES_SEQ")
    @SequenceGenerator(name = "FILES_SEQ", sequenceName = "FILES_SEQ", initialValue = 1)
    private Long id;

    @Column(name="FILE_NAME",length=30)
    @NotNull
    private String fileName;

    @Column(name="FILE_SIZE")
    @NotNull
    private Long fileSize;

    @Column(name="LOCATION", length=100)
    @NotNull
    private String location;

}
