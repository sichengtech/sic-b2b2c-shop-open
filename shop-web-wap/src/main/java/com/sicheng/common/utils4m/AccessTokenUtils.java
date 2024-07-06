package com.sicheng.common.utils4m;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * access_token是公众号的全局唯一接口调用凭据
 * sapi_ticket是公众号用于调用微信JS接口的临时票据
 * access_token的每天调用限额2000次
 * 长期存储access_token
 */
public class AccessTokenUtils {
    private static Logger log = LoggerFactory.getLogger(AccessTokenUtils.class);
    private static Map<String, String> accessTokenMap = new HashMap<>();

    public static Map<String, String> getAccessToken() {
        String time = accessTokenMap.get("time");
        String accessToken = accessTokenMap.get("access_token");
        Long nowDate = new Date().getTime();
        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < (1.5 * 60 * 60 * 1000)) {
            log.info("accessToken存在,且没有超时,返回accessTokenMap");
            return accessTokenMap;
        }
        synchronized (AccessTokenUtils.class) {
            if (accessToken != null && time != null && nowDate - Long.parseLong(time) < (1.5 * 60 * 60 * 1000)) {
                log.info("accessToken存在,且没有超时,返回accessTokenMap");
                return accessTokenMap;
            }
            log.info("accessToken 超时,或者不存在,重新获取");
            try {
                String access_token = WeiXinUtils.getAccessToken();
                log.info("access_token:" + access_token);
                //"这里是直接调用微信的API去直接获取 accessToken 和Jsapi_ticket 获取";
                String jsapi_ticket = WeiXinUtils.getTicket(access_token);
                log.info("jsapi_ticket:" + jsapi_ticket);
                //"获取jsapi_token";
                accessTokenMap.put("time", nowDate + "");
                accessTokenMap.put("access_token", access_token);
                accessTokenMap.put("jsapi_ticket", jsapi_ticket);
                log.info("获取的access_token:" + accessTokenMap.get("access_token"));
                log.info("获取的jsapi_ticket:" + accessTokenMap.get("jsapi_ticket"));
            } catch (Exception e) {
                log.error("微信服务器发生错误", e);
            }
        }
        return accessTokenMap;

    }

    /**
     * 并发测试
     *
     * @param args
     */
    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                AccessTokenUtils.getAccessToken();
            }
        };
        new Thread(r1).start();
        new Thread(r1).start();
    }
}
