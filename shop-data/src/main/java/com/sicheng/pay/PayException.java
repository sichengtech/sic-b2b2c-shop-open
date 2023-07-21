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
package com.sicheng.pay;

/**
 * <p>标题: 支付异常</p>
 * <p>描述: 第三方支付实现类抛出的异常。</p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2018年3月26日 下午1:39:52
 */
public class PayException extends Exception {

    /**
     * 无参构造方法
     */
    public PayException() {
        super();
    }

    /**
     * 有参的构造方法
     */
    public PayException(String message) {
        super(message);
    }

    /**
     * 用指定的详细信息和原因构造一个新的异常
     */
    public PayException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 用指定原因构造一个新的异常
     */
    public PayException(Throwable cause) {
        super(cause);
    }

}
