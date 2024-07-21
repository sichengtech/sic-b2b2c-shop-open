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
package com.sicheng.admin.settlement.entity;

import com.sicheng.admin.settlement.dao.SettlementPayWayAttrDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 支付方式 Entity 子类，请把你的业务代码写在这里
 *
 * @author fxx
 * @version 2017-02-06
 */
public class SettlementPayWay extends SettlementPayWayBase<SettlementPayWay> {

    private static final long serialVersionUID = 1L;

    public SettlementPayWay() {
        super();
    }

    public SettlementPayWay(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    //一对多映射
    private List<SettlementPayWayAttr> payWayAttrList;//一个支付方式--多个支付方式属性

    public List<SettlementPayWayAttr> getPayWayAttrList() {
        if (payWayAttrList == null) {
            SettlementPayWayAttrDao dao = SpringContextHolder.getBean(SettlementPayWayAttrDao.class);
            payWayAttrList = dao.selectByWhere(null, new Wrapper().and("pay_way_id=", this.getPayWayId()).orderBy("pay_way_attr_id"));
        }
        return payWayAttrList;
    }

    public void setPayWayAttrList(List<SettlementPayWayAttr> payWayAttrList) {
        this.payWayAttrList = payWayAttrList;
    }
    
    /**
	 * 手续费
	 */
	@Override
	public BigDecimal getPoundage() {
		if(super.getPoundage()==null){
			return super.getPoundage();
		}
		String poundage=super.getPoundage().stripTrailingZeros().toPlainString();
		return new BigDecimal(poundage);
	}

}