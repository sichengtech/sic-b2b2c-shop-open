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

import com.sicheng.admin.cms.entity.Article;
import com.sicheng.admin.cms.entity.Category;
import com.sicheng.admin.product.entity.ProductCar;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import com.sicheng.front.service.ArticleService;
import com.sicheng.front.service.CategoryService;
import com.sicheng.front.service.ProductCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>标题: front系统的入口页面(路由页面)</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cl
 * @version 2017年4月17日 下午3:28:05
 */
@Controller
@RequestMapping(value = "${frontPath}")
public class RouteController extends BaseController {

    @Autowired
    private ProductCarService productCarService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("ctx", R.getCtx());
        model.addAttribute("ctxf", Global.getConfig("ctx_front") + Global.getFrontPath());
        model.addAttribute("ctxs", Global.getConfig("ctx_front") + Global.getSellerPath());
        model.addAttribute("ctxm", Global.getConfig("ctx_front") + Global.getMemberPath());
        model.addAttribute("ctxsso", Global.getConfig("ctx_front") + Global.getSsoPath());
        model.addAttribute("ctxa", Global.getConfig("ctx_admin") + Global.getAdminPath());
        model.addAttribute("ctxw", Global.getConfig("ctx_wap") + Global.getWapPath());
        model.addAttribute("ctxu", Global.getConfig("ctx_upload") + Global.getUploadPath());
        model.addAttribute("ctxStatic", Global.getConfig("ctx_static") + "/static");
        model.addAttribute("ctxfs", Global.getConfig("ctx_upload") + Global.getUploadPath() + Global.getConfig("filestorage.dir"));
    }

    /**
     * 进入front系统的首页，商城的大首页
     * http://www.one.com
     * http://www.one.com/index.htm
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/index";
    }

    /**
     * 进入front系统的商品品牌街
     * http://www.one.com/storeBrand.htm
     */
    @RequestMapping(value = "/storeBrand")
    public String storeBrand(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("name", "storeBrand");
        return "/storeBrand";
    }

    /**
     * 进入店铺列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/store/list")
    public String storeList(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/storeList";
    }

    /**
     * 进入front系统的店铺首页
     * http://www.one.com/store/1.htm 1号店铺
     */
    @RequestMapping(value = "/store/{sid}")
    public String store(@PathVariable String sid, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("sid", sid);
        return "/storeIndex";
    }

    /**
     * 进入front系统的店铺产品页面
     * http://www.one.com/store/1.htm 1号店铺
     */
    @RequestMapping(value = "/store/{sid}/pList")
    public String storePList(@PathVariable String sid, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("sid", sid);
        return "/storePList";
    }

    /**
     * 进入front系统的店铺文章页面
     */
    @RequestMapping(value = "/store/{sid}/article")
    public String storeArticle(@PathVariable String sid, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("sid", sid);
        return "/storeArticle";
    }

    /**
     * 进入front系统的一级分类首页(淘气猫使用)
     * http://www.one.com/categoryOne.htm?cid=1
     */
    @RequestMapping(value = "/categoryOne/{cid}")
    public String categoryOne(@PathVariable String cid, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("cid", cid);
        return "/categoryOne";
    }

    /**
     * 进入front系统的二级分类首页(淘气猫使用)
     * http://www.one.com/categoryTwo.htm?cid=1
     */
    @RequestMapping(value = "/categoryTwo/{cid}")
    public String categoryTwo(@PathVariable String cid, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("cid", cid);
        return "/categoryTwo";
    }

    /**
     * 进入front系统的分类首页
     *
     * @return
     */
    @RequestMapping(value = "/category/{cid}")
    public String category(@PathVariable String cid, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("cid", cid);
        return "/categoryIndex";
    }

    /**
     * 进入front系统的产品列表
     * http://www.one.com/product/list.htm?cid=5 按分类
     * http://www.one.com/product/list.htm?w=手机 按关键字
     * http://www.one.com/search.htm?w=手机 （暂时不用）
     */
    @RequestMapping(value = "/product/list")
    public String productList(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/productList";
    }

    /**
     * 进入front系统的产品详情页 （按产品pid来查询）
     * http://www.one.com/detail/100.htm
     */
    @RequestMapping(value = "/detail/{pid}")
    public String productDetail(@PathVariable String pid, HttpServletRequest request, HttpServletResponse response, Model model) {
        //request.setAttribute("sid", 1);
        //request.setAttribute("pid",pid);
        model.addAttribute("pid", pid);
        return "/productDetail";
    }

    /**
     * 进入单页、扩展页
     * http://www.one.com/custom/xxxx.htm
     */
    @RequestMapping(value = "/custom/{name}")
    public String custom(@PathVariable String name, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("name", name);
        return "/custom/" + name;
    }

    /**
     * 进入front系统的活动中心页
     */
    @RequestMapping(value = "/activityCenter")
    public String activityCenter(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/activityCenter";
    }

    /**
     * 进入front系统的排行榜页
     */
    @RequestMapping(value = "/ranking")
    public String ranking(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/ranking";
    }

    /**
     * 进入front系统的文章列表页
     *
     * @param channelId 文章栏目id
     */
    @RequestMapping(value = "/articList/{channelId}")
    public String articList(@PathVariable String channelId, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("channelId", channelId);
        return "/articleList";
    }

    /**
     * 进入front系统的文章详情页
     *
     * @param articleId 文章id
     */
    @RequestMapping(value = "/articleDetail/{articleId}")
    public String articleDetail(@PathVariable String articleId, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("articleId", articleId);
        return "/articleDetail";
    }

    /**
     * 进入front系统的新闻首页
     */
    @RequestMapping(value = "/newsIndex")
    public String newsIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/newsIndex";
    }

    /**
     *  进入front系统的新闻列表页 
     *  @param channelId 新闻栏目id
     */
    @RequestMapping(value = "/newsList/{channelId}")
    public String newsList(@PathVariable String channelId, HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("channelId", channelId);
        return "/newsList";
    }

    /**
     * 进入front系统的新闻详情页
     *
     * @param articleId 新闻id
     */
    @RequestMapping(value = "/newsDetail/{articleId}")
    public String newsDetail(@PathVariable String articleId, HttpServletRequest request, HttpServletResponse response, Model model) {

        //点击量原子性自增
        articleService.raiseHitsById(new Long(articleId));

        //父栏目的ID，在此栏目下查找"推荐文章"
        Long pId=843L;

        //先查找栏目
        Wrapper w=new Wrapper();
        w.and("del_flag =",'0');
        w.and("parent_ids like", "%,"+pId+",%");
        List<Category> list=categoryService.selectByWhere(w);
        List<Long> cidList=new ArrayList<Long>();
        for(int i=0;i<list.size();i++){
            Category c=list.get(i);
            cidList.add(c.getId());
        }

        //查询相关文章,随机查询出4篇文章
        Article article = new Article();
        article.setDelFlag("0");
        Wrapper w2=new Wrapper(article);
        w2.and("category_id in",cidList);
        Page<Article> page = new Page<Article>(request, response);
        page.setPageSize(4);
        page = articleService.selectByWhere(page, w2);
        int count=page.getTotalPage();//总页数
        Random r=new Random();
        int pageNo=r.nextInt(count);
        page.setPageNo(pageNo);//随机页码
        page = articleService.selectByWhere(page, w2);//查询第N页，是随机页码
        if (!page.getList().isEmpty()) {
            for (Article entity : page.getList()) {
                if (entity.getArticleData().getContent().length() > 150) {
                    entity.getArticleData().setContent(StringUtils.abbr(entity.getArticleData().getContent(), 280));
                } else {
                    entity.getArticleData().setContent(StringUtils.replaceHtml(entity.getArticleData().getContent()));
                }
            }
        }
        model.addAttribute("list", page.getList());
        request.setAttribute("articleId", articleId);
        return "/newsDetail";
    }


    /**
     * 进入车型库主页
     * http://www.one.com/custom/xxxx.htm
     */
    @RequestMapping(value = "/carSearch")
    public String carSearch(HttpServletRequest request, HttpServletResponse response, Model model) {
        String pinpaiFirstLetter = R.get("pinpaiFirstLetter");
        model.addAttribute("pinpaiFirstLetter", pinpaiFirstLetter);
        //一级车系
        ProductCar productCar1 = new ProductCar();
        productCar1.setCarId(0L);
        ProductCar productCar2 = new ProductCar();
        productCar2.setParent(productCar1);
        List<ProductCar> productCarOneList = productCarService.selectByWhere(new Wrapper(productCar2));
        model.addAttribute("productCarOneList", productCarOneList);
        model.addAttribute("name", "carSearch");
        return "/carSearch";
    }

    /**
     * 进入front系统的全部分类页
     */
    @RequestMapping(value = "/categoryAll")
    public String categoryAll(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/categoryAll";
    }

//	/*  -------------------------fxx(测试demo)start-----------------------------  */
//	/**
//	 * 进入front系统的商品分页
//	 */
//	@RequestMapping(value = "/productPage")
//	public String productPage(HttpServletRequest request, HttpServletResponse response,Model model) {
//		//aa();
//		return "/demo/productPage";
//	}
//	
//	/**
//	 * 进入front系统的商品分类
//	 */
//	@RequestMapping(value = "/productCategory")
//	public String testProductCategory(HttpServletRequest request, HttpServletResponse response,Model model) {
//		return "/demo/productCategory";
//	}
//
//
//	/**
//	 * 进入front系统的商品品牌
//	 */
//	@RequestMapping(value = "/productBrand")
//	public String productBrand(HttpServletRequest request, HttpServletResponse response,Model model) {
//		return "/demo/productBrand";
//	}
//	
//	/**
//	 * 进入front系统的
//	 */
//	@RequestMapping(value = "/productCommentPage")
//	public String productCommentPage(HttpServletRequest request, HttpServletResponse response,Model model) {
//		return "/demo/productCommentPage";
//	}
//	
//	/**
//	 * 进入front系统的
//	 */
//	@RequestMapping(value = "/productConsultationPage")
//	public String productConsultationPage(HttpServletRequest request, HttpServletResponse response,Model model) {
//		return "/demo/productConsultationPage";
//	}
//	
//	/*  -------------------------fxx(测试demo)end-----------------------------  */
//	
//	/*  -------------------------cl(测试demo)start-----------------------------  */
//	
//	/**
//	 * 进入front系统的页面demo
//	 */
//	@RequestMapping(value = "/labelDemo")
//	public String labelDemo(HttpServletRequest request, HttpServletResponse response,Model model) {
//		return "/labelDemo";
//	}
//	
//	/**
//	 * 进入front系统的页面demo
//	 */
//	@RequestMapping(value = "/cmsDemo")
//	public String cmsDemo(HttpServletRequest request, HttpServletResponse response,Model model) {
//		return "/demo/cmsDemo";
//	}
//	
//	/*  -------------------------cl(测试demo)end-----------------------------  */

    /**
     * 进入demo页，测试单个标签
     * 先修改/shop-web-seller/src/main/resources/spring-mvc-front.xml文件
     * 在Beelt 父子资源加载器中，启用demo模板(value="/views/front/demo")，然后重启tomcat，方可进行测试。
     * 使用方法：http://www.one.com/demo/xxxx.htm，访问到的就是xxxx.html模板
     */
    @RequestMapping(value = "/demo/{name}")
    public String demo(@PathVariable String name, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("name", name);
        return "/" + name;
    }
}
