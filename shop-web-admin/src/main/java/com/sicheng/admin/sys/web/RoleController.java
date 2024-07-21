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
package com.sicheng.admin.sys.web;

import com.google.common.collect.Lists;
import com.sicheng.admin.sys.dao.SysUserRoleDao;
import com.sicheng.admin.sys.entity.*;
import com.sicheng.admin.sys.service.MenuService;
import com.sicheng.admin.sys.service.OfficeService;
import com.sicheng.admin.sys.service.RoleService;
import com.sicheng.admin.sys.service.UserService;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.Collections3;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色Controller
 *
 * @author zhaolei
 * @version 2013-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private MenuService sysMenuService;

    @Autowired
    private RoleService sysRoleService;

    @Autowired
    private UserService sysUserService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute("role")
    public Role get(@RequestParam(required = false) Long id, Model model) {
        String menu3 = "080110";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (id != null) {
            return sysRoleService.selectById(id);
        } else {
            return new Role();
        }
    }

    /**
     * 进入列表页
     *
     * @param role  实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:view")
    @RequestMapping(value = {"list", ""})
    public String list(Role role, Model model) {
        List<Role> list = sysRoleService.selectByWhere(new Wrapper(role));
        model.addAttribute("list", list);
        model.addAttribute("name", role.getName());
        return "admin/sys/sysRoleList";
    }

    /**
     * 进入添加页/编辑页(新页面)
     *
     * @param role  实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "form")
    public String form(Role role, Model model) {
        if (role != null && sysRoleService.selectById(role.getId()) != null) {
            Office office = sysRoleService.selectById(role.getId()).getOffice();
            if (office == null) {
                role.setOffice(UserUtils.getUser().getOffice());
            } else {
                role.setOffice(officeService.selectById(office.getId()));
            }
        } else {
            role.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("role", role);

        List<Menu> targetList = Lists.newArrayList();
        List<Menu> sourceList = sysMenuService.findAllMenu();//取用户可见的菜单
        Menu.sortList2Tree(targetList, sourceList, Menu.getRootId(), true);
        model.addAttribute("menuList", targetList);
        return "admin/sys/sysRoleForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param role               实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "save")
    public String save(Role role, Model model, RedirectAttributes redirectAttributes) {
        if (!UserUtils.getUser().isAdmin() && role.getSysData().equals(Global.YES)) {
            addMessage(redirectAttributes, FYUtils.fyParams("越权操作，只有超级管理员才能修改此数据！"));
            return "redirect:" + adminPath + "/sys/role.do?repage";
        }
        //表单验证
        List<String> errorList = validate(role, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(role, model);//回显错误提示
        }
        if (!"true".equals(ajaxCheckName(role.getOldName(), role.getName()))) {
            addMessage(model, FYUtils.fyParams("保存角色'") + role.getName() + FYUtils.fyParams("'失败, 角色名已存在"));
            return form(role, model);
        }
        if (!"true".equals(ajaxCheckName(role.getOldEnname(), role.getEnname()))) {
            addMessage(model, FYUtils.fyParams("保存角色'") + role.getName() + FYUtils.fyParams("'失败, 英文名已存在"));
            return form(role, model);
        }
        sysRoleService.saveRole(role);
        addMessage(redirectAttributes, FYUtils.fyParams("保存角色'") + role.getName() + FYUtils.fyParams("'成功"));
        String stat = R.get("submitBtn");
        if (!"".equals(stat) && "1".equals(stat)) {
            return "redirect:" + adminPath + "/sys/role/form.do";
        } else {
            return "redirect:" + adminPath + "/sys/role.do?repage";
        }
    }

    /**
     * 执行删除方法
     *
     * @param role               实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:role:drop")
    @RequestMapping(value = "delete")
    public String delete(Role role, RedirectAttributes redirectAttributes) {
        if (!UserUtils.getUser().isAdmin() && role.getSysData().equals(Global.YES)) {
            addMessage(redirectAttributes, FYUtils.fyParams("越权操作，只有超级管理员才能修改此数据！"));
            return "redirect:" + adminPath + "/sys/role.do?repage";
        }
        sysRoleService.deleteByWhere(new Wrapper(role));
        addMessage(redirectAttributes, FYUtils.fyParams("删除角色成功"));
        return "redirect:" + adminPath + "/sys/role.do?repage";
    }

    /**
     * 角色分配页面
     *
     * @param role
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "assign")
    public String assign(Role role, Model model) {
        //未分配用户
        List<User> inRoleuserList = new ArrayList<>();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(role.getId());
        List<SysUserRole> sysUserRoleList = sysUserRoleDao.selectByWhere(null, new Wrapper(sysUserRole));
        if (!sysUserRoleList.isEmpty()) {
            List<Long> userIds = new ArrayList<>();
            for (int i = 0; i < sysUserRoleList.size(); i++) {
                userIds.add(sysUserRoleList.get(i).getUserId());
            }
            if (!userIds.isEmpty()) {
                inRoleuserList = sysUserService.selectByIdIn(userIds);
            }
        }
        //列表数据
        List<User> notInRoleUserList = sysUserService.selectAll(new Wrapper(new User()));
        model.addAttribute("inRoleuserList", inRoleuserList);
        model.addAttribute("notInRoleUserList", notInRoleUserList);
        return "admin/sys/sysRoleAssignedList";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "assign2")
    public String assign2(Role role, Model model) {
        List<User> userList = new ArrayList<>();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(role.getId());
        List<SysUserRole> sysUserRoleList = sysUserRoleDao.selectByWhere(null, new Wrapper(sysUserRole));
        if (!sysUserRoleList.isEmpty()) {
            List<Long> userIds = new ArrayList<>();
            for (int i = 0; i < sysUserRoleList.size(); i++) {
                userIds.add(sysUserRoleList.get(i).getUserId());
            }
            if (!userIds.isEmpty()) {
                userList = sysUserService.selectByIdIn(userIds);
            }
        }
        model.addAttribute("userList", userList);
        return "admin/sys/roleAssign";
    }

    /**
     * 角色分配 -- 打开角色分配对话框
     *
     * @param role
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "usertorole")
    public String selectUserToRole(Role role, Model model) {
        List<User> userList = new ArrayList<>();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(role.getId());
        List<SysUserRole> sysUserRoleList = sysUserRoleDao.selectByWhere(null, new Wrapper(sysUserRole));
        if (!sysUserRoleList.isEmpty()) {
            List<Long> userIds = new ArrayList<>();
            for (int i = 0; i < sysUserRoleList.size(); i++) {
                userIds.add(sysUserRoleList.get(i).getUserId());
            }
            if (!userIds.isEmpty()) {
                userList = sysUserService.selectByIdIn(userIds);
            }
        }
        model.addAttribute("role", role);
        model.addAttribute("userList", userList);
        model.addAttribute("selectIds", Collections3.extractToString(userList, "name", ","));
        model.addAttribute("officeList", officeService.findAll());
        return "admin/sys/selectUserToRole";
    }

    /**
     * 角色分配 -- 从角色中移除用户
     *
     * @param userId
     * @param roleId
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:role:drop")
    @RequestMapping(value = "outrole")
    public String outrole(Long userId, Long roleId, RedirectAttributes redirectAttributes) {
        Role role = sysRoleService.selectById(roleId);
        User user = sysUserService.selectById(userId);
        if (UserUtils.getUser().getId().equals(userId)) {
            addMessage(redirectAttributes, FYUtils.fyParams("无法从角色【") + role.getName() + FYUtils.fyParams("】中移除用户【") + user.getName() + FYUtils.fyParams("】自己！"));
        } else {
            if (user.getRoleList().size() <= 1) {
                addMessage(redirectAttributes, FYUtils.fyParams("用户【") + user.getName() + FYUtils.fyParams("】从角色【") + role.getName() + FYUtils.fyParams("】中移除失败！这已经是该用户的唯一角色，不能移除。"));
            } else {
                Boolean flag = sysRoleService.outUserInRole(role, user);
                if (!flag) {
                    addMessage(redirectAttributes, FYUtils.fyParams("用户【") + user.getName() + FYUtils.fyParams("】从角色【") + role.getName() + FYUtils.fyParams("】中移除失败！"));
                } else {
                    addMessage(redirectAttributes, FYUtils.fyParams("用户【") + user.getName() + FYUtils.fyParams("】从角色【") + role.getName() + FYUtils.fyParams("】中移除成功！"));
                }
            }
        }
        return "redirect:" + adminPath + "/sys/role/assign.do?id=" + role.getId();
    }

    /**
     * 角色分配
     *
     * @param role
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "assignrole")
    public String assignRole(Role role, RedirectAttributes redirectAttributes) {
        StringBuilder msg = new StringBuilder();
        int newNum = 0;
        String inRoleUser = R.get("inRoleUser");
        if (StringUtils.isNotBlank(inRoleUser)) {
            String[] inRoleUsers = inRoleUser.split(",");
            for (int i = 0; i < inRoleUsers.length; i++) {
                User user = sysRoleService.assignUserToRole(role, sysUserService.selectById(Long.valueOf(inRoleUsers[i])));
                if (user != null) {
                    msg.append("<br/" + FYUtils.fyParams("新增用户【") + user.getName() + FYUtils.fyParams("】到角色【") + role.getName() + FYUtils.fyParams("】！"));
                    newNum++;
                }
            }
            addMessage(redirectAttributes, FYUtils.fyParams("已成功分配") + newNum + FYUtils.fyParams("个用户") + msg);
        }
        return "redirect:" + adminPath + "/sys/role/assign.do?id=" + role.getId();
    }

    /**
     * 验证角色名是否有效
     *
     * @param oldName
     * @param name
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "checkName")
    public String ajaxCheckName(String oldName, String name) {
        if (name != null && name.equals(oldName)) {
            return "true";
        }
        if (name != null) {
            Role role = new Role();
            role.setName(name);
            List<Role> roleList = sysRoleService.selectByWhere(new Wrapper(role));
            if (roleList.isEmpty()) {
                return "true";
            }
        }
        return "false";
    }

    /**
     * 验证角色英文名是否有效
     *
     * @param oldEnname
     * @param enname
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "checkEnname")
    public String ajaxCheckEnname(String oldEnname, String enname) {
        if (enname != null && enname.equals(oldEnname)) {
            return "true";
        }
        if (enname != null) {
            Role role = new Role();
            role.setEnname(enname);
            List<Role> roleList = sysRoleService.selectByWhere(new Wrapper(role));
            if (roleList.isEmpty()) {
                return "true";
            }
        }
        return "false";
    }

    /**
     * 表单验证
     *
     * @param role  实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Role role, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("请填写角色名称"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 100) {
            errorList.add(FYUtils.fyParams("角色名称最大长度不能超过100字符"));
        }
        if (StringUtils.isBlank(R.get("enname"))) {
            errorList.add(FYUtils.fyParams("请填写英文名称"));
        }
        if (StringUtils.isNotBlank(R.get("enname")) && R.get("enname").length() > 100) {
            errorList.add(FYUtils.fyParams("英文名称最大长度不能超过100字符"));
        }
        return errorList;
    }
}
