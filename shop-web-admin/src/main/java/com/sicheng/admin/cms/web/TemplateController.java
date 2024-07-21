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
package com.sicheng.admin.cms.web;

import com.google.common.collect.Lists;
import com.sicheng.admin.cms.entity.FileTpl;
import com.sicheng.admin.cms.service.FileTplService;

import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: TemplateController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年4月23日 下午9:14:33
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/template")
public class TemplateController extends BaseController {

    @Autowired
    private FileTplService fileTplService;

    @Autowired
    ServletContext context;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {

        String menu3 = "070215";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入jsp模板页（新页面）
     *
     * @param name  要修改页面的名字
     * @param model
     * @return
     */
    @RequiresPermissions("cms:template:view")
    @RequestMapping(value = "list")
    public String list(Model model) {
        return "admin/sys/sysTemplet";
    }

    /**
     * 获取目录树json数据（ajax请求）
     *
     * @param model
     * @return
     */
    @RequiresPermissions("cms:template:view")
    @RequestMapping(value = "queryTree")
    @ResponseBody
    public Object queryTree(Model model) {
        List<FileTpl> list = fileTplService.getFileTree();//获取目录树json数据
        List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
        for (FileTpl tpl : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", tpl.getName());
            map.put("pId", tpl.getParent() == null ? 0 : tpl.getParent());//目录
            map.put("name", tpl.getFilename());//文件名
            map.put("fullName", tpl.getName());//全名
            map.put("isDirectory", tpl.isDirectory());
            //map.put("isParent",true);
            json.add(map);
        }
        return json;
    }

    /**
     * 获取目录下的目录和文件 （ajax请求）
     *
     * @param path  目录
     * @param model
     * @return
     */
    @RequiresPermissions("cms:template:view")
    @RequestMapping(value = "queryFiles")
    @ResponseBody
    public List<Map<String, Object>> queryFiles(String path, Model model) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (StringUtils.isBlank(path)) {
            return list;
        }
        List<FileTpl> fileTplList = fileTplService.getFiles(path, true);
        if (fileTplList != null && fileTplList.size() > 0) {
            for (int i = 0; i < fileTplList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("fullName", fileTplList.get(i).getName());        //全名
                map.put("fileName", fileTplList.get(i).getFilename());    //文件名
                map.put("dir", fileTplList.get(i).getParent());            //目录
                map.put("size", fileTplList.get(i).getSize());            //大小kb
                map.put("lastModifiedDate", DateUtils.formatDate(fileTplList.get(i).getLastModifiedDate(), "yyyy-MM-dd HH:mm:ss"));//最后修改时间
                map.put("isDirectory", fileTplList.get(i).isDirectory());//最后修改时间
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 保存模板内容
     *
     * @param content  模板模板
     * @param fullName 模板所在目录
     * @return
     */
    @RequiresPermissions("cms:template:edit")
    @RequestMapping(value = "save2")
    @ResponseBody
    public boolean save2(String content, String fullName) {
        return fileTplService.saveTpl(content, fullName);
    }

    /**
     * 获取模板内容
     *
     * @param fullName 模板文件名称
     * @param model
     * @return
     */
    @RequiresPermissions("cms:template:view")
    @RequestMapping(value = "getTemplateContent")
    @ResponseBody
    public Map<String, String> getTemplateContent(String fullName, Model model) {
        if (StringUtils.isBlank(fullName)) {
            return null;
        }
        FileTpl tpl = fileTplService.createFileTpl(fullName);
        if (tpl == null) {
            return null;
        }
        String templateContent = tpl.getSource();
        Map<String, String> map = new HashMap<>();
        map.put("content", templateContent);
        return map;
    }

    /**
     * 删除模板文件
     *
     * @param name 文件名
     * @return
     */
    @RequiresPermissions("cms:template:drop")
    @RequestMapping(value = "deleteTemplateFile")
    @ResponseBody
    public boolean deleteTemplateFile(String name) {
        return fileTplService.delTpl(name);
    }
}