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
package com.sicheng.tools;

import com.sicheng.common.utils.FYUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码中文替换工具
 *
 * @author zhaolei
 */
public class Codei18n {

    /**
     * 代码行数统计
     */
    public static void main(String[] args) {
        System.out.println("开始");
        String file = Codei18n.class.getResource("/").getFile();
        String path = file.replace("target/test-classes", "src");

        //只匹配控制器
        String regular1 = ".*Controller\\.java$";
        //匹配全部
        String regular2 = ".*\\.java$";
        //只匹配Service
        String regular3 = ".*Service\\.java$";

        ArrayList<File> al = getFile(new File(path));
        for (File f : al) {
            if (f.getName().matches(regular3)) { // 匹配java格式的文件
                count(f);
                //System.out.println(f);
            }
        }
        System.out.println("=============");
        System.out.println("统计文件：" + files);
        System.out.println("代码行数：" + codeLines);
        System.out.println("注释行数：" + commentLines);
        System.out.println("空白行数：" + blankLines);
    }

    static long files = 0;
    static long codeLines = 0;
    static long commentLines = 0;
    static long blankLines = 0;
    static ArrayList<File> fileArray = new ArrayList<File>();

    /**
     * 获得目录下的文件和子目录下的文件
     *
     * @param f
     * @return
     */
    public static ArrayList<File> getFile(File f) {
        File[] ff = f.listFiles();
        for (File child : ff) {
            if (child.isDirectory()) {
                getFile(child);
            } else
                fileArray.add(child);
        }
        return fileArray;

    }

    /**
     * 统计方法
     *
     * @param f
     */
    private static void count(File f) {
        BufferedReader br = null;
        boolean flag = false;
        int lineNum = 0;

        try {
            br = new BufferedReader(new FileReader(f));
            String line = "";
            while ((line = br.readLine()) != null) {
                lineNum++;
                line = line.trim(); // 除去注释前的空格
                if (line.matches("^[ ]*$")) { // 匹配空行
                    blankLines++;
                } else if (line.startsWith("//")) {
                    commentLines++;
                } else if (line.startsWith("/*") && !line.endsWith("*/")) {
                    commentLines++;
                    flag = true;
                } else if (line.startsWith("/*") && line.endsWith("*/")) {
                    commentLines++;
                } else if (flag == true) {
                    commentLines++;
                    if (line.endsWith("*/")) {
                        flag = false;
                    }
                } else {
                    codeLines++;
                    boolean first = true;

                    for (int i = 1; i <= 10; i++) {
                        // 指定判断规则(中括号内字符顺序随便)
                        //Pattern p = Pattern.compile("[A-Za-z0-9_\\(\\),\\.\"\';@:\\s]+([\\u4e00-\\u9fa5，：。、】【！#￥%……&*（）]{1,1000})[A-Za-z0-9_\\(\\),\\.\"\';@:\\s]+");
                        Pattern p = Pattern.compile(".*\"(.*[\\u4e00-\\u9fa5]+[A-Za-z0-9_]*[\\，\\。\\：\\'\\【\\】\\！\\￥\\%\\…\\…\\&\\（\\）\\—\\—\\+\\{\\}\\：\\“\\？\\》\\《\\,\\.\\;\\'\\[\\]\\=\\-\\!\\@\\#\\$\\%\\^\\&\\*]*)\".*");
                        // 进行规则匹配
                        Matcher matcher = p.matcher(line);
                        // 进行判断
                        boolean b = matcher.matches();
                        //System.out.println(b);
                        if (b) {
                            if (first) {
                                System.out.println("=============");
                                System.out.println("文件 = " + f + "，行 = " + lineNum);
                                System.out.println(line);
                                first = false;
                            }
                            //int k=matcher.groupCount();
                            //System.out.println("groupCount is -->" + matcher.groupCount());
                            String s = matcher.group(1);
                            System.out.println(s);
                            line = line.replace("\"" + s + "\"", "");//从行中删除s,再检测一次
                        }
                    }

                }
            }
            files++;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static void main(String[] args) {
////        String code="map.put(\"content\", \"商品分类绑定推荐位成功\");";
////        String code="@RequiresPermissions(\"product:productCategory:edit:中文\")";
//        String code="logger.error(\"商品分类绑定推荐位异常，异常信息：\", e);";
//
//        // 指定判断规则(中括号内字符顺序随便)
//        Pattern p = Pattern.compile("[A-Za-z0-9_\\(\\),\\.\"\';@:\\s]+([\\u4e00-\\u9fa5，：。、】【！#￥%……&*（）]{1,1000})[A-Za-z0-9_\\(\\),\\.\"\';@:\\s]+");
//        // 进行规则匹配
//        Matcher matcher = p.matcher(code);
//        // 进行判断
//        boolean b = matcher.matches();
//        System.out.println(b);
//
//        System.out.println("groupCount is -->" + matcher.groupCount());
//        if(b) {
//            System.out.println("found: " + matcher.group(1));
//        }
//    }
}