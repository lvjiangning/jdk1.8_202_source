package testjdk;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
//https://blog.csdn.net/weixin_43767015/article/details/105211090
public class ReferenceTest {
    /**
     * 1Mb内存
     */
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws InterruptedException {
        /*测试强引用
        * 强引用就是指在程序代码之中普遍存在的，指创建一个对象并把这个对象赋给一个引用变量，并没有像其它三种引用一样有一个就具体的类来描述。
        * 对于强引用对象，即使内存不足，JVM宁愿抛出OutOfMemoryError (OOM)错误也不会回收这种对象。
        * */
//        testStrongReference();
        /*测试软引用
        * 软引用是用来描述一些还有用但并非必需的对象。只使用SoftReference类修饰的对象就是一个软引用对象(软可到达对象)，
        * 如果一个对象只具有软引用，内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。
        * */
//        testSoftReference();
        /*测试弱引用
        * 弱引用也是用来描述非必需对象的。只使用WeakReference类修饰的对象就是一个弱引用对象(弱可达对象)。
        * 弱引用的对象相比软引用拥有更短暂的生命周期。无论内存是否足够，一旦下次垃圾回收器运行后扫描到弱引用，便会回收。
        * */
//        testWeakReference();
        /*测试虚引用
        * 它是最弱的一种引用关系，虚引用与软引用和弱引用的一个区别在于：虚引用必须和引用队列 （ReferenceQueue）联合使用。
        * 无法通过get获取该引用,但其实虚引用其实是持有对象引用的，只是PhantomReference的get方法的实现永远返回null。
        * */
//        testPhantomReference();
    }

    /**
     * 测试强引用
     *
     * @throws InterruptedException
     */
    public static void testStrongReference() throws InterruptedException {
        byte[] StrongReference1, StrongReference2, StrongReference3, StrongReference4;
        StrongReference1 = new byte[1 * _1MB];
        StrongReference2 = new byte[1 * _1MB];
        //到这里由于内存不足,所以虚拟机会自动尝试一次自动GC,但是由于是强引用,无法清除对象,造成OutOfMemoryError异常
        StrongReference3 = new byte[1 * _1MB];
    }

    /**
     * 测试软引用
     *
     * @throws InterruptedException
     */
    public static void testSoftReference() throws InterruptedException {
        ReferenceQueue<byte[]> objectReferenceQueue = new ReferenceQueue<>();
        SoftReference softReference1, softReference2, softReference3, softReference4;
        softReference1 = new SoftReference(new byte[1 * _1MB], objectReferenceQueue);
        softReference2 = new SoftReference(new byte[1 * _1MB], objectReferenceQueue);
        //到这里由于内存不足,虚拟机会自动尝试一次自动GC
        softReference3 = new SoftReference(new byte[1 * _1MB], objectReferenceQueue);
        //执行到这里实际上又GC了一次
        softReference4 = new SoftReference(new byte[1 * _1MB], objectReferenceQueue);
        System.out.println("第一次GC之后的值");
        System.out.println(softReference1.get());
        System.out.println(softReference2.get());
        System.out.println(softReference3.get());
        System.out.println(softReference4.get());
        System.out.println("===========>");
        System.out.println(objectReferenceQueue.poll());
        System.out.println(objectReferenceQueue.poll());
        System.out.println(objectReferenceQueue.poll());
        System.out.println(objectReferenceQueue.poll());
        //到这里,尝试手动使虚拟机GC一次,对于软引用,如果内存足够,GC是并不会回收对象的
        System.gc();
        Thread.sleep(500);
        System.out.println("第二次GC之后的值");
        System.out.println(softReference4.get());
        System.out.println("===========>");
        System.out.println(objectReferenceQueue.poll());

    }

    /**
     * 测试弱引用
     *
     * @throws InterruptedException
     */
    public static void testWeakReference() throws InterruptedException {
        ReferenceQueue<byte[]> objectReferenceQueue = new ReferenceQueue<>();
        WeakReference weakReference1, weakReference2, weakReference3, weakReference4;
        weakReference1 = new WeakReference(new byte[1 * _1MB], objectReferenceQueue);
        weakReference2 = new WeakReference(new byte[1 * _1MB], objectReferenceQueue);
        //到这里由于内存不足,虚拟机会自动尝试一次自动GC
        weakReference3 = new WeakReference(new byte[1 * _1MB], objectReferenceQueue);
        //执行到这里实际上又GC了一次
        weakReference4 = new WeakReference(new byte[1 * _1MB], objectReferenceQueue);
        System.out.println("第一次GC之后的值");
        System.out.println(weakReference1.get());
        System.out.println(weakReference2.get());
        System.out.println(weakReference3.get());
        System.out.println(weakReference4.get());
        System.out.println("===========>");
        System.out.println(objectReferenceQueue.poll());
        System.out.println(objectReferenceQueue.poll());
        System.out.println(objectReferenceQueue.poll());
        System.out.println(objectReferenceQueue.poll());
        //到这里,尝试手动使虚拟机GC一次,,对于弱引用,即使内存足够,GC还是会回收对象的
        System.gc();
        Thread.sleep(500);
        System.out.println("第二次GC之后的值");
        System.out.println(weakReference4.get());
        System.out.println("===========>");
        System.out.println(objectReferenceQueue.poll());
    }

    /**
     * 测试虚引用
     *
     * @throws InterruptedException
     */
    public static void testPhantomReference() throws InterruptedException {
        ReferenceQueue<byte[]> objectReferenceQueue = new ReferenceQueue<>();

        PhantomReference phantomReference1, phantomReference2, phantomReference3;
        phantomReference1 = new PhantomReference(new byte[1 * _1MB], objectReferenceQueue);
        phantomReference2 = new PhantomReference(new byte[1 * _1MB], objectReferenceQueue);
        System.gc();
        Thread.sleep(500);
        System.out.println(objectReferenceQueue.poll());
        System.out.println(objectReferenceQueue.poll());
        /*按照我们的思维,这是比软引用和弱引用还弱的引用,调用一次GC,虚引用关联的匿名对象会被GC掉,这看起来确实没错,并且已经被加入到了ReferenceQueue中(可以poll得到数据),但实际上在初始化下一个phantomReference3时还是会会抛出OOM异常,就像强引用一样
        问题 就在于这个虚引用.在GC启动时,会将虚引用对象传到它的引用队列中去,这没错.但是却不会将虚引用的referent字段设置成null,
        这样一来,也就不会释放虚引用指向的匿名数组的堆内存空间,看起来这个匿名数组被回收了,但实际上phantomReference的内部的referent已经持有了这个数组,造成了内存泄漏
        使用时一定要注意这个问题*/

        /*我们可以采用下面的操作,手动将PhantomReference的referent置为null,然后再次GC时,这样就会真正的清理内存空间*/

        /*phantomReference1.clear();
        phantomReference2.clear();*/

        phantomReference3 = new PhantomReference(new byte[1 * _1MB], objectReferenceQueue);
    }
}

