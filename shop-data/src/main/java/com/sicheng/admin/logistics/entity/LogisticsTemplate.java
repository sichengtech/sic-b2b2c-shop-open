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
package com.sicheng.admin.logistics.entity;

import com.google.common.collect.Lists;
import com.sicheng.admin.logistics.dao.LogisticsTemplateItemDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 运费模板 Entity 子类，请把你的业务代码写在这里
 *
 * @author 范秀秀
 * @version 2017-02-20
 */
public class LogisticsTemplate extends LogisticsTemplateBase<LogisticsTemplate> {

    private static final long serialVersionUID = 1L;

    public LogisticsTemplate() {
        super();
    }

    public LogisticsTemplate(Long id) {
        super(id);
    }

    private List<LogisticsTemplateItem> itemList = Lists.newArrayList();

    public List<LogisticsTemplateItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<LogisticsTemplateItem> itemList) {
        this.itemList = itemList;
    }

    //一对多映射
    private List<LogisticsTemplateItem> logisticsTemplateItemList;//一条运费模板--多个运费模板详情

    public List<LogisticsTemplateItem> getLogisticsTemplateItemList() {
        if (logisticsTemplateItemList == null) {
//			LogisticsTemplateItemService service=SpringContextHolder.getBean(LogisticsTemplateItemService.class);
//			logisticsTemplateItemList= service.selectByWhere(new Wrapper().and("lt_id=",this.getLtId()));
            LogisticsTemplateItemDao dao = SpringContextHolder.getBean(LogisticsTemplateItemDao.class);
            logisticsTemplateItemList = dao.selectByWhere(null, new Wrapper().and("lt_id=", this.getLtId()));
        }
        return logisticsTemplateItemList;
    }

    public void setLogisticsTemplateItemList(List<LogisticsTemplateItem> logisticsTemplateItemList) {
        this.logisticsTemplateItemList = logisticsTemplateItemList;
    }

}