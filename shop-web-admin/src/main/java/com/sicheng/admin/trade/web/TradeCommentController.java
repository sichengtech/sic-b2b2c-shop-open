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
package com.sicheng.admin.trade.web;

import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.product.service.ProductSpuService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.service.StoreService;

import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.service.TradeCommentService;
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
 * 评论 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-07O
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeComment")
public class TradeCommentController extends BaseController {

    @Autowired
    private TradeCommentService tradeCommentService;



    @Autowired
    private UserMainService userMainService;

    @Autowired
    private ProductSpuService productSpuService;

    @Autowired
    private StoreService storeService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030102";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param tradeComment 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeComment:view")
    @RequestMapping(value = "list")
    public String list(TradeComment tradeComment, HttpServletRequest request, HttpServletResponse response, Model model) {
    	//搜索条件
        String username = R.get("userName");//接收会员名
        String productName = R.get("productName"); //接收产品名
        String storeName = R.get("storeName");//接收店铺名
    	model.addAttribute("username", username);
        model.addAttribute("productName", productName);
        model.addAttribute("storeName", storeName);
        
        //查询
    	Wrapper wrapper = new Wrapper(tradeComment);
        Long uId = 0L;
        if (StringUtils.isNotBlank(username)) {
            //用户名转小写
            username = username.toLowerCase();
            //用会员名去换取id
            UserMain userMain = new UserMain();
            userMain.setLoginName(username);
            List<UserMain> memberUserList = userMainService.selectByWhere(new Wrapper(userMain));
            if (memberUserList.size() > 0) {
                uId = memberUserList.get(0).getUId();
                tradeComment.setUId(uId);
            } else {
                model.addAttribute("username", username);
                return "admin/trade/tradeCommentList";
            }
        }
        Long pId = 0L;
        if (StringUtils.isNotBlank(productName)) {
            //用产品名换取id
            ProductSpu productSpu = new ProductSpu();
            productSpu.setName(productName);
            List<ProductSpu> productSpuList = productSpuService.selectByWhere(new Wrapper(productSpu));
            if (productSpuList.size() > 0) {
                pId = productSpuList.get(0).getId();
                tradeComment.setPId(pId);
            } else {
                model.addAttribute("productName", productName);
                return "admin/trade/tradeCommentList";
            }
        }
        if (!(storeName == null || "".equals(storeName))) {
            //店铺名转小写
            storeName = storeName.toLowerCase();
            //用店铺名去换取id
            Store store = new Store();
            store.setName(storeName);
            List<Store> storeList = storeService.selectByWhere(new Wrapper(store));
            if (storeList.size() > 0) {
                List<Long> storeIdList = new ArrayList<>();
                for (int i = 0; i < storeList.size(); i++) {
                    storeIdList.add(storeList.get(i).getStoreId());
                }
                wrapper.and("store_id in", storeIdList);
            } else {
                model.addAttribute("storeName", storeName);
                return "admin/trade/tradeCommentList";
            }
        }
        //类型，0评论、1追评、2回复
        tradeComment.setType("0");
        //是否显示，0否、1是
        tradeComment.setIsShow("1");
        Page<TradeComment> page = tradeCommentService.selectByWhere(new Page<TradeComment>(request, response), new Wrapper(tradeComment));
        model.addAttribute("page", page);
        return "admin/trade/tradeCommentList";
    }

    /**
     * 进入保存页面
     *
     * @param tradeComment 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeComment:save")
    @RequestMapping(value = "save1")
    public String save1(TradeComment tradeComment, Model model) {
        if (tradeComment == null) {
            tradeComment = new TradeComment();
        }
        model.addAttribute("tradeComment", tradeComment);
        return "admin/trade/tradeCommentForm";
    }

    /**
     * 执行保存
     *
     * @param tradeComment       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComment:save")
    @RequestMapping(value = "save2")
    public String save2(TradeComment tradeComment, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeComment, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(tradeComment, model);//回显错误提示
        }

        //业务处理
        tradeCommentService.insertSelective(tradeComment);
        addMessage(redirectAttributes, "保存评论成功");
        return "redirect:" + adminPath + "/trade/tradeComment/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param tradeComment 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeComment:edit")
    @RequestMapping(value = "edit1")
    public String edit1(TradeComment tradeComment, Model model) {
        TradeComment entity = null;
        if (tradeComment != null) {
            if (tradeComment.getId() != null) {
                entity = tradeCommentService.selectById(tradeComment.getId());
            }
        }
        model.addAttribute("tradeComment", entity);
        return "admin/trade/tradeCommentForm";
    }

    /**
     * 执行编辑
     *
     * @param tradeComment       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComment:edit")
    @RequestMapping(value = "edit2")
    public String edit2(TradeComment tradeComment, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeComment, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeComment, model);//回显错误提示
        }

        //业务处理
        tradeCommentService.updateByIdSelective(tradeComment);
        addMessage(redirectAttributes, "编辑评论成功");
        return "redirect:" + adminPath + "/trade/tradeComment/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeComment       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComment:drop")
    @RequestMapping(value = "delete")
    public String delete(TradeComment tradeComment, RedirectAttributes redirectAttributes) {
        if (tradeComment != null) {
            tradeCommentService.deleteComment(tradeComment);
        }
        addMessage(redirectAttributes, FYUtils.fy("删除评价成功"));
        return "redirect:" + adminPath + "/trade/tradeComment/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeComment tradeComment, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isNotBlank(R.get("uId")) && R.get("uId").length() > 19) {
            errorList.add("会员id最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 19) {
            errorList.add("商家id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("pId"))) {
            errorList.add("产品id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("pId")) && R.get("pId").length() > 19) {
            errorList.add("产品id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("skuId"))) {
            errorList.add("sku id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("skuId")) && R.get("skuId").length() > 19) {
            errorList.add("sku id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("orderId"))) {
            errorList.add("订单id不能为空");
        }
        if (StringUtils.isNotBlank(R.get("orderId")) && R.get("orderId").length() > 19) {
            errorList.add("订单id最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("content"))) {
            errorList.add("评论内容不能为空");
        }
        if (StringUtils.isNotBlank(R.get("content")) && R.get("content").length() > 512) {
            errorList.add("评论内容最大长度不能超过512字符");
        }
        if (StringUtils.isBlank(R.get("score"))) {
            errorList.add("评分不能为空");
        }
        if (StringUtils.isNotBlank(R.get("score")) && R.get("score").length() > 64) {
            errorList.add("评分最大长度不能超过64字符");
        }
        if (StringUtils.isBlank(R.get("type"))) {
            errorList.add("类型不能为空");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("类型最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("replyId")) && R.get("replyId").length() > 19) {
            errorList.add("回复的评论最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("ip")) && R.get("ip").length() > 64) {
            errorList.add("ip最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("isShow")) && R.get("isShow").length() > 1) {
            errorList.add("是否显示最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("bak1")) && R.get("bak1").length() > 64) {
            errorList.add("备用字段1最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak2")) && R.get("bak2").length() > 64) {
            errorList.add("备用字段2最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak3")) && R.get("bak3").length() > 64) {
            errorList.add("备用字段3最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak4")) && R.get("bak4").length() > 64) {
            errorList.add("备用字段4最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak5")) && R.get("bak5").length() > 64) {
            errorList.add("备用字段5最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak6")) && R.get("bak6").length() > 64) {
            errorList.add("备用字段6最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak7")) && R.get("bak7").length() > 64) {
            errorList.add("备用字段7最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak8")) && R.get("bak8").length() > 64) {
            errorList.add("备用字段8最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak9")) && R.get("bak9").length() > 64) {
            errorList.add("备用字段9最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak10")) && R.get("bak10").length() > 64) {
            errorList.add("备用字段10最大长度不能超过64字符");
        }
        return errorList;
    }

}