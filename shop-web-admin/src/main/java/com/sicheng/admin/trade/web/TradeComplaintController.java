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
package com.sicheng.admin.trade.web;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.admin.sso.service.UserMainService;
import com.sicheng.admin.sys.entity.User;

import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.admin.trade.entity.TradeComplaint;
import com.sicheng.admin.trade.service.TradeComplaintService;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 投诉 Controller
 * 所属模块：trade
 *
 * @author 范秀秀
 * @version 2017-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tradeComplaint")
public class TradeComplaintController extends BaseController {

    @Autowired
    private TradeComplaintService tradeComplaintService;



    @Autowired
    private UserMainService userMainService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "030106";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入列表页
     *
     * @param tradeComplaint 实体对象
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:view")
    @RequestMapping(value = "list")
    public String list(TradeComplaint tradeComplaint, HttpServletRequest request, HttpServletResponse response, Model model) {
        //设置投诉的类型 0投诉、1申诉
        tradeComplaint.setType("0");
        //接收会员名
        String userName = R.get("userName");
        Long uId = 0L;
        if (!(userName == null || "".equals(userName))) {
            //用户名转小写
            userName = userName.toLowerCase();
            //用投诉人的名字去换取id
            UserMain userMain = new UserMain();
            userMain.setLoginName(userName);
            List<UserMain> userMemberList = userMainService.selectByWhere(new Wrapper(userMain));
            if (userMemberList.size() > 0) {
                uId = userMemberList.get(0).getUId();
                tradeComplaint.setUId(uId);
            } else {
                model.addAttribute("userName", userName);
                return "admin/trade/tradeComplaintList";
            }
        }
        Page<TradeComplaint> page = tradeComplaintService.selectByWhere(new Page<TradeComplaint>(request, response), new Wrapper(tradeComplaint));
        TradeComplaint.fillUser(page.getList());
        TradeComplaint.fillStore(page.getList());
        model.addAttribute("userName", userName);
        model.addAttribute("page", page);
        return "admin/trade/tradeComplaintList";
    }

//	/**
//	 * 进入保存页面
//	 * @param tradeComplaint 实体对象
//	 * @param model
//	 * @return
//	 */
//	@RequiresPermissions("trade:tradeComplaint:save")
//	@RequestMapping(value = "save1")
//	public String save1(TradeComplaint tradeComplaint, Model model) {
//		if (tradeComplaint == null){
//			tradeComplaint = new TradeComplaint();
//		}
//		model.addAttribute("tradeComplaint", tradeComplaint);
//		return "admin/trade/tradeComplaintForm";
//	}
//
//	/**
//	 * 执行保存
//	 * @param tradeComplaint 实体对象
//	 * @param model
//	 * @param redirectAttributes
//	 * @return
//	 */
//	@RequiresPermissions("trade:tradeComplaint:save")
//	@RequestMapping(value = "save2")
//	public String save2(TradeComplaint tradeComplaint, Model model, RedirectAttributes redirectAttributes) {
//		//表单验证
//		List<String> errorList=validate(tradeComplaint, model);
//		if(errorList.size()>0){
//			errorList.add(0, "数据验证失败：");
//			addMessage(model, errorList.toArray(new String[]{}));
//			return save1(tradeComplaint, model);//回显错误提示
//		}
//		//业务处理
//		tradeComplaintService.insertSelective(tradeComplaint);
//		addMessage(redirectAttributes, "保存投诉成功");
//		return "redirect:"+adminPath+"/trade/tradeComplaint/list.do?repage";
//	}

    /**
     * 进入编辑(处理投诉、查看投诉)页面
     *
     * @param tradeComplaint 实体对象
     * @param model
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:auth")
    @RequestMapping(value = "edit1")
    public String edit1(TradeComplaint tradeComplaint, Model model) {
        TradeComplaint entity = null;
        if (tradeComplaint != null) {
            if (tradeComplaint.getId() != null) {
                entity = tradeComplaintService.selectById(tradeComplaint.getId());
            }
        }
        String stat = R.get("stat");
        //stat:1处理,2查看
        model.addAttribute("stat", stat);
        model.addAttribute("tradeComplaint", entity);
        return "admin/trade/tradeComplaintEdit";
    }

    /**
     * 执行编辑(提交仲裁意见)
     *
     * @param tradeComplaint     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:auth")
    @RequestMapping(value = "edit2")
    public String edit2(TradeComplaint tradeComplaint, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(tradeComplaint, model);
        if (errorList.size() > 0) {
            errorList.add(0, FYUtils.fy("数据验证失败："));
            addMessage(model, errorList.toArray(new String[]{}));
            return edit1(tradeComplaint, model);//回显错误提示
        }
        // 投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
        tradeComplaint.setStatus("50");
        //获取到当前登录的用户，设置到adminId
        User user = UserUtils.getUser();//获取当前登录用户
        if (user != null) {
            Long adminId = user.getId();
            if (adminId != null) {
                tradeComplaint.setAdminId(adminId);//管理员(管理员表id)
            }
        }
        tradeComplaint.setSystemHandleTime(new Date());
        //业务处理
        tradeComplaintService.updateByIdSelective(tradeComplaint);
        addMessage(redirectAttributes, FYUtils.fy("仲裁投诉成功"));
        return "redirect:" + adminPath + "/trade/tradeComplaint/list.do?repage";
    }

    /**
     * 审核并交由商家处理
     *
     * @param tradeComplaint     实体对象
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:auth")
    @RequestMapping(value = "audit")
    public String audit(TradeComplaint tradeComplaint, Model model, RedirectAttributes redirectAttributes) {
        if (tradeComplaint != null) {
            //投诉状态，10新投诉、20待申诉、30对话中、40待仲裁、50已完成
            tradeComplaint.setStatus("20");
        }
        //业务处理
        tradeComplaintService.updateByIdSelective(tradeComplaint);
        addMessage(redirectAttributes, FYUtils.fy("审核投诉成功"));
        return "redirect:" + adminPath + "/trade/tradeComplaint/list.do?repage";
    }

    /**
     * 删除
     *
     * @param tradeComplaint     实体对象
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("trade:tradeComplaint:drop")
    @RequestMapping(value = "delete")
    public String delete(TradeComplaint tradeComplaint, RedirectAttributes redirectAttributes) {
        tradeComplaintService.deleteById(tradeComplaint.getId());
        addMessage(redirectAttributes, FYUtils.fy("删除投诉成功"));
        return "redirect:" + adminPath + "/trade/tradeComplaint/list.do?repage";
    }

    /**
     * 表单验证
     *
     * @param tradeComplaint 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(TradeComplaint tradeComplaint, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("systemHandleHandelOpinion"))) {
            errorList.add(FYUtils.fy("处理意见不能为空"));
        }
        if (StringUtils.isNotBlank(R.get("systemHandleHandelOpinion")) && R.get("systemHandleHandelOpinion").length() > 1024) {
            errorList.add(FYUtils.fy("处理意见最大长度不能超过1024字符"));
        }
        return errorList;
    }

}