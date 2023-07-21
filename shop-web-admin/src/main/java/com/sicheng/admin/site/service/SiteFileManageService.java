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
package com.sicheng.admin.site.service;

import com.sicheng.admin.site.dao.SiteFileManageDao;
import com.sicheng.admin.site.entity.SiteFileManage;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理器 Service
 * @author 范秀秀
 * @version 2018-06-01
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS)
public class SiteFileManageService extends CrudService<SiteFileManageDao, SiteFileManage> {

	@Transactional(rollbackFor=Exception.class)
	public void editFile(SiteFileManage siteFileManage, String[] name, String[] path){
		//先删除文件，在添加文件
		dao.deleteById(siteFileManage.getSfId());
		List<SiteFileManage> siteFileManageList=new ArrayList<>();
		for(int i=0;i<path.length;i++){
			if(StringUtils.isBlank(path[i]) || StringUtils.isBlank(name[i])){
				continue;
			}
			SiteFileManage file=new SiteFileManage();
			file.setCategory(siteFileManage.getCategory());
			file.setPath(path[i]);
			file.setName(name[i]);
			file.setRemarks(siteFileManage.getRemarks());
			siteFileManageList.add(file);
		}
		super.insertBatch(siteFileManageList);
	}
	
}