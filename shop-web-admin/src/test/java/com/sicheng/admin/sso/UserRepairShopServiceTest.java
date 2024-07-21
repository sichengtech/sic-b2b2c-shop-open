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

import com.sicheng.admin.sso.entity.UserRepairShop;
import com.sicheng.admin.sso.service.UserRepairShopService;
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
 * <p>标题: 单元测试--汽车服务门店 Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017-07-30
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class UserRepairShopServiceTest {
    @Autowired
    private UserRepairShopService userRepairShopService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        UserRepairShop useRepairShop = userRepairShopService.selectById(0L);
        Assert.assertNull(useRepairShop);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<UserRepairShop> list = userRepairShopService.selectByIdIn(paramList);
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
        Page<UserRepairShop> page = new Page<UserRepairShop>();
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        page = userRepairShopService.selectByWhere(page, new Wrapper(entity));
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
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        List<UserRepairShop> list = userRepairShopService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        List<UserRepairShop> list = userRepairShopService.selectAll(new Wrapper(entity));
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
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userRepairShopService.insert(entity);
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
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userRepairShopService.insertSelective(entity);
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
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userRepairShopService.updateById(entity);
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
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        UserRepairShop condition = new UserRepairShop();
        condition.setId(2L);
        int rs = userRepairShopService.updateByWhere(entity, new Wrapper(condition));
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
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userRepairShopService.updateByIdSelective(entity);
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
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        UserRepairShop condition = new UserRepairShop();
        condition.setId(2L);
        int rs = userRepairShopService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = userRepairShopService.deleteById(1L);
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
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userRepairShopService.deleteByWhere(new Wrapper(entity));
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
        UserRepairShop entity = new UserRepairShop();
        entity.setUId(1L);//Long 主键,会员的ID
        entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
        entity.setType("1");//String 门店类型（字典）use_shop_type
        entity.setName("1");//String 门店名称
        entity.setIntroduce("1");//String 门店介绍
        entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
        entity.setCountryName("1");//String 门店所在地国家名字
        entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
        entity.setProvinceName("1");//String 门店所在地省名字
        entity.setCityId(1L);//Long 门店所在地市(关联地区表)
        entity.setCityName("1");//String 门店所在地市名字
        entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 详细地址
        entity.setBurns("1");//String 门店面积
        entity.setBrands("1");//String 经营品牌，号分割
        entity.setOpenDate(new Date());//java.util.Date 建店日期
        entity.setBossName("1");//String 老板姓名
        entity.setBossMobile("1");//String 老板电话
        entity.setPath1("1");//String 门店照片path1
        entity.setPath2("1");//String 门店照片path2
        entity.setPath3("1");//String 门店照片path3
        entity.setPath4("1");//String 门店照片path4
        entity.setPath5("1");//String 门店照片path5
        entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
        entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
        entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
        entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
        entity.setHotline("1");//String 服务热线
        entity.setShopkeeperName("1");//String 店主姓名
        entity.setShopkeeperMobile("1");//String 店主手机
        entity.setContactsName("1");//String 联系人姓名
        entity.setDepartment("1");//String 所在部门
        entity.setMobile("1");//String 本人手机
        entity.setBak1("1");//String 备用字段1
        entity.setBak2("1");//String 备用字段2
        entity.setBak3("1");//String 备用字段3
        entity.setBak4("1");//String 备用字段4
        entity.setBak5("1");//String 备用字段5

        int rs = userRepairShopService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        UserRepairShop p1 = new UserRepairShop();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        UserRepairShop p2 = new UserRepairShop();//p2的属性全是空值

        userRepairShopService.selectAll(null);
        userRepairShopService.selectAll(new Wrapper(p2));
        userRepairShopService.countByWhere(null);
        userRepairShopService.countByWhere(new Wrapper(p2));

        try {
            userRepairShopService.updateByWhere(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userRepairShopService.updateByWhereSelective(p1, new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userRepairShopService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userRepairShopService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userRepairShopService.deleteByWhere(new Wrapper());
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            userRepairShopService.deleteByWhere(null);
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
        List<UserRepairShop> list = new ArrayList<UserRepairShop>();
        for (int i = 0; i < 10; i++) {
            UserRepairShop entity = new UserRepairShop();
            entity.setUId(1L);//Long 主键,会员的ID
            entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
            entity.setType("1");//String 门店类型（字典）use_shop_type
            entity.setName("1");//String 门店名称
            entity.setIntroduce("1");//String 门店介绍
            entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
            entity.setCountryName("1");//String 门店所在地国家名字
            entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
            entity.setProvinceName("1");//String 门店所在地省名字
            entity.setCityId(1L);//Long 门店所在地市(关联地区表)
            entity.setCityName("1");//String 门店所在地市名字
            entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 详细地址
            entity.setBurns("1");//String 门店面积
            entity.setBrands("1");//String 经营品牌，号分割
            entity.setOpenDate(new Date());//java.util.Date 建店日期
            entity.setBossName("1");//String 老板姓名
            entity.setBossMobile("1");//String 老板电话
            entity.setPath1("1");//String 门店照片path1
            entity.setPath2("1");//String 门店照片path2
            entity.setPath3("1");//String 门店照片path3
            entity.setPath4("1");//String 门店照片path4
            entity.setPath5("1");//String 门店照片path5
            entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
            entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
            entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
            entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
            entity.setHotline("1");//String 服务热线
            entity.setShopkeeperName("1");//String 店主姓名
            entity.setShopkeeperMobile("1");//String 店主手机
            entity.setContactsName("1");//String 联系人姓名
            entity.setDepartment("1");//String 所在部门
            entity.setMobile("1");//String 本人手机
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = userRepairShopService.insertBatch(list);
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
        List<UserRepairShop> list = new ArrayList<UserRepairShop>();
        for (int i = 0; i < 10; i++) {
            UserRepairShop entity = new UserRepairShop();
            entity.setUId(1L);//Long 主键,会员的ID
            entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
            entity.setType("1");//String 门店类型（字典）use_shop_type
            entity.setName("1");//String 门店名称
            entity.setIntroduce("1");//String 门店介绍
            entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
            entity.setCountryName("1");//String 门店所在地国家名字
            entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
            entity.setProvinceName("1");//String 门店所在地省名字
            entity.setCityId(1L);//Long 门店所在地市(关联地区表)
            entity.setCityName("1");//String 门店所在地市名字
            entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 详细地址
            entity.setBurns("1");//String 门店面积
            entity.setBrands("1");//String 经营品牌，号分割
            entity.setOpenDate(new Date());//java.util.Date 建店日期
            entity.setBossName("1");//String 老板姓名
            entity.setBossMobile("1");//String 老板电话
            entity.setPath1("1");//String 门店照片path1
            entity.setPath2("1");//String 门店照片path2
            entity.setPath3("1");//String 门店照片path3
            entity.setPath4("1");//String 门店照片path4
            entity.setPath5("1");//String 门店照片path5
            entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
            entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
            entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
            entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
            entity.setHotline("1");//String 服务热线
            entity.setShopkeeperName("1");//String 店主姓名
            entity.setShopkeeperMobile("1");//String 店主手机
            entity.setContactsName("1");//String 联系人姓名
            entity.setDepartment("1");//String 所在部门
            entity.setMobile("1");//String 本人手机
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = userRepairShopService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<UserRepairShop> list = new ArrayList<UserRepairShop>();
        for (int i = 0; i < 10; i++) {
            UserRepairShop entity = new UserRepairShop();
            entity.setUId(1L);//Long 主键,会员的ID
            entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
            entity.setType("1");//String 门店类型（字典）use_shop_type
            entity.setName("1");//String 门店名称
            entity.setIntroduce("1");//String 门店介绍
            entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
            entity.setCountryName("1");//String 门店所在地国家名字
            entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
            entity.setProvinceName("1");//String 门店所在地省名字
            entity.setCityId(1L);//Long 门店所在地市(关联地区表)
            entity.setCityName("1");//String 门店所在地市名字
            entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 详细地址
            entity.setBurns("1");//String 门店面积
            entity.setBrands("1");//String 经营品牌，号分割
            entity.setOpenDate(new Date());//java.util.Date 建店日期
            entity.setBossName("1");//String 老板姓名
            entity.setBossMobile("1");//String 老板电话
            entity.setPath1("1");//String 门店照片path1
            entity.setPath2("1");//String 门店照片path2
            entity.setPath3("1");//String 门店照片path3
            entity.setPath4("1");//String 门店照片path4
            entity.setPath5("1");//String 门店照片path5
            entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
            entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
            entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
            entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
            entity.setHotline("1");//String 服务热线
            entity.setShopkeeperName("1");//String 店主姓名
            entity.setShopkeeperMobile("1");//String 店主手机
            entity.setContactsName("1");//String 联系人姓名
            entity.setDepartment("1");//String 所在部门
            entity.setMobile("1");//String 本人手机
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = userRepairShopService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<UserRepairShop> list = new ArrayList<UserRepairShop>();
        for (int i = 0; i < 10; i++) {
            UserRepairShop entity = new UserRepairShop();
            entity.setUId(1L);//Long 主键,会员的ID
            entity.setAuthType("1");//String 审核状态：0待审、1通过、2未通过
            entity.setType("1");//String 门店类型（字典）use_shop_type
            entity.setName("1");//String 门店名称
            entity.setIntroduce("1");//String 门店介绍
            entity.setCountryId(1L);//Long 门店所在地国家(关联地区表)
            entity.setCountryName("1");//String 门店所在地国家名字
            entity.setProvinceId(1L);//Long 门店所在地省(关联地区表)
            entity.setProvinceName("1");//String 门店所在地省名字
            entity.setCityId(1L);//Long 门店所在地市(关联地区表)
            entity.setCityName("1");//String 门店所在地市名字
            entity.setDistrictId(1L);//Long 门店所在地县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 详细地址
            entity.setBurns("1");//String 门店面积
            entity.setBrands("1");//String 经营品牌，号分割
            entity.setOpenDate(new Date());//java.util.Date 建店日期
            entity.setBossName("1");//String 老板姓名
            entity.setBossMobile("1");//String 老板电话
            entity.setPath1("1");//String 门店照片path1
            entity.setPath2("1");//String 门店照片path2
            entity.setPath3("1");//String 门店照片path3
            entity.setPath4("1");//String 门店照片path4
            entity.setPath5("1");//String 门店照片path5
            entity.setPeopleCount("1");//String 门店人数（字典）user_people_count
            entity.setBusinessStatus("1");//String 营业状态（字典）user_business_status
            entity.setOpenShopDate(new Date());//java.util.Date 开店营业时间
            entity.setCloseShopDate(new Date());//java.util.Date 关店营业时间
            entity.setHotline("1");//String 服务热线
            entity.setShopkeeperName("1");//String 店主姓名
            entity.setShopkeeperMobile("1");//String 店主手机
            entity.setContactsName("1");//String 联系人姓名
            entity.setDepartment("1");//String 所在部门
            entity.setMobile("1");//String 本人手机
            entity.setBak1("1");//String 备用字段1
            entity.setBak2("1");//String 备用字段2
            entity.setBak3("1");//String 备用字段3
            entity.setBak4("1");//String 备用字段4
            entity.setBak5("1");//String 备用字段5

            list.add(entity);
        }
        boolean rs = userRepairShopService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        UserRepairShop entity = new UserRepairShop();
        entity.setId(1L);
        userRepairShopService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = userRepairShopService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }

}