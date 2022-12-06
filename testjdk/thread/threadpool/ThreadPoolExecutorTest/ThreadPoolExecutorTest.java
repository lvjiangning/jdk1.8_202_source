package testjdk.thread.threadpool.ThreadPoolExecutorTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pool=new ThreadPoolExecutor(1, 2,
                500, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(4),new MyThreadFactory("ThreadPoolExecutorTest"),new MyRejectedExecutionHandler());
        //保存任务执行结果
        List<Future> futures=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int j=i;
            System.out.println("创建第"+(i+1)+"个线程");
            Future<?> submit = pool.submit(new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前执行任务的工作线程===" + Thread.currentThread().getName() + "；当前执行的任务数" + j);
            }),"第"+(j+1)+"个线程执行完成");

            futures.add(submit);
        }
        //阻塞获取结果
        for (int i = 0; i < futures.size(); i++) {
            Future future = futures.get(i);
            if (future.isDone()) { // 如果Future没有进入线程池执行，则不获取结果，否则线程一直是new状态。阻塞在这
                System.out.println(future.get());
            }
        }
        try {
            //关闭线程，不再接收新任务，只执行队列中的任务
            System.out.println("开始关闭线程池");
            pool.shutdown();
            do {
                pool.awaitTermination(5000,TimeUnit.MILLISECONDS);
                System.out.println("线程池是否关闭退出:"+pool.isTerminated());
            }while (!pool.isTerminated());
            Thread.sleep(5000);
            System.out.println("主线程退出");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
