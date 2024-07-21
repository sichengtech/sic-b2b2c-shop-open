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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sicheng.admin.sys.dao.SysSmsLogDao;
import com.sicheng.admin.sys.dao.SysSmsServerDao;
import com.sicheng.admin.sys.entity.SysSmsLog;
import com.sicheng.admin.sys.entity.SysSmsServer;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SmsHc360SenderImpl implements SmsSender {

    protected int MAX_RECEIVE_SIZE = 1000 * 1000;
    protected static String METHOD = "POST";// 方法
    protected static boolean doinput = true;//
    protected static boolean dooutput = true;//
    protected static boolean followRedirects = true;// 跟随重定向
    protected static int timeoutForConnect = 3000;// 连接超时
    protected static int timeoutForRead = 10000;// 读取超时
    protected static Map<String, List<String>> headerMap = null;// 请求头键值
    private static final String CHARSET = "GBK";

    protected Logger logger = LoggerFactory.getLogger(getClass());//日志对象

    @Autowired
    private SysSmsServerDao smsServerDao;

    @Autowired
    private SysSmsLogDao sysSmsLogDao;

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;


    /**
     * 发送一条短信消息
     *
     * @param phone           手机号  公共参数
     * @param completeContent 使用本地消息模板获得的完整内容  直接发送短信内容时单独使用
     * @param paramMap        第三方短信内容参数  使用第三方短信模板时单独使用
     * @param templateCode    本地消息模板的编号  使用第三方短信模板时单独使用
     * @param async           true表示异步发送，false表示同步发送 公共参数
     */
    @Override
    public boolean sendSmsMessage(String phone, final String completeContent, Map<String, String> paramMap, String templateCode, boolean async) {
        final SysSmsServer entity = selectSmsServer();
        if (entity == null) {
            logger.warn("在管理后台，未设置短信服务器相关信息，无法发出短信");
            return false;
        }

        if ("0".equals(entity.getStatus())) {  //(状态，0停用，1启用)
            logger.warn("在管理后台，短信服务器被停用，无法发出短信");
            return false;
        }

        String time_stamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        StringBuilder sbl = new StringBuilder();
        sbl.append("{");
        sbl.append("client_id:'" + entity.getAccessKey() + "',");
        sbl.append("client_secret:'" + entity.getAppId() + "',");
        sbl.append("mobile:'" + phone + "',");
        sbl.append("content:'" + completeContent + "',");
        sbl.append("time_stamp:'" + time_stamp + "'");
        sbl.append("}");

        final Map<String, String> map = new HashMap<String, String>();
        map.put("schema", "json");
        map.put("param", sbl.toString());

        if (async) {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    String str = post(entity.getUrl(), map);
                    saveSmsLog(str, completeContent);
                }
            });
            return true;
        } else {
            String str = post(entity.getUrl(), map);
            saveSmsLog(str, completeContent);
            boolean result = saveSmsLog(str, completeContent);
            return result;
        }
    }

    /**
     * 查出sms设置信息
     * SysSmsServer表可以保存多行，但只使用一行记录
     * 从库中查出ID最小的一个
     */
    private SysSmsServer selectSmsServer() {
        SysSmsServer entity = null;
        if (entity == null) {
            //从库中查出ID最小的一个
            Page<SysSmsServer> page = new Page<SysSmsServer>();
            page.setOrderBy("id asc");//按ID排序
            SysSmsServer smsServer = new SysSmsServer();

            List<SysSmsServer> list = smsServerDao.selectByWhere(page, new Wrapper(smsServer));

            if (list.size() > 0) {
                entity = list.get(0);//取ID最小的一个
            }
        }
        return entity;
    }

    /**
     * 使用慧聪短信网关发送短信
     *
     * @param targetUrl 短信网关地址
     * @param maps      短信网关参数
     * @return
     */
    public String post(String targetUrl, Map<String, String> maps) {
        OutputStream out = null;
        BufferedReader in = null;
        String res = "";
        try {
            URL url = new URL(targetUrl);
            HttpURLConnection con = null;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(METHOD);
            con.setInstanceFollowRedirects(followRedirects);
            con.setDoInput(doinput);
            con.setDoOutput(dooutput);
            con.setConnectTimeout(timeoutForConnect);
            con.setReadTimeout(timeoutForRead);
            if (headerMap != null) {
                for (Entry<String, List<String>> entry : headerMap.entrySet()) {
                    String key = entry.getKey();
                    List<String> valueList = entry.getValue();
                    for (String value : valueList) {
                        con.addRequestProperty(key, value);
                    }
                }
            }
            // 发送POST请求必须设置如下两行
            con.setDoOutput(true);
            con.setDoInput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = con.getOutputStream();
            // 准备参数
            StringBuilder sb = new StringBuilder();
            for (String key : maps.keySet()) {
                String value = maps.get(key);
                sb.append(key);
                sb.append("=");
                sb.append(value);
                sb.append("&");
            }
            // 发送请求参数
            out.write(sb.toString().getBytes(CHARSET));
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                res += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.error("输出流或缓冲字符输入流关闭异常！", ex);
            }
        }
        return res;
    }

    /**
     * @param str  慧聪短信网关发送短信响应结果
     * @param text 短信内容
     */
    private boolean saveSmsLog(String str, String text) {
        JSONObject object = JSON.parseObject(str);

        //将短信信息存入到短信日志表中
        SysSmsLog smsLog = new SysSmsLog();
        smsLog.setContent(text);//短信内容
        if ((boolean) object.get("result")) {
            smsLog.setStatus("1");//发送状态（0、失败，1、成功）
        } else {
            smsLog.setStatus("0");//发送状态（0、失败，1、成功）
        }
        smsLog.setBewrite(object.get("message").toString());//描述
        smsLog.setType("2");//短信网关类型（1、阿里大于，2、慧聪短信网关）
        sysSmsLogDao.insertSelective(smsLog);
        return (boolean) object.get("result");
    }
}