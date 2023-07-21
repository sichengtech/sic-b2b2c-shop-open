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

import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.entity.TradeCommentImage;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.TradeCommentImageService;
import com.sicheng.wap.service.TradeCommentService;
import com.sicheng.wap.service.TradeOrderService;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * <p>标题: TradeCommentController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fanxiuxiu
 * @version 2018年1月5日 上午11:26:45
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class TradeCommentController extends BaseController {

    @Autowired
    private TradeCommentService tradeCommentService;
    @Autowired
    private TradeCommentImageService tradeCommentImageService;
    @Autowired
    private TradeOrderService tradeOrderService;

    /**
     * 根据回复的评价id、评价类型、评价等级获取当前用户的评价
     *
     * @param replyIds 回复的评价id
     * @param type     评价类型(0评论、1追评、2回复)
     * @param grade    评价等级(1好评,2中评,3差评)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/comment/page")
    public Map<String, Object> tradeCommentPage(String replyIds, String type, String grade) {
        List<String> errorList = vaidate2(type, grade);
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        TradeComment tradeComment = new TradeComment();
        tradeComment.setUId(AppTokenUtils.findUser().getUId());
        //类型，0评论、1追评、2回复
        if (StringUtils.isNotBlank(type)) {
            tradeComment.setType(type);
        }
        //评价等级(1好评,2中评,3差评)
        if (StringUtils.isNotBlank(grade)) {
            tradeComment.setGrade(grade);
        }
        Wrapper wrapper = new Wrapper(tradeComment);
        if (StringUtils.isNotBlank(replyIds)) {
            Object[] commentIdss = replyIds.split(",");
            List<Object> commentIdList = Arrays.asList(commentIdss);
            wrapper.and("reply_id in", commentIdList);
        }
        try {
            Page<TradeComment> tradeCommentPage = Page.newPage();
            tradeCommentPage = tradeCommentService.selectByWhere(tradeCommentPage, wrapper);
            //取头像
            for (TradeComment buffer : tradeCommentPage.getList()) {
                buffer.setHeadPicPath(buffer.getUserMain().getUserMember().getHeadPicPath());
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, tradeCommentPage.getList(), tradeCommentPage);
        } catch (Exception e) {
            logger.error("查询评价异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 根据多个评价id获取评价的图片
     *
     * @param commentIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/comment/image/list")
    public Map<String, Object> tradeCommentImageList(String commentIds) {
        if (StringUtils.isBlank(commentIds)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("评论id不能为空"), null, null);
        }
        Object[] commentIdss = commentIds.split(",");
        List<Object> commentIdList = Arrays.asList(commentIdss);
        List<TradeComment> tradeCommentList = tradeCommentService.selectByWhere(new Wrapper().and("comment_id in", commentIdList));
        if (tradeCommentList == null || tradeCommentList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("评论不存在"), null, null);
        }
        List<Long> commentIdList2 = new ArrayList<>();
        for (int i = 0; i < tradeCommentList.size(); i++) {
            commentIdList2.add(tradeCommentList.get(i).getCommentId());
        }
        try {
            List<TradeCommentImage> tradeCommentImageList = tradeCommentImageService.selectByWhere(new Wrapper().and("comment_id in", commentIdList2));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询成功"), tradeCommentImageList, null);
        } catch (Exception e) {
            logger.error("查询评价图片异常：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 提交评价
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/comment/save")
    public Map<String, Object> tradeCommentSave() {
        String orderId = R.get("orderId");                            //订单id
        String isAddComment = R.get("isAddComment");                    //是否追评
        String serviceAttitudeScore = R.get("serviceAttitudeScore");    //卖家服务态度评分
        String deliverySpeedScore = R.get("deliverySpeedScore");        //卖家发货速度评分
        String[] contents = R.getArray("content");                    //评论内容
        String[] pids = R.getArray("pid");                            //多个商品id
        String[] skuIds = R.getArray("skuId");                        //多个skuId
        String[] productScores = R.getArray("productScore");            //多个商品评分
        String[] grades = R.getArray("grade");                        //评价等级
        String[] imgPath = R.getArray("imgPath");                        //多个图片
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(orderId)) {
            errorList.add(FYUtils.fy("订单id不能为空"));
        }
        if (StringUtils.isNotBlank(orderId) && !StringUtils.isNumeric(orderId)) {
            errorList.add(FYUtils.fy("订单id只能是数字"));
        }
        if (contents == null || contents.length == 0) {
            errorList.add(FYUtils.fy("评论内容不能为空"));
        }
        if (contents != null && contents.length > 0) {
            for (int i = 0; i < contents.length; i++) {
                if(StringUtils.isBlank(contents[i])){
                    errorList.add(FYUtils.fy("评论内容不能为空"));
                }else if (contents[i].length() > 500) {
                    errorList.add(FYUtils.fy("评论内容不能超过500字"));
                }
            }
        }
        if (pids == null || pids.length == 0) {
            errorList.add(FYUtils.fy("商品id不能为空"));
        }
        if (skuIds == null || skuIds.length == 0) {
            errorList.add(FYUtils.fy("skuId不能为空"));
        }
        if (grades == null || grades.length == 0) {
            errorList.add(FYUtils.fy("商品评分不能为空"));
        }
        try {
            TradeOrder tradeOrder = tradeOrderService.selectById(Long.parseLong(orderId));
            if (tradeOrder == null) {
                errorList.add(FYUtils.fy("订单不存在"));
            }
            String message = ApiUtils.errorMessage(errorList);
            if (!errorList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
            }
            //用户id
            UserMember userMember = AppTokenUtils.findUser().getUserMember();
            List<TradeComment> tradeCommentList = new ArrayList<>();
            //业务处理
            for (int i = 0; i < pids.length; i++) {
                TradeComment tradeComment2 = new TradeComment();
                tradeComment2.setOrderId(Long.parseLong(orderId));
                tradeComment2.setStoreId(tradeOrder.getStoreId());
                tradeComment2.setPId(Long.valueOf(pids[i]));
                tradeComment2.setSkuId(Long.valueOf(skuIds[i]));
                tradeComment2.setUId(userMember.getUId());//属主检查
                if ("1".equals(isAddComment)) {
                    TradeComment tradeComment = new TradeComment();
                    tradeComment.setOrderId(Long.parseLong(orderId));
                    tradeComment.setPId(Long.parseLong(pids[i]));
                    tradeComment.setSkuId(Long.parseLong(skuIds[i]));
                    List<TradeComment> list = tradeCommentService.selectByWhere(new Wrapper(tradeComment));
                    if (list.isEmpty()) {
                        continue;
                    }
                    tradeComment2.setReplyId(list.get(0).getCommentId());
                    //type类型，0评论、1追评、2回复
                    tradeComment2.setType("1");
                    //是否追评,0否、1是
                    tradeOrder.setIsAdditionalComment("1");
                } else {
                    //type类型，0评论、1追评、2回复
                    tradeComment2.setType("0");
                    //是否追评,0否、1是
                    tradeOrder.setIsAdditionalComment("0");
                }
                tradeComment2.setProductScore(productScores[i]);
                tradeComment2.setServiceAttitudeScore(serviceAttitudeScore);
                tradeComment2.setDeliverySpeedScore(deliverySpeedScore);
                //是否显示,0否 、1是
                tradeComment2.setIsShow("1");
                tradeComment2.setCreateDate(new Date());
                if (contents.length > i) {
                    if ("".equals(contents[i])) {
                        tradeComment2.setContent(FYUtils.fy("好评"));
                    } else {
                        tradeComment2.setContent(contents[i]);
                    }
                }
                if (grades.length > i) {
                    tradeComment2.setGrade(grades[i]);
                }
                tradeCommentList.add(tradeComment2);
            }

            //业务处理
            tradeOrder.setUId(userMember.getUId());//属主检查
            tradeCommentService.addComment(tradeCommentList, imgPath, tradeOrder);
            message = FYUtils.fy("评价成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("查询评价图片异常：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 隐藏\显示评价
     *
     * @param commentId 评价id
     * @param isShow    是否显示(0否、1是)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/comment/hide")
    public Map<String, Object> tradeCommentHide(String commentId, String isShow) {
        List<String> errorList = validate1(commentId);
        if (StringUtils.isBlank(isShow)) {
            errorList.add(FYUtils.fy("缺少参数是否显示"));
        }
        if (StringUtils.isNotBlank(isShow) && !StringUtils.isNumeric(isShow)) {
            errorList.add(FYUtils.fy("是否显示只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        TradeComment tradeComment = new TradeComment();
        //是否显示，0否、1是
        tradeComment.setIsShow(isShow);
        TradeComment tradeComment2 = new TradeComment();
        tradeComment2.setCommentId(Long.parseLong(commentId));
        tradeComment2.setUId(AppTokenUtils.findUser().getUId());//属主检查
        try {
            tradeCommentService.updateByWhereSelective(tradeComment, new Wrapper(tradeComment2));
            message = FYUtils.fy("隐藏成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("隐藏评价异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }


    /**
     * 验证参数
     *
     * @param commentId
     * @return
     */
    private List<String> validate1(String commentId) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(commentId)) {
            errorList.add(FYUtils.fy("评价id不能为空"));
        }
        if (StringUtils.isNotBlank(commentId) && !StringUtils.isNumeric(commentId)) {
            errorList.add(FYUtils.fy("评价id只能是数字"));
        }
        return errorList;
    }

    /**
     * 验证参数
     *
     * @param commentId
     * @return
     */
    private List<String> vaidate2(String type, String grade) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isNotBlank(grade) && !StringUtils.isNumeric(grade)) {
            errorList.add(FYUtils.fy("评价等级只能是数字"));
        }
        if (StringUtils.isNotBlank(type) && !StringUtils.isNumeric(type)) {
            errorList.add(FYUtils.fy("评价类型只能是数字"));
        }
        return errorList;
    }

}
