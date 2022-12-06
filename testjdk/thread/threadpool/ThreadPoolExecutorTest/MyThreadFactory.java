package testjdk.thread.threadpool.ThreadPoolExecutorTest;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 */
public class MyThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    MyThreadFactory(String namePrefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        this.namePrefix =namePrefix+ "pool-" +
                poolNumber.getAndIncrement() +
                "-thread";
    }

    public Thread newThread(Runnable r) {
        int andIncrement = threadNumber.getAndIncrement();
        Thread t = new Thread(group, r,
                namePrefix + andIncrement,
                0);
        System.out.println("创建工作线程数量"+ andIncrement);
        if (t.isDaemon())
            t.setDaemon(false);//非守护线程，jvm结束，不会进行关闭
        if (t.getPriority() != Thread.NORM_PRIORITY) //线程优先级
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
