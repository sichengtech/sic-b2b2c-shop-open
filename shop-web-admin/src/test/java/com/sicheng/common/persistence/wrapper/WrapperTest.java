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
package com.sicheng.common.persistence.wrapper;

import com.sicheng.admin.product.entity.ProductSpu;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * <p>标题: WrapperTest</p>
 * <p>描述: Wrapper的单元测试</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年1月31日 下午10:25:28
 */
public class WrapperTest {

    /**
     * 各种运算符测试   (使用了隐式占位符)
     * =、<>、is null、is not null、>、>=、<、<=、like %?%、like _?_、
     * in、not in、between、not between
     */
    @Test
    public void test_operator_a() {
        Wrapper wrapper = new Wrapper();
        wrapper
                .and("category_id =", 1)//=等于
                .and("status <>", 0) //<>不等于
                .and("bak6 is null")//is null 是空
                .or("bak7 is not null")//is not null 非空

                .andNew("market_price >", 0) //大于
                .and("market_price >=", 0) //大于等于
                .and("weight <", 100) //<>小于
                .and("weight <=", 200) //<>小于等于

                .orNew("name like", "%手机%") //like
                .and("name like", "%手机")//左like
                .and("name like", "手机%")//右like
                .and("name like", "_手机") //like 单字符匹配

                .orNew("status in", new int[]{1, 2, 3, 4, 5}) //in 包含
                .and("status not in", Arrays.asList(-1, 0)) //not in 不包含

                .orNew("market_price between", 0, 1000) //between 在范围内
                .or("market_price not between", 0, 1) //not between 不在范围内
        ;
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        Map<String, Object> paramMap = wrapper.getValueMap();
        System.out.print("占位符参数:");
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            System.out.print(key + "=" + value + ",");
        }
        System.out.println();
        String rs = "AND (category_id = #{w.v[p0]} AND status <> #{w.v[p1]} AND bak6 is null OR bak7 is not null) \n" +
                "AND (market_price > #{w.v[p2]} AND market_price >= #{w.v[p3]} AND weight < #{w.v[p4]} AND weight <= #{w.v[p5]}) \n" +
                "OR (name like #{w.v[p6]} AND name like #{w.v[p7]} AND name like #{w.v[p8]} AND name like #{w.v[p9]}) \n" +
                "OR (status in(#{w.v[p10]},#{w.v[p11]},#{w.v[p12]},#{w.v[p13]},#{w.v[p14]}) AND status not in(#{w.v[p15]},#{w.v[p16]})) \n" +
                "OR (market_price between #{w.v[p17]} AND #{w.v[p18]} OR market_price not between #{w.v[p19]} AND #{w.v[p20]})";
        Assert.assertEquals(rs, wrapper.getOutputSqlAll());
    }

    /**
     * 测试运算符(使用Wrapper{0}{1}占位符，是显式占位符)
     * =、<>、is null、is not null、>、>=、<、<=、like %?%、like _?_、
     * in、not in、between、not between
     */
    @Test
    public void test_operator_b() {
        Wrapper wrapper = new Wrapper();
        wrapper
                .and("category_id = {0}", 1)//=等于
                .and("status <> {0}", 0) //<>不等于
                .and("bak6 is null")//is null 是空
                .or("bak7 is not null")//is not null 非空

                .andNew("market_price > {0}", 0) //大于
                .and("market_price >= {0}", 0) //大于等于
                .and("weight < {0}", 100) //<>小于
                .and("weight <= {0}", 200) //<>小于等于

                .orNew("name like {0}", "%手机%") //like
                .and("name like {0}", "%手机")//左like
                .and("name like {0}", "手机%")//右like
                .and("name like {0}", "_手机") //like 单字符匹配

                .orNew("status in {0}", new int[]{1, 2, 3, 4, 5}) //in 包含
                .and("status not in {0}", Arrays.asList(-1, 0)) //not in 不包含

                .orNew("market_price between {0} and {1}", 0, 1000) //between 在范围内
                .or("market_price not between {0} and {1}", 0, 1) //not between 不在范围内
        ;

        String rs = "AND (category_id = #{w.v[p0]} AND status <> #{w.v[p1]} AND bak6 is null OR bak7 is not null) \n" +
                "AND (market_price > #{w.v[p2]} AND market_price >= #{w.v[p3]} AND weight < #{w.v[p4]} AND weight <= #{w.v[p5]}) \n" +
                "OR (name like #{w.v[p6]} AND name like #{w.v[p7]} AND name like #{w.v[p8]} AND name like #{w.v[p9]}) \n" +
                "OR (status in (#{w.v[p10]},#{w.v[p11]},#{w.v[p12]},#{w.v[p13]},#{w.v[p14]}) AND status not in (#{w.v[p15]},#{w.v[p16]})) \n" +
                "OR (market_price between #{w.v[p17]} and #{w.v[p18]} OR market_price not between #{w.v[p19]} and #{w.v[p20]})";
        Assert.assertEquals(rs, wrapper.getOutputSqlAll());
    }

    @Test
    public void test_日期_函数() {
        Wrapper wrapper = new Wrapper();
        wrapper
                .and("create_date between", new Date(1L), new Date()) //按日期查询，未使用占位符
                .and("create_date between {0} and {1}", new Date(1L), new Date()) //按日期查询，使用占位符
                .orNew("lower(name) like lower({0})", "%HuaWei%") //lower()函数，使用lower()把HuaWei转为小写
                .or("lower(name) like", "%huawei%") //lower()函数，开发人员保证huawei是全小写的，与数据库中的值对比
        ;
        String rs = "AND (create_date between #{w.v[p0]} AND #{w.v[p1]} AND create_date between #{w.v[p2]} and #{w.v[p3]}) \n" +
                "OR (lower(name) like lower(#{w.v[p4]}) OR lower(name) like #{w.v[p5]})";
        Assert.assertEquals(rs, wrapper.getOutputSqlAll());
    }

    /**
     * and 测试
     */
    @Test
    public void test_and() {
        Wrapper wrapper = new Wrapper();
        wrapper.and("p_id=", 301).and("name=", "1");
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        Map<String, Object> paramMap = wrapper.getValueMap();
        System.out.print("占位符参数:");
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            System.out.print(key + "=" + value + ",");
        }
        System.out.println();
        String target = "AND (p_id= #{w.v[p0]} AND name= #{w.v[p1]})";
        Assert.assertEquals(target, sqlWhere);
    }

    /**
     * or 测试
     */
    @Test
    public void test_or() {
        Wrapper wrapper = new Wrapper();
        wrapper.and("p_id=", 301)
                .or("name=", "1");
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        Map<String, Object> paramMap = wrapper.getValueMap();
        System.out.print("占位符参数:");
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            System.out.print(key + "=" + value + ",");
        }
        System.out.println();
        String target = "AND (p_id= #{w.v[p0]} OR name= #{w.v[p1]})";
        Assert.assertEquals(target, sqlWhere);
    }

    /**
     * 多个 and or测试 ,用括号确定运算优先级
     */
    @Test
    public void test_and_or() {
        Wrapper wrapper = new Wrapper();
        wrapper.and("p_id=", 301).or("name=", "1")
                .orNew("brand_id=", 100).and("util=", 20)
                .andNew("status=", 5)
        ;
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        Map<String, Object> paramMap = wrapper.getValueMap();
        System.out.print("占位符参数:");
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            System.out.print(key + "=" + value + ",");
        }
        System.out.println();
        String target = "AND (p_id= #{w.v[p0]} OR name= #{w.v[p1]}) \n" +
                "OR (brand_id= #{w.v[p2]} AND util= #{w.v[p3]}) \n" +
                "AND (status= #{w.v[p4]})";
        Assert.assertEquals(target, sqlWhere);
    }

    /**
     * 测试order by
     */
    @Test
    public void test_orderby() {
        Wrapper wrapper = new Wrapper();
        wrapper.and("name=", "zhangsan").orderBy("id,name ASC");
        wrapper.setDistinct(true);
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        Map<String, Object> paramMap = wrapper.getValueMap();
        System.out.print("占位符参数:");
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            System.out.print(key + "=" + value + ",");
        }
        System.out.println();
        String target = "AND (name= #{w.v[p0]})\nORDER BY id,name ASC";
        Assert.assertEquals(target, sqlWhere);
    }

    /**
     * 测试group By,having
     */
    @Test
    public void test_groupby_having() {
        Wrapper wrapper = new Wrapper();
        wrapper.groupBy("brand_id,area").having("id =", 22).and().having("password is not null");
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        Map<String, Object> paramMap = wrapper.getValueMap();
        System.out.print("占位符参数:");
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            System.out.print(key + "=" + value + ",");
        }
        System.out.println();
        String target = "GROUP BY brand_id,area\n" +
                "HAVING (id = #{w.v[p0]} AND password is not null)";
        Assert.assertEquals(target, sqlWhere);
    }

    /**
     * 测试exists
     */
    @Test
    public void test_exists() {
        Wrapper wrapper = new Wrapper();
        wrapper.exists("select 1 form table where id=a.p_id");
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        Map<String, Object> paramMap = wrapper.getValueMap();
        System.out.print("占位符参数:");
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            System.out.print(key + "=" + value + ",");
        }
        System.out.println();
        String target = "AND ( EXISTS (select 1 form table where id=a.p_id))";
        Assert.assertEquals(target, sqlWhere);
    }

    /**
     * 测试 not exists
     */
    @Test
    public void test_notexists() {
        Wrapper wrapper = new Wrapper();
        wrapper.notExists("select 1 form table where id=a.p_id");
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        Map<String, Object> paramMap = wrapper.getValueMap();
        System.out.print("占位符参数:");
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            System.out.print(key + "=" + value + ",");
        }
        System.out.println();
        String target = "AND ( NOT EXISTS (select 1 form table where id=a.p_id))";
        Assert.assertEquals(target, sqlWhere);
    }

    @Test
    public void test_safe_1() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id=" + id);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_2() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id <> " + id);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_3() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id != " + id);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_4() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id>" + id);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_5() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id>=" + id);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_6() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id<" + id);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_7() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id<=" + id);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_8() {
        try {
            String id = "%张%";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("name like " + id);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_9() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id in (" + id + ")");
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_10() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id=" + id);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_11() {
        try {
            String id = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id ont in (" + id + ")");
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_12() {
        try {
            String value1 = "100";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            String value2 = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id between " + value1 + " and " + value2);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void test_safe_13() {
        try {
            String value1 = "100";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            String value2 = "200";//模拟由外部来自互联网的用户通过表单输入值，此值可以会带有风验
            Wrapper wrapper = new Wrapper();
            wrapper.and("p_id not between " + value1 + " and " + value2);
            wrapper.getOutputSqlAll();
            Assert.assertEquals(true, false);//禁止拼接SQL，抛出异常就满意了
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

    /**
     * 拼接 where id in(...) ,in的内容为空的情况测试
     * List的长度为0，这个的SQL是无法运行的，需要特殊处理，在保证sql原意的同时保证能运行
     */
    @Test
    public void test_WrapperNull_5() {
        ProductSpu entity = null;
        Wrapper wrapper = new Wrapper(entity);
        wrapper.and("status in", new int[]{}); //in 包含
        wrapper.or("id in", Arrays.asList());
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        System.out.println();
        String target = "AND (1=2 OR 1=2)";
        Assert.assertEquals(target, sqlWhere);
    }

    /**
     * 拼接 where id in(...) ,in的内容为空的情况测试   (使用了显示占位符)
     * List的长度为0，这个的SQL是无法运行的，需要特殊处理，在保证sql原意的同时保证能运行
     */
    @Test
    public void test_WrapperNull_6() {
        ProductSpu entity = null;
        Wrapper wrapper = new Wrapper(entity);
        wrapper.and("status in {0}", new int[]{});
        wrapper.or("id in {0}", Arrays.asList());
        String sqlWhere = wrapper.getOutputSqlAll();
        System.out.println("sqlWhere:" + sqlWhere);
        System.out.println();
        String target = "AND (1=2 OR 1=2)";
        Assert.assertEquals(target, sqlWhere);
    }

}
