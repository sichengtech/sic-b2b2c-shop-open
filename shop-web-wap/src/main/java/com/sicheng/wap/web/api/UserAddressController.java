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
 *  
 */
package com.sicheng.wap.web.api;

import com.google.common.collect.Lists;
import com.sicheng.admin.member.entity.MemberAddress;
import com.sicheng.admin.sys.entity.Area;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.AreaService;
import com.sicheng.wap.service.MemberAddressService;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
  * <p>标题: UserAddressController</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2018年2月11日 下午3:26:07
  *
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class UserAddressController extends BaseController {

    @Autowired
    private MemberAddressService memberAddressService;

    @Autowired
    private AreaService areaService;

    /**
      * 获取收货地址信息 
      * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userAddress/one")
    public Map<String, Object> userAddressOne() {
        MemberAddress memberAddress = new MemberAddress();
        String addressId = R.get("addressId");//收货地址id
        String isDefault = R.get("default");//是否是默认收货地址(0不默认 1默认)
        //参数验证
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isNotBlank(addressId) && !StringUtils.isNumeric(addressId)) {
            errorList.add(FYUtils.fy("收货地址id只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //查询收货地址信息
        if (StringUtils.isNotBlank(addressId)) {
            memberAddress.setAddressId(Long.parseLong(addressId));
        }
        if (StringUtils.isNotBlank(isDefault)) {
            memberAddress.setIsDefault(isDefault);
        }
        memberAddress.setUId(AppTokenUtils.findUser().getId());//用户id,属主检查
        try {
            Wrapper w = new Wrapper();
            w.setEntity(memberAddress);
            w.orderBy("a.is_default desc");
            memberAddress = memberAddressService.selectOne(w);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("收货地址信息获取成功"), memberAddress, null);
        } catch (Exception e) {
            logger.error("获取网站注册设置信息:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取收货地址信息错误"), null, null);
        }
    }

    /**
      * 获取收货地址列表信息 
      * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userAddress/list")
    public Map<String, Object> userAddressList() {
        //查询收货地址信息
        MemberAddress memberAddress = new MemberAddress();
        memberAddress.setUId(AppTokenUtils.findUser().getId());//用户id,属主检查
        try {
            Wrapper w = new Wrapper();
            w.setEntity(memberAddress);
            w.orderBy("a.is_default desc");
            List<MemberAddress> list = memberAddressService.selectByWhere(w);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("收货地址信息获取成功"), list, null);
        } catch (Exception e) {
            logger.error("获取网站注册设置信息:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取收货地址信息错误"), null, null);
        }
    }

    /**
      * 添加地址列表 
      * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userAddress/save")
    public Map<String, Object> userAddressSave() {
        //表单验证
        List<String> errorList = validate();
        if (errorList.size() > 0) {
            String message = ApiUtils.errorMessage(errorList);
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //具体业务
        String name = R.get("name");//姓名
        String mobile = R.get("mobile");//手机号
        Long provinceId = R.getLong("provinceId");//省id
        String provinceName = R.get("provinceName");//省名字
        Long cityId = R.getLong("cityId");//市id
        String cityName = R.get("cityName");//市名字
        Long districtId = R.getLong("districtId");//县id
        String districtName = R.get("districtName");//县名字
        String detailedAddress = R.get("detailedAddress");//详细地址
        String isDefault = R.get("isDefault");//是否默认地址(0不默认,1默认)
        try {
            MemberAddress memberAddress = new MemberAddress();
            memberAddress.setUId(AppTokenUtils.findUser().getId());//用户id
            memberAddress.setName(name);
            memberAddress.setMobile(mobile);
            memberAddress.setCountryId(1L);
            memberAddress.setCountryName(FYUtils.fy("中国"));//TODO 默认国家
            memberAddress.setProvinceId(provinceId);
            memberAddress.setProvinceName(provinceName);
            memberAddress.setCityId(cityId);
            memberAddress.setCityName(cityName);
            memberAddress.setDistrictId(districtId);
            memberAddress.setDistrictName(districtName);
            memberAddress.setDetailedAddress(detailedAddress);
            memberAddressService.insertAddress(isDefault, memberAddress);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("收货地址信息添加成功"), "/user/address/list.htm", null);
        } catch (Exception e) {
            logger.error("获取网站注册设置信息:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("添加收货地址信息错误"), null, null);
        }
    }

    /**
      * 编辑收货地址 
      * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userAddress/edit")
    public Map<String, Object> userAddressEdit() {
        String addressId = R.get("addressId");//收货地址id
        if (StringUtils.isBlank(addressId)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("收货地址id不能为空"), null, null);
        }
        //表单验证
        List<String> errorList = validate();
        if (errorList.size() > 0) {
            String message = ApiUtils.errorMessage(errorList);
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //具体业务
        String name = R.get("name");//姓名
        String mobile = R.get("mobile");//手机号
        Long provinceId = R.getLong("provinceId");//省id
        String provinceName = R.get("provinceName");//省名字
        Long cityId = R.getLong("cityId");//市id
        String cityName = R.get("cityName");//市名字
        Long districtId = R.getLong("districtId");//县id
        String districtName = R.get("districtName");//县名字
        String detailedAddress = R.get("detailedAddress");//详细地址
        String isDefault = R.get("isDefault");//是否默认地址(0不默认,1默认)
        try {
            MemberAddress memberAddress = new MemberAddress();
            memberAddress.setAddressId(Long.valueOf(addressId));//属主检查
            memberAddress.setUId(AppTokenUtils.findUser().getId());//用户id
            memberAddress.setName(name);
            memberAddress.setMobile(mobile);
            memberAddress.setCountryId(1L);
            memberAddress.setCountryName(FYUtils.fy("中国"));
            memberAddress.setProvinceId(provinceId);
            memberAddress.setProvinceName(provinceName);
            memberAddress.setCityId(cityId);
            memberAddress.setCityName(cityName);
            memberAddress.setDistrictId(districtId);
            memberAddress.setDistrictName(districtName);
            memberAddress.setDetailedAddress(detailedAddress);
            memberAddressService.updateAddress(isDefault, memberAddress);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("收货地址信息编辑成功"), "/user/address/list.htm", null);
        } catch (Exception e) {
            logger.error("获取网站注册设置信息:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("编辑收货地址信息错误"), null, null);
        }
    }

    /**
      * 删除收货地址 
      * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userAddress/delete")
    public Map<String, Object> userAddressDelete() {
        String addressId = R.get("addressId");
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(addressId)) {
            errorList.add(FYUtils.fy("收货地址id不能为空"));
        }
        if (StringUtils.isNotBlank(addressId) && !StringUtils.isNumeric(addressId)) {
            errorList.add(FYUtils.fy("收货地址id只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            MemberAddress entity = new MemberAddress();
            entity.setAddressId(Long.parseLong(addressId));
            entity.setUId(AppTokenUtils.findUser().getUId());//属主检查
            memberAddressService.deleteByWhere(new Wrapper(entity));
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("收货地址删除成功"), null, null);
        } catch (Exception e) {
            logger.error("收货地址删除失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("收货地址删除失败"), null, null);
        }
    }

    /**
      * 收货地址列表页设置默认地址 
      * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userAddress/default")
    public Map<String, Object> addressDefault() {
        String addressId = R.get("addressId");
        String isDefault = R.get("isDefault");
        List<String> errorList = new ArrayList<>();
        if (StringUtils.isBlank(addressId)) {
            errorList.add(FYUtils.fy("收货地址id不能为空"));
        }
        if (StringUtils.isNotBlank(addressId) && !StringUtils.isNumeric(addressId)) {
            errorList.add(FYUtils.fy("收货地址id只能是数字"));
        }
        String message = ApiUtils.errorMessage(errorList);
        if (!errorList.isEmpty()) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        try {
            MemberAddress memberAddress = new MemberAddress();
            memberAddress.setAddressId(Long.parseLong(addressId));
            memberAddress.setIsDefault(isDefault);//是否默认地址(0不默认 1默认)
            memberAddress.setUId(AppTokenUtils.findUser().getUId());//属主检查
            memberAddressService.updateAddress(isDefault, memberAddress);
            String messages = "";
            if ("1".equals(isDefault)) {
                messages = FYUtils.fy("设置默认地址成功");
            } else {
                messages = FYUtils.fy("取消默认地址成功");
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, messages, null, null);
        } catch (Exception e) {
            logger.error("收货地址删除失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("默认地址设置失败"), null, null);
        }
    }

    /**
      * 收货地址表单验证 
      * @return
     */
    private List<String> validate() {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add(FYUtils.fy("收货人不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add(FYUtils.fy("收货人不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("mobile"))) {
            errorList.add(FYUtils.fy("手机号不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("mobile")) && R.get("mobile").length() > 64) {
            errorList.add(FYUtils.fy("手机号不能超过64字符"));
        }
        if (StringUtils.isBlank(R.get("provinceId"))) {
            errorList.add(FYUtils.fy("省id不能为空"));
        }
        if (StringUtils.isBlank(R.get("provinceName"))) {
            errorList.add(FYUtils.fy("省名字不能为空"));
        }
        if (StringUtils.isBlank(R.get("cityId"))) {
            errorList.add(FYUtils.fy("市id不能为空"));
        }
        if (StringUtils.isBlank(R.get("cityName"))) {
            errorList.add(FYUtils.fy("市名字不能为空"));
        }
        if (StringUtils.isBlank(R.get("districtId"))) {
            errorList.add(FYUtils.fy("县id不能为空"));
        }
        if (StringUtils.isBlank(R.get("districtName"))) {
            errorList.add(FYUtils.fy("县名字不能为空"));
        }
        if (StringUtils.isBlank(R.get("detailedAddress"))) {
            errorList.add(FYUtils.fy("详细地址不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("detailedAddress")) && R.get("detailedAddress").length() > 255) {
            errorList.add(FYUtils.fy("详细地址不能超过255字符"));
        }
        return errorList;
    }

    /**
      * 根据父id查询地区数据 
      * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/sys/area/selectArea")
    public Map<String, Object> getArea() {
        Long parentId = R.getLong("parentId");//地区的父id
        if (parentId == null) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("请输入地区父id"), null, null);
        }
        try {
            Area area = new Area();
            area.setParent(new Area(parentId));
            area.setSort(null);
            area.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
            Wrapper wrapper = new Wrapper();
            wrapper.setEntity(area);
            wrapper.orderBy("sort");
            List<Area> list = areaService.selectByWhere(wrapper);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("地区数据获取成功"), list, null);
        } catch (Exception e) {
            logger.error("地区数据获取失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("地区数据获取失败"), null, null);
        }
    }
    
    /**
     * 查询所有地区数据 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/sys/area/selectAll")
    public Map<String, Object> getAreaAll() {
    	try {
    	   	//查询所有的地区数据
      		Area area = new Area();
      		area.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
      		Wrapper wrapper = new Wrapper();
      		wrapper.setEntity(area);
      		wrapper.orderBy("sort");
      		List<Area> list = areaService.selectAll(wrapper);
      		//每个地区数据保留三个字段
      		List<HashMap<String,Object>> listData = Lists.newArrayList();
      		for(Area entity:list){
      			HashMap<String,Object> mapData=new LinkedHashMap<>();
    			mapData.put("id", entity.getId());//地区id
    			mapData.put("name", entity.getName());//地区名称
    			mapData.put("pid", entity.getParentId());//地区的父级id
    			listData.add(mapData);
      		}
      		//组装数据
      		while(listData.size()>1){
      			//按父级id进行数据分组
      			Map<String,List<HashMap<String,Object>>> map=new HashMap<>();
      			for(HashMap<String,Object> entity:listData){
      				String key=entity.get("pid").toString();
      				if(map.get(key)==null){
      					List<HashMap<String,Object>> listSub=new ArrayList<>();
      					map.put(key,listSub);
      				}
      				map.get(key).add(entity);
      				
      			}
      			//拼接数据
      			List<HashMap<String,Object>> bjListData = Lists.newArrayList();//每次拼接后的数据
      			for(HashMap<String,Object> entity:listData){
      				for (String key:map.keySet()) {
      					//没有子集数据退出此次循环
      					if(!key.equals(entity.get("id").toString())){
      						continue;
      					}
      					//存在子集数据的地区进行组装数据
      					HashMap<String,Object> mapData=new LinkedHashMap<>();
      					mapData.put("id", entity.get("id"));//地区id
      					mapData.put("name", entity.get("name"));//地区名称
      					mapData.put("pid", entity.get("pid"));//地区的父级id
      					mapData.put("sub", map.get(key));//地区的子集列表数据
      					bjListData.add(mapData);
      				}
      			}
      			listData=bjListData;
      		}
    		return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("地区数据获取成功"), listData, null);
    	} catch (Exception e) {
    		logger.error("地区数据获取失败:" , e );
    		return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("地区数据获取失败"), null, null);
    	}
    }
}
