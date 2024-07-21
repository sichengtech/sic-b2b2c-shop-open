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
package com.sicheng.member.logistics.web;

import com.sicheng.admin.sys.entity.Area;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.seller.sys.service.AreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>标题: 会员中心三级联动</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年2月23日 下午2:49:08
 */
@Controller
@RequestMapping(value = "${memberPath}/sys/area")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;

    /**
     * 三级联动地区获取
     * 2017-4-18  蔡龙
     *
     * @param response
     * @param parentId
     * @param model
     * @return
     * @throws IOException
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "selectArea")
    public Object selectCity(HttpServletResponse response, String parentId, Model model) throws IOException {
        List<Area> list = areaService.selectByWhere(new Wrapper().and("parent_id=", parentId).and("del_flag=", BaseEntity.DEL_FLAG_NORMAL));
        String json = JsonMapper.getInstance().toJson(list);
        R.writeJson(response, json, "UTF-8");
        return null;
    }

}
