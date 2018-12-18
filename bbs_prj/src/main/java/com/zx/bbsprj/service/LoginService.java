package com.zx.bbsprj.service;

public interface LoginService {

    /**
     * v2.0
     * 使用Spring Data JPA对单表进行CRUD操作
     * 注册新账号
     * @param user
     * @return
     */
    Boolean registerUser(com.zx.bbsprj.entity.User user);

    /**
     * v2.0
     * 使用Spring Data JPA对单表进行CRUD操作
     * 校验账号的唯一性
     * @param username
     * @return
     */
    Long countByUsername(String username);

    /**
     * v2.0
     * 使用Spring Data JPA对单表进行CURD操作
     * @param username
     * @param password
     * @return
     */
    com.zx.bbsprj.entity.User loginCheck(String username,String password);

}
