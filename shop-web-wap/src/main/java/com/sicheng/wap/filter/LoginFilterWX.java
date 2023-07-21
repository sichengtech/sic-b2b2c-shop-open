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
package com.sicheng.wap.filter;

import com.sicheng.admin.sso.entity.UserMain;
import com.sicheng.common.config.Global;
import com.sicheng.common.mapper.JsonMapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.R;
import com.sicheng.common.web.Servlets;
import com.sicheng.common.utils4m.AppDataUtils;
import com.sicheng.common.utils4m.AppTokenUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
  * <p>标题: LoginFilterWX</p>
  * <p>描述: </p>
  * <p>公司: 思程科技 www.sicheng.net</p>
  * @author zhangjiali
  * @version 2018年1月27日 上午11:11:17
 */
public class LoginFilterWX implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 获得在下面代码中要用的request,response,session对象
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        //判断这次请求是否是 OPTION 请求
        Boolean isOptions = CorsFilter.isOptions(servletRequest);
        if (isOptions) {
            chain.doFilter(servletRequest, servletResponse);//放过
            return;
        }

        // 获得用户请求的URI
        String path = servletRequest.getRequestURI();//获取访问地址(不带参数，用于过滤匿名地址)
        //具有匿名权限的Url--不做拦截
        List<String> list = getAnonymousUrl();
        for (String url : list) {
            if (path.contains(url)) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        if (StringUtils.isNotBlank(servletRequest.getQueryString())) {
            path = servletRequest.getRequestURI() + "?" + servletRequest.getQueryString();//获取访问地址(带参数，用于登录成功后重定向)
        }

        //获取当前登录用户
        //如果是wap微信商城，从Cookie Session中获取当前登录用户
        //如果是uni-app开发的app、小程序、h5,使用AppToken来获取当前登录用户
        UserMain userMain = AppTokenUtils.findUser();
        //UserMain userMain=(UserMain) servletRequest.getSession().getAttribute(AppTokenUtils.USER_SESSION_KEY);
        // 判断如果没有取到会员信息,就跳转到登录页面
        if (userMain == null) {
            if (Servlets.isAjaxRequest2(servletRequest) || AppTokenUtils.isAppRequest()) {
                // 是wap微信商城的ajax请求，或是app发来的请求，都返回json
                String json = JsonMapper.getInstance().toJson(AppDataUtils.getMap(AppDataUtils.STATUS_UNAUTHORIZED, "没有登录", null, null));
                R.writeJson(servletResponse, json, "UTF-8");
                return;
            } else {
                //返回html页面
                servletRequest.getSession().setAttribute("path", path);
                // 进入微信用户授权流程
//				String flag="1";//入口类型
//				WeiXinUtils.weixinAccredit(servletResponse, flag);
                servletResponse.sendRedirect(servletRequest.getContextPath() + Global.getFrontPath() + "/user/login/form.htm");
                return;
            }
        } else {
            // 已经是登陆状态,继续此次请求
            chain.doFilter(servletRequest, servletResponse);
            //在最后一步，为AppToken续命
            AppTokenUtils.touchAppToken();
            return;
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 获取匿名权限的地址
     */
    private List<String> getAnonymousUrl() {
        // 是否包含以下链接--有以下链接不能拦截
        List<String> list = new ArrayList<>();
        // 路由
        list.add("/user/register/form.htm"); // 进入微商城的注册页面
        list.add("/user/login/form.htm"); // 进入微商城的登录页面
        list.add("/user/forgetPwd/form.htm"); // 进入微商城的忘记密码页面
        list.add("/index.htm"); // 进入微商城的首页
        list.add("/categoryIndex.htm"); // 进入微商城的分类首页
        list.add("/store/error.htm"); // 进入微商城的店铺不存在页面
        list.add("/store/article/list.htm"); // 进入微商城的店铺文章列表页面
        list.add("/store/article/detail.htm");// 进入微商城的店铺文章详情页面
        list.add("/product/category/list.htm");// 进入微商城的商品分类页
        list.add("/store/index.htm");// 进入微商城的店铺主页
        list.add("/product/list.htm");// 进入微商城的商品列表
        list.add("/store/storeProduct/list.htm");// 进入微商城的店铺商品列表
        list.add("/product/detail.htm");// 进入微商城的产品详情页
        list.add("/trade/consultation/list.htm");// 进入微商城的商品咨询列表页
        list.add("/trade/comment/list.htm");// 进入微商城的商品评价列表
        list.add("/product/param/list.htm");// 根据商品id查询商品参数
        list.add("/product/error.htm");// 进入商品不存在页
        list.add("/webRequest/error.htm");// 网络请求发生异常，返回错误页面
        // 接口
        list.add("/errorTest.htm");// 异常测试接口，用于产生一个服务端异常
        list.add("/restTest.htm");// 异常测试接口，用于产生一个服务端异常
        list.add("/app/ad/list.htm");// 开屏广告列表
        list.add("/app/version/check.htm");// 开屏广告列表
        list.add("/product/productCategory/list.htm");// 商品分类列表
        list.add("/site/carouselPicture/list.htm");// 商品分类列表
        list.add("/site/HotSearchWord/list.htm");// 商品分类列表
        list.add("/store/page.htm");// 商品分类列表
        list.add("/product/all.htm");// 商品分类列表
        list.add("/store/one/all.htm");// 商品分类列表

        list.add("/sys/area/selectArea.htm");// 查询地区数据
        list.add("/user/register/save.htm");// 保存注册信息
        list.add("/user/login/authentication.htm");// wap登录认证(基于cookie和session)
        list.add("/user/login/app.htm");// APP登录认证(基于token,专为APP开发)
        list.add("/user/forgetPwd/edit.htm");// 提交忘记密码的信息,保存新密码
        list.add("/user/list.htm");// 根据用户list
        list.add("/getCode.htm");// 获取验证码（手机号注册、账号注册、手机号登录、手机号找回密码、邮箱地址找回密码）
        list.add("/site/carouselPicture/list.htm");// 获取轮播图
        list.add("/site/recommend/one.htm");// 获取推荐位
        list.add("/site/ad/one.htm");// 获取广告位
        list.add("/site/info.htm");// 获取网站信息
        list.add("/store/article/page.htm");// 获取店铺文章列表
        list.add("/store/article/one.htm");// 获取店铺文章
        list.add("/sys/dict/list.htm");// 根据字典类型获取字典列表
        list.add("/sys/dict/labelOne.htm");// 获取字典键
        list.add("/sys/dict/labelList.htm");// 获取多个字典键
        list.add("/sys/dict/valueOne.htm");// 获取字典值
        list.add("/store/one.htm");// 获取店铺信息
        list.add("/store/list.htm");// 获取多个店铺信息
        list.add("/store/category/list.htm");// 获取店铺分类
        list.add("/store/nav/count.htm");// 获取店铺头部数据
        list.add("/store/carouselPicture/list.htm");// 获取店铺轮播图
        list.add("/product/page.htm");// 根据条件来获取商品
        list.add("/product/list.htm");// 根据条件来获取商品
        list.add("/product/one.htm");// 根据商品id查询商品
        list.add("/product/sectionPrice/list.htm");// 获取商品批发价
        list.add("/product/getSectionPrice.htm");// 获取商品批发价
        list.add("/product/sku/list.htm");// 根据商品id查询商品
        list.add("/product/sku/one.htm");// 根据商品id查询商品
        list.add("/product/image/list.htm");// 根据商品id获取商品图片
        list.add("/product/detail.htm");// 获取商品描述详情
        list.add("/product/comment/list.htm");// 获取商品评价list
        list.add("/product/comment/page.htm");// 获取商品评价page
        list.add("/product/consultation/list.htm");// 获取商品咨询list
        list.add("/product/consultation/page.htm");// 获取商品咨询page
        list.add("/product/comment/image/list.htm");// 获取商品评价图片list
        list.add("/trade/consultation/list.htm");// 获取商品咨询list信息
        list.add("/trade/consultation/page.htm");// 获取商品咨询page信息
        list.add("/siteRegister/info.htm");// 获取网站注册设置信息
        //地址
        list.add("/oauth2/wxlogin.htm");// 微信回调，获取微信openId
        list.add("/weixin/payNotify.htm");// 微信支付通知
        //获取国际化配置文件内容
        list.add("/fy/poperties.htm");// 获取国际化配置
        return list;
    }
}
