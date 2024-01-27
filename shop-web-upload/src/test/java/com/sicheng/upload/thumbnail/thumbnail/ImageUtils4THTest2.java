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
package com.sicheng.upload.thumbnail.thumbnail;

import com.sicheng.upload.thumbnail.ImageInfo;
import com.sicheng.upload.thumbnail.MaterialLibrary;
import com.sicheng.upload.thumbnail.thumbnailator.ImageUtils4TH;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * 单元测试类
 * <p>
 * 标题:
 * </p>
 * <p>
 * 描述:
 * </p>
 * <p>
 * 公司: 思程科技 www.sicheng.net
 * </p>
 *
 * @author zhaolei
 * @version 2016年7月31日 下午10:14:40
 */
public class ImageUtils4THTest2 {

    int width = 180;//生成的缩略图宽度，单位像素
    int height = 180;//生成的缩略图高度，单位像素
    int quality = 90;//画质
    int times = 10;//循环次数
    String format = "jpg";
    String name = "f1.jpg";//被处理的图片名称
    String path = MaterialLibrary.getImagePath(name);//被处理的图片的路径
    String outFolder = System.getProperty("java.io.tmpdir");//文件输出路径

    /**
     * 一个循环把MaterialLibrary中的所有图片全测试了
     * @throws IOException
     */
    @Test
    public void test_All() throws IOException {
        Set<String> set=MaterialLibrary.getkeys();
        for(String key:set){
            String path = MaterialLibrary.getImagePath(key);//被处理的图片的路径
            FileInputStream inputStream = new FileInputStream(path);
            System.out.println("处理："+path);
            InputStream data = ImageUtils4TH.resize(inputStream, 50, 50, 95, "jpg");
            File file_out = new File(outFolder + "/th_resize." + format);
            FileUtils.copyInputStreamToFile(data, file_out);
        }
    }

    @Test
    public void test_resize() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        InputStream data = ImageUtils4TH.resize(inputStream, 185, 185, 95, "jpg");
        File file_out = new File(outFolder + "/th_resize." + format);
        FileUtils.copyInputStreamToFile(data, file_out);
    }

    @Test
    public void test_resizeAndCut() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        InputStream data = ImageUtils4TH.resizeAndCut(inputStream, 185, 185, 95, "jpg");
        File file_out = new File(outFolder + "/th_resizeAndCut." + format);
        FileUtils.copyInputStreamToFile(data, file_out);
    }

    @Test
    public void test_getImageInfo() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        ImageInfo info = ImageUtils4TH.getImageInfo(inputStream);
        System.out.println(info.toString());
        TestCase.assertEquals("JPEG", info.getFormat());
        TestCase.assertEquals(4000, info.getWidth());
        TestCase.assertEquals(3000, info.getHeight());
    }

    @Test
    public void test_getFormat() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        String format = ImageUtils4TH.getFormat(inputStream);
        TestCase.assertEquals("JPEG", format);
    }

    @Test
    public void test_getImageWH() throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        ImageInfo info = ImageUtils4TH.getImageWH(inputStream);
        System.out.println(info.toString());
        TestCase.assertEquals(4000, info.getWidth());
        TestCase.assertEquals(3000, info.getHeight());
    }

}