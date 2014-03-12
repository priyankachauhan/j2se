package com.pugwoo;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 2013-3-26 上午09:54:21
 * Callable可以返回对象、查询进程状态、取消某个进程
 * 而Runnable是不能做到这些的
 * Callable和Future配套使用，推荐两者的组合：FutureTask
 * 
 * 参考：http://blog.csdn.net/ghsau/article/details/7451464
 */
public class TestCallable2 {

	public static void main(String[] args) {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();  
        // 提交给线程池去执行
        Future<Integer> future = threadPool.submit(new Callable<Integer>() {  
            public Integer call() throws Exception {  
                return new Random().nextInt(100);  
            }  
        });
        
        // 异步等待，处理线程返回结果
        try {  
            Thread.sleep(1000);// 可能做一些事情  
            System.out.println(future.get());  // future.get()是阻塞的
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (ExecutionException e) {  
            e.printStackTrace();  
        }
        
        threadPool.shutdown();

	}

}
