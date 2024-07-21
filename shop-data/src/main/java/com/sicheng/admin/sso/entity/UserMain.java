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
package com.sicheng.admin.sso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.sso.dao.*;
import com.sicheng.admin.store.dao.StoreEnterDao;
import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.admin.store.entity.StoreRole;
import com.sicheng.common.web.SpringContextHolder;
import org.apache.solr.common.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员总表 Entity 子类，请把你的业务代码写在这里
 *
 * @author cl
 * @version 2017-04-25
 */
public class UserMain extends UserMainBase<UserMain> {

    private static final long serialVersionUID = 1L;

    public UserMain() {
        super();
    }

    public UserMain(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    private List<StoreRole> storeRoles; //商家角色

    public List<StoreRole> getStoreRoles() {
        return storeRoles;
    }

    public void setStoreRoles(List<StoreRole> storeRoles) {
        this.storeRoles = storeRoles;
    }

    private UserMember userMember;  //买家

    @JsonIgnore
    public UserMember getUserMember() {
        if (userMember == null) {
            UserMemberDao dao = SpringContextHolder.getBean(UserMemberDao.class);
            userMember = dao.selectById(this.getUId());
        }
        return userMember;
    }

    @JsonIgnore
    public void setUserMember(UserMember userMember) {
        this.userMember = userMember;
    }

    private UserSeller userSeller; //卖家

    @JsonIgnore
    public UserSeller getUserSeller() {
        if (userSeller == null) {
            UserSellerDao dao = SpringContextHolder.getBean(UserSellerDao.class);
            if ("1".equals(this.getTypeAccount())) {
                userSeller = dao.selectById(this.getUId());
            }
            if ("2".equals(this.getTypeAccount())) {
                userSeller = dao.selectById(this.getParentUid());
            }
        }
        return userSeller;
    }

    @JsonIgnore
    public void setUserSeller(UserSeller userSeller) {
        this.userSeller = userSeller;
    }

    private UserRepairShop useRepairShop;  //门店

    @JsonIgnore
    public UserRepairShop getUseRepairShop() {
        if (useRepairShop == null) {
            UserRepairShopDao dao = SpringContextHolder.getBean(UserRepairShopDao.class);
            useRepairShop = dao.selectById(this.getUId());
        }
        return useRepairShop;
    }

    @JsonIgnore
    public void setUseRepairShop(UserRepairShop useRepairShop) {
        this.useRepairShop = useRepairShop;
    }

    private UserPurchase usePurchase;  //采购商

    @JsonIgnore
    public UserPurchase getUsePurchase() {
        if (usePurchase == null) {
            UserPurchaseDao dao = SpringContextHolder.getBean(UserPurchaseDao.class);
            usePurchase = dao.selectById(this.getUId());
        }
        return usePurchase;
    }

    @JsonIgnore
    public void setUsePurchase(UserPurchase usePurchase) {
        this.usePurchase = usePurchase;
    }

    private StoreEnter storeEnter;  //入驻信息

    @JsonIgnore
    public StoreEnter getStoreEnter() {
        if (storeEnter == null) {
            StoreEnterDao dao = SpringContextHolder.getBean(StoreEnterDao.class);
            storeEnter = dao.selectById(this.getUId());
        }
        return storeEnter;
    }

    @JsonIgnore
    public void setStoreEnter(StoreEnter storeEnter) {
        this.storeEnter = storeEnter;
    }

    /**
     * 判断会员账号是否被激活（可通过验证邮箱激活，或手机短信激活）
     * 激活return true
     * 未激活return false
     */
    public boolean isOperation() {
        boolean flag = false;
        if ("1".equals(this.getEmailValidate()) || "1".equals(this.getMobileValidate())) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断用户类型是否是：会员--个人买家
     *
     * @return
     */
    public boolean isTypeUserMember() {
        return isTypeUser(1);
    }

    /**
     * 判断用户类型是否是：会员--采购商
     *
     * @return
     */
    public boolean isTypeUserPurchaser() {
        return isTypeUser(2);
    }

    /**
     * 判断用户类型是否是：会员--门店服务商
     *
     * @return
     */
    public boolean isTypeUserService() {
        return isTypeUser(3);
    }

    /**
     * 判断用户类型是否是：会员--卖家、商家
     *
     * @return
     */
    public boolean isTypeUserSeller() {
        return isTypeUser(4);
    }

    /**
     * 判断typeUser的第N位是否为1
     * <p>
     * 用户类型如何存储
     * 用户类型（个人买家、采购商、门店服务商、卖家）
     * 10位编码，可容纳10种用户类型，某位为1表示是某种用户类型
     * (从右向左数，最右侧的一位是第1个)
     * <p>
     * 第1位为1表示：个人买家
     * 第2位为1表示：采购商
     * 第3位为1表示：门店服务商
     * 第4位为1表示：卖家
     * 第5位为1表示：预留
     * 第5位为1表示：预留
     * <p>
     * 示例：
     * 0000000001 个人买家
     * 0000000010 采购商
     * 0000000100 门店服务商
     * 0000001000 卖家
     * 0000001110 采购商、门店服务商、卖家（多种类型）
     *
     * @param index 第几位，从1开始，1表示第一位
     * @return
     */
    public boolean isTypeUser(int index) {
        if (index < 1) {
            return false;
        }
        String typeUser = getTypeUserDefault();
        String[] arr = typeUser.split("");
        reverse(arr);//实现 数组翻转
        if (arr.length >= index) {
            String p = arr[index - 1];
            return "1".equalsIgnoreCase(p);
        }
        return false;
    }

    /**
     * 添加用户类型是：会员--个人买家
     * 可多次添加，最终结果是一致的
     *
     * @return
     */
    public void addTypeUserMember() {
        addTypeUser(1);
    }

    /**
     * 添加用户类型是：会员--采购商
     * 可多次添加，最终结果是一致的
     *
     * @return
     */
    public void addTypeUserPurchaser() {
        addTypeUser(2);
    }

    /**
     * 添加用户类型是：会员--门店服务商
     * 可多次添加，最终结果是一致的
     *
     * @return
     */
    public void addTypeUserService() {
        addTypeUser(3);
    }

    /**
     * 添加用户类型是：会员--卖家、商家
     * 可多次添加，最终结果是一致的
     *
     * @return
     */
    public void addTypeUserSeller() {
        addTypeUser(4);
    }

    /**
     * 给用户添加一种“会员类型”
     * 修改typeUser的第N位的值为1
     * <p>
     * 用户类型如何存储
     * 用户类型（个人买家、采购商、门店服务商、卖家）
     * 10位编码，可容纳10种用户类型，某位为1表示是某种用户类型
     * (从右向左数，最右侧的一位是第1个)
     * <p>
     * 第1位为1表示：个人买家
     * 第2位为1表示：采购商
     * 第3位为1表示：门店服务商
     * 第4位为1表示：卖家
     * 第5位为1表示：预留
     * 第5位为1表示：预留
     * <p>
     * 示例：
     * 0000000001 个人买家
     * 0000000010 采购商
     * 0000000100 门店服务商
     * 0000001000 卖家
     * 0000001110 采购商、门店服务商、卖家（多种类型）
     *
     * @param index 第几位，从1开始，1表示第一位
     * @return
     */
    public void addTypeUser(int index) {
        modifyTypeUser(index, true);
    }

    /**
     * 减少用户类型是：会员--个人买家
     * 可多次减少，最终结果是一致的
     *
     * @return
     */
    public void reduceTypeUserMember() {
        reduceTypeUser(1);
    }

    /**
     * 减少用户类型是：会员--采购商
     * 可多次减少，最终结果是一致的
     *
     * @return
     */
    public void reduceTypeUserPurchaser() {
        reduceTypeUser(2);
    }

    /**
     * 减少用户类型是：会员--门店服务商
     * 可多次减少，最终结果是一致的
     *
     * @return
     */
    public void reduceTypeUserService() {
        reduceTypeUser(3);
    }

    /**
     * 减少用户类型是：会员--卖家、商家
     * 可多次减少，最终结果是一致的
     *
     * @return
     */
    public void reduceTypeUserSeller() {
        reduceTypeUser(4);
    }

    /**
     * 给用户减少一种“会员类型”
     * 修改typeUser的第N位的值为1
     *
     * @param index 第几位，从1开始，1表示第一位
     */
    public void reduceTypeUser(int index) {
        modifyTypeUser(index, false);
    }

    /**
     * @param index 第几位（从右侧开始数）
     * @param add   是否是加，true加，false减
     */
    public void modifyTypeUser(int index, boolean add) {
        if (index < 1) {
            return;
        }
        String typeUser = getTypeUserDefault();
        String[] arr = typeUser.split("");
        reverse(arr);//实现 数组翻转
        if (arr.length >= index) {
            if (add) {
                arr[index - 1] = "1";
            } else {
                arr[index - 1] = "0";
            }
        }
        reverse(arr);//实现 数组翻转
        StringBuilder sbl = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sbl.append(arr[i]);
        }
        setTypeUser(sbl.toString());
    }

    //实现 数组翻转
    private void reverse(String[] arr) {
        ArrayList<String> array_list = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            array_list.add(arr[arr.length - i - 1]);
        }
        arr = array_list.toArray(arr);
    }

    /**
     * getter typeUser(用户类型（个人买家、采购商、门店服务商、卖家）)
     * 覆盖了父类的方法，增加了：当typeUser为空时，返回默认值0000000000
     */
    public String getTypeUserDefault() {
        String typeUser = super.getTypeUser();
        if (StringUtils.isEmpty(typeUser)) {
            return "0000000000";
        } else {
            return typeUser;
        }

    }
}