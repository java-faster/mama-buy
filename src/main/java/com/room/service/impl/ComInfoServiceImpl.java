package com.room.service.impl;

import com.room.common.R;
import com.room.dao.ledger.UserDao;
import com.room.entity.ComListEntity;
import com.room.service.ComInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ComInfoService")
public class ComInfoServiceImpl implements ComInfoService {

    @Autowired
    private UserDao userDao;

    @Override
    public R createComInfo(ComListEntity comListEntity) {
        userDao.insertComInfo(comListEntity);
        return R.ok("succ");
    }
}
