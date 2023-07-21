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
package com.sicheng.admin.site.web;

import com.sicheng.admin.site.entity.SiteHotSearchWord;
import com.sicheng.admin.site.service.SiteHotSearchWordService;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 热搜词 Controller
 * 所属模块：site
 *
 * @author 贺东泽
 * @version 2019-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteHotSearchWord")
public class SiteHotSearchWordController extends BaseController {

    @Autowired
    private SiteHotSearchWordService siteHotSearchWordService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070112";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteHotSearchWord 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteHotSearchWord:view")
    @RequestMapping(value = "list")
    public String list(SiteHotSearchWord siteHotSearchWord, HttpServletRequest request, HttpServletResponse response, Model model) {
        String keyWord = null;
        //为了能使用like模糊搜索
        if (siteHotSearchWord.getWord() != null && StringUtils.isNotBlank(siteHotSearchWord.getWord())) {
            keyWord = siteHotSearchWord.getWord();
            siteHotSearchWord.setWord(null);
        }
        Wrapper wrapper = new Wrapper(siteHotSearchWord);
        if (keyWord != null) {
            wrapper.and("word like ", "%" + keyWord + "%");
        }
        wrapper.orderBy("`sort` asc");
        Page<SiteHotSearchWord> page = siteHotSearchWordService.selectByWhere(new Page<SiteHotSearchWord>(request, response), wrapper);
        //这一步是为了页面回显
        if (keyWord != null) {
            siteHotSearchWord.setWord(keyWord);
        }
        model.addAttribute("page", page);
        return "admin/site/siteHotSearchWordList";
    }

    /**
     * 进入保存页面
     *
     * @param siteHotSearchWord 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteHotSearchWord:save")
    @RequestMapping(value = "save1")
    public String save1(SiteHotSearchWord siteHotSearchWord, Model model) {
        if (siteHotSearchWord == null) {
            siteHotSearchWord = new SiteHotSearchWord();
        }
        model.addAttribute("siteHotSearchWord", siteHotSearchWord);
        return "admin/site/siteHotSearchWordForm";
    }

    /**
     * 执行保存
     *
     * @param siteHotSearchWord  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteHotSearchWord:save")
    @RequestMapping(value = "save2")
    public String save2(SiteHotSearchWord siteHotSearchWord, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteHotSearchWord, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteHotSearchWord, model);//回显错误提示
        }

        //业务处理
        siteHotSearchWordService.insertSelective(siteHotSearchWord);
        addMessage(redirectAttributes, FYUtils.fyParams("保存热搜词成功"));
        return "redirect:" + adminPath + "/site/siteHotSearchWord/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteHotSearchWord 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteHotSearchWord:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteHotSearchWord siteHotSearchWord, Model model) {
        SiteHotSearchWord entity = null;
        if (siteHotSearchWord != null) {
            if (siteHotSearchWord.getId() != null) {
                entity = siteHotSearchWordService.selectById(siteHotSearchWord.getId());
            }
        }
        model.addAttribute("siteHotSearchWord", entity);
        return "admin/site/siteHotSearchWordForm";
    }

    /**
     * 执行编辑
     *
     * @param siteHotSearchWord  实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteHotSearchWord:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteHotSearchWord siteHotSearchWord, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteHotSearchWord, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteHotSearchWord, model);//回显错误提示
        }

        //业务处理
        siteHotSearchWordService.updateByIdSelective(siteHotSearchWord);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑热搜词成功"));
        return "redirect:" + adminPath + "/site/siteHotSearchWord/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteHotSearchWord  实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteHotSearchWord:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteHotSearchWord siteHotSearchWord, RedirectAttributes redirectAttributes) {
        siteHotSearchWordService.deleteById(siteHotSearchWord.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除热搜词成功"));
        return "redirect:" + adminPath + "/site/siteHotSearchWord/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteHotSearchWord 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteHotSearchWord siteHotSearchWord, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("word"))) {
            errorList.add(FYUtils.fyParams("热搜词或热搜店铺不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("word")) && R.get("word").length() > 32) {
            errorList.add(FYUtils.fyParams("热搜词或热搜店铺最大长度不能超过32字符"));
        }
        if (StringUtils.isNotBlank(R.get("isShow")) && R.get("isShow").length() > 1) {
            errorList.add(FYUtils.fyParams("是否展示：0为否，1为是最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add(FYUtils.fyParams("搜索类型：0为商品，1为店铺最大长度不能超过1字符"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序权重，权重越高越靠前最大长度不能超过10字符"));
        }
        return errorList;
    }

}