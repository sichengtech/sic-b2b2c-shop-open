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
package com.sicheng.common.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Zip2String {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(Zip2String.class);

    /**
     * 通过支付宝查询对账单接口返回的url,下载zip文件
     *
     * @param alipay_url
     * @param down_url
     * @return
     */
    public static boolean downLoadZip(String alipay_url, String down_url) {
        boolean down_success = false;
        int bytesum = 0;
        int byteread = 0;
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            URL url = new URL(alipay_url);
            URLConnection conn = url.openConnection();
            inStream = conn.getInputStream();
            //自定义文件保存地址
            String unzipFilePath = down_url;
            if (down_url.lastIndexOf("\\") != -1) {
                unzipFilePath = down_url.substring(0, down_url.lastIndexOf("\\"));//判断下载保存路径文件夹
            }
            File unzipFileDir = new File(unzipFilePath);//下载文件存放地址
            if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
                unzipFileDir.mkdirs();
            }
            //解压文件是否已存在
            File entryFile = new File(down_url);
            if (entryFile.exists()) {
                //删除已存在的目标文件
                entryFile.delete();
            }
            fs = new FileOutputStream(down_url);
            byte[] buffer = new byte[4028];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            down_success = true;
        } catch (Exception e) {
            logger.debug("下载文件失败", e);
            return false;
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                inStream = null;
                logger.debug("下载文件失败", e);
            }
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException e) {
                fs = null;
                logger.debug("下载文件失败", e);
            }
        }
        return down_success;
    }

    /**
     * 读取zip文件，不解压缩直接解析，支持文件名中文，解决内容乱码
     *
     * @param file
     * @return 读取zip文件，返回字符串
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static String readZipToString(File file) throws Exception {
        String connet = "";
        try {
            //获得输入流，文件为zip格式，
            //支付宝提供
            ZipInputStream in = new ZipInputStream(new FileInputStream(file));
            //不解压直接读取,加上gbk解决乱码问题
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "gbk"));
            ZipEntry zipFile;
            //返回的字符串---每个文件内容相加
            BufferedWriter bw = null;
            //循环读取zip中的cvs文件，无法使用jdk自带，因为文件名中有中文
            while ((zipFile = in.getNextEntry()) != null) {
                if (zipFile.isDirectory()) {
                    //如果是目录，不处理
                    continue;
                }
                String file_connet = "";
                //获得cvs名字
                String fileName = zipFile.getName();
                //检测文件是否存在
                if (fileName != null && fileName.indexOf(".") != -1) {
                    String line;
                    /*
                     * 1.每一行用 | 隔开
                     * 2.每一个文件用 ; 隔开
                     */
                    //bw = new BufferedWriter(new FileWriter("d:\\test\\ceshi123.txt"));
                    while ((line = br.readLine()) != null) {
                        file_connet = file_connet + "|" + line;
                    }
                }
                connet = connet + file_connet + ";";
            }
            //bw.write(connet);
            //关闭流
            //bw.close();
            br.close();
            in.close();
        } catch (Exception e) {
            logger.debug("读取zip文件失败：", e);
            return "false";
        }
        return connet;
    }

    /**
     * 通过alipay_url获取下载的文件名称
     *
     * @param alipay_url
     * @return
     */
    public static String getDownloadFileName(String alipay_url) {
        String tempStr = alipay_url.substring(alipay_url.indexOf("downloadFileName") + 17);
        tempStr = tempStr.substring(0, tempStr.indexOf(".zip"));
        return tempStr;
    }

}
