package com.sicheng.common.utils;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * <p>标题: FYUtils的测试测试  </p>
 * <p>描述: xxxx  </p>
 * <p>使用示例: </P>
 * <pre>
 *    Window win = new Window(parent);//请作者手动完善此信息
 *    win.show();
 * </pre>
 * <p>公司: 思程科技 www.sicheng.net</p>
 *
 * @author zhaolei
 * @version 2023-10-02 12:29
 *
 * <p>重要修改历史记录1: xxxx  。修改人：xx</p>
 * <p>重要修改历史记录2: xxxx  。修改人：xx</p>
 */
public class FYUtilsTest {
//
//    //因需要R.getRequest()，无法独立运行
//    @Test
//    public void test_fyParams(){
//        System.out.println(FYUtils.fyParams("手机号"));
//        System.out.println(FYUtils.fyParams("查询第三方订单发生错误："));
//    }
    @Test
    public void test_getFYProperties(){
//        Map<String, String> cnMap = FYUtils.getFYProperties("test_msg_zh_CN.properties");
        Map<String, String> cnMap = FYUtils.getFYProperties("test_member_zh_CN.properties");
        Set<String> keys=cnMap.keySet();
        for(String key:keys){
            String value=cnMap.get(key);
            System.out.println(key+" = "+value);
        }

    }
}
