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
 *  
 */
package com.sicheng.wap.web.api;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sys.entity.SysMessage;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.SysMessageService;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
  * <p>标题: UserMessageController</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2018年2月11日 下午3:27:12
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class UserMessageController extends BaseController {

    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 查询我的消息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userMessage/page")
    public Map<String, Object> userMessagePage() {
        String type = R.get("type");//消息类型:1交易信息 2售后服务信息 3商品信息 4 运营信息
        try {
            SysMessage message = new SysMessage();
            message.setType(type);
            message.setUId(AppTokenUtils.findUser().getUId());//属主检查
            Page<SysMessage> page = Page.newPage();// 从入参中取得Page分页对象
            page = sysMessageService.selectByWhere(page, new Wrapper(message));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询成功"), page.getList(), page);
        } catch (Exception e) {
            logger.error("获取消息错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取消息错误"), null, null);
        }
    }

    /**
     * 查询我的未读消息数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userMessage/unreadCount")
    public Map<String, Object> unreadCount() {
        //reading 0未读、1已读 （只限站内信）
        UserMain userMain = AppTokenUtils.findUser();
        if (userMain == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_UNAUTHORIZED, null, 0, null);
        }
        try {
            SysMessage message = new SysMessage();
            message.setReading("0");//0未读、1已读 （只限站内信）
            message.setUId(userMain.getUId());//属主检查
            Integer messageCount = sysMessageService.countByWhere(new Wrapper(message));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询成功"), messageCount, null);
        } catch (Exception e) {
            logger.error("获取消息数量错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取消息错误"), 0, null);
        }
    }

    /**
     * 删除我的消息
     * messageIds: 4170,4167
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userMessage/delete")
    public Map<String, Object> userMessageDelete(String[] messageIds) {
        if (messageIds == null || messageIds.length == 0) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("删除消息id参数不正确"), null, null);
        }
        List<String> list = java.util.Arrays.asList(messageIds);
        try {
            SysMessage message = new SysMessage();
            message.setUId(AppTokenUtils.findUser().getUId());//属主检查
            Wrapper wrapper = new Wrapper();
            wrapper.setEntity(message);
            wrapper.and("a.information_id in", list);
            sysMessageService.deleteByWhere(wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("删除消息成功"), null, null);
        } catch (Exception e) {
            logger.error("获取消息错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("删除消息失败"), null, null);
        }
    }
}
