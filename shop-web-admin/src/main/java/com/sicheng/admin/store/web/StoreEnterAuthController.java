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
package com.sicheng.admin.store.web;

import com.sicheng.admin.site.service.SiteMessageTemplateService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sso.service.UserSellerService;
import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.admin.store.entity.StoreIndustry;
import com.sicheng.admin.store.entity.StoreLevel;
import com.sicheng.admin.store.service.StoreEnterAuthService;
import com.sicheng.admin.store.service.StoreIndustryService;
import com.sicheng.admin.store.service.StoreLevelService;

import com.sicheng.common.fileStorage.AccessKey;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.sms.SmsSender;
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
 * 入驻申请（业务审核） Controller
 * 所属模块：store
 *
 * @author 蔡龙
 * @version 2017-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/store/storeEnterAuth")
public class StoreEnterAuthController extends BaseController {

    @Autowired
    private StoreEnterAuthService storeEnterAuthService;

    @Autowired
    private StoreLevelService storeLevelService;
    @Autowired
    private StoreIndustryService storeIndustryService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private UserSellerService userSellerService;
    @Autowired
    private SiteMessageTemplateService siteMessageTemplateService;
    @Autowired
    private SmsSender smsSender;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "050102";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param storeEnterAuth 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeEnterAuth:view")
    @RequestMapping(value = "list")
    public String list(StoreEnterAuth storeEnterAuth, HttpServletRequest request, HttpServletResponse response, Model model) {
        String sellerName = R.get("sellerName");
        model.addAttribute("sellerName", sellerName);
        if (StringUtils.isNoneBlank(sellerName)) {
            //用户名转小写
            sellerName = sellerName.toLowerCase();
            UserMain userMain = new UserMain();
            userMain.setLoginName(sellerName);
            List<UserMain> userMains = userMainService.selectByWhere(new Wrapper(userMain));
            if (!userMains.isEmpty()) {
                storeEnterAuth.setEnterId(userMains.get(0).getUId());
            } else {
                return "admin/store/storeEnterAuthList";
            }
        }
        if (StringUtils.isNotBlank(storeEnterAuth.getStoreName())) {
            //店铺名转换小写
            storeEnterAuth.setStoreName(storeEnterAuth.getStoreName().toLowerCase());
        }
        //需要查询入驻信息完善信息的
        storeEnterAuth.setIsPerfect("1");
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(storeEnterAuth);
        wrapper.orderBy("a.update_date Desc");
        Page<StoreEnterAuth> page = storeEnterAuthService.selectByWhere(new Page<StoreEnterAuth>(request, response), wrapper);
        List<StoreEnterAuth> storeEnterAuths = page.getList();
        StoreEnterAuth.fillUserMain(storeEnterAuths);
        model.addAttribute("page", page);
        return "admin/store/storeEnterAuthList";
    }

    /**
     * 进入保存页面
     *
     * @param storeEnterAuth 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeEnterAuth:save")
    @RequestMapping(value = "save1")
    public String save1(StoreEnterAuth storeEnterAuth, Model model) {
        if (storeEnterAuth == null) {
            storeEnterAuth = new StoreEnterAuth();
        }
        model.addAttribute("storeEnterAuth", storeEnterAuth);
        return "admin/store/storeEnterAuthForm";
    }

    /**
     * 执行保存
     *
     * @param storeEnterAuth     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeEnterAuth:auth")
    @RequestMapping(value = "save2")
    public String save2(StoreEnterAuth storeEnterAuth, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeEnterAuth, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(storeEnterAuth, model);//回显错误提示
        }

        //业务处理
        storeEnterAuthService.insertSelective(storeEnterAuth);
        addMessage(redirectAttributes, "保存入驻申请（业务审核）成功");
        return "redirect:" + adminPath + "/store/storeEnterAuth/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param storeEnterAuth 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeEnterAuth:auth")
    @RequestMapping(value = "edit1")
    public String edit1(StoreEnterAuth storeEnterAuth, Model model) {
        StoreEnterAuth entity = null;
        if (storeEnterAuth != null) {
            if (storeEnterAuth.getId() != null) {
                entity = storeEnterAuthService.selectById(storeEnterAuth.getId());
            }
        }
        //获取等级
        StoreLevel storeLevel = storeLevelService.selectById(entity.getLevelId());
        //把等级中的空间容量转成MB
        if (storeLevel == null || storeLevel.getPictureSpace() == 0) {
            storeLevel = new StoreLevel();
            storeLevel.setPictureSpaceM("0");
        } else {
            String pictureSpaceM = FileSizeHelper.getHumanReadableFileSize(storeLevel.getPictureSpace());
            storeLevel.setPictureSpaceM(pictureSpaceM);
        }
        //获取主营行业
        StoreIndustry storeIndustry = storeIndustryService.selectById(entity.getIndustryId());
        //店铺品牌商标证书
        List<String> storeBrandPathsList = new ArrayList<String>();
        String storebrandPath = entity.getStoreBrandPath();
        if (StringUtils.isNotBlank(storebrandPath)) {
            String[] storebrandPaths = storebrandPath.split(",");
            for (int i = 0; i < storebrandPaths.length; i++) {
                storeBrandPathsList.add(storebrandPaths[i]);
            }
        }
        //付款说明
        List<String> paymentVoucherPathsList = new ArrayList<String>();
        String paymentVoucherPath = entity.getPaymentVoucherPath();
        if (StringUtils.isNotBlank(paymentVoucherPath)) {
            String[] paymentVoucherPaths = paymentVoucherPath.split(",");
            for (int i = 0; i < paymentVoucherPaths.length; i++) {
                paymentVoucherPathsList.add(paymentVoucherPaths[i]);
            }
        }
        model.addAttribute("storeEnterAuth", entity);
        model.addAttribute("storeLevel", storeLevel);
        model.addAttribute("storeIndustry", storeIndustry);
        model.addAttribute("storeBrandPathsList", storeBrandPathsList);
        model.addAttribute("paymentVoucherPathsList", paymentVoucherPathsList);
        model.addAttribute("attr", R.get("attr"));
        model.addAttribute("generateAccessKey", AccessKey.generateAccessKey());//隐秘图片密钥
        return "admin/store/storeEnterAuthOperation";
    }

    /**
     * 执行编辑
     *
     * @param storeEnterAuth     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeEnterAuth:auth")
    @RequestMapping(value = "edit2")
    public String edit2(StoreEnterAuth storeEnterAuth, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeEnterAuth, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(storeEnterAuth, model);//回显错误提示
        }
        String auth = R.get("auth");
        //业务处理
        String info = storeEnterAuthService.storeEnterAuthview(storeEnterAuth, auth);
        addMessage(redirectAttributes, info);
        //后台管理员审核完入驻信息后向会员发送审核结果
        sendMessage(storeEnterAuth);
        if ("4".equals(auth)) {
            //如果入驻二审审核通过,进入店铺初始化成功页面
            UserSeller userSeller = userSellerService.selectById(storeEnterAuth.getEnterId());
            return "redirect:" + adminPath + "/store/store/storeInit.do?storeId=" + userSeller.getStoreId();
        }
        return "redirect:" + adminPath + "/store/storeEnterAuth/list.do?repage";
    }

    /**
     * 删除
     *
     * @param storeEnterAuth     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeEnterAuth:drop")
    @RequestMapping(value = "delete")
    public String delete(StoreEnterAuth storeEnterAuth, RedirectAttributes redirectAttributes) {
        storeEnterAuthService.deleteById(storeEnterAuth.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除入驻申请（业务审核）成功"));
        return "redirect:" + adminPath + "/store/storeEnterAuth/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param storeEnterAuth 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreEnterAuth storeEnterAuth, Model model) {
        List<String> errorList = new ArrayList<String>();
//        if (StringUtils.isBlank(R.get("summaryOfCoping"))) {
//            errorList.add(FYUtils.fyParams("应付总金额不能为空"));
//        }
//        if (StringUtils.isNotBlank(R.get("summaryOfCoping")) && R.get("summaryOfCoping").length() > 12) {
//            errorList.add(FYUtils.fyParams("应付总金额最大长度不能超过12字符"));
//        }
//		if(StringUtils.isBlank(R.get("oneAuditOpinion"))){
//			errorList.add(FYUtils.fyParams("一审审核意见不能为空"));
//		}
        if (StringUtils.isNotBlank(R.get("oneAuditOpinion")) && R.get("oneAuditOpinion").length() > 255) {
            errorList.add(FYUtils.fyParams("一审审核意见最大长度不能超过255字符"));
        }
//		if(StringUtils.isBlank(R.get("twoAuditOpinion"))){
//			errorList.add(FYUtils.fyParams("二审审核意见不能为空"));
//		}
        if (StringUtils.isNotBlank(R.get("twoAuditOpinion")) && R.get("twoAuditOpinion").length() > 255) {
            errorList.add(FYUtils.fyParams("二审审核意见最大长度不能超过255字符"));
        }
//		if(StringUtils.isBlank(R.get("auditOpinion"))){
//			errorList.add(FYUtils.fyParams("入驻信息更改审核意见不能为空"));
//		}
        if (StringUtils.isNotBlank(R.get("auditOpinion")) && R.get("auditOpinion").length() > 255) {
            errorList.add(FYUtils.fyParams("入驻信息更改审核意见最大长度不能超过255字符"));
        }
        return errorList;
    }

    /**
     *  后台管理员审核完入驻信息后向会员发送审核结果
     *  @param storeEnterAuth 会员入驻信息
     *  
     */
    private void sendMessage(StoreEnterAuth storeEnterAuth) {
        if ("50".equals(storeEnterAuth.getStatus())) {
            String content = siteMessageTemplateService.getSmsContent(null, "enterAuthSuccess");
            if (StringUtils.isNotBlank(storeEnterAuth.getUserMain().getMobile())) {
                smsSender.sendSmsMessage(storeEnterAuth.getUserMain().getMobile(), content, null, "enterAuthSuccess", true);
            }
        }
        if ("60".equals(storeEnterAuth.getStatus())) {
            String content = siteMessageTemplateService.getSmsContent(null, "enterAuthFailure");
            if (StringUtils.isNotBlank(storeEnterAuth.getUserMain().getMobile())) {
                smsSender.sendSmsMessage(storeEnterAuth.getUserMain().getMobile(), content, null, "enterAuthFailure", true);
            }
        }
        if ("80".equals(storeEnterAuth.getStatus())) {
            String content = siteMessageTemplateService.getSmsContent(null, "enterInfoEditSuccess");
            if (StringUtils.isNotBlank(storeEnterAuth.getUserMain().getMobile())) {
                smsSender.sendSmsMessage(storeEnterAuth.getUserMain().getMobile(), content, null, "enterInfoEditSuccess", true);
            }
        }
        if ("90".equals(storeEnterAuth.getStatus())) {
            String content = siteMessageTemplateService.getSmsContent(null, "enterInfoEditFailure");
            if (StringUtils.isNotBlank(storeEnterAuth.getUserMain().getMobile())) {
                smsSender.sendSmsMessage(storeEnterAuth.getUserMain().getMobile(), content, null, "enterInfoEditFailure", true);
            }
        }
    }
}