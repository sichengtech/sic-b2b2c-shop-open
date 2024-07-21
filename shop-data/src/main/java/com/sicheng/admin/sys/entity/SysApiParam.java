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
package com.sicheng.admin.sys.entity;

import com.sicheng.admin.sys.dao.DictDao;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.web.SpringContextHolder;

import java.util.List;

/**
 * 接口参数 Entity 子类，请把你的业务代码写在这里
 *
 * @author fxx
 * @version 2018-02-28
 */
public class SysApiParam extends SysApiParamBase<SysApiParam> {

    private static final long serialVersionUID = 1L;

    public SysApiParam() {
        super();
    }

    public SysApiParam(Long id) {
        super(id);
    }

    //参数类型字典标签，一对一映射
    String paramTypeLabel;

    public String getParamTypeLabel() {
        if (paramTypeLabel == null) {
            DictDao dictDao = SpringContextHolder.getBean(DictDao.class);
            Dict dict = new Dict();
            dict.setType("sys_api_param_type");
            dict.setValue(this.getParamType());
            List<Dict> dictList = dictDao.selectByWhere(null, new Wrapper(dict));
            if (dictList != null && !dictList.isEmpty()) {
                paramTypeLabel = dictList.get(0).getLabel();
            }
        }
        return paramTypeLabel;
    }

    public void setParamTypeLabel(String paramTypeLabel) {
        this.paramTypeLabel = paramTypeLabel;
    }

    //是否必填字典标签，一对一映射
    String isRequiredLabel;

    public String getIsRequiredLabel() {
        if (isRequiredLabel == null) {
            DictDao dictDao = SpringContextHolder.getBean(DictDao.class);
            Dict dict = new Dict();
            dict.setType("yes_no");
            dict.setValue(this.getIsRequired());
            List<Dict> dictList = dictDao.selectByWhere(null, new Wrapper(dict));
            if (dictList != null && !dictList.isEmpty()) {
                isRequiredLabel = dictList.get(0).getLabel();
            }
        }
        return isRequiredLabel;
    }

    public void setIsRequiredLabel(String isRequiredLabel) {
        this.isRequiredLabel = isRequiredLabel;
    }


}