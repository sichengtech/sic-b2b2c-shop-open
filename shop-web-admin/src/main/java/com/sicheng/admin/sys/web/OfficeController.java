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
import com.sicheng.admin.sys.entity.Area;
import com.sicheng.admin.sys.entity.Office;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.admin.sys.service.AreaService;

import com.sicheng.admin.sys.service.OfficeService;
import com.sicheng.admin.sys.service.UserService;
import com.sicheng.admin.sys.utils.DictUtils;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 机构Controller
 *
 * @author zhaolei
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private AreaService areaService;
    @Autowired
    private UserService userService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     * @return
     */
    @ModelAttribute("office")
    public Office get(@RequestParam(required = false) Long id, Model model) {
        String menu3 = "080101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (id != null) {
            return officeService.selectById(id);
        } else {
            return new Office();
        }
    }

    /**
     * 进入列表页
     *
     * @param office 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:office:view")
    @RequestMapping(value = "list")
    public String list(Office office, Model model) {
        List<Office> list = officeService.findAll();
        model.addAttribute("list", list);
        return "admin/sys/sysOfficeList";
    }

    /**
     * 查询
     *
     * @param office  实体对象
     * @param model
     * @return 查询出当前条件下的部门以及所有父部门
     */
    @RequiresPermissions("sys:office:view")
    @RequestMapping(value = "search")
    public String search(Office office, Model model) {
        List<Office> officeList = officeService.selectByWhere(new Wrapper(office).orderBy("a.code"));
        if (!officeList.isEmpty()) {
            List<Object> officeIds = new ArrayList<>();
            for (Office a : officeList) {
                if (!"".equals(a.getParentIds())) {
                    String[] parentIds = a.getParentIds().split(",");
                    for (String id : parentIds) {
                        if (!"0".equals(id) && !officeIds.contains(id)) {
                            officeIds.add(id);
                        }
                    }
                }
            }
            if (!officeIds.isEmpty()) {
                List<Office> parentOfficeList = officeService.selectByIdIn(officeIds);
                if (parentOfficeList.size() > 0) {
                    for (Office of : parentOfficeList) {
                        if (!officeList.contains(of)) {
                            officeList.add(of);
                        }
                    }
                }
            }
            //组装地区
            if (!officeList.isEmpty()) {
                List<Long> areaIds = new ArrayList<Long>();
                for (int i = 0; i < officeList.size(); i++) {
                    areaIds.add(officeList.get(i).getArea().getId());
                }
                List<Area> areaList = areaService.selectByIdIn(areaIds);
                for (int i = 0; i < officeList.size(); i++) {
                    for (int j = 0; j < areaList.size(); j++) {
                        if (officeList.get(i).getArea().getId().equals(areaList.get(j).getId())) {
                            officeList.get(i).setArea(areaList.get(j));
                        }
                    }
                }
            }
        }
        model.addAttribute("list", officeList);
        model.addAttribute("name", office.getName());
        model.addAttribute("type", office.getType());
        return "admin/sys/sysOfficeList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param office 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:office:edit")
    @RequestMapping(value = "form")
    public String form(Office office, Model model) {
        User user = UserUtils.getUser();
        if (office.getParent() == null || office.getParent().getId() == null) {
            office.setParent(user.getOffice());
        }
        office.setParent(officeService.selectById(office.getParent().getId()));
        if (office.getArea() == null) {
            office.setArea(areaService.selectOne(new Wrapper().and("parent_id=", 0)));
        } else {
            office.setArea(areaService.selectById(office.getArea().getId()));
        }
        if (office.getPrimaryPerson() == null) {
            office.setPrimaryPerson(user.getOffice().getPrimaryPerson());
        } else {
            office.setPrimaryPerson(userService.selectById(office.getPrimaryPerson().getId()));
        }
        if (office.getDeputyPerson() == null) {
            office.setDeputyPerson(user.getOffice().getDeputyPerson());
        } else {
            office.setDeputyPerson(userService.selectById(office.getDeputyPerson().getId()));
        }
        // 自动获取排序号
        if (office.getId() == null && office.getParent() != null) {
            int size = 0;
            List<Office> list = officeService.findAll();
            for (int i = 0; i < list.size(); i++) {
                Office e = list.get(i);
                if (e.getParent() != null && e.getParent().getId() != null
                        && e.getParent().getId().equals(office.getParent().getId())) {
                    size++;
                }
            }
            office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size + 1 : 1), 3, "0"));
        }
        model.addAttribute("office", office);
        return "admin/sys/sysOfficeForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param office             实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:office:edit")
    @RequestMapping(value = "save")
    public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(office, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(office, model);//回显错误提示
        }
        //业务处理
        office.preInsert(UserUtils.getUser());
        officeService.save(office);
        office.setUseable(R.get("useable", "0"));
        if (office.getChildDeptList() != null) {
            Office childOffice = null;
            for (String id : office.getChildDeptList()) {
                childOffice = new Office();
                childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", FYUtils.fyParams("未知")));
                childOffice.setParent(office);
                childOffice.setArea(office.getArea());
                childOffice.setType("2");
                childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade()) + 1));
                childOffice.setUseable(Global.YES);
                childOffice.preInsert(UserUtils.getUser());
                officeService.save(childOffice);
            }
        }

        addMessage(redirectAttributes, FYUtils.fyParams("保存机构'") + office.getName() + FYUtils.fyParams("'成功"));
        return "redirect:" + adminPath + "/sys/office/list.do";
    }

    /**
     * 执行删除方法
     *
     * @param office             实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:office:drop")
    @RequestMapping(value = "delete")
    public String delete(Office office, RedirectAttributes redirectAttributes) {
        officeService.delete(office);
        addMessage(redirectAttributes, FYUtils.fyParams("删除机构成功"));
        return "redirect:" + adminPath + "/sys/office/list.do";
    }

    /**
     * 获取机构JSON数据。
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     *
     * @param extId    排除的ID
     * @param type     类型（1：公司；2：部门/小组/其它：3：用户）
     * @param grade    显示级别
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Office> list = officeService.selectAll(new Wrapper(new Office()));
        for (int i = 0; i < list.size(); i++) {
            Office e = list.get(i);
            if ((StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1))
                    && (type == null || (type != null && (!type.equals("1") || type.equals(e.getType()))))
                    && (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
                    && Global.YES.equals(e.getUseable())) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("pIds", e.getParentIds());
                map.put("name", e.getName());
                if (type != null && "3".equals(type)) {
                    map.put("isParent", true);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 表单验证
     *
     * @param office 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Office office, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("请填写机构名称"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 100) {
            errorList.add(FYUtils.fyParams("机构名称最大长度不能超过100字符"));
        }
        if (StringUtils.isBlank(R.get("useable"))) {
            errorList.add(FYUtils.fyParams("请选择是否可用"));
        }
        return errorList;
    }
}
