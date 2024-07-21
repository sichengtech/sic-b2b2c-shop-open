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
package com.sicheng.front.template;

import java.util.List;
import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;

import com.sicheng.admin.sys.entity.Dict;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.sys.service.DictService;

/**
 * <p>标题: 获取字典值</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年5月18日 下午6:54:52
 */
public class DictValueFunction implements Function {

    private static final String LABEL = "label";//键
    private static final String TYPE = "type";//字典类型

    public Object call(Object[] args, Context ctx) {
        // 处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        String label = TagUtils.getString(tagParamMap, LABEL);
        String type = TagUtils.getString(tagParamMap, TYPE);
        if (StringUtils.isBlank(label) || StringUtils.isBlank(type)) {
            return null;
        }
        //执行业务，查询出字典值
        DictService dictService = SpringContextHolder.getBean(DictService.class);
        Dict dict = new Dict();
        dict.setLabel(label);
        dict.setType(type);
        List<Dict> dictList = dictService.selectByWhere(new Wrapper(dict));
        if (dictList.isEmpty()) {
            return null;
        } else {
            return dictList.get(0);
        }
    }

}
