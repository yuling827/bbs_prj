package com.zx.bbsprj;

import com.zx.bbsprj.service.UserService;
import com.zx.bbsprj.test.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings("unchecked")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BbsPrjApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @Test
    public void setAndGet() {
        redisTemplate.opsForValue().set("test:set","testValue1");
        Assert.assertEquals("testValue1",redisTemplate.opsForValue().get("test:set"));
    }

    @Test
    public void setAndGetUser() {
        User user = new User("Tom",10);
        redisTemplate.opsForValue().set("test:setUser",user);
        Assert.assertEquals(user.getUsername(),((User)redisTemplate.opsForValue().get("test:setUser")).getUsername());
    }

    /**
     * 从Redis缓存中获取数据
     */
    @Test
    public void contextLoads() {
        User user;
        user = userService.getUser("Tom");
        user = userService.getUser("Tom");
        user = userService.getUser("Tom");
    }

}
