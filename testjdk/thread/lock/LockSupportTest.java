package testjdk.thread.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest extends Thread {
    private Object object;

    public LockSupportTest(Object object) {
        this.object = object;
    }

    public void run() {
        System.out.println("before unpark");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 获取blocker
        System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));
        // 释放许可 main线程
        LockSupport.unpark((Thread) object);
        // 休眠500ms，保证先执行park中的setBlocker(t, null);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 再次获取blocker
        System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));

        System.out.println("after unpark");
    }



    public static void main(String[] args) {
        //将当前线程main传递进入run方法
        LockSupportTest myThread = new LockSupportTest(Thread.currentThread());
        myThread.start();
        System.out.println("main before park");
        // 获取许可,阻塞当前线程，main线程
        LockSupport.park("ParkAndUnparkDemo");
        System.out.println("after park");
    }
}