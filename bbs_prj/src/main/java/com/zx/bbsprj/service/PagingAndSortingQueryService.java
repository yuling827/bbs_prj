package com.zx.bbsprj.service;

import com.zx.bbsprj.entity.Article;
import com.zx.bbsprj.entity.Critique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingAndSortingQueryService {

    Page<Article> findAll(Article article, Pageable pageable);

    Page<Critique> findAll(Critique critique,Pageable pageable);

}
