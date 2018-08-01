package com.xinjian.wechat.domain;

import com.xinjian.wechat.util.AuthorityName;
import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "AUTHORITY")
public class Authority {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHORITY_SEQ")
    @SequenceGenerator(name = "AUTHORITY_SEQ", sequenceName = "AUTHORITY_SEQ", initialValue = 3)
    private Long id;

    @Column(name = "AUTHORITY_NAME", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

//    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
//    private List<User> users;


//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
}