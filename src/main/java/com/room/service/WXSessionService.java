package com.room.service;

import java.util.Map;

public interface WXSessionService {

    void save(Map<String,Object> params);

    String getOpenIdBySessionId(String sessionId);

    String getSessionIdByOpenId(String openId);

    void update(Map<String, Object> params);
}
