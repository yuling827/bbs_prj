package com.zx.bbsprj.test;

import java.io.Serializable;

/**
 * 测试Redis，可删除该javabean
 */
public class User implements Serializable {

    private static final long serialVersionUID = -1L;

    private String username;
    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}
