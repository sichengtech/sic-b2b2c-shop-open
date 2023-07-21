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
package com.sicheng.seller.logistics.service;

import com.sicheng.admin.logistics.dao.LogisticsTemplateDao;
import com.sicheng.admin.logistics.entity.LogisticsTemplate;
import com.sicheng.admin.logistics.entity.LogisticsTemplateItem;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.sso.utils.SsoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 运费模板 Service
 *
 * @author 范秀秀
 * @version 2017-02-20
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class LogisticsTemplateService extends CrudService<LogisticsTemplateDao, LogisticsTemplate> {

    @Autowired
    private LogisticsTemplateItemService logisticsTemplateItemService;

    /**
     * 删除物流模板
     *
     * @param logisticsTemplate 物流模板
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(LogisticsTemplate logisticsTemplate) {
        if (logisticsTemplate == null) {
            return;
        }
        //删除模板
        dao.deleteByWhere(new Wrapper(logisticsTemplate));//属主检查
        //删除模板详情
        List<LogisticsTemplate> templateList = super.selectByWhere(new Wrapper(logisticsTemplate));//属主检查
        if (!templateList.isEmpty()) {
            LogisticsTemplateItem item = new LogisticsTemplateItem();
            item.setLtId(templateList.get(0).getId());
            logisticsTemplateItemService.deleteByWhere(new Wrapper(item));
        }
    }

    /**
     * 添加或更新物流模板
     *
     * @param logisticsTemplate
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateTemplate(LogisticsTemplate logisticsTemplate) {
        if (logisticsTemplate == null) {
            return;
        }
        if (logisticsTemplate.getLtId() == null) {
            dao.insertSelective(logisticsTemplate);
        } else {
            //更新
            UserSeller userSeller = SsoUtils.getUserMain().getUserSeller();
            LogisticsTemplate condition = new LogisticsTemplate();
            condition.setId(logisticsTemplate.getId());//属主检查
            condition.setStoreId(userSeller.getStoreId());//属主检查
            int count = dao.updateByWhereSelective(logisticsTemplate, new Wrapper(condition));
            //删除原有的模板详情
            if (count == 0) {
                return;
            }
            LogisticsTemplateItem logisticsTemplateItem = new LogisticsTemplateItem();
            logisticsTemplateItem.setLtId(logisticsTemplate.getLtId());
            logisticsTemplateItemService.deleteByWhere(new Wrapper(logisticsTemplateItem));
        }
        List<LogisticsTemplateItem> itemList = logisticsTemplate.getLogisticsTemplateItemList();
        List<LogisticsTemplateItem> itemList2 = new ArrayList<>();
        if (itemList.size() == 0) {
            return;
        }
        for (LogisticsTemplateItem item : itemList) {
            item.setLtId(logisticsTemplate.getLtId());
            if (StringUtils.isBlank(item.getIds())) {
                continue;
            }
            //如果是卖家承担运费，设置运费为空
            //IsFreeShipping是否包邮，0自定义运费、1卖家承担运费
            if ("1".equals(logisticsTemplate.getIsFreeShipping())) {
                item.setFirstPrice(new BigDecimal("0"));
                item.setContinuePrice(new BigDecimal("0"));
                if (item.getFirstItem() == null) {
                    item.setFirstItem(0);
                }
                if (item.getContinueItem() == null) {
                    item.setContinueItem(0);
                }
            }
            itemList2.add(item);
        }
        //业务处理
        logisticsTemplateItemService.insertBatch(itemList2);
    }

}