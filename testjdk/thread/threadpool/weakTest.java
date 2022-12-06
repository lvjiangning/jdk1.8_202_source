package testjdk.thread.threadpool;

import testjdk.thread.User;

import java.lang.ref.WeakReference;

public class weakTest  {
   static User user=new User().setName("#3");
    public static void main(String[] args) {

        Student student=new Student("1122");
        UserWeak userWeak=new UserWeak(user,student);
        try {
            System.out.println("创建后开始休眠");
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("休眠结束");
    }

    static  class  UserWeak extends  WeakReference<User>{

        private Student student;

        public UserWeak(User referent,Student student) {
            super(referent);
            this.student=student;
        }
    }
}

