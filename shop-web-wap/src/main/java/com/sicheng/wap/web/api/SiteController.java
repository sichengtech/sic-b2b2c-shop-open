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
package com.sicheng.wap.web.api;

import com.sicheng.admin.site.entity.*;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.wap.service.*;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>标题: SiteController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年12月16日 上午9:52:03
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class SiteController extends BaseController {

    @Autowired
    private SiteCarouselPictureService siteCarouselPictureService;
    @Autowired
    private SiteRecommendService siteRecommendService;
    @Autowired
    private SiteAdService siteAdService;
    @Autowired
    private SiteAdContentService siteAdContentService;
    @Autowired
    private SiteRegisterService siteRegisterService;
    @Autowired
    private SiteHotSearchWordService siteHotSearchWordService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取热搜词
     *
     * @param version  接口版本，当前版本：v1
     * @param type     热搜词类型0为商品，1为店铺
     * @param pageSize 查询个数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/site/HotSearchWord/list", produces = {"application/json; charset=UTF-8"})
    public Object checkVersion(@PathVariable String version, String type, Integer pageSize) {
        try {
            //表单验证
            if (!"0".equals(type) && !"1".equals(type)) {
                type = "0";
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 10;
            }
            //查询
            Wrapper wrapper = new Wrapper();
            wrapper.and("type = ", type);
            wrapper.and("is_show = ", 1);
            wrapper.orderBy("`sort` asc");
            Page<SiteHotSearchWord> page = new Page<>();
            page.setPageSize(pageSize);
            page = siteHotSearchWordService.selectByWhere(page, wrapper);
            //排除不展示的属性
            for (SiteHotSearchWord buffer : page.getList()) {
                buffer.setCreateDate(null);
                buffer.setUpdateDate(null);
                buffer.setIsShow(null);
                buffer.setType(null);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取热搜词成功"), page.getList(), null);
        } catch (Exception e) {
            logger.error("获取热搜词失败，异常内容：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取热搜词失败"), null, null);
        }
    }

    /**
     * 根据类型获取轮播图列表
     *
     * @param type 类型
     * @return
     */
    @RequestMapping(value = "/{version}/site/carouselPicture/list")
    @ResponseBody
    public Map<String, Object> siteCarouselPictureList(String type) {
        try {
            Integer limit = ApiUtils.getLimit(null);
            if (StringUtils.isBlank(type)) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("轮播图类型为空"), null, null);
            }
            SiteCarouselPicture siteCarouselPicture = new SiteCarouselPicture();
            siteCarouselPicture.setStatus("1");//状态，0禁用 、1启用
            siteCarouselPicture.setType(type);
            Page<SiteCarouselPicture> siCarouselPicturePage = siteCarouselPictureService.selectByWhere(new Page<SiteCarouselPicture>(1, limit, limit), new Wrapper(siteCarouselPicture).orderBy("a.sort asc"));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("轮播图数据获取成功"), siCarouselPicturePage.getList(), null);
        } catch (Exception e) {
            logger.error("轮播图数据参数错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 根据推荐位编号获取推荐位
     *
     * @param number 推荐位编号
     * @return
     */
    @RequestMapping(value = "/{version}/site/recommend/one")
    @ResponseBody
    public Map<String, Object> siteRecommendOne(String number) {
        if (StringUtils.isBlank(number)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("推荐位编号为空"), null, null);
        }
        SiteRecommend sr = new SiteRecommend();
        sr.setRecommendNumber(number);
        sr.setIsOpen("1");//是否开启(0否、1是)
        try {
            logger.info("根据推荐位编号获取推荐位1，siteRecommendService="+siteRecommendService );
            SiteRecommend siteRecommend = siteRecommendService.selectOne(new Wrapper(sr));
            logger.info("根据推荐位编号获取推荐位2，siteRecommend="+siteRecommend );
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("推荐数据获取成功"), siteRecommend, null);
        } catch (Exception e) {
            logger.error("根据推荐位编号获取推荐位时异常" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 根据广告位编号获取广告位
     *
     * @param number 广告位编号
     * @return
     */
    @RequestMapping(value = "/{version}/site/ad/one")
    @ResponseBody
    public Map<String, Object> siteAdOne(String number) {
        if (StringUtils.isBlank(number)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("广告位编号为空"), null, null);
        }
        SiteAd s = new SiteAd();
        s.setAdNumber(number);
        s.setIsOpen("1");//是否开启(0否、1是)
        List<SiteAd> siteAdList = siteAdService.selectByWhere(new Wrapper(s));
        if (siteAdList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("广告位不存在"), null, null);
        }
        SiteAd siteAd = siteAdList.get(0);
        Long adId = siteAd.getAdId();
        //获取广告位内容
        SiteAdContent siteAdContent = new SiteAdContent();
        siteAdContent.setAdId(adId);
        siteAdContent.setStatus("1");//0删除、1有效（同ad_id下最多只有一个有效时间最近的为有效）
        try {
            List<SiteAdContent> siteAdContentList = siteAdContentService.selectByWhere(new Wrapper(siteAdContent));
            if (siteAdContentList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("广告位不存在"), null, null);
            }
            String content = siteAdContentList.get(0).getContent();
            if (StringUtils.isBlank(content)) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("广告位不存在"), null, null);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("广告位数据获取成功"), content, null);
        } catch (Exception e) {
            logger.error("广告位数据出现错误：" + e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 获取网站信息
     *
     * @return
     */
    @RequestMapping(value = "/{version}/site/info")
    @ResponseBody
    public Map<String, Object> siteInfo() {
        //暂时无用直接返回空
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("暂无数据"), null, null);
//		try {
//			SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper(new SiteInfo()));
//			return ApiUtils.getMap(ApiUtils.STATUS_OK, "网站信息数据获取成功", siteInfo, null);
//		} catch (Exception e) {
//			logger.error("网站信息数据出现错误："+e);
//			return ApiUtils.getMap(ApiUtils.STATUS_NOT_FOUND, "服务发生错误", null, null);
//		}
    }

    /**
     * 获取网站注册设置信息
     *
     * @return
     */
    @RequestMapping(value = "/{version}/siteRegister/info")
    @ResponseBody
    public Map<String, Object> siteRegisterInfo() {
        try {
            SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper(new SiteRegister()));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("网站注册设置信息获取成功"), siteRegister, null);
        } catch (Exception e) {
            logger.error("获取网站注册设置信息:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取网站注册设置信息错误"), null, null);
        }
    }

}
