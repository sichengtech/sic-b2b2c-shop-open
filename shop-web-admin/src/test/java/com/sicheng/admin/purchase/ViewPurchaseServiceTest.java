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
package com.sicheng.admin.purchase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sicheng.admin.purchase.entity.ViewPurchase;
import com.sicheng.admin.purchase.service.ViewPurchaseService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;

/**
 * <p>标题: 单元测试--采购单视图 Service </p>
 * <p>描述: </p>
 * @author zjl
 * @version 2018-07-23
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"}) 
public class ViewPurchaseServiceTest {
	@Autowired
	private ViewPurchaseService viewPurchaseService;
	
	
	/**
	 * 获取单条数据
	 */
	@Test
	public void test_selectById(){
		ViewPurchase viewPurchase= viewPurchaseService.selectById(0L);
		Assert.assertNull(viewPurchase);
	}
	
	/**
	 * 查询 select * from a where id in(…)
	 */
	@Test
	public void test_selectByIdIn(){
		List<Object> paramList=new ArrayList<Object>();
		paramList.add("1");
		paramList.add("2");
		List<ViewPurchase> list= viewPurchaseService.selectByIdIn(paramList);
		Assert.assertNotNull(list);
	}
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
	 * 					入参为null，或入参为new一个实体对象但无属性值，会查全表
	 */
	@Test
	public void test_selectByWhere(){
		Page<ViewPurchase> page=new Page<ViewPurchase>();
		ViewPurchase entity=new ViewPurchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setPurchaseTitle("a");//String 采购标题
		entity.setPurchaseContent("a");//String 采购内容
		entity.setPurchaseBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setPurchaseBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setPurchaseUId(999999999L);//Long 会员ID
		entity.setPurchaseType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setPurchaseStatus("a");//String 采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setPurchaseExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setPuCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		entity.setPurchaseCreateDate(new Date());//java.util.Date 创建时间
		entity.setPurchaseUpdateDate(new Date());//java.util.Date 更新时间
		entity.setPurchaseCounts(999999999L);//Long purchase_counts
		entity.setPiId(999999999L);//Long 采购详情id
		entity.setPiName("a");//String 产品名称
		entity.setPiModel("a");//String 产品型号
		entity.setPiBrand("a");//String 产品品牌
		page= viewPurchaseService.selectByWhere(page, new Wrapper(entity));
		Assert.assertNotNull(page.getList());
	}
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
	 * 					入参为null，或入参为new一个实体对象但无属性值，会查全表
	 */
	@Test
	public void test_selectByWhere2(){
		ViewPurchase entity=new ViewPurchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setPurchaseTitle("a");//String 采购标题
		entity.setPurchaseContent("a");//String 采购内容
		entity.setPurchaseBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setPurchaseBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setPurchaseUId(999999999L);//Long 会员ID
		entity.setPurchaseType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setPurchaseStatus("a");//String 采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setPurchaseExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setPuCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		entity.setPurchaseCreateDate(new Date());//java.util.Date 创建时间
		entity.setPurchaseUpdateDate(new Date());//java.util.Date 更新时间
		entity.setPurchaseCounts(999999999L);//Long purchase_counts
		entity.setPiId(999999999L);//Long 采购详情id
		entity.setPiName("a");//String 产品名称
		entity.setPiModel("a");//String 产品型号
		entity.setPiBrand("a");//String 产品品牌
		List<ViewPurchase> list= viewPurchaseService.selectByWhere(new Wrapper(entity));
		Assert.assertNotNull(list);
	}
	
	/**
	 * 查询所有数据列表，无条件
	 * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
	 */
	@Test
	public void test_selectAll(){
		ViewPurchase entity=new ViewPurchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setPurchaseTitle("a");//String 采购标题
		entity.setPurchaseContent("a");//String 采购内容
		entity.setPurchaseBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setPurchaseBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setPurchaseUId(999999999L);//Long 会员ID
		entity.setPurchaseType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setPurchaseStatus("a");//String 采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setPurchaseExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setPuCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		entity.setPurchaseCreateDate(new Date());//java.util.Date 创建时间
		entity.setPurchaseUpdateDate(new Date());//java.util.Date 更新时间
		entity.setPurchaseCounts(999999999L);//Long purchase_counts
		entity.setPiId(999999999L);//Long 采购详情id
		entity.setPiName("a");//String 产品名称
		entity.setPiModel("a");//String 产品型号
		entity.setPiBrand("a");//String 产品品牌
		//因当表中数据量较大时，查全表很耗时，所以注释了
		//List<ViewPurchase> list= viewPurchaseService.selectAll(new Wrapper(entity));
		//Assert.assertNotNull(list);
	}
	
	/**
	 * 根据条件查询记录总数
	 * @param condition 可为null。或new一个实体对象，用于控制del_flag、控制distinct
	 * @return 总行数
	 * 
	 * 注意：data.setDelFlag(null);//把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
	 */
	@Test
	public void test_countByWhere(){
		ViewPurchase entity=new ViewPurchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setPurchaseTitle("a");//String 采购标题
		entity.setPurchaseContent("a");//String 采购内容
		entity.setPurchaseBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setPurchaseBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setPurchaseUId(999999999L);//Long 会员ID
		entity.setPurchaseType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setPurchaseStatus("a");//String 采购状态:10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setPurchaseExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setPuCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		entity.setPurchaseCreateDate(new Date());//java.util.Date 创建时间
		entity.setPurchaseUpdateDate(new Date());//java.util.Date 更新时间
		entity.setPurchaseCounts(999999999L);//Long purchase_counts
		entity.setPiId(999999999L);//Long 采购详情id
		entity.setPiName("a");//String 产品名称
		entity.setPiModel("a");//String 产品型号
		entity.setPiBrand("a");//String 产品品牌
		int rs= viewPurchaseService.countByWhere(new Wrapper(entity));
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 获取单条数据 
	 */
	@Test
	public void test_selectOne(){
		ViewPurchase entity=new ViewPurchase();
		entity.setId(1L);
		viewPurchaseService.selectOne(new Wrapper(entity));
	}
}