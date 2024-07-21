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
package com.sicheng.admin.site.service;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.site.entity.SiteInfo;
import com.sicheng.admin.site.entity.SiteMessageTemplate;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sys.dao.SysMessageDao;
import com.sicheng.admin.sys.entity.SysMessage;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.admin.sys.service.SysMessageService;
import com.sicheng.admin.sys.service.SysVariableService;
import com.sicheng.common.apppush.AppPush;
import com.sicheng.common.apppush.AppPushImpl;
import com.sicheng.common.email.EmailSender;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.sms.SmsSender;
import com.sicheng.common.utils.StringUtils;

/**
 * 发送消息 Service
 *
 * @author fxx
 * @version 2017-02-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SiteSendMessagsService extends CrudService<SysMessageDao, SysMessage> {

    @Autowired
    private SiteMessageTemplateService messageTemplateService;
    @Autowired
    private SysMessageService sysMessageService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private SiteInfoService siteInfoService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private SmsSender smsSender;
    @Autowired
    private AppPush appPush;
    @Autowired
    private SysVariableService variableService;
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;
    //消息模板编号
    public final String STORE_ORDERS_DELAY = "storeOrdersDelay";//延迟收货通知
    public static final String MEMBER_ORDERS_MODIFY_FREIGHT = "memberOrdersModifyFreight";//修改运费通知
    public static final String MEMBER_COMPLAINT_UPDATE = "memberComplaintUpdate";//处理投诉通知
    public static final String STORE_COMPLAINT = "storeComplaint";//投诉通知
    public static final String MEMBER_RETURN_UPDATE = "memberReturnUpdate";//处理退款通知
    public static final String STORE_RETURN = "storeReturn";//申请退款通知
    public static final String MEMBER_ORDERS_COMMENT_REPLAY = "memberOrdersCommentReply";//评价回复通知
    public static final String MEMBER_ORDERS_COMMENT = "storeOrdersComment";//评价通知
    public static final String MEMBER_ORDERS_RESEIVE = "memberOrdersReceive";//收货通知(通知买家)
    public static final String STORE_ORDERS_RESEIVE = "storeOrdersReceive";//收货通知(通知商家)
    public static final String MEMBER_ORDERS_SEND = "memberOrdersSend";//发货通知
    public static final String MEMBER_ORDERS_PAY = "memberOrdersPay";//付款通知(通知买家)
    public static final String STORE_ORDERS_PAY = "storeOrdersPay";//付款通知(通知商家)
    public static final String STORE_ORDERS_NEW = "storeOrdersNew";//下单通知
    public static final String MEMBER_CANCEL_ORDER = "memberCancelOrder";//取消订单通知(通知买家)
    public static final String STORE_CANCEL_ORDER = "storeCancelOrder";//取消订单通知(通知商家)
    public static final String STORE_FORBID_SALE_PRODUCT = "storeForbidSaleProduct";//禁售商品通知
    public static final String STORE_PRODUCT_AUDIT_FAILURE = "storeProductAuditFailure";//商品审核不通过通知
    public static final String ACTIVATE_ACCOUNT = "activateAccount";//通过验证短信邮箱激活账号通知
    public static final String USER_REGISTER = "userRegister";//会员注册通知
    public static final String PURCHASE_ORDER = "purchaseOrder";//大宗采购发布采购单通知
    public static final String PURCHASE_ENTERED = "purchaseEntered";//大宗采购报名采购单通知
    public static final String ENTER_AURH = "enterAuth";//入驻申请
    public static final String ENTER_INFO_EDIT = "enterInfoEdit";//入驻信息（修改）
    public static final String ENTER_AURH_SUCCESS = "enterAuthSuccess";//入驻申请成功
    public static final String ENTER_AURH_FAILURE = "enterAuthFailure";//入驻申请失败
    public static final String ENTER_INFO_EDIT_SUCCESS2 = "enterInfoEditSuccess";//入驻信息（修改）成功
    public static final String ENTER_INFO_EDITFAILURE2 = "enterInfoEditFailure";//入驻信息（修改）失败
    public static final String PRODUCT_ADD = "productAdd";//发布商品
    public static final String PRODUCT_AUTH = "productAuth";//商家添加品牌，后台审核
    
    public static final String PURCHASE_RELEASE = "purchaseRelease";//供采发布通知（发给管理员）
	public static final String PURCHASE_AUTH_SUCCESS = "purchaseAuthSuccess";//供采审核成功（发给发布人）
	public static final String PURCHASE_AUTH_ERROR = "purchaseAuthError";//供采审核失败（发给发布人）
	public static final String PURCHASE_CHOOSE = "purchaseChoose";//供采被选择通知（发给报价人）

    /**
     * 给网站的会员(买家、卖家)发送消息(短信、邮件、站内信)
     *
     * @param contentMap         参数map
     * @param messageTemplateNum 消息模板编号
     * @param uId                用户id(接收人id)
     */
    public void sendMessage(final Map<String, String> contentMap, final String messageTemplateNum, final Long uId) {
        //全站是否发送消息
        if (!isSendMessage()) {
            return;
        }
        //给网站的会员(买家、卖家)发送消息通知
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    if (uId == null) {
                        return;
                    }
                    UserMain userMain = userMainService.selectById(uId);
                    if (userMain == null) {
                        return;
                    }
                    // 短信：0未发送、1已发送
                    String statusSms = "0";
                    // 邮件：0未发送、1已发送
                    String statusEmail = "0";
                    //获取短信模板内容
                    String smsContent = messageTemplateService.getSmsContent(contentMap, messageTemplateNum);
                    if (StringUtils.isNotBlank(smsContent) && StringUtils.isNotBlank(userMain.getMobile()) && "1".equals(userMain.getMobileValidate())) {
                        //发送短信
                        smsSender.sendSmsMessage(userMain.getMobile(), smsContent, contentMap, messageTemplateNum, false);
                        statusSms = "1";
                    }
                    //获取邮件模板内容
                    SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
                    if (siteInfo != null) {
                        contentMap.put("siteName", siteInfo.getName());
                    }
                    Map<String, String> emailContent = messageTemplateService.getEmailInfo(contentMap, messageTemplateNum);
                    if (emailContent != null) {
                        String title = emailContent.get("emailTitle");
                        String content = emailContent.get("emailContent");
                        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(content) && StringUtils.isNotBlank(userMain.getEmail()) && "1".equals(userMain.getEmailValidate())) {
                            emailSender.send(userMain.getEmail(), title, content, false);
                            statusEmail = "1";
                        }
                    }
                    //获取模板信息
                    SiteMessageTemplate template = messageTemplateService.selectOne(new Wrapper().and("num=", messageTemplateNum));
                    //获取站内信内容
                    String masContent = messageTemplateService.getMsgContent(contentMap, messageTemplateNum);
                    if (StringUtils.isNotBlank(masContent)) {
                        SysMessage message = new SysMessage();
                        //发送者：0系统
                        message.setSender("0");
                        String type = "";
                        if (template != null) {
                            //模板类型:1交易消息、2售后服务消息、3 商品消息、4 运营消息
                            type = template.getType();
                        }
                        message.setType(type);
                        message.setUId(uId);
                        message.setContent(masContent);
                        // 0未读、1已读
                        message.setReading("0");
                        //短息是否发送
                        message.setStatusSms(statusSms);
                        //邮件是否发送
                        message.setStatusEmail(statusEmail);
                        //站内信是否发送，0否、1是
                        message.setStatusMsg("1");
                        //微信是否发送，0否、1是
                        message.setStatusWeixin("0");
                        sysMessageService.insertSelective(message);
                    }
                    //给个人买家发送app消息通知
                    if (userMain.isTypeUserMember() && StringUtils.isNotBlank(masContent) && !messageTemplateNum.equals("memberReturnUpdate")) {
                        //退货不发消息通知（app）
                        Map<String, String> appMap = new LinkedHashMap<>();
                        appMap.put("title", template.getName());
                        appMap.put("text", masContent);
                        appMap.put("go_url", messageTemplateNum);
                        appPush.sendOneAppMessage(AppPushImpl.MASSAGE_TEMPLATE_1, userMain.getUId() + "", appMap, false);
                    }
                }
            });
        } catch (Exception e) {
            logger.error("线程异常", e);
        }
    }

    /**
     *  给网站的系统管理员发送消息(短信、邮件)
     *  @param contentMap 参数map
     *  @param messageTemplateNum 消息模板编号
     */
    public void sendMessage(final Map<String, String> contentMap, final String messageTemplateNum) {
        //全站是否发送消息
        if (!isSendMessage()) {
            return;
        }
        //给网站的系统管理员发送消息通知
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    //获取系统变量的设置（后台管理员手机号）
                    SysVariable phoneNums = variableService.getSysVariable("sys_phone_num");
                    if (phoneNums == null || StringUtils.isBlank(phoneNums.getValueClob())) {
                        return;
                    }
                    //获取发送的短信消息内容
                    String smsContent = messageTemplateService.getSmsContent(contentMap, messageTemplateNum);
                    if (StringUtils.isBlank(smsContent)) {
                        return;
                    }
                    //给后台管理员发送短信
                    String[] phones = phoneNums.getValueClob().split(",");
                    if (phones.length > 0) {
                        for (int i = 0; i < phones.length; i++) {
                            smsSender.sendSmsMessage(phones[i], smsContent, contentMap, SiteSendMessagsService.USER_REGISTER, true);
                        }
                    }
                    //获取系统变量的设置（后台管理员邮件）
                    SysVariable emaileNums = variableService.getSysVariable("emaileNums");
                    if (emaileNums == null || StringUtils.isBlank(emaileNums.getValueClob())) {
                        return;
                    }
                    //获取发送的邮件消息内容和标题
                    Map<String, String> emailInfo = messageTemplateService.getEmailInfo(contentMap, messageTemplateNum);
                    if (emailInfo == null) {
                        return;
                    }
                    String emailTitle = emailInfo.get("emailTitle");//邮件标题
                    String emailContent = emailInfo.get("emailContent");//邮件内容
                    if (StringUtils.isBlank(emailTitle) || StringUtils.isBlank(emailContent)) {
                        return;
                    }
                    //获取站点信息
                    SiteInfo siteInfo = siteInfoService.selectOne(new Wrapper().orderBy("id asc"));
                    //给后台管理员发送邮件
                    String[] emailes = emaileNums.getValueClob().split(",");
                    if (emailes.length > 0) {
                        for (int i = 0; i < emailes.length; i++) {
                            emailSender.send(emailes[i], emailTitle, emailContent, true);
                        }
                    }
                }
            });
        } catch (Exception e) {
            logger.error("线程异常", e);
        }
    }

    /**
     *  全站发送消息开关 
     * 
     *  
     */
    private boolean isSendMessage() {
        //获取系统变量的设置（是否发送信息）
        SysVariable variable = variableService.getSysVariable("sys_is_send_message");
        Long isSendMessage = 0L;//默认值 ，0代表不发送短信、邮件、微信通知
        if (variable != null && StringUtils.isNotBlank(variable.getValue())) {
            if (StringUtils.isNumeric(variable.getValue())) {
                isSendMessage = Long.parseLong(variable.getValue());
            }
        }
        return isSendMessage.equals(1L);
    }
}