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
package com.sicheng.common.utils4m;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.sicheng.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * <p>标题: WeiXinUtils</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2018年3月6日 下午4:43:52
 */
public class WeiXinUtils {

    /**
     * 用户授权，获取code
     * @param response
     * @param flag 入口类型（1,2,3）
     * 1表示被拦截器拦到微信授权获取openId后，自动登录
     * 2表示直接进入注册页，无openId，进入到微信授权获取openId后，进入注册页
     * 3表示直接进入登陆页，无openId，进入到微信授权获取openId后，进入登陆页
     * 4表示直接进入忘记密码页，无openId，进入到微信授权获取openId后，进入忘记密码页
     * @throws IOException
     */
    public static void weixinAccredit(HttpServletResponse response, String flag) throws IOException {
        Map<String, String> param = new HashMap<String, String>();
        param.put("flag", flag);
        weixinAccredit(response, param);
    }

    /**
     * 用户授权，获取code
     * @param response
     * @param param 可存放多个参数
     * flag 入口类型（1,2,3,4）
     * 1表示被拦截器拦到微信授权获取openId后，自动登录
     * 2表示直接进入注册页，无openId，进入到微信授权获取openId后，进入注册页
     * 3表示直接进入登陆页，无openId，进入到微信授权获取openId后，进入登陆页
     * 4表示直接进入忘记密码页，无openId，进入到微信授权获取openId后，进入忘记密码页
     * @throws IOException
     */
    public static void weixinAccredit(HttpServletResponse response, Map<String, String> param) throws IOException {
        StringBuilder sbl = new StringBuilder();
        sbl.append("?");
        if (param != null) {
            for (String key : param.keySet()) {
                if (key != null) {
                    String value = param.get(key);
                    sbl.append(key);
                    sbl.append("=");
                    sbl.append(value);
                    sbl.append("&");
                }
            }
        }
        String s = sbl.toString();
        if (sbl.length() > 1) {
            s = sbl.substring(0, sbl.length() - 1);
        }
        s = java.net.URLEncoder.encode(s, "UTF-8");
        String redirectUrl = Global.getConfig("wx_redirect_url") + s;
        String url = SnsAccessTokenApi.getAuthorizeURL(Global.getConfig("wx_appid"), redirectUrl, null, false);
        response.sendRedirect(url);
    }

    protected static Logger logger = LoggerFactory.getLogger(WeiXinUtils.class);

    /**
     * access_token是公众号的全局唯一接口调用凭据
     * */
    public static String getAccessToken() {
        String accessToken = "";
        String grantType = "client_credential";// 获取access_token填写client_credential
        String appId = Global.getConfig("wx_appid");// 第三方用户唯一凭证
        String secret = Global.getConfig("wx_secret");// 第三方用户唯一凭证密钥，即appsecret
        logger.info("获取access_token的appId:" + appId);
        logger.info("获取access_token的secret:" + secret);
        // 这个url链接地址和参数皆不能变
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grantType + "&appid=" + appId + "&secret=" + secret;
        logger.info("获取access_token的url:" + url);
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, StandardCharsets.UTF_8);
            logger.debug("获取access_token返回的message:" + message);
            JSONObject demoJson = JSON.parseObject(message);
            logger.info("获取access_token返回的json:" + demoJson);
            accessToken = demoJson.getString("access_token");
            logger.info("新获取的access_token:" + accessToken);
            is.close();
        } catch (Exception e) {
            logger.debug("获取access_token发生异常", e);
        }
        return accessToken;
    }

    /**
     * sapi_ticket是公众号用于调用微信JS接口的临时票据,获得jsapi_ticket之后，就可以生成JSSDK权限验证的签名了
     * 参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分）
     * @param accessToken
     * @return
     */
    public static String getTicket(String accessToken) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";// 这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, StandardCharsets.UTF_8);
            JSONObject demoJson = JSON.parseObject(message);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            logger.error("获取ticket发生错误", e);
        }
        return ticket;
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte[] messageDigest = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA1发生错误", e);
        }
        return "";
    }

    /**
     * 此方法用于服务器请求微信接口
     * **/
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            }
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            logger.error("https request error:{}", e);
        }
        return jsonObject;
    }

    /**
      * 获取微信小程序的openid 
      * @param code
      * @return
     */
    public static String getOpenId(String code) {
        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wx0b579b1fcf8ce991";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "d60f6f1f2c245d23a3491577f3132e37";
        //授权（必填），默认authorization_code
        String grant_type = "authorization_code";
        //向微信服务器 使用登录凭证 code 获取openid
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String result = sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        String openid = null;//用户的唯一标识（openid）
        String[] ayyay = result.split(",");
        for (int i = 0; i < ayyay.length; i++) {
            String[] arrayChild = ayyay[i].split(":");
            String key = arrayChild[0].replace("\"", "");
            if ("openid".equals(key)) {
                openid = arrayChild[1].replace("\"", "");
                break;
            }
        }
        return openid;
    }

    /**
      * get请求 
      * @return
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String code = "001JBbmm1n9O7r0Q44nm14idmm1JBbmj";
        getOpenId(code);
    }
}
