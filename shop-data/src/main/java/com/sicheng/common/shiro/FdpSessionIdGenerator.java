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

import com.sicheng.common.utils.IdGen;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * <p>标题: FdpSessionIdGenerator是shiro专用的session id生成器</p>
 * <p>描述: 一般使用uuid来当做session id</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年7月4日 上午9:07:18
 */
public class FdpSessionIdGenerator implements SessionIdGenerator {

    /**
     * <p>专为shiro生成session id的方法，使用uuid </p>
     *
     * @param session
     * @return
     * @see org.apache.shiro.session.mgt.eis.SessionIdGenerator#generateId(org.apache.shiro.session.Session)
     */
    @Override
    public Serializable generateId(Session session) {
        return IdGen.uuid();
    }

}
