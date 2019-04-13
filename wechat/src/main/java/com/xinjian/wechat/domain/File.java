package com.xinjian.wechat.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "FILE")
public class File implements Serializable {

    @Id
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_SEQ")
    @SequenceGenerator(name = "FILE_SEQ", sequenceName = "FILE_SEQ", initialValue = 1)
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

    @Column(name="UPLOAD_DATE")
    @NotNull
    @CreatedDate
    private Date uploadDate;

}
