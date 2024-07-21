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
package com.sicheng.admin.store.web;

import com.sicheng.admin.product.entity.ProductBrand;
import com.sicheng.admin.product.service.ProductBrandService;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.store.entity.Store;
import com.sicheng.admin.store.entity.StoreBrand;
import com.sicheng.admin.store.service.StoreBrandService;
import com.sicheng.admin.store.service.StoreLevelService;
import com.sicheng.admin.store.service.StoreService;

import com.sicheng.common.cache.CacheConstant;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺 Controller
 * 所属模块：store
 *
 * @author cl
 * @version 2017-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/store/store")
public class StoreController extends BaseController {

    @Autowired
    private StoreService storeService;



    @Autowired
    private StoreBrandService storeBrandService;

    @Autowired
    private ProductBrandService productBrandService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private StoreLevelService storeLevelService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "050103";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param store    实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("store:store:view")
    @RequestMapping(value = "list")
    public String list(Store store, HttpServletRequest request, HttpServletResponse response, Model model) {
        //店铺名转小写
        if (StringUtils.isNotBlank(store.getName())) {
            store.setName(store.getName().toLowerCase());
        }
        Page<Store> page = storeService.selectByWhere(new Page<Store>(request, response), new Wrapper(store));
        List<Store> stores = page.getList();
        Store.fillStoreLevel(stores);
        Store.fillStoreIndustry(stores);
        //赋值是否绑定品牌
        List<Long> storeIds = new ArrayList<Long>();
        if (!stores.isEmpty()) {
            for (int i = 0; i < stores.size(); i++) {
                storeIds.add(stores.get(i).getStoreId());
            }
            List<StoreBrand> storeList = storeBrandService.selectByIdIn(storeIds);

            List<Long> storeIds1 = new ArrayList<Long>();
            for (int i = 0; i < storeList.size(); i++) {
                storeIds1.add(storeList.get(i).getStoreId());
            }

            for (int i = 0; i < stores.size(); i++) {
                for (int j = 0; j < storeList.size(); j++) {
                    if (stores.get(i).getStoreId().equals(storeList.get(j).getStoreId())) {
                        stores.get(i).setIsBindingBrand("1");// 是否绑定品牌 1:是
                    }
                }
            }
        }
        model.addAttribute("page", page);
        return "admin/store/storeList";
    }

    /**
     * 进入店铺待运营列表页
     *
     * @param userMain    实体对象
     * @param request request
     * @param response response
     * @param model model
     * @return
     */
    @RequiresPermissions("store:store:view")
    @RequestMapping(value = "operatelist")
    public String operatelist(UserMain userMain, HttpServletRequest request, HttpServletResponse response, Model model) {
        String menu3 = "050115";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
        userMain.setParentUid(0L);//父ID，为0表示是主账号
        Page<UserMain> page = userMainService.selectByWhere(new Page<UserMain>(request, response), new Wrapper(userMain));
        model.addAttribute("page", page);
        return "admin/store/storeOperateList";
    }

    /**
     * 模拟登录进入店铺
     *
     * @param um
     * @return
     */
    @RequiresPermissions("store:store:edit")
    @ResponseBody
    @RequestMapping(value = "storeOperate")
    public Map<String, String> operatelist(UserMain um) {
        String verification = IdGen.uuid();//验证码
        Map<String, String> map = new HashMap<String, String>();
        if (um == null || um.getUId() == null) {
            map.put("status", "0");
            map.put("content", FYUtils.fyParams("进入店铺失败"));
            return map;
        }
        UserMain userMain = userMainService.selectById(um.getUId());
        if ("1".equals(userMain.getIsLocked())) {
            map.put("status", "0");
            map.put("content", FYUtils.fyParams("账户已被锁定"));
            return map;
        }
        map.put("status", "1");
        map.put("loginName", userMain.getLoginName());
        map.put("verification", verification);
        //往缓存中放值
        cache.put(CacheConstant.SIMULATION_LOGIN + userMain.getUId(), verification, 60L);

        //如果使用ehcache缓存，这里需要延时1秒，让ehcache缓存同步数据完成再登录
        //ehcache数据同步有延迟，不可大规模使用这个特性。只在这里使用了。
        if(cache!=null && cache.getClass().toString().endsWith("EhShopCache")){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
        }
        return map;
    }

    /**
     * 进入保存页面
     *
     * @param store 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:store:save")
    @RequestMapping(value = "save1")
    public String save1(Store store, Model model) {
        if (store == null) {
            store = new Store();
        }
        model.addAttribute("store", store);
        return "admin/store/storeForm";
    }

    /**
     * 执行保存
     *
     * @param store              实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:store:save")
    @RequestMapping(value = "save2")
    public String save2(Store store, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(store, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return save1(store, model);//回显错误提示
        }
        store.setIsOpen(R.get("isOpen", "0"));
        //业务处理
        storeService.insertSelective(store);
        addMessage(redirectAttributes, FYUtils.fyParams("保存店铺成功"));
        return "redirect:" + adminPath + "/store/store/list.do?repage";
    }

    /**
     * 进入编辑页面
     *
     * @param store 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "edit1")
    public String edit1(Store store, Model model) {
        Store entity = null;
        if (store != null) {
            if (store.getId() != null) {
                entity = storeService.selectById(store.getId());
            }
        }
        model.addAttribute("store", entity);
        model.addAttribute("storeLevel", storeLevelService.selectByWhere(new Wrapper().and("is_Open=", "1").orderBy("sort ASC")));
        return "admin/store/storeForm";
    }

    /**
     * 执行编辑
     *
     * @param store              实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "edit2")
    public String edit2(Store store, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(store, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fyParams("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(store, model);//回显错误提示
        }
        String isOpen = R.get("isOpen", "1");
        store.setIsOpen(isOpen);//店铺营业状态(0关闭、1开启)(首页搜索店铺搜索未关闭的)
        //业务处理
        storeService.closeStore(store, isOpen);
        addMessage(redirectAttributes, FYUtils.fyParams("编辑店铺成功"));
        return "redirect:" + adminPath + "/store/store/list.do?repage";
    }

    /**
     * 删除
     *
     * @param store              实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "delete")
    public String delete(Store store, RedirectAttributes redirectAttributes) {
        storeService.deleteById(store.getId());
        addMessage(redirectAttributes, FYUtils.fyParams("删除店铺成功"));
        return "redirect:" + adminPath + "/store/store/list.do?repage";
    }

    /**
     * 进入店铺绑定品牌
     *
     * @param model
     * @return
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "bindBrand1")
    public String bindBrand1(Model model) {
        Long storeId = R.getLong("storeId");
        //获取已选择店铺品牌
        StoreBrand storeBrand = new StoreBrand();
        storeBrand.setStoreId(storeId);
        List<StoreBrand> selectedBrand = storeBrandService.selectByWhere(new Wrapper(storeBrand));
        List<Long> brandIds1 = new ArrayList<Long>();
        for (int i = 0; i < selectedBrand.size(); i++) {
            brandIds1.add(selectedBrand.get(i).getBrandId());
        }
        //获取所有店铺品牌
        ProductBrand productBrand = new ProductBrand();
        productBrand.setStatus("1");//审核状态，0待审、1通过、2未通过
        List<ProductBrand> productBrandList = productBrandService.selectByWhere(new Wrapper(productBrand));
        List<Long> brandIds3 = new ArrayList<Long>();
        if (!productBrandList.isEmpty()) {
            for (int i = 0; i < productBrandList.size(); i++) {
                brandIds3.add(productBrandList.get(i).getBrandId());
            }
        }
        brandIds3.removeAll(brandIds1);
        List<ProductBrand> selectedStoreBrand = new ArrayList<ProductBrand>();
        List<ProductBrand> unselectedStoreBrand = new ArrayList<ProductBrand>();
        if (brandIds1.size() != 0) {
            //已选择
            for (int i = 0; i < brandIds1.size(); i++) {
                for (int j = 0; j < productBrandList.size(); j++) {
                    if (brandIds1.get(i).equals(productBrandList.get(j).getBrandId())) {
                        selectedStoreBrand.add(productBrandList.get(j));
                    }
                }
            }
        }
        for (int i = 0; i < brandIds3.size(); i++) {
            for (int j = 0; j < productBrandList.size(); j++) {
                if (brandIds3.get(i).equals(productBrandList.get(j).getBrandId())) {
                    unselectedStoreBrand.add(productBrandList.get(j));
                }
            }
        }
        model.addAttribute("selectedStoreBrand", selectedStoreBrand);
        model.addAttribute("unselectedStoreBrand", unselectedStoreBrand);
        return "admin/store/storeBindBrand";
    }

    /**
     * 保存店铺品牌
     */
    @RequiresPermissions("store:store:edit")
    @ResponseBody
    @RequestMapping(value = "bindBrand2")
    public Map<String, String> bindBrand2(Long storeId, String brandIds) {
        Map<String, String> map = new HashMap<String, String>();
        if (storeId == null || StringUtils.isBlank(brandIds)) {
            map.put("status", "1");
            map.put("content", FYUtils.fyParams("绑定失败:店铺或品牌为空"));
            return map;
        }
        storeBrandService.save(storeId, brandIds);
        map.put("status", "0");
        map.put("content", FYUtils.fyParams("绑定成功"));
        return map;
    }

    /**
     * 弹出绑定佣金
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "storeCommissionSave1")
    public String storeCommission(Long storeId, Model model) {
        Store store = storeService.selectById(storeId);
        model.addAttribute("store", store);
        return "admin/store/storeCommissionForm";
    }

    /**
     * 保存店铺佣金
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "storeCommissionSave2")
    public String bindCommission(Long storeId, BigDecimal commission, RedirectAttributes redirectAttributes) {
        if (storeId == null || commission == null) {
            addMessage(redirectAttributes, FYUtils.fyParams("佣金为空"));
            return "redirect:" + adminPath + "/store/store/list.do?repage";
        }
        storeService.bindCommission(storeId, commission);
        addMessage(redirectAttributes, FYUtils.fyParams("绑定佣金成功"));
        return "redirect:" + adminPath + "/store/store/list.do?repage";
    }

    /**
     * 弹出店铺打标
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "storeMarkingSave1")
    public String storeMarking(Long storeId, Model model) {
        Store store = storeService.selectById(storeId);
        model.addAttribute("store", store);
        return "admin/store/storeMarkingForm";
    }

    /**
     * 保存店铺打标
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "storeMarkingSave2")
    public String storeMarking(Long storeId, String markingImgPath, RedirectAttributes redirectAttributes) {
        if (storeId == null || StringUtils.isBlank(markingImgPath)) {
            addMessage(redirectAttributes, FYUtils.fyParams("打标失败"));
            return "redirect:" + adminPath + "/store/store/list.do?repage";
        }
        Store store = new Store();
        store.setStoreId(storeId);
        store.setMarkingImgPath(markingImgPath);
        storeService.updateByIdSelective(store);
        addMessage(redirectAttributes, FYUtils.fyParams("打标成功"));
        return "redirect:" + adminPath + "/store/store/list.do?repage";
    }

    /**
     * 店铺初始化成功页面
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "storeInit")
    public String storeInit(Long storeId, Model model) {
        //查询店铺
        Store store = storeService.selectById(storeId);
        //查询店铺绑定品牌
        StoreBrand storeBrand = new StoreBrand();
        storeBrand.setStoreId(storeId);
        List<StoreBrand> storeBrandList = storeBrandService.selectByWhere(new Wrapper(storeBrand));
        model.addAttribute("store", store);
        model.addAttribute("storeBrandList", storeBrandList);
        return "admin/store/storeInit";
    }

    /**
     * 成功页面绑定店铺品牌
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "storeBindBrand")
    public String storeBindBrand(Long storeId, String brandIds, RedirectAttributes redirectAttributes) {
        if (storeId == null || StringUtils.isBlank(brandIds)) {
            addMessage(redirectAttributes, FYUtils.fyParams("绑定失败：店铺不存在，或未选择品牌"));
            return "redirect:" + adminPath + "/store/store/storeInit.do?repage";
        }
        storeBrandService.save(storeId, brandIds);
        addMessage(redirectAttributes, FYUtils.fyParams("绑定品牌成功"));
        return "redirect:" + adminPath + "/store/store/storeInit.do?repage";
    }

    /**
     * 成功页面保存店铺佣金
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "storeInitCommissionSave")
    public String storeInitCommissionSave(Long storeId, BigDecimal commission, RedirectAttributes redirectAttributes) {
        if (storeId == null || commission == null) {
            addMessage(redirectAttributes, FYUtils.fyParams("佣金为空"));
            return "redirect:" + adminPath + "/store/store/storeInit.do?repage";
        }
        storeService.bindCommission(storeId, commission);
        addMessage(redirectAttributes, FYUtils.fyParams("绑定佣金成功"));
        return "redirect:" + adminPath + "/store/store/storeInit.do?repage";
    }

    /**
     * 成功页面保存店铺打标
     */
    @RequiresPermissions("store:store:edit")
    @RequestMapping(value = "storeInitMarkingSave")
    public String storeInitMarkingSave(Long storeId, String markingImgPath, RedirectAttributes redirectAttributes) {
        if (storeId == null || StringUtils.isBlank(markingImgPath)) {
            addMessage(redirectAttributes, FYUtils.fyParams("打标失败"));
            return "redirect:" + adminPath + "/store/store/storeInit.do?repage";
        }
        Store store = new Store();
        store.setStoreId(storeId);
        store.setMarkingImgPath(markingImgPath);
        storeService.updateByIdSelective(store);
        addMessage(redirectAttributes, FYUtils.fyParams("打标成功"));
        return "redirect:" + adminPath + "/store/store/storeInit.do?repage";
    }

    /**
     * 验证店铺名称是否重复
     *
     * @param oldStoreName
     * @param name
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "validateStoreName")
    public String validateStoreName(String oldStoreName, String name) {
        if (StringUtils.isNotBlank(oldStoreName) && name.equals(oldStoreName)) {
            return "true";
        } else if (StringUtils.isNotBlank(name)) {
            List<Store> stores = storeService.selectByWhere(new Wrapper().and("a.name like", name));
            if (stores.isEmpty()) {
                return "true";
            } else {
                return "false";
            }
        }
        return "false";
    }

    /**
     * 表单验证
     *
     * @param store 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(Store store, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("storeId"))) {
            errorList.add(FYUtils.fyParams("店铺ID不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("storeId")) && R.get("storeId").length() > 19) {
            errorList.add(FYUtils.fyParams("店铺ID最大长度不能超过19字符"));
        }
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fyParams("店铺名称不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name"))) {
            if (R.get("name").length() > 64) {
                errorList.add(FYUtils.fyParams("店铺名称最大长度不能超过64字符"));
            }
            Wrapper wrapper = new Wrapper();
            wrapper.and("a.store_id !=", R.get("storeId"));
            wrapper.and("a.name like", R.get("name"));
            List<Store> storeList = storeService.selectByWhere(wrapper);
            if (!storeList.isEmpty()) {
                errorList.add(FYUtils.fyParams("店铺名称已存在"));
            }
        }
        if (StringUtils.isNotBlank(R.get("detailedAddress")) && R.get("detailedAddress").length() > 64) {
            errorList.add(FYUtils.fyParams("详细地址最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("industry")) && R.get("industry").length() > 255) {
            errorList.add(FYUtils.fyParams("详细地址最大长度不能超过255字符"));
        }
        if (StringUtils.isNotBlank(R.get("logo")) && R.get("logo").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺logo图片最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("banner"))) {
            errorList.add(FYUtils.fyParams("店铺横幅图片不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("banner")) && R.get("banner").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺横幅图片最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("headPic")) && R.get("headPic").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺头像图片最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("storeTel")) && R.get("storeTel").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺客服电话最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("storeQq")) && R.get("storeQq").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺QQ最大长度不能超过64字符"));
        }
        if (StringUtils.isNotBlank(R.get("storeWechat")) && R.get("storeWechat").length() > 64) {
            errorList.add(FYUtils.fyParams("店铺联系微信最大长度不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("seoTitle"))) {
            errorList.add(FYUtils.fyParams("SEO Title不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("seoTitle")) && R.get("seoTitle").length() > 255) {
            errorList.add(FYUtils.fyParams("SEO Title最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("seoTitle"))) {
            errorList.add(FYUtils.fyParams("SEO关键字不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("seoKeyword")) && R.get("seoKeyword").length() > 255) {
            errorList.add(FYUtils.fyParams("SEO关键字最大长度不能超过255字符"));
        }
        if (StringUtils.isBlank(R.get("seoTitle"))) {
            errorList.add(FYUtils.fyParams("SEO店铺描述不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("seoDescribe")) && R.get("seoDescribe").length() > 255) {
            errorList.add(FYUtils.fyParams("SEO店铺描述最大长度不能超过255字符"));
        }
        return errorList;
    }

}