package com.sicheng.common.utils4m;

import com.sicheng.common.mapper.JsonMapper;

public class SignFilterTest {
    public static void main(String[] args) {
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
