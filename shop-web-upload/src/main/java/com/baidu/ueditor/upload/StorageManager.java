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
package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.sicheng.common.fileStorage.FileStorage;
import com.sicheng.common.utils.IOUtils;
import com.sicheng.upload.fileStorage.FileStorageFcatory;
import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>标题: 百度富文本编辑器的文件存储管理器</p>
 * <p>描述: 做出了修改，写把文件写入到指定的文件存储系统中</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年9月15日 下午3:25:41
 */
public class StorageManager {
    public static final int BUFFER_SIZE = 8192;

    public StorageManager() {
    }

    public static State saveBinaryFile(byte[] data, String path) {
        File file = new File(path);
        String suffix = FileType.getSuffixByFilename(path);
        FileStorage lf = FileStorageFcatory.get();
        String fileName = lf.fileName(suffix);
        try {
            // 写入文件存储系统
            lf.write2(IOUtils.byteArray2InputStream(data), fileName);
        } catch (IOException ioe) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        State state = new BaseState(true);
        state.putInfo("url", fileName);// 返回生成的文件名，/20/99/99/5263bcec293d4c998b758143525654ee.png
        state.putInfo("size", data.length);
        state.putInfo("title", file.getName());
        return state;
    }

    public static State saveFileByInputStream(InputStream is, String path, long maxSize) {
//		State state = null;
//		byte[] dataBuf = new byte[2048];
        try {
            BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);
//			// 输出流
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			int count = 0;
//			while ((count = bis.read(dataBuf)) != -1) {
//				bos.write(dataBuf, 0, count);
//			}
//			bos.flush();
//			bos.close();
//
//			// 文件的内容
//			byte[] data = bos.toByteArray();
//
//			// 检查文件大小
//			if (data.length > maxSize) {
//				return new BaseState(false, AppInfo.MAX_SIZE);
//			}

            // 写入文件存储系统
            String suffix = FileType.getSuffixByFilename(path);
            FileStorage lf = FileStorageFcatory.get();
            String fileName = lf.fileName(suffix);
            lf.write2(bis, fileName);

            State storageState = new BaseState(true);
            storageState.putInfo("size", 0);
            storageState.putInfo("url", fileName);// 返回生成的文件名，/20/99/99/5263bcec293d4c998b758143525654ee.png

            return storageState;

        } catch (IOException e) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    public static State saveFileByInputStream(InputStream is, String path) {
        try {
            BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

            // 写入文件存储系统
            String suffix = FileType.getSuffixByFilename(path);
            FileStorage lf = FileStorageFcatory.get();
            String fileName = lf.fileName(suffix);
            lf.write2(bis, fileName);

            State storageState = new BaseState(true);
            storageState.putInfo("size", 0);
            storageState.putInfo("url", fileName);// 返回生成的文件名，/20/99/99/5263bcec293d4c998b758143525654ee.png

            return storageState;
        } catch (IOException e) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static File getTmpFile() {
        File tmpDir = FileUtils.getTempDirectory();
        String tmpFileName = (Double.toString(Math.random() * 10000)).replace(".", "");
        return new File(tmpDir, tmpFileName);
    }

    private static State saveTmpFile(File tmpFile, String path) {
        State state = null;
        File targetFile = new File(path);

        if (targetFile.canWrite()) {
            return new BaseState(false, AppInfo.PERMISSION_DENIED);
        }
        try {
            FileUtils.moveFile(tmpFile, targetFile);
        } catch (IOException e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        state = new BaseState(true);
        state.putInfo("size", targetFile.length());
        state.putInfo("title", targetFile.getName());

        return state;
    }

    private static State valid(File file) {
        File parentPath = file.getParentFile();

        if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
            return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
        }

        if (!parentPath.canWrite()) {
            return new BaseState(false, AppInfo.PERMISSION_DENIED);
        }

        return new BaseState(true);
    }
}
