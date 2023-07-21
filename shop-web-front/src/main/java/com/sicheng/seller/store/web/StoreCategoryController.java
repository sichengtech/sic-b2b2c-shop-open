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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sicheng.seller.interceptor.SellerMenuInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreCategory;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.store.service.StoreCategoryService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.utils.SsoUtils;

/**
 * <p>标题: StoreCategoryController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年2月20日 下午4:35:52
 */

@Controller
@RequestMapping(value = "${sellerPath}/store/storeCategory")
public class StoreCategoryController extends BaseController {

    @Autowired
    private StoreCategoryService storeCategoryService;

    @Autowired
    private ProductSpuService productSpuService;



    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "040120";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        SellerMenuInterceptor.menuHighLighting(menu3);
    }

    /**
     * 店内商品分类页面
     *
     * @param storeCategory
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeCategory:view")
    @RequestMapping(value = "list")
    public String list(StoreCategory storeCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeCategory.setStoreId(userSeller.getStoreId());
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(storeCategory);
        wrapper.orderBy("a.sort asc");
        List<StoreCategory> list = storeCategoryService.selectByWhere(wrapper);
        model.addAttribute("list", list);
        return "seller/store/storeCategoryList";
    }

    /**
     * 店内商品分类新增，编辑，添加下级分类页面
     *
     * @param storeCategory
     * @param model
     * @return
     */
    @RequiresPermissions("store:storeCategory:view")
    @RequestMapping(value = "form")
    public String form(StoreCategory storeCategory, Model model) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        StoreCategory entity = null;
        if (storeCategory.getStoreCategoryId() != null) {//编辑
            storeCategory.setStoreId(userSeller.getStoreId());//属主检查
            entity = storeCategoryService.selectOne(new Wrapper(storeCategory));
        }
        if (entity == null) {
            entity = storeCategory;
        }
        if (entity.getParent() != null && entity.getParent().getId() != null) {
            entity.setParent(storeCategoryService.selectById(entity.getParent().getId()));
            // 获取排序号，最末节点排序号+30
            if (entity.getId() == null) {
                StoreCategory storeCategoryChild = new StoreCategory();
                storeCategoryChild.setParent(new StoreCategory(entity.getParent().getId()));
                List<StoreCategory> list = storeCategoryService.selectAll(null);
                if (list.size() > 0) {
                    entity.setSort(list.get(list.size() - 1).getSort());
                    if (entity.getSort() != null) {
                        entity.setSort(entity.getSort() + 30);
                    }
                }
            }
        }
        if (entity.getSort() == null) {
            entity.setSort(30);
        }
        model.addAttribute("storeCategory", entity);
        return "seller/store/storeCategoryForm";
    }

    /**
     * 保存店内分类
     *
     * @param storeCategory
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeCategory:edit")
    @RequestMapping(value = "save")
    public String save(StoreCategory storeCategory, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(storeCategory, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return form(storeCategory, model);//回显错误提示
        }
        if (StringUtils.isBlank(R.get("isOpenHidden"))) {
            storeCategory.setIsOpen("1");
        } else {
            if (StringUtils.isBlank(storeCategory.getIsOpen())) {
                storeCategory.setIsOpen(R.get("isOpenHidden"));
            } else {
                storeCategory.setIsOpen(storeCategory.getIsOpen());
            }
        }
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeCategory.setStoreId(userSeller.getStoreId());
        if (storeCategory.getId() == null) {//保存
            storeCategoryService.save(storeCategory);
        } else {//编辑
            Wrapper wrapper = new Wrapper();
            wrapper.and("a.store_category_id =", storeCategory.getId());
            wrapper.and("a.store_id = ", userSeller.getStoreId());
            List<StoreCategory> list = storeCategoryService.selectByWhere(wrapper);//属主检查
            if (!list.isEmpty()) {
                storeCategoryService.save(storeCategory);
            }
        }
        addMessage(redirectAttributes, FYUtils.fyParams("保存店铺商品分类成功"));
        return "redirect:" + sellerPath + "/store/storeCategory/list.htm?repage";
    }

    /**
     * 删除店内分类
     *
     * @param storeCategory
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeCategory:edit")
    @RequestMapping(value = "delete")
    public String delete(StoreCategory storeCategory, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        //入参检查
        if (storeCategory == null || storeCategory.getStoreCategoryId() == null) {
            model.addAttribute("message", FYUtils.fyParams("分类不存在！"));
            return "error/400";
        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        List<StoreCategory> storeCategories = storeCategoryService.findChildNodeAll(storeCategory.getStoreCategoryId());
        if (storeCategories.isEmpty()) {
            //终极节点
            ProductSpu productSpu = new ProductSpu();
            productSpu.setStoreCategoryId(storeCategory.getStoreCategoryId());
            List<ProductSpu> productSpus = productSpuService.selectByWhere(new Wrapper(productSpu));
            if (!productSpus.isEmpty()) {
                addMessage(redirectAttributes, "删除失败，删除分类有商品");
                return "redirect:" + sellerPath + "/store/storeCategory/list.htm?repage";
            }
        } else {
            //不是终极节点
            for (int i = 0; i < storeCategories.size(); i++) {
                ProductSpu productSpu = new ProductSpu();
                productSpu.setStoreCategoryId(storeCategories.get(i).getStoreCategoryId());
                List<ProductSpu> productSpus = productSpuService.selectByWhere(new Wrapper(productSpu));
                if (!productSpus.isEmpty()) {
                    addMessage(redirectAttributes, FYUtils.fyParams("删除失败，删除子分类有商品"));
                    return "redirect:" + sellerPath + "/store/storeCategory/list.htm?repage";
                }
            }
        }
        storeCategory.setStoreId(userSeller.getStoreId());
        storeCategoryService.delete(storeCategory);
        addMessage(redirectAttributes, FYUtils.fyParams("删除店铺商品分类成功"));
        return "redirect:" + sellerPath + "/store/storeCategory/list.htm?repage";
    }

    /**
     * 店铺分类
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     * @param extId 排除的ID
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        List<Map<String, Object>> mapList = Lists.newArrayList();
        StoreCategory storeCategory = new StoreCategory();
        storeCategory.setStoreId(userSeller.getStoreId());
        List<StoreCategory> list = storeCategoryService.selectByWhere(new Wrapper(storeCategory));
        for (int i = 0; i < list.size(); i++) {
            StoreCategory e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 添加店内分类(发布商品页zTree中添加)
     *
     * @return
     */
    @RequiresPermissions("store:storeCategory:edit")
    @ResponseBody
    @RequestMapping(value = "addStoreCategory")
    public Map<String, Object> addStoreCategory() {
        String name = R.get("name");
        String parentId = R.get("parentId");
        String sort = R.get("sort");
        String storeCategoryId = R.get("storeCategoryId");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(name)) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("分类名不能为空"));
            return map;
        }
        if (name.length() > 64) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("分类名不能超过64字符"));
            return map;
        }
        if (StringUtils.isNoneBlank(parentId) && !StringUtils.isNumeric(parentId)) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("父分类Id必须是数字"));
            return map;
        }
        if (StringUtils.isNoneBlank(storeCategoryId) && !StringUtils.isNumeric(storeCategoryId)) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("分类Id必须是数字"));
            return map;
        }
        if (StringUtils.isNoneBlank(sort) && !StringUtils.isNumeric(sort)) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("排序必须是数字"));
            return map;
        }
        StoreCategory storeCategory = new StoreCategory();
        storeCategory.setName(name);
        if (StringUtils.isNotBlank(parentId)) {
            StoreCategory parentStoreCagetory = new StoreCategory();
            parentStoreCagetory.setId(Long.parseLong(parentId));
            storeCategory.setParent(parentStoreCagetory);
        }
        if (StringUtils.isNotBlank(sort)) {
            storeCategory.setSort(Integer.parseInt(sort));
        }
        if (StringUtils.isNotBlank(storeCategoryId)) {
            storeCategory.setId(Long.parseLong(storeCategoryId));
        }
        //判断当前分类是否有新商品
		/*ProductSpu productSpu = new ProductSpu();
		productSpu.setStoreCategoryId(storeCategory.getParentId());
		List<ProductSpu> productSpus = productSpuService.selectByWhere(new Wrapper(productSpu));
		if(!productSpus.isEmpty()){
			map.put("success", false);
			map.put("message","新增或编辑失败，目标分类有商品不能新增或编辑");
			return map;
		}*/
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
        storeCategory.setStoreId(userSeller.getStoreId());
        if (storeCategory.getStoreCategoryId() == null) {//保存
            if (StringUtils.isNoneBlank(parentId) && !"0".equals(parentId)) {
                StoreCategory storeCategoryParent = storeCategoryService.selectById(Long.parseLong(parentId));
                if (storeCategoryParent == null) {
                    map.put("success", false);
                    map.put("message", FYUtils.fyParams("父分类不存在"));
                    return map;
                }
                Long level = storeCategoryParent.getLevel();
                if (level == 2) {
                    map.put("success", false);
                    map.put("message", FYUtils.fyParams("店铺分类最多为2级"));
                    return map;
                }
            }
            storeCategoryService.save(storeCategory);
            map.put("store_category_id", storeCategory.getStoreCategoryId());
            map.put("success", true);
            return map;
        }
        //编辑
        Wrapper wrapper = new Wrapper();
        wrapper.and("a.store_category_id =", storeCategory.getId());
        wrapper.and("a.store_id = ", userSeller.getStoreId());
        List<StoreCategory> list = storeCategoryService.selectByWhere(wrapper);//属主检查
        if (!list.isEmpty()) {
            storeCategoryService.save(storeCategory);
        }
        map.put("success", true);
        map.put("store_category_id", storeCategory.getStoreCategoryId());
        return map;
    }

    /**
     * 删除店内分类(发布商品页zTree中删除)
     *
     * @param storeCategory
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:storeCategory:edit")
    @ResponseBody
    @RequestMapping(value = "deleteStoreCategory")
    public Map<String, Object> deleteStoreCategory(StoreCategory storeCategory, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        Map<String, Object> map = new HashMap<>();
        //入参检查
        if (storeCategory == null || storeCategory.getStoreCategoryId() == null) {
            map.put("success", false);
            map.put("message", FYUtils.fyParams("分类不存在"));
            return map;
        }
        //检查合格后，业务处理
        UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
		/*List<StoreCategory> storeCategoryList = storeCategoryService.findChildNodeAll(storeCategory.getStoreCategoryId());
		if(storeCategoryList.isEmpty()){
			//终极节点
			ProductSpu productSpu = new ProductSpu();
			productSpu.setStoreCategoryId(storeCategory.getStoreCategoryId());
			List<ProductSpu> productSpuList = productSpuService.selectByWhere(new Wrapper(productSpu));
			if(!productSpuList.isEmpty()){
				map.put("success", false);
				map.put("message","删除失败，本分类有商品");
				return map;
			}
		}else{
			//不是终极节点
			List<Long> storeCagegoryIdList=new ArrayList<>();
			for (int i = 0; i < storeCategoryList.size(); i++) {
				storeCagegoryIdList.add(storeCategoryList.get(i).getStoreCategoryId());
			}
			List<ProductSpu> productSpuList=productSpuService.selectByWhere(new Wrapper().and("store_category_id in",storeCagegoryIdList));
			if(!productSpuList.isEmpty()){
				map.put("success", false);
				map.put("message","删除失败，删除子分类有商品");
				return map;
			}
		}*/
        storeCategory.setStoreId(userSeller.getStoreId());
        storeCategoryService.delete(storeCategory);
        addMessage(redirectAttributes, FYUtils.fyParams("删除店铺商品分类成功"));
        map.put("success", true);
        map.put("message", FYUtils.fyParams("删除成功"));
        return map;
    }

    /**
     * 表单验证
     *
     * @param storeNavigation 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(StoreCategory storeCategory, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("分类名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fyParams("主键最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("sort"))) {
            errorList.add(FYUtils.fyParams("排序不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("sort")) && R.get("sort").length() > 10) {
            errorList.add(FYUtils.fyParams("排序最大长度不能超过10字符"));
        }
        Long parentId = storeCategory.getParentId();
        if (parentId != 0 && parentId != null) {
            StoreCategory sc = storeCategoryService.selectById(parentId);
            if (sc == null) {
                errorList.add(FYUtils.fyParams("店铺分类为空"));
            } else {
                Long level = sc.getLevel();
                if (level == 2) {
                    errorList.add(FYUtils.fyParams("店铺分类最多为2级"));
                }
            }
        }
        return errorList;
    }

}
