package com.zx.bbsprj.repository;

import com.zx.bbsprj.entity.BlogInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogInfoRepository extends JpaRepository<BlogInfo,Integer> {

    /**
     * 根据用户名查询个性化信息
     * @param username
     * @return
     */
    BlogInfo findByUsername(String username);

}
