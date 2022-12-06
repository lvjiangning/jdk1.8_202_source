package testjdk.thread;

/**
 *  一个ThreadLocal对象，可以存放一个value，存放在当前Thread对象的threadLocals 属性中
 *  多个ThreadLocal对象，存放的value,都是存放在当前Thread对象的threadLocals 属性中
 *  参考地址 ：https://blog.csdn.net/weixin_43767015/article/details/106940089
 */
public class ThreadLocalTest {
    //姓名
  static   ThreadLocal<User> threadLocalName=new ThreadLocal<>();
    public static void main(String[] args) {
          new Thread(new ThreadBank()).start();
        try {
            System.out.println("主线程休眠");
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ThreadBank implements  Runnable{
        @Override
        public void run() {
            User user=new User();
            user.setAge("232sdadasdassdsadsadasda");
            user.setName("2322222222223223");
            System.out.println("创建了userd对象");
            threadLocalName.set(user);
            System.out.println(threadLocalName.get());
            try {
                System.out.println("开始第一次休眠");
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("第二次打印"+threadLocalName.get());
            System.out.println("线程结束");
        }
    }
}
