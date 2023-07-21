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
package com.sicheng.common.aes;

import com.sicheng.pay.weixin.WeixinPayUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.Security;

/**
 * <p>标题: AES256</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author cailong
 * @version 2018年4月18日 下午6:41:07
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class AES256Test {

    /**
     * 测试AES256解密
     * 注意：只有JDK1.8 161 之后的版本才支持
     */
    @Test
    public void AES256() {

        String content = "VjbBTu93C9TKbcbiN3qCK0qXqKb4IaHvisWfnBfOc3ocvd0q7c8x0jm99RV/A55FT6Pko2AhJ/bHtFctMEMlhSFasOLnR/gYxq2g3redAOluPVA9qZbjNCMZYJ7TqsOYkqfX3uiNhfd9CV1fn+fYEC/quxQUqzpBIL7JikPqb1on03nxr/2G+6G1e1NduyKMioxxIpQG+3k4YPCfv5FAoUWQMg6z42elMvGX8BY5iyHek57+LnUUmlGaSlPf89a7VsWXxB+K86hS/TcXrdlwKDxRISHl8TW6QkrGTeIVmqYUf/mKFiraimZDXPEARJKhoP/z+dFDO4HVXGuTWJAIM4f1hIo1lhIO0MMIrJIUkIYISnoqJWa2JjA9M8wek8b9v02kqXUIHBW13Tpb0k8ACLgZp0pq5GELRED+XwElhz6+grrp102iejMmZaIpbzb1cNsKIich7aBs5Tfq43argiyMutad6/cyTIRzAayh7bV64wE8idLnm1QPYKRlkTm27KuZhBw4GtmNnAKbCuZV72+7+IC6Kc/OpC2fwitdnhyWKRDTvjWT1ranIaHTiWGGqTH+xePAmuezjpInC8gL+mkpA9HBZcjOMp8H9fmXM4DJJF1hSukrYWVtOQrUSbpL7Q8U6ZtSuAcC4/YZOpvf/GdKYnDfO5Xs4ix9ltmYx4AKuFlr2qzef7IwsGPLzE3JvKCZouDN7JdqZHgS+jedYPX9MZljd1uQTfomphdnjZyMgbOSXmeq0HMTc1rtOjQv/TVKezPOLTPTW5v+B2msMPpULk1bTkE5kC67vUqjtAJ8LV+gJNmcxwvyLpDkeZ4AvXgaF/H7jwnSimli+S3pZqYxNWmAMJpEfDuwZYWkbnYM7QzlE2PZxdGh0o8KH2lEDwaafMc4U9GaTrNeMclLyW70GN4NK5ZZKFKXpPn428uf5yHPPM4U7NJ+SX7BTw5hjLbBHXO/WECdoCbnrpWbeaVLr8YifWT1OLdPy5jrFglcEJEzUKQ8VCVwOYXcS7Uj9Ijw6y+5f8NHyB+iNEuUfQ==";
        String deciphering_content;
        try {
            Security.addProvider(new BouncyCastleProvider());
            deciphering_content = WeixinPayUtil.decryptData(content, "weixin");

            System.out.println("微信密文通知：" + content);

            System.out.println("微信密文解密后通知：" + deciphering_content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
