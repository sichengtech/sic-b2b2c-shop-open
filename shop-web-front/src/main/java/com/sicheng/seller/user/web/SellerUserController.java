/**
 * 本作品使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权。
 * Copyright (c) 2016 SiCheng.Net
 * This software is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.seller.user.web;

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.store.entity.StoreRole;
import com.sicheng.admin.store.entity.StoreSellerRole;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.site.service.SiteRegisterService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.seller.store.service.StoreRoleService;
import com.sicheng.seller.store.service.StoreSellerRoleService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.shiro.SsoAuthorizingRealm;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>标题: userRole</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2017年2月15日 下午4:07:58
 */
@Controller
@RequestMapping(value = "${sellerPath}/user/userSeller")
public class SellerUserController extends BaseController {
    @Autowired
    private StoreRoleService storeRoleService;
    @Autowired
    private StoreSellerRoleService storeSellerRoleService;
    @Autowired
    private SiteRegisterService registerService;

    @Autowired
    private UserMainService userMainService;
    @Autowired
    private SsoAuthorizingRealm ssoAuthorizingRealm;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "060301";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 查询店主和它的子账号并显示
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("sso:userSeller:view")
    @RequestMapping(value = "list")
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        UserMain u = SsoUtils.getUserMain();
        UserMain userMain = new UserMain();
        userMain.setParentUid(u.getUId());
        Page<UserMain> page = userMainService.selectByWhere(new Page<UserMain>(request, response), new Wrapper(userMain));//商铺子账号
        model.addAttribute("userMain", u);
        model.addAttribute("page", page);
        return "seller/user/userSellerList";
    }

    /**
     * 进入添加商家子账号页面
     *
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "save1")
    public String save1(Model model, HttpServletRequest request) {
        //获取账号验证规则
        SiteRegister siteRegister = registerService.selectOne(new Wrapper());
        model.addAttribute("siteRegister", siteRegister);
        //获取本店铺所有卖家角色
        StoreRole storeRole = new StoreRole();
        storeRole.setDelFlag("0");
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeRole.setStoreId(userSeller.getStoreId());
        List<StoreRole> list = storeRoleService.selectByWhere(new Wrapper(storeRole));
        model.addAttribute("list", list);
        return "seller/user/userSellerSave";
    }

    /**
     * 执行添加商家子账号
     *
     * @param userMain        商家管理
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "save2")
    public String save2(UserMain userMain, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //表单验证
        Long usernameMin = R.getLong("usernameMin", 6L);
        Long usernameMax = R.getLong("usernameMax", 20L);
        Long pwdMin = R.getLong("pwdMin", 6L);
        Long pwdMax = R.getLong("pwdMax", 20L);
        String[] listRole = R.getArray("listRole");
        String nextPassword = R.get("nextPassword");
        List<String> errorList = validateSave(userMain, usernameMin, usernameMax, pwdMin, pwdMax, nextPassword, listRole, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(model, request);//回显错误提示
        }
        //用户名转小写
        if (StringUtils.isNotBlank(userMain.getLoginName())) {
            userMain.setLoginName(userMain.getLoginName().toLowerCase());
        }
        //邮箱转小写
        if (StringUtils.isNotBlank(userMain.getEmail())) {
            userMain.setEmail(userMain.getEmail().toLowerCase());
        }
        String salt = IdGen.randomBase62(32);
        //业务处理
        UserMain u = SsoUtils.getUserMain();
        userMain.setTypeAccount("2");//类型(1主卖家管理员 2子卖家管理员)
        userMain.setRegisterIp(R.getRealIp());//注册ip
        userMain.setLoginIp(R.getRealIp());//登录ip
        userMain.setPassword(PasswordUtils.entryptPassword(userMain.getPassword(), salt));
        userMain.setParentUid(u.getUserSeller().getUId());
        userMain.addTypeUserMember();
        userMain.addTypeUserSeller();
        userMain.setSalt(salt);//密码的盐
        userMain.setEmailValidate(u.getEmailValidate());//（是否邮箱验证）添加与主账号相同
        userMain.setMobileValidate(u.getMobileValidate());//（是否手机验证）添加与主账号相同
        userMainService.insertAll(userMain, listRole);
        addMessage(redirectAttributes, FYUtils.fyParams("添加子账号成功"));
        return "redirect:" + sellerPath + "/user/userSeller/list.htm";
    }

    /**
     * 进入编辑商家子账号页面
     *
     * @param userMain 商家管理
     * @param model
     * @param request
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "edit1")
    public String edit1(UserMain userMain, Model model, HttpServletRequest request) {
        //入参检查
        if (userMain == null || userMain.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("账号不存在！"));
            return "error/400";
        }
        //入参检查
        UserMain entity = userMainService.selectById(userMain.getUId());
        if (entity == null) {
            model.addAttribute("message", FYUtils.fyParams("账号不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //商家账号
        if ("1".equals(entity.getTypeAccount())) {//1主账号,2子账号
            if (entity.getId().equals(userSeller.getId())) {//属主检查
                //商家账号所拥有的角色
                StoreSellerRole storeSellerRole = new StoreSellerRole();
                storeSellerRole.setUId(entity.getUId());
                List<StoreSellerRole> listssr = storeSellerRoleService.selectByWhere(new Wrapper(storeSellerRole));
                model.addAttribute("listssr", listssr);
            }
        } else if (entity.getParentUid().equals(userSeller.getId())) {//属主检查
            //商家账号所拥有的角色
            StoreSellerRole storeSellerRole = new StoreSellerRole();
            storeSellerRole.setUId(entity.getUId());
            List<StoreSellerRole> listssr = storeSellerRoleService.selectByWhere(new Wrapper(storeSellerRole));
            model.addAttribute("listssr", listssr);
        }
        model.addAttribute("userMain", entity);
        //获取账号验证规则
        SiteRegister siteRegister = registerService.selectOne(new Wrapper());
        model.addAttribute("siteRegister", siteRegister);
        //获取本店铺所有卖家角色
        StoreRole storeRole = new StoreRole();
        storeRole.setDelFlag("0");
        storeRole.setStoreId(userSeller.getStoreId());
        List<StoreRole> list = storeRoleService.selectByWhere(new Wrapper(storeRole));
        model.addAttribute("list", list);
        return "seller/user/userSellerSave";
    }

    /**
     * 执行编辑商家子账号
     *
     * @param userMain         商家管理
     * @param model
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "edit2")
    public String edit2(UserMain userMain, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //入参检查
        if (userMain == null || userMain.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("账号不存在！"));
            return "error/400";
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //表单验证
        Long usernameMin = R.getLong("usernameMin", 6L);
        Long usernameMax = R.getLong("usernameMax", 20L);
        String oldLoginName = R.get("oldLoginName");
        Long pwdMin = R.getLong("pwdMin", 6L);
        Long pwdMax = R.getLong("pwdMax", 20L);
        String[] listRole = R.getArray("listRole");
        String nextPassword = R.get("nextPassword");
        List<String> errorList = validatEdit(userMain, pwdMin, usernameMin, usernameMax, oldLoginName, pwdMax, nextPassword, listRole, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(userMain, model, request);//回显错误提示
        }
        //用户名转小写
        if (StringUtils.isNotBlank(userMain.getLoginName())) {
            userMain.setLoginName(userMain.getLoginName().toLowerCase());
        }
        //邮箱转小写
        if (StringUtils.isNotBlank(userMain.getEmail())) {
            userMain.setEmail(userMain.getEmail().toLowerCase());
        }
        //入参检查
        UserMain oldUserMain = userMainService.selectById(userMain.getUId());
        if (oldUserMain == null || oldUserMain.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("账号不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        if (StringUtils.isNotBlank(userMain.getPassword())) {
            userMain.setPassword(PasswordUtils.entryptPassword(userMain.getPassword(), oldUserMain.getSalt()));
        } else {
            userMain.setPassword(null);
        }
        if ("1".equals(oldUserMain.getTypeAccount())) {//类型(1主卖家管理员 2子卖家管理员)
            if (!(userMain.getEmail()).equals(oldUserMain.getEmail())) {
                //主账号修改了邮箱，邮箱状态置为未激活
                userMain.setEmailValidate("0");//是否激活(0否，1是)
            }
            if (oldUserMain.getId().equals(userSeller.getId())) {//属主检查
                userMainService.updateAll(userMain, listRole);
            }
        } else if (oldUserMain.getParentUid().equals(userSeller.getId())) {//属主检查
            userMainService.updateAll(userMain, listRole);
        }
        //清空所有权限
        ssoAuthorizingRealm.clearAllCachedAuthorizationInfo();
        addMessage(redirectAttributes, FYUtils.fyParams("修改账号成功"));
        return "redirect:" + sellerPath + "/user/userSeller/list.htm";
    }

    /**
     * 删除商家子账号
     *
     * @param userMain 商家管理
     * @param redirectAttributes redirectAttributes
     * @param model model
     * @return
     */
    @RequiresPermissions("sso:userSeller:edit")
    @RequestMapping(value = "delete")
    public String delete(UserMain userMain, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (userMain == null || userMain.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("账号不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        userMain = userMainService.selectById(userMain.getUId());
        if (userMain.getParentUid().equals(userSeller.getId())) {//属主检查
            userMainService.deleteAll(userMain);
        }
        addMessage(redirectAttributes, FYUtils.fyParams("删除子账号成功"));
        return "redirect:" + sellerPath + "/user/userSeller/list.htm";
    }

    /**
     * 验证商铺用户名是否存在
     *
     * @param oldLoginName 库中用户名
     * @param loginName    输入的用户名
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "exitLoginName")
    public String exitLoginName(String oldLoginName, String loginName) {
        if (StringUtils.isNotBlank(loginName)) {
            loginName = loginName.toLowerCase();
            if (loginName.equals(oldLoginName)) {
                return "true";
            } else {
                UserMain userMain = new UserMain();
                userMain.setLoginName(loginName);
                List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
                if (userMains.isEmpty()) {
                    return "true";
                } else {
                    return "false";
                }
            }
        }
        return "false";
    }

    /**
     * 服务端验证(新增)
     */
    private List<String> validateSave(UserMain userMain, Long usernameMin, Long usernameMax,
                                      Long pwdMin, Long pwdMax, String nextPassword, String[] listRole, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(userMain.getLoginName())) {
            errorList.add(FYUtils.fyParams("请输入用户名"));
        }
        if (StringUtils.isNotBlank(userMain.getLoginName())) {
            if (userMain.getLoginName().length() < usernameMin) {
                errorList.add(FYUtils.fyParams("账号名称长度小于") + usernameMin);
            }
            if (userMain.getLoginName().length() > usernameMax) {
                errorList.add(FYUtils.fyParams("账号名称长度大于") + usernameMax);
            }
            UserMain u = new UserMain();
            u.setLoginName(userMain.getLoginName());
            List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(u));
            if (userMains.size() > 0) {
                errorList.add(FYUtils.fyParams("用户名已存在"));
            }
        }
        if (StringUtils.isBlank(userMain.getPassword())) {
            errorList.add(FYUtils.fyParams("请输入密码"));
        }
        if (StringUtils.isNotBlank(userMain.getPassword())) {
            if (userMain.getPassword().length() < pwdMin) {
                errorList.add(FYUtils.fyParams("密码长度小于") + pwdMin);
            }
            if (userMain.getPassword().length() > pwdMax) {
                errorList.add(FYUtils.fyParams("密码长度大于") + pwdMax);
            }
        }
        if (StringUtils.isBlank(nextPassword)) {
            errorList.add(FYUtils.fyParams("请再次输入密码"));
        }
        if (StringUtils.isNotBlank(nextPassword) && !nextPassword.equals(userMain.getPassword())) {
            errorList.add(FYUtils.fyParams("两次输入密码不一致"));
        }
        if (listRole.length == 0) {
            errorList.add(FYUtils.fyParams("请选择账号组"));
        }
        return errorList;
    }

    /**
     * 服务端验证(编辑)
     */
    private List<String> validatEdit(UserMain userMain, Long pwdMin, Long usernameMin, Long usernameMax,
                                     String oldLoginName, Long pwdMax, String nextPassword, String[] listRole, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(userMain.getLoginName())) {
            errorList.add(FYUtils.fyParams("请输入用户名"));
        }
        if (StringUtils.isNotBlank(userMain.getLoginName())) {
            if (userMain.getLoginName().length() < usernameMin) {
                errorList.add(FYUtils.fyParams("账号名称长度小于") + usernameMin);
            }
            if (userMain.getLoginName().length() > usernameMax) {
                errorList.add(FYUtils.fyParams("账号名称长度大于") + usernameMax);
            }
            if (!userMain.getLoginName().equals(oldLoginName)) {
                UserMain u = new UserMain();
                u.setLoginName(userMain.getLoginName());
                List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(u));
                if (!userMains.isEmpty()) {
                    errorList.add(FYUtils.fyParams("用户名已存在"));
                }
            }
        }
        if (StringUtils.isNotBlank(userMain.getPassword())) {
            if (userMain.getPassword().length() < pwdMin) {
                errorList.add(FYUtils.fyParams("密码长度小于") + pwdMin);
            }
            if (userMain.getPassword().length() > pwdMax) {
                errorList.add(FYUtils.fyParams("密码长度大于") + pwdMax);
            }
            if (StringUtils.isBlank(nextPassword)) {
                errorList.add(FYUtils.fyParams("请再次输入密码"));
            }
            if (StringUtils.isNotBlank(nextPassword) && !nextPassword.equals(userMain.getPassword())) {
                errorList.add(FYUtils.fyParams("两次输入密码不一致"));
            }
        }
        if ("1".equals(R.get("myType"))) {
            if (StringUtils.isBlank(userMain.getEmail())) {
                errorList.add(FYUtils.fyParams("邮箱不能为空"));
            }
        }
        UserMain u = SsoUtils.getUserMain();
        if ("1".equals(u.getTypeAccount())) {//账号类型 (1主账号,2子账号)
            return errorList;
        }
        if (listRole.length == 0) {
            errorList.add(FYUtils.fyParams("请选择账号组"));
        }
        return errorList;
    }
}
