package com.zx.bbsprj.entity;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sys_article")
public class Article {

    //作为主键，也作为文章id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;

    //使用如下注解可以映射mysql数据表的text类型字段
    @Lob    //声明属性对应的数据库字段为大文本类型，可以存放大的数据
    @Type(type = "text")
    @Column
    private String content;
    @Column(name = "user_name",length = 50)
    private String username;

    //定义创建日期的格式
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column
    private Integer hasread;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getHasread() {
        return hasread;
    }

    public void setHasread(Integer hasread) {
        this.hasread = hasread;
    }
}
