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
package com.sicheng.upload.thumbnail;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>标题: 物料库 MaterialLibrary</p>
 * <p>描述: 物料库中有批用于测试的图片</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年9月17日 下午10:40:30
 */
public class MaterialLibrary {

    private static Map<String, String> map = new HashMap<String, String>();

    static {
        map.put("变色.jpg", "/测试专用图片/变色/变色.jpg");
        map.put("变色2.jpg", "/测试专用图片/变色/变色2.jpg");
//        map.put("2万像素的大图.jpg", "/测试专用图片/大图/2万像素的大图.jpg");  //,Thumbnailator方案无法处理本图片所以先注释了，它造成了java.lang.OutOfMemoryError: Java heap space。但GM方案可处理
        map.put("36M大图.bmp", "/测试专用图片/大图/36M大图.bmp");
        map.put("透明.png", "/测试专用图片/透明背景图/透明.png");
        map.put("透明2.png", "/测试专用图片/透明背景图/透明2.png");
        map.put("8位cmyk.jpg", "/测试专用图片/cmyk/8位cmyk.jpg");  //另类的图片
        map.put("g1.gif", "/测试专用图片/GIF/g1.gif");
        map.put("g2.gif", "/测试专用图片/GIF/g2.gif");
        map.put("g3.gif", "/测试专用图片/GIF/g3.gif");
        map.put("g4.gif", "/测试专用图片/GIF/g4.gif");
        map.put("a1.jpg", "/测试专用图片/JPEG/a1.jpg");
        map.put("a2.jpg", "/测试专用图片/JPEG/a2.jpg");
//        map.put("b1.jpg", "/测试专用图片/JPEG/b1.jpg"); //另类的图片,Thumbnailator方案无法处理本图片所以先注释了，但GM方案可处理
        map.put("b2.jpg", "/测试专用图片/JPEG/b2.jpg");
        map.put("c1.jpg", "/测试专用图片/JPEG/c1.jpg");
        map.put("c2.jpg", "/测试专用图片/JPEG/c2.jpg");
        map.put("e1.jpg", "/测试专用图片/JPEG/e1.jpg");
        map.put("e2.png", "/测试专用图片/JPEG/e2.png");
        map.put("f1.jpg", "/测试专用图片/JPEG/f1.jpg");
        map.put("f2.jpg", "/测试专用图片/JPEG/f2.jpg");
        map.put("p1.png", "/测试专用图片/PNG/p1.png");
        map.put("p2.png", "/测试专用图片/PNG/p2.png");
        map.put("p3.png", "/测试专用图片/PNG/p3.png");
        map.put("p4.png", "/测试专用图片/PNG/p4.png");
        map.put("ps1.jpg", "/测试专用图片/破损/111.jpg");  //另类的图片，已解决可成功处理
    }

    /**
     * 获取所有key
     * @return
     */
    public static Set<String> getkeys(){
        Set<String> set = map.keySet();
        return set;
    }

    /**
     * 从物料库中取测试图片
     *
     * @param name 名称
     * @return 测试图片的路径
     */
    public static String getImagePath(String name) {
        String path = map.get(name);
        //获取类的根路径classes/(重要)
        URL s2 = MaterialLibrary.class.getResource("/");
        String parnetPath = s2.getPath();//如果路径有空格会使用 %20替代
        return parnetPath + path;
    }

}
