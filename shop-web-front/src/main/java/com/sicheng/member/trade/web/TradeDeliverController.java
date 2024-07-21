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
package com.sicheng.member.trade.web;

import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sys.entity.Dict;
import com.sicheng.admin.sys.utils.DictUtils;
import com.sicheng.admin.trade.entity.TradeDeliver;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.seller.trade.service.TradeDeliverService;
import com.sicheng.sso.utils.SsoUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发票 Controller
 * 所属模块：trade
 *
 * @author fxx
 * @version 2017-01-06
 */
@Controller
@RequestMapping(value = "${memberPath}/trade/tradeDeliver")
public class TradeDeliverController extends BaseController {

    @Autowired
    private TradeDeliverService tradeDeliverService;

    /**
     * 执行保存
     *
     * @param tradeDeliver       实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "save2")
    public Map<String, Object> save2(TradeDeliver tradeDeliver, Model model, RedirectAttributes redirectAttributes) {
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeDeliver.setUId(userMember.getUId());
        Map<String, Object> map = new HashMap<>();
        //表单验证
        List<String> errorList = validate(tradeDeliver, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            map.put("message", errorList.toString());
            return map;
        }
        //业务处理
        tradeDeliverService.insertSelective(tradeDeliver);
        map.put("message", "success");
        map.put("tradeDeliver", tradeDeliver);
        return map;
    }

    /**
     * 删除
     *
     * @param tradeDeliver       实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "delete")
    public String delete(TradeDeliver tradeDeliver, RedirectAttributes redirectAttributes) {
        tradeDeliverService.deleteById(tradeDeliver.getId());
        addMessage(redirectAttributes, "删除发票成功");
        return "redirect:" + Global.getAdminPath() + "/trade/tradeDeliver/list.do?repage";
    }

    /**
     * 获取用户发票信息
     *
     * @param memberAddress 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "getDeliver")
    public Map<String, Object> getAddress(HttpServletRequest request, HttpServletResponse response, Model model) {
        //用户id
        Map<String, Object> map = new HashMap<>();
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        List<Dict> dicts = DictUtils.getDictList("deliver_type");
        List<TradeDeliver> deliverList = tradeDeliverService.selectByWhere(new Wrapper().and("u_id=", userMember.getUId()));
        map.put("dicts", dicts);
        map.put("deliverList", deliverList);
        return map;
    }

    /**
     * 删除
     *
     * @param memberAddress      实体对象
     * @param redirectAttributes
     * @return 返回json，在确认订单页中用
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "deleteDeliver")
    public String deleteDeliver(TradeDeliver tradeDeliver, RedirectAttributes redirectAttributes) {
        String message = "1";
        if (tradeDeliver == null) {
            message = "0";
            return message;
        }
        //用户id
        UserMember userMember = SsoUtils.getUserMain().getUserMember();
        tradeDeliver.setUId(userMember.getUId());
        tradeDeliverService.deleteByWhere(new Wrapper(tradeDeliver));
        return message;
    }

    /**
     * 表单验证
     *
     * @param tradeDeliver 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeDeliver tradeDeliver, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (tradeDeliver.getUId() == null) {
            errorList.add("会员id(会员表Id)不能为空");
        }
        if (StringUtils.isBlank(tradeDeliver.getDeliverType())) {
            errorList.add("发票类型：1普通发票，2增值税普通发票，3增值税专用发票不能为空");
        }
        if (StringUtils.isNotBlank(tradeDeliver.getDeliverType()) && tradeDeliver.getDeliverType().length() > 1) {
            errorList.add("发票类型：1普通发票，2增值税普通发票，3增值税专用发票最大长度不能超过1字符");
        }
		/*if(StringUtils.isBlank(tradeDeliver.getDeliverTitle())){
			errorList.add("发票抬头不能为空");
		}
		if(StringUtils.isNotBlank(tradeDeliver.getDeliverTitle()) && tradeDeliver.getDeliverTitle().length() > 64){
			errorList.add("发票抬头最大长度不能超过64字符");
		}
		if(StringUtils.isBlank(tradeDeliver.getDeliverContent())){
			errorList.add("发票内容不能为空");
		}
		if(StringUtils.isNotBlank(tradeDeliver.getDeliverContent()) && tradeDeliver.getDeliverContent().length() > 255){
			errorList.add("发票内容最大长度不能超过255字符");
		}*/
        return errorList;
    }

}