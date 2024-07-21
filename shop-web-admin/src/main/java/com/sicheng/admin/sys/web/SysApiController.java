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

import com.sicheng.admin.sys.entity.SysApi;
import com.sicheng.admin.sys.entity.SysApiParam;

import com.sicheng.admin.sys.service.SysApiService;
import com.sicheng.common.config.Global;
import com.sicheng.common.mapper.JsonMapper;
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
 * 接口 Controller
 * 所属模块：sys
 *
 * @author fxx
 * @version 2018-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysApi")
public class SysApiController extends BaseController {

    @Autowired
    private SysApiService sysApiService;



    //base url盐(e)
//    private String salt_baseUrl = Global.getConfig("wap.api.baseUrl");
    //固化在代码中的盐(d)
    private String salt_d = Global.getConfig("wap.api.sign");

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "080150";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param sysApi   实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysApi:view")
    @RequestMapping(value = "list")
    public String list(SysApi sysApi, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysApi> page = sysApiService.selectByWhere(new Page<SysApi>(request, response), new Wrapper(sysApi).orderBy("api_category asc,api_name asc"));
        model.addAttribute("page", page);
        return "admin/sys/sysApiList";
    }

    /**
     * 进入保存页面
     *
     * @param sysApi 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysApi:save")
    @RequestMapping(value = "save1")
    public String save1(SysApi sysApi, Model model) {
        if (sysApi == null) {
            sysApi = new SysApi();
        }
        model.addAttribute("sysApi", sysApi);
        return "admin/sys/sysApiForm";
    }

    /**
     * 执行保存
     *
     * @param sysApi             实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysApi:save")
    @RequestMapping(value = "save2")
    public String save2(SysApi sysApi, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysApi, model);
        //验证参数
        errorList = validate2(errorList);
        //参数名
        String[] paramNames = R.getArray("paramName");
        //参数类型
        String[] types = R.getArray("type");
        //参数是否必填
        String[] isRequireds = R.getArray("isRequired");
        //参数描述
        String[] paramDescribes = R.getArray("paramDescribe");
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysApi, model);//回显错误提示
        }
        //业务处理
        List<SysApiParam> sysApiParamList = new ArrayList<>();
        for (int i = 0; i < paramNames.length; i++) {
            SysApiParam sysApiParam = new SysApiParam();
            sysApiParam.setParamName(paramNames[i]);
            sysApiParam.setParamType(types[i]);
            sysApiParam.setIsRequired(isRequireds[i]);
            sysApiParam.setParamDescribe(paramDescribes[i]);
            sysApiParamList.add(sysApiParam);
        }
        //执行保存
        sysApiService.saveApi(sysApi, sysApiParamList);
        addMessage(redirectAttributes, FYUtils.fyParams("保存接口成功"));
        return "redirect:" + adminPath + "/sys/sysApi/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysApi 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysApi:edit")
    @RequestMapping(value = "edit1")
    public String edit1(SysApi sysApi, Model model) {
        SysApi entity = null;
        if (sysApi != null) {
            if (sysApi.getId() != null) {
                entity = sysApiService.selectById(sysApi.getId());
            }
        }
        model.addAttribute("sysApi", entity);
        model.addAttribute("apiParamList", JsonMapper.getInstance().toJson(entity.getSysApiParamList()));
        return "admin/sys/sysApiForm";
    }

    /**
     * 执行编辑
     *
     * @param sysApi             实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysApi:edit")
    @RequestMapping(value = "edit2")
    public String edit2(SysApi sysApi, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(sysApi, model);
        if (StringUtils.isBlank(R.get("apiId"))) {
            errorList.add(FYUtils.fyParams("接口id不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("apiId")) && R.get("apiId").length() > 19) {
            errorList.add(FYUtils.fyParams("接口id最大长度不能超过19字符"));
        }
        //验证参数
        errorList = validate2(errorList);
        //参数名
        String[] paramNames = R.getArray("paramName");
        //参数类型
        String[] types = R.getArray("type");
        //参数是否必填
        String[] isRequireds = R.getArray("isRequired");
        //参数描述
        String[] paramDescribes = R.getArray("paramDescribe");
        if (!errorList.isEmpty()) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(sysApi, model);//回显错误提示
        }
        //业务处理
        List<SysApiParam> sysApiParamList = new ArrayList<>();
        for (int i = 0; i < paramNames.length; i++) {
            SysApiParam sysApiParam = new SysApiParam();
            sysApiParam.setParamName(paramNames[i]);
            sysApiParam.setApiId(sysApi.getApiId());
            sysApiParam.setParamType(types[i]);
            sysApiParam.setIsRequired(isRequireds[i]);
            sysApiParam.setParamDescribe(paramDescribes[i]);
            sysApiParamList.add(sysApiParam);
        }
        //执行保存
        sysApiService.saveApi(sysApi, sysApiParamList);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑接口成功"));
        return "redirect:" + adminPath + "/sys/sysApi/list.do?repage";
    }

    /**
     * 删除
     *
     * @param sysApi             实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:sysApi:drop")
    @RequestMapping(value = "delete")
    public String delete(SysApi sysApi, RedirectAttributes redirectAttributes) {
        if (sysApi == null || sysApi.getApiId() == null) {
            addMessage(redirectAttributes, FYUtils.fyParams("接口id不能为空"));
            return "redirect:" + adminPath + "/sys/sysApi/list.do?repage";
        }
        sysApiService.deleteApi(sysApi.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除接口成功"));
        return "redirect:" + adminPath + "/sys/sysApi/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param sysApi 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("sys:sysApi:test")
    @RequestMapping(value = "test")
    public String test(SysApi sysApi, Model model) {
        SysApi entity = null;
        if (sysApi != null) {
            if (sysApi.getId() != null) {
                entity = sysApiService.selectById(sysApi.getId());
            }
        }
        model.addAttribute("sysApi", entity);
        model.addAttribute("apiParamList", JsonMapper.getInstance().toJson(entity.getSysApiParamList()));
//        model.addAttribute("salt_e", salt_baseUrl);
        model.addAttribute("salt_d", salt_d);
        return "admin/sys/sysApiTest";
    }

    /**
     * 验证接口参数
     *
     * @param errorList
     * @return
     */
    private List<String> validate2(List<String> errorList) {
        //参数名
        String[] paramNames = R.getArray("paramName");
        //参数类型
        String[] types = R.getArray("type");
        //参数是否必填
        String[] isRequireds = R.getArray("isRequired");
        //参数描述
        String[] paramDescribes = R.getArray("paramDescribe");
        //验证参数名
        if (paramNames != null && paramNames.length != 0) {
            for (int i = 0; i < paramNames.length; i++) {
                if (StringUtils.isBlank(paramNames[i])) {
                    errorList.add(FYUtils.fyParams("参数名不能为空"));
                    break;
                }
                if (paramNames[i].length() > 64) {
                    errorList.add(FYUtils.fyParams("参数名最大长度不能超过64字符"));
                    break;
                }
            }
        }
        //验证参数类型
        if (types != null && types.length != 0) {
            for (int i = 0; i < types.length; i++) {
                if (StringUtils.isBlank(types[i])) {
                    errorList.add(FYUtils.fyParams("参数类型不能为空"));
                    break;
                }
                if (types[i].length() > 1) {
                    errorList.add(FYUtils.fyParams("参数类型长度不能超过1字符"));
                    break;
                }
            }
        }
        //验证参数是否必填
        if (isRequireds != null && isRequireds.length != 0) {
            for (int i = 0; i < isRequireds.length; i++) {
                if (StringUtils.isBlank(isRequireds[i])) {
                    errorList.add(FYUtils.fyParams("是否必填不能为空"));
                    break;
                }
                if (isRequireds[i].length() > 1) {
                    errorList.add(FYUtils.fyParams("是否必填不能超过1字符"));
                    break;
                }
            }
        }
        //验证参数描述
        if (paramDescribes != null && paramDescribes.length > 0) {
            for (int i = 0; i < paramDescribes.length; i++) {
                if (StringUtils.isNotBlank(paramDescribes[i]) && paramDescribes[i].length() > 256) {
                    errorList.add(FYUtils.fyParams("参数描述不能超过256字符"));
                    break;
                }
            }
        }
        int paramNamesLen = paramNames.length;
        int typesLen = types.length;
        int isRequiredsLen = isRequireds.length;
        int paramDescribesLen = paramDescribes.length;
        if (paramNamesLen != typesLen || paramNamesLen != isRequiredsLen || paramNamesLen != paramDescribesLen) {
            errorList.add(FYUtils.fyParams("参数错误"));
        }
        return errorList;
    }

    /**
     * 表单验证
     *
     * @param sysApi 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(SysApi sysApi, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isNotBlank(R.get("apiCategory")) && R.get("apiCategory").length() > 1) {
            errorList.add(FYUtils.fyParams("所属分类最大长度不能超过1字符"));
        }
        if (StringUtils.isBlank(R.get("apiName"))) {
            errorList.add(FYUtils.fyParams("接口名不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("apiName")) && R.get("apiName").length() > 64) {
            errorList.add(FYUtils.fyParams("接口名最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("apiVersion"))) {
            errorList.add(FYUtils.fyParams("接口版本号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("apiVersion")) && R.get("apiVersion").length() > 64) {
            errorList.add(FYUtils.fyParams("接口版本号最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("apiDescribe")) && R.get("apiDescribe").length() > 256) {
            errorList.add(FYUtils.fyParams("接口描述最大长度不能超过256字符"));
        }
        if (StringUtils.isBlank(R.get("apiUrl"))) {
            errorList.add(FYUtils.fyParams("接口地址不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("apiUrl")) && R.get("apiUrl").length() > 64) {
            errorList.add(FYUtils.fyParams("接口地址最大长度不能超过64字符"));
        }
        return errorList;
    }

}
