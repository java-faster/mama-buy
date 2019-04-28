package com.room.controller;

import com.room.common.R;
import com.room.service.UserService;
import com.room.service.WXSessionService;
import com.room.wx.HttpRequest;
import com.room.wx.WXConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(value = "WXController", description = "微信信息")
@RestController
@RequestMapping("/wx")
public class WXController {

    @Autowired
    private WXSessionService wxSessionService;
    @Autowired
    private UserService userService;

    //String encryptedData, String iv
    @ApiOperation(value = "微信登录", notes = "微信登录")
    @RequestMapping(value = "/login")
    public Map<String, Object> decodeUserInfo(String code) { //,HttpServletResponse response

        Map<String, Object> map = new HashMap<>();
        System.out.println("code------"+code);
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
//            map.put("sessionId","");
//            map.put("msg", "code为空");
//            map.put("请授权后","");
            map.put("msg", "请授权后再操作");
            return map;
        }

        //1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        //请求参数
        String params = "appid=" + WXConst.app_id + "&secret=" + WXConst.secret + "&js_code=" + code + "&grant_type=" + WXConst.grant_type+"&connect_redirect=1";
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        System.out.println("json---------"+json);
        //获取会话密钥（session_key）
       if (json != null){
           String session_key = json.get("session_key").toString();
           //用户的唯一标识（openid）
           String openid = (String) json.get("openid");
           if (null == openid || openid.length() <= 0) {
               return R.error("openId为空");
           }
           if(null != userService && null != userService.selectUserNameByOpenId(openid)) {
               int userId = userService.selectUserIdByOpenId(openid);//通过openId获取userId
               String userName = userService.selectUserNameByOpenId(openid);//通过openId获取userName
               Integer loginState = userService.selectLoginStateByOpenId(openid);//通过openId获取loginState
               Integer role = userService.selectRoleByOpenId(openid);//通过openId获取role
               map.put("userId", userId);
               map.put("userName", userName);
               map.put("loginState", loginState);
               map.put("role", role);
           }
           UUID uuid = UUID.randomUUID();
           String sessionId = uuid.toString().replace("-", "");
           map.put("status", 1);
           map.put("sessionId",sessionId);
           map.put("msg", "成功");

           Map<String, Object> temp = new HashMap<>();
           temp.put("openId",openid);
           temp.put("sessionKey",session_key);
           temp.put("sessionId",sessionId);
           if (wxSessionService.getSessionIdByOpenId(openid)==null) {
               temp.put("id",0);
               wxSessionService.save(temp);
           }else{
               wxSessionService.update(temp);
           }
       }
        //2、对encryptedData加密数据进行AES解密
//        try {
//            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
//            if (null != result && result.length() > 0) {
//                map.put("status", 1);
//                map.put("msg", "成功");
//
//                JSONObject userInfoJSON = JSONObject.fromObject(result);
//                Map<String,Object> userInfo = new HashMap<>();
//                userInfo.put("openId", userInfoJSON.get("openId"));
//                userInfo.put("unionId", userInfoJSON.get("unionId"));
//                map.put("userInfo", userInfo);
//                return map;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        map.put("status", 0);
//        map.put("msg", "解密失败");
        return map;
    }

    @RequestMapping(value = "/check")
    public R checkSession(String sessionId){    //此方法目前没被调用
        if (sessionId==null || "".equals(sessionId.trim()))
            return R.error("参数错误");
        String openId = wxSessionService.getOpenIdBySessionId(sessionId);
        if (openId!=null && !"".equals(openId))
            return R.ok();
        else
            return R.error("id不合法");
    }
}
