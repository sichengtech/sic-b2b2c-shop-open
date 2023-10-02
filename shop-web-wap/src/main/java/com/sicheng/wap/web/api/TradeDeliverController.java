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

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.trade.entity.TradeDeliver;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.TradeDeliverService;
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
 * <p>标题: TradeDeliverController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年12月18日 上午10:23:26
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class TradeDeliverController extends BaseController {

    @Autowired
    private TradeDeliverService tradeDeliverService;

    /**
     * 查询发票信息（列表）
     *
     * @param limit 数量
     * @return
     */
    @RequestMapping(value = "/{version}/trade/deliver/list")
    @ResponseBody
    public Map<String, Object> tradeDeliverList(String limit) {
        UserMain userMain = AppTokenUtils.findUser();
        Integer defaultLimit = null;
        try {
            defaultLimit = ApiUtils.getLimit(limit);
        } catch (Exception e) {
            logger.error("limit参数出现错误：" + e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("limit必须为数字"), null, null);
        }
        TradeDeliver tradeDeliver = new TradeDeliver();
        tradeDeliver.setUId(userMain.getUId());//属主检查
        try {
            Page<TradeDeliver> tradeDeliverPage = tradeDeliverService.selectByWhere(new Page<TradeDeliver>(1, defaultLimit, defaultLimit), new Wrapper(tradeDeliver));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("发票信息列表数据获取成功"), tradeDeliverPage.getList(), tradeDeliverPage);
        } catch (Exception e) {
            logger.error("发票信息列表数据获取失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器错误"), null, null);
        }
    }

    /**
     * 查询发票信息（单个）
     *
     * @param deliverId 发票id
     * @return
     */
    @RequestMapping(value = "/{version}/trade/deliver/one")
    @ResponseBody
    public Map<String, Object> tradeDeliverOne(String deliverId) {
        if (deliverId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("发票id不能为空"), "", new Page<>());
        }
        if (!StringUtils.isNumeric(deliverId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("发票id只能为数字"), null, null);
        }
        try {
            TradeDeliver entity = new TradeDeliver();
            entity.setUId(AppTokenUtils.findUser().getUId());//属主检查
            entity.setDeliverId(Long.parseLong(deliverId));
            TradeDeliver tradeDeliver = tradeDeliverService.selectOne(new Wrapper(entity));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("发票信息获取成功"), tradeDeliver, null);
        } catch (Exception e) {
            logger.error("发票信息获取失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器错误"), null, null);
        }
    }

    /**
     * 添加发票信息
     *
     * @return
     */
    @RequestMapping(value = "/{version}/trade/deliver/save")
    @ResponseBody
    public Map<String, Object> tradeDeliverSave() {
        String deliverType = R.get("deliverType");                        //发票类型：1普通发票，2增值税普通发票，3增值税专用发票
        String companyName = R.get("companyName");                        //公司名称
        String axpayerIdentityNumber = R.get("axpayerIdentityNumber");    //纳税人识别号
        String openingBank = R.get("openingBank");                        //开户行
        String accountNumber = R.get("accountNumber");                    //账号
        String address = R.get("address");                                //地址
        String phone = R.get("phone");                                    //手机号
        String hbjbuyer = R.get("hbjbuyer");                            //默认，0否、1是
        //表单验证
        List<String> errorList = validate();
        if (!errorList.isEmpty()) {
            String message = ApiUtils.errorMessage(errorList);
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        if (StringUtils.isBlank(hbjbuyer)) {
            hbjbuyer = "0";
        } else {
            hbjbuyer = "1";
        }
        TradeDeliver tradeDeliver = new TradeDeliver();
        tradeDeliver.setUId(AppTokenUtils.findUser().getUId());//属主检查
        tradeDeliver.setDeliverType(deliverType);
        tradeDeliver.setCompanyName(companyName);
        tradeDeliver.setAxpayerIdentityNumber(axpayerIdentityNumber);
        tradeDeliver.setOpeningBank(openingBank);
        tradeDeliver.setAccountNumber(accountNumber);
        tradeDeliver.setAddress(address);
        tradeDeliver.setPhone(phone);
        tradeDeliver.setHbjbuyer(hbjbuyer);
        try {
            tradeDeliverService.saveTradeDeliver(tradeDeliver);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("发票信息保存成功"), null, null);
        } catch (Exception e) {
            logger.error("发票信息保存失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器错误"), null, null);
        }
    }

    /**
     * 编辑保存发票信息
     *
     * @return
     */
    @RequestMapping(value = "/{version}/trade/deliver/edit")
    @ResponseBody
    public Map<String, Object> tradeDeliverEdit() {
        String deliverId = R.get("deliverId");                            //发票id
        String deliverType = R.get("deliverType");                        //发票类型：1普通发票，2增值税普通发票，3增值税专用发票
        String companyName = R.get("companyName");                        //公司名称
        String axpayerIdentityNumber = R.get("axpayerIdentityNumber");    //纳税人识别号
        String openingBank = R.get("openingBank");                        //开户行
        String accountNumber = R.get("accountNumber");                    //账号
        String address = R.get("address");                                //地址
        String phone = R.get("phone");                                    //手机号
        String hbjbuyer = R.get("hbjbuyer");                            //默认，0否、1是
        if (StringUtils.isBlank(deliverId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("发票id不能为空"), null, null);
        }
        if (StringUtils.isNotBlank(deliverId) && !StringUtils.isNumeric(deliverId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("发票id只能为数字"), null, null);
        }
        //表单验证
        List<String> errorList = validate();
        if (!errorList.isEmpty()) {
            String message = ApiUtils.errorMessage(errorList);
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        if (StringUtils.isBlank(hbjbuyer)) {
            hbjbuyer = "0";
        } else {
            hbjbuyer = "1";
        }
        TradeDeliver tradeDeliver = new TradeDeliver();
        tradeDeliver.setDeliverId(Long.parseLong(deliverId));
        tradeDeliver.setUId(AppTokenUtils.findUser().getUId());
        tradeDeliver.setDeliverType(deliverType);
        tradeDeliver.setCompanyName(companyName);
        tradeDeliver.setAxpayerIdentityNumber(axpayerIdentityNumber);
        tradeDeliver.setOpeningBank(openingBank);
        tradeDeliver.setAccountNumber(accountNumber);
        tradeDeliver.setAddress(address);
        tradeDeliver.setPhone(phone);
        tradeDeliver.setHbjbuyer(hbjbuyer);
        try {
            tradeDeliverService.editTradeDeliver(tradeDeliver);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("发票信息编辑成功"), null, null);
        } catch (Exception e) {
            logger.error("发票信息编辑失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器错误"), null, null);
        }
    }

    /**
     * 删除发票信息
     *
     * @param deliverId 发票id
     * @return
     */
    @RequestMapping(value = "/{version}/trade/deliver/delete")
    @ResponseBody
    public Map<String, Object> tradeDeliverDelete(String deliverId) {
        if (deliverId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("发票id不能为空"), "", new Page<>());
        }
        if (!StringUtils.isNumeric(deliverId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("发票id只能为数字"), null, null);
        }
        UserMain userMain = AppTokenUtils.findUser();
        TradeDeliver tradeDeliver = new TradeDeliver();
        tradeDeliver.setUId(userMain.getUId());//属主检查
        tradeDeliver.setDeliverId(Long.parseLong(deliverId));
        try {
            tradeDeliverService.deleteByWhere(new Wrapper(tradeDeliver));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("发票信息删除成功"), null, null);
        } catch (Exception e) {
            logger.error("发票信息删除失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器错误"), null, null);
        }
    }

    /**
     *  发票信息设为默认 
     *
     * @param deliverId 发票id
     * @param hbjbuyer  默认，0否、1是
     *                   * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/trade/deliver/default")
    public Map<String, Object> tradeDeliverDefault(String deliverId, String hbjbuyer) {
        if (deliverId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("发票id不能为空"), "", new Page<>());
        }
        if (!StringUtils.isNumeric(deliverId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("发票id只能为数字"), null, null);
        }
        UserMain userMain = AppTokenUtils.findUser();
        TradeDeliver tradeDeliver = new TradeDeliver();
        tradeDeliver.setUId(userMain.getUId());
        tradeDeliver.setDeliverId(Long.parseLong(deliverId));
        tradeDeliver.setHbjbuyer(hbjbuyer);
        try {
            tradeDeliverService.editTradeDeliver(tradeDeliver);
            if ("0".equals(hbjbuyer)) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("发票信息取消默认成功"), null, null);
            } else {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("发票信息设置默认成功"), null, null);
            }
        } catch (Exception e) {
            logger.error("发票信息设置默认失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器错误"), null, null);
        }
    }

    /**
     * 发票表单验证
     *
     * @return
     */
    private List<String> validate() {
        String deliverType = R.get("deliverType");                        //发票类型：1普通发票，2增值税普通发票，3增值税专用发票
        String companyName = R.get("companyName");                        //公司名称
        String axpayerIdentityNumber = R.get("axpayerIdentityNumber");    //纳税人识别号
        String openingBank = R.get("openingBank");                        //开户行
        String accountNumber = R.get("accountNumber");                    //账号
        String address = R.get("address");                                //地址
        String phone = R.get("phone");                                    //手机号
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(deliverType) || !("1".equals(deliverType) || "2".equals(deliverType) || "3".equals(deliverType))) {
            errorList.add(FYUtils.fy("发票类型参数不正确"));
        }
        if (StringUtils.isBlank(companyName)) {
            errorList.add(FYUtils.fy("公司名称不能为空"));
        }
        if (StringUtils.isNotBlank(companyName) && companyName.length() > 64) {
            errorList.add(FYUtils.fy("公司名称最大长度不能到于64字符"));
        }
        if (StringUtils.isNotBlank(axpayerIdentityNumber) && axpayerIdentityNumber.length() > 64) {
            errorList.add(FYUtils.fy("纳税人识别号最大长度不能到于64字符"));
        }
        if (StringUtils.isNotBlank(openingBank) && openingBank.length() > 64) {
            errorList.add(FYUtils.fy("开户行最大长度不能到于64字符"));
        }
        if (StringUtils.isNotBlank(accountNumber) && accountNumber.length() > 64) {
            errorList.add(FYUtils.fy("账号最大长度不能到于64字符"));
        }
        if (StringUtils.isNotBlank(address) && address.length() > 64) {
            errorList.add(FYUtils.fy("地址最大长度不能到于64字符"));
        }
        if (StringUtils.isBlank(phone)) {
            errorList.add(FYUtils.fy("手机号不能为空"));
        }
        if (StringUtils.isNotBlank(phone) && phone.length() > 64) {
            errorList.add(FYUtils.fy("手机号最大长度不能到于64字符"));
        }
        return errorList;
    }

}
