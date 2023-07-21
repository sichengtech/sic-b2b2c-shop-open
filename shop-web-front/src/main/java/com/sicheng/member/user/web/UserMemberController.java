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
package com.sicheng.member.user.web;

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.member.interceptor.MemberMenuInterceptor;
import com.sicheng.seller.site.service.SiteRegisterService;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.seller.sys.service.SysVariableService;
import com.sicheng.sso.service.UserMemberService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>标题: 账户信息</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年5月10日 上午10:30:52
 */
@Controller
@RequestMapping(value = "${memberPath}/user/userMember")
public class UserMemberController extends BaseController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private SiteRegisterService siteRegisterService;

    @Autowired
    private UserMemberService userMemberService;

    @Autowired
    private SysVariableService variableService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting("userMember");//三级菜单高亮
    }

    /**
     * 账户信息页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(Model model) {
        UserMain userMain = SsoUtils.getUserMain();
        //获取用户名规则
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        model.addAttribute("siteRegister", siteRegister);
        model.addAttribute("userMain", userMain);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        return "member/user/userMemberSave";
    }

    /**
     * 保存账户信息
     *
     * @param userMain
     * @param userMember
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(UserMain userMain, UserMember userMember, Model model, RedirectAttributes redirectAttributes) {
        //获取后台会员名修改次数
        SysVariable variable = variableService.getSysVariable("user_name_update_num");
        Long loginNameEditCount = 0L;//默认值，0代表可以随意修改
        if (variable != null && StringUtils.isNotBlank(variable.getValue())) {
            if (StringUtils.isNumeric(variable.getValue())) {
                loginNameEditCount = Long.parseLong(variable.getValue());
            }
        }
        //表单验证
        List<String> errorList = validate(userMember, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(model);//回显错误提示
        }
        //用户名转小写
        userMain.setLoginName(userMain.getLoginName().toLowerCase());
        String oldLoginName = R.get("oldLoginName");
        String rab = userMemberService.update(oldLoginName, userMain, userMember, loginNameEditCount);
        addMessage(redirectAttributes, rab);
        return "redirect:" + memberPath + "/user/userMember/save1.htm?repage";
    }

    /**
     * 表单验证(编辑)
     *
     * @param userMember 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(UserMember userMember, Model model) {
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("loginName"))) {
            errorList.add(FYUtils.fyParams("用户名不能为空"));
            return errorList;
        }
        if (StringUtils.isBlank(R.get("oldLoginName"))) {
            errorList.add(FYUtils.fyParams("用户名不能为空"));
            return errorList;
        }
        if (!(StringUtils.isNotBlank(R.get("loginName")) && StringUtils.isNotBlank(R.get("oldLoginName")) && R.get("loginName").equals(R.get("oldLoginName")))) {
            if (!SsoUtils.checkLoginName(R.get("loginName"))) {
                errorList.add(FYUtils.fyParams("用户名已存在"));
            }
            if (siteRegister != null) {
                if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > siteRegister.getUsernameMax()) {
                    errorList.add(FYUtils.fyParams("用户名不能超过") + siteRegister.getUsernameMax() + FYUtils.fyParams("字符"));
                }
                if (StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() < siteRegister.getUsernameMin()) {
                    errorList.add(FYUtils.fyParams("用户名不能少于") + siteRegister.getUsernameMin() + FYUtils.fyParams("字符"));
                }
                if (StringUtils.isNotBlank(R.get("loginName")) && StringUtils.isNotBlank(siteRegister.getDisableUsername())) {
                    String[] disableName = siteRegister.getDisableUsername().split(",");
                    for (int i = 0; i < disableName.length; i++) {
                        if (R.get("loginName").equals(disableName)) {
                            errorList.add(FYUtils.fyParams("用户名不能是") + disableName);
                        }
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(R.get("realName")) && R.get("realName").length() > 64) {
            errorList.add(FYUtils.fyParams("真实姓名最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isNotBlank(R.get("qq")) && R.get("qq").length() > 64) {
            errorList.add(FYUtils.fyParams("qq最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isNotBlank(R.get("microblog")) && R.get("microblog").length() > 64) {
            errorList.add(FYUtils.fyParams("微博最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isNotBlank(R.get("weChat")) && R.get("weChat").length() > 64) {
            errorList.add(FYUtils.fyParams("微信最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        return errorList;
    }

}
