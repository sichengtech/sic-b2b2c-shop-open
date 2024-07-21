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
package com.baidu.ueditor;

import com.baidu.ueditor.define.ActionMap;
import com.sicheng.common.config.Global;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置管理器
 *
 * @author zhaolei
 */
public final class ConfigManager {

    //配置文件名称
    private static final String CONFIG_FILE_NAME = "ueditor_upload_config.json";
    // 涂鸦上传filename定义
    private static final String SCRAWL_FILE_NAME = "scrawl";
    // 远程图片抓取filename定义
    private static final String REMOTE_FILE_NAME = "remote";

    private final String rootPath;
    //	private final String originalPath;
    private final String contextPath;
    //	private String parentPath = null;
    private JSONObject jsonConfig = null;

    /**
     * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
     */
    private ConfigManager(String rootPath, String contextPath, String uri) throws IOException {
        this.rootPath = rootPath.replace("\\", "/");
        this.contextPath = contextPath;
//		if (contextPath.length() > 0) {
//			this.originalPath = this.rootPath + uri.substring(contextPath.length());
//		} else {
//			this.originalPath = this.rootPath + uri;
//		}
        this.initEnv();
    }

    /**
     * 配置管理器构造工厂
     *
     * @param rootPath    服务器根路径
     * @param contextPath 服务器所在项目路径
     * @param uri         当前访问的uri
     * @return 配置管理器实例或者null
     */
    public static ConfigManager getInstance(String rootPath, String contextPath, String uri) {
        try {
            return new ConfigManager(rootPath, contextPath, uri);
        } catch (Exception e) {
            return null;
        }
    }

    // 验证配置文件加载是否正确
    public boolean valid() {
        return this.jsonConfig != null;
    }

    public JSONObject getAllConfig() {
        return this.jsonConfig;
    }

    public Map<String, Object> getConfig(int type) {
        Map<String, Object> conf = new HashMap<>();
        String savePath = null;
        switch (type) {

            case ActionMap.UPLOAD_FILE:
                conf.put("isBase64", "false");
                conf.put("maxSize", this.jsonConfig.getLong("fileMaxSize"));
                conf.put("allowFiles", this.getArray("fileAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("fileFieldName"));
                savePath = this.jsonConfig.getString("filePathFormat");
                break;

            case ActionMap.UPLOAD_IMAGE:
                conf.put("isBase64", "false");
                conf.put("maxSize", this.jsonConfig.getLong("imageMaxSize"));
                conf.put("allowFiles", this.getArray("imageAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("imageFieldName"));
                savePath = this.jsonConfig.getString("imagePathFormat");
                break;

            case ActionMap.UPLOAD_VIDEO:
                conf.put("maxSize", this.jsonConfig.getLong("videoMaxSize"));
                conf.put("allowFiles", this.getArray("videoAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("videoFieldName"));
                savePath = this.jsonConfig.getString("videoPathFormat");
                break;

            case ActionMap.UPLOAD_SCRAWL:
                conf.put("filename", ConfigManager.SCRAWL_FILE_NAME);
                conf.put("maxSize", this.jsonConfig.getLong("scrawlMaxSize"));
                conf.put("fieldName", this.jsonConfig.getString("scrawlFieldName"));
                conf.put("isBase64", "true");
                savePath = this.jsonConfig.getString("scrawlPathFormat");
                break;

            case ActionMap.CATCH_IMAGE:
                conf.put("filename", ConfigManager.REMOTE_FILE_NAME);
                conf.put("filter", this.getArray("catcherLocalDomain"));
                conf.put("maxSize", this.jsonConfig.getLong("catcherMaxSize"));
                conf.put("allowFiles", this.getArray("catcherAllowFiles"));
                conf.put("fieldName", this.jsonConfig.getString("catcherFieldName") + "[]");
                savePath = this.jsonConfig.getString("catcherPathFormat");
                break;

            case ActionMap.LIST_IMAGE:
                conf.put("allowFiles", this.getArray("imageManagerAllowFiles"));
                conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
                conf.put("count", this.jsonConfig.getInt("imageManagerListSize"));
                break;

            case ActionMap.LIST_FILE:
                conf.put("allowFiles", this.getArray("fileManagerAllowFiles"));
                conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
                conf.put("count", this.jsonConfig.getInt("fileManagerListSize"));
                break;
        }

        conf.put("savePath", savePath);
        conf.put("rootPath", this.rootPath);
        // conf.put("savePath", savePath);
        // conf.put("rootPath", this.jsonConfig.getString("uploadRoot"));
        return conf;
    }

    private void initEnv() throws IOException {
//		File file = new File(this.originalPath);
//		if (!file.isAbsolute()) {
//			file = new File(file.getAbsolutePath());
//		}
        //this.parentPath = file.getParent();
        String configContent = this.readFile(this.getConfigPath());

        // 替换变量
        configContent = insertPathVar(configContent, contextPath);
        try {
            JSONObject json = new JSONObject(configContent);
            this.jsonConfig = json;
        } catch (Exception e) {
            this.jsonConfig = null;
        }
    }

    /**
     * 替换变量 赵磊 使用Global.getConfig("filestorage.dir")替换####
     *
     * @param configContent json配置文件
     * @param contextPath2  应用的根路径 contextPath
     * @return
     */
    private String insertPathVar(String configContent, String contextPath2) {
        String path = "";
        if (contextPath != null && contextPath.length() > 0) {
            path = contextPath2 + Global.getConfig("filestorage.dir");
        } else {
            path = Global.getConfig("filestorage.dir");
        }
        return configContent.replaceAll("####", path);
    }

    /**
     * 这方法是拼全config.json的绝对路径。 赵磊 UEditor官方给的方案是：parentPath是controller.jsp
     * 的目录路径，所以限制config.json 必须和controller.jsp在同一个目录下。 赵磊的替代方案：用spring
     * controller代替controller.jsp，在类路径下查找config.json。
     */
    private String getConfigPath() {
        // 获取类的根路径classes/(重要)
        URL url = ConfigManager.class.getResource("/");
        String rootClassPath = url.getPath();// 如果路径有空格会使用 %20替代
        try {
            // %20 转换为 空格(方案二)
            rootClassPath = java.net.URLDecoder.decode(rootClassPath, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // 忽略
        }
        return rootClassPath + File.separator + ConfigManager.CONFIG_FILE_NAME;
    }

    private String[] getArray(String key) {
        JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
        String[] result = new String[jsonArray.length()];

        for (int i = 0, len = jsonArray.length(); i < len; i++) {
            result[i] = jsonArray.getString(i);
        }
        return result;
    }

    private String readFile(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = null;
        BufferedReader bfReader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
            bfReader = new BufferedReader(reader);
            String tmpContent = null;
            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }
        } catch (UnsupportedEncodingException e) {
            // 忽略
        } finally {
            if (bfReader != null) {
                bfReader.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return this.filter(builder.toString());
    }

    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    private String filter(String input) {
        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
    }
}