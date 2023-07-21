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
package com.sicheng.seller.interceptor;

import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.common.interceptor.MenuInterceptor;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.seller.store.service.StoreMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 菜单高亮拦截器（seller子系统专用）
 * SellerMenuInterceptor类是seller商家后台志用的单高亮拦截器，它继承自MenuInterceptor
 * <p>
 * 当URL中没有menuId885参数时，将按URL字符串来匹配。 90%
 * 当URL中有明确的menuId885参数时，会按菜单ID来处理菜单高亮。menuId885是可以为空的。10%
 *
 * @author zhaolei
 * @version 2022-06-14 11:38
 */
public class SellerMenuInterceptor extends MenuInterceptor {

    static String MENU_HIGHLIGHT_CACHE_KEY = "menu_highlight_seller_";//菜单高亮缓存key的前缀【seller子系统专用】

    @Value("${sellerPath}")
    protected String sellerPath;

    @Autowired
    StoreMenuService storeMenu;//此menuDao是管理后台的，对应查sys_menu表

    /**
     * 用于取得 菜单高亮缓存key的前缀
     * 问：为什么要写这个方法？
     * 答：本方法重写了父类的方法，从而实现子类可使用不同的 菜单高亮缓存key的前缀 的效果
     * @return 菜单高亮缓存key的前缀
     */
    @Override
    protected String getMenuHighlightCacheKey(){
        return MENU_HIGHLIGHT_CACHE_KEY;
    }

    /**
     * 根据请求的URL，计算出menuIds菜单ID串
     * 重点：
     * SellerMenuInterceptor类是seller商家后台志用的单高亮拦截器，它继承自MenuInterceptor
     * SellerMenuInterceptor子类重写了父类的findMenuIds()方法，实现从商家后台的专用菜单表查询菜单数据的目标。
     *
     *
     * @param url     请求的URL
     * @return menuIds 菜单ID串
     */
    @Override
    public String findMenuIds(String url) {
        if(StringUtils.isBlank(url)){
            return null;
        }
        //url格式：/seller/product/productSpu/save1.htm
        //要去掉前面的/seller，才能与Store_Menu表中存储的url相匹配。
        if(StringUtils.isNotBlank(sellerPath) && url.startsWith(sellerPath)){
            url=url.substring(sellerPath.length());
        }

        String menuIds = null;
        StoreMenu menu =null;
        Wrapper wrapper = new Wrapper();
        wrapper.and("href = ", url);//按URL来查询
        wrapper.and("del_flag = ", BaseEntity.DEL_FLAG_NORMAL);//非删除状态的
        List<StoreMenu> list= storeMenu.selectByWhere(wrapper);//按URL来查找menu
        if(list.size()>0){
            menu=list.get(0);
        }
        if (menu != null) {
            Long id = menu.getId();
            String parentIds = menu.getParentIds();
            // 组出菜单ID串
            // 菜单ID串：0,1,97,820,1123。永远是0,1开头，一级菜单ID是97，二级菜单ID是820，三级菜单ID是1123，
            menuIds = parentIds + id;
        }
        return menuIds;
    }


    /**
     * 手动设置菜单高亮（seller子系统专用）
     * 你传一个未级菜单的ID(一般是第三级)，自动找出二级、一级菜单的ID，并存放在Request的Attribute中，供 top.jsp\left.jsp使用。
     * 达到手动控制某一个菜单高亮的目标。
     * 90%的时候你不需要手动控制，因为MenuInterceptor类会自动控制菜单高亮的。
     * 只有10%的少数场景下，需要你手动控制，本工具就是手动控制菜单高亮的工具
     *
     * @param menuId  未级菜单ID
     */
    public static void menuHighLighting(Long menuId){
        if(menuId==null){
            return ;
        }
        //此menuDao是管理后台的，对应查sys_menu表
        StoreMenuService storeMenu= SpringContextHolder.getBean(StoreMenuService.class);
        StoreMenu menu= storeMenu.selectById(menuId);//按ID来查找menu
        if (menu != null) {
            Long id = menu.getId();
            String parentIds = menu.getParentIds();
            // 组出菜单ID串
            String menuIds = null;
            menuIds = parentIds + id;
            // 菜单ID串：0,1,97,820,1123,2145。永远是0,1开头，一级菜单ID是97，二级菜单ID是820，三级菜单ID是1123，四级菜单ID是2145
            String[] arr = menuIds.split(",");
            if (arr.length >= 3) {
                String menu1id = arr[2];//一级菜单ID
                R.getRequest().setAttribute("menu1id", menu1id);//把menu1id放入上下文供jsp页面使用
            }
            if (arr.length >= 4) {
                String menu2id = arr[3];//二级菜单ID
                R.getRequest().setAttribute("menu2id", menu2id);//把menu2id放入上下文供jsp页面使用
            }
            if (arr.length >= 5) {
                String menu3id = arr[4];//三级菜单ID
                R.getRequest().setAttribute("menu3id", menu3id);//把menu3id放入上下文供jsp页面使用
            }
            if (arr.length >= 6) {
                String menu4id = arr[5];//四级菜单ID
                R.getRequest().setAttribute("menu4id", menu4id);//把menu4id放入上下文供jsp页面使用
            }
        }
    }

    /**
     * 手动设置菜单高亮（seller子系统专用）
     * 你传一个未级菜单的menu_num(一般是第三级)，自动找出三级、二级、一级菜单的ID，并存放在Request的Attribute中，供 top.jsp\left.jsp使用。
     * 达到手动控制某一个菜单高亮的目标。
     * 90%的时候你不需要手动控制，因为MenuInterceptor类会自动控制菜单高亮的。
     * 只有10%的少数场景下，需要你手动控制，本工具就是手动控制菜单高亮的工具
     *
     * 注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
     *
     * @param menuNum  未级菜单menu_num
     */
    @Deprecated
    public static void menuHighLighting(String menuNum){
        if(StringUtils.isBlank(menuNum)){
            return ;
        }
        //此menuDao是管理后台的，对应查sys_menu表
        Wrapper wrapper=new Wrapper();
        wrapper.and("menu_num=",menuNum.trim());
        StoreMenuService storeMenu= SpringContextHolder.getBean(StoreMenuService.class);
        List<StoreMenu> menuList= storeMenu.selectByWhere(wrapper);//按menu_num来查找menu
        if(menuList.size()>0){
            StoreMenu menu= menuList.get(0);
            Long menu3id=menu.getId();//得到了菜单的ID
            menuHighLighting(menu3id);
        }
    }
}
