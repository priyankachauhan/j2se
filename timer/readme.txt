http://blog.csdn.net/ahxu/article/details/249610

默认情况下，只要一个程序的timer线程在运行，那么这个程序就会保持运行。当然，你可以通过以下四种方法终止一个timer线程：

1.调用timer的cancle方法。你可以从程序的任何地方调用此方法，甚至在一个timer task的run方法里。
2.让timer线程成为一个daemon线程（可以在创建timer时使用new Timer(true)达到这个目地），这样当程序只有daemon线程的时候，它就会自动终止运行。
3.当timer相关的所有task执行完毕以后，删除所有此timer对象的引用（置成null），这样timer线程也会终止。
4.调用System.exit方法，使整个程序（所有线程）终止。

每一个Timer仅对应唯一一个线程。Timer类的线程安全的。