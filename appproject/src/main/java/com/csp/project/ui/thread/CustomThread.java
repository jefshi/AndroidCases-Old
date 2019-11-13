package com.csp.project.ui.thread;

/**
 * 串行线程组件
 * 1) 一个线程: Thread
 * 2) 一个消息(任务)队列: MessageQueue
 * 3) 一个消息迭代器: Looper (本身含消息队列)
 * 4) 多个消息发送与处理器: Handler
 * 
 * 自定义工作线程
 * 1) 参考HandlerThread
 * 2) 重写run()
 *   a) 创建Looper
 *   b) 绑定Looper(同步加锁)
 *   c) 设定优先级
 *   d) 追加额外动作(需对外提供相关方法以便被重写)
 *   e) Looper 迭代消息, 死循环
 * 3) 提供获取绑定的[looper]的方法
 *   a) 由于获取与设置在不同的并发线程中
 *   b) 并发线程共享同一数据: [looper]
 *   c) 并发线程对该数据执行非原子操作(数据变化)
 *   d) 故该步骤需要使用同步加锁
 * 4) 提供退出方法(可选)
 * 
 * 线程生命周期
 * 1) 创建: new Thread()[需重写run()方法]、new Thread(Runnable)
 * 1) 启动: start()
 * 2) 执行任务: run()
 * 2) 结束: run() 方法执行完毕(请结合消息迭代器说明)
 * 
 * 消息(任务)队列
 * 1) 线程按顺序执行任务
 * 2) 向线程发布多个任务时, 未处理的任务存放在消息队列
 * 
 * 消息迭代器
 * 1) 用于迭代消息, 以便线程能够串行执行多个任务
 * 2) 创建: Looper.prepare()
 *   a) 逻辑: 创建并绑定一个消息队列, 绑定当前线程
 *   b) 该方法一个线程只能执行一次
 * 3) 获取
 *   a) 获取当前线程的Looper, Looper.myLooper()
 *   a) 获取主线程的Looper, Looper.getMainLooper()
 * 4) 启动: Looper.loop()
 *   a) 迭代器一旦启动, 就是一个死循环, 故Looper不停止, 线程也不会停止
 * 5) 停止: quit()
 * 
 * 消息发送与处理器
 * 1) 该部分写在业务部分
 * 2) 创建与绑定线程: new Handler(Looper looper)
 *   a) 绑定的线程即[Looper]绑定的线程
 *   b) new Handler(), 绑定当前线程
 * 3) 消息发送: sendMessage(Message msg)
 *   a) 哪个线程想要发送消息, 该方法写在哪个线程中
 *   b) 发送消息会将Handler对象存储在Message.target中, 故会有内存泄漏问题
 * 4) 消息处理: 重写[handleMessage(Message msg)]
 *   a) 由[Handler]绑定的线程处理消息
 * 内存泄漏问题(Handler)
 * 1) [Handler]绑定线程, 线程绑定[Looper], [Looper]不停止
 * 2) 所以重写[Handler]时, 内部类请使用[static]修饰
 *   a) 非静态内部类含一个对外部类的隐式引用
 * 
 * 其他
 * 1) ThreadLocal<T> 本地线程存储(貌似是数组存储)
 * 
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-28 10:22:03
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class CustomThread extends Thread {

}
