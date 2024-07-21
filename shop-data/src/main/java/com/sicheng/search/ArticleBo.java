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
package com.sicheng.search;

import java.util.Date;

/**
 * ArticleBo表示一篇文章
 * @author zhaolei
 */
public class ArticleBo {
	private Long id;//文章ID
	private String title;// 原始的title
	private String title2;// 给加了高亮后的title
	private String description;// 原始的body文章内容
	private String description2;// 加了高亮后的body文章内容
	private String author; //作者
	private Date releaseDate;//发布时间
	private String origin;//来源
	private String keywords;//关键字
	private String image;// 封面图片的URL
	private Float score;// 评分
	private String[] tags; // tag列表

	public ArticleBo() {

	}

	public String toString() {
		StringBuilder sbl = new StringBuilder();
		sbl.append("id:" + id);
		sbl.append(",score:" + score);
		sbl.append(",title:" + title);
		sbl.append(",author:" + author);
		sbl.append(",origin:" + origin);
		sbl.append(",keywords:" + keywords);
		sbl.append(",releaseDate:" + releaseDate);
		sbl.append(",image:" + image);
		String b = null;
		if (description != null) {
			if (description.length() > 30) {
				b = description.substring(0, 30);
			}
		}
		sbl.append(",description:" + b);
		sbl.append(",title2:" + title2);
		sbl.append(",description2:" + description2);
		return sbl.toString();
	}

	// 下面的get方法是扩展出来的，保持与Content对像方法签名相同，便于在模板中调用
	public String getShortTitle() {
		return getTitle();
	}

	public Date getDate() {
		return getReleaseDate();
	}

	// 下面的get方法是原生的
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	/**
	 * 有高亮，加了高亮后的Title
	 * @return
	 */
	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 有高亮，加了高亮后的body文章内容
	 *
	 * @return
	 */
	public String getDescription2() {
		//return StringEscapeUtils.escapeHtml4(description2); //不应该  转义 < > 为&lt;  &gt;，可以在页面展示 ，否则会破坏<em></em>高亮标签
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
}