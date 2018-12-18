package com.zx.bbsprj.service;

import java.util.Date;

public interface ArticelService {

    /**
     * 查询当前IP用户在当天有没有点击记录
     * @param id
     * @param ip
     * @param time
     * @return
     */
    Long queryCountById(Integer id,String ip,Date time);

    /**
     * 当前IP用户在当天：
     * 没有点击记录-->保存当前点击记录，点击量+1
     * 有点击记录-->不做任何处理
     * @param id
     * @param ip
     * @param time
     * @return
     */
    Boolean isVistor(Integer id, String ip, Date time);

}
