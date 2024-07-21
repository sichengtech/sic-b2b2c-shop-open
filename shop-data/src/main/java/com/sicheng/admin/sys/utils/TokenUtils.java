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

import com.sicheng.admin.sys.dao.SysTokenDao;
import com.sicheng.admin.sys.entity.SysToken;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.IdGen;
import com.sicheng.common.web.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * <p>标题: TokenUtils</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年3月23日 下午5:28:05
 */
public class TokenUtils {

    @Autowired
    private static SysTokenDao sysTokenDao = SpringContextHolder.getBean(SysTokenDao.class);

    /**
     * 新增令牌表一条数据（并返回对象）
     *
     * @param userId 用户id
     * @param type   业务类型 10.公用上传 20.商家激活邮箱
     */
    public static SysToken generateToken(Long userId, String type) {
        String token = IdGen.uuid();
        SysToken sysToken = new SysToken();
        sysToken.setUserId(userId);
        sysToken.setToken(token);
        sysToken.setType(type);
        sysToken.setStatus("1");  //状态：0.失效1.有效
        sysToken.setCreateDate(new Date());
        sysTokenDao.insertSelective(sysToken);
        return sysToken;
    }

    /**
     * 验证token是否有效，如果有效改为失效
     *
     * @param token 令牌
     * @return
     */
    public static boolean verificationToken(String token) {
        return verificationToken(null, token);
    }

    /**
     * 验证token是否有效，如果有效改为失效
     *
     * @param userId 用户id
     * @param token  令牌
     * @return
     */
    public static boolean verificationToken(Long userId, String token) {
        boolean flag = false;
        //查询token是否有效
        SysToken st = new SysToken();
        st.setUserId(userId);
        st.setToken(token);
        st.setStatus("1"); //状态：0.失效1.有效
        List<SysToken> sysTokens = sysTokenDao.selectByWhere(null, new Wrapper(st));
        if (sysTokens.isEmpty()) {
            return flag;
        } else {
            SysToken sysToken = sysTokens.get(0);
            SysToken sysToken_update = new SysToken();
            sysToken_update.setTId(sysToken.getTId());
            sysToken_update.setStatus("0"); //状态：0.失效1.有效
            sysTokenDao.updateByIdSelective(sysToken_update);
            flag = true;
            return flag;
        }
    }

}
