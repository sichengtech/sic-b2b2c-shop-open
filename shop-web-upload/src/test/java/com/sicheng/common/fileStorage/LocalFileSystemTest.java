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
package com.sicheng.common.fileStorage;

import com.sicheng.common.utils.IOUtils;
import com.sicheng.upload.fileStorage.FileStorageFcatory;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>标题: LocalFileSystemTest</p>
 * <p>描述: </p>
 *
 * @author zhaolei
 * @version 2017年9月21日 上午9:57:33
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class LocalFileSystemTest {

    //生成一个无扩展名的文件路径
    @Test
    public void testFileName() {
        FileStorage lf = FileStorageFcatory.get();
        String fileName = lf.fileName();
        System.out.println(fileName);
        TestCase.assertNotNull(fileName);
    }

    //生成一个有扩展名的文件路径
    @Test
    public void testFileName2() {
        FileStorage lf = FileStorageFcatory.get();
        String fileName = lf.fileName("jpg");
        System.out.println(fileName);
        TestCase.assertNotNull(fileName);
    }

    //生成一个有扩展名的文件路径，扩展名统一转为小写
    @Test
    public void testFileName2_1() {
        FileStorage lf = FileStorageFcatory.get();
        String fileName = lf.fileName(".JPG");
        System.out.println(fileName);
        TestCase.assertNotNull(fileName);
    }

    // 生成一个使用安全区域的文件路径
    // 生成文件名（含扩展名），使用安全safe目录,目录随机选择，文件名是使用UUID
    // 例如：/safe/49/45/9174e0d7c76b4e50991a188d7fa82a8b.jpg
    @Test
    public void testFileNameSafe() {
        FileStorage lf = FileStorageFcatory.get();
        String fileName = lf.fileNameSafe(".JPG");
        System.out.println(fileName);
        TestCase.assertNotNull(fileName);
    }

    // 写入文件（自动生成文件名）
    @Test
    public void testWrite() throws IOException {
        byte[] data = "abc".getBytes();
        FileStorage lf = FileStorageFcatory.get();
        String path = lf.write(IOUtils.byteArray2InputStream(data), "txt");
        System.out.println(path);
    }

    //写入文件（自动生成文件名），使用安全safe目录,本方法把文件存储到文件系统.并返回生成的文件名
    //例如：/safe/71/62/2b1ed5e940314a319154f7f31d4e6d0e.txt
    @Test
    public void testWriteSafe() throws IOException {
        byte[] data = "abc".getBytes();
        FileStorage lf = FileStorageFcatory.get();
        String path = lf.writeSafe(IOUtils.byteArray2InputStream(data), "txt");
        System.out.println(path);
    }

    //写入文件（外部传入文件名）,本方法把文件存储到文件系统，使用指定的文件名，存储到文件系统.并返回原文件名
    @Test
    public void testWrite2() throws IOException {
        byte[] data = "abc".getBytes();
        FileStorage lf = FileStorageFcatory.get();
        //String fileName=lf.fileName("jpg");//生成文件名
        String path = lf.write2(IOUtils.byteArray2InputStream(data), "/00/75/69/4cf339d7c7a742c9804c19fe843670bd.txt");
        System.out.println(path);
    }

    @Test
    public void testAppend() throws IOException {
        byte[] data = "abc".getBytes();
        FileStorage lf = FileStorageFcatory.get();
        boolean path = lf.append(IOUtils.byteArray2InputStream(data), "/00/75/69/4cf339d7c7a742c9804c19fe843670bd.txt");
        System.out.println(path);
    }

    @Test
    public void testRead() throws IOException {
        //先写入文件
        testWrite2();
        FileStorage lf = FileStorageFcatory.get();
        byte[] data = lf.read("/00/75/69/4cf339d7c7a742c9804c19fe843670bd.txt");
        System.out.println(new String(data));
    }

    @Test
    public void testModify() throws IOException {
        byte[] data = "dfg".getBytes();
        FileStorage lf = FileStorageFcatory.get();
        boolean bl = lf.modify(IOUtils.byteArray2InputStream(data), "/00/75/69/4cf339d7c7a742c9804c19fe843670bd.txt");
        System.out.println(bl);
    }

    @Test
    public void testDelete() throws IOException {
        FileStorage lf = FileStorageFcatory.get();
        boolean bl = lf.delete("/00/75/69/4cf339d7c7a742c9804c19fe843670bd.txt");
        System.out.println(bl);
    }

    @Test
    public void testSize() throws IOException {
        //先写入文件
        testWrite2();
        FileStorage lf = FileStorageFcatory.get();
        long size = lf.size("/00/75/69/4cf339d7c7a742c9804c19fe843670bd.txt");
        System.out.println(size);
    }

    @Test
    public void testOpenInputStream() throws IOException {
        FileStorage lf = FileStorageFcatory.get();
        InputStream in = lf.openInputStream("/00/75/69/4cf339d7c7a742c9804c19fe843670bd.txt");
        System.out.println(in);
    }

    @Test
    public void testOpenOutputStream() throws IOException {
        FileStorage lf = FileStorageFcatory.get();
        OutputStream size = lf.openOutputStream("/00/75/69/4cf339d7c7a742c9804c19fe843670bd.txt", false);
        System.out.println(size);
    }

}