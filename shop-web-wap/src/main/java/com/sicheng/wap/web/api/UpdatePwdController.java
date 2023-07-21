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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicheng.admin.site.entity.SiteRegister;
import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.PasswordUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.wap.service.SiteRegisterService;
import com.sicheng.wap.service.UserMainService;
import com.sicheng.common.utils4m.ApiUtils;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;

/**
  * <p>标题: ForgetPwdController</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2018年1月28日 上午9:15:13
 */
@Controller
@RequestMapping(value = "${wapPath}/api")
public class UpdatePwdController extends BaseController {

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private SiteRegisterService siteRegisterService;

    /**
      * 忘记密码--修改成新密码
      * @return
     */
    @RequestMapping(value = "/{version}/user/updatePwd/edit")
    @ResponseBody
    public Map<String, Object> userUpdataPwdEdit() {
        //表单验证
        List<String> errorList = validate();
        if (errorList.size() > 0) {
            String message = ApiUtils.errorMessage(errorList);
            return AppDataUtils.getMap(AppDataUtils.STATUS_INVALID, message, null, null);
        }
        //具体业务
        String password = R.get("password");//新密码
        UserMain u = AppTokenUtils.findUser();
        UserMain userMain = new UserMain();
        userMain.setUId(u.getUId());
        userMain.setPassword(PasswordUtils.entryptPassword(password, u.getSalt()));
        userMainService.updateByIdSelective(userMain);
        return AppDataUtils.getMap(AppDataUtils.STATUS_OK, FYUtils.fy("修改密码成功"), null, null);
    }

    /**
     * 表单验证(修改密码)
     *
     * @param tradeComment 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate() {
        SiteRegister siteRegister = siteRegisterService.selectOne(new Wrapper());
        UserMain userMain = AppTokenUtils.findUser();
        List<String> errorList = new ArrayList<String>();
        String oldPassword = R.get("oldPassword");//旧密码
        String password = R.get("password");//新密码
        String repassword = R.get("repassword");//再次输入密码
        if (StringUtils.isBlank(oldPassword)) {
            errorList.add(FYUtils.fyParams("原密码不能为空"));
        }
        if (StringUtils.isNotBlank(oldPassword) && !PasswordUtils.validatePassword(oldPassword, userMain.getPassword(), userMain.getSalt())) {
            errorList.add(FYUtils.fyParams("原密码不正确"));
        }
        if (StringUtils.isBlank(password)) {
            errorList.add(FYUtils.fyParams("密码不能为空"));
        }
        if (siteRegister != null) {
            if (StringUtils.isNotBlank(password) && password.length() > siteRegister.getPwdMax()) {
                errorList.add(FYUtils.fyParams("密码最大长度",siteRegister.getUsernameMax()+""));
            }
            if (StringUtils.isNotBlank(password) && password.length() < siteRegister.getPwdMin()) {
                errorList.add(FYUtils.fyParams("密码最小长度",siteRegister.getUsernameMin()+""));
            }
            if (StringUtils.isNotBlank(repassword) && repassword.length() > siteRegister.getPwdMax()) {
            	errorList.add(FYUtils.fyParams("密码最大长度",siteRegister.getUsernameMax()+""));
            }
            if (StringUtils.isNotBlank(repassword) && repassword.length() < siteRegister.getPwdMin()) {
            	errorList.add(FYUtils.fyParams("密码最小长度",siteRegister.getUsernameMin()+""));
            }
        }
        if (!password.equals(repassword)) {
            errorList.add(FYUtils.fyParams("两次密码必须一致"));
        }
        return errorList;
    }
}
