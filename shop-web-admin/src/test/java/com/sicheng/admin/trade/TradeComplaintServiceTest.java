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
package com.sicheng.admin.trade;

import com.sicheng.admin.trade.entity.TradeComplaint;
import com.sicheng.admin.trade.service.TradeComplaintService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>标题: 单元测试--投诉 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 范秀秀
 * @version 2017-01-10
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class TradeComplaintServiceTest {
    @Autowired
    private TradeComplaintService tradeComplaintService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        TradeComplaint tradeComplaint = tradeComplaintService.selectById(0L);
        Assert.assertNull(tradeComplaint);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<TradeComplaint> list = tradeComplaintService.selectByIdIn(paramList);
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
        Page<TradeComplaint> page = new Page<TradeComplaint>();
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("1");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        page = tradeComplaintService.selectByWhere(page, new Wrapper(entity));
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
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("1");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        List<TradeComplaint> list = tradeComplaintService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("1");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        List<TradeComplaint> list = tradeComplaintService.selectAll(new Wrapper(entity));
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
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("0");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("10");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        int rs = tradeComplaintService.insert(entity);
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
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("1");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        int rs = tradeComplaintService.insertSelective(entity);
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
        TradeComplaint entity = new TradeComplaint();
        entity.setId(1L);
        int rs = tradeComplaintService.updateById(entity);
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
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("1");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        TradeComplaint condition = new TradeComplaint();
        condition.setId(2L);
        int rs = tradeComplaintService.updateByWhere(entity, new Wrapper(condition));
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
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("1");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        int rs = tradeComplaintService.updateByIdSelective(entity);
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
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("1");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        TradeComplaint condition = new TradeComplaint();
        condition.setId(2L);
        int rs = tradeComplaintService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = tradeComplaintService.deleteById(1L);
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
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("1");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        int rs = tradeComplaintService.deleteByWhere(new Wrapper(entity));
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
        TradeComplaint entity = new TradeComplaint();
        entity.setComplaintId(1L);//Long 主键
        entity.setUId(1L);//Long 会员id(会员表id)
        entity.setStoreId(1L);//Long 关联(店铺表)
        entity.setPId(1L);//Long 产品id(产品表id)
        entity.setSkuId(1L);//Long sku id(产品sku表id)
        entity.setOrderId(1L);//Long 订单id(订单表id)
        entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
        entity.setContent("1");//String 投诉(申诉)内容
        entity.setIp("1");//String ip
        entity.setType("0");//String 类型，0投诉、1申诉
        entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
        entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        entity.setCreateDate(new Date());//java.util.Date 创建时间
        entity.setUpdateDate(new Date());//java.util.Date 更新时间
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5
        entity.setBak6("1");//String 备用字段6
        entity.setBak7("1");//String 备用字段7
        entity.setBak8("1");//String 备用字段8
        entity.setBak9("1");//String 备用字段9
        entity.setBak10("1");//String 备用字段10
        entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)

        int rs = tradeComplaintService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        TradeComplaint p1 = new TradeComplaint();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        TradeComplaint p2 = new TradeComplaint();//p2的属性全是空值

        tradeComplaintService.selectAll(null);
        tradeComplaintService.selectAll(new Wrapper(p2));
        tradeComplaintService.countByWhere(null);
        tradeComplaintService.countByWhere(new Wrapper(p2));

        try {
            tradeComplaintService.updateByWhere(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeComplaintService.updateByWhereSelective(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeComplaintService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeComplaintService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeComplaintService.deleteByWhere(new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            tradeComplaintService.deleteByWhere(null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

    }

    @Test
    public void t() {
        for (int i = 0; i < 20; i++) {
            test_insert();
        }
    }

    /**
     * 批量插入
     */
    @Test
    public void test_insertBatch() {
        List<TradeComplaint> list = new ArrayList<TradeComplaint>();
        for (int i = 0; i < 10; i++) {
            TradeComplaint entity = new TradeComplaint();
            entity.setComplaintId(1L);//Long 主键
            entity.setUId(1L);//Long 会员id(会员表id)
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setPId(1L);//Long 产品id(产品表id)
            entity.setSkuId(1L);//Long sku id(产品sku表id)
            entity.setOrderId(1L);//Long 订单id(订单表id)
            entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
            entity.setContent("1");//String 投诉(申诉)内容
            entity.setIp("1");//String ip
            entity.setType("1");//String 类型，0投诉、1申诉
            entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
            entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setBak6("1");//String 备用字段6
            entity.setBak7("1");//String 备用字段7
            entity.setBak8("1");//String 备用字段8
            entity.setBak9("1");//String 备用字段9
            entity.setBak10("1");//String 备用字段10
            entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)
            entity.setSystemHandleTime(new Date());//java.util.Date 平台处理时间
            entity.setSystemHandleHandelOpinion("1");//String 平台处理意见
            entity.setAdminId(1L);//Long 处理人(管理员表id)

            list.add(entity);
        }
        boolean rs = tradeComplaintService.insertBatch(list);
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
        List<TradeComplaint> list = new ArrayList<TradeComplaint>();
        for (int i = 0; i < 10; i++) {
            TradeComplaint entity = new TradeComplaint();
            entity.setComplaintId(1L);//Long 主键
            entity.setUId(1L);//Long 会员id(会员表id)
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setPId(1L);//Long 产品id(产品表id)
            entity.setSkuId(1L);//Long sku id(产品sku表id)
            entity.setOrderId(1L);//Long 订单id(订单表id)
            entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
            entity.setContent("1");//String 投诉(申诉)内容
            entity.setIp("1");//String ip
            entity.setType("1");//String 类型，0投诉、1申诉
            entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
            entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setBak6("1");//String 备用字段6
            entity.setBak7("1");//String 备用字段7
            entity.setBak8("1");//String 备用字段8
            entity.setBak9("1");//String 备用字段9
            entity.setBak10("1");//String 备用字段10
            entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)
            entity.setSystemHandleTime(new Date());//java.util.Date 平台处理时间
            entity.setSystemHandleHandelOpinion("1");//String 平台处理意见
            entity.setAdminId(1L);//Long 处理人(管理员表id)

            list.add(entity);
        }
        boolean rs = tradeComplaintService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<TradeComplaint> list = new ArrayList<TradeComplaint>();
        for (int i = 0; i < 10; i++) {
            TradeComplaint entity = new TradeComplaint();
            entity.setComplaintId(1L);//Long 主键
            entity.setUId(1L);//Long 会员id(会员表id)
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setPId(1L);//Long 产品id(产品表id)
            entity.setSkuId(1L);//Long sku id(产品sku表id)
            entity.setOrderId(1L);//Long 订单id(订单表id)
            entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
            entity.setContent("1");//String 投诉(申诉)内容
            entity.setIp("1");//String ip
            entity.setType("1");//String 类型，0投诉、1申诉
            entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
            entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setBak6("1");//String 备用字段6
            entity.setBak7("1");//String 备用字段7
            entity.setBak8("1");//String 备用字段8
            entity.setBak9("1");//String 备用字段9
            entity.setBak10("1");//String 备用字段10
            entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)
            entity.setSystemHandleTime(new Date());//java.util.Date 平台处理时间
            entity.setSystemHandleHandelOpinion("1");//String 平台处理意见
            entity.setAdminId(1L);//Long 处理人(管理员表id)

            list.add(entity);
        }
        boolean rs = tradeComplaintService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<TradeComplaint> list = new ArrayList<TradeComplaint>();
        for (int i = 0; i < 10; i++) {
            TradeComplaint entity = new TradeComplaint();
            entity.setComplaintId(1L);//Long 主键
            entity.setUId(1L);//Long 会员id(会员表id)
            entity.setStoreId(1L);//Long 关联(店铺表)
            entity.setPId(1L);//Long 产品id(产品表id)
            entity.setSkuId(1L);//Long sku id(产品sku表id)
            entity.setOrderId(1L);//Long 订单id(订单表id)
            entity.setTheme("1");//String 投诉主题，1售后保障服务，2未收到货
            entity.setContent("1");//String 投诉(申诉)内容
            entity.setIp("1");//String ip
            entity.setType("1");//String 类型，0投诉、1申诉
            entity.setReplyId(1L);//Long 申诉的投诉id(本表的id)
            entity.setStatus("1");//String 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
            entity.setCreateDate(new Date());//java.util.Date 创建时间
            entity.setUpdateDate(new Date());//java.util.Date 更新时间
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5
            entity.setBak6("1");//String 备用字段6
            entity.setBak7("1");//String 备用字段7
            entity.setBak8("1");//String 备用字段8
            entity.setBak9("1");//String 备用字段9
            entity.setBak10("1");//String 备用字段10
            entity.setOrderDetailId(1L);//Long 订单详情id(订单详情表id)
            entity.setSystemHandleTime(new Date());//java.util.Date 平台处理时间
            entity.setSystemHandleHandelOpinion("1");//String 平台处理意见
            entity.setAdminId(1L);//Long 处理人(管理员表id)

            list.add(entity);
        }
        boolean rs = tradeComplaintService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        TradeComplaint entity = new TradeComplaint();
        entity.setId(1L);
        tradeComplaintService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = tradeComplaintService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }
}