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
package com.sicheng.seller.user.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.store.entity.StoreAdminLog;
import com.sicheng.admin.store.entity.StoreMenu;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.seller.store.service.StoreAdminLogService;
import com.sicheng.seller.store.service.StoreMenuService;
import com.sicheng.sso.service.UserSellerService;
import com.sicheng.sso.utils.SsoUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: LogUtils</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zjl
 * @version 2017年2月27日 下午6:26:47
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
        if (SsoUtils.getPrincipal() != null) {
            UserSeller userSeller = SpringContextHolder.getBean(UserSellerService.class).selectById(SsoUtils.getPrincipal().getUId());
            if (userSeller != null && userSeller.getStoreId()!=null) {
                StoreAdminLog log = new StoreAdminLog();
                log.setTitle(title);//日志标题（操作菜单）
                log.setType(ex == null ? StoreAdminLog.TYPE_ACCESS : StoreAdminLog.TYPE_EXCEPTION);//日志类型
                log.setRemoteAddr(R.getRealIp());//操作用户的IP地址
                log.setUserAgent(request.getHeader("user-agent"));//操作用户代理信息
                log.setRequestUri(request.getRequestURI());//操作的uri
                log.setParams(request.getParameterMap());//操作提交的数据
                log.setMethod(request.getMethod());//操作的方式（提交方式）
                log.setOperatorId(SsoUtils.getPrincipal().getUId());//操作者id(登录的店铺会员id)
                User user = new User();
                user.setId(SsoUtils.getPrincipal().getUId());
                log.setCreateBy(user);//操作者id(登录的店铺会员id)
                log.setStoreId(userSeller.getStoreId());
                // 异步保存日志
                new SaveLogThread(log, handler, ex).start();
            }
        }
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread {

        private StoreAdminLog log;
        private Object handler;
        private Exception ex;

        public SaveLogThread(StoreAdminLog log, Object handler, Exception ex) {
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.ex = ex;
        }

        @Override
        public void run() {
            // 如果有异常，设置异常信息
            log.setException(ex == null ? null : ex.toString());
            //记录整个异常栈信息，因信息量太大所以注释了 2016-12-22 zhaolei
            //log.setException(Exceptions.getStackTraceAsString(ex));
            // 保存日志信息
            log.preInsert();
            SpringContextHolder.getBean(StoreAdminLogService.class).insertSelective(log);
        }
    }

    /**
     * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
     */
    public static String getMenuNamePath(String requestUri, String permission) {
        String href = StringUtils.substringAfter(requestUri, Global.getSellerPath());
        Map<String, String> menuMap = Maps.newHashMap();
        List<StoreMenu> menuList = SpringContextHolder.getBean(StoreMenuService.class).selectAll(new Wrapper());
        for (StoreMenu menu : menuList) {
            // 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
            String namePath = "";
            if (menu.getParentIds() != null) {
                List<String> namePathList = Lists.newArrayList();
                for (String id : StringUtils.split(menu.getParentIds(), ",")) {
                    if (StoreMenu.getRootId().equals(id)) {
                        continue; // 过滤跟节点
                    }
                    for (StoreMenu m : menuList) {
                        if (m.getId().equals(id)) {
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
                menuMap.put(menu.getHref(), namePath);
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
