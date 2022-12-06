package testjdk.thread;

/**
 * https://blog.csdn.net/weixin_43767015/article/details/106943401
 */
public class InheritableThreadLocalTest {
    static InheritableThreadLocal<InheritableThreadLocalTest> threadLocal = new InheritableThreadLocal<>();

    public static void set() {
        threadLocal.set(new InheritableThreadLocalTest());
    }

    public static InheritableThreadLocalTest get() {
        //InheritableThreadLocal 集成了ThreadLocal，并重写的get方法
        return threadLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程中尝试获取值====>" + get());
        System.out.println("主线程中设置值====>");
        set();
        //主线程中尝试获取值
        System.out.println("主线程中再次尝试获取值====>" + get());
        System.out.println();

        System.out.println("开启一条子线程====>");
        //线程其实是有group的概念，对应的类是ThreadGroup, 如果创建线程不指定Group，则默认在main组
        //创建线程中的构造方法，有个参数inheritThreadLocals，默认为True，会将父线程中ThreadLocalMap中的值，负责一份引用到子线程
        Thread thread = new Thread(new Th1(), "child");
        thread.start();
        //主线程等待子线程执行完毕
        thread.join();
        System.out.println();

        System.out.println("等待子线程执行完毕，主线程中再次尝试获取值====>" + get());
        System.out.println("获取到的还是父线程原来的值，即父可以传递数据给子，子不能传递数据给父");
    }

    static class Th1 implements Runnable {
        @Override
        public void run() {
            System.out.println("子线程中尝试获取值====>" + get());
            System.out.println("能够直接获取到，说明父线程的数据传递给了子线程，并且获取到的对象和父类获取到的对象还是同一个，即浅拷贝");
            System.out.println("子线程中设置值====>");
            set();
            System.out.println("子线程中再次尝试获取值====>" + get());
            System.out.println("此时获取到的，是自己设置的值");
            System.out.println();

        }
    }

    @Override
    public String toString() {
        return this.hashCode() + "";
    }

}
