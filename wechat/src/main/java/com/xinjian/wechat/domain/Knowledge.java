package com.xinjian.wechat.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * knowledge class
 */
@Data
@Entity
@Table(name = "KNOWLEDGE")
public class Knowledge implements Serializable {

	@Id
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KNOWLEDGE_SEQ")
    @SequenceGenerator(name = "KNOWLEDGE_SEQ", sequenceName = "KNOWLEDGE_SEQ", initialValue = 1)
    private Long id;

	@Column(name = "KNOWLEDGENAME", length = 50, unique = true)
    @NotNull
    @Size(min = 1, max = 50)
    private String knowledgeName;

    @Column(name = "sequence" )
	private int sequence;

	@Column(name = "KNOWLEDGECONTENT", length = 300)
    @NotNull
    @Size(min = 0, max = 300)
    private String knowledgeContent;

    @Column(name="LOCATION", length=100)
    @NotNull
    private String location;


    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="FILE")
	@JoinColumn(name="id")
    private List<File> fileList = new ArrayList();
}
