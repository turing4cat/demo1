package com.itheima.tx;

import java.util.Random;

public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal th = new ThreadLocal();

        for (int i = 0; i < 4; i++) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            //生存随机串
                            String randomStr = new Random().nextInt() + "";
                            //存
                            System.out.println(Thread.currentThread().getName() + "存入:" + randomStr);
                            th.set(randomStr);

                            //取
                            System.out.println(Thread.currentThread().getName() + "取出:" + th.get());
                        }
                    }
            ).start();
        }
    }
}
