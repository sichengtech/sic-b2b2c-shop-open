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
package com.sicheng.wap.web.api;

import com.google.common.collect.Lists;
import com.sicheng.admin.sys.entity.Dict;
import com.sicheng.admin.sys.entity.SysToken;
import com.sicheng.admin.sys.utils.TokenUtils;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.wap.service.DictService;
import com.sicheng.common.utils4m.AppDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题: SysController</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2017年12月18日 下午2:21:09
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class SysController extends BaseController {

    @Autowired
    private DictService dictService;

    /**
     * 根据字典类型获取字典值列表
     *
     * @param type 类型
     * @return
     */
    @RequestMapping(value = "/{version}/sys/dict/list")
    @ResponseBody
    public Map<String, Object> sysDictList(String type) {
        if (StringUtils.isBlank(type)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("字典类型为空"), null, null);
        }
        try {
            Dict dict = new Dict();
            dict.setType(type);
            Wrapper wrapper = new Wrapper();
            wrapper.setEntity(dict);
            wrapper.orderBy("a.sort asc");
            List<Dict> dictList = dictService.selectByWhere(wrapper);
            if (dictList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("字典列表数据获取成功"), dictList, null);
            }
            List<Object> dList = new ArrayList<>();
            for (int i = 0; i < dictList.size(); i++) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("id", dictList.get(i).getId());//字典id
                map.put("label", FYUtils.fy("字典_"+dictList.get(i).getLabel()));//键
                map.put("value", dictList.get(i).getValue());//值
                dList.add(map);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("字典列表数据获取成功"), dList, null);
        } catch (Exception e) {
            logger.error("根据字典类型获取字典值列表参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 获取字典键
     *
     * @param type  类型
     * @param value 字典值
     * @return
     */
    @RequestMapping(value = "/{version}/sys/dict/labelOne")
    @ResponseBody
    public Map<String, Object> sysDictLabelOne(String type, String value) {
        if (StringUtils.isBlank(type) || StringUtils.isBlank(value)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("字典参数为空"), null, null);
        }
        try {
            Dict d = new Dict();
            d.setType(type);
            d.setValue(value);
            List<Dict> dictList = dictService.selectByWhere(new Wrapper(d));
            Dict dict = new Dict();
            if (dictList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("字典数据获取成功"), dict, null);
            }
            dict = dictList.get(0);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("label", FYUtils.fy("字典_"+dict.getLabel()));//键
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("字典键获取成功"), map, null);
        } catch (Exception e) {
            logger.error("获取字典键参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 获取多个字典键
     *
     * @param type   类型
     * @param values 多个字典值
     * @return
     */
    @RequestMapping(value = "/{version}/sys/dict/labelList")
    @ResponseBody
    public Map<String, Object> sysDictLabelList(String type, String values) {
        if (StringUtils.isBlank(type) || StringUtils.isBlank(values)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_NOT_FOUND, FYUtils.fy("字典参数为空"), null, null);
        }
        try {
            List<String> valueList = new ArrayList<>();
            String[] valueArray = values.split(",");
            for (int i = 0; i < valueArray.length; i++) {
                if (StringUtils.isNotBlank(valueArray[i])) {
                    valueList.add(valueArray[i]);
                }
            }
            Dict d = new Dict();
            d.setType(type);
            Wrapper wrapper = new Wrapper();
            wrapper.setEntity(d);
            wrapper.and("a.value in", valueList);
            List<Dict> dictList = dictService.selectByWhere(wrapper);
            List<Dict> dictListData=Lists.newArrayList();
            if(!dictList.isEmpty()){
            	for(Dict dict:dictList){
            		dict.setLabel(FYUtils.fy("字典_"+dict.getLabel()));
            		dictListData.add(dict);
            	}
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("字典数据获取成功"), dictListData, null);
        } catch (Exception e) {
            logger.error("获取多个字典键参数错误:" + e.toString());
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }

    /**
     * 获取字典值
     *
     * @param type  类型
     * @param label 字典键
     * @return
     */
    @RequestMapping(value = "/{version}/sys/dict/valueOne")
    @ResponseBody
    public Map<String, Object> sysDictValueOne(String type, String label) {
        //暂时没用直接返回 空
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("暂无数据"), null, null);
//		if(StringUtils.isBlank(type) || StringUtils.isBlank(label)){
//			return ApiUtils.getMap(ApiUtils.STATUS_NOT_FOUND, "字典参数为空", null, null);
//		}
//		try{
//			Dict d = new Dict();
//			d.setType(type);
//			d.setLabel(label);
//			List<Dict> dictList = dictService.selectByWhere(new Wrapper(d));
//			Dict dict = new Dict();
//			if(!dictList.isEmpty()){
//				dict = dictList.get(0);
//			}
//			return ApiUtils.getMap(ApiUtils.STATUS_OK, "字典数据获取成功", dict, null);
//		}catch(Exception e){
//			logger.error("获取字典值参数错误:"+e.toString());
//			return ApiUtils.getMap(ApiUtils.STATUS_SERVER_ERROR, "服务器发生错误",null,null);
//		}
    }

    /**
     * 获取token(令牌)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/sys/getToken")
    public Map<String, Object> getToken() {
        try {
            SysToken sysToken = TokenUtils.generateToken(null, "10");
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取token成功"), sysToken.getToken(), null);
        } catch (Exception e) {
            logger.error("获取token发生错误", e);
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务器发生错误"), null, null);
        }
    }
}
