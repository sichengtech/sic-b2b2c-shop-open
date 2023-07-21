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
package com.sicheng.admin.site.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.admin.product.dao.ProductCategoryDao;
import com.sicheng.admin.product.dao.ProductSpuDao;
import com.sicheng.admin.product.entity.ProductCategory;
import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;

/**
 * 推荐位详情 Entity 子类，请把你的业务代码写在这里
 *
 * @author fanxiuxiu
 * @version 2017-09-25
 */
public class SiteRecommendItem extends SiteRecommendItemBase<SiteRecommendItem> {

    private static final long serialVersionUID = 1L;

    public SiteRecommendItem() {
        super();
    }

    public SiteRecommendItem(Long id) {
        super(id);
    }

    private String url;////链接地址(pc端)

    public String getUrl() {
        // 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        if (StringUtils.isBlank(this.getOperationType()) || StringUtils.isBlank(this.getOperationContent())) {
            return url;
        }
        if ("1".equals(this.getOperationType())) {
            url = "";
            return url;
        }
        if ("2".equals(this.getOperationType())) {
            url = this.getOperationContent();
            return url;
        }
        if ("3".equals(this.getOperationType())) {
            url = R.getRequest().getContextPath() + "/product/list.htm?k=" + this.getOperationContent();
            return url;
        }
        if ("4".equals(this.getOperationType())) {
            url = R.getRequest().getContextPath() + "/detail/" + this.getOperationContent() + ".htm";
            return url;
        }
        if ("5".equals(this.getOperationType())) {
            R.getRequest().getContextPath();
            url = R.getRequest().getContextPath() + "/store/" + this.getOperationContent() + ".htm";
            return url;
        }
        if ("6".equals(this.getOperationType())) {
            if (!StringUtils.isNumeric(this.getOperationContent())) {
                return url;
            }
            ProductCategoryDao productCategoryDao = SpringContextHolder.getBean(ProductCategoryDao.class);
            ProductCategory productCategory = productCategoryDao.selectById(Long.parseLong(this.getOperationContent()));
            if (productCategory == null || productCategory.getCateLevel() == null) {
                return url;
            }
            if (1 == productCategory.getCateLevel()) {
                url = R.getRequest().getContextPath() + "/categoryOne/" + this.getOperationContent() + ".htm";
            }
            if (2 == productCategory.getCateLevel()) {
                url = R.getRequest().getContextPath() + "/categoryTwo/" + this.getOperationContent() + ".htm";
            }
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String weixinUrl;////链接地址(微信端)

    public String getWeixinUrl() {
        // 操作类型(1无操作、2链接地址、3关键字、4商品编号、5店铺编号、6商品分类)
        if (StringUtils.isBlank(this.getOperationType()) || StringUtils.isBlank(this.getOperationContent())) {
            return weixinUrl;
        }
        if ("1".equals(this.getOperationType())) {
            weixinUrl = "";
            return weixinUrl;
        }
        if ("2".equals(this.getOperationType())) {
            weixinUrl = this.getOperationContent();
            return weixinUrl;
        }
        if ("3".equals(this.getOperationType())) {
            weixinUrl = R.getRequest().getContextPath() + "/product/list.htm?k=" + this.getOperationContent();
            return weixinUrl;
        }
        if ("4".equals(this.getOperationType())) {
            weixinUrl = R.getRequest().getContextPath() + "/product/detail.htm?pid=" + this.getOperationContent();
            return weixinUrl;
        }
        if ("5".equals(this.getOperationType())) {
            R.getRequest().getContextPath();
            weixinUrl = R.getRequest().getContextPath() + "/store/index.htm?storeId=" + this.getOperationContent();
            return weixinUrl;
        }
        if ("6".equals(this.getOperationType())) {
            if (!StringUtils.isNumeric(this.getOperationContent())) {
                return weixinUrl;
            }
            weixinUrl = R.getRequest().getContextPath() + "/product/list.htm?cid=" + this.getOperationContent();
            return weixinUrl;
        }
        return weixinUrl;
    }

    public void setWeixinUrl(String weixinUrl) {
        this.weixinUrl = weixinUrl;
    }

    //对于实体类的扩展代码，请写在这里
    //一对一映射
    @JsonIgnore
    private ProductSpu productSpu;

    public ProductSpu getProductSpu() {
        if (productSpu == null && this.getPId() != null) {
            ProductSpuDao productSpuDao = SpringContextHolder.getBean(ProductSpuDao.class);
            productSpu = productSpuDao.selectById(this.getPId());
        }
        return productSpu;
    }

    public void setProductSpu(ProductSpu productSpu) {
        this.productSpu = productSpu;
    }

}