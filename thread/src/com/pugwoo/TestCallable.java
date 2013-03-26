package com.pugwoo;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 2013-3-26 上午09:54:21
 * Callable可以返回对象、查询进程状态、取消某个进程
 * 而Runnable是不能做到这些的
 * Callable和Future配套使用，推荐两者的组合：FutureTask
 * 
 * 参考：http://blog.csdn.net/ghsau/article/details/7451464
 */
public class TestCallable {

	public static void main(String[] args) {

		Callable<Integer> callable = new Callable<Integer>() {  
            public Integer call() throws Exception {  
                return new Random().nextInt(100);  
            }  
        };  
        
        // FutureTask实现了两个接口，Runnable和Future，所以它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值
        FutureTask<Integer> future = new FutureTask<Integer>(callable);  
        new Thread(future).start();  
        try {  
            Thread.sleep(1000);// 可能做一些事情  
            System.out.println(future.get());  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (ExecutionException e) {  
            e.printStackTrace();  
        }  
	}

}
