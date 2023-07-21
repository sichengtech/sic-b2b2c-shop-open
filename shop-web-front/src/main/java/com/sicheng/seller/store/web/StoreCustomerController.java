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
package com.sicheng.seller.store.web;

import com.google.common.collect.Lists;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreCustomerService;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import com.sicheng.seller.store.service.StoreCustomerServiceService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: StoreCustomerController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhangjiali
 * @version 2017年2月23日 下午1:11:34
 */
@Controller
@RequestMapping(value = "${sellerPath}/store/customer")
public class StoreCustomerController extends BaseController {

    @Autowired
    private StoreCustomerServiceService customerService;


    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040145";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    @RequiresPermissions("store:storeCustomer:view")
    @RequestMapping(value = "list")
    public String list(StoreCustomerService storeCustomer, Model model, HttpServletRequest request) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeCustomer.setStoreId(userSeller.getStoreId());
        Wrapper w = new Wrapper();
        w.orderBy("sort");
        w.setEntity(storeCustomer);
        List<StoreCustomerService> list = customerService.selectByWhere(w);
        if (!list.isEmpty()) {
            model.addAttribute("customerList", list);
            model.addAttribute("remarks", list.get(0).getRemarks());
        }
        return "seller/user/userCustomerServiceSet";
    }

    @RequiresPermissions("store:storeCustomer:edit")
    @RequestMapping(value = "save")
    public String save(RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
        String[] name = R.getArray("name");//客服名称
        String[] tool = R.getArray("tool");//客服工具（1.QQ，2.站内IM，3.旺旺）
        String[] account = R.getArray("account");//客服账号
        String[] type = R.getArray("type");//类型（1.售前服务，2.售后服务）
        String[] sort = R.getArray("sort");//排序
        String remarks = R.get("remarks");//备注
        String[] scsId = R.getArray("scsId");//客服id
        List<Object> ids = Lists.newArrayList();
        if (scsId.length > 0 && scsId != null) {
            //修改
            for (int i = 0; i < scsId.length; i++) {
                ids.add(Long.valueOf(scsId[i]));
            }
            customerService.deleteByIdIn(ids);//将库中客服都删除
            if (name.length == 0 && tool.length == 0 && account.length == 0 && type.length == 0 && sort.length == 0) {
                addMessage(redirectAttributes, FYUtils.fyParams("保存客服信息成功"));
                return "redirect:" + sellerPath + "/store/customer/list.htm";
            } else {
                //表单验证
                boolean confirmation = validate(name, tool, account, type, sort, model);
                if (confirmation == false) {
                    //验证未通过
                    return "seller/user/userCustomerServiceSet";
                }
            }
        } else {
            //保存
            if (name.length == 0 && tool.length == 0 && account.length == 0 && type.length == 0 && sort.length == 0) {
                model.addAttribute("message", FYUtils.fyParams("请添加客服"));
                return "seller/user/userCustomerServiceSet";
            } else {
                //表单验证
                boolean confirmation = validate(name, tool, account, type, sort, model);
                if (confirmation == false) {
                    //验证未通过
                    return "seller/user/userCustomerServiceSet";
                }
            }
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        //存储客服信息
        List<StoreCustomerService> newList = Lists.newArrayList();
        for (int i = 0; i < name.length; i++) {
            StoreCustomerService storeCustomer = new StoreCustomerService();
            storeCustomer.setName(name[i]);
            storeCustomer.setTool(tool[i]);
            storeCustomer.setAccount(account[i]);
            storeCustomer.setType(type[i]);
            storeCustomer.setSort(Integer.valueOf(sort[i]));
            storeCustomer.setRemarks(remarks);
            storeCustomer.setStoreId(userSeller.getStoreId());
            storeCustomer.setCreateDate(new Date());
            storeCustomer.setUpdateDate(new Date());
            newList.add(storeCustomer);
        }
        customerService.insertSelectiveBatch(newList);
        addMessage(redirectAttributes, FYUtils.fyParams("保存客服信息成功"));
        return "redirect:" + sellerPath + "/store/customer/list.htm";
    }

    /**
     * 客服表单验证
     *
     * @param name    客服名称
     * @param tool    客服工具
     * @param account 客服账号
     * @param type    客服类型
     * @param sort    客服排序
     * @param model
     * @return
     */
    private boolean validate(String[] name, String[] tool, String[] account, String[] type, String[] sort, Model model) {
        boolean confirmation = true;
        if (name.length != tool.length || name.length != account.length || name.length != type.length ||
                name.length != sort.length) {
            //判断接受的数组参数都是同样的长度
            model.addAttribute("message", FYUtils.fyParams("客服参数缺失"));
            confirmation = false;
        } else {
            //字段规则验证
            List<String> errorList = new ArrayList<String>();
            //判断客服名称
            for (int i = 0; i < name.length; i++) {
                boolean a = false;
                boolean b = false;
                if (StringUtils.isBlank(name[i])) {
                    errorList.add(FYUtils.fyParams("客服名称不能为空"));
                    a = true;
                }
                if (StringUtils.isNotBlank(name[i]) && name[i].length() > 64) {
                    errorList.add(FYUtils.fyParams("客服名称不能大于64字符"));
                    b = true;
                }
                if (a == true || b == true) {
                    break;
                }
            }
            //判断客服工具
            for (int i = 0; i < tool.length; i++) {
                boolean a = false;
                if (StringUtils.isBlank(tool[i])) {
                    errorList.add(FYUtils.fyParams("请选择客服工具"));
                    a = true;
                }
                if (a == true) {
                    break;
                }
            }
            //判断客服账号
            for (int i = 0; i < account.length; i++) {
                boolean a = false;
                boolean b = false;
                if (StringUtils.isBlank(account[i])) {
                    errorList.add(FYUtils.fyParams("客服账号不能为空"));
                    a = true;
                }
                if (StringUtils.isNotBlank(account[i]) && account[i].length() > 64) {
                    errorList.add(FYUtils.fyParams("客服账号不能大于64字符"));
                    b = true;
                }
                if (a == true || b == true) {
                    break;
                }
            }
            //判断客服类型
            for (int i = 0; i < type.length; i++) {
                boolean a = false;
                if (StringUtils.isBlank(type[i])) {
                    errorList.add(FYUtils.fyParams("请选择客服类型"));
                    a = true;
                }
                if (a == true) {
                    break;
                }
            }
            //判断客服排序
            for (int i = 0; i < sort.length; i++) {
                boolean a = false;
                boolean b = false;
                if (StringUtils.isBlank(sort[i])) {
                    errorList.add(FYUtils.fyParams("客服排序不能为空"));
                    a = true;
                }
                if (StringUtils.isNotBlank(sort[i]) && sort[i].length() > 64) {
                    errorList.add(FYUtils.fyParams("客服排序不能大于10字符"));
                    b = true;
                }
                if (a == true || b == true) {
                    break;
                }
            }
            if (errorList.size() > 0) {
                errorList.add(0, FYUtils.fyParams("数据验证失败："));
                model.addAttribute("message", errorList.toArray(new String[]{}));
                confirmation = false;
            }
        }
        return confirmation;
    }
}
