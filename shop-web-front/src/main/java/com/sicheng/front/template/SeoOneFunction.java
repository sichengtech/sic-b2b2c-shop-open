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

import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.site.entity.SiteSeo;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.front.template.util.TagUtils;
import com.sicheng.seller.product.service.ProductCategoryService;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.site.service.SiteInfoService;
import com.sicheng.seller.site.service.SiteSeoService;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <p>
 * 标题: 自定义函数，SeoOneFunction函数
 * </p>
 * <p>
 * 描述: 根据业务类型获取seo设置
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author zjl
 * @version 2017年5月22日 上午10:59:10
 */
public class SeoOneFunction implements Function {

    public static final String TYPE = "type";
    public static final String[] VARIABLES = {"${siteName}", "${categoryName}", "${productName}", "${storeName}", "${searchKey}"};
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public SiteSeo call(Object[] args, Context ctx) {
        //处理标签的入参数，json参数
        Map<String, Object> tagParamMap = TagUtils.getTagParamMap(args);
        String type = TagUtils.getString(tagParamMap, TYPE);
        if (type == null) {
            return null;
        }
        SiteSeoService siteSeoService = SpringContextHolder.getBean(SiteSeoService.class);
        //执行业务，查询seo设置
        SiteSeo siteSeo = siteSeoService.selectOne(new Wrapper().and("a.type=", type));
        String cid = (String) ctx.globalVar.get("cid");//分类id
        String pid = (String) ctx.globalVar.get("pid");//商品id
        String searchKey = R.get("k");//搜索的关键字
        if (siteSeo != null) {
            siteSeo.setTitle(getReplaceSeoContent(siteSeo.getTitle(), cid, pid, searchKey));
            siteSeo.setKeywords(getReplaceSeoContent(siteSeo.getKeywords(), cid, pid, searchKey));
            siteSeo.setDescription(getReplaceSeoContent(siteSeo.getDescription(), cid, pid, searchKey));
        }
        return siteSeo;
    }

    /**
     *  将seo设置的title的变量替换成具体的内容,并获得新的title
     *  @param seoContent seo设置中要被替换的内容
     *  @param cid 分类id
     *  @param pid 商品id
     *  @param searchKey 搜索的关键字
     *  @return 替换变量名后新的seo设置的内容
     */
    private String getReplaceSeoContent(String seoContent, String cid, String pid, String searchKey) {
        if (StringUtils.isBlank(seoContent)) {
            return seoContent;
        }
        if (seoContent.indexOf(VARIABLES[0]) != -1) {
            SiteInfoService siteInfoService = SpringContextHolder.getBean(SiteInfoService.class);
            SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
            //把text中的searchString替换成replacement,max是最大替换次数，默认是替换所有
            seoContent = StringUtils.replace(seoContent, VARIABLES[0], siteInfo.getName());
        }
        if (seoContent.indexOf(VARIABLES[1]) != -1) {
            if (StringUtils.isNotBlank(cid)) {
                ProductCategoryService productCategoryService = SpringContextHolder.getBean(ProductCategoryService.class);
                ProductCategory productCategory = productCategoryService.selectById(Long.valueOf(cid));
                //把text中的searchString替换成replacement,max是最大替换次数，默认是替换所有
                if (productCategory == null) {
                    seoContent = StringUtils.replace(seoContent, VARIABLES[1], "本分类下的商品");
                } else {
                    seoContent = StringUtils.replace(seoContent, VARIABLES[1], productCategory.getName());
                }
            }
        }
        if (seoContent.indexOf(VARIABLES[2]) != -1) {
            if (StringUtils.isNotBlank(pid)) {
                ProductSpuService productSpuService = SpringContextHolder.getBean(ProductSpuService.class);
                ProductSpu productSpu = productSpuService.selectById(Long.valueOf(pid));
                //把text中的searchString替换成replacement,max是最大替换次数，默认是替换所有
                if (productSpu == null) {
                    seoContent = StringUtils.replace(seoContent, VARIABLES[2], "商品详情");
                } else {
                    seoContent = StringUtils.replace(seoContent, VARIABLES[2], productSpu.getName());
                }
            }
        }
        if (seoContent.indexOf(VARIABLES[3]) != -1) {
            if (StringUtils.isNotBlank(pid)) {
                ProductSpuService productSpuService = SpringContextHolder.getBean(ProductSpuService.class);
                ProductSpu productSpu = productSpuService.selectById(Long.valueOf(pid));
                //把text中的searchString替换成replacement,max是最大替换次数，默认是替换所有
                if (productSpu == null) {
                    seoContent = StringUtils.replace(seoContent, VARIABLES[3], "店铺名");
                } else {
                    seoContent = StringUtils.replace(seoContent, VARIABLES[3], productSpu.getStore().getName());
                }
            }
        }
        if (seoContent.indexOf(VARIABLES[4]) != -1) {
            if (StringUtils.isBlank(searchKey)) {
                //把text中的searchString替换成replacement,max是最大替换次数，默认是替换所有
                seoContent = StringUtils.replace(seoContent, VARIABLES[4], "搜索列表");
            } else {
                seoContent = StringUtils.replace(seoContent, VARIABLES[4], searchKey);
            }
        }
        return seoContent;
    }
}