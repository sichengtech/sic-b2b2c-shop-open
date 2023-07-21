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

import com.sicheng.admin.sys.entity.Area;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.sys.service.AreaService;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，AreaProvinceListFunction函数
 * </p>
 * <p>
 * 描述: 根据id获取一个省份列表,不分页
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author 张加利
 * @version 2017年5月22日 上午10:59:10
 */
public class AreaListFunction implements Function {

    public static final String PARENTID = "parentId";//省份的父id

    public List<Area> call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        Long id = TagUtils.getLong(tagParamMap, PARENTID);//省份的父id
        if (id == null) {
            return new ArrayList<>();
        }
        Area parentArea = new Area();
        parentArea.setId(id);
        Area area = new Area();
        area.setParent(parentArea);
        area.setSort(null);
        area.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        AreaService service = SpringContextHolder.getBean(AreaService.class);
        //执行业务，查询出产品分类列表
        Integer limit = TagUtils.getInteger(tagParamMap, TagUtils.LIMIT_KEY, TagUtils.LIMIT_DEFAULT);
        Page<Area> page = service.selectByWhere(new Page<Area>(1, limit, limit), new Wrapper(area).orderBy("sort"));
        return page.getList();
    }
}