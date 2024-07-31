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
import com.google.common.collect.Maps;
import com.sicheng.admin.sys.entity.Area;
import com.sicheng.admin.sys.service.AreaService;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.BaseEntity;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 区域Controller
 *
 * @author zhaolei
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/area")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;


    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute("area")
    public Area get(@RequestParam(required = false) Long id, Model model) {

        String menu3 = "020201";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (id != null) {
            return areaService.selectById(id);
        } else {
            return new Area();
        }
    }

    /**
     * 进入列表页
     *
     * @param area  实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:area:view")
    @RequestMapping(value = {"list", ""})
    public String list(Area area, Model model) {
        String name = R.get("name");
        String largeArea = R.get("largeArea");
        area.setSort(null);
        area.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        Wrapper wrapper = new Wrapper();
        if (StringUtils.isNotBlank(name)){
        	wrapper.and("a.name like", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(largeArea)) {
        	wrapper.and("a.large_area =", largeArea);
        }
        if(StringUtils.isBlank(name) && StringUtils.isBlank(largeArea)){
            if (area.getParent() == null || area.getParent().getId() == null) {
                Area a = new Area();
                a.setId(0L);
                area.setParent(a);
            }
        }
        wrapper.setEntity(area);
        wrapper.orderBy("sort");
        List<Area> list = areaService.selectByWhere(wrapper);
        model.addAttribute("list", list);
        model.addAttribute("name", name);
        return "admin/logistics/logisticsAreaList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param area  实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:area:edit")
    @RequestMapping(value = "form")
    public String form(Area area, Model model) {
        if (area.getParent() == null || area.getParent().getId() == null) {
            area.setParent(areaService.selectById(1L));
            area.setParentIds(areaService.selectById(1L).getParentIds() + "1");
        } else {
            area.setParent(areaService.selectById(area.getParent().getId()));
            if (area.getParent() != null) {
                area.setParentIds(areaService.selectById(area.getParent().getId()).getParentIds() + area.getParent().getId());
            }
        }
        if (area != null && StringUtils.isNotBlank(area.getParentIds())) {
            String[] parentIds = area.getParentIds().split(",");
            model.addAttribute("idsLength", parentIds.length);
        }
        model.addAttribute("area", area);
        return "admin/logistics/logisticsAreaForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param area               实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:area:edit")
    @RequestMapping(value = "save")
    public String save(Area area, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(area, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(area, model);//回显错误提示
        }
        String preIds = R.get("preIds");
        if (preIds.split(",").length > 2) {
            area.setLargeArea("");
        }
        area.preInsert(UserUtils.getUser());
        areaService.save(area);
        addMessage(redirectAttributes, FYUtils.fy("保存区域")+"'" + area.getName() + "'"+FYUtils.fy("成功"));
        String stat = R.get("submit");
        if (!"".equals(stat) && "1".equals(stat)) {
            return "redirect:" + adminPath + "/sys/area/form.do?repage&parent.id=" + area.getParentId();
        } else {
            return "redirect:" + adminPath + "/sys/area.do?repage";
        }
    }

    /**
     * 执行删除方法
     *
     * @param area               实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:area:drop")
    @RequestMapping(value = "delete")
    public String delete(Area area, RedirectAttributes redirectAttributes) {
        areaService.delete(area);
        addMessage(redirectAttributes, FYUtils.fy("删除区域成功"));
        return "redirect:" + adminPath + "/sys/area.do?repage";
    }

    /**
     * 获取地区树的数据
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     *
     * @param extId 排除的ID
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Area> list = areaService.findAll();
        for (int i = 0; i < list.size(); i++) {
            Area e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                map.put("parentIds", e.getParentIds());
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 三级联动地区获取
     * 2017-1-12  cl
     *
     * @param response
     * @param parentId
     * @param model
     * @return
     * @throws IOException
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "selectArea")
    public Object ajaxSelectCity(HttpServletResponse response, String parentId, Model model) throws IOException {
        List<Area> list = areaService.selectByWhere(new Wrapper().and("parent_id=", parentId));
        String json = JsonMapper.getInstance().toJson(list);
        R.writeJson(response, json, "UTF-8");
        return null;
    }

    /**
     * 表单验证
     *
     * @param area  实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Area area, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fy("请填写地区名称"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 100) {
            errorList.add(FYUtils.fy("地区名称最大长度不能超过100字符"));
        }
        return errorList;
    }
}
