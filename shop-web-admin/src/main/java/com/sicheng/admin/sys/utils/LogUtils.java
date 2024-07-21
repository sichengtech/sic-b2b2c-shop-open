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
package com.sicheng.admin.sys.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sicheng.admin.sys.entity.Log;
import com.sicheng.admin.sys.entity.Menu;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.admin.sys.service.LogService;

import com.sicheng.admin.sys.service.MenuService;
import com.sicheng.admin.sys.shiro.SystemAuthorizingRealm.Principal;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日志工具类
 *
 * @author zhaolei
 * @version 2014-11-7
 */
public class LogUtils {

    public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, String title) {
        saveLog(request, null, null, title);
    }

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title) {
//		User user = UserUtils.getUser();
//		if (user != null && user.getId() != null){
        Log log = new Log();
        log.setTitle(title);
        log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
        log.setRemoteAddr(R.getRealIp());
        log.setUserAgent(request.getHeader("user-agent"));
        log.setRequestUri(request.getRequestURI());
        log.setParams(request.getParameterMap());
        log.setMethod(request.getMethod());
        // 异步保存日志
        new SaveLogThread(log, handler, ex).start();
//		}
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread {

        private Log log;
        private Object handler;
        private Exception ex;

        public SaveLogThread(Log log, Object handler, Exception ex) {
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.ex = ex;
        }

        @Override
        public void run() {
            // 如果有异常，设置异常信息
            log.setException(ex == null ? null : ex.toString());
            //记录整个异常栈信息，因信息量太大所以注释了 2016-12-22 赵磊
            //log.setException(Exceptions.getStackTraceAsString(ex));
            // 保存日志信息
            User user = new User();
            Principal principal = UserUtils.getPrincipal();
            if (principal != null) {
                user.setId(UserUtils.getPrincipal().getId());
                log.setCreateBy(user);
                log.setUpdateBy(user);
                log.setCreateDate(new Date());
                log.setUpdateDate(new Date());
                SpringContextHolder.getBean(LogService.class).insertSelective(log);
            }
        }
    }

    /**
     * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
     */
    public static String getMenuNamePath(String requestUri, String permission) {
        String href = StringUtils.substringAfter(requestUri, Global.getAdminPath());
        Map<String, String> menuMap = Maps.newHashMap();
        List<Menu> menuList = SpringContextHolder.getBean(MenuService.class).selectAll(new Wrapper());
        for (Menu menu : menuList) {
            // 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
            String namePath = "";
            if (menu.getParentIds() != null) {
                List<String> namePathList = Lists.newArrayList();
                for (String id : StringUtils.split(menu.getParentIds(), ",")) {
                    if (Menu.getRootId().equals(Long.valueOf(id))) {
                        continue; // 过滤根节点
                    }
                    for (Menu m : menuList) {
                        if (m.getId().equals(Long.valueOf(id))) {
                            namePathList.add(m.getName());
                            break;
                        }
                    }
                }
                namePathList.add(menu.getName());
                namePath = StringUtils.join(namePathList, "-");
            }
            // 设置菜单名称路径
            if (StringUtils.isNotBlank(menu.getHref())) {
                menuMap.put("/admin" + menu.getHref(), namePath);
            } else if (StringUtils.isNotBlank(menu.getPermission())) {
                for (String p : StringUtils.split(menu.getPermission())) {
                    menuMap.put(p, namePath);
                }
            }

        }
        String menuNamePath = menuMap.get(href);
        if (menuNamePath == null) {
            for (String p : StringUtils.split(permission)) {
                menuNamePath = menuMap.get(p);
                if (StringUtils.isNotBlank(menuNamePath)) {
                    break;
                }
            }
            if (menuNamePath == null) {
                return "";
            }
        }
        return menuNamePath;
    }


}
