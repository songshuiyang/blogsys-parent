package com.songsy.web.test;

/**
 * @author songshuiyang
 * @date 2018/7/11 21:15
 */
public class DaemonThreadTest extends Thread {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(getName()+ "" + i);
        }
    }

    public static void main(String[] args) {
        DaemonThreadTest daemonThreadTest = new DaemonThreadTest();
        daemonThreadTest.setDaemon(true);
        daemonThreadTest.start();
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i );
        }
        // 唯一的前台线程运行结束后，JVM会主动退出，因而后台线程也就被结束了
        
    }
}
