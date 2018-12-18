package com.zx.bbsprj.repository;

import com.zx.bbsprj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//类似于第一版的UserDao，在JPA中可以意会Repository替换Dao
public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * 根据账户名查询系统用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 登录时校验账号、密码
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username,String password);

    /**
     * 查询账户的唯一性
     * @param username
     * @return
     */
    Long countByUsername(String username);

}
