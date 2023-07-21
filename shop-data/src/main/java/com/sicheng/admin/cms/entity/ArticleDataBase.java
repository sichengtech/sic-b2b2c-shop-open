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


import com.sicheng.common.persistence.DataEntity;

/**
 * 文章详情表 Entity 父类
 *
 * @author 蔡龙
 * @version 2017-02-09
 */
public class ArticleDataBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private String content;                 // 文章内容
    private String copyfrom;                // 文章来源
    private String relation;                // 相关文章
    private String allowComment;            // 是否允许评论

    public ArticleDataBase() {
        super();
    }

    public ArticleDataBase(Long id) {
        super(id);
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * 描述: 设置ID
     *
     * @param id
     * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter content(文章内容)
     */
    public String getContent() {
        return content;
    }

    /**
     * setter content(文章内容)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * getter copyfrom(文章来源)
     */
    public String getCopyfrom() {
        return copyfrom;
    }

    /**
     * setter copyfrom(文章来源)
     */
    public void setCopyfrom(String copyfrom) {
        this.copyfrom = copyfrom;
    }

    /**
     * getter relation(相关文章)
     */
    public String getRelation() {
        return relation;
    }

    /**
     * setter relation(相关文章)
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     * getter allowComment(是否允许评论)
     */
    public String getAllowComment() {
        return allowComment;
    }

    /**
     * setter allowComment(是否允许评论)
     */
    public void setAllowComment(String allowComment) {
        this.allowComment = allowComment;
    }


}