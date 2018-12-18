package com.zx.bbsprj.repository;

import com.zx.bbsprj.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//有多条件查询，接口还需要实现JpaSpecificationExecutor接口，不同的多条件查询可参考该接口的5个方法
public interface ArticleRepository extends JpaRepository<Article,Integer>,JpaSpecificationExecutor<Article> {
}
