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
package com.sicheng.wap.service;

import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.CookieUtils;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
  * <p>标题: UserMainService</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2018年3月6日 下午4:43:20
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserMainService extends CrudService<UserMainDao, UserMain> {
    //请在这里编写业务逻辑

    @Autowired
    private UserMemberService userMemberService;

    /**
      * 保存注册信息 
      * @param userMain 主用户对像，userMain表映射的对像
      * @param openId 微信openId
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveRegister(UserMain userMain, String openId) {
        //查询微信openId是否绑定用户
        UserMember entity = new UserMember();
        //entity.setOpenId(openId.toString());
        entity = userMemberService.selectOne(new Wrapper(entity));
        if (entity != null) {
            //微信openId绑定用户,给这个用户解绑
            UserMember member = new UserMember();
            member.setUId(entity.getUId());
            member.setOpenId("");
            userMemberService.updateByIdSelective(member);
        }
        //保存用户信息
        super.insertSelective(userMain);
        //会员扩展表_买家插入数据
        UserMember userMember = new UserMember();
        userMember.setPkMode(1);
        userMember.setUId(userMain.getUId());
        //userMember.setOpenId(openId);
        userMember.setHeadPicPath("/shop_init/user_face_01.png");//给用户默认头像
        userMemberService.insertSelective(userMember);
        //将用户信息保存到session中
        String appToken = AppTokenUtils.saveUser(userMain);
        //将登录的用户信息存入Cookie中
        CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.uid", userMain.getUId().toString(), "/", -1);//用户id(临时存储)
        CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.isTypeUserPurchaser", userMain.isTypeUserPurchaser() ? "true" : "false", "/", -1); //是否为采购商(临时存储)
        CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.isloginInvalid", "true", 1800); //是否登录失效(持久性存储30分钟)（有效期要和session时间一样长）

        return appToken;
    }

    /**
      * 处理登录业务(wap)
      * @param userMain 主用户对像，userMain表映射的对像
      * @param openId 微信openId
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveLogin4Wap(UserMain userMain, String openId) {
        //若当前openI绑定过其它用户，则解除绑定关系，为建立新的绑定关系做好准备
        UserMember oldUser = new UserMember();
        //oldUser.setOpenId(openId.toString());
        oldUser = userMemberService.selectOne(new Wrapper(oldUser));
        if (oldUser != null) {
            UserMember member = new UserMember();
            member.setUId(oldUser.getUId());
            member.setOpenId("");
            userMemberService.updateByIdSelective(member);//解绑
        }

        //登录成功后更新登录ip和登录日期
        upUserIpDate(userMain.getUId());

        //openID与刚登录的用户之间绑定
        UserMember userMember = new UserMember();
        userMember.setUId(userMain.getUId());
        //userMember.setOpenId(openId);
        userMemberService.updateByIdSelective(userMember);

        cache.del(CacheConstant.SMS_MOBILE + userMain.getUId());
        cache.del(CacheConstant.SMS_CODE + userMain.getUId());

        //将用户信息保存到session中
        AppTokenUtils.saveUser(userMain);

        //将登录的用户信息存入Cookie中
        CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.uid", userMain.getUId().toString(), "/", -1);//用户id(临时存储)
        CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.isTypeUserPurchaser", userMain.isTypeUserPurchaser() ? "true" : "false", "/", -1); //是否为采购商(临时存储)
        CookieUtils.setCookie(R.getRequest(), R.getResponse(), "wxusm.isloginInvalid", "true", 1800); //是否登录失效(持久性存储30分钟)（有效期要和session时间一样长）
    }

    /**
      * 处理登录业务（app） 
      * @param userMain 主用户对像，userMain表映射的对像
      * @param openId 微信openId
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveLogin4App(UserMain userMain, String openId) {
//		//若当前openID绑定过其它用户，则解除绑定关系，为建立新的绑定关系做好准备
//		UserMember oldUser=new UserMember();
//		oldUser.setOpenId(openId.toString());
//		oldUser=userMemberService.selectOne(new Wrapper(oldUser));
//		if(oldUser!=null){
//			UserMember member=new UserMember();
//			member.setUId(oldUser.getUId());
//			member.setOpenId("");
//			userMemberService.updateByIdSelective(member);//解绑
//		}

        //登录成功后更新登录ip和登录日期
        upUserIpDate(userMain.getUId());

//		//openID与刚登录的用户之间绑定
//		UserMember userMember = new UserMember();
//		userMember.setUId(userMain.getUId());
//		userMember.setOpenId(openId);
//		userMemberService.updateByIdSelective(userMember);

        //将用户信息保存到session中
        String appToken = AppTokenUtils.saveUser(userMain);
        return appToken;
    }

    /**
      * 忘记密码后，设置新的密码
      * @param type 找回密码的类型(1邮箱地址找回密码、2手机号找回密码)
      * @param userMain 用户信息
      * @param passward 设置的新密码
     */
    @Transactional(rollbackFor = Exception.class)
    public void editForgetPwd(String type, UserMain userMain, String passward) {
        //设置新密码
        userMain = super.selectOne(new Wrapper(userMain));
        UserMain u = new UserMain();
        u.setUId(userMain.getUId());
        u.setPassword(PasswordUtils.entryptPassword(passward, userMain.getSalt()));//密码
        super.updateByIdSelective(u);
        if ("1".equals(type)) {
            cache.del(CacheConstant.EMAIL_ADDR + userMain.getUId());
            cache.del(CacheConstant.EMAIL_CODE + userMain.getUId());

        }
        if ("2".equals(type)) {
            cache.del(CacheConstant.SMS_MOBILE + userMain.getUId());
            cache.del(CacheConstant.SMS_CODE + userMain.getUId());
        }
    }

    /**
      * 编辑账号信息 
      * @param userMain 主用户对像，userMain表映射的对像
      * @param userMember  UserMember表映射的对像
     */
    @Transactional(rollbackFor = Exception.class)
    public void editUserInfo(UserMain userMain, UserMember userMember) {
        super.updateByIdSelective(userMain);
        AppTokenUtils.saveUser(super.selectById(userMain.getUId()));
        userMemberService.updateByIdSelective(userMember);
    }

    /**
     *
      * 登录成功后更新登录ip和登录日期 
      * @param uid 用户id
     */
    public void upUserIpDate(Long uid) {
        UserMain entity = new UserMain();
        entity.setUId(uid);
        entity.setLoginIp(R.getRealIp());//最后登录ip
        entity.setLoginDate(new Date());//最后登录日期
        super.updateByIdSelective(entity);
    }
}
