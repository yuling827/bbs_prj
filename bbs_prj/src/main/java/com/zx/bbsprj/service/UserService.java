package com.zx.bbsprj.service;

import com.zx.bbsprj.test.User;

/**
 * 测试Redis，可删除该接口
 */
public interface UserService {

    public User getUser(String username);

}
