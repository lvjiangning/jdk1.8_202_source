package testjdk.thread.lock;

/**
 * 测试 wait_notify
 * 使用wait/notify实现同步时，必须先调用wait，后调用notify，如果先调用notify，再调用wait，将起不了作用。
 */
public class LockSupportTest_wait_notify {


    static class MyThread extends Thread {

        public void run() {
            synchronized (this) {
                System.out.println("before notify");
                notify();
                System.out.println("after notify");
            }
        }
    }


        public static void main(String[] args) throws InterruptedException {
            MyThread myThread = new MyThread();
            synchronized (myThread) {
                try {
                    myThread.start();
                    // 主线程睡眠3s
                    Thread.sleep(3000);
                    System.out.println("before wait");
                    // 阻塞主线程
                    myThread.wait(); //阻塞会释放锁
                    System.out.println("after wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

}
