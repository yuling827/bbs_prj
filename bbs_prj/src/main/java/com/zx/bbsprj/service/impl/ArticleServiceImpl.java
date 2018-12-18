package com.zx.bbsprj.service.impl;

import com.zx.bbsprj.repository.ClickRateRepository;
import com.zx.bbsprj.service.ArticelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleServiceImpl implements ArticelService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClickRateRepository clickRateRepository;

    /**
     * 当前IP用户在当天：
     * 没有点击记录-->保存当前点击记录，点击量+1
     * 有点击记录-->不做任何处理
     * @param id
     * @param ip
     * @param time
     * @return
     */
    public Boolean isVistor(Integer id, String ip, Date time) {
        Long num = queryCountById(id,ip,time);
        if (num!=0) {
            //有过点击记录
            logger.info("当前IP用户--"+ip+"，在"+time+"对编号为："+id+"的博客已经有过点击记录...不再做任何处理...");
            return true;
        } else {
            //没有点击记录
            logger.info("当前IP用户--"+ip+",在"+time+"对编号为："+id+"的博客点击一次...");
            com.zx.bbsprj.entity.ClickRate clickRate = new com.zx.bbsprj.entity.ClickRate();
            clickRate.setAid(id);
            clickRate.setIp(ip);
            clickRate.setTime(time);
            com.zx.bbsprj.entity.ClickRate save = clickRateRepository.save(clickRate);
            System.out.println(save.getId());
            return false;
        }
    }

    @Override
    public Long queryCountById(Integer id, String ip, Date time) {
        com.zx.bbsprj.entity.ClickRate clickRate = new com.zx.bbsprj.entity.ClickRate();
        clickRate.setAid(id);
        clickRate.setIp(ip);
        clickRate.setTime(time);
        Example<com.zx.bbsprj.entity.ClickRate> example = Example.of(clickRate);
        return clickRateRepository.count(example);
    }

}
