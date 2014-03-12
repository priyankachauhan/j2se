2010年11月24日 下午10:03:57

线程状态：
1) new ：当Thread对象被创建但还没有执行start()时的状态
2) ready： Thread对象准备就绪，只有一获得CPU，就可执行
3) run: 运行状态，Thread对象获得CPU
4) wait: 等待/阻塞/睡眠状态，线程仍是alive，但不是ready状态
5) dead: 线程死亡，当run()代码执行完成或没有捕获到的异常事件终止了run方法的执行，从而导致线程突然死亡

创建线程的两种方法：
1) 实现Runnable接口，重写run方法。然后创建该类实例obj,用new Thread(obj)创建Thread实例，调用该实例start()方法。
2) 继承Thread，重写run方法。创建该类实例，调用该实例start()方法。

实现Runnable接口的好处：多个线程可共用一个runnable对象，这样不用static属性就可实现共享变量。
当然，如果想让一个线程对应于一个obj，那继承Thread是最好的选择。

常用方法：
1) Thread.currentThread() 获得当前运行的线程对象
2) Thread对象.getName(); Thread对象.setName() 动态方法，获取或设置Thread对象名称
3) Thread对象.start(); 激活线程
4) Thread对象.isAlive(); 判断线程是否存活
5) Thread.sleep(ms); 当前执行这条语句的线程睡眠ms毫秒；唤醒睡眠中的线程：thread对象.interrupt()方法
6) 每个线程都有唯一的Id，thread对象getId()方法
7) Thread对象的setPriority方法，一共有三种优先级MAX、NORM、MIN
8) Thread对象getState()方法，Thread.State枚举状态：NEW  RUNNABLE  BLOCKED  WAITING  TIMED_WAITING  TERMINATED
9) 线程让步,Thread.yield()主动让出CPU，进程从running状态转为runnable状态，不是阻塞。注意：yield出去的进程还是可能在下一次被选中的。
10) thread对象的join方法，使当前进程暂停“等待”thread对象执行完之后再执行。

11) 所有obj对象都有wait()方法，调用该方法的线程将阻塞在该obj上，直到该obj在【其他】进程中被调用了notify()或notifyAll()方法
    调用该obj的wait()和notify()、notifyAll()方法时，必须放在该obj的synchronized(obj){}中

关于关键字volatile
当一个变量声明为volatile类型，则说明该变量随时可能被其它线程更改，要求程序每次读取时都“刷新”

同步：synchronized
Java中每个对象都有一个内置锁。
可以同步对象、方法、代码块
线程睡眠时，它所持的任何锁都不会释放。
方法同步：public synchronized void test() {}
同步代码段： synchronized(this) { … }
如果要同步静态方法：synchronized(Xxx.class){ … }
synchronized不能用于属性的声明
如果要同步对象，这样写：
synchronized(obj) {
  // 同步的代码段
}

ExecutorService提供线程池的服务
