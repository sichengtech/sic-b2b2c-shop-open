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
 * SiC B2B2C Shop 使用 AGPLv3 开源，请遵守 AGPLv3 的相关条款，或者联系作者获取商业授权。
 */
package com.sicheng.common.persistence.wrapper;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>标题: SqlSageTest</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年2月1日 下午9:47:07
 */
public class SqlSafeTest {

    /**
     * 针对exists语句的测试
     */
    @Test
    public void test_securityCheck() {
        String sql = "exists(select 1 form table where id=a.p_id)";
        SqlConditionElement element = new SqlConditionElement(sql);
        boolean bl = SqlSafe.securityCheck(element);
        Assert.assertEquals(bl, true);
    }

    /**
     * 模拟由外部来自互联网的用户通过表单输入值，此值带有风验
     * 应被检查出来
     */
    @Test
    public void test_checkSqlInjection_1() {
        String value = "'- or 1=1 --";
        String sql = "where name='" + value + "'";
        boolean bl = SqlSafe.checkSqlInjection(sql);
        Assert.assertEquals(bl, true);
    }

    @Test
    public void test_all() {
        String[] arr = {
                "and 1=1 and 1=2",//判断有无注入点
                "1=1 #",//#是mysql的注释符号之一
                "and 0<>(select count(*) from admin)--",//判断是否存在admin这张表
                "and 0<(select count(*) from admin)",//猜帐号数目 如果遇到0< 返回正确页面 1<返回错误页面说明帐号数目就是1个
                "and 1=(select count(*) from admin where len(id)>0)--",//猜解字段名称 在len( ) 括号里面加上我们想到的字段名称
                "and 1=(select @@VERSION)",//看windows服务器打的补丁,出错了打了SP4补丁
                "and 1=(select count(*) from master.dbo.sysobjects where xtype = 'x' and name = 'xp_cmdshell')--",//是否存在xp_cmdshell
        };
        for (int i = 0; i < arr.length; i++) {
            String sql = arr[i];
            boolean bl = SqlSafe.checkSqlInjection(sql);
            Assert.assertEquals(bl, true);
        }
    }
}
