http://damies.iteye.com/blog/51778

2012年11月6日 16:19:32

关于性能：
RMI在windows本机上测试，空跑单线程每秒钟可达6000+次/秒，网络性能符合要求。

使用了spring的rmi（可以不继承remote），还有异步的例子：http://buralin.iteye.com/blog/1389172
命令行运行方式：
E:\j2se_workspace\CypherService\target\classes>java -cp .;..\..\lib\spring-2.5.6.jar;..\..\lib\commons-logging-1.1.1.jar edu.gmit.rmi.Server

规则
1. 远程接口一定要extends java.rmi.Remote接口
2. 远程接口的实现一定要继承UnicastRemoteObject以实现序列化
      实际上，参数和返回值也是要实现java.io.Serializable以实现序列化
      实际上，UnicastRemoteObject实现了java.io.Serializable
   
【重点】
rmi的神奇之处在于：调用远程的service的返回值会随着服务器端的改变而改变！(由此可以实现【异步】)

这就意味着，client的remote对象，就等同于远程服务器的对象，操作本机的remote对象和操作服务器上的该对象完全一致。
只有当客户端发起远程调用时才真正构造远程对象。 

有两种方式可以获得remote对象：1）通过Naming.lookup获得；2）通过调用remote对象的返回值获得另一个remote对象

 垃圾回收 
   RMI采用其分布式垃圾回收功能收集不再被网络中任何客户程序所引用的远程服务对象。
      当前的分布式垃圾回收采用的是引用计数的方式（类似于Modula-3's Network Objects），
      因此无法检测到循环引用的情况，这要求程序员打破循环引用，以便无用的对象可以被回收。
   RMI运行时通过WeakReference引用远程对象，当某个远程对象不再被任何客户端引用时，JVM会对其进行垃圾回收。