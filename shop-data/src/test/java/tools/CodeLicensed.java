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
package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 代码头部的版权检查
 *
 * @author zhaolei
 * @version 2014-7-22
 */
public class CodeLicensed {
    static long allFiles = 0; //总文件数量
    static long okFiles = 0; //通过文件数量
    static ArrayList<File> fileArray = new ArrayList<File>();

    //这是我的标准版权信息的格式
    static String copyright = "/**\n" +
            " * 本作品使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权。\n" +
            " * Copyright (c) 2016 SiCheng.Net\n" +
            " * This software is licensed under Mulan PubL v2.\n" +
            " * You can use this software according to the terms and conditions of the Mulan PubL v2.\n" +
            " * You may obtain a copy of Mulan PubL v2 at:\n" +
            " *          http://license.coscl.org.cn/MulanPubL-2.0\n" +
            " * THIS SOFTWARE IS PROVIDED ON AN \"AS IS\" BASIS, WITHOUT WARRANTIES OF ANY KIND,\n" +
            " * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,\n" +
            " * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.\n" +
            " * See the Mulan PubL v2 for more details.\n" +
            " */\n";

    /**
     * 入口方法，可独立运行
     */
    public static void main(String[] args) {
        String info = null;
        info = "######################################\r\n";
        info += "##          代码头部的版权检查           ##\r\n";
        info += "######################################\r\n";
        System.out.println(info);


        //确定要分析的工程根目录位置
        // /Users/atom/workspace/dev-java/IdeaProjects/project-fdp
        String file = CodeLicensed.class.getResource("/").getFile();
        String path = new File(file).getParentFile().getParentFile().getParentFile().getPath();

        System.out.println("正在分析此目录下的文件：" + path);
        ArrayList<File> allFiles = findAllFiles(new File(path));//获得目录下的所有文件

//        String[] typeArr=new String[]{"java","jsp","js","xml","properties"};//要分析的文件类型
        String[] typeArr = new String[]{"java"};//要分析的文件类型
        for (String type : typeArr) {
            for (File f : allFiles) {
                if (f.getName().matches(".*\\." + type + "$")) { // 匹配java格式的文件
                    analysis(f);
                }
            }
            out(type);
        }
    }

    /**
     * 输出统计的数量并清空累加值
     *
     * @param type 文件类型
     */
    public static void out(String type) {
        System.out.println(type + "总文件数量：" + allFiles);
        System.out.println(type + "检查通过文件数量：" + okFiles);
        System.out.println(type + "检查未通过文件数量：" + (allFiles- okFiles));
        System.out.println();
        allFiles = 0;
        okFiles = 0;
    }

    /**
     * 获得目录下的所有文件
     *
     * @param dir 目录
     * @return 文件 List
     */
    public static ArrayList<File> findAllFiles(File dir) {
        File[] files = dir.listFiles();
        for (File child : files) {
            if (child.isDirectory()) {
                findAllFiles(child);
            } else
                fileArray.add(child);
        }
        return fileArray;
    }

    /**
     * 分析统计某个 java 文件 头部版权信息
     *
     * @param file java 文件
     */
    private static void analysis(File file) {
        BufferedReader br = null;
        boolean flag = false;
        try {
            String text = "";
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                String lineText = line.trim(); // 除去注释前的空格

                if ((lineText.startsWith("/*") && !lineText.endsWith("*/")) ||
                        (lineText.startsWith("<!--") && !lineText.endsWith("-->")) ||
                        (lineText.startsWith("<%--") && !lineText.endsWith("--%>"))
                ) { // 匹配多行注释
                    text += line+"\n";
                    flag = true;
                } else if (flag == true) {
                    text += line+"\n";
                    if (lineText.endsWith("*/") || lineText.endsWith("-->") || lineText.endsWith("--%>")) {
                        flag = false;
                        break;
                    }
                } else if(lineText.startsWith("package")){
                    break;

                }
            }

            if(!text.equals(copyright)){
                System.out.println(text);
                System.out.println("文件："+file.getAbsolutePath()+" 未通过版权检查");
            }else{
//                System.out.println("文件："+file.getAbsolutePath()+" 通过版权检查");
                okFiles++; // 统计通过版权检查的文件数量
            }
            allFiles++; // 统计文件数量
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignore) {
                }
            }
        }
    }
}