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

import com.google.common.collect.Lists;
import com.sicheng.admin.cms.utils.CmsUtils2;

import java.util.Date;
import java.util.List;

/**
 * 文章栏目 Entity 子类，请把你的业务代码写在这里
 *
 * @author 蔡龙
 * @version 2017-02-14
 */
public class Category extends CategoryBase<Category> {

    private static final long serialVersionUID = 1L;

    public Category() {
        super();
//		this.setModule("");
//		this.setSort(30);
//		this.setInMenu(Global.HIDE);
//		this.setInList(Global.SHOW);
//		this.setShowModes("0");
//		this.setAllowComment(Global.NO);
//		this.setDelFlag(DEL_FLAG_NORMAL);
//		this.setIsAudit(Global.NO);
    }

    public Category(Long id) {
        super(id);
    }

    public Category(Long id, Site site) {
        this();
        this.id = id;
        this.setSite(site);
    }

    //对于实体类的扩展代码，请写在这里

    public static final String DEFAULT_TEMPLATE = "frontList";

    private Date beginDate;    // 开始时间
    private Date endDate;    // 结束时间
    private Site site;        // 归属站点
    private String cnt;//信息量
    private String hits;//点击量
    private List<Category> childList = Lists.newArrayList();    // 拥有子分类列表

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public List<Category> getChildList() {
        return childList;
    }

    public void setChildList(List<Category> childList) {
        this.childList = childList;
    }

    public static void sortList(List<Category> list, List<Category> sourcelist, Long parentId) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Category e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)) {
                list.add(e);
                // 判断是否还有子节点, 有则继续获取子节点
                for (int j = 0; j < sourcelist.size(); j++) {
                    Category child = sourcelist.get(j);
                    if (child.getParent() != null && child.getParent().getId() != null
                            && child.getParent().getId().equals(e.getId())) {
                        sortList(list, sourcelist, e.getId());
                        break;
                    }
                }
            }
        }
    }

    public String getIds() {
        return (this.getParentIds() != null ? this.getParentIds().replaceAll(",", " ") : "")
                + (this.getId() != null ? this.getId() : "");
    }

    public boolean isRoot() {
        return isRoot(this.id);
    }

    public static boolean isRoot(Long id) {
        //return id != null && id.equals("1");
        return id == 1;
    }

    public String getUrl() {
        return CmsUtils2.getUrlDynamic(this);
    }
}