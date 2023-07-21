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
package com.sicheng.front.template;

import com.sicheng.admin.cms.entity.Category;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.service.CategoryService;
import com.sicheng.front.template.util.TagUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: 获取栏目(列表,有分页)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年5月25日 下午4:21:54
 */
public class CmsChannelPageFunction implements Function {

    private static final String PARENT_ID = "parentId";//栏目父id
    private static final String IDS = "ids";//，隔开的多个id

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long parentId = TagUtils.getLong(tagParamMap, PARENT_ID);
        String ids = TagUtils.getString(tagParamMap, IDS);
        Page page = TagUtils.getPage(tagParamMap);// 从入参中取得Page分页对象
        //执行业务，查询出栏目列表
        CategoryService categoryService = SpringContextHolder.getBean(CategoryService.class);
        Category category = new Category();
        Category categoryParent = new Category();
        categoryParent.setId(parentId);
        category.setParent(categoryParent);
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(category);
        if (StringUtils.isNotBlank(ids)) {
            String[] idsArray = ids.split(",");
            List<Long> idList = new ArrayList<>();
            if (idsArray.length > 0) {
                for (int i = 0; i < idsArray.length; i++) {
                    idList.add(Long.parseLong(idsArray[i]));
                }
                wrapper.and("a.id in", idList);
            }
        }
        wrapper.and("a.in_menu = ", "1");
        wrapper.and("a.in_list = ", "1");
        wrapper.orderBy("a.sort ASC");
        page = categoryService.selectByWhere(page, wrapper);
        return page;
    }

}
