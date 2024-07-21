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
package com.sicheng.common.utils4m;

import com.sicheng.admin.sso.entity.UserAppToken;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.cache.ShopCache;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.wap.service.UserAppTokenService;
import com.sicheng.wap.service.UserMainService;
import org.apache.ibatis.cursor.Cursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;

/**
  * <p>标题: AppTokenUtils</p>
  * <p>描述: 通过TerminalType请求头实现对新老两套方案的兼容
 老方案：使用cookie 和session实现的一套登录认证
 新方案：使用了AppToken，这是基于缓存+数据库实现的一套登录认证</p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author  zhaolei
  * @version 2019年01月16日 下午3:04:40
  *
  */
public class AppTokenUtils {
    public static final String APP_TOKEN_KEY = "AppToken";//AppToken的key
    public static final String TERMINAL_TYPE_KEY = "TerminalType";//终端类型的key
    public static final String TERMINAL_TYPE_VALUE = "app";//终端类型的value
    public static final String USER_SESSION_KEY = "userSessionKey485";//存储登录用户信息的key

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(AppTokenUtils.class);

    /**
     * 判断是否是APP发来的请求
     * AppTokenUtils工具类，同时支持这两种登录认证机制，是依靠http请求头中是否携带了TerminalType参数来区分的。
     * 参数文档请看：App调用接口时的请求头
     * @return
     */
    public static boolean isAppRequest() {
        HttpServletRequest request = R.getRequest();
        String value = request.getHeader(AppTokenUtils.TERMINAL_TYPE_KEY);
        return AppTokenUtils.TERMINAL_TYPE_VALUE.equals(value);
    }

    /**
      * 从session获取当前登录的用户 
      * @return 登录用户信息
     */
    public static UserMain findUser() {
        if (!AppTokenUtils.isAppRequest()) {
            //之前开发的wap微信商城登录认证使用Cookie Session，
            return (UserMain) R.getSession().getAttribute(AppTokenUtils.USER_SESSION_KEY);
        } else {
            //是uni-app开发的app、小程序、h5,要使用AppToken登录认证机制
            UserAppTokenService userAppTokenService = SpringContextHolder.getBean(UserAppTokenService.class);
            UserMainService userMainService = SpringContextHolder.getBean(UserMainService.class);
            //先从head中取出AppToken，移动端访问服务端时会携带AppToken
            String appToken = AppTokenUtils.getAppTokenFromRequest();
            if (StringUtils.isBlank(appToken)) {
                return null;
            }
            UserMain userMain = findCache(appToken);
            if (userMain != null) {
                return userMain;
            }
            //从库中查出用户信息
            UserAppToken uat = new UserAppToken();
            uat.setToken(appToken);
            uat.setType("0");//业务类型：0用户是前台会员，1用户是后台管理员。
            uat.setStatus("1");//是否有效（0无效，1有效），如用户退出或修改了密码，应置为无效

            long t1 = getDefValidDate() * 60L * 60L * 24L * 1000L;//90天的毫秒数
            long t2 = System.currentTimeMillis();
            Wrapper w = new Wrapper(uat);
            w.and("valid_date >", new Date(t2 - t1));//在有效期内
            UserAppToken one = userAppTokenService.selectOne(w);
            if(one==null || one.getUId()==null){
                logger.warn("userAppToken无效认定为未登录，userAppToken={}",appToken);
                return null;
            }
            userMain = userMainService.selectById(one.getUId());
            //放入缓存
            if (userMain != null) {
                putCache(userMain, appToken);
            }
            return userMain;
        }
    }

    /**
      * 保存登录信息到session 
      * @param userMain 登录用户信息
      * @return AppToken
     */
    public static String saveUser(UserMain userMain) {
        if (!AppTokenUtils.isAppRequest()) {
            //之前开发的wap微信商城登录认证使用Cookie Session，
            R.getSession().setAttribute(AppTokenUtils.USER_SESSION_KEY, userMain);
            return null;
        } else {
            //是uni-app开发的app、小程序、h5,要使用AppToken登录认证机制
            if (userMain == null) {
                return null;
            }
            UserAppTokenService userAppTokenService = SpringContextHolder.getBean(UserAppTokenService.class);

            //在服务端生成token并存入token表，
            String appToken = IdGen.uuid();
            UserAppToken uat = new UserAppToken();
            uat.setUId(userMain.getId());
            uat.setToken(appToken);
            userAppTokenService.insertSelective(uat);

            //放入缓存
            if (userMain != null) {
                putCache(userMain, appToken);
            }
            return appToken;
        }
    }

    /**
      * 从session移除登录用户 
     */
    public static void removeUser() {
        if (!AppTokenUtils.isAppRequest()) {
            //之前开发的wap微信商城登录认证使用Cookie Session，
            R.getSession().removeAttribute(AppTokenUtils.USER_SESSION_KEY);
        } else {
            //是uni-app开发的app、小程序、h5,要使用AppToken登录认证机制

            //先从head中取出AppToken，移动端访问服务端时会携带AppToken
            String appToken = AppTokenUtils.getAppTokenFromRequest();
            if (StringUtils.isBlank(appToken)) {
                return;
            }

            //清空缓存
            AppTokenUtils.clearCache(appToken);

            //从库中删除
            UserAppTokenService userAppTokenService = SpringContextHolder.getBean(UserAppTokenService.class);
            UserAppToken uat = new UserAppToken();
            uat.setToken(appToken);
            userAppTokenService.deleteByWhere(new Wrapper(uat));
        }
    }

    /**
      * 为AppToken续命,异步
     */
    public static void touchAppToken() {
        //是uni-app开发的app、小程序、h5,要使用AppToken登录认证机制
        try {
            //先从head中取出AppToken，移动端访问服务端时会携带AppToken
            final String appToken = AppTokenUtils.getAppTokenFromRequest();
            TaskExecutor taskExecutor = SpringContextHolder.getBean(TaskExecutor.class);
            taskExecutor.execute(new Runnable() {
                public void run() {
                    touchAppTokenInner(appToken);
                }
            });
        } catch (Exception e) {
            logger.error("AppToken续命时发生异常", e);
        }
    }

    /**
     * 为AppToken续命,同步
     */
    private static void touchAppTokenInner(String appToken) {
        //是uni-app开发的app、小程序、h5,要使用AppToken登录认证机制
        try {
            if (StringUtils.isBlank(appToken)) {
                return;
            }
            //缓存续命
            AppTokenUtils.touchCache(appToken);
            //数据表续命
            UserAppTokenService userAppTokenService = SpringContextHolder.getBean(UserAppTokenService.class);
            UserAppToken entity = new UserAppToken();
            //有效期的开始时间，如2019-01-01。
            //如果有效期是90天，那么将于2019-03-01的前一秒到期，由定时任务清理。
            //当为AppToken续命时，valid_date值被更新.
            entity.setValidDate(new Date());
            UserAppToken uat = new UserAppToken();
            uat.setToken(appToken);
            Wrapper wrapper = new Wrapper(uat);
            userAppTokenService.updateByWhereSelective(entity, wrapper);
        } catch (Exception e) {
            logger.error("AppToken续命时发生异常", e);
        }
    }

    /**
     * 批量删除所有过期的AppToken
     * @return 成功清理apptoken的数量
     */
    public static int clearAppToken() {
//		long t1=AppTokenUtils.DEF_VALID_DATE*60L*60L*24L*1000L;//90天的毫秒数
//		long t2=System.currentTimeMillis();
//		Wrapper w=new Wrapper();
//		w.and("valid_date <",new Date(t2-t1));//超出有效期
//		UserAppTokenService userAppTokenService=SpringContextHolder.getBean(UserAppTokenService.class);
//		int count=userAppTokenService.deleteByWhere(w);
//		return count;

        long t1 = getDefValidDate() * 60L * 60L * 24L * 1000L;//90天的毫秒数
        long t2 = System.currentTimeMillis();
        Wrapper w = new Wrapper();
        w.and("valid_date <", new Date(t2 - t1));//超出有效期
        String sqlId = "selectByWhere";
        UserAppTokenService userAppTokenService = SpringContextHolder.getBean(UserAppTokenService.class);
        Cursor<UserAppToken> cursor = userAppTokenService.selectCursor(sqlId, w);
        Iterator<UserAppToken> iter = cursor.iterator();
        int count = 0;
        while (iter.hasNext()) {
            count++;
            UserAppToken entity = iter.next();//每一个超期的appToken
            String appToken = entity.getToken();
            //清空缓存
            AppTokenUtils.clearCache(appToken);

            //从库中删除
            UserAppToken uat = new UserAppToken();
            uat.setToken(appToken);
            userAppTokenService.deleteByWhere(new Wrapper(uat));
        }
        return count;
    }

    /*
     * 放入缓存
     * @param userMain
     * @param AppToken
     */
    private static void putCache(UserMain userMain, String appToken) {
        if (userMain != null && StringUtils.isNotBlank(appToken)) {
            HttpServletRequest request = R.getRequest();
            if (request != null) {
                request.setAttribute(AppTokenUtils.USER_SESSION_KEY, userMain);
            }
            ShopCache cache = SpringContextHolder.getBean(ShopCache.class);
            if (cache != null) {
                cache.put(AppTokenUtils.USER_SESSION_KEY + "." + appToken, userMain, 86400 * getDefValidDate());//缓存时间长度，单位秒,90天的秒数
            }
        }
    }

    /*
     * 清空缓存
     * @param AppToken
     */
    private static void clearCache(String appToken) {
        if (StringUtils.isBlank(appToken)) {
            return;
        }
        HttpServletRequest request = R.getRequest();
        if (request != null) {
            request.removeAttribute(AppTokenUtils.USER_SESSION_KEY);
        }
        ShopCache cache = SpringContextHolder.getBean(ShopCache.class);
        if (cache != null) {
            cache.del(AppTokenUtils.USER_SESSION_KEY + "." + appToken);
        }
    }

    /*
     * 缓存续命
     * @param AppToken
     */
    private static void touchCache(String appToken) {
        if (StringUtils.isBlank(appToken)) {
            return;
        }
        ShopCache cache = SpringContextHolder.getBean(ShopCache.class);
        if (cache != null) {
            cache.touch(AppTokenUtils.USER_SESSION_KEY + "." + appToken, 86400 * getDefValidDate());//缓存时间长度，单位秒,90天的秒数
        }
    }

    /*
     * 从缓存中查找
     */
    private static UserMain findCache(String appToken) {
        UserMain userMain = null;
        if (StringUtils.isBlank(appToken)) {
            return userMain;
        }

        HttpServletRequest request = R.getRequest();
        if (request != null) {
            //先尝试从Request中取出UserMain
            userMain = (UserMain) request.getAttribute(AppTokenUtils.USER_SESSION_KEY);
            if (userMain == null) {
                ShopCache cache = SpringContextHolder.getBean(ShopCache.class);
                if (cache != null) {
                    //再尝试从cache中取出UserMain
                    userMain = (UserMain) cache.get(AppTokenUtils.USER_SESSION_KEY + "." + appToken);
                }

            }
        }
        return userMain;
    }

    /*
     * 从Request中取AppToken
     * @return
     */
    private static String getAppTokenFromRequest() {
        //先从head中取出AppToken，移动端访问服务端时会携带AppToken
        HttpServletRequest request = R.getRequest();
        String appToken = request.getHeader(AppTokenUtils.APP_TOKEN_KEY);
        if (StringUtils.isBlank(appToken)) {
            //若没有再尝试从参数中取AppToken
            appToken = R.get(AppTokenUtils.APP_TOKEN_KEY);
        }
        if (StringUtils.isBlank(appToken)) {
            return null;//未携带AppToken
        } else {
            return appToken;
        }
    }

    /*
     * AppToken的默认有效期,单位:天
     * @return
     */
    private static int getDefValidDate() {
        try {
            String value = Global.getConfig("app.AppToken.def_valid_date");
            return Integer.valueOf(value);
        } catch (Exception e) {
            logger.error("Global.getConfig(\"app.AppToken.def_valid_date\")发生异常", e);
            return 90;
        }
    }

    /**
      * 获取请求标记
      * @return
     */
    public static String getRequestFlag() {
        String requestFlag = "";
        if (AppTokenUtils.isAppRequest()) {
            //APP
            requestFlag = AppTokenUtils.getAppTokenFromRequest();
        } else {
            //wap微信商城
            requestFlag = R.getSession().getId();
        }
        return requestFlag;
    }
}