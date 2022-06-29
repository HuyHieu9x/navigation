package com.techvify.navigation.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "navigate")
@SQLDelete(sql = "UPDATE navigate SET deleted = true WHERE id = ?")

@FilterDef(name = "deletedNavigateFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedNavigateFilter", condition = "deleted = :isDeleted")

public class Navigate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "flag")
    private boolean flag;

    @Column(name = "link")
    private String link;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date created_at;

    public Navigate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
