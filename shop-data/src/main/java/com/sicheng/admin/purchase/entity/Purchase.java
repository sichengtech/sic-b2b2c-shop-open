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
package com.sicheng.admin.purchase.entity;

import java.util.List;

import com.sicheng.admin.sso.dao.UserMainDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.store.dao.StoreEnterDao;
import com.sicheng.admin.store.entity.StoreEnter;
import com.sicheng.common.web.SpringContextHolder;

/**
 * purchase Entity 子类，请把你的业务代码写在这里
 * @author 蔡龙
 * @version 2018-06-10
 */
public class Purchase extends PurchaseBase<Purchase> {
	
	private static final long serialVersionUID = 1L;
	public Purchase() {
		super();
	}

	public Purchase(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	String companyName; //公司名
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	private UserMain userMain; //会员总表
	public UserMain getUserMain() {
		if(userMain==null){
			UserMainDao dao=SpringContextHolder.getBean(UserMainDao.class);
			userMain=dao.selectById(this.getUId());
		}
		return userMain;
	}
	public void setUserMain(UserMain userMain) {
		this.userMain = userMain;
	}
	public static void fillUserMain(List<Purchase> list){
		List<Object> ids=batchField(list,"uId");//批量调用对象的getXxx()方法
		UserMainDao dao=SpringContextHolder.getBean(UserMainDao.class);
		List<UserMain> userMainlist=dao.selectByIdIn(ids);
		fill(userMainlist,"uId",list,"uId","userMain");//循环填充
	}
	
	private StoreEnter storeEnter;	//企业认证
	public StoreEnter getStoreEnter() {
		if(storeEnter==null){
			StoreEnterDao dao=SpringContextHolder.getBean(StoreEnterDao.class);
			storeEnter=dao.selectById(this.getUId());
		}
		return storeEnter;
	}
	public void setStoreEnter(StoreEnter storeEnter) {
		this.storeEnter = storeEnter;
	}
	public static void fillStoreEnter(List<Purchase> list){
		List<Object> ids=batchField(list,"uId");//批量调用对象的getXxx()方法
		StoreEnterDao dao=SpringContextHolder.getBean(StoreEnterDao.class);
		List<StoreEnter> storeEnterlist=dao.selectByIdIn(ids);
		fill(storeEnterlist,"enterId",list,"uId","storeEnter");//循环填充
	}
	
}