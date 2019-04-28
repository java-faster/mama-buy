package com.room.service.impl;

import com.room.common.R;
import com.room.dao.ledger.UserDao;
import com.room.dao.room.CompanyDao;
import com.room.entity.UserEntity;
import com.room.service.UserService;
import com.room.service.WXSessionService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private WXSessionService wxSessionService;

    @Override
    public Map<String, Object> userLoginFirst(String sessionId) {
        //用户是否登录过小程序
        String wxId = wxSessionService.getOpenIdBySessionId(sessionId);
        if (wxId==null || "".equals(wxId))
            return R.error("小程序openId不合法");

        Map<String,Object> map = new HashMap<>();
        map.put("wxId",wxId);
        UserEntity userEntity = userDao.queryUserName(map);
        if (userEntity==null)
            return new HashMap<>();

        Map<String, Object> result = new HashMap<>();
        result.put("userName", userEntity.getUserName());
        result.put("userId",userEntity.getUserId());
        return result;
    }

//    @Override
//    public R login(Map<String, Object> params) {
//        //根据用户名查询用户信息
//        Map<String,Object> map = new HashMap<>();
//        map.put("userName",params.get("userName"));
//        UserEntity user = userDao.queryUserName(map);
//        //账号不存在、密码错误
//        if (user==null)   //user==null说明user表里没有这条数据，需要把这条数据保存在user表里新建一条记录
//            return R.error("用户不存在").put("userId","");
////              userDao.addNewUser(params);
//        //若用户表中密码为空
//        if (user.getPassword()==null || "".equals(user.getPassword().trim()))
//            return R.error(2,"修改密码").put("userId","");
//
//        if (!user.getPassword().equals(new Sha256Hash(params.get("password").toString(), user.getSalt()).toHex()))
//            return R.error("密码错误").put("userId","");
//
//        //验证邮箱
//        /*if (params.get("email")==null || "".equals(params.get("email").toString().trim()))
//            return R.error("邮箱不能为空");
//
//        if (params.get("email") != user.getEmail())
//            return R.error("邮箱错误");*/
//
//        //账号锁定
//        if (user.getDeleted() == 1) {
//            return R.error("已锁定联系管理员").put("userId","");
//        }
//        //更新用户的微信id
//        String wxId = wxSessionService.getOpenIdBySessionId(params.get("sessionId").toString());
//        if (wxId==null || "".equals(wxId))
//            return R.error("id不合法").put("userId","");
//        user.setWxId(wxId);
//        userDao.update(user);
//        //创建token
////        return userTokenService.createToken(user.getUserId());
//        return R.ok().put("userId",user.getUserId());
//    }

//    @Override
//    public R login(Map<String, Object> params) {
//        //根据用户名查询用户信息
//        Map<String,Object> map = new HashMap<>();
//        String name = (String) params.get("userName");
//        map.put("userName",name);
//        UserEntity user = userDao.queryUserName(map);
////        //user==null说明user表里没有这条数据，需要把这条数据保存在user表里新建一条记录
//        if (user==null) {
//            UserEntity entity = new UserEntity();
//            if (null != params && params.size() > 0)
////                entity.setUserId((Integer) params.get("userId"));
//            entity.setUserName((String) params.get("userName"));
//            entity.setDeleted(0);
//            entity.setLoginState(0);
////            if(userDao.existUser((String) params.get("userName")) > 0) {
////                return R.error("用户名已存在");
////            }
//            //插入数据
//            userDao.addNewUser(entity);
//            user = entity;
//        }
////        //账号锁定
//        if (null != user && user.getDeleted() == 1) {
//            return R.error("已锁定联系管理员").put("userId","");
//        }
////        //更新用户的微信id
//        String wxId = wxSessionService.getOpenIdBySessionId(params.get("sessionId").toString());
////        if (wxId==null || "".equals(wxId))
////            return R.error("id不合法").put("userId","");
////        if(null != user && user.getWxId().length() == 0) {
////            user.setWxId(wxId);
////        }
//        String open_id = userDao.selectOpenId(params.get("userName").toString());
////        userDao.update(user);
//        String inviteCode = companyService.selectInviteCode(1);
//        String password = (String) params.get("password");
//        if(!inviteCode.equals(password)) {
//            return R.error("邀请码有误");
//        }
////        if (null != open_id && wxId.equals(open_id)) {
//        //根据userName查询登录状态，如果为1则提示此账号已占用
//        if(userDao.selectLoginState(name) != 1) {
//            userDao.updateLoginStateByName(name);
//            return R.ok().put("userId", user.getUserId());
//        }
//        else {
//            return R.error("此账号已被占用");
//        }
//        //创建token
////        return userTokenService.createToken(user.getUserId());
////        System.out.println(userDao.selectOpenId());
////        map.put("userId",user.getUserId());
////        String open_id = userDao.selectOpenId(params.get("userName").toString());
////        if(null != open_id && open_id.length() <= 0){
////            UserEntity tmp_user = new UserEntity();
////            tmp_user.setWxId(wxId);
////            tmp_user.setUserName(params.get("userName").toString());
////            tmp_user.setDeleted(0);
////            userDao.addNewUser(tmp_user);
////            return R.ok().put("userId", tmp_user.getUserId());
////        }else {
////            if (null != open_id && wxId.equals(open_id)) {
////                return R.ok().put("userId", 0);
////            } else {
////                return R.error("账号登录不正确，请重新登录");
////            }
////        }
//    }



    @Override
    public R login(Map<String, Object> params) {
        //根据openId匹配用户表里的数据，如果有数据就是老用户，没有数据就是新用户
        if (null == params || params.size() <= 0) {
//            return R.error("参数为空");
            return R.error("用户信息错误");
        }
        String userName = (String) params.get("username");//前端传的userName
        String sessionId = (String) params.get("sessionId");//前端传的sessionId

        if(null == userName || userName.length() <= 0) {
            return R.error("userName为空");
        }
        if(null == sessionId || sessionId.length() <= 0) {
//          sessionId为空
            return R.error("请授权后再操作");
        }
        if(userName.equals("管理员")) {
            return R.error("用户名不可用");
        }

        String openId = wxSessionService.getOpenIdBySessionId(sessionId);//通过sessionId拿到openId
        int num1 = userDao.checkOpenIdIsEqual(openId);//数据表里符合条件的条数
        int num2 = userDao.checkUserByName(userName);//数据表里符合条件的条数

        if (num1 > 0 && num2 > 0) {//openId和用户名都一致，老用户
            //老用户，直接进入
            //根据用户名查询用户表里的userId
            int userId = userDao.selectUserIdByName(userName);
            int role = userDao.selectRoleByUserName(userName);//根据用户名获取用户的角色
            int loginState = userDao.selectLoginState(userName);//根据用户名获取用户的登录状态
            return R.ok("登录成功").put("userId", userId).put("role", role).put("loginState", loginState);
        } else if (num1 <= 0 && num2 > 0) {//openId不一致，用户名一致，提示此用户已存在
            return R.error("此用户已存在");
        } else if (num1 > 0 && num2 <= 0) {//openId一致，用户名不一致，提示请输入正确的用户名
            return R.error("用户名有误");
        }
        else {//新用户，保存到用户表
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            //得到当前时间字符串
            String dateNowStr = sdf.format(d);
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(userName);
            userEntity.setWxId(openId);
            userEntity.setRole(1);//设置角色默认是普通
            userEntity.setLoginState(0);
            userEntity.setLogindate(dateNowStr);
            //新用户，把用户名和openId存到用户表里再登录
            userDao.save(userEntity);
            //根据用户名查询用户表里的userId
            int userId = userDao.selectUserIdByName(userName);//根据用户名获取用户id
            int role = userDao.selectRoleByUserName(userName);//根据用户名获取用户的角色
            int loginState = userDao.selectLoginState(userName);//根据用户名获取用户的登录状态
            String inviteCode =(String) params.get("inviteCode");//获取前端输入的邀请码
            if(null == inviteCode || inviteCode.length() <= 0) {
                return R.error("inviteCode为空");
            }
            int isExitUser = userDao.checkUserByName(userName);
            if(isExitUser <= 0) {
                return R.error(301, "用户不存在");
            }
            int num = companyDao.checkInvite(inviteCode);//前端发过来的邀请码匹配用户表里的邀请码,返回符合要求的数据条数
            if(num > 0) {//num>0表明输入的邀请码和公司表里查询的是一致的
                userDao.updateLoginStateByName(userName);//根据用户名修改登录状态
                return R.ok("登录成功").put("userId", userId).put("role", role).put("loginState", loginState);
            } else {
                return R.error("邀请码输入有误");
            }
//            Map map = new HashMap<String, Object>();
            //用户名检验通过并且存入数据库
        }
    }

    @Override
    public R checkInvite(Map<String, Object> params) {
        if (null == params || params.size() <= 0){
//            return R.error("参数为空");
            return R.error("用户信息错误");
        }
        String userName =(String) params.get("userName");//获取前端输入的用户名
        String inviteCode =(String) params.get("inviteCode");//获取前端输入的邀请码
        if(null == userName || userName.length() <= 0) {
            return R.error("userName为空");
        }
        if(null == inviteCode || inviteCode.length() <= 0) {
            return R.error("inviteCode为空");
        }
        int isExitUser = userDao.checkUserByName(userName);
        if(isExitUser <= 0) {
            return R.error(301, "用户不存在");
        }
        int loginState = userDao.selectLoginState(userName);
        int num = companyDao.checkInvite(inviteCode);//前端发过来的邀请码匹配用户表里的邀请码,返回符合要求的数据条数
        if(num > 0) {//num>0表明输入的邀请码和公司表里查询的是一致的
            userDao.updateLoginStateByName(userName);//根据用户名修改登录状态
            return R.ok().put("loginState", loginState);
        } else {
            return R.error("邀请码输入有误");
        }

    }

    @Override
    public R updateInvite(Map<String, String> params) {
        //判断用户的角色，管理员身份才可以进行这个操作
        String userName = params.get("userName");
        String inviteCode = params.get("newInviteCode");
        if(null == userName || userName.length() <= 0) {
            return R.error("userName为空");
        }
        if(null == inviteCode || inviteCode.length() <= 0) {
            return R.error("inviteCode为空");
        }
        int isExitUser = userDao.checkUserByName(userName);
        if(isExitUser <= 0) {
            return R.error(301, "用户不存在");
        }
        //根据用户名查询用户角色,返回用户角色标识码
        if (userName.length() > 0) {
            int role = userDao.selectRoleByUserName(userName);
            //如果role==0证明是管理员，有权限修改邀请码
            if (0 == role) {
                //根据公司id修改邀请码
                companyDao.updateInviteById(inviteCode);
            }
            return R.ok("修改邀请码成功");
        }

        return R.error("输入不能为空");
    }

    @Override
    public Integer selectUserIdByOpenId(String openId) {
        return userDao.selectUserIdByOpenId(openId);
    }

    @Override
    public String selectUserNameByOpenId(String openId) {
        return userDao.selectUserNameByOpenId(openId);
    }

    @Override
    public Integer selectLoginStateByOpenId(String openId) {
        return userDao.selectLoginStateByOpenId(openId);
    }

    @Override
    public Integer selectRoleByOpenId(String openId) {
        return userDao.selectRoleByOpenId(openId);
    }

    public String getUserNameByUserId(String userId) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("userId",userId);
//        return userDao.queryUserNameByUserId(map);
        return userDao.queryUserNameByUserId(userId);
    }


    @Override
    public void upateUser(UserEntity userEntity) {
        userDao.update(userEntity);
    }

    @Override
    public R modifyPassword(Map<String, Object> params) {
        //查找用户是否存在
        String password = params.get("password").toString();
        String passwordSecond = params.get("passwordRe").toString();
        UserEntity userInfo = userDao.queryUserName(params);
        if (userInfo==null)
            return R.error("用户不存在");
        if (password==null || password.trim().length()<4 || password.trim().length()>10)
            return R.error("密码不合法");
        if (!password.equals(passwordSecond))
            return R.error("两次不一致");
        password = new Sha256Hash(password, userInfo.getSalt()).toHex();
        userInfo.setPassword(password);
        upateUser(userInfo);
        return R.ok();
    }

}
