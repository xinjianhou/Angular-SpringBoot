package com.xinjian.wechat.domain;

import com.xinjian.wechat.util.AuthorityName;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "authority")
public class Authority {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "AUTHORITY_NAME", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;


}