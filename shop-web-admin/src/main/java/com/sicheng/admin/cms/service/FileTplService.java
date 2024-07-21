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
package com.sicheng.admin.cms.service;

import com.sicheng.admin.cms.entity.FileTpl;
import com.sicheng.common.config.Global;
import com.sicheng.common.utils.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.*;

/**
 * <p>标题: FileTplService</p>
 * <p>描述: 用于操作磁盘上的模板文件</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年8月2日 上午11:54:51
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class FileTplService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ServletContext context;

    public List<String> getNameListByPrefix(String fileName) {
        List<FileTpl> list = getFiles(fileName, false);
        List<String> result = new ArrayList<String>(list.size());
        for (FileTpl tpl : list) {
            result.add(tpl.getName());
        }
        return result;
    }

    /**
     * 取指定目录下的 一层文件,只取一层
     *
     * @param dir       路径
     * @param directory 是否包含目录
     * @return
     */
    public List<FileTpl> getFiles(String dir, boolean directory) {
        if (StringUtils.isBlank(dir)) {
            return new ArrayList<FileTpl>(0);
        }
        File f = new File(dir);
        if (!f.exists()) {
            return new ArrayList<FileTpl>(0);
        }
        File[] files = f.listFiles();
        if (files == null) {
            return new ArrayList<FileTpl>(0);
        }

        //对获取的文件进行排序
        List<File> fileList = Arrays.asList(files);
        //Comparator(java 比较器)是定义在比较对象的外部的
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                // 返回值为int类型，大于0表示正序，小于0表示逆序
                if (o1.isDirectory() && o2.isFile()) {
                    return -1;
                }
                if (o1.isFile() && o2.isDirectory()) {
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });

        List<FileTpl> list = new ArrayList<FileTpl>();
        for (File file : fileList) {
            if (file.isFile() || directory) {
                list.add(new FileTpl(file, ""));
            }
        }
        return list;
    }

    /**
     * 获取目录树json数据
     *
     * @param dir
     * @return
     */
    public List<FileTpl> getFileTree() {
        String path = this.getFullPath("");
        List<FileTpl> list = getFiles(path, true);//取指定目录下的 一层文件,只取一层

        //根目录
        FileTpl root = new FileTpl(new File(path), "");

        List<FileTpl> result = new ArrayList<FileTpl>();
        result.add(root);
        recursion(result, list);
        return result;
    }

    /**
     * createFileTpl
     *
     * @param fileName
     * @return
     */
    public FileTpl createFileTpl(String fileName) {
        File f = new File(fileName);
        if (f.exists()) {
            return new FileTpl(f, "");
        } else {
            return null;
        }
    }

    /**
     * 删除一个模板文件
     *
     * @param fileName
     * @return
     */
    public boolean delTpl(String fileName) {
        if (fileName == null) {
            return false;
        }
        String allPath = fileName;
        boolean result = FileUtils.deleteFile(allPath);
        return result;
    }

    /**
     * 编辑或创建一个模板文件
     *
     * @param content  内容
     * @param fileName 文件名
     * @return
     */
    public boolean saveTpl(String content, String fileName) {
        String allPath = fileName;
        FileUtils.createFile(allPath);//文件存在就不会重复创建
        content = StringEscapeUtils.unescapeHtml4(content);//转回来（还原）
        boolean result = FileUtils.writeToFile(allPath, content, false);
        return result;
    }

    /**
     * 递归目录  recursion
     *
     * @param result
     * @param list
     */
    private void recursion(List<FileTpl> result, List<FileTpl> list) {
        for (FileTpl tpl : list) {
            result.add(tpl);
            if (tpl.isDirectory()) {
                recursion(result, getFiles(tpl.getName(), true));
            }
        }
    }

    /**
     * 格式化路径
     *
     * @return
     */
    private String formatPath(String path) {
        String allPath = path;
        allPath = allPath.replaceAll("\\\\", "/");
        if (allPath.endsWith("/")) {
            allPath = allPath.substring(0, allPath.length() - 1);
        }
        if (allPath.endsWith("/")) {
            allPath = allPath.substring(0, allPath.length() - 1);
        }
        return allPath;
    }

    /**
     * 计算出全路径(绝对路径)
     * @param fileName 目录
     * @return
     */
    private String getFullPath(String fileName) {
        if (!fileName.startsWith("/")) {
            fileName = "/" + fileName;
        }
        String allPath = formatPath(getTplRootPath()) + fileName;
        return allPath;
    }

    /**
     * 模板的根路径（绝对路径）
     *
     * @return
     */
    private String getTplRootPath() {
        String path = Global.getConfig("tplRootPath");
        File f = new File(path);
        if (!f.exists()) {
            //当未正确配置目录时，
            //取出 当前web工程的/views目录的 绝对路径,做为临时的补充
            path = context.getRealPath("/views");
            f = new File(path);
        }

        if (!f.canRead()) {
            logger.warn("模板目录不可读 " + f.getAbsolutePath());
        }
        if (!f.canWrite()) {
            logger.warn("模板目录不可写，你将无法修改模板 " + f.getAbsolutePath());
        }

        return path;
    }
}