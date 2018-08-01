package com.xinjian.wechat.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * user class
 */
@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable{


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", initialValue = 4)
    private Long id;

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_AUTHORITY", joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private List<Authority> authorities;

}