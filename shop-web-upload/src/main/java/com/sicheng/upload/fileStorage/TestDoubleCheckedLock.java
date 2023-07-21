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
package com.sicheng.upload.fileStorage;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 双重检查锁double-checked locking 的可靠性测试工具类
 * 如何使用
 * 1、修改main方法中有4个测试方法，注释掉3个测试方法，放开1个测试方法。
 * 2、运行本程序看控制台输出的结果
 * 3、控制台输出“测试结束,共创建了1个对象，未重复创建对象线程安全”  说明是线程安全的
 * 4、否则说明是线程不安全的
 *
 * 其它参考文章：
 * 双重检查锁（double-checked locking）  https://blog.csdn.net/felix021/article/details/106066786
 * Java中的双重检查锁（double checked locking） https://www.cnblogs.com/lzghyh/p/12581477.html
 */
public class TestDoubleCheckedLock {

    /**
     * 多线程测试方法
     * <p>
     * CyclicBarrier内部也维护了一个计数器，用于标识多个线程是否已经执行到了某个地方，通知这些线程往下执行。
     * 与CountDownLatch不同的在于，栅栏可以被使用多次，所有参与方处于唤醒状态，
     * 任何一个参与方再度调用CyclicBarrier.await()又会被暂停，直到最后一个参与方执行了CyclicBarrier.await()。
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        TestDoubleCheckedLock test = new TestDoubleCheckedLock();//被测试的业务模拟类
        int threadCount = 10;//并发的线程数
        final CountDownLatch cdl = new CountDownLatch(threadCount);//类是一个计数器
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount);//集合点
        Set<Integer> set=new TreeSet<Integer>();
        for (int i = 1; i <= threadCount; i++) {
            int finalI = i;
            Thread t=new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 收集到第" + finalI + "颗龙珠");
                try {
                    // 用于等待-集合点
                    cyclicBarrier.await();

                    //多线程去同时去调用 test.createOrGetClient()方法，用于测试此方法是否线程安全
                    System.out.println("****召唤神龙");

                    //测试0
//                    Object obj = test.createOrGetClient0();

                    //测试1
//                    Object obj = test.createOrGetClient1();

                    //测试2
                    Object obj = test.createOrGetClient2();

                    //测试3
//                    Object obj = test.createOrGetClient3();

                    //测试4
//                    Object obj = test.createOrGetClient3();

                    //输出hashcode,通过hashcode是否相同来判断是否是同一个对象
                    //System.out.println("hashcode:" + obj.hashCode());
                    set.add(obj.hashCode());
                    cdl.countDown();//此方法是CountDownLatch的线程数-1

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, String.valueOf(i));
            t.start();
        }

        //线程启动后调用countDownLatch方法
        try {
            cdl.await();//需要捕获异常，当其中线程数为0时这里才会继续运行
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("测试结束,共创建了"+set.size()+"个对象，"+(set.size()>1?"重复创建了对象线程不安全":"未重复创建对象线程安全"));
    }

    /**
     * 测试0
     * 这是一个最显而易见的错误写法，无法满足多线程的场景
     * <p>
     * 经过测试这个方法不是线程安全的
     * 调用都反回了不是同一个对象（对象的hashcode不同说明是重复创建了对象）
     * 你要多次执行，才可能暴露出问题！！！
     *
     * @return
     */
    private Object client0 = null; //客户端类
    public Object createOrGetClient0() throws InterruptedException {
        if (client0 == null) {
            Thread.sleep(0L);
            System.out.println("创建新对象了:client = new Object();");
            // 初始化一个Object
            client0 = new Object();
        }
        return client0;
    }

    /**
     * 测试1
     * 这是我最常用、最习惯的 双重检查锁double-checked locking 写法，
     * 网上说这样不对，sonar代码检查说这样写不对。
     * <p>
     * 经过测试这个方法是线程安全的
     * 每次调用都反回了同一个对象（每个对象的hashcode相同说明是旧对象）
     *
     * sonar代码检查说这样写不对，是因为这样写导致了一个新问题：
     * 对可变对象的延迟初始化使用双重检查锁定，可能会导致第二个线程在第一个线程仍在创建时使用未初始化或部分初始化的成员，并使程序崩溃。
     *
     * @return
     */
    private Object client = null; //客户端类

    private Object createOrGetClient1() throws InterruptedException {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    Thread.sleep(0L);
                    System.out.println("创建新对象了:client = new Object();");
                    // 初始化一个Object
                    client = new Object();
                }
            }
        }
        return client;
    }

    /**
     * 测试2
     * 这是sonar建议的 双重检查锁double-checked locking 写法，是否可信呢？请看下面的测试
     * <p>
     * 经过测试这个方法是线程安全的
     * 每次调用都反回了同一个对象（每个对象的hashcode相同说明是旧对象）
     *
     * 遗憾：每次读取client2变量，性能稍差一点，因为 volatile的内存屏障语义，会导致每次读、写的时候对应缓存失效
     *
     * 建议采用这种方案，本方案编写简单一学变会不会出错，虽然性能稍有损失但体绘不到。
     *
     * @return
     */

    private volatile Object client2 = null; //客户端类，有volatile 也是安全的
//    private Object client2 = null; //客户端类，无volatile也是线程安全的，好像volatile没用？答：其实加volatile是解决上面的“获取到未初始化或部分初始化的成员，并使程序崩溃”的问题

    private Object createOrGetClient2() throws InterruptedException {
        if (client2 == null) {
            synchronized (this) {//安全
                if (client2 == null) {
                    Thread.sleep(0L);
                    System.out.println("创建新对象了:client = new Object();");
                    // 初始化一个Object
                    client2 = new Object();
                }
            }
        }
        return client2;
    }

    /**
     * 测试3
     * 这是sonar建议的 双重检查锁double-checked locking 写法，是否可信呢？请看下面的测试
     * <p>
     * 经过测试这个方法是线程安全的
     * 每次调用都反回了同一个对象（每个对象的hashcode相同说明是旧对象）
     *
     * 针对上面的遗憾：“每次读取client2变量，性能稍差一点，因为 volatile的内存屏障语义，会导致每次读、写的时候对应缓存失效”  做了局部变量缓存优化。
     *
     * @return
     */

    private volatile Object client3 = null; //客户端类，有volatile 也是安全的
//    private Object client2 = null; //客户端类，无volatile也是线程安全的，好像volatile没用？答：其实加volatile是解决上面的“获取到未初始化或部分初始化的成员，并使程序崩溃”的问题

    private Object createOrGetClient3() throws InterruptedException {
        // 注意这个局部变量 "client33"，似乎看起来没必要，实际作用是，当 client3 被初始化以后（大多数情况下），
        // 这个 volatile 字段只需要被访问一次（最后return的是 client33 的值，而不是 client3），这最高可以使该方法的整体性能提高25%[7]（注：volatile的内存屏障语义，会导致每次读、写的时候对应缓存失效）。
        Object client33 = client3;
        if (client33 == null) {
//            synchronized (FileStorageMinio.class) {//不安全，为什么使用FileStorageMinio.class会线程不安全? 当然你也不应用去使用FileStorageMinio.class
//            synchronized (Object.class) {//安全
            synchronized (this) {//安全
                client33 = client3;//重要，必须加，否则会导致双重检查锁失效，不信你多执行几次测试就会复现的。
                if (client33 == null) {
                    Thread.sleep(0L);
                    System.out.println("创建新对象了:client = new Object();");
                    // 初始化一个Object
                    client3 = client33 = new Object();

                }
            }
        }
        return client33;
    }


    /**
     * 测试4
     * 这是sonar建议的 使用传统synchronized 的写法，是否可信呢？请看下面的测试
     * <p>
     * 经过测试这个方法是线程安全的
     * 每次调用都反回了同一个对象（每个对象的hashcode相同说明是旧对象）
     *
     * @return
     */
    private volatile Object client4 = null; //客户端类

    private synchronized Object createOrGetClient4() throws InterruptedException {
        if (client4 == null) {
            Thread.sleep(0L);
            System.out.println("创建新对象了:client = new Object();");
            // 初始化一个Object
            client4 = new Object();
        }
        return client4;
    }


}
