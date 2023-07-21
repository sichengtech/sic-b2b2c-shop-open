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

import com.sicheng.admin.site.entity.SiteUpload;
import com.sicheng.admin.site.service.SiteUploadService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.FileSizeHelper;
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
 * 管理文件上传的格式 Controller
 * 所属模块：site
 *
 * @author 张加利
 * @version 2017-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/site/siteUpload")
public class SiteUploadController extends BaseController {

    @Autowired
    private SiteUploadService siteUploadService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070104";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param siteUpload 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteUpload:view")
    @RequestMapping(value = "list")
    public String list(SiteUpload siteUpload, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SiteUpload> page = siteUploadService.selectByWhere(new Page<SiteUpload>(request, response), new Wrapper(siteUpload));
        //把列表中的上传文件大小转换单位为M
        if (!page.getList().isEmpty()) {
            for (int i = 0; i < page.getList().size(); i++) {
                String uploadSizeM = FileSizeHelper.getHumanReadableFileSize(page.getList().get(i).getUploadSize());
                page.getList().get(i).setUploadSizeM(uploadSizeM);
            }
        }
        model.addAttribute("page", page);
        return "admin/site/siteUploadList";
    }

    /**
     * 进入保存页面
     *
     * @param siteUpload 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteUpload:save")
    @RequestMapping(value = "save1")
    public String save1(SiteUpload siteUpload, Model model) {
        if (siteUpload == null) {
            siteUpload = new SiteUpload();
        }
        model.addAttribute("siteUpload", siteUpload);
        return "admin/site/siteUploadForm";
    }

    /**
     * 执行保存
     *
     * @param siteUpload         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteUpload:save")
    @RequestMapping(value = "save2")
    public String save2(SiteUpload siteUpload, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteUpload, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(siteUpload, model);//回显错误提示
        }
        //将首字母转换成小写
        if (siteUpload != null) {
            siteUpload.setType(siteUpload.getType().toLowerCase());
        }
        //业务处理
        siteUploadService.insertSelective(siteUpload);
        addMessage(redirectAttributes, FYUtils.fyParams("保存上传设置成功"));
        return "redirect:" + adminPath + "/site/siteUpload/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param siteUpload 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("site:siteUpload:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SiteUpload siteUpload, Model model) {
        SiteUpload entity = null;
        if (siteUpload != null) {
            if (siteUpload.getId() != null) {
                entity = siteUploadService.selectById(siteUpload.getId());
            }
        }
        model.addAttribute("siteUpload", entity);
        return "admin/site/siteUploadForm";
    }

    /**
     * 执行编辑
     *
     * @param siteUpload         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteUpload:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SiteUpload siteUpload, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(siteUpload, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(siteUpload, model);//回显错误提示
        }
        //将首字母转换成小写
        if (siteUpload != null) {
            siteUpload.setType(siteUpload.getType().toLowerCase());
        }
        //业务处理
        siteUploadService.updateByIdSelective(siteUpload);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑上传设置成功"));
        return "redirect:" + adminPath + "/site/siteUpload/list.do?repage";
    }

    /**
     * 删除
     *
     * @param siteUpload         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("site:siteUpload:drop")
    @RequestMapping(value = "delete")
    public String delete(SiteUpload siteUpload, RedirectAttributes redirectAttributes) {
        siteUploadService.deleteById(siteUpload.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除上传设置成功"));
        return "redirect:" + adminPath + "/site/siteUpload/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param siteUpload 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SiteUpload siteUpload, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("uploadSize"))) {
            errorList.add(FYUtils.fyParams("上传文件大小不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("uploadSize")) && R.get("uploadSize").length() > 10) {
            errorList.add(FYUtils.fyParams("上传文件大小最大长度不能超过10字符"));
        }
        if (StringUtils.isNotBlank(R.get("uploadSize")) && R.getLong("uploadSize") > 52428800L) {
            errorList.add(FYUtils.fyParams("上传文件大小最大值是52428800"));
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add(FYUtils.fyParams("上传文件类型不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 64) {
            errorList.add(FYUtils.fyParams("上传文件类型最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak1")) && R.get("bak1").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段1最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak2")) && R.get("bak2").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段2最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak3")) && R.get("bak3").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段3最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak4")) && R.get("bak4").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段4最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak5")) && R.get("bak5").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段5最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak6")) && R.get("bak6").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段6最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak7")) && R.get("bak7").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段7最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak8")) && R.get("bak8").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段8最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak9")) && R.get("bak9").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段9最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("bak10")) && R.get("bak10").length() > 64) {
            errorList.add(FYUtils.fyParams("备用字段10最大长度不能超过64字符"));
        }
        return errorList;
    }

}