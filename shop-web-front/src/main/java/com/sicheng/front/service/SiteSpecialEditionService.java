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
package com.sicheng.front.service;

import com.sicheng.admin.site.dao.SiteSpecialEditionDao;
import com.sicheng.admin.site.entity.SiteSpecialEdition;
import com.sicheng.admin.site.entity.SiteSpecialEditionDetail;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 网站特版管理 Service
 * @author 张加利
 * @version 2018-06-01
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS)
public class SiteSpecialEditionService extends CrudService<SiteSpecialEditionDao, SiteSpecialEdition> {

	@Autowired
	private SiteSpecialEditionDetailService siteSpecialEditionDetailService;
	
	/**
	 * 删除特版同时删除特版明细 
	 * @param seId
	 */
	@Transactional(rollbackFor=Exception.class)
	public void delete(Long seId) {
		super.deleteById(seId);
		SiteSpecialEditionDetail siteSpecialEditionDetail=new SiteSpecialEditionDetail();
		siteSpecialEditionDetail.setSeId(seId);
		siteSpecialEditionDetailService.deleteByWhere(new Wrapper(siteSpecialEditionDetail));
	}
	
}