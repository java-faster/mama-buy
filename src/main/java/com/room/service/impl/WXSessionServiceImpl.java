package com.room.service.impl;

import com.room.dao.room.WXSessionDao;
import com.room.service.WXSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("wxSessionService")
public class WXSessionServiceImpl implements WXSessionService {
    @Autowired
    private WXSessionDao wxSessionDao;

    @Override
    public void save(Map<String,Object> params) {
        wxSessionDao.save(params);
    }

    @Override
    public String getOpenIdBySessionId(String sessionId) {
//        Map map = new HashMap();
//        map.put("sessionId",sessionId);
        return wxSessionDao.selectOpenId(sessionId);
    }

    @Override
    public String getSessionIdByOpenId(String openId) {
        return wxSessionDao.selectSessionId(openId);
    }

    @Override
    public void update(Map<String, Object> params) {
        wxSessionDao.update(params);
    }
}
