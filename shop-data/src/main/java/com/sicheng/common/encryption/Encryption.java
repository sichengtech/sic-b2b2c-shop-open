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
package com.sicheng.common.encryption;

public class Encryption {

    /**
     * 截取字符串，中间按***代替
     *
     * @param str       字符串
     * @param frontShow 前面显示几位
     * @param afterShow 后面显示几位
     * @return 替换后的字符串
     */
    public static String EncryptionStr(String str, int frontShow, int afterShow) {
        if (str == null || "".equals(str)) {
            return "";
        }
        if (frontShow < 0 || afterShow < 0) {
            return str;
        }
        int length = str.length();
        int size = frontShow + afterShow;
        String frontShow1 = "";
        String frontShow2 = "";
        String frontShow3 = "";
        if (size == length) {
            return str;
        }
        if (size < length) {
            if (frontShow == 0) {
                if (afterShow == 0) {
                    for (int i = 0; i < length; i++) {
                        frontShow3 += "*";
                    }
                    return frontShow3;
                }
            }
            frontShow1 = str.substring(0, frontShow);
            frontShow2 = str.substring(length - afterShow, length);
            return frontShow1 + "****" + frontShow2;
        }
        if (size > length) {
            for (int i = 0; i < length; i++) {
                frontShow3 += "*";
            }
            return frontShow3;
        }
        return str;
    }

    /**
     * 测试用例
     *
     * @param args
     */
    public static void main(String[] args) {
        test1();
        test2();
        test2_1();
        test2_2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
    }

    public static void test1() {
        String a = "aaaaaabbhgjhfvghjvh";
        String b = EncryptionStr(a, 4, 2);
        System.out.println("1：" + b.equals("aaaa****vh"));
    }

    public static void test2() {
        String a = "aaaaaabb";
        String b = EncryptionStr(a, 0, 0);
        System.out.println("2：" + b.equals("********"));
    }

    public static void test2_1() {
        String a = "aaaaaabb";
        String b = EncryptionStr(a, 0, 5);
        System.out.println("2_1：" + b.equals("****aaabb"));
    }

    public static void test2_2() {
        String a = "aaaaaabb";
        String b = EncryptionStr(a, 5, 0);
        System.out.println("2_2：" + b.equals("aaaaa****"));
    }

    public static void test3() {
        String a = "aaaaaabb";
        String b = EncryptionStr(a, -1, -1);
        System.out.println("3：" + b.equals("aaaaaabb"));
    }

    public static void test4() {
        String a = "aaaaaabb";
        String b = EncryptionStr(a, 100, 100);
        System.out.println("4：" + b.equals("********"));
    }

    public static void test5() {
        String a = null;
        String b = EncryptionStr(a, 100, 100);
        System.out.println("5：" + b.equals(""));
    }

    public static void test6() {
        String a = "";
        String b = EncryptionStr(a, 100, 100);
        System.out.println("6：" + b.equals(""));
    }

    public static void test7() {
        String a = "aaaaaa";
        String b = EncryptionStr(a, 1, 5);
        System.out.println("7：" + b.equals("aaaaaa"));
    }

    public static void test8() {
        String a = "aaaaaa";
        String b = EncryptionStr(a, 4, 2);
        System.out.println("8：" + b.equals("aaaaaa"));
    }

    public static void test9() {
        String a = "aaaaaa";
        String b = EncryptionStr(a, 2, 4);
        System.out.println("9：" + b.equals("aaaaaa"));
    }

    public static void test10() {
        String a = "aaaaaa";
        String b = EncryptionStr(a, 3, 3);
        System.out.println("10：" + b.equals("aaaaaa"));
    }

}
