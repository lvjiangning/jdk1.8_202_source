package testjdk.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试reentrantLock
 * 1、同一个线程多次获取锁对变量state+1
 * 2、同一个线程多次释放锁对变量state-1
 * 3、变量state通过volatile修饰
 */
public class ReentrantLockTest_1 {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 1; i <= 3; i++) {
            lock.lock();
        }
        for(int i=1;i<=3;i++){
            try {
            } finally {
                lock.unlock();
            }
        }
    }
}
