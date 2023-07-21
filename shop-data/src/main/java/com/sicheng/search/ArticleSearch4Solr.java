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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sicheng.common.utils.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sicheng.admin.cms.dao.ArticleDao;
import com.sicheng.admin.cms.entity.Article;
import com.sicheng.common.config.Global;
import com.sicheng.common.web.SpringContextHolder;

/**
 * “文章”全文检索接口，用于通过关键词来搜索出相关的文章
 * solr搜索引擎实现
 *
 * @author zhaolei
 */
public class ArticleSearch4Solr implements ArticleSearchInterface<ArticleBo> {
    private static final Logger log = LoggerFactory.getLogger(ArticleSearch4Solr.class);
    private static volatile CloudSolrClient client = null;
    private static String zk = Global.getConfig("solr.zk");// zookeeper的地址，向zk查询可用的solr集群服务器的节点列表
    private static String collectionName = Global.getConfig("solr.collectionName.article");// 文章索引的名称

    /**
     * 构造方法
     */
    public ArticleSearch4Solr() {
        // 建立连接，与solr服务器建立连接
        if (client == null) {
            synchronized (ArticleSearch4Solr.class) {
                if (client == null) {
                    List<String> zkServers = new ArrayList<String>();
                    zkServers.add(zk);
                    client = new CloudSolrClient.Builder(zkServers, Optional.empty()).build();
                    client.setDefaultCollection(collectionName);
                    client.setZkClientTimeout(45000);//Client超时时间
                    client.setZkConnectTimeout(45000);//Connect超时时间
                    log.debug("创建CloudSolrClient成功，collectionName=" + collectionName + "，zk服务器:" + StringUtils.join(zkServers, ","));
                }
            }
        }
    }

    /**
     * 搜索文章
     * 按一个关键词搜索文章列表
     *
     * @param queryStr 搜索的关键词
     * @param start    指定搜索结果集的偏移量。取值范围：[0,5000], 默认值：0
     * @param count    指定返回结果集的数量。 取值范围：[0,500],默认值：20
     * @param type     0:文章的摘要中的关键字要高亮，1：关键字不高亮
     *                 0为文章服务搜索文章，1为sitemap服务只搜索id
     * @return
     */
    public ResultSet<ArticleBo> search(String queryStr, int start, int count, int type) {
        // 处理solr专用字符，一些字符对于solr来说是命令有意义，要处理转义。
        if (StringUtils.isNotBlank(queryStr)) {
            queryStr = parseKeywords(queryStr);
        }

        /////////////
        // 准备查询条件
        /////////////
        SolrQuery query = new SolrQuery();
        if (StringUtils.isBlank(queryStr)) {
            //一般：首页、列表页，会走这里，无查询关键词，只能按日期排序
            query.setQuery("*:*");// 默认查*
            query.addSort("release_date", SolrQuery.ORDER.desc);// 排序，sort=date asc,price desc
        } else {
            //一般：按词搜索、按tag搜索会走这里，有查询关键词，按相关度打分排序
            // 条件“java OR (empId:1000 AND empId:1001)”
            // query.setQuery("title:'"+queryStr+"' AND body:'"+queryStr+"'");
            // query.setQuery("title:'" + queryStr + "'");
            query.setQuery("text:'" + queryStr + "'");// text字段，是title与body字段的合并，使用copyfield实现
        }

        //分页
        query.set("start", start);// 将初始偏移量指定到结果集中。可用于对结果进行分页。默认值为 0
        query.set("rows", count);// 返回文档的最大数目。默认值为 10。

        if (type == 0) {
            // 高亮
            query.setHighlight(true);
            query.setHighlightSnippets(2);// 结果分片数，默认为1,若为0就不会高亮显示
            query.setParam("hl.fl", "title,description");// 那些字段高亮显示，可以用空格或者逗号分隔
            // 高亮显示字段前后添加html代码
            query.setHighlightSimplePre("<em>");
            query.setHighlightSimplePost("</em>");
            query.setHighlightFragsize(40); // 每个分片的最大长度，默认为100
            // 取文章的摘要，包含高亮部分.显示第一个匹配关键字附近的部分内容

            // 指定文档结果中应返回的 Field 集，作为逗号分隔。默认为 “*”，指所有的字段。“score” 指还应返回记分。
            //为文章服务搜索文章
            query.setParam("fl", "score,*");
        } else {
            //为sitemap服务只搜索id
            query.setParam("id");//过滤，只返回指定的字段
        }

        ///////////
        // 执行查询
        ///////////
        QueryResponse response = null;
        try {
            response = client.query(query);
        } catch (SolrException e) {
            throw new RuntimeException("查询solr时异常", e);
        } catch (SolrServerException e) {
            throw new RuntimeException("查询solr时异常", e);
        } catch (IOException e) {
            throw new RuntimeException("查询solr时异常", e);
        }

        // 转换结果
        return json2Object(response);
    }

    /**
     * 向索引库中添加文档
     *
     * @param id 商品ID，用于从库中查出商品
     */
    public void addDoc(Long id) {
        if (id == null) {
            return;
        }

        // 执行查询
        ArticleDao dao = SpringContextHolder.getBean(ArticleDao.class);
        Article a = dao.selectById(id);
        if (a == null) {
            return;
        }

        // 执行保存
        UpdateResponse response = null;
        try {

            // 2.创建一个文档对象
            SolrInputDocument inputDocument = new SolrInputDocument();
            // 向文档中添加域以及对应的值,注意：所有的域必须在schema.xml中定义过,前面已经给出过我定义的域。
            inputDocument.addField("id", a.getId());
            inputDocument.addField("title", a.getTitle());
//            inputDocument.addField("author", a.getAuthor());
//            inputDocument.addField("release_date", a.getReleaseDate());
//            inputDocument.addField("origin", a.getOrigin());
            inputDocument.addField("image", a.getImage());
            inputDocument.addField("keywords", a.getKeywords());
            inputDocument.addField("description", a.getDescription());//摘要200汉字
            inputDocument.addField("create_date", a.getCreateDate());
            // 3.将文档写入索引库中
            response = client.add(inputDocument);
            // 提交
            response = client.commit();
            int QTime = response.getQTime();
            int status = response.getStatus();
            log.debug("solr添加索引,id:{},耗时:{},返回状态值:{}", id, QTime, status);
        } catch (Exception e) {
            log.error("添加时异常", e);
        }
    }

    /**
     * 批量添加文章到索引中
     *
     * @param cmsArticles
     */
    public void addDocList(List<Article> cmsArticles) {
        if (cmsArticles == null || cmsArticles.size() == 0) {
            return;
        }

        // 执行保存
        UpdateResponse response = null;
        try {
            // 3.将文档集合写入索引库中
            client.addBeans(cmsArticles);
            // 提交
            response = client.commit();
            int QTime = response.getQTime();
            int status = response.getStatus();
            log.debug("solr添加索引,耗时:{},返回状态值:{}", QTime, status);
        } catch (Exception e) {
            log.error("添加时异常", e);
        }
    }

    /**
     * 按ID删除索引
     *
     * @param id
     */
    public void deleteDoc(Long id) {
        if (id == null) {
            return;
        }

        // 执行删除
        UpdateResponse response = null;
        try {
            response = client.deleteById(id.toString());
            // 提交
            response = client.commit();
            int QTime = response.getQTime();
            int status = response.getStatus();
            log.debug("solr删除索引,id:{},耗时:{},返回状态值:{}", id, QTime, status);
        } catch (Exception e) {
            log.error("删除时异常", e);
        }
    }

    /**
     * 删除全部索引
     */
    @Override
    public void deleteDocAll() {
        throw new RuntimeException("未实现");
    }

    /**
     * 销毁
     */
    @Override
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
                synchronized (ArticleSearch4Solr.class) {
                    ArticleSearch4Solr.class.wait(1000L);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 转换结果
     *
     * @param response
     * @return
     */
    private ResultSet<ArticleBo> json2Object(QueryResponse response) {
        SolrDocumentList results = response.getResults();

        // 处理高亮
        // solr把高亮后的字段，单独放在一个map中
        for (int i = 0; i < results.size(); ++i) {
            SolrDocument resultDoc = results.get(i);
            String id = (String) resultDoc.getFieldValue("id"); // id is the uniqueKey field
            if (response.getHighlighting() != null && response.getHighlighting().get(id.toString()) != null) {
                // 第一个Map的键是文档的ID，第二个Map的键是高亮显示的字段名
                Map<String, Map<String, List<String>>> map = response.getHighlighting();
                List<String> highlightSnippets = map.get(id.toString()).get("description");
                List<String> highlightSnippets2 = map.get(id.toString()).get("title");

                // 高亮后的摘要在这里输出
                if (highlightSnippets != null && highlightSnippets.size() > 0) {
                    StringBuilder sbl = new StringBuilder();
                    for (String s : highlightSnippets) {
                        sbl.append(s);
                        sbl.append("...");
                    }
                    resultDoc.setField("description2", sbl.toString());// 复盖
                } else {
                    resultDoc.setField("description2", (String) resultDoc.get("description"));// 复盖
                }
                if (highlightSnippets2 != null && highlightSnippets2.size() > 0) {
                    StringBuilder sbl = new StringBuilder();
                    for (int j = 0; j < highlightSnippets2.size(); j++) {
                        String s = highlightSnippets2.get(j);
                        sbl.append(s);
                        if (j != (highlightSnippets2.size() - 1)) {
                            sbl.append("...");
                        }
                    }
                    resultDoc.setField("title2", sbl.toString());// 复盖
                } else {
                    resultDoc.setField("title2", (String) resultDoc.get("title"));// 复盖
                }
            }
        }

        long rowCount = results.getNumFound();// 搜索到的总条数
        int QTime = response.getQTime();//
        int status = response.getStatus();//
        ResultSet resultSet = new ResultSet();
        resultSet.setStatus(String.valueOf(status));
        resultSet.setRequestId("request_id");
        resultSet.setNum(0);// 页大小，未传进来，先设个0吧
        resultSet.setSearchtime(QTime);// 搜索耗时
        resultSet.setTotal((int) rowCount);// 搜索到的总条数
        resultSet.setViewtotal((int) (rowCount > 5000 ? 5000 : rowCount));// 可显示的总条数，5000封顶

        for (int i = 0; i < results.size(); ++i) {
            SolrDocument resultDoc = results.get(i);
            String id_str = (String) resultDoc.getFieldValue("id"); // id is the uniqueKey field
            Long id = null;
            try {
                id = Long.valueOf(id_str);
            } catch (NumberFormatException e) {
                continue;
            }
            ArticleBo articleBo = new ArticleBo();
            articleBo.setId(id);
            articleBo.setTitle((String) resultDoc.get("title"));
            articleBo.setAuthor((String) resultDoc.get("author"));
            articleBo.setReleaseDate((Date) resultDoc.get("release_date"));
            articleBo.setOrigin((String) resultDoc.get("origin"));
            articleBo.setImage((String) resultDoc.get("image"));
            articleBo.setKeywords((String) resultDoc.get("keywords"));
            articleBo.setDescription((String) resultDoc.get("description"));
            articleBo.setScore((Float) resultDoc.get("score"));
            articleBo.setTitle2((String) resultDoc.get("title2"));// solr给加了高亮后的title
            articleBo.setDescription2((String) resultDoc.get("description2"));// solr给加了高亮后的body
            resultSet.getItems().add(articleBo);
        }
        return resultSet;
    }

    /**
     * solr 官方的处理方法 如果query中带有非法字符串，结果直接报错，所以你对用户的输入必须要先做处理
     *
     * @param s
     * @return
     */
    private String parseKeywords(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' || c == '^'
                    || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~' || c == '*' || c == '?'
                    || c == '|' || c == '&' || c == ';' || c == '/' || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

//    /**
//     * 截断字符串,并删除html标签
//     * @param source
//     * @return
//     */
//    public String cutString(String source,int length){
//        String s=deleteAllHTMLTag(source);
//        if(s.length()>length){
//            return s.substring(0,length);
//        }
//        return s;
//    }
//
//    /**
//     * 删除所有的HTML标签
//     *
//     * @param source 需要进行除HTML的文本
//     * @return
//     */
//    public String deleteAllHTMLTag(String source) {
//        if(source == null) {
//            return "";
//        }
//
//        String s = source;
//        /** 删除普通标签  */
//        s = s.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
//        /** 删除转义字符 */
//        s = s.replaceAll("&.{2,6}?;", "");
//        return s;
//    }

}
