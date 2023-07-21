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
import com.sicheng.admin.sys.entity.Area;
import com.sicheng.admin.sys.entity.Office;
import com.sicheng.admin.sys.entity.Role;
import com.sicheng.admin.sys.entity.User;
import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.service.PurchaseService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;

/**
 * <p>标题: 单元测试--purchase Service </p>
 * <p>描述: </p>
 * @author 蔡龙
 * @version 2018-07-20
 */
@Transactional//表示测试完成后回滚事务
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"}) 
public class PurchaseServiceTest {
	@Autowired
	private PurchaseService purchaseService;
	
	
	/**
	 * 获取单条数据
	 */
	@Test
	public void test_selectById(){
		Purchase purchase= purchaseService.selectById(0L);
		Assert.assertNull(purchase);
	}
	
	/**
	 * 查询 select * from a where id in(…)
	 */
	@Test
	public void test_selectByIdIn(){
		List<Object> paramList=new ArrayList<Object>();
		paramList.add("1");
		paramList.add("2");
		List<Purchase> list= purchaseService.selectByIdIn(paramList);
		Assert.assertNotNull(list);
	}
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
	 * 					入参为null，或入参为new一个实体对象但无属性值，会查全表
	 */
	@Test
	public void test_selectByWhere(){
		Page<Purchase> page=new Page<Purchase>();
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		page= purchaseService.selectByWhere(page, new Wrapper(entity));
		Assert.assertNotNull(page.getList());
	}
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param condition 查询条件，可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
	 * 					入参为null，或入参为new一个实体对象但无属性值，会查全表
	 */
	@Test
	public void test_selectByWhere2(){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		List<Purchase> list= purchaseService.selectByWhere(new Wrapper(entity));
		Assert.assertNotNull(list);
	}
	
	/**
	 * 查询所有数据列表，无条件
	 * @param entity 可为null。或new一个实体对象，用于控制order by 排序、控制del_flag、控制distinct
	 */
	@Test
	public void test_selectAll(){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		//因当表中数据量较大时，查全表很耗时，所以注释了
		//List<Purchase> list= purchaseService.selectAll(new Wrapper(entity));
		//Assert.assertNotNull(list);
	}
	
	/**
	 * 插入数据
	 * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
	 * @param entity
	 * @return 受影响的行数
	 */
	@Test
	public void test_insert(){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999998L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999998L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		int rs= purchaseService.insert(entity);
		Assert.assertEquals(1, rs);
	}
	
	/**
	 * 插入,只把非空的值插入到对应的字段
	 * 如果要在entity中带回自增长生成的主键值，mybatis的xml中要添加<insert id="insertSelective" keyProperty="pageId" useGeneratedKeys="true">
	 * @param entity
	 * @return 受影响的行数
	 */
	@Test
	public void test_insertSelective(){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999997L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999997L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		int rs= purchaseService.insertSelective(entity);
		Assert.assertEquals(1, rs);
	}
	
	/**
	 * 根据主键更新记录,更新除了主键的所有字段
	 * @param entity
	 * @return 受影响的行数
	 */
	@Test
	public void test_updateById(){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		int rs= purchaseService.updateById(entity);
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 根据条件更新记录,更新除了主键的所有字段
	 * @param entity 数据实体，用于存储数据，这些数据将被update到表中
	 * @param condition 条件，用于where条件，找出符合条件的数据。入参为null，或入参为new一个实体对象但无属性值，执行sql时会报错，防止更新全表
	 * @return 受影响的行数
	 * 
	 * 注意：condition.setDelFlag(null);把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
	 */
	@Test
	public void test_updateByWhere(){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		Purchase condition=new Purchase();
		condition.setId(2L);
		int rs= purchaseService.updateByWhere(entity,new Wrapper(condition));
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 根据主键更新记录,只把非空的值更到对应的字段
	 * @param entity 数据实体，用于存储数据，这些数据将被update到表中
	 * @return 受影响的行数
	 */
	@Test
	public void test_updateByIdSelective(){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		int rs= purchaseService.updateByIdSelective(entity);
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 根据条件更新记录,只把非空的值更到对应的字段
	 * @param entity 数据实体，用于存储数据，这些数据将被update到表中
	 * @param condition 条件，用于where条件，找出符合条件的数据。入参为null，或入参为new一个实体对象但无属性值，执行sql时会报错，防止更新全表
	 * @return 受影响的行数
	 * 
	 * 注意：condition.setDelFlag(null);把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
	 */
	@Test
	public void test_updateByWhereSelective(){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		Purchase condition=new Purchase();
		condition.setId(2L);
		int rs= purchaseService.updateByWhereSelective(entity,new Wrapper(condition));
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 删除数据
	 * （如果有del_flag字段，就逻辑删除，更新del_flag字段为1表示删除）
	 * （如果无del_flag字段，就物理删除）
	 * @param id 主键
	 * @return 受影响的行数
	 */
	@Test
	public void test_deleteById(){
		int rs= purchaseService.deleteById(1L);
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 删除数据（物理删除）
	 * @param condition 删除条件。入参为null，或入参为new一个实体对象但无属性值，执行SQL会报错，防止删除全表
	 * @return 受影响的行数
	 * 
	 * 注意：data.setDelFlag(null);//把del_flag设为null，表示del_flag不做为条件查询，请你根据业务情况自己把握
	 */
	@Test
	public void test_deleteByWhere(){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		int rs= purchaseService.deleteByWhere(new Wrapper(entity));
		Assert.assertNotNull(rs);
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
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
		int rs= purchaseService.countByWhere(new Wrapper(entity));
		Assert.assertNotNull(rs);
	}
	
	/**
	 * 空值测试，防止空值导致无where条件时，删除全表、更新全表
	 */
	@Test
	public void test_emptyTest(){
		Purchase p1=new Purchase();
		p1.setCreateDate(new Date());
		//p1.setBak1("bak1");
		
		Purchase p2=new Purchase();//p2的属性全是空值
		
		//purchaseService.selectAll(null);
		//purchaseService.selectAll(new Wrapper(p2));
		purchaseService.countByWhere(null);
		purchaseService.countByWhere(new Wrapper(p2));
		
		try{
			purchaseService.updateByWhere(p1,new Wrapper());
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			purchaseService.updateByWhereSelective(p1,new Wrapper());
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			purchaseService.updateByWhere(p1,null);
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			purchaseService.updateByWhereSelective(p1,null);
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			purchaseService.deleteByWhere(new Wrapper());
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
		
		try{
			purchaseService.deleteByWhere(null);
			Assert.assertEquals(true, false);
		}catch(Exception e){
			Assert.assertEquals(true, true);//抛异常就满意了
		}
	}
	
	/**
	 * 批量插入
	 */
	@Test
	public void test_insertBatch(){
		List<Purchase> list=new ArrayList<Purchase>();
		for(int i=0;i<1;i++){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
			list.add(entity);
		}
		boolean rs= purchaseService.insertBatch(list);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getId());
		}
		Assert.assertEquals(true, rs);
	}
	
	/**
	 * 批量插入 selective
	 */
	@Test
	public void test_insertSelectiveBatch(){
		List<Purchase> list=new ArrayList<Purchase>();
		for(int i=0;i<1;i++){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999996L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999996L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
			list.add(entity);
		}
		boolean rs= purchaseService.insertSelectiveBatch(list);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getId());
		}
		Assert.assertEquals(true, rs);
	}
	
	@Test
	public void test_updateBatch(){
		List<Purchase> list=new ArrayList<Purchase>();
		for(int i=0;i<1;i++){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
			list.add(entity);
		}
		boolean rs= purchaseService.updateBatch(list);
		Assert.assertEquals(true, rs);
	}
	
	/**
	 * 批量插入 selective
	 */
	@Test
	public void test_updateSelectiveBatch(){
		List<Purchase> list=new ArrayList<Purchase>();
		for(int i=0;i<1;i++){
		Purchase entity=new Purchase();
		entity.setPurchaseId(999999999L);//Long 采购单id
		entity.setContent("a");//String 采购内容
		entity.setTitle("a");//String 采购标题
		entity.setBomPath("a");//String 用户bom表的地址（当type=20时使用本字段）
		entity.setBomNewPath("a");//String 管理员上传的bom表地址（当type=20时使用本字段）
		entity.setUId(999999999L);//Long 会员ID
		entity.setType("a");//String 类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		entity.setStatus("a");//String 采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		entity.setExpiryTime(new Date());//java.util.Date 采购到期时间
		entity.setCreateDate(new Date());//java.util.Date 创建时间
		entity.setUpdateDate(new Date());//java.util.Date 更新时间
		entity.setCycle("a");//String 交货周期
		entity.setPurchaseExplain("a");//String purchase_explain
			list.add(entity);
		}
		boolean rs= purchaseService.updateSelectiveBatch(list);
		Assert.assertEquals(true, rs);
	}
	
	/**
	 * 获取单条数据 
	 */
	@Test
	public void test_selectOne(){
		Purchase entity=new Purchase();
		entity.setId(1L);
		purchaseService.selectOne(new Wrapper(entity));
	}
	
	/**
	 *  批量删除数据
	 */
	@Test
	public void test_deleteByIdIn(){
		List<Object> list =new ArrayList<Object>();
		list.add(1L);
		list.add(2L);
		list.add(3L);
		int rs= purchaseService.deleteByIdIn(list);
		Assert.assertNotNull(rs);
	}	
	
}