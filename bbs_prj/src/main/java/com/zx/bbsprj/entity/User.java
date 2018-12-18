package com.zx.bbsprj.entity;

import javax.persistence.*;

@Entity //告诉JPA该类是与数据表映射的实体类--映射数据库的系统用户表
@Table(name = "sys_user_info")  //指定映射数据表的表名
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name",length = 50)
    private String username;

    @Column(name = "password",length = 50)
    private String password;

    @Column(name = "nick_name",length = 50)
    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
