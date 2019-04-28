package com.room.service;

import com.room.common.R;
import com.room.entity.UserTokenEntity;

public interface UserTokenService {

    UserTokenEntity queryByUserId(Long userId);

    void save(UserTokenEntity tokenEntity);

    void update(UserTokenEntity tokenEntity);

    R createToken(long userId);
}
