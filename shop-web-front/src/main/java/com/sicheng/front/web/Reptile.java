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
package com.sicheng.front.web;

import com.sicheng.front.service.StoreEnterAuthService2;
import com.sicheng.seller.product.service.ProductConfigService;
import com.sicheng.seller.product.service.ProductSkuService;
import com.sicheng.seller.product.service.ProductSpuService;
import com.sicheng.seller.store.service.StoreEnterService;
import com.sicheng.sso.service.UserMainService;
import com.sicheng.sso.service.UserSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 抓取数据用
 */
@Controller
@RequestMapping(value = "/api/store/reptile")
public class Reptile {
    @Autowired
    private StoreEnterAuthService2 storeEnterAuthService;
    @Autowired
    private StoreEnterService storeEnterService;
    @Autowired
    private ProductConfigService productConfigService;
    @Autowired
    private ProductSpuService productSpuService;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private UserSellerService userSellerService;

//    /**
//     * 新增店铺
//     *
//     * @param store
//     * @param companyIntroduction
//     * @param contactUs
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/add")
//    public String list(Store store, String companyIntroduction, String contactUs) {
//        companyIntroduction = StringEscapeUtils.unescapeHtml4(companyIntroduction);
//        companyIntroduction = XssClean.clean(companyIntroduction);
//        contactUs = StringEscapeUtils.unescapeHtml4(contactUs);
//        contactUs = XssClean.clean(contactUs);
//        Long storeId = storeEnterAuthService.initStore(store, companyIntroduction, contactUs);
//        return "" + storeId;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "save3")
//    public String save3(ProductSpu productSpu, Model model, RedirectAttributes redirectAttributes) {
//        productSpu.setShelfTime(new Date());
//        //初始化的商品默认推荐
//        productSpu.setIsRecommend("1");//是否推荐，0非，1是
//        //根据商品是否审核的设置，修改商品的状态,10仓库中商品，20禁售商品，30等待审核商品，40审核失败商品，50在售商品
//        String status = "50";
//        //发布,0放入仓库中，1立即发布
//        String publish = R.get("publish");
//        //商品分类参数
//        //车系
//        String[] carIds = R.getArray("carIds");
//        //是否全车系
//        String allCar = R.get("allCar");
//        //参数id_参数名
//        String param = R.get("selectParams");
//        String[] params = null;
//        if (StringUtils.isNotBlank(param)) {
//            params = param.split(",");
//        }
//        //参数值图片
//        String[] valuesImg = R.getArray("valuesImg");
//        //参数类型：1核心参数、2辅助参数、3自定义参数
//        String[] type = R.getArray("paramType");
//        //参数格式：1图片、2文字、3文字+图片（颜色要配图片）
//        String[] format = R.getArray("format");
//        //参数名
//        String[] name = R.getArray("paramName");
//        Map<String, String[]> paramMap = new HashMap<>();
//        paramMap.put("params", params);
//        paramMap.put("valuesImg", valuesImg);
//        paramMap.put("type", type);
//        paramMap.put("format", format);
//        paramMap.put("name", name);
//        //商品区间价
//        String section = R.get("section");
//        String[] sections = section.split(",");
//        //商品规格
//        String productSku1 = R.get("productSku");
//        //String[] productSku2=productSku1.split("-");
//        String[] productSku2 = R.get("productSpec").split("/");
//        //获取商品详情
//        String detail = R.get("introduction");
//        String htmlUnsafe = StringEscapeUtils.unescapeHtml4(detail);//转回来（还原）
//        String detailSafe = XssClean.clean(htmlUnsafe);//按白名单进行危险字符过滤
//        //商品图片(颜色：图片id-图片id)
//        String productImgs = R.get("productImgs");
//        String[] productImgArray = productImgs.split(",");
//        //业务处理
//        productSpuService.publishProduct(paramMap, sections, productSku2, productSpu, detailSafe, productImgArray, carIds, allCar);
//        redirectAttributes.addFlashAttribute("productSpu", productSpu);
//        redirectAttributes.addFlashAttribute("menu3", "020105");//发布商品菜单
//
//
//        //按ID添加索引（异步线程池）
//        SolrSearchProduct.getInstance().addByIdAsyn(productSpu.getPId());
//
//        return "1";
//    }
//
//    /**
//     * 修复名称中带有 & 导致无法登陆的问题
//     *
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/update/loginName")
//    public String update() {
//        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper().and("login_name like", "%&%"));
//        System.out.println("需要修复" + userMains.size() + "条数据");
//        for (UserMain userMain : userMains) {
//            userMain.setSalt(IdGen.randomBase62(32));
//            String loginName = userMain.getLoginName().replaceAll("&", "");
//            Integer number = 0;
//            while (true) {
//                String buffer = loginName;
//                if (number != 0) {
//                    buffer += number;
//                }
//                if (this.duplicateNameCheck(buffer)) {
//                    loginName = number == 0 ? loginName : loginName + buffer;
//                    break;
//                }
//                number++;
//            }
//            userMain.setLoginName(loginName);
//            userMain.setPassword(PasswordUtils.entryptPassword(userMain.getLoginName() + "168", userMain.getSalt()));
//            userMainService.updateByIdSelective(userMain);
//        }
//        return "结束";
//    }
//
//    /**
//     * 重名检查
//     *
//     * @param name
//     * @return
//     */
//    private Boolean duplicateNameCheck(String name) {
//        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper().and("login_name =", name));
//        if (userMains != null && userMains.size() > 0) {
//            return false;
//        }
//        return true;
//    }


//    @ResponseBody
//    @RequestMapping(value = "/update")
//    public String update() {
//        List<UserMain> userMains = userMainService.selectByWhere(new Wrapper().and("type_user = ", "0000001111"));
//        for (UserMain userMain : userMains) {
//            if (userSellerService.selectById(userMain.getUId()) == null) {
//                System.out.println("需要删除，id：" + userMain.getUId());
//                userMainService.deleteById(userMain.getUId());
//            } else {
//                System.out.println("不需要删除，id：" + userMain.getUId());
//            }
//        }
//        return "结束";
//    }

}
