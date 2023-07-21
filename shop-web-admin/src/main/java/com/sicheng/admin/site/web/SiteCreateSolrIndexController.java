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
package com.sicheng.admin.site.web;

import com.sicheng.admin.site.service.SiteCreateSolrIndexService;
import com.sicheng.admin.store.service.StoreAnalyzeService;

import com.sicheng.common.utils.FYUtils;
import com.sicheng.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 手动创建Solr索引
 */
@Controller
@RequestMapping(value = "${adminPath}/site/SiteCreateSolrIndexController")
public class SiteCreateSolrIndexController extends BaseController {

    @Autowired
    private SiteCreateSolrIndexService siteCreateSolrIndexService;

    @Autowired
    private StoreAnalyzeService storeAnalyzeService;

    /**
     * 菜单高亮
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void get(Long id, Model model) {
        String menu3 = "070113";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 进入创建文章索引页面
     *
     * @return
     */
    @RequiresPermissions("site:SiteCreateSolrIndexController:index")
    @RequestMapping(value = "staticForm")
    public String staticForm() {
        return "admin/site/siteCreateSolrIndex";
    }

    /**
     * 开始创建Solr索引
     *
     * @param threadNumber 线程数
     * @param subType      为谁生成Solr索引，可选值：store、product
     * @return
     */
    @RequiresPermissions("site:SiteCreateSolrIndexController:index")
    @ResponseBody
    @RequestMapping(value = "start")
    public Object start(Integer threadNumber, String subType) {
        Map<String, Object> data = new HashMap<>();
        try {
            if (threadNumber == null || threadNumber <= 0 || threadNumber > 10) {
                threadNumber = 5;
            }
            //设置参数
            siteCreateSolrIndexService.setThreadName("创建" + subType + "索引");
            siteCreateSolrIndexService.setMaximumPoolSize(threadNumber);
            siteCreateSolrIndexService.setSubType(subType);
            //开启线程
            if (siteCreateSolrIndexService.isIsrun()) {
                data.put("code", 202);
                data.put("msg", FYUtils.fyParams("正在生成") + siteCreateSolrIndexService.getSubType() + FYUtils.fyParams("索引，请勿反复点击"));
            } else {
                siteCreateSolrIndexService.start();
                data.put("code", 200);
                data.put("msg", "开始生成");
            }
        } catch (Exception e) {
            logger.info("开始创建" + subType + "索引异常：", e);
            data.put("code", 500);
            data.put("msg", FYUtils.fyParams("开始创建") + subType + FYUtils.fyParams("索引异常"));
        } finally {
            return data;
        }
    }

    /**
     * 停止
     *
     * @return
     */
    @RequiresPermissions("site:SiteCreateSolrIndexController:index")
    @ResponseBody
    @RequestMapping(value = "stop")
    public Object stop() {
        Map<String, Object> data = new HashMap<>();
        try {
            //关闭线程
            siteCreateSolrIndexService.stop();
            data.put("code", 200);
            data.put("msg", FYUtils.fyParams("已停止"));
        } catch (Exception e) {
            logger.info("停止创建索引异常：", e);
            data.put("code", 500);
            data.put("msg", FYUtils.fyParams("停止创建索引异常"));
        } finally {
            return data;
        }
    }

    /**
     * 查看生成索引日志
     *
     * @return
     */
    @RequiresPermissions("site:SiteCreateSolrIndexController:index")
    @ResponseBody
    @RequestMapping(value = "logs")
    public Object logs() {
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("code", 200);
            data.put("data", siteCreateSolrIndexService.getLog());
        } catch (Exception e) {
            logger.info("查看创建索引日志异常：", e);
            data.put("code", 500);
            data.put("msg", FYUtils.fyParams("查看创建索引日志异常"));
        } finally {
            return data;
        }
    }
}
