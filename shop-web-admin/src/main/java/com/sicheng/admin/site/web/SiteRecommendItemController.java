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
package com.sicheng.admin.site.web;

import com.alibaba.fastjson.JSONArray;
import com.sicheng.admin.site.entity.SiteRecommendItem;
import com.sicheng.admin.site.service.SiteRecommendItemService;

import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐位详情 Controller
 * 所属模块：site
 *
 * @author fxx
 * @version 2017-09-25
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteRecommendItem")
public class SiteRecommendItemController extends BaseController {

    @Autowired
    private SiteRecommendItemService siteRecommendItemService;



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
     * 进入列表页
     *
     * @param siteRecommendItem 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteRecommendItem:view")
    @RequestMapping(value = "list")
    public String list(SiteRecommendItem siteRecommendItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteRecommendItem> page = siteRecommendItemService.selectByWhere(new Page<SiteRecommendItem>(request, response), new Wrapper(siteRecommendItem));
        model.addAttribute("page", page);
        return "admin/site/siteRecommendItemList";
    }

    /**
     * 进入保存页面
     *
     * @param siteRecommendItem 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteRecommendItem:save")
    @RequestMapping(value = "save1")
    public String save1(SiteRecommendItem siteRecommendItem, Model model) {
        String recommendId = R.get("recommendId");
        if (StringUtils.isNoneBlank(recommendId) && StringUtils.isNumeric(recommendId)) {
            List<SiteRecommendItem> siteRecommendItemList = siteRecommendItemService.selectByWhere(new Wrapper().and("recommend_id=", recommendId).orderBy("sort"));
            model.addAttribute("siteRecommendItemList", JsonMapper.getInstance().toJson(siteRecommendItemList));
        }
        //推荐位类型，1图片，2商品
        model.addAttribute("type", R.get("type"));
        return "admin/site/siteRecommendItemForm";
    }

    /**
     * 执行保存
     *
     * @param siteRecommendItem  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteRecommendItem:save")
    @ResponseBody
    @RequestMapping(value = "save2")
    public Map<String, Object> save2(SiteRecommendItem siteRecommendItem, Model model, RedirectAttributes redirectAttributes) {
        String type = R.get("type");
        String recommendId = R.get("recommendId");
        String dataArray = R.get("data_array");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(type)) {
            map.put("success", false);
            map.put("message", "推荐位的类型不能为空");
            return map;
        }
        if (StringUtils.isBlank(recommendId)) {
            map.put("success", false);
            map.put("message", "推荐位id不能为空");
            return map;
        }
        if (!StringUtils.isNumeric(recommendId)) {
            map.put("success", false);
            map.put("message", "推荐位id只能是数字");
            return map;
        }
        String dataArray2 = dataArray = dataArray.replaceAll("&quot;", "'");
        JSONArray jsonArray = new JSONArray();
        jsonArray = JSONArray.parseArray(dataArray2);
        List<SiteRecommendItem> siteRecommendItemList = new ArrayList<>();
        siteRecommendItemList = jsonArray.toJavaList(SiteRecommendItem.class);
        for (int i = 0; i < siteRecommendItemList.size(); i++) {
            siteRecommendItemList.get(i).setRecommendId(Long.parseLong(recommendId));
        }
        //siteRecommendItemService.insertBatch(siteRecommendItemList);
        siteRecommendItemService.addSiteRecommendItem(siteRecommendItemList, Long.parseLong(recommendId));
        map.put("success", true);
        map.put("message", "推荐成功");
        return map;
    }

    /**
     * 进入编辑页面
     *
     * @param siteRecommendItem 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteRecommendItem:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteRecommendItem siteRecommendItem, Model model) {
        SiteRecommendItem entity = null;
        if (siteRecommendItem != null) {
            if (siteRecommendItem.getId() != null) {
                entity = siteRecommendItemService.selectById(siteRecommendItem.getId());
            }
        }
        model.addAttribute("siteRecommendItem", entity);
        return "admin/site/siteRecommendItemForm";
    }

    /**
     * 执行编辑
     *
     * @param siteRecommendItem  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteRecommendItem:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteRecommendItem siteRecommendItem, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteRecommendItem, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteRecommendItem, model);//回显错误提示
        }

        //业务处理
        siteRecommendItemService.updateByIdSelective(siteRecommendItem);
        addMessage(redirectAttributes, "编辑推荐位详情成功");
        return "redirect:" + adminPath + "/site/siteRecommendItem/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteRecommendItem  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteRecommendItem:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteRecommendItem siteRecommendItem, RedirectAttributes redirectAttributes) {
        siteRecommendItemService.deleteById(siteRecommendItem.getId());
        addMessage(redirectAttributes, "删除推荐位详情成功");
        return "redirect:" + adminPath + "/site/siteRecommendItem/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteRecommendItem 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteRecommendItem siteRecommendItem, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("riId"))) {
            errorList.add("主键不能为空");
        }
        if (StringUtils.isNotBlank(R.get("riId")) && R.get("riId").length() > 19) {
            errorList.add("主键最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("recommendId"))) {
            errorList.add("推荐位表id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("recommendId")) && R.get("recommendId").length() > 19) {
            errorList.add("推荐位表id最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("path")) && R.get("path").length() > 128) {
            errorList.add("图片地址最大长度不能超过128字符");
        }
        if (StringUtils.isBlank(R.get("operationType"))) {
            errorList.add("操作类型不能为空");
        }
        if (StringUtils.isNotBlank(R.get("operationType")) && R.get("operationType").length() > 1) {
            errorList.add("操作类型最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("operationContent")) && R.get("operationContent").length() > 255) {
            errorList.add("操作内容最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add("排序最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("addInfo1")) && R.get("addInfo1").length() > 255) {
            errorList.add("附加值1最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("addInfo2")) && R.get("addInfo2").length() > 255) {
            errorList.add("附加值2最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("addInfo3")) && R.get("addInfo3").length() > 255) {
            errorList.add("附加值3最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("addInfo4")) && R.get("addInfo4").length() > 255) {
            errorList.add("附加值4最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("addInfo5")) && R.get("addInfo5").length() > 255) {
            errorList.add("附加值5最大长度不能超过255字符");
        }
        return errorList;
    }

}