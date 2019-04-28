package com.room.service;

public interface CompanyService {
    //根据公司id和公司名称查询邀请码
    String selectInviteCode (int companyId);
}
