package com.zx.bbsprj.service.impl;

import com.zx.bbsprj.service.UserService;
import com.zx.bbsprj.test.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 测试Redis，可删除该类
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    @Cacheable(value = "user",key = "'user_'+#username")
    public User getUser(String username) {
        System.out.println(username+"进入实现类获取数据");
        return new User("Tom",22);
    }
}
