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
package com.sicheng.admin.product.web;

import com.sicheng.admin.product.entity.ProductPictureMapping;
import com.sicheng.admin.product.entity.ProductSku;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.product.service.ProductSkuService;
import com.sicheng.admin.product.service.ProductSpuService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.service.StoreService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.search.ProductSearchInterface;
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
import java.util.Date;
import java.util.List;

/**
 * 商品 Controller
 * 所属模块：product
 *
 * @author zhaolei
 * @version 2017-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productSpu")
public class ProductSpuController extends BaseController {

    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private ProductSearchInterface productSearch;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "020101";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param productSpu 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:view")
    @RequestMapping(value = "list")
    public String list(ProductSpu productSpu, HttpServletRequest request, HttpServletResponse response, Model model) {
        Wrapper wrapper = new Wrapper(productSpu);
        //处理按店铺名搜索
        if (StringUtils.isNoneBlank(productSpu.getStoreName())) {
            Store store = new Store();
            store.setName(productSpu.getStoreName().toLowerCase());
            List<Store> storeList = storeService.selectByWhere(new Wrapper(store));
            if (storeList.size() > 0) {
                //通过商家店铺名查询出ID
                List<Long> storeIdList = new ArrayList<>();
                for (int i = 0; i < storeList.size(); i++) {
                    storeIdList.add(storeList.get(i).getStoreId());
                }
                wrapper.and("store_id in", storeIdList);
            } else {
                productSpu.setStoreId(-1l);
            }
        }
        //按商家用户名搜索
        if (StringUtils.isNotBlank(productSpu.getSellerName())) {
            UserMain userMain = new UserMain();
            userMain.setLoginName(productSpu.getSellerName().toLowerCase());
            List<UserMain> userMainList = userMainService.selectByWhere(new Wrapper(userMain));
            if (userMainList.size() > 0) {
                List<Long> uIdList = new ArrayList<>();
                for (int i = 0; i < userMainList.size(); i++) {
                    uIdList.add(userMainList.get(i).getUId());
                }
                wrapper.and("u_id in", uIdList);
            } else {
                productSpu.setUId(-1l);
            }
        }
        //三级菜单高亮
        String status = R.get("status");
        if ("50".equals(status)) {
            //在售商品
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            super.menuHighLighting("020103");
        } else if ("20".equals(status)) {
            //禁售商品
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            super.menuHighLighting("020106");
        } else if ("30".equals(status)) {
            //待审商品
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            super.menuHighLighting("020109");
        } else {
            //全部商品
            //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
            super.menuHighLighting("020101");
        }
        Page<ProductSpu> page = productSpuService.selectByWhere(new Page<ProductSpu>(request, response), wrapper);
        ProductSpu.fillProductCategory(page.getList());
        ProductSpu.fillUserMain(page.getList());
        ProductSpu.fillProductBrand(page.getList());
        ProductSpu.fillStore(page.getList());
        model.addAttribute("page", page);
        model.addAttribute("status", status);
        return "admin/product/productSpuList";
    }

    /**
     * 查看规格
     *
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:view")
    @RequestMapping(value = "showSku")
    public String showSku(Model model) {
        Long pId = R.getLong("pId");
        if (pId != null) {
            List<ProductSku> productSkuList = productSkuService.selectByWhere(new Wrapper().and("p_id =", pId));
            ProductSpu productSpu = productSpuService.selectById(pId);
            List<ProductPictureMapping> productPictureMappingList = productSpu.getProductPictureMappingList();
            model.addAttribute("productSkuList", productSkuList);
            model.addAttribute("productPictureMappingList", productPictureMappingList);
            model.addAttribute("productSpu", productSpu);
        }
        return "admin/product/productSku";
    }

    /**
     * 进入保存页面
     *
     * @param productSpu 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:save")
    @RequestMapping(value = "save1")
    public String save1(ProductSpu productSpu, Model model) {
        if (productSpu == null) {
            productSpu = new ProductSpu();
        }
        model.addAttribute("productSpu", productSpu);
        return "admin/product/productSpuForm";
    }

    /**
     * 执行保存
     *
     * @param productSpu         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "save2")
    public String save2(ProductSpu productSpu, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSpu, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(productSpu, model);//回显错误提示
        }

        //业务处理
        productSpuService.insertSelective(productSpu);
        addMessage(redirectAttributes, "保存商品成功");

        //按ID添加索引（异步线程池）
        productSearch.addDocAsyn(productSpu.getPId());

        return "redirect:" + adminPath + "/product/productSpu/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param productSpu 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "edit1")
    public String edit1(ProductSpu productSpu, Model model) {
        ProductSpu entity = null;
        if (productSpu != null) {
            if (productSpu.getId() != null) {
                entity = productSpuService.selectById(productSpu.getId());
            }
        }
        model.addAttribute("productSpu", entity);
        return "admin/product/productSpuForm";
    }

    /**
     * 执行编辑
     *
     * @param productSpu         实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "edit2")
    public String edit2(ProductSpu productSpu, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productSpu, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(productSpu, model);//回显错误提示
        }

        //业务处理
        productSpuService.updateByIdSelective(productSpu);
        addMessage(redirectAttributes, "编辑商品成功");

        //按ID添加索引（异步线程池）
        productSearch.addDocAsyn(productSpu.getPId());

        return "redirect:" + adminPath + "/product/productSpu/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productSpu         实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductSpu productSpu, RedirectAttributes redirectAttributes) {
        productSpuService.deleteById(productSpu.getId());
        addMessage(redirectAttributes, "删除商品成功");

        //按ID删除索引（异步线程池）
        productSearch.deleteDocAsyn(productSpu.getPId());

        return "redirect:" + adminPath + "/product/productSpu/list.do?repage";
    }

    /**
     * 根据商品id查询商品(推荐商品页面使用)
     *
     * @param pId 商品id
     * @return
     */
    @RequiresPermissions("product:productSpu:view")
    @ResponseBody
    @RequestMapping(value = "selectById")
    public ProductSpu selectById(String pId) {
        ProductSpu productSpu = new ProductSpu();
        //String pId=R.get("pId");
        if (StringUtils.isBlank(pId) || !StringUtils.isNumeric(pId)) {
            return productSpu;
        }
        Long pIdl = Long.parseLong(pId);
        productSpu = productSpuService.selectById(pIdl);
        return productSpu;
    }


    /**
     * 表单验证
     *
     * @param productSpu 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductSpu productSpu, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("商品名称不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 128) {
            errorList.add("商品名称最大长度不能超过128字符");
        }
        if (StringUtils.isBlank(R.get("categoryId"))) {
            errorList.add("商品分类不能为空");
        }
        if (StringUtils.isNotBlank(R.get("categoryId")) && R.get("categoryId").length() > 19) {
            errorList.add("店内分类最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("storeCategoryId"))) {
            errorList.add("商品分类不能为空");
        }
        if (StringUtils.isNotBlank(R.get("storeCategoryId")) && R.get("storeCategoryId").length() > 19) {
            errorList.add("店内分类最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("storeId"))) {
            errorList.add("店铺不能为空");
        }
        if (StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 19) {
            errorList.add("店铺表最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("sellerId"))) {
            errorList.add("卖家不能为空");
        }
        if (StringUtils.isNotBlank(R.get("sellerId")) && R.get("sellerId").length() > 19) {
            errorList.add("卖家最大长度不能超过19字符");
        }
        if (StringUtils.isBlank(R.get("status"))) {
            errorList.add("状态不能为空");
        }
        if (StringUtils.isNotBlank(R.get("status")) && R.get("status").length() > 2) {
            errorList.add("状态最大长度不能超过2字符");
        }
        if (StringUtils.isBlank(R.get("image"))) {
            errorList.add("图片不能为空");
        }
        if (StringUtils.isNotBlank(R.get("image")) && R.get("image").length() > 128) {
            errorList.add("图片最大长度不能超过128字符");
        }
        if (StringUtils.isBlank(R.get("brandId"))) {
            errorList.add("品牌不能为空");
        }
        if (StringUtils.isNotBlank(R.get("brandId")) && R.get("brandId").length() > 19) {
            errorList.add("品牌最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("nameSub")) && R.get("nameSub").length() > 255) {
            errorList.add("副标题最大长度不能超过255字符");
        }
        if (StringUtils.isNotBlank(R.get("unit")) && R.get("unit").length() > 12) {
            errorList.add("计量单位最大长度不能超过12字符");
        }
        if (StringUtils.isNotBlank(R.get("type")) && R.get("type").length() > 1) {
            errorList.add("销售类型最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("isGift"))) {
            errorList.add("是否为赠品不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isGift")) && R.get("isGift").length() > 1) {
            errorList.add("赠品最大长度不能超过1字符");
        }
        if (StringUtils.isBlank(R.get("isRecommend"))) {
            errorList.add("是否推荐不能为空");
        }
        if (StringUtils.isNotBlank(R.get("isRecommend")) && R.get("isRecommend").length() > 1) {
            errorList.add("是否推荐最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("recommendSort")) && R.get("recommendSort").length() > 10) {
            errorList.add("推荐排序最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("point")) && R.get("point").length() > 64) {
            errorList.add("赠送积分最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("weight")) && R.get("weight").length() > 12) {
            errorList.add("重量最大长度不能超过12字符");
        }
        if (StringUtils.isNotBlank(R.get("volume")) && R.get("volume").length() > 12) {
            errorList.add("体积最大长度不能超过12字符");
        }
        if (StringUtils.isNotBlank(R.get("invoice")) && R.get("invoice").length() > 1) {
            errorList.add("发票最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("action")) && R.get("action").length() > 1) {
            errorList.add("是否参加促销活动最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("expressType")) && R.get("expressType").length() > 1) {
            errorList.add("运费方式最大长度不能超过1字符");
        }
        if (StringUtils.isNotBlank(R.get("ltId")) && R.get("ltId").length() > 19) {
            errorList.add("运费模板ID最大长度不能超过19字符");
        }
        if (StringUtils.isNotBlank(R.get("bak1")) && R.get("bak1").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak2")) && R.get("bak2").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak3")) && R.get("bak3").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak4")) && R.get("bak4").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak5")) && R.get("bak5").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak6")) && R.get("bak6").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak7")) && R.get("bak7").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak8")) && R.get("bak8").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak9")) && R.get("bak9").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak10")) && R.get("bak10").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak11")) && R.get("bak11").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak12")) && R.get("bak12").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak13")) && R.get("bak13").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak14")) && R.get("bak14").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("bak15")) && R.get("bak15").length() > 64) {
            errorList.add("备用字段最大长度不能超过64字符");
        }
        if (StringUtils.isNotBlank(R.get("n1")) && R.get("n1").length() > 10) {
            errorList.add("备用字段最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("n2")) && R.get("n2").length() > 10) {
            errorList.add("备用字段最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("n3")) && R.get("n3").length() > 10) {
            errorList.add("备用字段最大长度不能超过10字符");
        }
        if (StringUtils.isNotBlank(R.get("cause")) && R.get("cause").length() > 512) {
            errorList.add("原因最大长度不能超过512字符");
        }
        return errorList;
    }

    /**
     * 禁售、解禁某个商品
     *
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "forbidSale")
    public String forbidSale(Model model, RedirectAttributes redirectAttributes) {
        Long pId = R.getLong("pId");//商品ID
        Boolean forbidSale = R.getBoolean("forbidSale");//true:禁售，false：解禁
        String cause = R.get("cause");//禁售原因
        if (pId == null || forbidSale == null) {
            addMessage(redirectAttributes,FYUtils.fy("操作商品失败,缺少参数"));
            return "redirect:" + adminPath + "/product/productSpu/list.do?repage";
        }
        ProductSpu productSpu = productSpuService.selectById(pId);
        if (productSpu == null) {
            addMessage(redirectAttributes, FYUtils.fy("审核商品失败,商品不存在"));
            return "redirect:" + adminPath + "/product/productSpu/list.do?repage";
        }
        String status = "";
        if (forbidSale) {
            status = "20";
        } else {
            //发布，0放入仓库中，1立即发布
            if ("1".equals(productSpu.getPublish())) {
                //10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
                status = "50";
            } else {
                status = "10";
            }
        }
        if (StringUtils.isBlank(cause)) {
            if (forbidSale) {
                cause = FYUtils.fy("禁售商品");
            } else {
                cause = FYUtils.fy("解禁商品");
            }
        }

        ProductSpu productSpu2 = new ProductSpu();
        productSpu2.setPId(pId);
        productSpu2.setStatus(status);  //10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        productSpu2.setCause(cause);
        int rs = productSpuService.updateByIdSelective(productSpu2);
        if (rs == 1) {
            if (forbidSale) {
                addMessage(redirectAttributes, FYUtils.fy("商品已被禁售"));
            } else {
                String message = FYUtils.fy("解禁商品成功");
                if ("50".equals(status)) {
                    message = FYUtils.fy("解禁商品成功,成为在售商品");
                } else {
                    message = FYUtils.fy("解禁商品成功,放入仓库中");
                }
                addMessage(redirectAttributes, message);
            }
        } else {
            addMessage(redirectAttributes, FYUtils.fy("操作商品失败")+",pId=" + pId +FYUtils.fy("的商品不存在"));
        }

        //向MQ发消息
        //状态,10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        String status2 = productSpu2.getStatus();
        if(status2!=null) {
            int status3=Integer.valueOf(status2);
            if(status3<50) {
                //按ID删除索引（异步线程池）
                productSearch.deleteDocAsyn(productSpu.getPId());
            }else {
                //按ID添加索引（异步线程池）
                productSearch.addDocAsyn(productSpu.getPId());
            }
        }

        return "redirect:" + adminPath + "/product/productSpu/list.do?repage";
    }

    /**
     * 审核通过、审核不通过某个商品
     *
     * @return
     */
    @RequiresPermissions("product:productSpu:edit")
    @RequestMapping(value = "auth")
    public String auth(Model model, RedirectAttributes redirectAttributes) {
        Long pId = R.getLong("pId");//商品ID
        Boolean auth = R.getBoolean("auth");//true:审核通过，false：审核不通
        String cause = R.get("cause");//审核不通原因
        if (pId == null || auth == null) {
            addMessage(redirectAttributes, FYUtils.fy("审核商品失败,缺少参数"));
            return "redirect:" + adminPath + "/product/productSpu/list.do?repage";
        }
        ProductSpu productSpu = productSpuService.selectById(pId);
        if (productSpu == null) {
            addMessage(redirectAttributes, FYUtils.fy("审核商品失败,商品不存在"));
            return "redirect:" + adminPath + "/product/productSpu/list.do?repage";
        }
        String status = "";
        if (auth) {
            //发布，0放入仓库中，1立即发布
            if ("1".equals(productSpu.getPublish())) {
                //10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
                status = "50";
            } else {
                status = "10";
            }
        } else {
            status = "40";
        }
        if (StringUtils.isBlank(cause)) {
            if (auth) {
                cause = FYUtils.fy("审核通过");
            } else {
                cause = FYUtils.fy("审核未通过");
            }
        }
        ProductSpu productSpu2 = new ProductSpu();
        productSpu2.setPId(pId);
        productSpu2.setStatus(status);  //10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        productSpu2.setCause(cause);
        if ("50".equals(status)) {
            productSpu2.setShelfTime(new Date());
        }
        int rs = productSpuService.updateByIdSelective(productSpu2);
        if (rs == 1) {
            if (auth) {
                String message = FYUtils.fy("审核通过");
                if ("50".equals(status)) {
                    message = FYUtils.fy("审核通过,成为在售商品");
                } else {
                    message = FYUtils.fy("审核通过,成为仓库中的商品");
                }
                addMessage(redirectAttributes, message);
            } else {
                addMessage(redirectAttributes, FYUtils.fy("已标记,审核未通过"));
            }
        } else {
            addMessage(redirectAttributes, FYUtils.fy("审核商品失败")+",pId=" + pId +FYUtils.fy("的商品不存在"));
        }

        //向MQ发消息
        //状态,10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
        String status2 = productSpu2.getStatus();
        if(status2!=null) {
            int status3=Integer.valueOf(status2);
            if(status3<50) {
                //按ID删除索引（异步线程池）
                productSearch.deleteDocAsyn(productSpu.getPId());
            }else {
                //按ID添加索引（异步线程池）
                productSearch.addDocAsyn(productSpu.getPId());
            }
        }

        return "redirect:" + adminPath + "/product/productSpu/list.do?repage";
    }

}