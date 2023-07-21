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
package com.sicheng.admin.purchase.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.sicheng.admin.purchase.entity.Purchase;
import com.sicheng.admin.purchase.entity.PurchaseItem;
import com.sicheng.admin.purchase.entity.ViewPurchase;
import com.sicheng.admin.purchase.service.PurchaseService;
import com.sicheng.admin.purchase.service.ViewPurchaseService;

import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.FileUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.admin.site.service.SiteSendMessagsService;

/**
 * purchase Controller
 * 所属模块：purchase 
 * @author 蔡龙
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/purchase/purchase")
public class PurchaseController extends BaseController {

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ViewPurchaseService viewPurchaseService;
	
	@Autowired
    private SiteSendMessagsService siteSendMessagsService;
	

	
	/**
	 * 菜单高亮
	 * @param id
	 * @param model
	 */
	@ModelAttribute
	public void get(Long id,Model model) {
		String menu3="030301";//请修改为正确的三级菜单编号
		//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
		super.menuHighLighting(menu3);
	}	
	/**
	 * 进入列表页
	 * @param viewPurchase 实体对象
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("purchase:purchase:view")
	@RequestMapping(value = "list")
	public String list(ViewPurchase viewPurchase, HttpServletRequest request, HttpServletResponse response, Model model) {
		//三级菜单高亮
		if("10".equals(viewPurchase.getPurchaseStatus())){
			//采购单审核
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			super.menuHighLighting("030302");
		}
		if("30".equals(viewPurchase.getPurchaseStatus())){
			//采购单拆分
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			super.menuHighLighting("030303");
		}
		//业务处理
		String title=R.get("title");//采购标题
		Wrapper wrapper=new Wrapper(viewPurchase);
		if(StringUtils.isNotBlank(title)){
			wrapper.and("a.purchase_title like","%"+title+"%");
		}
		Page<ViewPurchase> page = viewPurchaseService.selectByWhere(new Page<ViewPurchase>(request, response), wrapper); 
		model.addAttribute("title", title);
		model.addAttribute("page", page);
		return "admin/purchase/purchaseList";
	}


	/**
	 * 进入审核页面
	 * @param purchase 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchase:auth")
	@RequestMapping(value = "auth1")
	public String auth1(Purchase purchase, Model model) {
		Purchase entity = null;
		if(purchase!=null){
			if (purchase.getPurchaseId()!=null){
				entity = purchaseService.selectById(purchase.getPurchaseId());
			}
		}
		model.addAttribute("purchase", entity);
		return "admin/purchase/purchaseForm";
	}

	/**
	 * 执行审核
	 * @param purchase 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchase:auth")
	@RequestMapping(value = "auth2")
	public String auth2(Purchase purchase, Model model, RedirectAttributes redirectAttributes) {
		//业务处理
		String authResult=R.get("authResult");//审核结果:0.不通过、1.通过
		String type=R.get("type");//类型：10快捷采购一句话采购，20BOM采购 30批量发布采购
		if("1".equals(authResult)){
			if("30".equals(type)){
				purchase.setStatus("35");//采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
			}else{
				purchase.setStatus("30");
			}
		}else{
			purchase.setStatus("20");
		}
		purchaseService.authPurchase(purchase,type);
		addMessage(redirectAttributes, FYUtils.fyParams("审核采购单成功"));
		Purchase p = purchaseService.selectById(purchase.getPurchaseId());
		if("0".equals(authResult)){
			//发送消息
			Map<String,String> map=new HashMap<>();
			map.put("purchaseTitle", StringUtils.abbr(purchase.getTitle(), 20));
			siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PURCHASE_AUTH_ERROR, p.getUId());
		}
		if("1".equals(authResult)){
			//发送消息
			Map<String,String> map=new HashMap<>();
			map.put("purchaseTitle", StringUtils.abbr(purchase.getTitle(), 20));
			siteSendMessagsService.sendMessage(map, SiteSendMessagsService.PURCHASE_AUTH_SUCCESS, p.getUId());
		}
		return "redirect:"+adminPath+"/purchase/purchase/list.do?repage";
	}	
	
	/**
	 * 进入一句话拆分页面
	 * @param purchase 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchase:edit")
	@RequestMapping(value = "split1")
	public String split1(Purchase purchase, Model model) {
		Purchase entity = null;
		if(purchase!=null){
			model.addAttribute("expiryTime", purchase.getExpiryTime());
			if (purchase.getPurchaseId()!=null){
				entity = purchaseService.selectById(purchase.getPurchaseId());
			}
		}
		model.addAttribute("purchase", entity);
		//三级菜单高亮
		if("10".equals(entity.getStatus())){
			//采购单审核
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			super.menuHighLighting("030302");
		}
		if("30".equals(entity.getStatus())){
			//采购单拆分
			//手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
			super.menuHighLighting("030303");
		}
		return "admin/purchase/purchaseSpilt";
	}
	
	/**
	 * 执行一句话拆分
	 * @param purchase 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchase:edit")
	@RequestMapping(value = "split2")
	public String split2(Purchase purchase, Model model, RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate1(purchase);
		if(StringUtils.isBlank(R.get("name"))){
			errorList.add(FYUtils.fyParams("请添加采购明细"));
		}
		//支付方式属性验证
		errorList=validate2(errorList);
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return split1(purchase, model);//回显错误提示
		}
		//业务处理
		purchase.setStatus("35");//采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		String[] names=R.getArray("name");//产品名称
		String[] models=R.getArray("model");//规格型号
		String[] brands=R.getArray("brand");//品牌
		String[] amounts=R.getArray("amount");//数量
		String[] priceRequirements=R.getArray("priceRequirement");//单价
		String[] unit=R.getArray("unit");//单位
		String[] purchaseRemarks=R.getArray("purchaseRemarks");//采购备注
		List<PurchaseItem> list=Lists.newArrayList();
		for (int i = 0; i < names.length; i++) {
			PurchaseItem purchaseItem=new PurchaseItem();
			purchaseItem.setName(names[i]);
			purchaseItem.setModel(models[i]);
			purchaseItem.setBrand(brands[i]);
			purchaseItem.setAmount(Integer.valueOf(amounts[i]));
			if(StringUtils.isNotBlank(priceRequirements[i])){
				purchaseItem.setPriceRequirement(new BigDecimal(priceRequirements[i]));
			}
			purchaseItem.setUnit(unit[i]);
			purchaseItem.setPurchaseRemarks(purchaseRemarks[i]);
			purchaseItem.setPurchaseId(purchase.getPurchaseId());
			list.add(purchaseItem);
		}
		purchaseService.savePurchase(purchase,list);
		addMessage(redirectAttributes, FYUtils.fyParams("拆分采购单成功"));
		return "redirect:"+adminPath+"/purchase/purchase/list.do?repage";
	}	
	
	/**
	 * 进入bom上传页面
	 * @param purchase 实体对象
	 * @param model
	 * @return
	 */	
	@RequiresPermissions("purchase:purchase:edit")
	@RequestMapping(value = "bomUpload1")
	public String bomUpload1(Purchase purchase, Model model) {
		Purchase entity = null;
		if(purchase!=null){
			model.addAttribute("expiryTime", purchase.getExpiryTime());
			if (purchase.getPurchaseId()!=null){
				entity = purchaseService.selectById(purchase.getPurchaseId());
			}
		}
		model.addAttribute("purchase", entity);
		return "admin/purchase/purchaseBomUpload";
	}
	
	/**
	 * 执行bom上传
	 * @param purchase 实体对象
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("purchase:purchase:edit")
	@RequestMapping(value = "bomUpload2")
	public String bomUpload2(@RequestParam("excel") MultipartFile excel,Purchase purchase,Model model,RedirectAttributes redirectAttributes) {
		//表单验证
		List<String> errorList=validate1(purchase);
		if (excel==null) {
			errorList.add(FYUtils.fyParams("请选择上传BOM文件"));
		}
		if (excel.getSize() > 5242880) {
			errorList.add(FYUtils.fyParams("上传文件超过了5M"));
		}
		if (!FileUtils.isExcel(excel.getOriginalFilename())) {
			errorList.add(FYUtils.fyParams("文件格式不正确，请上传excle"));
		}
		// 判断是不是03版本的excel
		boolean is03Excel = excel.getOriginalFilename().matches("^.+\\.(?i)(xls)$");
		// 读取工作薄
		Workbook workbook=null;
		try {
			InputStream inputStream = excel.getInputStream();
			workbook = is03Excel ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
		} catch (FileNotFoundException e) {
			logger.error("e1：",e);
		} catch (IOException e) {
			logger.error("e1：",e);
		}
		
		Sheet sheet = workbook.getSheetAt(0);
		if (sheet.getPhysicalNumberOfRows() <= 1) {
			errorList.add(FYUtils.fyParams("Excel格式与模板格式不一致！"));
		}
		if(sheet.getPhysicalNumberOfRows()>20000){
			errorList.add(FYUtils.fyParams("上传的Excel超过2万行！"));
		}
		if(errorList.size()>0){
			errorList.add(0, FYUtils.fyParams("数据验证失败："));
			addMessage(model, errorList.toArray(new String[]{}));
			return bomUpload1(purchase, model);//回显错误提示
		}
		//将Excel的分类数据导入数据库中
		List<PurchaseItem> list=Lists.newArrayList();
		for (int i = 1;  i< sheet.getPhysicalNumberOfRows(); i++) {
			//验证单元格数据
			Row row = sheet.getRow(i);
			List<String> errorList3=validate3(row);
			if(errorList3.size()>0){
				errorList3.add(0, FYUtils.fyParams("第")+(i+1)+FYUtils.fyParams("行数据验证失败："));
				addMessage(model, errorList3.toArray(new String[]{}));
				return bomUpload1(purchase, model);//回显错误提示
			}
			// 将单元格数据存入实体字段中
			PurchaseItem purchaseItem=new PurchaseItem();
			purchaseItem.setName(getCellFormatValue(row.getCell(0)).trim());//产品名称
			purchaseItem.setModel(getCellFormatValue(row.getCell(1)).trim());//规格型号
			purchaseItem.setBrand(getCellFormatValue(row.getCell(2)).trim());//品牌
			purchaseItem.setAmount(Integer.valueOf(getCellFormatValue(row.getCell(3)).trim()));//数量
			if(StringUtils.isNotBlank(getCellFormatValue(row.getCell(4)).trim())){
				purchaseItem.setPriceRequirement(new BigDecimal(getCellFormatValue(row.getCell(4)).trim()));//单价
			}
			purchaseItem.setUnit(getCellFormatValue(row.getCell(5)).trim());//单位
			purchaseItem.setPurchaseRemarks(getCellFormatValue(row.getCell(6)).trim());//采购备注
			purchaseItem.setPurchaseId(purchase.getPurchaseId());//采购单id
			list.add(purchaseItem);
			
		}
		//业务处理
		purchase.setStatus("35");//采购状态:  10.审核中20.审核未通过30.待拆分35.报价中40.交易中50.完成交易60.交易取消
		purchaseService.savePurchase(purchase,list);
		addMessage(redirectAttributes, FYUtils.fyParams("上传BOM采购单成功"));
		return "redirect:"+adminPath+"/purchase/purchase/list.do?repage";
	}	

	/**
	 * 删除
	 * @param purchase 实体对象
	 * @param redirectAttributes
	 * @return
	 */	
	@RequiresPermissions("purchase:purchase:drop")
	@RequestMapping(value = "delete")
	public String delete(Purchase purchase, RedirectAttributes redirectAttributes) {
		purchaseService.delete(purchase.getPurchaseId());
		addMessage(redirectAttributes, FYUtils.fyParams("删除采购单成功"));
		return "redirect:"+adminPath+"/purchase/purchase/list.do?repage";
	}
	
	/**
	 * 根据HSSFCell类型设置数据
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (!(cell == null || "".equals(cell))) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cellvalue = cell.getStringCellValue();
		}
		if(StringUtils.isNotBlank(cellvalue)){
			cellvalue = cellvalue.trim();
		}
		return cellvalue;
	}

	/**
	 * 采购单验证
	 * @return
	 */
	private List<String> validate1(Purchase purchase){
		List<String> errorList = new ArrayList<String>();
		if(purchase.getPurchaseId()==null){
			errorList.add(FYUtils.fyParams("采购单id不能为空"));
		}
		if(purchase.getUId()==null){
			errorList.add(FYUtils.fyParams("采购方id不能为空"));
		}
//		if(purchase.getExpiryTime()==null){
//			errorList.add(FYUtils.fyParams("到期时间不能为空"));
//		}
		return errorList;
	}
	
	/**
	 * 一句话拆分采购单明细
	 * @param errorList
	 * @return
	 */
	private List<String> validate2(List<String> errorList){
		String[] name=R.getArray("name");//产品名称
		String[] model=R.getArray("model");//规格型号
		String[] brand=R.getArray("brand");//品牌
		String[] amount=R.getArray("amount");//数量
		String[] priceRequirement=R.getArray("priceRequirement");//单价
		String[] unit=R.getArray("unit");//单位
		String[] purchaseRemarks=R.getArray("purchaseRemarks");//采购备注
		//验证采购单明细产品名称
		if(name!=null && name.length!=0){
			for(int i=0;i<name.length;i++){
				if(StringUtils.isBlank(name[i])){
					errorList.add(FYUtils.fyParams("产品名称不能为空"));
					break;
				}
				if(name[i].length()>64){
					errorList.add(FYUtils.fyParams("产品名称最大长度不能超过64字符"));
					break;
				}
			}
		}
		//验证采购单明细产品型号
		if(model!=null && model.length!=0){
			for(int i=0;i<model.length;i++){
				if(StringUtils.isBlank(model[i])){
					errorList.add(FYUtils.fyParams("产品型号不能为空"));
					break;
				}
				if(model[i].length()>64){
					errorList.add(FYUtils.fyParams("产品型号最大长度不能超过64字符"));
					break;
				}
			}
		}
		//验证采购单明细产品品牌
		if(brand!=null && brand.length!=0){
			for(int i=0;i<brand.length;i++){
				if(brand[i].length()>64){
					errorList.add(FYUtils.fyParams("产品品牌不能超过64字符"));
					break;
				}
			}
		}
		//验证采购单明细产品数量
		if(amount!=null && amount.length!=0){
			for(int i=0;i<amount.length;i++){
				if(StringUtils.isBlank(amount[i])){
					errorList.add(FYUtils.fyParams("产品数量不能为空"));
					break;
				}
				if(amount[i].length()>9){
					errorList.add(FYUtils.fyParams("产品数量最大长度不能超过9字符"));
					break;
				}
			}
		}
		//验证采购单明细单位
		if(unit!=null && unit.length!=0){
			for(int i=0;i<unit.length;i++){
				if(StringUtils.isBlank(unit[i])){
					errorList.add(FYUtils.fyParams("单位不能为空"));
					break;
				}
				if(unit[i].length()>64){
					errorList.add(FYUtils.fyParams("单位最大长度不能超过64字符"));
					break;
				}
			}
		}
		//验证采购单明细采购备注
		if(brand!=null && brand.length!=0){
			for(int i=0;i<brand.length;i++){
				if(brand[i].length()>255){
					errorList.add(FYUtils.fyParams("采购备注不能超过255字符"));
					break;
				}
			}
		}
		int nameLen=name.length;
		int modelLen=model.length;
		int brandLen=brand.length;
		int amountLen=amount.length;
		int priceRequirementLen=priceRequirement.length;
		int unitLen=unit.length;
		int purchaseRemarksLen=purchaseRemarks.length;
		
		if(nameLen!=modelLen || nameLen!=brandLen || nameLen!=amountLen ||
				 nameLen!=priceRequirementLen || nameLen!=unitLen || nameLen!=purchaseRemarksLen){
			errorList.add(FYUtils.fyParams("参数错误"));
		}
		return errorList;
	}

	/**
	 * 采购单验证
	 * @return
	 */
	private List<String> validate3(Row row){
		List<String> errorList = new ArrayList<String>();
		String name = getCellFormatValue(row.getCell(0)).trim();//产品名称
		String model = getCellFormatValue(row.getCell(1)).trim();//规格型号
		String brand = getCellFormatValue(row.getCell(2)).trim();//品牌
		String amount = getCellFormatValue(row.getCell(3)).trim();//数量
		String priceRequirement = getCellFormatValue(row.getCell(4)).trim();//单价
		String unit = getCellFormatValue(row.getCell(5)).trim();//单位
		String purchaseRemarks = getCellFormatValue(row.getCell(6)).trim();//采购备注
		if(StringUtils.isBlank(name)){
			errorList.add(FYUtils.fyParams("产品名称不能为空"));
		}
		if(StringUtils.isBlank(model)){
			errorList.add(FYUtils.fyParams("规格型号不能为空"));
		}
		if(StringUtils.isBlank(amount)){
			errorList.add(FYUtils.fyParams("需求数量不能为空"));
		}
		if(StringUtils.isBlank(unit)){
			errorList.add(FYUtils.fyParams("单位不能为空"));
		}
		if(StringUtils.isNotBlank(name) && name.length()>64){
			errorList.add(FYUtils.fyParams("产品名称不能超过64字符"));
		}
		if(StringUtils.isNotBlank(model) && model.length()>64){
			errorList.add(FYUtils.fyParams("产品型号不能超过64字符"));
		}
		if(StringUtils.isNotBlank(brand) && brand.length()>64){
			errorList.add(FYUtils.fyParams("产品品牌不能超过64字符"));
		}
		if(StringUtils.isNotBlank(amount) && amount.length()>9){
			errorList.add(FYUtils.fyParams("需求数量不能超过9字符"));
		}
		if(StringUtils.isNotBlank(priceRequirement) && priceRequirement.length()>12){
			errorList.add(FYUtils.fyParams("价格要求不能超过12字符"));
		}
		if(StringUtils.isNotBlank(unit) && unit.length()>64){
			errorList.add(FYUtils.fyParams("单位不能超过64字符"));
		}
		if(StringUtils.isNotBlank(purchaseRemarks) && purchaseRemarks.length()>255){
			errorList.add(FYUtils.fyParams("采购备注不能超过255字符"));
		}
		return errorList;
	}
}