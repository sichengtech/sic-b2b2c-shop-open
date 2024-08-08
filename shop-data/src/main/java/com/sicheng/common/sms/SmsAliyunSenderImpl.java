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
package com.sicheng.common.sms;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sicheng.admin.site.dao.SiteMessageTemplateDao;
import com.sicheng.admin.site.entity.SiteMessageTemplate;
import com.sicheng.admin.sys.dao.SysSmsLogDao;
import com.sicheng.admin.sys.dao.SysSmsServerDao;
import com.sicheng.admin.sys.entity.SysSmsLog;
import com.sicheng.admin.sys.entity.SysSmsServer;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;

/**
 * <p>标题: SmsAlidayuSenderImpl 阿里云短信网关</p>
 * <p>描述: 使用阿里云短信服务发送一条短信</p>
 * 
 * @author zjl
 * @date 2017年4月8日 下午6:49:28
 */
public class SmsAliyunSenderImpl implements SmsSender {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysSmsServerDao smsServerDao;
	
	@Autowired
	private SysSmsLogDao sysSmsLogDao;
	
	@Autowired
	private SiteMessageTemplateDao messageTemplateDao;
	
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	
	/**
	 * 发送一条短信消息
	 * @param phone 手机号  公共参数
	 * @param completeContent 使用本地消息模板获得的完整内容  直接发送短信内容时单独使用
	 * @param paramMap 第三方短信内容参数  使用第三方短信模板时单独使用
	 * @param templateCode 本地消息模板的编号  使用第三方短信模板时单独使用
	 * @param async	true表示异步发送，false表示同步发送 公共参数
	 */
	@Override
	public boolean sendSmsMessage(String phone,String completeContent,Map<String,String> paramMap,String templateCode,boolean async){
		//短信通道信息
		SysSmsServer entity= selectSmsServer();
		if(entity==null){
			logger.warn("在管理后台，未设置短信服务器相关信息，无法发出短信");
			return false;
		}
		
		if("0".equals(entity.getStatus())){  //(状态，0停用，1启用)
			logger.warn("在管理后台，短信服务器被停用，无法发出短信");
			return false;
		}
		final String thirdTemplateCode=selectThirdTemplateCode(templateCode);//用本地消息模标号查询出对应第三方短信模板id
		if(thirdTemplateCode==null){
			logger.warn("还没有添加此类消息的第三方短信模板id，无法发出短信");
			return false;
		}
		if(paramMap==null || paramMap.isEmpty()){
			logger.warn("还没有添加模板参数，无法发出短信");
			return false;
		}
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		final String paramString=JsonMapper.toJsonString(paramMap);//短信模板参数
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = entity.getUrl();//短信API产品域名（接口地址固定，无需修改）
		//替换成你的AK
		final String accessKeyId = entity.getAccessKey();//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = entity.getAppId();//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			logger.error("阿里短信网关初始化ascClient出错:", e1);
			return false;
		}
		final IAcsClient acsClient = new DefaultAcsClient(profile);
		//组装请求对象
		final SendSmsRequest request = new SendSmsRequest();
		//使用post提交
		request.setMethod(MethodType.POST);
		//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
		request.setPhoneNumbers(phone);
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(entity.getClientId());
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(thirdTemplateCode);
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam(paramString);
		//可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");
		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("yourOutId");
		//请求失败这里会抛ClientException异常
		if(async){
			taskExecutor.execute(new Runnable() {
				public void run() {
					try {
						SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
						saveSmsLog(sendSmsResponse, paramString, thirdTemplateCode);
					} catch (ClientException e) {
						logger.error("阿里云短信网关出错", e);
					}
				}
			});
			return true;
		}else{
			try {
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				saveSmsLog(sendSmsResponse, paramString, thirdTemplateCode);
				if(sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
					return true;
				}else{
					return false;
				}
			} catch (ClientException e) {
				logger.error("阿里云短信网关出错", e);
				return false;
			}
		}
	}
	
	/**
	 * 查出sms设置信息
	 * SysSmsServer表可以保存多行，但只使用一行记录
	 * 从库中查出ID最小的一个
	 */
	private SysSmsServer selectSmsServer(){
		SysSmsServer entity = null;
		if(entity==null){
			//从库中查出ID最小的一个
			Page<SysSmsServer> page=new Page<SysSmsServer>();
			page.setOrderBy("id asc");//按ID排序
			SysSmsServer smsServer=new SysSmsServer();
			
			List<SysSmsServer> list=smsServerDao.selectByWhere(page,new Wrapper(smsServer));
		
			if(list.size()>0){
				entity=list.get(0);//取ID最小的一个
			}
		}
		return entity;
	}
	
	/**
	 * 用本地消息模标号查询出对应第三方短信模板id
	 * @param templateCode 本地消息模标号
	 * @return 返回的是第三方短信模板id，如果返回null,说明还没有添加此类消息的第三方短信模板id，不发送短信
	 
	 */
	private String selectThirdTemplateCode(String templateCode){
		String thirdTemplateCode = null;
		if(StringUtils.isNotBlank(templateCode)){
			List<SiteMessageTemplate> list=messageTemplateDao.selectByWhere(new Page<SiteMessageTemplate>(), new Wrapper().and("a.num=", templateCode));
			if(!list.isEmpty()){
				thirdTemplateCode=list.get(0).getThirdtemplatecode();
			}
		}
		return thirdTemplateCode;
	}
	
	/**
	 * 将短信信息存入到短信日志表中 
	 * @param sendSmsResponse 阿里大于发送短信响应结果
	 * @param paramString 短信内容参数
	 * @param templateCode 短信模板ID
	 */
	
	private void saveSmsLog(SendSmsResponse sendSmsResponse,String paramString, String templateCode) {
		SysSmsLog smsLog = new SysSmsLog();
		smsLog.setContent(paramString);//短信内容
		smsLog.setTemplatecode(templateCode);//模板id
		
		if(sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
			smsLog.setStatus("1");//发送状态（0、失败，1、成功）
		}else{
			smsLog.setStatus("0");//发送状态（0、失败，1、成功）
		}
		smsLog.setBewrite(sendSmsResponse.getMessage());//描述
		smsLog.setType("3");//短信网关类型（1、阿里大于，2、慧聪短信网关，3、阿里云短信服务）
		sysSmsLogDao.insertSelective(smsLog);
	}
	
}