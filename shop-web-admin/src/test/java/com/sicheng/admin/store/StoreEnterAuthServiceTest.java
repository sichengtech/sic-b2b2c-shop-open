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
package com.sicheng.admin.store;

import com.sicheng.admin.store.entity.StoreEnterAuth;
import com.sicheng.admin.store.service.StoreEnterAuthService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.IdGen;
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
 * <p>标题: 单元测试--入驻申请（业务审核） Service </p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author 蔡龙
 * @version 2017-01-13
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class StoreEnterAuthServiceTest {
    @Autowired
    private StoreEnterAuthService storeEnterAuthService;


    /**
     * 获取单条数据
     */
    @Test
    public void test_selectById() {
        StoreEnterAuth storeEnterAuth = storeEnterAuthService.selectById(0L);
        Assert.assertNull(storeEnterAuth);
    }

    /**
     * 查询 select * from a where id in(…)
     */
    @Test
    public void test_selectByIdIn() {
        List<Object> paramList = new ArrayList<Object>();
        paramList.add("1");
        paramList.add("2");
        List<StoreEnterAuth> list = storeEnterAuthService.selectByIdIn(paramList);
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
        Page<StoreEnterAuth> page = new Page<StoreEnterAuth>();
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setEnterId(1L);//Long 主键(卖家表，入驻申请用1个id)
        entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
        entity.setCompanyName("1");//String 公司名称
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 公司_详细地址
        entity.setRegisteredCapital(1);//Long 注册资金，单位万元
        entity.setContact("1");//String 联系人
        entity.setContactNumber("1");//String 联系电话
        entity.setSellerLicense("1");//String 营业执照号
        entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
        entity.setOrganizationCode("1");//String 组织机构代码
        entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
        entity.setTaxRegistrationNumber("1");//String 税务登记证号
        entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
        entity.setStoreName("1");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
        entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
        entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
        entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
        entity.setPaymentInstructions("1");//String 商家付款凭证说明
        entity.setOneAuditOpinion("1");//String 一审审核意见
        entity.setTwoAuditOpinion("1");//String 二审审核意见
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
        entity.setAuditOpinion("1");//String 入驻信息审核意见
        page = storeEnterAuthService.selectByWhere(page, new Wrapper(entity));
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
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
        entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
        entity.setCompanyName("1");//String 公司名称
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 公司_详细地址
        entity.setRegisteredCapital(1);//Long 注册资金，单位万元
        entity.setContact("1");//String 联系人
        entity.setContactNumber("1");//String 联系电话
        entity.setSellerLicense("1");//String 营业执照号
        entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
        entity.setOrganizationCode("1");//String 组织机构代码
        entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
        entity.setTaxRegistrationNumber("1");//String 税务登记证号
        entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
        entity.setStoreName("1");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
        entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
        entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
        entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
        entity.setPaymentInstructions("1");//String 商家付款凭证说明
        entity.setOneAuditOpinion("1");//String 一审审核意见
        entity.setTwoAuditOpinion("1");//String 二审审核意见
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
        entity.setAuditOpinion("1");//String 入驻信息审核意见
        List<StoreEnterAuth> list = storeEnterAuthService.selectByWhere(new Wrapper(entity));
        Assert.assertNotNull(list);
    }

    /**
     * 查询所有数据列表，无条件
     *
     * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
     */
    @Test
    public void test_selectAll() {
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
        entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
        entity.setCompanyName("1");//String 公司名称
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 公司_详细地址
        entity.setRegisteredCapital(1);//Long 注册资金，单位万元
        entity.setContact("1");//String 联系人
        entity.setContactNumber("1");//String 联系电话
        entity.setSellerLicense("1");//String 营业执照号
        entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
        entity.setOrganizationCode("1");//String 组织机构代码
        entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
        entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
        entity.setStoreName("1");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
        entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
        entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
        entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
        entity.setPaymentInstructions("1");//String 商家付款凭证说明
        entity.setOneAuditOpinion("1");//String 一审审核意见
        entity.setTwoAuditOpinion("1");//String 二审审核意见
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
        entity.setAuditOpinion("1");//String 入驻信息审核意见
        List<StoreEnterAuth> list = storeEnterAuthService.selectAll(new Wrapper(entity));
        Assert.assertNotNull(list);
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
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
        entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
        entity.setCompanyName("1");//String 公司名称
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 公司_详细地址
        entity.setRegisteredCapital(1);//Long 注册资金，单位万元
        entity.setContact("1");//String 联系人
        entity.setContactNumber("1");//String 联系电话
        entity.setSellerLicense("1");//String 营业执照号
        entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
        entity.setOrganizationCode("1");//String 组织机构代码
        entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
        entity.setTaxRegistrationNumber("1");//String 税务登记证号
        entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
        entity.setStoreName(IdGen.randomBase62(5));//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
        entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
        entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
        entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
        entity.setPaymentInstructions("1");//String 商家付款凭证说明
        entity.setOneAuditOpinion("1");//String 一审审核意见
        entity.setTwoAuditOpinion("1");//String 二审审核意见
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
        entity.setAuditOpinion("1");//String 入驻信息审核意见
        int rs = storeEnterAuthService.insertSelective(entity);
        Assert.assertEquals(1, rs);
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
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
        entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
        entity.setCompanyName("1");//String 公司名称
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 公司_详细地址
        entity.setRegisteredCapital(1);//Long 注册资金，单位万元
        entity.setContact("1");//String 联系人
        entity.setContactNumber("1");//String 联系电话
        entity.setSellerLicense("1");//String 营业执照号
        entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
        entity.setOrganizationCode("1");//String 组织机构代码
        entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
        entity.setTaxRegistrationNumber("1");//String 税务登记证号
        entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
        entity.setStoreName("1");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
        entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
        entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
        entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
        entity.setPaymentInstructions("1");//String 商家付款凭证说明
        entity.setOneAuditOpinion("1");//String 一审审核意见
        entity.setTwoAuditOpinion("1");//String 二审审核意见
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
        entity.setAuditOpinion("1");//String 入驻信息审核意见
        StoreEnterAuth condition = new StoreEnterAuth();
        condition.setId(2L);
        int rs = storeEnterAuthService.updateByWhere(entity, new Wrapper(condition));
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
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
        entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
        entity.setCompanyName("1");//String 公司名称
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 公司_详细地址
        entity.setRegisteredCapital(1);//Long 注册资金，单位万元
        entity.setContact("1");//String 联系人
        entity.setContactNumber("1");//String 联系电话
        entity.setSellerLicense("1");//String 营业执照号
        entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
        entity.setOrganizationCode("1");//String 组织机构代码
        entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
        entity.setTaxRegistrationNumber("1");//String 税务登记证号
        entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
        entity.setStoreName("1a");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
        entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
        entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
        entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
        entity.setPaymentInstructions("1");//String 商家付款凭证说明
        entity.setOneAuditOpinion("1");//String 一审审核意见
        entity.setTwoAuditOpinion("1");//String 二审审核意见
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
        entity.setAuditOpinion("1");//String 入驻信息审核意见
        int rs = storeEnterAuthService.updateByIdSelective(entity);
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
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
        entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
        entity.setCompanyName("1");//String 公司名称
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 公司_详细地址
        entity.setRegisteredCapital(1);//Long 注册资金，单位万元
        entity.setContact("1");//String 联系人
        entity.setContactNumber("1");//String 联系电话
        entity.setSellerLicense("1");//String 营业执照号
        entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
        entity.setOrganizationCode("1");//String 组织机构代码
        entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
        entity.setTaxRegistrationNumber("1");//String 税务登记证号
        entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
        entity.setStoreName("1");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
        entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
        entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
        entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
        entity.setPaymentInstructions("1");//String 商家付款凭证说明
        entity.setOneAuditOpinion("1");//String 一审审核意见
        entity.setTwoAuditOpinion("1");//String 二审审核意见
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
        entity.setAuditOpinion("1");//String 入驻信息审核意见
        StoreEnterAuth condition = new StoreEnterAuth();
        condition.setId(2L);
        int rs = storeEnterAuthService.updateByWhereSelective(entity, new Wrapper(condition));
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
        int rs = storeEnterAuthService.deleteById(1L);
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
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
        entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
        entity.setCompanyName("1");//String 公司名称
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 公司_详细地址
        entity.setRegisteredCapital(1);//Long 注册资金，单位万元
        entity.setContact("1");//String 联系人
        entity.setContactNumber("1");//String 联系电话
        entity.setSellerLicense("1");//String 营业执照号
        entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
        entity.setOrganizationCode("1");//String 组织机构代码
        entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
        entity.setTaxRegistrationNumber("1");//String 税务登记证号
        entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
        entity.setStoreName("1");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
        entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
        entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
        entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
        entity.setPaymentInstructions("1");//String 商家付款凭证说明
        entity.setOneAuditOpinion("1");//String 一审审核意见
        entity.setTwoAuditOpinion("1");//String 二审审核意见
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
        entity.setAuditOpinion("1");//String 入驻信息审核意见
        int rs = storeEnterAuthService.deleteByWhere(new Wrapper(entity));
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
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
        entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
        entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
        entity.setCompanyName("1");//String 公司名称
        entity.setCountryId(1L);//Long 国家(关联地区表)
        entity.setCountryName("1");//String 国家名字
        entity.setProvinceId(1L);//Long 省(关联地区表)
        entity.setProvinceName("1");//String 省名字
        entity.setCityId(1L);//Long 市(关联地区表)
        entity.setCityName("1");//String 市名字
        entity.setDistrictId(1L);//Long 县(关联地区表)
        entity.setDistrictName("1");//String 县名字
        entity.setDetailedAddress("1");//String 公司_详细地址
        entity.setRegisteredCapital(1);//Long 注册资金，单位万元
        entity.setContact("1");//String 联系人
        entity.setContactNumber("1");//String 联系电话
        entity.setSellerLicense("1");//String 营业执照号
        entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
        entity.setOrganizationCode("1");//String 组织机构代码
        entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
        entity.setTaxRegistrationNumber("1");//String 税务登记证号
        entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
        entity.setStoreName("1");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
        entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
        entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
        entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
        entity.setPaymentInstructions("1");//String 商家付款凭证说明
        entity.setOneAuditOpinion("1");//String 一审审核意见
        entity.setTwoAuditOpinion("1");//String 二审审核意见
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
        entity.setAuditOpinion("1");//String 入驻信息审核意见
        int rs = storeEnterAuthService.countByWhere(new Wrapper(entity));
        Assert.assertNotNull(rs);
    }

    /**
     * 空值测试，防止空值导致无where条件时，删除全表、更新全表
     */
    @Test
    public void test_emptyTest() {
        StoreEnterAuth p1 = new StoreEnterAuth();
        p1.setCreateDate(new Date());
        p1.setBak1("bak1");

        StoreEnterAuth p2 = new StoreEnterAuth();//p2的属性全是空值

        storeEnterAuthService.selectAll(null);
        storeEnterAuthService.selectAll(new Wrapper(p2));
        storeEnterAuthService.countByWhere(null);
        storeEnterAuthService.countByWhere(new Wrapper(p2));

        try {
            storeEnterAuthService.updateByWhere(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeEnterAuthService.updateByWhereSelective(p1, new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeEnterAuthService.updateByWhere(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeEnterAuthService.updateByWhereSelective(p1, null);
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeEnterAuthService.deleteByWhere(new Wrapper(p2));
            Assert.assertEquals(true, false);
        } catch (Exception e) {
            Assert.assertEquals(true, true);//抛异常就满意了
        }

        try {
            storeEnterAuthService.deleteByWhere(null);
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
        List<StoreEnterAuth> list = new ArrayList<StoreEnterAuth>();
        for (int i = 0; i < 10; i++) {
            StoreEnterAuth entity = new StoreEnterAuth();
            entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
            entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
            entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
            entity.setCompanyName("1");//String 公司名称
            entity.setCountryId(1L);//Long 国家(关联地区表)
            entity.setCountryName("1");//String 国家名字
            entity.setProvinceId(1L);//Long 省(关联地区表)
            entity.setProvinceName("1");//String 省名字
            entity.setCityId(1L);//Long 市(关联地区表)
            entity.setCityName("1");//String 市名字
            entity.setDistrictId(1L);//Long 县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 公司_详细地址
            entity.setRegisteredCapital(1);//Long 注册资金，单位万元
            entity.setContact("1");//String 联系人
            entity.setContactNumber("1");//String 联系电话
            entity.setSellerLicense("1");//String 营业执照号
            entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
            entity.setOrganizationCode("1");//String 组织机构代码
            entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
            entity.setTaxRegistrationNumber("1");//String 税务登记证号
            entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
            entity.setStoreName(IdGen.randomBase62(5));//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
            entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
            entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
            entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
            entity.setPaymentInstructions("1");//String 商家付款凭证说明
            entity.setOneAuditOpinion("1");//String 一审审核意见
            entity.setTwoAuditOpinion("1");//String 二审审核意见
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
            entity.setAuditOpinion("1");//String 入驻信息审核意见
            entity.setCommission(new BigDecimal(0));//BigDecimal 分佣比例

            list.add(entity);
        }
        boolean rs = storeEnterAuthService.insertBatch(list);
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
        List<StoreEnterAuth> list = new ArrayList<StoreEnterAuth>();
        for (int i = 0; i < 1; i++) {
            StoreEnterAuth entity = new StoreEnterAuth();
            entity.setPkMode(1);
            entity.setEnterId(96523L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
            entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
            entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
            entity.setCompanyName("1");//String 公司名称
            entity.setCountryId(1L);//Long 国家(关联地区表)
            entity.setCountryName("1");//String 国家名字
            entity.setProvinceId(1L);//Long 省(关联地区表)
            entity.setProvinceName("1");//String 省名字
            entity.setCityId(1L);//Long 市(关联地区表)
            entity.setCityName("1");//String 市名字
            entity.setDistrictId(1L);//Long 县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 公司_详细地址
            entity.setRegisteredCapital(1);//Long 注册资金，单位万元
            entity.setContact("1");//String 联系人
            entity.setContactNumber("1");//String 联系电话
            entity.setSellerLicense("1");//String 营业执照号
            entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
            entity.setOrganizationCode("1");//String 组织机构代码
            entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
            entity.setTaxRegistrationNumber("1");//String 税务登记证号
            entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
            entity.setStoreName(IdGen.randomBase62(5));//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
            entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
            entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
            entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
            entity.setPaymentInstructions("1");//String 商家付款凭证说明
            entity.setOneAuditOpinion("1");//String 一审审核意见
            entity.setTwoAuditOpinion("1");//String 二审审核意见
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
            entity.setAuditOpinion("1");//String 入驻信息审核意见

            list.add(entity);
        }
        boolean rs = storeEnterAuthService.insertSelectiveBatch(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        Assert.assertEquals(true, rs);
    }

    @Test
    public void test_updateBatch() {
        List<StoreEnterAuth> list = new ArrayList<StoreEnterAuth>();
        for (int i = 0; i < 1; i++) {
            StoreEnterAuth entity = new StoreEnterAuth();
            entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
            entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
            entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
            entity.setCompanyName("1");//String 公司名称
            entity.setCountryId(1L);//Long 国家(关联地区表)
            entity.setCountryName("1");//String 国家名字
            entity.setProvinceId(1L);//Long 省(关联地区表)
            entity.setProvinceName("1");//String 省名字
            entity.setCityId(1L);//Long 市(关联地区表)
            entity.setCityName("1");//String 市名字
            entity.setDistrictId(1L);//Long 县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 公司_详细地址
            entity.setRegisteredCapital(1);//Long 注册资金，单位万元
            entity.setContact("1");//String 联系人
            entity.setContactNumber("1");//String 联系电话
            entity.setSellerLicense("1");//String 营业执照号
            entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
            entity.setOrganizationCode("1");//String 组织机构代码
            entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
            entity.setTaxRegistrationNumber("1");//String 税务登记证号
            entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
            entity.setStoreName("120210");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
            entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
            entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
            entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
            entity.setPaymentInstructions("1");//String 商家付款凭证说明
            entity.setOneAuditOpinion("1");//String 一审审核意见
            entity.setTwoAuditOpinion("1");//String 二审审核意见
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
            entity.setAuditOpinion("1");//String 入驻信息审核意见
            entity.setCommission(new BigDecimal(0));//BigDecimal 分佣比例

            list.add(entity);
        }
        boolean rs = storeEnterAuthService.updateBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 批量插入 selective
     */
    @Test
    public void test_updateSelectiveBatch() {
        List<StoreEnterAuth> list = new ArrayList<StoreEnterAuth>();
        for (int i = 0; i < 1; i++) {
            StoreEnterAuth entity = new StoreEnterAuth();
            entity.setEnterId(1L);//Long 主键(卖家表，入驻申请，店铺表，店铺二级域名用1个id)
            entity.setStatus("1");//String 入驻信息完善后才有值 审核状态：10一审待审核、20一审审核成功、30一审审核不通过、40二审待审核、50二审审核通过、60二审核不通过 70 入驻信息更改后审核中、  80入驻信息更改后审核通过、90入驻信息更改后审核不通过
            entity.setIsPerfect("1");//String 入驻信息一审是否完善，0不完善、1完善
            entity.setCompanyName("1");//String 公司名称
            entity.setCountryId(1L);//Long 国家(关联地区表)
            entity.setCountryName("1");//String 国家名字
            entity.setProvinceId(1L);//Long 省(关联地区表)
            entity.setProvinceName("1");//String 省名字
            entity.setCityId(1L);//Long 市(关联地区表)
            entity.setCityName("1");//String 市名字
            entity.setDistrictId(1L);//Long 县(关联地区表)
            entity.setDistrictName("1");//String 县名字
            entity.setDetailedAddress("1");//String 公司_详细地址
            entity.setRegisteredCapital(1);//Long 注册资金，单位万元
            entity.setContact("1");//String 联系人
            entity.setContactNumber("1");//String 联系电话
            entity.setSellerLicense("1");//String 营业执照号
            entity.setSellerLicensePath("1");//String 营业执照电子版图片路径
            entity.setOrganizationCode("1");//String 组织机构代码
            entity.setOrganizationCodePath("1");//String 组织机构代码电子版图片路径
            entity.setTaxRegistrationNumber("1");//String 税务登记证号
            entity.setTaxRegistrationNumberPath("1");//String 税务登记，税务登记证号电子版path
            entity.setStoreName("120210");//String 店铺名（注意店铺设置修改的时候记得把这个修改了）
            entity.setLevelId(1L);//Long 店铺等级（关联店铺等级id）
            entity.setIndustryId(1L);//Long 主营行业（关联主营行业id）
            entity.setPaymentVoucherPath("1");//String 商家上传付款凭证path
            entity.setPaymentInstructions("1");//String 商家付款凭证说明
            entity.setOneAuditOpinion("1");//String 一审审核意见
            entity.setTwoAuditOpinion("1");//String 二审审核意见
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
            entity.setAuditOpinion("1");//String 入驻信息审核意见

            list.add(entity);
        }
        boolean rs = storeEnterAuthService.updateSelectiveBatch(list);
        Assert.assertEquals(true, rs);
    }

    /**
     * 获取单条数据
     */
    @Test
    public void test_selectOne() {
        StoreEnterAuth entity = new StoreEnterAuth();
        entity.setId(1L);
        storeEnterAuthService.selectOne(new Wrapper(entity));
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_deleteByIdIn() {
        Long[] ids = new Long[]{1L, 2L, 3L};
        List list = Arrays.asList(ids);
        int rs = storeEnterAuthService.deleteByIdIn(list);
        Assert.assertNotNull(rs);
    }
}