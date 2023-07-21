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
package com.sicheng.upload.component;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * <p>标题: MyCommonsMultipartResolver</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2017年8月14日 下午9:35:33
 */
public class MyCommonsMultipartResolver extends CommonsMultipartResolver {

    /**
     * @Fields excludeUrls : 要排除的url
     */
    private List<String> excludeUrls = Collections.emptyList();

    /**
     * <p>
     * 重写父类的isMultipart方法
     * 目标：对指定的url，不使用spring的CommonsMultipartResolver来处理上传，放过，由后面的业务程序自行处理。
     * 理由：因本项目中使用了ueditor,接收上传的服务端，是一套独立，不能让spring来参与处理，否则会有冲突的。
     * </p>
     *
     * @param request
     * @return
     * @see org.springframework.web.multipart.commons.CommonsMultipartResolver#isMultipart(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public boolean isMultipart(HttpServletRequest request) {
//		String s1=request.getRequestURI();
//		String s2=request.getRequestURL().toString();
//		String s3=request.getContextPath();
//		String s4=request.getPathInfo();
//		String s5=request.getQueryString();
//		String s6=request.getServletPath();
//		System.out.println("s1:"+s1);
//		System.out.println("s2:"+s2);
//		System.out.println("s3:"+s3);
//		System.out.println("s4:"+s4);
//		System.out.println("s5:"+s5);
//		System.out.println("s6:"+s6);

        String path = request.getServletPath();
        for (String s : excludeUrls) {
            if (StringUtils.isNoneBlank(s) && s.equals(path)) {
                return false;
            }
        }
        return (request != null && ServletFileUpload.isMultipartContent(request));
    }

    /**
     * @return the excludeUrls
     */
    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    /**
     * @param excludeUrls the excludeUrls to set
     */
    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    /**
     * 覆盖了父类的方法
     */
    public void setMaxUploadSize(long maxUploadSize) {
        super.setMaxUploadSize(maxUploadSize);
    }

}
