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
package com.sicheng.admin.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sicheng.admin.sys.entity.Office;
import com.sicheng.admin.sys.entity.Role;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.admin.sys.service.*;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.beanvalidator.BeanValidators;
import com.sicheng.common.config.Global;
import com.sicheng.common.excel.ExportExcel;
import com.sicheng.common.excel.ImportExcel;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户Controller
 *
 * @author zhaolei
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

    @Autowired
    private UserService sysUserService;



    @Autowired
    private RoleService sysRoleService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private OfficeService officeService;

    @ModelAttribute
    public User get(@RequestParam(required = false) Long id, Model model) {
        String menu3 = "080105";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (id != null) {
            return sysUserService.selectById(id);
        } else {
            return new User();
        }
    }

    /**
     * 管理员列表页面
     *
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"list", ""})
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<User> page = sysUserService.selectByWhere(new Page<User>(request, response), new Wrapper(user));
        model.addAttribute("page", page);
        return "admin/sys/sysAdminList";
    }

    /**
     * 管理员进入新增页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:save")
    @RequestMapping(value = "save1")
    public String save1(Model model) {
        User user = new User();
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }
        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", sysRoleService.selectByWhere(new Wrapper(new Role())));

        return "admin/sys/sysAdminSave";
    }

    /**
     * 新增管理员保存方法
     *
     * @return
     */
    @RequiresPermissions("sys:user:save")
    @RequestMapping(value = "save2")
    public String save2(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = new ArrayList<String>();
        //验证工号是否为数字或者是否为空
        String no = user.getNo();
        if (no == null || "".equals(no)) {
            errorList.add(FYUtils.fyParams("工号不能为空"));
        }
        //姓名是否为空
        String name = user.getName();
        if (name == null || "".equals(name)) {
            errorList.add(FYUtils.fyParams("姓名不能为空"));
        }
        String loginName = user.getLoginName();
        if (loginName == null || "".equals(loginName)) {
            errorList.add(FYUtils.fyParams("登录名不能为空"));
        }
        //判断登录名是否重复
        if (!"true".equals(ajaxCheckLoginName(user.getOldLoginName(), user.getLoginName()))) {
            errorList.add(FYUtils.fyParams("保存用户'") + user.getLoginName() + FYUtils.fyParams("'失败，登录名已存在"));
        }
        //判断密码
        String password = user.getNewPassword();
        if (password == null || "".equals(password)) {
            errorList.add(FYUtils.fyParams("密码不能为空"));
        }
        //判断密码是否6位以上
        String regexPassword = "^\\S{6,}$";
        Pattern patternPassword = Pattern.compile(regexPassword);
        Matcher matcherPassword = patternPassword.matcher(password);
        if (!matcherPassword.matches()) {
            errorList.add(FYUtils.fyParams("密码请填6位以上数字"));
        }
        //判断确认密码和密码的一致性
        String confirmNewPassword = R.get("confirmNewPassword");
        if (!password.equals(confirmNewPassword)) {
            errorList.add(FYUtils.fyParams("两个密码不一致"));
        }
        //判断是否选择用户角色
        List<Role> roleList = Lists.newArrayList();
        List<Long> roles = user.getRoleIdList();
        String[] roleIds = R.getArray("roleIds");
        for (int i = 0; i < roleIds.length; i++) {
            roles.add(Long.parseLong(roleIds[i]));
        }
        if (roles.isEmpty()) {
            errorList.add(FYUtils.fyParams("用户角色不能为空"));
        }
        //验证未通过
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(model);//回显错误提示
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        for (Role r : sysRoleService.findAllRole()) {
            if (roles.contains(r.getId())) {
                roleList.add(r);
            }
        }
        user.setPassword(sysUserService.entryptPassword(password));
        user.setRoleList(roleList);
        //判断是否锁定传上来的值
        user.setLoginFlag(R.get("loginFlag", "0"));
        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        user.setCompany(new Office(R.getLong("company.id")));
        user.setOffice(new Office(R.getLong("office.id")));

        // 保存用户信息
        sysUserService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            //UserUtils.clearCache();
        }
        addMessage(redirectAttributes, FYUtils.fyParams("保存用户'") + user.getLoginName() + FYUtils.fyParams("'成功"));
        if ("1".equals(R.get("submit"))) {
            return "redirect:" + adminPath + "/sys/user/save1.do?repage";
        } else if ("2".equals(R.get("submit"))) {
            return "redirect:" + adminPath + "/sys/user/list.do?repage";
        }
        return null;
    }

    /**
     * 管理员进入编辑页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "edit1")
    public String edit1(Model model) {
        Long id = R.getLong("id");
        User user = sysUserService.selectById(id);
        model.addAttribute("user", user);
        if (user.getCompany() != null) {
            user.setCompany(officeService.selectById(user.getCompany().getId()));
        }
        if (user.getOffice() != null) {
            user.setOffice(officeService.selectById(user.getOffice().getId()));
        }
        model.addAttribute("allRoles", sysRoleService.selectByWhere(new Wrapper(new Role())));
        return "admin/sys/sysAdminEdit";
    }

    /**
     * 编辑管理员保存方法
     *
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "edit2")
    public String edit2(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = new ArrayList<>();
        //验证工号是否为数字或者是否为空
        String no = user.getNo();
        if (no == null || "".equals(no)) {
            errorList.add(FYUtils.fyParams("工号不能为空"));
        }
        //姓名是否为空
        String name = user.getName();
        if (name == null || "".equals(name)) {
            errorList.add(FYUtils.fyParams("姓名不能为空"));
        }
        String loginName = user.getLoginName();
        if (loginName == null || "".equals(loginName)) {
            errorList.add(FYUtils.fyParams("登录名不能为空"));
        }
        //判断登录名是否重复
        if (!"true".equals(ajaxCheckLoginName(user.getOldLoginName(), user.getLoginName()))) {
            errorList.add(FYUtils.fyParams("保存用户'") + user.getLoginName() + FYUtils.fyParams("'失败，登录名已存在"));
        }
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            //判断密码是否6位以上
            String regexPassword = "^\\S{6,}$";
            Pattern patternPassword = Pattern.compile(regexPassword);
            Matcher matcherPassword = patternPassword.matcher(user.getNewPassword());
            if (!matcherPassword.matches()) {
                errorList.add(FYUtils.fyParams("密码请填6位以上数字"));
            }
            //判断确认密码和密码的一致性
            String confirmNewPassword = R.get("confirmNewPassword");
            if (!user.getNewPassword().equals(confirmNewPassword)) {
                errorList.add(FYUtils.fyParams("两个密码不一致"));
            }
            user.setPassword(sysUserService.entryptPassword(user.getNewPassword()));
        }
        //判断是否选择用户角色
        List<Role> roleList = Lists.newArrayList();
        List<Long> roles = user.getRoleIdList();
        String[] roleIds = R.getArray("roleIds");
        for (int i = 0; i < roleIds.length; i++) {
            roles.add(Long.parseLong(roleIds[i]));
        }
        if (roles.isEmpty()) {
            errorList.add(FYUtils.fyParams("用户角色不能为空"));
        }
        //验证未通过
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(model);//回显错误提示
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        for (Role r : sysRoleService.findAllRole()) {
            if (roles.contains(r.getId())) {
                roleList.add(r);
            }
        }
        // 如果新密码为空，则不更换密码
        if (user.getNewPassword() == null || "".equals(user.getNewPassword())) {
            User u = sysUserService.selectById(user.getId());
            user.setPassword(u.getPassword());
        }
        user.setRoleList(roleList);
        //判断是否锁定传上来的值
        user.setLoginFlag(R.get("loginFlag", "0"));

        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        user.setCompany(new Office(R.getLong("company.id")));
        user.setOffice(new Office(R.getLong("office.id")));

        // 保存用户信息
        sysUserService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            //UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }
        addMessage(redirectAttributes, FYUtils.fyParams("保存用户'") + user.getLoginName() + FYUtils.fyParams("'成功"));
        return "redirect:" + adminPath + "/sys/user/list.do?repage";
    }

    /**
     * 删除管理员
     *
     * @return
     */
    @RequiresPermissions("sys:user:drop")
    @RequestMapping(value = "delete")
    public String delete(User user, RedirectAttributes redirectAttributes) {
        if (UserUtils.getUser().getId().equals(user.getId())) {
            addMessage(redirectAttributes, FYUtils.fyParams("删除用户失败, 不允许删除当前用户"));
        } else if (User.isAdmin(user.getId())) {
            addMessage(redirectAttributes, FYUtils.fyParams("删除用户失败, 不允许删除超级管理员用户"));
        } else {
            sysUserService.deleteByWhere(new Wrapper(user));
            addMessage(redirectAttributes, FYUtils.fyParams("删除用户") + user.getLoginName() + FYUtils.fyParams("成功"));
        }
        // 清除缓存
        //UserUtils.clearCache();
        return "redirect:" + adminPath + "/sys/user/list.do?repage";
    }

    /**
     * 导入用户数据
     *
     * @param file
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<User> list = ei.getDataList(User.class);
            for (User user : list) {
                try {
                    if ("true".equals(ajaxCheckLoginName("", user.getLoginName()))) {
                        user.setPassword(sysUserService.entryptPassword("123456"));
                        BeanValidators.validateWithException(validator, user);
                        sysUserService.saveUser(user);
                        successNum++;
                    } else {
                        failureMsg.append("<br/>" + FYUtils.fyParams("登录名") + user.getLoginName() + FYUtils.fyParams("已存在;"));
                        failureNum++;
                    }
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>" + FYUtils.fyParams("登录名") + user.getLoginName() + FYUtils.fyParams("导入失败："));
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>" + FYUtils.fyParams("登录名") + user.getLoginName() + FYUtils.fyParams("导入失败：") + ex.getMessage());
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, FYUtils.fyParams("，失败") + failureNum + FYUtils.fyParams("条用户，导入信息如下："));
            }
            addMessage(redirectAttributes, FYUtils.fyParams("已成功导入") + successNum + FYUtils.fyParams("条用户") + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, FYUtils.fyParams("导入用户失败！失败信息：") + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list.do?repage";
    }

    /**
     * 下载导入用户数据模板
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户数据导入模板.xlsx";
            List<User> list = Lists.newArrayList();
            list.add(UserUtils.getUser());
            new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, FYUtils.fyParams("导入模板下载失败！失败信息：") + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list.do?repage";
    }

    /**
     * 用户信息显示
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "info")
    public String info(User user, HttpServletResponse response, Model model) {
        User currentUser = UserUtils.getUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("Global", new Global());

        String menu3 = "010101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        //只查看
        return "admin/sys/sysUserInfo";

    }

    /**
     * 用户信息保存
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "saveInfo")
    public String saveInfo(User user, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        user.preUpdate(user);
        sysUserService.updateByIdSelective(user);
        addMessage(redirectAttributes, FYUtils.fyParams("保存用户信息成功"));
        //保存后重定向
        return "redirect:" + adminPath + "/sys/user/info.do?repage";
    }

    /**
     * 修改个人用户密码
     *
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(String oldPassword, String newPassword, Model model) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
            if (systemService.validatePassword(oldPassword, user.getPassword())) {
                User u = new User();
                u.setId(user.getId());
                u.setPassword(sysUserService.entryptPassword(newPassword));
                sysUserService.updateByIdSelective(u);
                model.addAttribute("message", FYUtils.fyParams("修改密码成功"));
            } else {
                model.addAttribute("message", FYUtils.fyParams("修改密码失败，旧密码错误"));
            }
        }
        model.addAttribute("user", user);
        String menu3 = "010102";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        return "admin/sys/sysChangePassword";
    }

    /**
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     * @param officeId
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) Long officeId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        User user = new User();
        user.setOffice(new Office(officeId));
        List<User> list = sysUserService.selectByWhere(new Wrapper(user));

        for (int i = 0; i < list.size(); i++) {
            User e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "u_" + e.getId());
            map.put("pId", officeId);
            map.put("name", StringUtils.replace(e.getName(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 返回用户信息
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "infoData")
    public User ajaxInfoData() {
        return UserUtils.getUser();
    }

    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @ResponseBody
    @RequestMapping(value = "checkLoginName")
    public String ajaxCheckLoginName(String oldLoginName, String loginName) {
        if (loginName != null && loginName.equals(oldLoginName)) {
            return "true";
        }
        if (loginName != null) {
            User u = new User();
            u.setLoginName(loginName);
            List<User> users = sysUserService.selectByWhere(new Wrapper(u));
            if (users.isEmpty()) {
                return "true";
            }
        }
        return "false";
    }

    /**
     * 修改语言页面
     *
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "editLanguage")
    public String editLanguage(HttpServletRequest request, Model model) {
        RequestContext requestContext = new RequestContext(request);
        String language = requestContext.getLocale().getLanguage();
        String menu3 = "010102";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        model.addAttribute("language", language);
        return "admin/sys/sysLanguageForm";
    }
}
