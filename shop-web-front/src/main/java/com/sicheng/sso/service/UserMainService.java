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
 */
package com.sicheng.sso.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.store.entity.StoreSellerRole;
import com.sicheng.admin.sys.entity.SysToken;
import com.sicheng.admin.sys.utils.TokenUtils;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.shiro.FdpSessionDAOI;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.seller.site.service.SiteInfoService;
import com.sicheng.seller.site.service.SiteMessageTemplateService;
import com.sicheng.seller.site.service.SiteSendMessagsService;
import com.sicheng.seller.store.service.StoreSellerRoleService;

/**
 * 会员总表 Service
 *
 * @author 蔡龙
 * @version 2017-04-25
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserMainService extends CrudService<UserMainDao, UserMain> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private UserMemberService userMemberService;

    @Autowired
    private StoreSellerRoleService storeSellerRoleService;

    @Autowired
    private SiteMessageTemplateService messageTemplateService;

    @Autowired
    private SiteInfoService siteInfoService;

    @Autowired
    private FdpSessionDAOI sessionDAO;

    @Autowired
    private SendEmailService sendEmailService;

    public FdpSessionDAOI getSessionDAO() {
        return sessionDAO;
    }

    /**
     * 保存上次登录信息，更新本次登录信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserLoginInfo(UserMain userMain) {
        // 更新本次登录信息
        UserMain u = new UserMain();
        u.setUId(userMain.getUId());
        u.setLoginIp(R.getRealIp());
        u.setLoginDate(new Date());
        dao.updateByIdSelective(u);
    }

    /**
     * 用户注册
     *
     * @param userMain
     * @param SsoReg
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(UserMain userMain, String SsoReg) {
        //手机号(开通慧付宝账号用)
        String mobile = "";
        String salt = IdGen.randomBase62(32);
        //会员总表插入数据
        if ("1".equals(SsoReg)) {
            //账号注册
            userMain.setPassword(PasswordUtils.entryptPassword(userMain.getPassword(), salt));//密码
            userMain.setSalt(salt);//盐+
            userMain.setEmailValidate("1");//邮箱是否通过验证(0否，1是)
            userMain.setParentUid(0L);//父ID，为0表示是主账号
            userMain.addTypeUserMember();//添加买家
            userMain.setTypeAccount("1");//账号类型 (1主账号,2子账号)
            userMain.setRegisterIp(R.getRealIp());//注册ip
            userMain.setLoginIp(R.getRealIp());//最后登录ip
            userMain.setLoginDate(new Date());
            dao.insertSelective(userMain);
            /**
             * (2019.12.30默认注册有邮箱，去掉发送验证邮件)
             */
//            //给用户发邮件
////            SysToken sysToken = TokenUtils.generateToken(userMain.getUId(), "20");
////            //邮件模板参数
////            Map<String, String> map = new HashMap<>();
////            SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
////            if (siteInfo != null) {
////                map.put("siteName", siteInfo.getName());
////            }
////            String url = R.getRequest().getScheme() + "://" + R.getRequest().getServerName() + ":" + R.getRequest().getServerPort() + R.getRequest().getContextPath() + Global.getConfig("activationUrl");
////            map.put("userName", userMain.getLoginName());
////            map.put("activationUrl", url + "?token=" + sysToken.getToken() + "&uId=" + sysToken.getUserId());
////            //返回的邮件模板的标题和邮件内容
////            Map<String, String> mapInfo = messageTemplateService.getEmailInfo(map, SiteSendMessagsService.ACTIVATE_ACCOUNT);
////            if (mapInfo != null) {
////                if (StringUtils.isNotBlank(mapInfo.get("emailContent")) && StringUtils.isNotBlank(mapInfo.get("emailTitle"))) {
////                    sendEmailService.sendEmail(userMain.getEmail(), mapInfo.get("emailTitle"), mapInfo.get("emailContent"));
////                }
////            }
        }
        if ("2".equals(SsoReg)) {
            //手机注册
            userMain.setLoginName(userMain.getMobile());
            userMain.setMobileValidate("1");//手机号是否通过验证(0否，1是)
            userMain.setSalt(salt);//盐
            userMain.setParentUid(0L);//父ID，为0表示是主账号
            userMain.addTypeUserMember();//添加买家
            userMain.setTypeAccount("1");//账号类型 (1主账号,2子账号)
            userMain.setRegisterIp(R.getRealIp());//注册ip
            userMain.setLoginIp(R.getRealIp());//最后登录ip
            userMain.setLoginDate(new Date());
            dao.insertSelective(userMain);
            mobile = userMain.getMobile();
        }
        //会员扩展表_买家插入数据
        UserMember userMember = new UserMember();
        userMember.setPkMode(1);
        userMember.setUId(userMain.getUId());
        userMember.setHeadPicPath("/shop_init/user_face_01.png");//给用户默认头像
        userMemberService.insertSelective(userMember);
    }

    /**
     * 用户注册(个人注册，商家注册，汽车门店注册)
     *
     * @param userMain
     * @param SsoReg
     */
//    @Transactional(rollbackFor = Exception.class)
//    public void carSave(UserMain userMain, UserMember userMember) {
//        //手机号(开通慧付宝账号用)
//        String mobile = "";
//        String salt = IdGen.randomBase62(32);
//        //会员总表插入数据
//        userMain.setMobileValidate("1");//手机号是否通过验证(0否，1是)
//        userMain.setPassword(PasswordUtils.entryptPassword(userMain.getPassword(), salt));//密码
//        userMain.setSalt(salt);//盐
//        userMain.setParentUid(0L);//父ID，为0表示是主账号
//        userMain.addTypeUserMember();//添加买家
//        userMain.setTypeAccount("1");//账号类型 (1主账号,2子账号)
//        userMain.setRegisterIp(R.getRealIp());//注册ip
//        userMain.setLoginIp(R.getRealIp());//最后登录ip
//        userMain.setLoginDate(new Date());
//        dao.insertSelective(userMain);
//        //会员扩展表_买家插入数据
//        userMember.setPkMode(1);
//        userMember.setUId(userMain.getUId());
//        userMember.setHeadPicPath("/shop_init/user_face_01.png");//给用户默认头像
//        userMemberService.insertSelective(userMember);
//    }

    /**
     * 激活邮箱
     */
    @Transactional(rollbackFor = Exception.class)
    public void activeMail(SysToken sysToken, String uId) {
        //修改主账号，并把当前账号的子账号一起激活了
        UserMain uChild1 = new UserMain();
        uChild1.setEmailValidate("1"); //邮箱是否通过验证(0否，1是)
        UserMain uChild2 = new UserMain();
        uChild2.setUId(Long.parseLong(uId));
        dao.updateByWhereSelective(uChild1, new Wrapper(uChild2));
        //把token置为失效
        TokenUtils.verificationToken(sysToken.getUserId(), sysToken.getToken());
    }

    /**
     * 商家账号与商家账号角色中间表的插入
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertAll(UserMain userMain, String[] listRole) {
        //新增会员总表
        super.insertSelective(userMain);
        //新增会员买家表
        UserMember userMember = new UserMember();
        userMember.setPkMode(1);
        userMember.setUId(userMain.getUId());
        userMember.setHeadPicPath("/shop_init/store_face_01.png");
        userMember.setCreateDate(new Date());
        userMember.setUpdateDate(new Date());
        userMemberService.insertSelective(userMember);
        //批量将卖家id和角色id存入中间表
        List<StoreSellerRole> list = Lists.newArrayList();
        for (int i = 0; i < listRole.length; i++) {
            StoreSellerRole storeSellerRole = new StoreSellerRole();
            storeSellerRole.setUId(userMain.getUId());
            storeSellerRole.setRoleId(Long.parseLong(listRole[i]));
            list.add(storeSellerRole);
        }
        storeSellerRoleService.insertSelectiveBatch(list);
    }

    /**
     * 商家账号与商家账号角色中间表的更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAll(UserMain userMain, String[] listRole) {
        //更新卖家账号
        super.updateByIdSelective(userMain);
        //删除卖家账号的角色
        StoreSellerRole storeSellerRole = new StoreSellerRole();
        storeSellerRole.setUId(userMain.getUId());
        storeSellerRoleService.deleteByWhere(new Wrapper(storeSellerRole));
        //批量将卖家id和角色id存入中间表
        List<StoreSellerRole> list = Lists.newArrayList();
        for (int i = 0; i < listRole.length; i++) {
            StoreSellerRole newSellerRole = new StoreSellerRole();
            newSellerRole.setUId(userMain.getUId());
            newSellerRole.setRoleId(Long.parseLong(listRole[i]));
            list.add(newSellerRole);
        }
        storeSellerRoleService.insertSelectiveBatch(list);
    }


    /**
     * 删除卖家账号，删除卖家账号的角色
     *
     * @param userMain
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(UserMain userMain) {
        //删除会员总表
        super.deleteById(userMain.getUId());
        //删除会员买家表
        userMemberService.deleteById(userMain.getUId());
        //删除卖家账号的角色
        StoreSellerRole storeSellerRole = new StoreSellerRole();
        storeSellerRole.setUId(userMain.getUId());
        storeSellerRoleService.deleteByWhere(new Wrapper(storeSellerRole));
    }

}