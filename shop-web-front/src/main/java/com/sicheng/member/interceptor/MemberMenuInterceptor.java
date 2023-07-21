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
package com.sicheng.member.interceptor;

import com.sicheng.common.interceptor.MenuInterceptor;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单高亮拦截器（member子系统专用）
 * MemberMenuInterceptor类是member商家后台志用的单高亮拦截器，它继承自MenuInterceptor
 * <p>
 * 当URL中没有menuId885参数时，将按URL字符串来匹配。 90%
 * 当URL中有明确的menuId885参数时，会按菜单ID来处理菜单高亮。menuId885是可以为空的。10%
 *
 * @author zhaolei
 * @version 2022-06-20 11:38
 */
public class MemberMenuInterceptor extends MenuInterceptor {

    static String MENU_HIGHLIGHT_CACHE_KEY = "menu_highlight_member_";//菜单高亮缓存key的前缀【member子系统专用】

    @Value("${memberPath}")
    protected String memberPath;

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
     * MemberMenuInterceptor类是member商家后台志用的单高亮拦截器，它继承自MenuInterceptor
     * MemberMenuInterceptor子类重写了父类的findMenuIds()方法，实现从商家后台的专用菜单表查询菜单数据的目标。
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
        url=url.trim();
        //url格式：/member/product/productSpu/save1.htm
        //要去掉前面的/member，才能与Store_Menu表中存储的url相匹配。
        if(StringUtils.isNotBlank(memberPath) && url.startsWith(memberPath)){
            url=url.substring(memberPath.length());
        }

        String menu1id=null;
        String menu3id=null;
        for(Menu menu:list){
            if(url.equals(menu.url)){
                //找到了
                if(menu.level==1){
                    menu1id=menu.id;
                }else if(menu.level==3){
                    menu3id=menu.id;
                    menu1id=menu.parent.id;
                }else{
                    //不会走到这里
                }
            }
        }

        // 菜单ID串：0,1,97,820,1123,2145。永远是0,1开头，一级菜单ID是97，二级菜单ID是820，三级菜单ID是1123，四级菜单ID是2145
        StringBuilder sbl=new StringBuilder();
        sbl.append("0,1,");
        if(StringUtils.isNotBlank(menu1id)){
            sbl.append(menu1id);
            sbl.append(",");
        }
        sbl.append(0);
        sbl.append(",");
        if(StringUtils.isNotBlank(menu3id)){
            sbl.append(menu3id);
            sbl.append(",");
        }
        return sbl.toString();
    }

    /**
     * 内部类，本类之内专用，所以就定义在内部
     */
    static private class Menu{
        public String id;
        public String url;
        public int level;
        public Menu parent;
        public Menu(String url,String id,int level){
            this.url=url;
            this.id=id;
            this.level=level;
        }
        public Menu(String url,String id,int level,Menu parent){
            this.url=url;
            this.id=id;
            this.level=level;
            this.parent=parent;
        }
    }

    /**
     * 静态化的member菜单数据，todo 以后要做到数据表中
     */
    static List<Menu> list=new ArrayList<>();
    static{
        //一级菜单
        Menu a=new Menu("/index.htm","index",1);//一级--首页
        Menu b=new Menu("/trade/tradeOrder/list.htm","trade",1);//一级--交易中心
        Menu c=new Menu("/collect/memberCollectionProduct/list.htm","collection",1);//一级--收藏中心
        Menu d=new Menu("/trade/tradeReturnOrder/tradeReturnOrderList.htm","userSevice",1);//一级--客户服务
        Menu e=new Menu("/user/userMember/save1.htm","userInfo",1);//一级--会员资料
        Menu f=new Menu("/finance/financePreDeposit/list.htm","finance",1);//一级--财产中心
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.add(f);
        //交易中心的下级菜单
        list.add(new Menu("/trade/tradeOrder/list.htm","tradrOrder",3, b));//3级--交易中心--商品订单
        list.add(new Menu("/trade/tradeComment/list.htm","tradeComment",3, b));//3级--交易中心--交易评价
        list.add(new Menu("/trade/cart/list.htm","tradeCart",3, b));//3级--交易中心--购物车
        //收藏中心的下级菜单
        list.add(new Menu("/collect/memberCollectionProduct/list.htm","collectionProduct",3, c));//收藏中心--商品收藏
        list.add(new Menu("/collect/memberCollectionStore/list.htm","collectionStore",3, c));//收藏中心--店铺收藏
        //客户服务的下级菜单
        list.add(new Menu("/trade/tradeReturnOrder/tradeReturnOrderList.htm","returnOrder",3, d));//客户服务--退款及退货
        list.add(new Menu("/trade/tradeComplaint/list.htm","complaint",3, d));//客户服务--交易投诉
        list.add(new Menu("/trade/consultation/list.htm","consultation",3, d));//客户服务--商品咨询
        //会员资料的下级菜单
        list.add(new Menu("/user/userMember/save1.htm","userMember",3, e));//会员资料--账户信息
        list.add(new Menu("${ctxs}/index.htm","",3, e));//会员资料--账户安全 ，跳转到${ctxs}了不需要菜单高亮
        list.add(new Menu("/user/memberAddress/list.htm","userAddress",3, e));//会员资料--收货地址
        list.add(new Menu("/user/memberMessage/list.htm","userMessage",3, e));//会员资料--我的消息
        //财务中心的下级菜单
        list.add(new Menu("/finance/financePreDeposit/list.htm","preDeposit",3, f));//财务中心--账户余额
        list.add(new Menu("/finance/financeRecharge/save1.htm","recharge",3, f));//财务中心--账户充值
        list.add(new Menu("/finance/financeWithdrawals/save1.htm","",3, f));//财务中心--账户提现
//        //用户升级的下级菜单
//        list.add(new Menu("${ctxs}/store/storeEnterAuth/auth4.htm","",3));//用户升级--升级为供应商，跳转到${ctxs}了不需要菜单高亮
//        list.add(new Menu("/upgrade/openDoorStore/save1.htm","",3));//用户升级--升级为服务门店 -- 菜单注释了用不上也放在这里
//        list.add(new Menu("/upgrade/upgradePurchaser/save1.htm","purchaser",3));//用户升级--升级为采购商 -- 菜单注释了用不上也放在这里
//        list.add(new Menu("/upgrade/carOwner/save1.htm","carOwner",3));//用户升级--完善车主信息 -- 菜单注释了用不上也放在这里
    }

//    /**
//     * 手动设置菜单高亮（member子系统专用）
//     * 你传一个未级菜单的ID(一般是第三级)，自动找出二级、一级菜单的ID，并存放在Request的Attribute中，供 top.jsp\left.jsp使用。
//     * 达到手动控制某一个菜单高亮的目标。
//     * 90%的时候你不需要手动控制，因为MenuInterceptor类会自动控制菜单高亮的。
//     * 只有10%的少数场景下，需要你手动控制，本工具就是手动控制菜单高亮的工具
//     *
//     * @param menuId  未级菜单ID
//     */
//    public static void menuHighLighting(Long menuId){
//        if(menuId==null){
//            return ;
//        }
//        //此menuDao是管理后台的，对应查sys_menu表
//        StoreMenuService storeMenu= SpringContextHolder.getBean(StoreMenuService.class);
//        StoreMenu menu= storeMenu.selectById(menuId);//按ID来查找menu
//        if (menu != null) {
//            Long id = menu.getId();
//            String parentIds = menu.getParentIds();
//            // 组出菜单ID串
//            String menuIds = null;
//            menuIds = parentIds + id;
//            // 菜单ID串：0,1,97,820,1123,2145。永远是0,1开头，一级菜单ID是97，二级菜单ID是820，三级菜单ID是1123，四级菜单ID是2145
//            String[] arr = menuIds.split(",");
//            if (arr.length >= 3) {
//                String menu1id = arr[2];//一级菜单ID
//                R.getRequest().setAttribute("menu1id", menu1id);//把menu1id放入上下文供jsp页面使用
//            }
//            if (arr.length >= 4) {
//                String menu2id = arr[3];//二级菜单ID
//                R.getRequest().setAttribute("menu2id", menu2id);//把menu2id放入上下文供jsp页面使用
//            }
//            if (arr.length >= 5) {
//                String menu3id = arr[4];//三级菜单ID
//                R.getRequest().setAttribute("menu3id", menu3id);//把menu3id放入上下文供jsp页面使用
//            }
//            if (arr.length >= 6) {
//                String menu4id = arr[5];//四级菜单ID
//                R.getRequest().setAttribute("menu4id", menu4id);//把menu4id放入上下文供jsp页面使用
//            }
//        }
//    }

    /**
     * 手动设置菜单高亮（member子系统专用）
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
        menuNum=menuNum.trim();
        String menu1id_1=null;
        String menu3id_3=null;
        for(Menu menu:list){
            if(menuNum.equals(menu.id)){
                //找到了
                if(menu.level==1){
                    menu1id_1=menu.id;
                }else if(menu.level==3){
                    menu3id_3=menu.id.trim();
                    menu1id_1=menu.parent.id.trim();
                }else{
                    //不会走到这里
                }
            }
        }

        // 菜单ID串：0,1,97,820,1123,2145。永远是0,1开头，一级菜单ID是97，二级菜单ID是820，三级菜单ID是1123，四级菜单ID是2145
        StringBuilder sbl=new StringBuilder();
        sbl.append("0,1,");
        if(StringUtils.isNotBlank(menu1id_1)){
            sbl.append(menu1id_1);
            sbl.append(",");
        }
        sbl.append(0);
        sbl.append(",");
        if(StringUtils.isNotBlank(menu3id_3)){
            sbl.append(menu3id_3);
            sbl.append(",");
        }

        // 组出菜单ID串
        String menuIds = sbl.toString();
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
