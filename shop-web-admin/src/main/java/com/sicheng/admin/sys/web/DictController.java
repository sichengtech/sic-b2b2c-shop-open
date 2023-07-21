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
import com.sicheng.admin.sys.entity.Dict;
import com.sicheng.admin.sys.service.DictService;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 字典Controller
 *
 * @author zhaolei
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;


    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public Dict get(@RequestParam(required = false) Long id, Model model) {
        String menu3 = "080120";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        if (id != null) {
            return dictService.selectById(id);
        } else {
            return new Dict();
        }
    }

    /**
     * 进入列表页
     *
     * @param dict     实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:dict:view")
    @RequestMapping(value = {"list", ""})
    public String list(Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Map<String, String>> typeMap = dictService.findTypeList();
        model.addAttribute("typeMap", typeMap);
//        Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict);

        Wrapper w=new Wrapper(dict);
        w.orderBy("type, sort, update_date DESC");
        Page<Dict> page =new Page<Dict>(request, response);
        page = dictService.selectByWhere(page, w);
        model.addAttribute("page", page);
        model.addAttribute("dictType", dict.getType());
        //return "admin/sys/dictList";
        return "admin/sys/sysDictList";
    }

    /**
     * 进入添加页/编辑页
     *
     * @param dict  实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:dict:edit")
    @RequestMapping(value = "form")
    public String form(Dict dict, Model model) {
        model.addAttribute("dict", dict);
        return "admin/sys/sysDictForm";
    }

    /**
     * 执行添加方法/编辑方法
     *
     * @param dict               实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:dict:edit")
    @RequestMapping(value = "save")//@Valid
    public String save(Dict dict, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(dict, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(dict, model);//回显错误提示
        }
        //过滤同类型相同键值的字典数据
        dict.setDelFlag("0");
        Integer number = dictService.colation(dict);
        if (number == 0) {
            if (dict.getId() != null) {
                dictService.updateById(dict);
            } else {
                dictService.save(dict);
            }
        } else {
            model.addAttribute("message", FYUtils.fyParams("类型:") + dict.getType() + FYUtils.fyParams("的字典,值:") + dict.getValue() + FYUtils.fyParams("已存在，请重新添加"));
            return "admin/sys/sysDictForm";
        }
        addMessage(redirectAttributes, FYUtils.fyParams("保存字典'") + dict.getLabel() + FYUtils.fyParams("'成功"));
        return "redirect:" + adminPath + "/sys/dict.do?repage";
    }

    /**
     * 执行删除方法
     *
     * @param dict               实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:dict:drop")
    @RequestMapping(value = "delete")
    public String delete(Dict dict, RedirectAttributes redirectAttributes) {
        dictService.delete(dict);
        addMessage(redirectAttributes, FYUtils.fyParams("删除字典成功"));
        return "redirect:" + adminPath + "/sys/dict.do?repage";
    }

    /**
     * 获取字典树的数据
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     *
     * @param type     字典类型
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String type, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Dict dict = new Dict();
        dict.setType(type);
        List<Dict> list = dictService.findList(dict);
        for (int i = 0; i < list.size(); i++) {
            Dict e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("pId", e.getParent().getId());
            map.put("name", StringUtils.replace(e.getLabel(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 按字典类型查询数据
     *
     * @param type 字典类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listData")
    public List<Dict> listData(@RequestParam(required = false) String type) {
        Dict dict = new Dict();
        dict.setType(type);
        return dictService.findList(dict);
    }

    /**
     * 表单验证
     *
     * @param dict  实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Dict dict, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("value"))) {
            errorList.add(FYUtils.fyParams("请填写值"));
        }
        System.out.println(R.get("value").length());
        if (StringUtils.isNotBlank(R.get("value")) && R.get("value").length() > 100) {
            errorList.add(FYUtils.fyParams("值最大长度不能超过100字符"));
        }
        if (StringUtils.isBlank(R.get("label"))) {
            errorList.add(FYUtils.fyParams("请填写显示标签"));
        }
        if (StringUtils.isNotBlank(R.get("label")) && R.get("label").length() > 100) {
            errorList.add(FYUtils.fyParams("显示标签最大长度不能超过100字符"));
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add(FYUtils.fyParams("请填写类型"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 100) {
            errorList.add(FYUtils.fyParams("类型最大长度不能超过100字符"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fyParams("请填写排序"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        if (StringUtils.isBlank(R.get("description"))) {
            errorList.add(FYUtils.fyParams("请填写描述"));
        }
        if (StringUtils.isNotBlank(R.get("description")) && R.get("description").length() > 100) {
            errorList.add(FYUtils.fyParams("描述最大长度不能超过100字符"));
        }
        if (StringUtils.isNotBlank(R.get("remarks")) && R.get("remarks").length() > 255) {
            errorList.add(FYUtils.fyParams("备注最大长度不能超过255字符"));
        }
        return errorList;
    }
}
