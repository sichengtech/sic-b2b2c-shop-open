/**
 * SiC B2B2C Shop 使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权书。
 * Copyright (c) 2016 SiCheng.Net
 * SiC B2B2C Shop is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 *  
 */
package com.sicheng.wap.web.api;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.CookieUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.utils.RegexUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.UserMainService;
import com.sicheng.wap.service.UserMemberService;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppPush;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * <p>标题: LoginController</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2018年1月28日 上午9:15:03
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class LoginController extends BaseController {


    @Autowired
    private UserMainService userMainService;

    @Autowired
    private UserMemberService userMemberService;

    /**
      * 登录认证(旧的)
      * 支持1账号登录、2手机号登录，登录成功后，在服务端把用户信息存入session，在客户端把用户信息存入cookie。
      * 当客户端支持cookie时可使用此方案。
      * @return
     */
    @RequestMapping(value = "/{version}/user/login/authentication")
    @ResponseBody
    public Map<String, Object> userLogin4Wap() {
        String type = R.get("type");//登录类型，1账号登录、2手机号登录
        if (StringUtils.isBlank(type)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("缺少参数：登录类型"), null, null);
        } else if (!("1".equals(type) || "2".equals(type))) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("登录类型值不正确"), null, null);
        }
        if (AppTokenUtils.isAppRequest()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("请求头中参数TerminalType（终端类型）有错误，这是wap专用登录接口，但被识别为App"), null, null);
        }
//		Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());//微信授权的openId
//		if(openId==null){
//			return ApiUtils.getMap(ApiUtils.STATUS_INVALID,"请使用微信登录",null,null);
//		}

        if ("1".equals(type)) {
            UserMain userMain = null;
            if (StringUtils.isNotBlank(R.get("loginName"))) {
                userMain = new UserMain();
                userMain.setLoginName(R.get("loginName").toLowerCase());
                userMain = userMainService.selectOne(new Wrapper(userMain));
            }

            //表单验证(账号登录)
            //List<String> errorList=validate1(userMain,openId.toString());
            List<String> errorList = validate1(userMain, null);
            if (errorList.size() > 0) {
                String message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            //具体业务
            //userMainService.doLogin4Wap(userMain,openId.toString());
            userMainService.saveLogin4Wap(userMain, null);
        }
        if ("2".equals(type)) {
            UserMain entity = null;
            if (StringUtils.isNotBlank(R.get("mobile"))) {
                entity = new UserMain();
                entity.setMobile(R.get("mobile"));
                entity = userMainService.selectOne(new Wrapper(entity));
            }

            //表单验证(手机号登录)
            //List<String> errorList=validate2(entity,openId.toString());
            List<String> errorList = validate2(entity, null);
            if (errorList.size() > 0) {
                String message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            //具体业务
//			userMainService.doLogin4Wap(userMain,openId.toString());
            userMainService.saveLogin4Wap(entity, null);
        }
        //登录成功后，跳转到要进入的页面
        String data = "/user/userCentral.htm";
        String path = (String) R.getSession().getAttribute("path");
        R.getSession().removeAttribute("path");
        if (path != null && !path.equals("")) {
            String suff = R.getRequest().getContextPath();
            if (path.startsWith(suff) && suff != null) {
                data = path.substring(suff.length());
            } else {
                data = path;
            }
        }
        //登录成功后清除缓存中的微信openId
        cache.del(CacheConstant.WEIXIN_OPENID + AppTokenUtils.getRequestFlag());
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("登录成功"), data, null);
    }

    /**
      * 登录认证(新的)
      * 支持1账号登录、2手机号登录，登录成功后，在服务端生成token并存入token表，在客户端把token存入本地存储，调用接口时要携带token，与服务端存储的token做对比。
      * 当开发app是使用此方案。
      * @return
     */
    @RequestMapping(value = "/{version}/user/login/app")
    @ResponseBody
    public Map<String, Object> userLogin4App() {
        String cid = R.get("cid");//手机设备的唯一标识
        String type = R.get("type");//登录类型，1账号登录、2手机号登录
        if (StringUtils.isBlank(type)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("缺少参数：登录类型"), null, null);
        } else if (!("1".equals(type) || "2".equals(type))) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("登录类型值不正确"), null, null);
        }
        if (!AppTokenUtils.isAppRequest()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("请求头中参数TerminalType（终端类型）有错误，这是App专用登录接口，请携带正确参数"), null, null);
        }

//		Object openId=cache.get(CacheConstant.WEIXIN_OPENID+AppTokenUtils.getRequestFlag());//微信授权的openId
//		if(openId==null){
//			return ApiUtils.getMap(ApiUtils.STATUS_INVALID,"请使用微信登录",null,null);
//		}

        UserMain userMain = null;
        if ("1".equals(type)) {
            if (StringUtils.isNotBlank(R.get("loginName"))) {
                userMain = new UserMain();
                userMain.setLoginName(R.get("loginName").toLowerCase());
                userMain = userMainService.selectOne(new Wrapper(userMain));
            }

            //表单验证(账号登录)
            //List<String> errorList=validate1(userMain,openId.toString());
            List<String> errorList = validate1(userMain, null);
            if (errorList.size() > 0) {
                String message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
        }
        if ("2".equals(type)) {
            if (StringUtils.isNotBlank(R.get("mobile"))) {
                userMain = new UserMain();
                userMain.setMobile(R.get("mobile"));
                userMain = userMainService.selectOne(new Wrapper(userMain));
            }

            //表单验证(手机号登录)
            //List<String> errorList=validate2(entity,openId.toString());
            List<String> errorList = validate2(userMain, null);
            if (errorList.size() > 0) {
                String message = ApiUtils.errorMessage(errorList);
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            cache.del(CacheConstant.SMS_MOBILE + userMain.getUId());
            cache.del(CacheConstant.SMS_CODE + userMain.getUId());
        }
        //userMainService.doLogin4App(userMain,openId.toString());
        String appToken = userMainService.saveLogin4App(userMain, null);
        Map<String, Object> data = LoginController.data(userMain, appToken);

        //登录成功后清除缓存中的微信openId
        cache.del(CacheConstant.WEIXIN_OPENID + AppTokenUtils.getRequestFlag());
        //用户登录成功后，将用户id绑定 手机设备的cid 进行绑定(个推：消息通知)
        AppPush.bindAlias(cid, userMain.getUId() + "");
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("登录成功"), data, null);
    }


    /**
     * 退出登录
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userCentral/exitLogin")
    public Map<String, Object> userExitLogin() {
        UserMain userMain = AppTokenUtils.findUser();//获取登录的用户信息
        try {
            UserMember userMember = new UserMember();
            userMember.setUId(userMain.getUId());//属主检查
            userMember.setOpenId("");
            userMemberService.updateByIdSelective(userMember);
            //清除session信息
            AppTokenUtils.removeUser();
            //清除Cookie信息(获得指定Cookie的值,并删除)
            CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.uid", null, "/", 0);
            CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.isTypeUserPurchaser", null, "/", 0);
            CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.isloginInvalid", null, "/", 0);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("退出登录成功"), "/user/login/form.htm", null);
        } catch (Exception e) {
            logger.error("退出登录失败:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("退出登录失败"), null, null);
        }
    }

    /**
      * 表单验证(账号登录) 
      * @param model
      * @return
     */
    private List<String> validate1(UserMain userMain, String openId) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("loginName"))) {
            errorList.add(FYUtils.fy("用户名不能为空"));
        }

        if (userMain == null) {
            errorList.add(FYUtils.fy("当前账号未注册"));
        }
        if (userMain != null && "1".equals(userMain.getIsLocked())) {
            //是否锁定(0否，1是)锁定后不能登录
            errorList.add(FYUtils.fy("当前账号被锁定，无法登录"));
        }
        if (userMain != null && "0".equals(userMain.getIsLocked())) {
            //账号验证通过，获取盐并验证密码
            String salt = userMain.getSalt();//盐
            String password = userMain.getPassword();//密文密码
            if (StringUtils.isBlank(password)) {
                errorList.add(FYUtils.fy("当前账号未设置密码,不能使用密码登录"));
            }
            if (StringUtils.isBlank(R.get("password"))) {
                errorList.add(FYUtils.fy("密码不能为空"));
            }
            if (StringUtils.isNotBlank(R.get("password")) && StringUtils.isNotBlank(password)) {
                if (!PasswordUtils.validatePassword(R.get("password"), password, salt)) {
                    errorList.add(FYUtils.fy("密码不正确"));
                }
            }
        }
        return errorList;
    }

    /**
      * 表单验证(手机号登录) 
      * @param model
      * @return
     */
    private List<String> validate2(UserMain userMain, String openId) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("mobile"))) {
            errorList.add(FYUtils.fy("请输入手机号"));
        }

        if (!RegexUtils.checkMobile(R.get("mobile"))) {
            errorList.add(FYUtils.fy("手机号格式不正确"));
        }

        if (userMain == null) {
            errorList.add(FYUtils.fy("当前手机号未注册，请输入其他手机号"));
        }
        if (userMain != null && "0".equals(userMain.getMobileValidate())) {
            //mobileValidate(手机号是否通过验证(0否，1是))
            errorList.add(FYUtils.fy("当前手机号未通过验证"));
        }

        if (StringUtils.isBlank(R.get("smsCode"))) {
            errorList.add(FYUtils.fy("请输入短信验证码"));
        }
        if (userMain != null && cache.get(CacheConstant.SMS_MOBILE + userMain.getUId()) == null) {
            errorList.add(FYUtils.fy("请填写正确手机号"));
        }
        if (userMain != null && StringUtils.isNotBlank(R.get("mobile")) && !(R.get("mobile")).equals(cache.get(CacheConstant.SMS_MOBILE + userMain.getUId()))) {
            errorList.add(FYUtils.fy("请填写正确手机号"));
        }
        if (userMain != null && cache.get(CacheConstant.SMS_CODE + userMain.getUId()) == null) {
            errorList.add(FYUtils.fy("请填写正确短信验证码"));
        }
        if (userMain != null && StringUtils.isNotBlank(R.get("smsCode")) && !(R.get("smsCode")).equals(cache.get(CacheConstant.SMS_CODE + userMain.getUId()))) {
            errorList.add(FYUtils.fy("验证码无效"));
        }
        return errorList;
    }

    /**
     * 封装返回的数据，供登录、注册两个地方使用
     * @param userMain
     * @param appToken
     * @return
     */
    public static Map<String, Object> data(UserMain userMain, String appToken) {
        //User实体转json发给客户端
        Map<String, Object> user = new HashMap<>();
        user.put("userId", userMain.getId());//用户的ID
        user.put("userName", userMain.getLoginName());//用户名、登录名
        //user.put("headPicPath","");//用户头像
        user.put("isTypeUserPurchaser", userMain.isTypeUserPurchaser());//是否为采购商(临时存储)

        //返回的数据结构
        Map<String, Object> data = new HashMap<>();
        data.put(AppTokenUtils.APP_TOKEN_KEY, appToken);
        data.put("user", user);
        return data;
    }
}
