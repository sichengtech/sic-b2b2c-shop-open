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
package com.sicheng.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.sso.dao.UserMemberDao;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import com.sicheng.sso.utils.SsoUtils;

/**
 * 会员（买家） Service
 *
 * @author 蔡龙
 * @version 2017-04-25
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserMemberService extends CrudService<UserMemberDao, UserMember> {

    //请在这里编写业务逻辑

    //父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Lazy
    @Autowired
    private UserMainService userMainService;

    /**
     * 修改用户信息
     *
     * @param userMain
     * @param userMember
     */
    @Transactional(rollbackFor = Exception.class)
    public String update(String oldLoginName, UserMain userMain, UserMember userMember, Long loginNameEditCount) {
//        UserMainService userMainService= SpringContextHolder.getBean(UserMainService.class);
        String rab = FYUtils.fyParams("修改用户信息成功！");
        String today = DateUtils.getYear() + "-" + DateUtils.getMonth();
        Long uId = SsoUtils.getUserMain().getUId();//属主检查
        userMain.setUId(uId);

        //查询上次修改日期
        UserMain buffer = userMainService.selectById(uId);
        if(StringUtils.isNotBlank(buffer.getModifyDate())){
            userMain.setModifyDate(buffer.getModifyDate());
        }

        if (!oldLoginName.equals(userMain.getLoginName())) {
            //修改用户总表
            int modifyCount = SsoUtils.getUserMain().getModifyCount();
            //0代表可以随意修改
            if (loginNameEditCount == 0) {
                modifyCount += 1;
                userMain.setModifyCount(modifyCount);
                userMain.setModifyDate(today);
                userMainService.updateByIdSelective(userMain);
            } else {
                if (StringUtils.isBlank(userMain.getModifyDate())) {
                    //当前年月和修改年月不一致
                    userMain.setModifyCount(1);
                    userMain.setModifyDate(today);
                    userMainService.updateByIdSelective(userMain);
                } else {
                    //判断用户名变更年月字段是否为空
                    if (today.equals(userMain.getModifyDate())) {
                        //当前年月和修改年月一致
                        if (modifyCount < loginNameEditCount) {
                            //当前的修改次数少于用户名修改次数
                            modifyCount += 1;
                            userMain.setModifyCount(modifyCount);
                            userMain.setModifyDate(today);
                            userMainService.updateByIdSelective(userMain);
                        } else {
                            //当前的修改次数大于等于用户名修改次数
                            rab = FYUtils.fyParams("修改失败,用户名在当前月份修改达到上限");
                        }
                    } else {
                        //当前年月和修改年月不一致
                        userMain.setModifyCount(1);
                        userMain.setModifyDate(today);
                        userMainService.updateByIdSelective(userMain);
                    }
                }
            }
        }
        userMember.setUId(uId);
        dao.updateByIdSelective(userMember);
        return rab;
    }

}