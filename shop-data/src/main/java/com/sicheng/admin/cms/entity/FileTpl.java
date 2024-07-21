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
package com.sicheng.admin.cms.entity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>标题: FileTpl</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年4月23日 下午9:25:34
 */
public class FileTpl {

    private File file;

    // 应用的根目录
    private String root;

    public FileTpl(File file, String root) {
        this.file = file;
        this.root = root;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getName() {
        String ap = file.getAbsolutePath().substring(root.length());
        ap = ap.replace(File.separatorChar, '/');
        // 在resin里root的结尾是带'/'的，这样会导致getName返回的名称不以'/'开头。
        if (!ap.startsWith("/")) {
            ap = "/" + ap;
        }
        return ap;
    }

    public String getParent() {
        String ap = file.getParent().substring(root.length());
        ap = ap.replace(File.separatorChar, '/');
        // 在resin里root的结尾是带'/'的，这样会导致getName返回的名称不以'/'开头。
        if (!ap.startsWith("/")) {
            ap = "/" + ap;
        }
        return ap;
    }

    public String getPath() {
        String name = getName();
        return name.substring(0, name.lastIndexOf('/'));
    }

    public String getFilename() {
        return file.getName();
    }

    public String getSource() {
        if (file.isDirectory()) {
            return null;
        }
        try {
            return FileUtils.readFileToString(this.file, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long getLastModified() {
        return file.lastModified();
    }

    public Date getLastModifiedDate() {
        return new Timestamp(getLastModified());
    }

    public long getLength() {
        return file.length();
    }

    public int getSize() {
        return (int) (getLength() / 1024) + 1;
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }
}
