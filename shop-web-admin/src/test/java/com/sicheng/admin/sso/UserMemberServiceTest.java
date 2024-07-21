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
package com.sicheng.admin.sso;

import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sso.service.UserMemberService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: 单元测试--会员（买家） Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017-04-25
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class UserMemberServiceTest {
    @Autowired
    private UserMemberService userMemberService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        UserMember userMember = userMemberService.selectById(0L);
        Assert.assertNull(userMember);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<UserMember> list = userMemberService.selectByIdIn(paramList);
        Assert.assertNotNull(list);
    }

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     *
     * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     *                  入参为null，或入参为new一个实体对象但无属性值，会查全表
     */
    @Test
    public void test_selectByWhere() {
        Page<UserMember> page = new Page<UserMember>();
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        page = userMemberService.selectByWhere(page, new Wrapper(entity));
        Assert.assertNotNull(page.getList());
    }

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     *
     * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     *                  入参为null，或入参为new一个实体对象但无属性值，会查全表
     */
    @Test
    public void test_selectByWhere2() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        List<UserMember> list = userMemberService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        List<UserMember> list = userMemberService.selectAll(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 插入数据
     * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
     *
     * @param entity
     * @return 受影响的行数
     */
    @Test
    public void test_insert() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userMemberService.insert(entity);
        Assert.assertEquals(1, rs);
    }

    /**
     * 插入,只把非空的值插入到对应的字段
     * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
     *
     * @param entity
     * @return 受影响的行数
     */
    @Test
    public void test_insertSelective() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userMemberService.insertSelective(entity);
        Assert.assertEquals(1, rs);
    }

    /**
     * 根据主键更新记录,更新除了主键的所有字段
     *
     * @param entity
     * @return 受影响的行数
     */
    @Test
    public void test_updateById() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userMemberService.updateById(entity);
        Assert.assertNotNull(rs);
    }

    /**
     * 根据条件更新记录,更新除了主键的所有字段
     *
     * @param entity    数据实体，用于存储数据，这些数据将被update到表中
     * @param condition 条件，用于where条件，找出符合条件的数据。入参为null，或入参为new一个实体对象但无属性值，执行sql时会报错，防止更新全表
     * @return 受影响的行数
     * <p>
     * 注意：condition.setDelFlag(null);把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
     */
    @Test
    public void test_updateByWhere() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        UserMember condition = new UserMember();
        condition.setId(2L);
        int rs = userMemberService.updateByWhere(entity, new Wrapper(condition));
        Assert.assertNotNull(rs);
    }

    /**
     * 根据主键更新记录,只把非空的值更到对应的字段
     *
     * @param entity 数据实体，用于存储数据，这些数据将被update到表中
     * @return 受影响的行数
     */
    @Test
    public void test_updateByIdSelective() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userMemberService.updateByIdSelective(entity);
        Assert.assertNotNull(rs);
    }

    /**
     * 根据条件更新记录,只把非空的值更到对应的字段
     *
     * @param entity    数据实体，用于存储数据，这些数据将被update到表中
     * @param condition 条件，用于where条件，找出符合条件的数据。入参为null，或入参为new一个实体对象但无属性值，执行sql时会报错，防止更新全表
     * @return 受影响的行数
     * <p>
     * 注意：condition.setDelFlag(null);把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
     */
    @Test
    public void test_updateByWhereSelective() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        UserMember condition = new UserMember();
        condition.setId(2L);
        int rs = userMemberService.updateByWhereSelective(entity, new Wrapper(condition));
        Assert.assertNotNull(rs);
    }

    /**
     * 删除数据
     * （如果有del_flag字段，就逻辑删除，更新del_flag字段为1表示删除）
     * （如果无del_flag字段，就物理删除）
     *
     * @param id 主键
     * @return 受影响的行数
     */
    @Test
    public void test_deleteById() {
        int rs = userMemberService.deleteById(1L);
        Assert.assertNotNull(rs);
    }

    /**
     * 删除数据（物理删除）
     *
     * @param condition 删除条件。入参为null，或入参为new一个实体对象但无属性值，执行SQL会报错，防止删除全表
     * @return 受影响的行数
     * <p>
     * 注意：data.setDelFlag(null);//把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
     */
    @Test
    public void test_deleteByWhere() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userMemberService.deleteByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 根据条件查询记录总数
     *
     * @param condition 可为null。或new一个实体对象，用于控制del_flag、控制distinct
     * @return 总行数
     * <p>
     * 注意：data.setDelFlag(null);//把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
     */
    @Test
    public void test_countByWhere() {
        UserMember entity = new UserMember();
        entity.setUId(1L);//Long 主键
        entity.setPoint("1");//String 积分（积分规则设置送的积分数）
        entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
        entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
        entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
        entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
        entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
        entity.setPaymentPassword("1");//String 支付密码
        entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
        entity.setRealName("1");//String 真实姓名
        entity.setHeadPicPath("1");//String 会员头像path
        entity.setSex("1");//String 性别(1男、2女、3保密)
        entity.setBirthday(new Date());//java.util.Date 生日
        entity.setPostcode("1");//String 邮编
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setQq("1");//String qq号
        entity.setMicroblog("1");//String 微博
        entity.setWeChat("1");//String 微信
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userMemberService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        UserMember p1 = new UserMember();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        UserMember p2 = new UserMember();//p2的属性全是空值

        userMemberService.selectAll(null);
        userMemberService.selectAll(new Wrapper(p2));
        userMemberService.countByWhere(null);
        userMemberService.countByWhere(new Wrapper(p2));

        try {
            userMemberService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userMemberService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userMemberService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userMemberService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userMemberService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userMemberService.deleteByWhere(null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }
    }

    /**
     * 批量插入
     */
    @Test
    public void test_insertBatch() {
        List<UserMember> list = new ArrayList<UserMember>();
        for (int i = 0; i < 10; i++) {
            UserMember entity = new UserMember();
            entity.setUId(1L);//Long 主键
            entity.setPoint("1");//String 积分（积分规则设置送的积分数）
            entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
            entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
            entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
            entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
            entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
            entity.setPaymentPassword("1");//String 支付密码
            entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
            entity.setRealName("1");//String 真实姓名
            entity.setHeadPicPath("1");//String 会员头像path
            entity.setSex("1");//String 性别(1男、2女、3保密)
            entity.setBirthday(new Date());//java.util.Date 生日
            entity.setPostcode("1");//String 邮编
            entity.setCountryId(1L);//Long 国家(关联地区表)
            entity.setCountryName("1");//String 国家名字
            entity.setProvinceId(1L);//Long 省(关联地区表)
            entity.setProvinceName("1");//String 省名字
            entity.setCityId(1L);//Long 市(关联地区表)
            entity.setCityName("1");//String 市名字
            entity.setDistrictId(1L);//Long 县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 详细地址
            entity.setQq("1");//String qq号
            entity.setMicroblog("1");//String 微博
            entity.setWeChat("1");//String 微信
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = userMemberService.insertBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_insertSelectiveBatch() {
        List<UserMember> list = new ArrayList<UserMember>();
        for (int i = 0; i < 10; i++) {
            UserMember entity = new UserMember();
            entity.setUId(1L);//Long 主键
            entity.setPoint("1");//String 积分（积分规则设置送的积分数）
            entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
            entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
            entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
            entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
            entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
            entity.setPaymentPassword("1");//String 支付密码
            entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
            entity.setRealName("1");//String 真实姓名
            entity.setHeadPicPath("1");//String 会员头像path
            entity.setSex("1");//String 性别(1男、2女、3保密)
            entity.setBirthday(new Date());//java.util.Date 生日
            entity.setPostcode("1");//String 邮编
            entity.setCountryId(1L);//Long 国家(关联地区表)
            entity.setCountryName("1");//String 国家名字
            entity.setProvinceId(1L);//Long 省(关联地区表)
            entity.setProvinceName("1");//String 省名字
            entity.setCityId(1L);//Long 市(关联地区表)
            entity.setCityName("1");//String 市名字
            entity.setDistrictId(1L);//Long 县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 详细地址
            entity.setQq("1");//String qq号
            entity.setMicroblog("1");//String 微博
            entity.setWeChat("1");//String 微信
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = userMemberService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<UserMember> list = new ArrayList<UserMember>();
        for (int i = 0; i < 10; i++) {
            UserMember entity = new UserMember();
            entity.setUId(1L);//Long 主键
            entity.setPoint("1");//String 积分（积分规则设置送的积分数）
            entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
            entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
            entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
            entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
            entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
            entity.setPaymentPassword("1");//String 支付密码
            entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
            entity.setRealName("1");//String 真实姓名
            entity.setHeadPicPath("1");//String 会员头像path
            entity.setSex("1");//String 性别(1男、2女、3保密)
            entity.setBirthday(new Date());//java.util.Date 生日
            entity.setPostcode("1");//String 邮编
            entity.setCountryId(1L);//Long 国家(关联地区表)
            entity.setCountryName("1");//String 国家名字
            entity.setProvinceId(1L);//Long 省(关联地区表)
            entity.setProvinceName("1");//String 省名字
            entity.setCityId(1L);//Long 市(关联地区表)
            entity.setCityName("1");//String 市名字
            entity.setDistrictId(1L);//Long 县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 详细地址
            entity.setQq("1");//String qq号
            entity.setMicroblog("1");//String 微博
            entity.setWeChat("1");//String 微信
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = userMemberService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<UserMember> list = new ArrayList<UserMember>();
        for (int i = 0; i < 10; i++) {
            UserMember entity = new UserMember();
            entity.setUId(1L);//Long 主键
            entity.setPoint("1");//String 积分（积分规则设置送的积分数）
            entity.setIsBuy("1");//String 是否允许购买(0不允许、1允许)
            entity.setNotBuyDateStart(new Date());//java.util.Date 禁止购买日期（开始时间）
            entity.setNotBuyDateEnd(new Date());//java.util.Date 禁止购买日期（结束时间）(需要有定时程序来检查并解锁)
            entity.setBalance(new BigDecimal("0.1"));//BigDecimal 预存款，账户余额
            entity.setFrozenMoney(new BigDecimal("0.1"));//BigDecimal 冻结金额
            entity.setPaymentPassword("1");//String 支付密码
            entity.setMemberTagId(1L);//Long 会员标签（给会员打标）(关联member_tag表)
            entity.setRealName("1");//String 真实姓名
            entity.setHeadPicPath("1");//String 会员头像path
            entity.setSex("1");//String 性别(1男、2女、3保密)
            entity.setBirthday(new Date());//java.util.Date 生日
            entity.setPostcode("1");//String 邮编
            entity.setCountryId(1L);//Long 国家(关联地区表)
            entity.setCountryName("1");//String 国家名字
            entity.setProvinceId(1L);//Long 省(关联地区表)
            entity.setProvinceName("1");//String 省名字
            entity.setCityId(1L);//Long 市(关联地区表)
            entity.setCityName("1");//String 市名字
            entity.setDistrictId(1L);//Long 县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 详细地址
            entity.setQq("1");//String qq号
            entity.setMicroblog("1");//String 微博
            entity.setWeChat("1");//String 微信
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = userMemberService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        UserMember entity = new UserMember();
        entity.setId(1L);
        userMemberService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = userMemberService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}