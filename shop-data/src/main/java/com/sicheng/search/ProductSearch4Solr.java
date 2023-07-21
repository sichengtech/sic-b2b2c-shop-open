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

package com.sicheng.search;

import com.sicheng.admin.product.dao.SolrProductDao;
import com.sicheng.admin.product.entity.SolrProduct;
import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.SpringContextHolder;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.task.TaskExecutor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>标题:“商品”全文检索</p>
 * <p>描述:Solr的客户端，从solr查询商品信息</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2016年7月27日 下午2:38:55
 */
public class ProductSearch4Solr implements ProductSearchInterface<SolrProduct> {
    private static Logger log = LoggerFactory.getLogger(ProductSearch4Solr.class);
    private static volatile SolrClient client = null;
//    private static CloudSolrClient client = null;
//    private static HttpSolrClient client = null;

    private static String zk = Global.getConfig("solr.zk");// solr 集群模式（依赖zookeeper）,zookeeper的地址，向zk查询可用的solr集群服务器的节点列表
    private static String url = Global.getConfig("solr.url");// solr 单机模式（不依赖zookeeper）
    private static String collectionName = Global.getConfig("solr.collectionName.product");// 商品索引的名称

    /**
     * 私有构造方法
     */
    private ProductSearch4Solr() {
        // 建立连接，与solr服务器建立连接
        if (client == null) {
            synchronized (ProductSearch4Solr.class) {
                if (StringUtils.isNotBlank(url)) {
                    //当url不为空时，优先使用url来创建HttpSolrClient
                    //solr 单机模式（不依赖zookeeper）
                    if (client == null) {
                        client = new HttpSolrClient.Builder(url)  //+collectionName
                                .withSocketTimeout(30000)
                                .withConnectionTimeout(5000)
                                .build();
                        log.debug("创建HttpSolrClient成功，collectionName=" + collectionName + "，url=:" + url);
                    }
                } else if (StringUtils.isNotBlank(zk)) {
                    //当url为空时，才使用zk。
                    //solr 集群模式（依赖zookeeper）
                    if (client == null) {
                        List<String> zkServers = new ArrayList<String>();
                        zkServers.add(zk);
                        client = new CloudSolrClient.Builder(zkServers, Optional.empty())
                                .withSocketTimeout(30000)
                                .withConnectionTimeout(5000)
                                .build();
                        if (client instanceof CloudSolrClient) {
                            CloudSolrClient cloudSolrClient = (CloudSolrClient) client;
                            cloudSolrClient.setDefaultCollection(collectionName);//默认集合
                            cloudSolrClient.setZkClientTimeout(5000);//Client超时时间
                            cloudSolrClient.setZkConnectTimeout(45000);//Connect超时时间
                        }
                        log.debug("创建CloudSolrClient成功，collectionName=" + collectionName + "，zk服务器:" + StringUtils.join(zkServers, ","));
                    }
                } else {
                    throw new RuntimeException("创建SolrClient异常,配置solr.url和solr.zk都为空，二者需要至少一个有值。");
                }
            }
        }
    }

    /**
     * 搜索
     *
     * @param paramMap 查询条件
     * @param sortMap  排序
     * @param page     分页对象
     * @return 搜索结果
     */
    public Map<String, Object> search(Map<String, Object> paramMap, Map<String, String> sortMap, Page page) {
        long t1 = System.currentTimeMillis();
        // 查询条件
        SolrQuery query = new SolrQuery();
        StringBuilder sbl = new StringBuilder();
        Boolean isFacet = true;
        //检索词内容
        String suggestWord = "";
        if (paramMap != null && paramMap.size() > 0) {
            for (String key : paramMap.keySet()) {
                if ("p_id".equals(key)) {
                    isFacet = false;
                }
                String value = paramMap.get(key).toString();
                if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
                    continue;
                }
                String[] vs = value.split(",");
                if (vs.length == 0) {
                    continue;
                }
                for (int i = 0; i < vs.length; i++) {
                    if ("name".equals(key) || "text".equals(key)) {
                        suggestWord = this.parseKeywords(vs[i], false);
                    }
                    if ("category_id".equals(key)) {
                        key = "{!tag=cid}category_id";
                    }
                    if ("brand_id".equals(key)) {
                        key = "{!tag=bid}brand_id";
                    }
                    if ("param_value".equals(key)) {
                        key = "{!tag=pv}param_value";
                    }
                    String s = i == 0 ? " AND " : " OR ";
                    sbl.append(s);// AND
                    sbl.append(key);
                    sbl.append(":");
                    if ("price2".equals(key) || "update_date".equals(key)) {
                        sbl.append(this.parseKeywords(vs[i], true));
                    } else {
                        sbl.append(this.parseKeywords(vs[i], false));//加上双引号是精确搜索
                    }
                }
            }
        }

        //facet分组统计
        //Facet是solr的高级搜索功能之一，可以给用户提供更友好的搜索体验。在搜索关键字的同时，能够按照Facet的字段进行分组并统计，类似数据库的count(*) group by 功能。
        if (isFacet) {
            query.setFacet(true);//是否分组查询
            query.addFacetField("{!ex=cid}category_id", "{!ex=pv}param_value", "{!ex=bid}brand_id");//增加分组字段
            query.setIncludeScore(true);//是否按每组数量高低排序
            query.setFacetMinCount(1);//限制了Facet字段值的最小count
        }
        if (sbl.length() > 0) {
            String s = sbl.toString();
            s = s.substring(" AND ".length(), sbl.length());
            query.setQuery(s);// 按条件查询
        } else {
            query.setQuery("*:*");// 无参数，就查全部
        }
        // 排序
        if (sortMap != null) {
            for (String key : sortMap.keySet()) {
                String value = sortMap.get(key);
//                if ("update_date".equals(key) && StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
//                    query.addSort("update_date", SolrQuery.ORDER.desc);
//                } else
                if ("min_price".equals(key) && "asc".equalsIgnoreCase(value) && StringUtils.isNotBlank(key)
                        && StringUtils.isNotBlank(value)) {
                    query.addSort("price2", SolrQuery.ORDER.asc);
                } else if ("min_price".equals(key) && "desc".equalsIgnoreCase(value) && StringUtils.isNotBlank(key)
                        && StringUtils.isNotBlank(value)) {
                    query.addSort("price2", SolrQuery.ORDER.desc);
                } else {
                    query.setSort("score ", SolrQuery.ORDER.desc);//默认按分数排序
                    query.addSort(key, "desc".equalsIgnoreCase(value) ? SolrQuery.ORDER.desc : SolrQuery.ORDER.asc);
                }
            }
        }

        // 分页
        int start = (page.getPageNo() - 1) * page.getPageSize();
        int size = page.getPageSize();
        query.set("start", start);// 将初始偏移量指定到结果集中。可用于对结果进行分页。默认值为 0
        query.set("rows", size);// 返回文档的最大数目。默认值为 10。
        // 指定文档结果中应返回的 Field 集，作为逗号分隔。默认为 “*”，指所有的字段。“score” 指还应返回记分。
        query.setParam("fl", "score,*");

        //设置相似查询的信息
        query.setParam("mlt", "true");//mlt:打开相似查询
        query.setParam("mlt.fl", "name");//mlt.fl:设置相似查询的字段(商品名)
        query.setParam("mlt.fl", "category_name");//mlt.fl:设置相似查询的字段(分类名)
        query.setParam("mlt.fl", "brand_name");//mlt.fl:设置相似查询的字段(品牌名)
        query.setParam("mlt.mindf", "1");//mlt.mindf:最小文档频率，所在文档的个数小于这个值的词将不用于相似判断
        query.setParam("mlt.mintf", "1");//mlt.mintf:最小分词频率，在单个文档中出现频率小于这个值的词将不用于相似判断

        // 设置高亮信息
        query.setHighlight(true);
        query.addHighlightField("name");
        query.setHighlightSimplePre("<em class='highLight'>");
        query.setHighlightSimplePost("</em>");

        // 执行查询
        QueryResponse response = null;
        try {
            response = this.client.query(this.collectionName, query);//向collectionName集合发起查询
        } catch (SolrServerException e) {
            throw new RuntimeException("查询solr时异常", e);
        } catch (IOException e) {
            throw new RuntimeException("查询solr时异常", e);
        }
        SolrDocumentList docList = response.getResults();
        int QTime = response.getQTime();// 耗时
        int status = response.getStatus();// 状态
        ResultSet<SolrProduct> resultSet = new ResultSet();
        if (docList != null) {
            long rowCount = docList.getNumFound();// 搜索到的总条数
            page.setCount(rowCount);// 总条数
            resultSet.setStatus(String.valueOf(status));
            resultSet.setRequestId("request_id");//未传进来
            resultSet.setNum(0);// 页大小，未传进来，先设个0吧
            resultSet.setSearchtime(QTime);// 搜索耗时
            resultSet.setTotal((int) rowCount);// 搜索到的总条数
            resultSet.setViewtotal((int) (rowCount));
            for (int i = 0; i < docList.size(); i++) {
                SolrDocument resultDoc = docList.get(i);
                SolrProduct product = this.json2Object(resultDoc);
                String id = resultDoc.getFieldValue("id").toString();
                //给商品名称设置高亮
                if (response.getHighlighting() != null && response.getHighlighting().get(id) != null) {
                    List<String> name = response.getHighlighting().get(id).get("name");
                    if (name != null && name.size() > 0) {
                        product.setName(name.get(0));
                    }
                }
                resultSet.getItems().add(product);
            }
        }

        //获取所有被分组统计字段的值
        List<FacetField> facetFieldList = response.getFacetFields();
        List<Long> categoryIdList = new ArrayList<>();//分类id
        List<Long> brandIdList = new ArrayList<>();//品牌id
        List<String> paramValueList = new ArrayList<>();//参数值
        Map<String, Set<String>> paramValueMap = new LinkedHashMap<>();//参数(参数id,参数值)
        if (facetFieldList != null && facetFieldList.size() > 0) {
            for (int i = 0; i < facetFieldList.size(); i++) {
                List<FacetField.Count> countList = facetFieldList.get(i).getValues();
                for (int j = 0; j < countList.size(); j++) {
                    String fieldName = countList.get(j).getFacetField().getName();
                    if ("category_id".equals(fieldName)) {
                        //System.out.println("category_id:"+countList.get(j).getName()+":"+countList.get(j).getCount());
                        categoryIdList.add(Long.parseLong(countList.get(j).getName()));
                    }
                    if ("brand_id".equals(fieldName)) {
                        //System.out.println("brand_id:"+countList.get(j).getName()+":"+countList.get(j).getCount());
                        brandIdList.add(Long.parseLong(countList.get(j).getName()));
                    }
                    if ("param_value".equals(fieldName)) {
                        //System.out.println("param_value:"+countList.get(j).getName()+":"+countList.get(j).getCount());
                        paramValueList.add(countList.get(j).getName());
                    }
                }
            }
            //组装参数值字段
            for (String paramValue : paramValueList) {
                String[] params = paramValue.split("_");
                if (params.length > 1) {
                    Set<String> v = paramValueMap.get(params[0]);
                    if (v == null || v.size() == 0) {
                        Set<String> pv = new HashSet<>();
                        pv.add(params[1]);
                        //paramValueMap.put(Long.parseLong(params[0]),pv);
                        paramValueMap.put(params[0], pv);
                    } else {
                        v.add(params[1]);
                    }
                }
            }
        }

        //获取建议检索词
        List<String> suggestionWordList = new ArrayList<>();
        if (StringUtils.isNotBlank(suggestWord)) {
            suggestionWordList = this.getSuggestionList(suggestWord);
        }

        //从solr获取相似商品信息
        List<SolrProduct> productLikeList = this.getLikeProduct(response, resultSet);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("search", resultSet);
        resultMap.put("categoryIdList", categoryIdList);
        resultMap.put("brandIdList", brandIdList);
        resultMap.put("paramValueMap", paramValueMap);
        resultMap.put("productLikeList", productLikeList);//相似商品信息
        resultMap.put("suggestionWordList", suggestionWordList);

        //记录执行耗时日志
        if (log.isDebugEnabled()) {
            long t2 = System.currentTimeMillis();
            StringBuilder sbllog = new StringBuilder();
            sbllog.append("solr执行总耗时:").append((t2 - t1)).append("ms,");
            sbllog.append("参数:");
            if (paramMap != null) {
                for (String key : paramMap.keySet()) {
                    Object value = paramMap.get(key);
                    sbllog.append(key);
                    sbllog.append("=");
                    sbllog.append(value.toString());
                    sbllog.append(",");
                }
            }
            if (page != null) {
                int pn = page.getPageNo();
                sbllog.append("PageNo");
                sbllog.append("=");
                sbllog.append(pn);
                sbllog.append(",");
            }
            if (docList != null) {
                long rowCount = docList.getNumFound();// 搜索到的总条数
                sbllog.append("solr状态:");
                sbllog.append("搜索到总条数=").append(rowCount).append(",");
                sbllog.append("solr内耗时=").append(QTime).append(",");
                sbllog.append("solr状态=").append(status).append(",");
            }
            log.debug(sbllog.toString());
        }
        return resultMap;
    }

    /**
     * 批量添加文档
     *
     * @param solrProducts 商品List
     */
    public void addDocList(List<SolrProduct> solrProducts) {
        if (solrProducts == null || solrProducts.size() == 0) {
            return;
        }

        //对象转换
        List<ProductBo> productBoList = new ArrayList<>();
        for (SolrProduct solrProduct : solrProducts) {
            ProductBo buffer = new ProductBo();
            BeanUtils.copyProperties(solrProduct, buffer);
            if (solrProduct.getMinPrice() != null) {
                buffer.setMinPrice(solrProduct.getMinPrice().toString());
            }
            if (solrProduct.getMinPrice1() != null) {
                buffer.setMinPrice1(solrProduct.getMinPrice1().toString());
            }
            if (solrProduct.getMinPrice2() != null) {
                buffer.setMinPrice2(solrProduct.getMinPrice2().toString());
            }
            if (solrProduct.getMaxPrice() != null) {
                buffer.setMaxPrice(solrProduct.getMaxPrice().toString());
            }
            if (solrProduct.getMaxPrice1() != null) {
                buffer.setMaxPrice1(solrProduct.getMaxPrice1().toString());
            }
            if (solrProduct.getMaxPrice2() != null) {
                buffer.setMaxPrice2(solrProduct.getMaxPrice2().toString());
            }
            if (solrProduct.getPId() != null) {
                buffer.setpId(solrProduct.getPId());
            }
            if (solrProduct.getUId() != null) {
                buffer.setuId(solrProduct.getUId());
            }
            if (buffer.getStoreCateParentIds() == null) {
                buffer.setStoreCateParentIds("-1");
            }
            productBoList.add(buffer);
        }

        // 执行保存
        UpdateResponse response = null;
        try {
            this.client.addBeans(this.collectionName, productBoList);// 3.将文档集合写入索引库中
            response = this.client.commit(this.collectionName);// 提交
            int QTime = response.getQTime();
            int status = response.getStatus();
            this.log.debug("solr添加索引,耗时:{},返回状态值:{}", QTime, status);
        } catch (Exception e) {
            this.log.error("solr批量添加商品索引时异常", e);
        }
    }

    /**
     * 按ID添加文档
     *
     * @param id 商品ID
     */
    public void addDoc(Long id) {
        if (id == null) {
            return;
        }
        // 按ID查询SolrProduct视图，这里有一个商品及关联信息
        SolrProductDao dao = SpringContextHolder.getBean(SolrProductDao.class);
        SolrProduct p = dao.selectById(id);
        if (p == null) {
            return;
        }
        List<SolrProduct> solrProducts = new ArrayList<>(1);
        solrProducts.add(p);
        addDocList(solrProducts);
    }

    /**
     * 按ID添加文档（异步线程池）
     *
     * @param id 商品ID
     */
    public void addDocAsyn(Long id) {
        if (id == null) {
            return;
        }
        try {
            TaskExecutor taskExecutor = SpringContextHolder.getBean("taskExecutor");
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    addDoc(id);
                }
            });
        } catch (Exception e) {
            log.error("按ID添加索引（异步线程池）", e);
        }
    }

    /**
     * 按ID删除文档
     *
     * @param id 商品的ID
     */
    public void deleteDoc(Long id) {
        if (id == null) {
            return;
        }
        UpdateResponse response = null;
        try {
            this.client.deleteById(this.collectionName, id.toString());// 按ID删除索引
            response = this.client.commit(this.collectionName);
            int QTime = response.getQTime();
            int status = response.getStatus();
            this.log.debug("solr按ID删除索引,耗时:{},返回状态值:{}", QTime, status);
        } catch (SolrServerException e) {
            this.log.error("solr按ID删除索引异常", e);
        } catch (IOException e) {
            this.log.error("solr按ID删除索引异常", e);
        }
    }

    /**
     * 按ID删除文档（异步线程池）
     *
     * @param id 商品的ID
     */
    public void deleteDocAsyn(Long id) {
        if (id == null) {
            return;
        }
        try {
            TaskExecutor taskExecutor = SpringContextHolder.getBean("taskExecutor");
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    deleteDoc(id);
                }
            });
        } catch (Exception e) {
            log.error("按ID删除索引（异步线程池）", e);
        }
    }

    /**
     * 删除全部商品文档
     */
    public void deleteDocAll() {
        UpdateResponse response = null;
        try {
            this.client.deleteByQuery(this.collectionName, "*:*");//*表示全部
            response = this.client.commit(this.collectionName);// 提交
            int QTime = response.getQTime();
            int status = response.getStatus();
            this.log.debug("solr删除全部商品索引,耗时:{},返回状态值:{}", QTime, status);
        } catch (Exception e) {
            this.log.error("solr删除全部商品索引异常", e);
        }
    }

    /**
     * 根据店铺id删除商品文档
     *
     * @param storeId
     */
    public void deleteDocByStoreId(Long storeId) {
        UpdateResponse response = null;
        try {
            this.client.deleteByQuery(this.collectionName, "store_id:" + storeId);//*表示全部
            response = this.client.commit(this.collectionName);// 提交
            int QTime = response.getQTime();
            int status = response.getStatus();
            this.log.debug("solr删除全部商品索引,耗时:{},返回状态值:{}", QTime, status);
        } catch (Exception e) {
            this.log.error("solr删除全部商品索引异常", e);
        }
    }

    /**
     * 销毁Client
     */
    public void destroy() {
        if (client != null) {
            try {
                client.close();
                // 解决zookeeper导致tomcat停止时报异常的问题
                // 运行工程时，发现停止 tomcat 时，发现控制台会报一些错误:Could not load [org.apache.zookeeper.server.ZooTrace]
                // 分析原因：
                // 本工程的 solr 要连接 zookeeper，在 spring bean 销毁时也正确的关闭了 zookeeper 连接。
                // 但仅仅只是修改了 SendThread 线程内部的变量，并没有等 SendThread 完全退出。
                // 这样就存在 spring bean 销毁了，但 SendThread 线程还活着的场景。
                // 解决方案：
                // 等待zookeeper的相关线程完全退出
                synchronized (ProductSearch4Solr.class) {
                    ProductSearch4Solr.class.wait(1000L);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //从solr获取相似商品信息
    private List<SolrProduct> getLikeProduct(QueryResponse response, ResultSet<SolrProduct> search) {
        List<SolrProduct> productList = new ArrayList<>();
        try {
            /*mltResults:相似商品的结果，类型是SimpleOrderedMap<SolrDocumentList>:
             * key是当前查询结果中商品的id,有几个商品，mltResults中就有几条记录，
             * mltResults的value是更当前商品相似的商品
             */

            Object obj = response.getResponse().get("moreLikeThis");
            NamedList list = (NamedList) obj;
//            SimpleOrderedMap<SolrDocumentList> mltResults = (SimpleOrderedMap<SolrDocumentList>) obj;
            if (list == null || list.size() == 0) {
                return productList;
            }
            for (int i = 0; i < list.size(); i++) {
                SolrDocumentList items = (SolrDocumentList) list.getVal(i);
                for (SolrDocument doc : items) {
                    //循环排除当前查出来的商品
                    Boolean falg = true;
                    String pid = doc.get("id").toString();
                    for (SolrProduct p : search.getItems()) {
                        if (pid.equals(p.getPId().toString())) {
                            falg = false;
                            break;
                        }
                    }
                    if (falg) {
                        SolrProduct product = new SolrProduct();
                        product = json2Object(doc);
                        productList.add(product);
                        //System.out.println("相似商品:"+doc.getFieldValue("p_id").toString()+"--"+doc.getFieldValue("name").toString()+"--"+doc.getFieldValue("brand_name").toString()+"--"+doc.getFieldValue("category_name").toString());
                    }
                }
            }
        } catch (Exception e) {
            log.error("从solr获取相似商品时遇到错误", e);
        }
        return productList;
    }

    //获取建议检索词
    private List<String> getSuggestionList(String name) {
        List<String> wordList = new ArrayList<String>();
        SolrQuery query = new SolrQuery();
        query.set("q", "text:" + name);//查询的词
        query.set("qt", "/suggest");//请求到suggest中
        query.set("spellcheck.count", "10");//返回数量
        QueryResponse response = null;

        try {
            response = client.query(this.collectionName, query);
        } catch (SolrServerException e) {
            log.error("从solr获取建议检索词时遇到错误", e);
        } catch (IOException e) {
            log.error("从solr获取建议检索词时遇到错误", e);
        } catch (Exception e) {
            log.error("从solr获取建议检索词时遇到错误", e);
        }
        if (response != null) {
            SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();//获取拼写检查的结果集
            if (spellCheckResponse != null) {
                List<SpellCheckResponse.Suggestion> suggestionList = spellCheckResponse.getSuggestions();
                for (SpellCheckResponse.Suggestion suggestion : suggestionList) {
                    List<String> suggestedWordList = suggestion.getAlternatives();
                    for (int i = 0; i < suggestedWordList.size(); i++) {
                        String word = suggestedWordList.get(i);
                        wordList.add(word);
                    }
                }
            }
        }
        return wordList;
    }

    //从result中获取商品信息
    private SolrProduct json2Object(SolrDocument resultDoc) {
        SolrProduct product = new SolrProduct();
        if (resultDoc.get("id") != null) {
            product.setPId(Long.parseLong(resultDoc.get("id").toString())); //商品id
        }
        product.setName((String) resultDoc.get("name"));//商品名称
        product.setNameSub((String) resultDoc.get("name_sub"));//商品副标题
        product.setCategoryName((String) resultDoc.get("category_name"));//分类名
        if (resultDoc.get("category_id") != null) {
            product.setCategoryId(Long.parseLong(resultDoc.get("category_id").toString()));//分类id
        }
        product.setCategoryLevel(resultDoc.get("category_level") != null ? Long.parseLong(resultDoc.get("category_level").toString()) : 0);//分类等级
        product.setCateFirstLetter((String) resultDoc.get("cate_first_letter"));//分类首字母
        product.setCateParentIds((String) resultDoc.get("cate_parent_ids"));//分类父ids
        product.setStoreCateName((String) resultDoc.get("store_cate_name"));//店铺分类名
        product.setStoreCateParentIds((String) resultDoc.get("store_cate_parent_ids"));//店铺分类ids
        if (resultDoc.get("store_id") != null) {
            product.setStoreId(Long.parseLong(resultDoc.get("store_id").toString()));//店铺id
        }
        product.setStoreName((String) resultDoc.get("store_name"));//店铺名
        product.setStoreType((String) resultDoc.get("store_type"));//店铺类型
        product.setUserName((String) resultDoc.get("user_name"));//用户名
        if (resultDoc.get("u_id") != null) {
            product.setUId(Long.parseLong(resultDoc.get("u_id").toString())); //用户id
        }
        if (resultDoc.get("brand_id") != null) {
            product.setBrandId(Long.parseLong(resultDoc.get("brand_id").toString()));//品牌id
        }
        product.setBrandName((String) resultDoc.get("brand_name"));//品牌名
        product.setBrandFirstLeftter((String) resultDoc.get("brand_first_leftter"));//品牌首字母
        product.setBrandEnglishName((String) resultDoc.get("brand_english_name"));//品牌英文名
        String minPrice = (String) resultDoc.get("min_price");//最小价格
        if (StringUtils.isNotBlank(minPrice)) {
            product.setMinPrice(new BigDecimal(minPrice));
        } else {
            product.setMinPrice(new BigDecimal("0"));
        }
        String maxPrice = (String) resultDoc.get("max_price");//最大价格
        if (StringUtils.isNotBlank(maxPrice)) {
            product.setMaxPrice(new BigDecimal(maxPrice));
        } else {
            product.setMaxPrice(new BigDecimal("0"));
        }
        String minPrice1 = (String) resultDoc.get("min_price1");//最小零售价
        if (StringUtils.isNotBlank(minPrice1)) {
            product.setMinPrice1(new BigDecimal(minPrice1));
        } else {
            product.setMinPrice1(new BigDecimal("0"));
        }
        String maxPrice1 = (String) resultDoc.get("max_price1");//最大零售价
        if (StringUtils.isNotBlank(maxPrice1)) {
            product.setMaxPrice1(new BigDecimal(maxPrice1));
        } else {
            product.setMaxPrice1(new BigDecimal("0"));
        }
        String minPrice2 = (String) resultDoc.get("min_price2");//最小批发价
        if (StringUtils.isNotBlank(minPrice2)) {
            product.setMinPrice2(new BigDecimal(minPrice2));
        } else {
            product.setMinPrice2(new BigDecimal("0"));
        }
        String maxPrice2 = (String) resultDoc.get("max_price2");//最大批发价
        if (StringUtils.isNotBlank(maxPrice2)) {
            product.setMaxPrice2(new BigDecimal(maxPrice2));
        } else {
            product.setMaxPrice2(new BigDecimal("0"));
        }
        product.setUnit((String) resultDoc.get("unit"));//商品单位
        product.setCarIds((String) resultDoc.get("car_ids"));//适用车型
        product.setStatus((String) resultDoc.get("status"));//商品状态
        product.setType((String) resultDoc.get("type"));//商品类型
        product.setIsRecommend((String) resultDoc.get("is_recommend"));//是否推荐
        product.setParamValue((String) resultDoc.get("param_value"));//参数值
        if (resultDoc.get("province_id") != null) {
            product.setProvinceId(Long.parseLong(resultDoc.get("province_id").toString()));//省id
        }
        if (resultDoc.get("city_id") != null) {
            product.setCityId(Long.parseLong(resultDoc.get("city_id").toString()));//市id
        }
        product.setProvinceName((String) resultDoc.get("province_name"));//省名称
        product.setCityName((String) resultDoc.get("city_name"));//市名称
        product.setAllSales(resultDoc.get("all_sales") != null ? Long.parseLong(resultDoc.get("all_sales").toString()) : 0);//总销量
        product.setWeekSales(resultDoc.get("week_sales") != null ? Long.parseLong(resultDoc.get("week_sales").toString()) : 0);//周销量
        product.setMonthSales(resultDoc.get("month_sales") != null ? Long.parseLong(resultDoc.get("month_sales").toString()) : 0);//月销量
        product.setMonth3Sales(resultDoc.get("month3_sales") != null ? Long.parseLong(resultDoc.get("month3_sales").toString()) : 0);//3个月销量
        product.setCollectionCount(resultDoc.get("collection_count") != null ? Long.parseLong(resultDoc.get("collection_count").toString()) : 0);//收藏量
        product.setCommentCount(resultDoc.get("comment_count") != null ? Long.parseLong(resultDoc.get("comment_count").toString()) : 0);//评论量
        product.setImage((String) resultDoc.get("image"));//商品封面图
        product.setCreateDate((Date) resultDoc.get("update_date"));// 创建日期
        product.setUpdateDate((Date) resultDoc.get("update_date"));// 更新日期
        return product;
    }

    /**
     * 转义非法字符（solr官方处理方法）
     * 如果query中带有非法字符串，导致搜索时会报错，所以对用户的输入必须要转义
     *
     * @param s       搜索的关键词
     * @param isPrice 是否是价格（价格的的符号过滤略有不同）
     * @return
     */
    @SuppressWarnings("Duplicates")
    private String parseKeywords(String s, Boolean isPrice) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isPrice) {
                if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' || c == '^' || c == '\"'
                        || c == '{' || c == '}' || c == '~' || c == '?' || c == '|' || c == '&' || c == ';' || c == '/') {
                    sb.append('\\');
                }
            } else {
                // These characters are part of the query syntax and must be escaped
                if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' || c == '^' || c == '[' || c == ']' || c == '\"'
                        || c == '{' || c == '}' || c == '~' || c == '*' || c == '?' || c == '|' || c == '&' || c == ';' || c == '/'
                        || Character.isWhitespace(c)) {
                    sb.append('\\');
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}