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
package com.sicheng.admin.task.entity;

import java.math.BigDecimal;

/**
 * admin首页数据 Entity 子类，请把你的业务代码写在这里
 *
 * @author zjl
 * @version 2017-05-09
 */
public class TaskAdminIndex extends TaskAdminIndexBase<TaskAdminIndex> {

    private static final long serialVersionUID = 1L;

    public TaskAdminIndex() {
        super();
    }

    public TaskAdminIndex(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里
    
    /**
     * 今日交易额（去掉无用小数点0）
     */
    @Override
    public BigDecimal getMoneycountday() {
        if (super.getMoneycountday() == null) {
            return super.getMoneycountday();
        }
        String moneycountday = super.getMoneycountday().stripTrailingZeros().toPlainString();
        return new BigDecimal(moneycountday);
    }
    
    /**
     * 总交易额（去掉无用小数点0）
     */
    @Override
    public BigDecimal getOrdermoneycount() {
    	if (super.getOrdermoneycount() == null) {
    		return super.getOrdermoneycount();
    	}
    	String ordermoneycount = super.getOrdermoneycount().stripTrailingZeros().toPlainString();
    	return new BigDecimal(ordermoneycount);
    }
}