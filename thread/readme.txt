2010年11月24日 下午10:03:57

线程状态：
1) new ：当Thread对象被创建但还没有执行start()时的状态
2) ready： Thread对象准备就绪，只有一获得CPU，就可执行
3) run: 运行状态，Thread对象获得CPU
4) wait: 等待/阻塞/睡眠状态，线程仍是alive，但不是ready状态
5) dead: 线程死亡，当run()代码执行完成或被其它线程中断

创建线程的两种方法：
1) 实现Runnable接口，重写run方法。然后创建该类实例obj,用new Thread(obj)创建Thread实例，调用该实例start()方法。
2) 继承Thread，重写run方法。创建该类实例，调用该实例start()方法。

常用方法：
1) Thread.currentThread() 获得当前运行的线程对象
2) Thread对象.getName(); Thread对象.setName() 动态方法，获取或设置Thread对象名称
3) Thread对象.start(); 激活线程
4) Thread对象.isAlive(); 判断线程是否存活
5) Thread.sleep(ms); 当前执行这条语句的线程睡眠ms毫秒

关于关键字volatile
当一个变量声明为volative类型，则说明该变量随时可能被其它线程更改，要求程序每次读取时都“刷新”