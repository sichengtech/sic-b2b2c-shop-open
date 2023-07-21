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

import com.sicheng.admin.product.entity.ProductSpu;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.wap.service.ProductSpuService;
import com.sicheng.wap.service.TradeConsultationService;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: TradeConsultationController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author fanxiuxiu
 * @version 2017年12月20日 上午10:44:04
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class TradeConsultationController extends BaseController {
    @Autowired
    private TradeConsultationService tradeConsultationService;
    @Autowired
    private ProductSpuService productSpuService;

    /**
     * 我的查询咨询或回复
     *
     * @param consultationId 多个咨询ids
     * @param type           类别，0咨询、1回复
     * @param category       咨询类型(数据字典(1商品咨询,2支付问题,3发票及保修,4促销及赠品))
     * @param isReply        是否回复
     * @param limit          数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/consultation/list")
    public Map<String, Object> tradeConsultationList(String consultationIds, String type, String category, String isReply, String limit) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isNotBlank(category) && !StringUtils.isNumeric(category)) {
            errorList.add(FYUtils.fy("咨询分类只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        List<Long> consultationIdList = new ArrayList<>();
        TradeConsultation tradeConsultation = new TradeConsultation();
        if (StringUtils.isNotBlank(consultationIds)) {
            String[] consultationIdArray = consultationIds.split(",");
            for (int i = 0; i < consultationIdArray.length; i++) {
                if (StringUtils.isNumeric(consultationIdArray[i])) {
                    consultationIdList.add(Long.parseLong(consultationIdArray[i]));
                }
            }
        }
        UserMain userMain = AppTokenUtils.findUser();
        //用户id
        tradeConsultation.setUId(userMain.getUId());
        // 类别，0咨询、1回复
        if (StringUtils.isNotBlank(type)) {
            tradeConsultation.setType(type);
        } else {
            tradeConsultation.setType("0");
        }
        if (StringUtils.isNotBlank(category)) {
            tradeConsultation.setCategory(category);
        }
        // 是否显示，0否、1是
        tradeConsultation.setIsShow("1");
        //是否回复，0否、1是
        if (StringUtils.isNotBlank(isReply)) {
            tradeConsultation.setIsReply(isReply);
        }
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(tradeConsultation);
        if (!consultationIdList.isEmpty()) {
            wrapper.and("a.reply_id in", consultationIdList);
        }
        try {
            Integer defaultLimit = ApiUtils.getLimit(limit);
            Page<TradeConsultation> tradeConsultationPage = tradeConsultationService.selectByWhere(new Page<TradeConsultation>(1, defaultLimit, defaultLimit), wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询咨询或回复成功"), tradeConsultationPage.getList(), null);
        } catch (Exception e) {
            logger.error("查询咨询异常：", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 我的查询咨询或回复(分页)
     *
     * @param consultationId 多个咨询ids
     * @param type           类别，0咨询、1回复
     * @param category       咨询类型(数据字典(1商品咨询,2支付问题,3发票及保修,4促销及赠品))
     * @param isReply        是否回复
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/consultation/page")
    public Map<String, Object> tradeConsultationPage(String consultationIds, String type, String category, String isReply) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isNotBlank(category) && !StringUtils.isNumeric(category)) {
            errorList.add(FYUtils.fy("咨询分类只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        List<Long> consultationIdList = new ArrayList<>();
        TradeConsultation tradeConsultation = new TradeConsultation();
        if (StringUtils.isNotBlank(consultationIds)) {
            String[] consultationIdArray = consultationIds.split(",");
            for (int i = 0; i < consultationIdArray.length; i++) {
                if (StringUtils.isNumeric(consultationIdArray[i])) {
                    consultationIdList.add(Long.parseLong(consultationIdArray[i]));
                }
            }
        }
        UserMain userMain = AppTokenUtils.findUser();
        //用户id
        tradeConsultation.setUId(userMain.getUId());
        // 类别，0咨询、1回复
        if (StringUtils.isNotBlank(type)) {
            tradeConsultation.setType(type);
        } else {
            tradeConsultation.setType("0");
        }
        if (StringUtils.isNotBlank(category)) {
            tradeConsultation.setCategory(category);
        }
        // 是否显示，0否、1是
        tradeConsultation.setIsShow("1");
        //是否回复，0否、1是
        if (StringUtils.isNotBlank(isReply)) {
            tradeConsultation.setIsReply(isReply);
        }
        Wrapper wrapper = new Wrapper();
        wrapper.setEntity(tradeConsultation);
        if (!consultationIdList.isEmpty()) {
            wrapper.and("a.reply_id in", consultationIdList);
        }
        try {
            Page<TradeConsultation> tradeConsultationPage = Page.newPage();
            tradeConsultationPage = tradeConsultationService.selectByWhere(tradeConsultationPage, wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询咨询或回复成功"), tradeConsultationPage.getList(), tradeConsultationPage);
        } catch (Exception e) {
            logger.error("查询咨询异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

    /**
     * 提交商品咨询
     *
     * @param pid      商品id
     * @param category 咨询类型
     * @param content  咨询内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/consultation/save")
    public Map<String, Object> tradeConsultationSave(String pid, String category, String content) {
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(pid)) {
            errorList.add(FYUtils.fy("商品id不能为空"));
        }
        if (StringUtils.isBlank(category)) {
            errorList.add(FYUtils.fy("咨询类型不能为空"));
        }
        if (StringUtils.isBlank(content)) {
            errorList.add(FYUtils.fy("咨询内容不能为空"));
        }
        if (StringUtils.isNotBlank(pid) && !StringUtils.isNumeric(pid)) {
            errorList.add(FYUtils.fy("商品id只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        TradeConsultation tradeConsultation = new TradeConsultation();
        tradeConsultation.setPId(Long.parseLong(pid));
        tradeConsultation.setCategory(category);
        tradeConsultation.setContent(content);
        tradeConsultation.setUId(AppTokenUtils.findUser().getUId());//属主检查
        // 是否显示，0否、1是
        tradeConsultation.setIsShow("1");
        // 是否回复，0否、1
        tradeConsultation.setIsReply("0");
        // 类别，0咨询、1回复
        tradeConsultation.setType("0");
        try {
            ProductSpu productSpu = productSpuService.selectById(Long.parseLong(pid));
            tradeConsultation.setStoreId(productSpu.getStoreId());
            tradeConsultationService.insert(tradeConsultation);
            message = FYUtils.fy("咨询成功");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, message, null, null);
        } catch (Exception e) {
            logger.error("提交咨询异常：", e);
            message = FYUtils.fy("服务器发生错误");
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, message, null, null);
        }
    }

}
