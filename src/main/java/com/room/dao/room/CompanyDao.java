package com.room.dao.room;

import com.room.dao.BaseDao;
import com.room.entity.ComListEntity;
import com.room.entity.CompanyEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyDao extends BaseDao<CompanyEntity>{
    //根据公司id查询邀请码
    String selectInviteCode(int companyId);

    Integer checkInvite(String inviteCode);

    void updateInviteById(String inviteCode);


}
