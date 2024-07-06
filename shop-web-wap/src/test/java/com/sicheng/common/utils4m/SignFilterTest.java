package com.sicheng.common.utils4m;

import com.sicheng.common.mapper.JsonMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 签名与验签的单元测试
 */
public class SignFilterTest {
    String salt_d="tianwanggaidihu" ; //签名用的盐,这是一个模拟值
    SignFilter signFilter = new SignFilter();
    /**
     * 测试签名与验签，正常的情况：0：验证签名成功
     *
     * 0：验证签名成功
     * 1：验证签名失败-1：无sign、无timestamp、无dataBase64
     * 2：验证签名失败-2: 验证时间戳未通过(允许正负偏差各一个小时)
     * 3：验证签名失败-3：验证签名未通过
     * 4：验证签名失败-4：业务数据格式不正确
     * 5：验证签名失败-5：服务端验证签名发生异常
     */
    @Test
    public void test0() {
        //签名
        //前端客户端的业务参数，前端永远只传这3个参数来
        String dataBase64 = "我是业务数据";//业务数据，前端发来的是Base64格式
        long timestamp = System.currentTimeMillis() - (1000 * 60 * 60) + 1000;//make : 前端发来的时间戳【+1000 是为了不超时】
        String clientSign=signFilter.signature(dataBase64, Long.toString(timestamp),salt_d);  //签名

        //验签
        //服务端对客户端传来的参数做“验签”
        int rs= signFilter.verifySignature(dataBase64, Long.toString(timestamp),clientSign,salt_d);
        assertEquals(rs, 0);
    }
    /**
     * 测试签名与验签。失败情况：验证签名失败-1：无sign、无timestamp、无dataBase64
     *
     * 0：验证签名成功
     * 1：验证签名失败-1：无sign、无timestamp、无dataBase64
     * 2：验证签名失败-2: 验证时间戳未通过(允许正负偏差各一个小时)
     * 3：验证签名失败-3：验证签名未通过
     * 4：验证签名失败-4：业务数据格式不正确
     * 5：验证签名失败-5：服务端验证签名发生异常
     */
    @Test
    public void test1() {
        //签名
        //前端客户端的业务参数，前端永远只传这3个参数来
        String dataBase64 = "我是业务数据";//业务数据，前端发来的是Base64格式
        long timestamp = System.currentTimeMillis() - (1000 * 60 * 60) + 1000;//make : 前端发来的时间戳【+1000 是为了不超时】
        String clientSign=signFilter.signature(dataBase64, Long.toString(timestamp),salt_d);  //签名

        //验签  （故意把clientSign 设置为 null）
        clientSign=null;

        //服务端对客户端传来的参数做“验签”
        int rs= signFilter.verifySignature(dataBase64, Long.toString(timestamp),clientSign,salt_d);
        assertEquals(rs, 1);
    }

    /**
     * 测试签名与验签。失败情况：验证签名失败-2: 验证时间戳未通过
     *
     * 0：验证签名成功
     * 1：验证签名失败-1：无sign、无timestamp、无dataBase64
     * 2：验证签名失败-2: 验证时间戳未通过(允许正负偏差各一个小时)
     * 3：验证签名失败-3：验证签名未通过
     * 4：验证签名失败-4：业务数据格式不正确
     * 5：验证签名失败-5：服务端验证签名发生异常
     */
    @Test
    public void test2() {
        //签名
        //前端客户端的业务参数，前端永远只传这3个参数来
        String dataBase64 = "我是业务数据";//业务数据，前端发来的是Base64格式

        //【-1 是为了制造时间戳正负偏差超过一个小时】
        long timestamp = System.currentTimeMillis() - (1000 * 60 * 60) -1 ;//make : 前端发来的时间戳 【-1 是为了制造超时】
        String clientSign=signFilter.signature(dataBase64, Long.toString(timestamp),salt_d);  //签名

        //验签
        //服务端对客户端传来的参数做“验签”
        int rs= signFilter.verifySignature(dataBase64, Long.toString(timestamp),clientSign,salt_d);
        assertEquals(rs, 2);
    }

    /**
     * 测试签名与验签。失败情况：验证签名失败-3：验证签名未通过
     *
     * 0：验证签名成功
     * 1：验证签名失败-1：无sign、无timestamp、无dataBase64
     * 2：验证签名失败-2: 验证时间戳未通过(允许正负偏差各一个小时)
     * 3：验证签名失败-3：验证签名未通过
     * 4：验证签名失败-4：业务数据格式不正确
     * 5：验证签名失败-5：服务端验证签名发生异常
     */
    @Test
    public void test3() {
        //签名
        //前端客户端的业务参数，前端永远只传这3个参数来
        String dataBase64 = "我是业务数据";//业务数据，前端发来的是Base64格式
        long timestamp = System.currentTimeMillis() - (1000 * 60 * 60) +1000 ;//make : 前端发来的时间戳 【-1 是为了制造超时】
        String clientSign=signFilter.signature(dataBase64, Long.toString(timestamp),salt_d);  //签名

        //模拟数据被篡改
        dataBase64=dataBase64+"-模拟被篡改";

        //验签
        //服务端对客户端传来的参数做“验签”
        int rs= signFilter.verifySignature(dataBase64, Long.toString(timestamp),clientSign,salt_d);
        assertEquals(rs, 3);//因为数据被篡改，希望验证失败
    }

    /**
     * 测试  JsonMapper.getInstance().fromJson
     */
    @Test
    public void test_fromJson() {
        String jsonStr = "{data:\"e2E6MSxiOjJ9\",timestamp:\"111\",sign:\"666\"}";
        Vo json = JsonMapper.getInstance().fromJson(jsonStr, SignFilterTest.Vo.class);
        System.out.println(json);
    }

    static class Vo {
        String data;
        String timestamp;
        String sign;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return "Vo{" +
                    "data='" + data + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", sign='" + sign + '\'' +
                    '}';
        }
    }
}
