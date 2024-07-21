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
package com.sicheng.member.user.web;

import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.member.interceptor.MemberMenuInterceptor;
import com.sicheng.seller.member.service.MemberAddressService;
import com.sicheng.seller.sys.service.AreaService;
import com.sicheng.sso.utils.SsoUtils;
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
import java.util.regex.Pattern;

/**
 * 买家收货地址 Controller
 * 所属模块：member
 *
 * @author fxx
 * @version 2017-01-12
 */
@Controller
@RequestMapping(value = "${memberPath}/user/memberAddress")
public class MemberAddressController extends BaseController {

    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private AreaService areaService;

    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        MemberMenuInterceptor.menuHighLighting("userAddress");//三级菜单高亮
    }

    /**
     * 进入列表页
     *
     * @param memberAddress 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "list")
    public String list(MemberAddress memberAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        memberAddress.setUId(userMember.getUId());
        List<MemberAddress> addressList = memberAddressService.selectByWhere(new Wrapper(memberAddress).orderBy("is_default desc"));
        model.addAttribute("addressList", addressList);
        return "member/user/memberAddressList";
    }

    /**
     * 进入保存页面
     *
     * @param memberAddress 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save1")
    public String save1(MemberAddress memberAddress, Model model) {
        if (memberAddress == null) {
            memberAddress = new MemberAddress();
        }
        model.addAttribute("memberAddress", memberAddress);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        return "member/user/memberAddressForm";
    }

    /**
     * 执行保存
     *
     * @param memberAddress      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "save2")
    public String save2(MemberAddress memberAddress, Model model, RedirectAttributes redirectAttributes) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        memberAddress.setUId(userMember.getUId());
        List<String> errorList=new ArrayList<>();
        //查询当前用户的收货地址数量，超过20个不让添加
        MemberAddress memberAddress2=new MemberAddress();
        memberAddress2.setUId(userMember.getUId());
        List<MemberAddress> addressList = memberAddressService.selectByWhere(new Wrapper(memberAddress2));
        if(addressList!=null && addressList.size()>=20){
        	errorList.add(FYUtils.fyParams("收货地址最多只能添加20个"));
        	errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(memberAddress, model);//回显错误提示
        }
        
        //表单验证
        errorList = validate(memberAddress, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(memberAddress, model);//回显错误提示
        }
        //业务处理
        memberAddressService.addAddress(memberAddress);
        addMessage(redirectAttributes, FYUtils.fyParams("保存收货地址成功！"));
        return "redirect:" + memberPath + "/user/memberAddress/list.htm?repage";
    }

    /**
     * ajax添加地址
     *
     * @param memberAddress      实体对象
     * @param model
     * @param redirectAttributes
     * @return 返回json
     * 确认订单页中添加收货地址
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "addAddress")
    public Map<String, Object> addAddress(MemberAddress memberAddress, Model model, RedirectAttributes redirectAttributes) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        memberAddress.setUId(userMember.getUId());
        Map<String, Object> map = new HashMap<>();
        //表单验证
        List<String> errorList = new ArrayList<>();
        //查询当前用户的收货地址数量，超过20个不让添加
        MemberAddress memberAddress2=new MemberAddress();
        memberAddress2.setUId(userMember.getUId());
        List<MemberAddress> addressList = memberAddressService.selectByWhere(new Wrapper(memberAddress2));
        if(addressList!=null && addressList.size()>=20){
        	errorList.add(FYUtils.fyParams("收货地址最多只能添加20个"));
        	errorList.add(0, FYUtils.fyParams("数据验证失败："));
            map.put("message", errorList.toString());
            return map;
        }
        
        errorList = validate(memberAddress, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            map.put("message", errorList.toString());
            return map;
        }
        //业务处理
        if (memberAddress.getAddressId() != null) {
            memberAddressService.editAddress(memberAddress);
        } else {
            memberAddressService.addAddress(memberAddress);
        }
        map.put("message", "success");
        map.put("memberAddress", memberAddress);
        return map;
    }

    /**
     * 进入编辑页面
     *
     * @param memberAddress 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "edit1")
    public String edit1(MemberAddress memberAddress, Model model) {
        //入参检查
        if (memberAddress == null || memberAddress.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("收货地址不存在！"));
            return "error/400";
        }
        memberAddress.setUId(SsoUtils.getUserMain().getUserMember().getUId());//属主检查
        MemberAddress entity = memberAddressService.selectOne(new Wrapper(memberAddress));
        //检查合格后，业务处理
        model.addAttribute("memberAddress", entity);
        model.addAttribute("provinceList", areaService.selectByWhere(new Wrapper().and("parent_id=", "1").and("del_flag=", BaseEntity.DEL_FLAG_NORMAL).orderBy("sort")));
        return "member/user/memberAddressForm";
    }

    /**
     * 执行编辑
     *
     * @param memberAddress      实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "edit2")
    public String edit2(MemberAddress memberAddress, Model model, RedirectAttributes redirectAttributes) {
        //入参检查
        if (memberAddress == null || memberAddress.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("收货地址不存在！"));
            return "error/400";
        }
        //表单验证
        List<String> errorList = validate(memberAddress, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(memberAddress, model);//回显错误提示
        }
        //业务处理
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        memberAddress.setUId(userMember.getUId());//属主检查
        memberAddressService.editAddress(memberAddress);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑收货地址成功！"));
        return "redirect:" + memberPath + "/user/memberAddress/list.htm?repage";
    }

    /**
     * 删除
     *
     * @param memberAddress      实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "delete")
    public String delete(MemberAddress memberAddress, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (memberAddress == null || memberAddress.getId() == null) {
            model.addAttribute("message", FYUtils.fyParams("收货地址不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        memberAddress.setUId(userMember.getUId());//属主检查
        memberAddressService.deleteByWhere(new Wrapper(memberAddress));
        addMessage(redirectAttributes, FYUtils.fyParams("删除收货地址成功！"));
        return "redirect:" + memberPath + "/user/memberAddress/list.htm?repage";
    }

    /**
     * 删除
     *
     * @param memberAddress      实体对象
     * @param redirectAttributes
     * @return 返回json，在确认订单页中用
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "deleteAddress")
    public String deleteAddress(MemberAddress memberAddress, RedirectAttributes redirectAttributes) {
        String message = "1";
        if (memberAddress == null) {
            message = "0";
            return message;
        }
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        memberAddress.setUId(userMember.getUId());//属主检查
        memberAddressService.deleteByWhere(new Wrapper(memberAddress));
        return message;
    }

    /**
     * 获取收货地址
     *
     * @param memberAddress 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "getAddress")
    public List<MemberAddress> getAddress(MemberAddress memberAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        memberAddress.setUId(userMember.getUId());
        List<MemberAddress> addressList = memberAddressService.selectByWhere(new Wrapper(memberAddress));
        return addressList;
    }

    /**
     * 表单验证
     *
     * @param memberAddress 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(MemberAddress memberAddress, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isNotBlank(memberAddress.getAddressName()) && memberAddress.getAddressName().length() > 64) {
            errorList.add(FYUtils.fyParams("收货地址名称最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isBlank(memberAddress.getName())) {
            errorList.add(FYUtils.fyParams("收货人不能为空"));
        }
        if (StringUtils.isNotBlank(memberAddress.getName()) && memberAddress.getName().length() > 64) {
            errorList.add(FYUtils.fyParams("收货人最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        if (memberAddress.getCountryId() == null) {
            errorList.add(FYUtils.fyParams("国家不能为空"));
        }
        if (memberAddress.getCountryId() != null && memberAddress.getCountryId().toString().length() > 19) {
            errorList.add(FYUtils.fyParams("国家ID最大长度不能超过"+"19" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isBlank(memberAddress.getCountryName())) {
            errorList.add(FYUtils.fyParams("国家名字不能为空"));
        }
        if (StringUtils.isNotBlank(memberAddress.getCountryName()) && memberAddress.getCountryName().length() > 64) {
            errorList.add(FYUtils.fyParams("国家名字最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        if (memberAddress.getProvinceId() == null) {
            errorList.add(FYUtils.fyParams("省ID不能为空"));
        }
        if (memberAddress.getProvinceId() != null && memberAddress.getProvinceId().toString().length() > 19) {
            errorList.add(FYUtils.fyParams("省ID最大长度不能超过"+"19" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isBlank(memberAddress.getProvinceName())) {
            errorList.add(FYUtils.fyParams("省名字不能为空"));
        }
        if (StringUtils.isNotBlank(memberAddress.getProvinceName()) && memberAddress.getProvinceName().length() > 64) {
            errorList.add(FYUtils.fyParams("省名字最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        if (memberAddress.getCityId() == null) {
            errorList.add(FYUtils.fyParams("市ID不能为空"));
        }
        if (memberAddress.getCityId() != null && memberAddress.getCityId().toString().length() > 19) {
            errorList.add(FYUtils.fyParams("市ID最大长度不能超过"+"19" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isBlank(memberAddress.getCityName())) {
            errorList.add(FYUtils.fyParams("市名字不能为空"));
        }
        if (StringUtils.isNotBlank(memberAddress.getCityName()) && memberAddress.getCityName().length() > 64) {
            errorList.add(FYUtils.fyParams("市名字最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        if (memberAddress.getDistrictId() == null) {
            errorList.add(FYUtils.fyParams("县ID不能为空"));
        }
        if (memberAddress.getDistrictId() != null && memberAddress.getDistrictId().toString().length() > 19) {
            errorList.add(FYUtils.fyParams("县ID最大长度不能超过"+"19" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isBlank(memberAddress.getDistrictName())) {
            errorList.add(FYUtils.fyParams("县名字不能为空"));
        }
        if (StringUtils.isNotBlank(memberAddress.getDistrictName()) && memberAddress.getDistrictName().length() > 64) {
            errorList.add(FYUtils.fyParams("县名字最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isBlank(memberAddress.getDetailedAddress())) {
            errorList.add(FYUtils.fyParams("详细地址不能为空"));
        }
        if (StringUtils.isNotBlank(memberAddress.getDetailedAddress()) && memberAddress.getDetailedAddress().length() > 255) {
            errorList.add(FYUtils.fyParams("详细地址最大长度不能超过"+"255" + FYUtils.fyParams("字符")));
        }
        if (StringUtils.isBlank(memberAddress.getMobile())) {
            errorList.add(FYUtils.fyParams("手机号不能为空"));
        }
        if (StringUtils.isNotBlank(memberAddress.getMobile()) && memberAddress.getMobile().length() > 64) {
            errorList.add(FYUtils.fyParams("手机号最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        String mobilePattern = "^1[0-9]{10}$";
        if (!Pattern.matches(mobilePattern, memberAddress.getMobile())) {
            errorList.add(FYUtils.fyParams("请输入正确格式的手机号"));
        }
        if (StringUtils.isNotBlank(memberAddress.getZipCode()) && memberAddress.getZipCode().length() > 64) {
            errorList.add(FYUtils.fyParams("邮编最大长度不能超过"+"64" + FYUtils.fyParams("字符")));
        }
        String codePattern = "[1-9]\\d{5}";
        if (StringUtils.isNotBlank(memberAddress.getZipCode()) && !Pattern.matches(codePattern, memberAddress.getZipCode())) {
            errorList.add(FYUtils.fyParams("请输入六位的邮编"));
        }
        if (StringUtils.isBlank(R.get("isDefault"))) {
            errorList.add(FYUtils.fyParams("是否默认不能为空"));
        }
        if (StringUtils.isNotBlank(memberAddress.getIsDefault()) && memberAddress.getIsDefault().length() > 1) {
            errorList.add(FYUtils.fyParams("是否默认最大长度不能超过1字符"));
        }
        return errorList;
    }

}