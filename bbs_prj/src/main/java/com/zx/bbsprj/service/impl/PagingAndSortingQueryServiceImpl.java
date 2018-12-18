package com.zx.bbsprj.service.impl;

import com.zx.bbsprj.entity.Article;
import com.zx.bbsprj.entity.Critique;
import com.zx.bbsprj.repository.ArticleRepository;
import com.zx.bbsprj.repository.CritiqueRepository;
import com.zx.bbsprj.service.PagingAndSortingQueryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PagingAndSortingQueryServiceImpl implements PagingAndSortingQueryService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CritiqueRepository critiqueRepository;

    @Override
    public Page<Article> findAll(Article article, Pageable pageable) {
        Page<Article> articlePage = articleRepository.findAll((Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            //多个条件参与where过滤查询
            List<Predicate> list = new ArrayList<>();
            //拼接查询条件：
            if (article!=null) {
                //精确查询
                if (StringUtils.isNotBlank(article.getUsername())) {
                    list.add(criteriaBuilder.equal(root.get("username").as(String.class),article.getUsername()));
                }
//                模糊查询
//                if (article.getContent()!=null) {
//                    list.add(criteriaBuilder.like(root.get("content").as(String.class),"%"+article.getContent()+"%"));
//                }
            }
            Predicate[] predicates = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(predicates));
        },pageable);
        return articlePage;
    }

    @Override
    public Page<Critique> findAll(Critique critique, Pageable pageable) {
        Page<Critique> critiquePage = critiqueRepository.findAll((Specification<Critique>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            //文章id作为where过滤条件
            if (critique!=null) {
                //精确查询
                if (critique.getAid()!=null) {
                    list.add(criteriaBuilder.equal(root.get("aid").as(Integer.class),critique.getAid()));
                }
            }
            Predicate[] predicates = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(predicates));
        },pageable);
        return critiquePage;
    }
}
