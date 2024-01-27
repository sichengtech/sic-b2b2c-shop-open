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
package com.sicheng.admin.sso.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.site.service.SiteRegisterService;
import com.sicheng.admin.store.service.StoreEnterAuthService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sso.service.UserMemberService;
import com.sicheng.admin.sso.service.UserSellerService;
import com.sicheng.admin.store.entity.Store;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;

/**
 * 会员总表 Controller
 * 所属模块：sso
 *
 * @author 蔡龙
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sso/userMain")
public class UserMainController extends BaseController {

    @Autowired
    private StoreEnterAuthService storeEnterAuthService;

    @Autowired
    private UserMainService userMainService;



    @Autowired
    private UserMemberService userMemberService;

    @Autowired
    private UserSellerService userSellerService;

    @Autowired
    private SiteRegisterService siteRegisterService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 买家进入列表页
     *
     * @param userMain 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sso:member:view")
    @RequestMapping(value = "memberList")
    public String memberList(UserMain userMain, UserMember userMember, HttpServletRequest request, HttpServletResponse response, Model model) {
        String menu3 = "040101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        //用户名转小写
        if (StringUtils.isNotBlank(userMain.getLoginName())) {
            userMain.setLoginName(userMain.getLoginName().toLowerCase());
        }
        //邮箱转小写
        if (StringUtils.isNotBlank(userMain.getEmail())) {
            userMain.setEmail(userMain.getEmail().toLowerCase());
        }
        //用搜索条件去换取id
        Long uId = 0L;
        if (StringUtils.isNotBlank(userMember.getRealName()) || userMember.getBeginBirthday() != null || userMember.getEndBirthday() != null) {
            //用买家的名字去换取id
            List<UserMember> userMembers = userMemberService.selectByWhere(new Wrapper(userMember));
            if (!userMembers.isEmpty()) {
                uId = userMembers.get(0).getUId();
                userMain.setUId(uId);
            } else {
                return "admin/sso/userMemberList";
            }
        }
        Page<UserMain> page = userMainService.selectByWhere(new Page<UserMain>(request, response), new Wrapper(userMain));
        model.addAttribute("page", page);
        model.addAttribute("userMember", userMember);
        model.addAttribute("userMain", userMain);
        return "admin/sso/userMemberList";
    }

    /**
     * 卖家进入列表页
     *
     * @param userMain 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sso:seller:view")
    @RequestMapping(value = "sellerList")
    public String sellerList(UserMain userMain, Store store, HttpServletRequest request, HttpServletResponse response, Model model) {
        String menu3 = "050101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        //用搜索条件去换取id
        Long uId = 0L;
        Wrapper wrapper = new Wrapper();
        if (StringUtils.isNotBlank(userMain.getLoginName())) {
            //用户名转小写
            userMain.setLoginName(userMain.getLoginName().toLowerCase());
            //用商家名称换取id
            List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
            if (!userMains.isEmpty()) {
                uId = userMains.get(0).getUId();
                wrapper.and("a.u_id=", uId);
            } else {
                return "admin/sso/userSellerList";
            }
        }
        Page<UserSeller> page = userSellerService.selectByWhere(new Page<UserSeller>(request, response), wrapper);
        model.addAttribute("page", page);
        model.addAttribute("store", store);
        model.addAttribute("userMain", userMain);
        return "admin/sso/userSellerList";
    }

    /**
     * 进入编辑页面
     *
     * @param userMain 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sso:member:edit")
    @RequestMapping(value = "edit1")
    public String edit1(UserMain userMain, Model model) {
        String menu3 = "040101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        UserMain entity = null;
        if (userMain != null) {
            if (userMain.getId() != null) {
                entity = userMainService.selectById(userMain.getId());
            }
        }
        model.addAttribute("userMain", entity);
        return "admin/sso/userMainForm";
    }

    /**
     * 执行编辑
     *
     * @param userMain           实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:member:edit")
    @RequestMapping(value = "edit2")
    public String edit2(UserMain userMain, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(userMain, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(userMain, model);//回显错误提示
        }
        //用户名转换小写
        userMain.setLoginName(userMain.getLoginName().toLowerCase());
        //获取盐
        UserMain uMain = userMainService.selectById(userMain.getUId());
        //填写密码
        String password = userMain.getPassword();
        if (StringUtils.isNotBlank(password)) {
            userMain.setPassword(PasswordUtils.entryptPassword(password, uMain.getSalt()));
        } else {
            userMain.setPassword(uMain.getPassword());
        }
        //填写邮箱
        String email = userMain.getEmail();
        if (StringUtils.isNotBlank(email)) {
            //邮箱转换小写
            userMain.setEmail(userMain.getEmail().toLowerCase());
            userMain.setEmailValidate("1");//邮箱是否通过验证(0否，1是)
        } else {
            userMain.setEmail(null);
            userMain.setEmailValidate("0");//邮箱是否通过验证(0否，1是)
        }
        //填写手机号
        String mobile = userMain.getMobile();
        if (StringUtils.isNotBlank(mobile)) {
            userMain.setMobileValidate("1");//手机号是否通过验证(0否，1是)
        } else {
            userMain.setMobile(null);
            userMain.setMobileValidate("0");//手机号是否通过验证(0否，1是)
        }
        userMainService.updateByIdSelective(userMain);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑会员成功"));
        return "redirect:" + adminPath + "/sso/userMain/memberList.do?repage";
    }

    /**
     * 会员买家删除（注意删除的是买家）
     *
     * 买家将被删除以下信息：
     * 删除会员总表记录
     * 删除会员卖家表记录
     * 删除会员扩展表记录
     * 删除会员扩展表_采购商记录
     * 删除会员扩展表_汽车服务门店记录
     * 删除会员会员扩展表_商家注册信息表记录
     * 删除入驻申请审核表记录
     * 删除入驻申请查看表记录
     * 删除收货地址表记录
     * 删除收藏商品表记录
     * 删除收藏店铺表记录
     * 删除充值表记录
     * 删除提现表记录
     * 删除预存款明细表记录
     * 删除系统消息表记录
     * 删除购物车记录
     *
     * @param userMain           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:member:drop")
    @RequestMapping(value = "memberDelete")
    public String memberDelete(UserMain userMain, RedirectAttributes redirectAttributes) {
        userMainService.cleanupMyWorld_DeleteUserEverything(userMain.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除买家会员成功"));
        return "redirect:" + adminPath + "/sso/userMain/memberList.do?repage";
    }

    /**
     * 卖家会员删除 （注意删除的是卖家）
     *
     * 对应的商家账号、店铺等全部信息将被彻底删除。
     *
     *
     * 商家将被删除以下信息：
     *
     * 删除会员总表(子账号)记录
     * 删除（角色和资源的中间表）记录
     * 删除卖家角色记录
     * 删除(卖家和角色的中间表)记录
     * 删除店铺表记录
     * 删除二级域名记录
     * 删除店铺商品分类记录
     * 删除店铺导航表内容记录
     * 删除店铺导航表记录
     * 删除店铺轮播图片记录
     * 删除店铺相册空间表记录
     * 删除相册夹表记录
     * 删除相册空间表记录
     * 删除店铺管理员操作日志记录
     * 删除商品SKU 删除商品区间价表 删除商品详情表 删除商品图片多对多中间表记录
     * 删除商品SPU记录
     * 删除店铺绑定品牌记录
     * 删除店铺装修表记录
     * 删除店铺物流公司中间表记录
     * 删除店铺客服表记录
     * 删除店铺文章表记录
     * 删除购物车表记录
     * 删除评论表记录
     * 删除咨询表记录
     * 删除投诉表记录
     * 删除运费模板、运费模板详情表记录
     * 删除结算账单表记录
     * 删除结算定时任务表记录
     * 删除采购空间表记录
     * 删除采购表记录
     * 删除交易凭证表记录
     * 删除采购咨询表记录
     * 删除账户表记录
     * 删除绑卡表记录
     * 删除提现表记录
     *
     * ------------------------------
     * 买家将被删除以下信息： （商家也是一个买家）
     *
     * 删除会员总表记录
     * 删除会员卖家表记录
     * 删除会员扩展表记录
     * 删除会员扩展表_采购商记录
     * 删除会员扩展表_汽车服务门店记录
     * 删除会员会员扩展表_商家注册信息表记录
     * 删除入驻申请审核表记录
     * 删除入驻申请查看表记录
     * 删除收货地址表记录
     * 删除收藏商品表记录
     * 删除收藏店铺表记录
     * 删除充值表记录
     * 删除提现表记录
     * 删除预存款明细表记录
     * 删除系统消息表记录
     * 删除购物车记录
     *
     * @param userMain           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:seller:drop")
    @RequestMapping(value = "sellerDelete")
    public String sellerDelete(UserMain userMain, RedirectAttributes redirectAttributes) {
        userMainService.cleanupMyWorld_DeleteUserEverything(userMain.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除卖家会员成功"));
        return "redirect:" + adminPath + "/sso/userMain/sellerList.do?repage";
    }

    /**
     * 锁定会员买家
     *
     * @param userMain           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:member:edit")
    @RequestMapping(value = "memberLocked")
    public String memberLocked(UserMain userMain, RedirectAttributes redirectAttributes) {
        userMainService.locked(userMain.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("锁定会员成功"));
        return "redirect:" + adminPath + "/sso/userMain/memberList.do?repage";
    }

    /**
     * 解锁会员买家
     *
     * @param userMain           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:member:edit")
    @RequestMapping(value = "memberUnLocked")
    public String memberUnLocked(UserMain userMain, RedirectAttributes redirectAttributes) {
        userMainService.unLocked(userMain.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("解锁会员成功"));
        return "redirect:" + adminPath + "/sso/userMain/memberList.do?repage";
    }

    /**
     * 锁定会员商家
     *
     * @param userMain           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:seller:edit")
    @RequestMapping(value = "sellerLocked")
    public String sellerLocked(UserMain userMain, RedirectAttributes redirectAttributes) {
        userMainService.locked(userMain.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("锁定会员成功"));
        return "redirect:" + adminPath + "/sso/userMain/sellerList.do?repage";
    }

    /**
     * 解锁会员商家
     *
     * @param userMain           实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sso:seller:edit")
    @RequestMapping(value = "sellerUnLocked")
    public String sellerUnLocked(UserMain userMain, RedirectAttributes redirectAttributes) {
        userMainService.unLocked(userMain.getUId());
        addMessage(redirectAttributes, FYUtils.fyParams("解锁会员成功"));
        return "redirect:" + adminPath + "/sso/userMain/sellerList.do?repage";
    }

    /**
     * 验证用户名是否重复
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "validateLoginName")
    public String validateLoginName(String oldLoginName, String loginName) {
        if (StringUtils.isNotBlank(loginName) && loginName.equals(oldLoginName)) {
            return "true";
        } else if (StringUtils.isNotBlank(loginName)) {
            //用户名转换小写
            loginName = loginName.toLowerCase();
            UserMain userMain = new UserMain();
            userMain.setLoginName(loginName);
            List<UserMain> userMainList = userMainService.selectByWhere(new Wrapper(userMain));
            if (userMainList.isEmpty()) {
                return "true";
            } else {
                return "false";
            }
        }
        return "false";
    }

    /**
     * 验证邮箱是否重复
     *
     * @param oldEmail
     * @param email
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "validateEmail")
    public String validateEmail(String oldEmail, String email) {
        if (StringUtils.isNotBlank(email) && email.equals(oldEmail)) {
            return "true";
        } else if (StringUtils.isNotBlank(email)) {
            //邮箱转换小写
            email = email.toLowerCase();
            UserMain userMain = new UserMain();
            userMain.setEmail(email);
            List<UserMain> userMainList = userMainService.selectByWhere(new Wrapper(userMain));
            if (userMainList.isEmpty()) {
                return "true";
            } else {
                return "false";
            }
        }
        return "false";
    }

    /**
     * 验证手机号是否重复
     *
     * @param oldMobile
     * @param mobile
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "validateMobile")
    public String validateMobile(String oldMobile, String mobile) {
        if (StringUtils.isNotBlank(mobile) && mobile.equals(oldMobile)) {
            return "true";
        } else if (StringUtils.isNotBlank(mobile)) {
            UserMain userMain = new UserMain();
            userMain.setMobile(mobile);
            List<UserMain> userMainList = userMainService.selectByWhere(new Wrapper(userMain));
            if (userMainList.isEmpty()) {
                return "true";
            } else {
                return "false";
            }
        }
        return "false";
    }

    /**
     * 新增店铺页面
     *
     * @param userMain
     * @param model
     * @return
     */
    @RequiresPermissions("sso:seller:save")
    @RequestMapping(value = "sellerSave1")
    public String sellerSave1(UserMain userMain, Model model) {
        String menu3 = "050101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        model.addAttribute("userMain", userMain);
        return "admin/sso/userSellerForm2";
    }

    /**
     * 新增商家
     *
     * @param userMain
     * @param model
     * @return
     */
    @RequiresPermissions("sso:seller:save")
    @RequestMapping(value = "sellerSave2")
    public String sellerSave2(UserMain userMain, String password2, Model model, RedirectAttributes redirectAttributes) {
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        //表单验证
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(userMain.getLoginName())) {
            errorList.add(FYUtils.fyParams("用户账号不能为空"));
        }
        if (StringUtils.isNotBlank(userMain.getLoginName())) {
            userMain.setLoginName(userMain.getLoginName().trim());
            Pattern regex = Pattern.compile("^[0-9a-zA-Z _-]{1,}$");
            Matcher matcher = regex.matcher(userMain.getLoginName());
            if (!matcher.matches() || "seller".equals(userMain.getLoginName()) || "admin".equals(userMain.getLoginName())) {
                errorList.add(FYUtils.fyParams("用户账号不符合规则，不能含有特殊符号"));
            }
        }
        if (siteRegister.getUsernameMax() < userMain.getLoginName().length()) {
            errorList.add(FYUtils.fyParams("长度不能大于")+ siteRegister.getUsernameMax());
        }
        if (siteRegister.getUsernameMin() > userMain.getLoginName().length()) {
            errorList.add(FYUtils.fyParams("长度不能小于")+ siteRegister.getUsernameMin());
        }

        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper().and("login_name=", userMain.getLoginName()));
        if (userMains != null && userMains.size() > 0) {
            errorList.add(FYUtils.fyParams("该用户账号已存在"));
        }
        if (StringUtils.isBlank(userMain.getPassword())) {
            errorList.add(FYUtils.fyParams("登录密码不能为空"));
        }
        userMain.setPassword(userMain.getPassword().trim());
        if (!userMain.getPassword().equals(password2.trim())) {
            errorList.add(FYUtils.fyParams("两次密码不相同"));
        }
        if (StringUtils.isNotBlank(userMain.getEmail()) && userMain.getEmail().length() > 64) {
            errorList.add(FYUtils.fyParams("邮箱长度不能大于64"));
        } else if (StringUtils.isNotBlank(userMain.getEmail())) {
            userMain.setEmail(userMain.getEmail().trim());
            userMains = userMainService.selectByWhere(new Wrapper().and("email=", userMain.getEmail()));
            if (userMains != null && userMains.size() > 0) {
                errorList.add(FYUtils.fyParams("该邮箱已被注册"));
            }
        } else {
            userMain.setEmail(null);
        }
        if (StringUtils.isNotBlank(userMain.getMobile()) && userMain.getMobile().length() > 64) {
            errorList.add(FYUtils.fyParams("电话长度不能大于64"));
        } else if (StringUtils.isNotBlank(userMain.getMobile())) {
            userMain.setMobile(userMain.getMobile().trim());
            userMains = userMainService.selectByWhere(new Wrapper().and("mobile=", userMain.getMobile()));
            if (userMains != null && userMains.size() > 0) {
                errorList.add(FYUtils.fyParams("该电话已被注册"));
            }
        } else {
            userMain.setMobile(null);
        }
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败"));
            addMessage(model, errorList.toArray(new String[]{}));
            //回显错误提示
            return sellerSave1(userMain, model);
        }

        //保存用户
        storeEnterAuthService.save(userMain);
        addMessage(redirectAttributes, FYUtils.fyParams("新增商家成功"));
        return "redirect:" + adminPath + "/sso/userMain/sellerList.do?repage";
    }

    /**
     * 表单验证
     *
     * @param userMain 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(UserMain userMain, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("uId"))) {
            errorList.add(FYUtils.fyParams("uId不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 12) {
            errorList.add(FYUtils.fyParams("uId最大长度不能超过12字符"));
        }
        if (StringUtils.isBlank(R.get("loginName"))) {
            errorList.add(FYUtils.fyParams("用户名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > 64) {
            errorList.add(FYUtils.fyParams("用户名最大长度不能超过64字符"));
        }
        //判断用户名是否重复
        UserMain uMain1 = new UserMain();
        uMain1.setLoginName(R.get("loginName"));
        Wrapper wrapper1 = new Wrapper();
        wrapper1.setEntity(uMain1);
        wrapper1.and("a.u_id !=", R.get("uId"));
        List<UserMain> userMainList1 = userMainService.selectByWhere(wrapper1);
        if (!userMainList1.isEmpty()) {
            errorList.add(FYUtils.fyParams("用户名不能重复"));
        }
        //判断邮箱是否重复
        if (StringUtils.isNotBlank(R.get("email"))) {
            if (R.get("loginName").length() > 64) {
                errorList.add(FYUtils.fyParams("邮箱最大长度不能超过64字符"));
            }
            UserMain uMain2 = new UserMain();
            uMain2.setEmail(R.get("email"));
            Wrapper wrapper2 = new Wrapper();
            wrapper2.setEntity(uMain2);
            wrapper2.and("a.u_id !=", R.get("uId"));
            List<UserMain> userMainList2 = userMainService.selectByWhere(wrapper2);
            if (!userMainList2.isEmpty()) {
                errorList.add(FYUtils.fyParams("邮箱不能重复"));
            }
        }
        //判断手机号是否重复
        if (StringUtils.isNotBlank(R.get("mobile"))) {
            if (R.get("mobile").length() > 64) {
                errorList.add(FYUtils.fyParams("手机号最大长度不能超过64字符"));
            }
            UserMain uMain3 = new UserMain();
            uMain3.setMobile(R.get("mobile"));
            Wrapper wrapper3 = new Wrapper();
            wrapper3.setEntity(uMain3);
            wrapper3.and("a.u_id !=", R.get("uId"));
            List<UserMain> userMainList3 = userMainService.selectByWhere(wrapper3);
            if (!userMainList3.isEmpty()) {
                errorList.add(FYUtils.fyParams("手机号不能重复"));
            }
        }
        //判断密码和确认密码是否一致
        String password = R.get("password");
        String repassword = R.get("repassword");
        if (StringUtils.isNotBlank(password) || StringUtils.isNotBlank(repassword)) {
            if (!password.equals(repassword)) {
                errorList.add(FYUtils.fyParams("密码和确认密码不一致"));
            }
        }
        return errorList;
    }

}