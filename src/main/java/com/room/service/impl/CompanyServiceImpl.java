package com.room.service.impl;

import com.room.dao.room.CompanyDao;
import com.room.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyDao companyDao;

    @Override
    public String selectInviteCode(int companyId) {
        return companyDao.selectInviteCode(companyId);
    }

}
