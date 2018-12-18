package com.zx.bbsprj.service.impl;

import com.zx.bbsprj.repository.UserRepository;
import com.zx.bbsprj.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean registerUser(com.zx.bbsprj.entity.User user) {
        //根据账号查询系统用户
        com.zx.bbsprj.entity.User userInfo = userRepository.findByUsername(user.getUsername());
        if (userInfo!=null) {
            //系统用户已存在
            return false;
        } else {
            //系统用户不存在，可新增
            com.zx.bbsprj.entity.User save = userRepository.save(user);
            System.out.println(save);
            return true;
        }
    }

    @Override
    public Long countByUsername(String username) {
        return userRepository.countByUsername(username);
    }

    @Override
    public com.zx.bbsprj.entity.User loginCheck(String username, String password) {
        return userRepository.findByUsernameAndPassword(username,password);
    }

}
