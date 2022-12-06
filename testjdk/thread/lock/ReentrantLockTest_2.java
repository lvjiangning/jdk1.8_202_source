package testjdk.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、当前获取锁的线程是不会在队列中的，队列中都是排队的线程
 *
 */
public class ReentrantLockTest_2 {
    static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {

        for(int i=0;i<5;i++){
            new Thread(new ThreadDemo(i)).start();
        }

    }

    static class ThreadDemo implements Runnable {
        Integer id;

        public ThreadDemo(Integer id) {
            this.id = id;
        }

        @Override

        public void run() {
//            try {
//                TimeUnit.MILLISECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            for(int i=0;i<2;i++){
                lock.lock();
                System.out.println("获得锁的线程："+id);
                lock.unlock();
            }
        }
    }
}
