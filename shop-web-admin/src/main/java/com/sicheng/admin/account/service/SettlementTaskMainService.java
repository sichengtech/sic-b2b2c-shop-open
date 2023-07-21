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
package com.sicheng.admin.account.service;

import com.sicheng.admin.account.dao.SettlementTaskMainDao;
import com.sicheng.admin.account.entity.SettlementBill;
import com.sicheng.admin.account.entity.SettlementTaskMain;
import com.sicheng.admin.account.entity.SettlementTaskSub;
import com.sicheng.admin.sso.entity.UserSeller;
import com.sicheng.admin.sso.service.UserSellerService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import com.sicheng.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 结算主任务 Service
 * @author 范秀秀
 * @version 2017-03-01
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class SettlementTaskMainService extends CrudService<SettlementTaskMainDao, SettlementTaskMain> {
	
	@Autowired
	private SettlementTaskSubService settlementTaskSubService;
	
	@Autowired
	private SettlementBillService settlementBillService;
	
	@Autowired
	private UserSellerService userSellerService;
	@Autowired
	private AccountUserService accountUserService;
	
	/**
	 * 定时任务
	 * 创建主任务，子任务
	 * 执行子任务
	 */
	@Transactional(rollbackFor=Exception.class)
	public void settlementTask(String billType){
		/*//1.获取上一个月的第一天和最后一天
		//获取当前时间
		Calendar firstCal = Calendar.getInstance();
		//调到上个月
		firstCal.add(Calendar.MONTH, -1);
		//前一个月的第一天
		int minDay=firstCal.getActualMinimum(Calendar.DAY_OF_MONTH);
		//按要求设置时间
		firstCal.set(firstCal.get(Calendar.YEAR),firstCal.get(Calendar.MONTH), minDay, 00, 00, 00);
		//按格式输出
		Date beginTime=firstCal.getTime();
		Calendar lastCal = Calendar.getInstance();
		lastCal.add(Calendar.MONTH, -1);
		//得到一个月最最后一天日期(31/30/29/28)
		int MaxDay=lastCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//按要求设置时间
		lastCal.set(lastCal.get(Calendar.YEAR), lastCal.get(Calendar.MONTH), MaxDay, 23, 59, 59);
		Date endTime=lastCal.getTime();
		try {
			beginTime=DateUtils.parseDate(DateUtils.formatDate(beginTime,"yyyy/MM/dd ")+"00:00:00","yyyy/MM/dd HH:mm:ss");
			endTime=DateUtils.parseDate(DateUtils.formatDate(endTime,"yyyy/MM/dd ")+"23:59:59","yyyy/MM/dd HH:mm:ss");
		} catch (ParseException e) {
			logger.error("日期转换发生异常", e);
		}*/
		
		//获取前一天的时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat( "yyyy/MM/dd").format(cal.getTime());
		String beginTimeStr = yesterday + " 00:00:00";
		String endTimeStr = yesterday + " 23:59:59";
		Date beginTime=DateUtils.parseDate(beginTimeStr);
		Date endTime=DateUtils.parseDate(endTimeStr);
		//2.插入一条结算定时任务--主任务
		SettlementTaskMain settlementTaskMain=new SettlementTaskMain();
		settlementTaskMain.setName(DateUtils.formatDate(beginTime,"yyyy-MM-dd"));//主任务名称(年-月-日)
		//账单类型：1商品账单
		settlementTaskMain.setType(billType);
		//3.判断库中有没有当前的主任务，如果有就不插入，如果有并且执行完就不需要执行
		List<SettlementTaskMain> taskMainList=super.selectByWhere(new Wrapper(settlementTaskMain));
		if(!taskMainList.isEmpty() && taskMainList.get(0)!=null){
			if("3".equals(taskMainList.get(0).getStatus())){
				return;
			}
			settlementTaskMain=taskMainList.get(0);
		}else{
			settlementTaskMain.setBeginTime(beginTime);
			settlementTaskMain.setEndTime(endTime);
			//结算定时任务--主任务状态:1未运行,2运行中,3已完成
			settlementTaskMain.setStatus("1");
			//设置任务类型:1商品账单，2服务账单
			settlementTaskMain.setType(billType);
			super.insertSelective(settlementTaskMain);
		}
		//4.插入子任务
		//账单类型：1商品账单
		if("1".equals(billType)){
			//插入商品子任务
			insertTaskSubProduct(settlementTaskMain);
		}
		//5.执行子任务
		performTaskSub(settlementTaskMain, null);
	}
	
	/**
	 * 运行
	 * 已有主任务，创建子任务
	 * 运行子任务
	 */
	@Transactional(rollbackFor=Exception.class)
	public void runTaskSub(SettlementTaskMain settlementTaskMain){
		if(settlementTaskMain!=null){
			SettlementTaskMain entity=super.selectById(settlementTaskMain.getTaskMainId());
			//创建子任务
			insertTaskSubProduct(entity);
			//执行子任务
			performTaskSub(entity, null);
		}
	}
	
	/**
	 * 重算
	 * 已有主任务、子任务
	 * 查找子任务将子任务状态置为“未运行”
	 * 运行子任务
	 */
	@Transactional(rollbackFor=Exception.class)
	public void retryTaskSub(SettlementTaskMain settlementTaskMain){
		if(settlementTaskMain!=null){
			SettlementTaskMain entity=super.selectById(settlementTaskMain.getTaskMainId());
			//查找子任务将子任务状态置为“未运行”
			SettlementTaskSub taskSub=new SettlementTaskSub();
			//结算定时任务--子任务状态:1未运行,2运行中,3成功,4失败
			taskSub.setStatus("1");
			SettlementTaskSub condition=new SettlementTaskSub();			
			condition.setTaskMainId(entity.getTaskMainId());
			settlementTaskSubService.updateByWhereSelective(taskSub, new Wrapper(condition));
			performTaskSub(entity, null);
		}
	}
	
	/**
	 * 补算
	 * 已有主任务、子任务
	 * 查找“未运行”和“失败”的子任务
	 * 运行子任务
	 */
	@Transactional(rollbackFor=Exception.class)
	public void supplementTaskSub(SettlementTaskMain settlementTaskMain){
		if(settlementTaskMain!=null){
			SettlementTaskMain entity=super.selectById(settlementTaskMain.getTaskMainId());
			//查找“未运行”和“失败”的子任务
			String[] status={"1","4"};
			//执行子任务
			performTaskSub(entity, status);
		}
	}
	
	/**
	 * 定时任务核心(执行任务)
	 * @param settlementTaskMain 主任务
	 * @param status 子任务的状态，作为查询条件
	 * 查找当前主任务下符合条件的所有子任务，进行执行
	 */	
	public void performTaskSub(SettlementTaskMain settlementTaskMain,String[] status){
		Long minId=0L;
		Wrapper wrapper=new Wrapper();
		if(status!=null && status.length!=0){
			for(int i=0;i<status.length;i++){
				wrapper.and("status =",status[i]);
			}
		}
		Page<SettlementTaskSub> taskSubpage=settlementTaskSubService.selectByWhere(new Page<SettlementTaskSub>(),wrapper.and("task_main_id = ",settlementTaskMain.getTaskMainId()));
		SettlementBill bill=new SettlementBill();
		//账单类型：1商品账单
		bill.setBillType(settlementTaskMain.getType());
		Long successCount=0L;
		Long errorCount=0L;
		if(!taskSubpage.getList().isEmpty()){
			//执行任务之前，将主任务置的状态置为“运行中”
			//结算定时任务--主任务状态:1未运行,2运行中,3已完成
			settlementTaskMain.setStatus("2");
			super.updateByIdSelective(settlementTaskMain);
			for(;;){
				Page<SettlementTaskSub> page= new Page<>();
				page.setPageSize(1);
				if(minId == 0){
					taskSubpage=settlementTaskSubService.selectByWhere(page,wrapper.and("task_main_id = ",settlementTaskMain.getTaskMainId()));
				}else{
					taskSubpage=settlementTaskSubService.selectByWhere(page,wrapper.and("task_main_id = ",settlementTaskMain.getTaskMainId()).and("task_sub_id <", minId));
				}
				//循环的出口，page一直取第一页，直到取不到数据为止，跳出循环
				if(taskSubpage.getList().isEmpty()){
					break;
				}
				
				for(SettlementTaskSub task:taskSubpage.getList()){
					if(taskSubpage.getList().get(0)!=null){
						minId=taskSubpage.getList().get(taskSubpage.getList().size()-1).getTaskSubId();
					}
					try {
						//结算定时任务--子任务状态:1未运行,2运行中,3成功,4失败
						task.setStatus("2");
						settlementTaskSubService.updateByIdSelective(task);
						bill.setStoreId(task.getStoreId());
						bill.setBeginTime(settlementTaskMain.getBeginTime());
						bill.setEndTime(settlementTaskMain.getEndTime());
						//查询当前结算阶段 此店铺或用户是已经结算过，如已结算过，直接更新账单；否则插入新的账单
						SettlementBill billEntity=new SettlementBill();
						//任务类型，1商品账单
						if("1".equals(settlementTaskMain.getType())){
							billEntity.setStoreId(task.getStoreId());
						}
						billEntity.setBeginBeginTime(settlementTaskMain.getBeginTime());
						billEntity.setEndBeginTime(settlementTaskMain.getEndTime());
						billEntity.setBillType(settlementTaskMain.getType());
						List<SettlementBill> billList=settlementBillService.selectByWhere(new Wrapper(billEntity));
						if(!billList.isEmpty()){
							bill.setBillId(billList.get(0).getBillId());
						}
						try {
							//任务类型，1商品账单，
							if("1".equals(settlementTaskMain.getType())){
								settlementBillService.retryProduct(bill);
							}
						} catch (Exception e) {
							logger.error("修改账户信息异常：",e.getMessage());
						}
						
						//结算定时任务--子任务状态:1未运行,2运行中,3成功,4失败
						task.setStatus("3");
						settlementTaskSubService.updateByIdSelective(task);
						successCount++;
					} catch (Exception e2) {
						//结算定时任务--子任务状态:1未运行,2运行中,3成功,4失败
						task.setStatus("4");
						task.setErrorMsg(e2.toString());
						settlementTaskSubService.updateByIdSelective(task);
						errorCount++;
						logger.error("结算定时任务失败",e2);
					}
				}
			}
			Long sumCount=successCount+errorCount;
			//任务执行完成后，将主任务的状态置为“已完成”
			//结算定时任务--主任务状态:1未运行,2运行中,3已完成
			settlementTaskMain.setStatus("3");
			settlementTaskMain.setSumCount(sumCount);
			settlementTaskMain.setSuccessCount(successCount);
			settlementTaskMain.setErrorCount(errorCount);
			super.updateByIdSelective(settlementTaskMain);
			//5.记录总共多少任务，成功了多少，失败了多少
			logger.info("总执行"+sumCount+"个任务,成功了"+successCount+"个,失败了"+errorCount+"个");
		}
	}
	
	/**
	 * 插入商品结算子任务
	 * @param settlementTaskMain 主任务
	 * 给当前主任务下插入子任务
	 */
	public void insertTaskSubProduct(SettlementTaskMain settlementTaskMain){
		UserSeller userSeller=new UserSeller();
		userSeller.setIsOpen("1");//是否已完成开店:0否、1是（入驻申请二审审核通过之后修改为1）
		List<UserSeller> sellerList=userSellerService.selectByWhere(new Wrapper(userSeller));
		//3.插入任务
		List<SettlementTaskSub> taskSubList=new ArrayList<>();
		if(!sellerList.isEmpty()){
			for(UserSeller s:sellerList){
				SettlementTaskSub taskSub=new SettlementTaskSub();
				taskSub.setStoreId(s.getStoreId());
				taskSub.setTaskMainId(settlementTaskMain.getTaskMainId());
				taskSub.setStatus("1");//状态(1未运行,2运行中,3成功,4失败)
				taskSubList.add(taskSub);
			}
			settlementTaskSubService.insertSelectiveBatch(taskSubList);
		}
	}

}