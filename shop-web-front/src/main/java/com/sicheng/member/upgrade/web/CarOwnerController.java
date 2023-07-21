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
package com.sicheng.member.upgrade.web;

import java.util.ArrayList;
import java.util.List;

import com.sicheng.member.interceptor.MemberMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicheng.admin.product.entity.ProductCar;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.front.service.ProductCarService;
import com.sicheng.sso.service.UserMemberService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: 完善车主信息</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年7月28日 下午3:39:43
 */
@Controller
@RequestMapping(value = "${memberPath}/upgrade/carOwner")
public class CarOwnerController extends BaseController {

    @Autowired
    private ProductCarService productCarService;
    @Autowired
    private UserMemberService userMemberService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting( "carOwner");//三级菜单高亮
    }

    /**
     * 完善车主信息页面,拼接名字
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(Model model) {
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        String applyCarIds = userMember.getApplyCarIds();
        StringBuffer applyCarName = new StringBuffer();
        if (StringUtils.isNotBlank(applyCarIds)) {
            String[] applyCarIdss = applyCarIds.split(",");
            for (int i = 0; i < applyCarIdss.length; i++) {
                if (StringUtils.isNotBlank(applyCarIdss[i])) {
                    String carName = productCarService.selectById(Long.parseLong(applyCarIdss[i])).getName();
                    applyCarName.append("-");
                    applyCarName.append(carName);
                }
            }
        }
        List<Long> carIds = new ArrayList<>();
        Wrapper w = new Wrapper();
        w.and("a.parent_id=", 0);
        List<ProductCar> productCars = productCarService.selectByWhere(w);
        if (!productCars.isEmpty()) {
            for (int i = 0; i < productCars.size(); i++) {
                carIds.add(productCars.get(i).getCarId());
            }
        }
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.parent_id in", carIds);
        List<ProductCar> productCarTwoList = productCarService.selectByWhere(wrapper);
        if (StringUtils.isNotBlank(applyCarName)) {
            model.addAttribute("applyCarName", applyCarName.substring(1));
        }
        model.addAttribute("productCarTwoList", productCarTwoList);
        return "member/upgrade/carOwnerForm";
    }

    /**
     * 保存完善车主信息页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(Model model, RedirectAttributes redirectAttributes) {
        Long carTwoId = R.getLong("carTwoId");
        Long carThreeId = R.getLong("carThreeId");
        Long carFourId = R.getLong("carFourId");
        if (carTwoId == null && carThreeId == null && carFourId == null) {
            addMessage(redirectAttributes, FYUtils.fyParams("保存适用车型成功"));
            return "redirect:" + memberPath + "/upgrade/carOwner/save1.htm";
        }
        if (carThreeId == null) {
            List<String> errorList = new ArrayList<>();
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            errorList.add(1, FYUtils.fyParams("请选择适用车型"));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(model);//回显错误提示
        }
        StringBuffer carIds = new StringBuffer();
        carIds.append(carTwoId);
        carIds.append(",");
        carIds.append(carThreeId);
        if (carFourId != null) {
            carIds.append(",");
            carIds.append(carFourId);
        }
        UserMain userMain = SsoUtils.getUserMain();
        UserMember userMember = new UserMember();
        userMember.setUId(userMain.getUId());
        userMember.setApplyCarIds(carIds.toString());
        userMemberService.updateByIdSelective(userMember);
        addMessage(redirectAttributes, FYUtils.fyParams("保存适用车型成功"));
        return "redirect:" + memberPath + "/upgrade/carOwner/save1.htm";
    }

}
