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

import com.sicheng.admin.member.entity.MemberCollectionProduct;
import com.sicheng.admin.member.entity.MemberCollectionStore;
import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.entity.UserMember;
import com.sicheng.admin.sys.entity.SysMessage;
import com.sicheng.admin.sys.entity.SysVariable;
import com.sicheng.admin.trade.entity.TradeCart;
import com.sicheng.admin.trade.entity.TradeComment;
import com.sicheng.admin.trade.entity.TradeConsultation;
import com.sicheng.admin.trade.entity.TradeOrder;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.DateUtils;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.*;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 *  <p>标题: UserInfoController</p>
 *  <p>描述: </p>
 *  <p>公司: 思程科技 www.sicheng.net</p>
 *  @author zhangjiali
 *  @version 2018年2月11日 下午3:27:23
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
//@CrossOrigin(origins = "*", maxAge = 3603, allowedHeaders="AppToken,TerminalType")
public class UserInfoController extends BaseController {

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private UserMemberService userMemberService;

    @Autowired
    private SiteRegisterService siteRegisterService;

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private TradeCartService tradeCartService;

    @Autowired
    private MemberCollectionStoreService memberCollectionStoreService;

    @Autowired
    private MemberCollectionProductService memberCollectionProductService;

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private TradeCommentService tradeCommentService;

    @Autowired
    private TradeConsultationService tradeConsultationService;

    @Autowired
    private SysVariableService variableService;

    /**
     *  获取登录用户的信息 
     *  @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/one")
    public Map<String, Object> userOne() {
        UserMain userMain = AppTokenUtils.findUser();//属主检查
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("uid", userMain.getUId());//用户id
        map.put("loginName", userMain.getLoginName());//用户名
        map.put("isOperation", userMain.isOperation());//是否被激活,激活返回false
        map.put("typeUser", userMain.getTypeUserDefault());//获取用户类型（个人买家、采购商、门店服务商、卖家）
        UserMember userMember = userMain.getUserMember();//得到买家会员，如果当前会员是买家才能取出值来
        if (userMember != null) {
            //只有买会员才有头像
            map.put("headPicPath", userMember.getHeadPicPath());//用户头像
        }
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("用户获取成功"), map, null);
    }

    /**
     * 根据多个用户id获取用户信息列表
     *
     * @param uids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/list")
    public Map<String, Object> userList(String uids) {
        if (StringUtils.isBlank(uids)) {
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, FYUtils.fy("用户id不能为空"), null, null);
        }
        try {
            Object[] uidArr = uids.split(",");
            List<Object> uidList = Arrays.asList(uidArr);
            List<UserMain> userMainList = userMainService.selectByWhere(new Wrapper().and("u_id in", uidList));
            if (userMainList == null || userMainList.isEmpty()) {
                return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询用户成功"), userMainList, null);
            }
            List<Object> userList = new ArrayList<>();
            for (int i = 0; i < userMainList.size(); i++) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("uid", userMainList.get(i).getUId());
                map.put("loginName", userMainList.get(i).getLoginName());
                userList.add(map);
            }
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("查询用户成功"), userMainList, null);
        } catch (Exception e) {
            logger.error("获取用户错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("服务发生错误"), null, null);
        }
    }

    /**
     * 根据用户id获取会员买家用户信息
     *
     * @param uid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/member")
    public Map<String, Object> userMember() {
        try {
            UserMember userMember = userMemberService.selectById(AppTokenUtils.findUser().getUId());
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("uid", userMember.getUId());//用户id
            map.put("headPicPath", userMember.getHeadPicPath());//用户头像
            if (userMember.getBirthday() != null) {
                map.put("birthday", DateUtils.formatDate(userMember.getBirthday(), "yyyy-MM-dd"));//出生日期
            }
            map.put("sex", userMember.getSex());//性别
            map.put("provinceId", userMember.getProvinceId());//省id
            map.put("provinceName", userMember.getProvinceName());//省名字
            map.put("cityId", userMember.getCityId());//市id
            map.put("cityName", userMember.getCityName());//市名字
            map.put("districtId", userMember.getDistrictId());//县id
            map.put("districtName", userMember.getDistrictName());//县名字
            map.put("realName", StringUtils.isBlank(userMember.getRealName()) ? "'" : userMember.getRealName());//真实姓名
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取用户成功"), map, null);
        } catch (Exception e) {
            logger.error("获取用户错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取用户错误"), null, null);
        }
    }

    /**
     * 查询用户中心页面各模块的数量
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userCentral/count")
    public Map<String, Object> userCentralCount() {
        UserMain userMain = AppTokenUtils.findUser();//获取登录的用户信息
        //待付款订单
        TradeOrder tradeOrder1 = new TradeOrder();
        tradeOrder1.setUId(userMain.getUId());//属主检查
        tradeOrder1.setOrderStatus("10");// 订单状态，10待付款、20待发货、30待收货、40(已完成)待评价、 50已评价、60已取消、70退货退款中
        //待发货订单
        TradeOrder tradeOrder2 = new TradeOrder();
        tradeOrder2.setUId(userMain.getUId());//属主检查
        tradeOrder2.setOrderStatus("20");
        //待收货订单
        TradeOrder tradeOrder3 = new TradeOrder();
        tradeOrder3.setUId(userMain.getUId());//属主检查
        tradeOrder3.setOrderStatus("30");
        //待评价订单
        TradeOrder tradeOrder4 = new TradeOrder();
        tradeOrder4.setUId(userMain.getUId());//属主检查
        tradeOrder4.setOrderStatus("40");
        //购物车
        TradeCart tradeCart = new TradeCart();
        tradeCart.setUId(userMain.getUId());//属主检查
        //收藏店铺
        MemberCollectionStore memberCollectionStore = new MemberCollectionStore();
        memberCollectionStore.setUId(userMain.getUId());//属主检查
        //收藏商品
        MemberCollectionProduct memberCollectionProduct = new MemberCollectionProduct();
        memberCollectionProduct.setUId(userMain.getUId());//属主检查
        //消息
        SysMessage sysMessage = new SysMessage();
        sysMessage.setUId(userMain.getUId());//属主检查
        sysMessage.setReading("0");//0未读、1已读 （只限站内信）
        //评价
        TradeComment tradeComment = new TradeComment();
        tradeComment.setUId(userMain.getUId());//属主检查
        //咨询
        TradeConsultation tradeConsultation = new TradeConsultation();
        tradeConsultation.setUId(userMain.getUId());//属主检查
        try {
            Integer orderStatus10 = tradeOrderService.countByWhere(new Wrapper(tradeOrder1));//待付款订单
            Integer orderStatus20 = tradeOrderService.countByWhere(new Wrapper(tradeOrder2));//待发货订单
            Integer orderStatus30 = tradeOrderService.countByWhere(new Wrapper(tradeOrder3));//待收货订单
            Integer orderStatus40 = tradeOrderService.countByWhere(new Wrapper(tradeOrder4));//待评价订单
            Integer tradeCarts = tradeCartService.countByWhere(new Wrapper(tradeCart));//购物车
            Integer collectionStores = memberCollectionStoreService.countByWhere(new Wrapper(memberCollectionStore));//收藏店铺
            Integer collectionProducts = memberCollectionProductService.countByWhere(new Wrapper(memberCollectionProduct));//收藏商品
            Integer messages = sysMessageService.countByWhere(new Wrapper(sysMessage));//消息
            Integer tradeComments = tradeCommentService.countByWhere(new Wrapper(tradeComment));//评价
            Integer tradeConsultations = tradeConsultationService.countByWhere(new Wrapper(tradeConsultation));//咨询
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderStatus10", orderStatus10);
            map.put("orderStatus20", orderStatus20);
            map.put("orderStatus30", orderStatus30);
            map.put("orderStatus40", orderStatus40);
            map.put("tradeCarts", tradeCarts);
            map.put("collectionStores", collectionStores);
            map.put("collectionProducts", collectionProducts);
            map.put("messages", messages);
            map.put("tradeComments", tradeComments);
            map.put("tradeConsultations", tradeConsultations);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("获取用户各模块的数量成功"), map, null);
        } catch (Exception e) {
            logger.error("获取用户各模块的数量错误:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("获取用户各模块的数量错误"), null, null);
        }
    }

    /**
     * 编辑用户信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{version}/user/userInfo/edit")
    public Map<String, Object> userInfoEdit() {
        //表单验证
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());//用户注册设置
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("loginName"))) {
            errorList.add(FYUtils.fy("用户名不能为空"));
        }
        if (siteRegister == null && R.get("loginName").length() > 64) {
            errorList.add(FYUtils.fy("用户名不能超过64字符"));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() > siteRegister.getUsernameMax()) {
            errorList.add(FYUtils.fyParam("用户名最大长度", siteRegister.getUsernameMax() + ""));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("loginName")) && R.get("loginName").length() < siteRegister.getUsernameMin()) {
            errorList.add(FYUtils.fyParam("用户名最小长度", siteRegister.getUsernameMin() + ""));
        }
        if (siteRegister != null && StringUtils.isNotBlank(R.get("loginName")) && StringUtils.isNotBlank(siteRegister.getDisableUsername())) {
            String[] disableName = siteRegister.getDisableUsername().split(",");
            for (int i = 0; i < disableName.length; i++) {
                if (R.get("loginName").equals(disableName)) {
                    errorList.add(FYUtils.fy("用户名不能是") + disableName);
                }
            }
        }
        if (StringUtils.isBlank(R.get("realName"))) {
            errorList.add(FYUtils.fy("真实姓名不能为空"));
        }
        if (siteRegister == null && R.get("realName").length() > 64) {
            errorList.add(FYUtils.fy("真实姓名不能超过64字符"));
        }
        UserMain entity = AppTokenUtils.findUser();//获取登录的用户信息
        if (!entity.getLoginName().equals(R.get("loginName")) && StringUtils.isNotBlank(R.get("loginName"))) {
            UserMain userMain = new UserMain();
            userMain.setLoginName(R.get("loginName").toLowerCase());
            List<UserMain> list = userMainService.selectByWhere(new Wrapper(userMain));
            if (!list.isEmpty()) {
                errorList.add(FYUtils.fy("账号已存在，请输入其他账号"));
            }
        }

        //获取后台会员名修改次数
        SysVariable variable = variableService.getSysVariable("user_name_update_num");
        Long loginNameEditCount = 0L;//默认值，0代表可以随意修改
        if (variable != null && StringUtils.isNotBlank(variable.getValue())) {
            if (StringUtils.isNumeric(variable.getValue())) {
                loginNameEditCount = Long.parseLong(variable.getValue());
            }
        }
        //验证修改次数
        String today = DateUtils.getYear() + "-" + DateUtils.getMonth();
        if (StringUtils.isNotBlank(R.get("loginName")) && !entity.getLoginName().equals(R.get("loginName"))) {
            //修改用户总表
            int modifyCount = entity.getModifyCount();
            //0代表可以随意修改
            if (loginNameEditCount == 0) {
                modifyCount += 1;
                entity.setModifyCount(modifyCount);
                entity.setModifyDate(today);
                userMainService.updateByIdSelective(entity);
            } else {
                if (StringUtils.isBlank(entity.getModifyDate())) {
                    //当前年月和修改年月不一致
                    entity.setModifyCount(1);
                    entity.setModifyDate(today);
                    userMainService.updateByIdSelective(entity);
                } else {
                    //判断用户名变更年月字段是否为空
                    if (today.equals(entity.getModifyDate())) {
                        //当前年月和修改年月一致
                        if (modifyCount < loginNameEditCount) {
                            //当前的修改次数少于用户名修改次数
                            modifyCount += 1;
                            entity.setModifyCount(modifyCount);
                            entity.setModifyDate(today);
                            userMainService.updateByIdSelective(entity);
                        } else {
                            //当前的修改次数大于等于用户名修改次数
                            errorList.add(FYUtils.fyParams("修改失败,用户名在当前月份修改达到上限"));
                        }
                    } else {
                        //当前年月和修改年月不一致
                        entity.setModifyCount(1);
                        entity.setModifyDate(today);
                        userMainService.updateByIdSelective(entity);
                    }
                }
            }
        }
        if (errorList.size() > 0) {
            String message = ApiUtils.errorMessage(errorList);
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //具体业务
        try {
            String headPicPath = R.get("headPicPath");//头像
            String loginName = R.get("loginName");//用户名
            String sex = R.get("sex");//性别
            Date birthday = R.getDate("birthday", "yyyy-MM-dd");//出生日期
            Long provinceId = R.getLong("provinceId");//省id
            String provinceName = R.get("provinceName");//省名字
            Long cityId = R.getLong("cityId");//市id
            String cityName = R.get("cityName");//市名字
            Long districtId = R.getLong("districtId");//县id
            String districtName = R.get("districtName");//县名字
            String realName = R.get("realName");
            UserMain user = new UserMain();
            user.setUId(entity.getUId());//属主检查
            user.setLoginName(loginName.toLowerCase());
            UserMember userMember = new UserMember();
            userMember.setUId(entity.getUId());
            userMember.setSex(sex);
            userMember.setHeadPicPath(headPicPath);
            userMember.setBirthday(birthday);
            userMember.setProvinceId(provinceId);
            userMember.setProvinceName(provinceName);
            userMember.setCityId(cityId);
            userMember.setCityName(cityName);
            userMember.setDistrictId(districtId);
            userMember.setDistrictName(districtName);
            userMember.setRealName(realName);
            userMainService.editUserInfo(user, userMember);
            return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("修改账号信息成功"), "/user/userCentral.htm", null);
        } catch (Exception e) {
            logger.error("退出登录失败:" , e );
            return AppDataUtils.getMap(AppDataUtils.STATUS_SERVER_ERROR, FYUtils.fy("修改账号信息失败"), null, null);
        }
    }
}
