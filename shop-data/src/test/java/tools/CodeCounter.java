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
 * 代码行数统计程序
 * @author zhaolei
 * @version 2014-7-22
 */
public class CodeCounter {
    static long files = 0; //文件数量
    static long codeLines = 0; //代码行数
    static long commentLines = 0; //注释行数
    static long blankLines = 0; //空白行数
    static ArrayList<File> fileArray = new ArrayList<File>();

    /**
     * 入口方法，可独立运行
     */
    public static void main(String[] args) {
        String info = null;
        info = "######################################\r\n";
        info += "##          代码行数统计程序           ##\r\n";
        info += "######################################\r\n";
        System.out.println(info);


        //确定要分析的工程根目录位置
        // /Users/atom/workspace/dev-java/IdeaProjects/project-fdp
        String file = CodeCounter.class.getResource("/").getFile();
        String path = new File(file).getParentFile().getParentFile().getParentFile().getPath();

        System.out.println("正在分析此目录下的文件：" + path);
        ArrayList<File> allFiles = findAllFiles(new File(path));//获得目录下的所有文件

        String[] typeArr=new String[]{"java","jsp","js","xml","properties"};//要分析的文件类型
        for(String type:typeArr){
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
     * @param type
     */
    public static void out(String type) {
        System.out.println(type + "文件数量：" + files);
        System.out.println(type + "代码行数：" + codeLines);
        System.out.println(type + "注释行数：" + commentLines);
        System.out.println(type + "空白行数：" + blankLines);
        System.out.println();
        files = 0;
        codeLines = 0;
        commentLines = 0;
        blankLines = 0;
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
     * 分析统计某个 java 文件 的代码行数
     *
     * @param file java 文件
     */
    private static void analysis(File file) {
        BufferedReader br = null;
        boolean flag = false;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.trim(); // 除去注释前的空格
                if (line.matches("^[ ]*$")) { // 匹配空行
                    blankLines++;
                } else if (line.startsWith("//")) {  // 匹配单行注释
                    commentLines++;
                } else if (line.startsWith("/*") && line.endsWith("*/")) { // 匹配单行注释
                    commentLines++;
                } else if (line.startsWith("<!--") && line.endsWith("-->")) { // 匹配单行注释
                    commentLines++;
                } else if (line.startsWith("<%--") && line.endsWith("--%>")) { // 匹配单行注释
                    commentLines++;
                } else if ((line.startsWith("/*") && !line.endsWith("*/")) ||
                        (line.startsWith("<!--") && !line.endsWith("-->")) ||
                        (line.startsWith("<%--") && !line.endsWith("--%>"))
                ) { // 匹配多行注释
                    commentLines++;
                    flag = true;
                } else if (flag == true) {
                    commentLines++;
                    if (line.endsWith("*/")||line.endsWith("-->")||line.endsWith("--%>")) {
                        flag = false;
                    }
                } else {
                    codeLines++; // 匹配代码行
                }
            }
            files++; // 统计文件数量
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException ignore) {
                }
            }
        }
    }
}