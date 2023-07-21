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
 */
package com.sicheng.common.shiro;

import com.google.common.collect.Sets;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.Servlets;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;


/**
 * <p>标题: FdpCacheSessionDAO</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年7月2日 下午12:21:27
 */
public class FdpCacheSessionDAO extends EnterpriseCacheSessionDAO implements FdpSessionDAOI {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public FdpCacheSessionDAO() {
        super();
    }

    @Override
    protected void doUpdate(Session session) {
        //什么也不用做，父父类中都已做了
        super.doUpdate(session);
        logger.debug("update {}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        //什么也不用做，父父类中都已做了
        super.doDelete(session);
        logger.debug("delete {} ", session.getId());
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionid = super.doCreate(session);
        logger.debug("doCreate {}", sessionid);
        return sessionid;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        //什么也不用做，父父类中都已做了
        return super.doReadSession(sessionId);
    }

    /**
     * 读取缓存
     * 在request生命周期内，把session缓存在request中
     */
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        try {
            Session s = null;
            HttpServletRequest request = Servlets.getRequest();
            if (request != null) {
                s = (Session) request.getAttribute("session_" + sessionId);
            }
            if (s != null) {
                return s;
            }

            Session session = super.readSession(sessionId);
            logger.debug("readSession {}", sessionId);

            if (request != null && session != null) {
                request.setAttribute("session_" + sessionId, session);
            }

            return session;
        } catch (UnknownSessionException e) {
            return null;
        }
    }

    /**
     * 获取活动会话
     *
     * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions(boolean includeLeave) {
        return getActiveSessions(includeLeave, null, null);
    }

    /**
     * 获取活动会话
     *
     * @param includeLeave  是否包括离线（最后访问时间大于3分钟为离线会话）
     * @param principal     根据登录者对象获取活动会话
     * @param filterSession 不为空，则过滤掉（不包含）这个会话。
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
        // 如果包括离线，并无登录者条件。
        if (includeLeave && principal == null) {
            return getActiveSessions();
        }
        Set<Session> sessions = Sets.newHashSet();
        for (Session session : getActiveSessions()) {
            boolean isActiveSession = false;
            // 不包括离线并符合最后访问时间小于等于3分钟条件。
            if (includeLeave || DateUtils.pastMinutes(session.getLastAccessTime()) <= 3) {
                isActiveSession = true;
            }
            // 符合登录者条件。
            if (principal != null) {
                PrincipalCollection pc = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (principal.toString().equals(pc != null ? pc.getPrimaryPrincipal().toString() : StringUtils.EMPTY)) {
                    isActiveSession = true;
                }
            }
            // 过滤掉的SESSION
            if (filterSession != null && filterSession.getId().equals(session.getId())) {
                isActiveSession = false;
            }
            if (isActiveSession) {
                sessions.add(session);
            }
        }
        return sessions;
    }

}
