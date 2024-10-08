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
package com.sicheng.admin.purchase.entity;

import java.util.List;

import com.sicheng.admin.purchase.dao.PurchaseSpaceDao;
import com.sicheng.admin.sso.dao.UserSellerDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 采购单视图 Entity 子类，请把你的业务代码写在这里
 * @author zjl
 * @version 2018-07-23
 */
public class ViewPurchase extends ViewPurchaseBase<ViewPurchase> {
	
	private static final long serialVersionUID = 1L;
	public ViewPurchase() {
		super();
	}

	public ViewPurchase(Long id){
		super(id);
	}
	
	//对于实体类的扩展代码，请写在这里
	
	private PurchaseSpace purchaseSpace; //采购空间
	/**
	 * @return the purchaseSpace
	 */
	public PurchaseSpace getPurchaseSpace() {
		if (purchaseSpace == null) {
		    PurchaseSpaceDao purchaseSpaceDao = SpringContextHolder.getBean(PurchaseSpaceDao.class);
		    PurchaseSpace p = new PurchaseSpace();
		    p.setSpaceId(this.getPsId());
		    p.setIsOpen("1");
		    List<PurchaseSpace> purchaseSpaceList = purchaseSpaceDao.selectByWhere(null, new Wrapper(p));
		    if(!purchaseSpaceList.isEmpty()){
		    	purchaseSpace = purchaseSpaceList.get(0);
		    }
		}
		return purchaseSpace;
	}

	/**
	 * @param purchaseSpace the purchaseSpace to set
	 */
	public void setPurchaseSpace(PurchaseSpace purchaseSpace) {
		this.purchaseSpace = purchaseSpace;
	}
	
}