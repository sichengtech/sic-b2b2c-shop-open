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
package com.sicheng.admin.cms.entity;

/**
 * 站点表 Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-02-09
 */
public class Site extends SiteBase<Site> {

    private static final long serialVersionUID = 1L;

    public Site() {
        super();
    }

    public Site(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里

    /**
     * 获取默认站点ID
     */
    public static Long defaultSiteId() {
        return 1L;
    }

    /**
     * 判断是否为默认（主站）站点
     */
    public static boolean isDefault(Long id) {
        return id != null && id.equals(defaultSiteId());
    }

    /**
     * 获取当前编辑的站点编号
     */
    public static Long getCurrentSiteId() {
        //Long siteId = (Long)UserUtils2.getCache("siteId");
        Long siteId = null;
        return siteId != null ? siteId : defaultSiteId();
    }

    /**
     * 模板路径
     */
    //public static final String TPL_BASE = "/views/admin/cms/front/themes";
    public static final String TPL_BASE = "/views/front/cms/themes";

    /**
     * 获得模板方案路径。如：/views/admin/cms/front/themes/fdp
     *
     * @return
     */
    public String getSolutionPath() {
        return TPL_BASE + "/" + getTheme();
    }

}