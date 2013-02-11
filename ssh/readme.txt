Orion SSH2（2010更新）
http://sourceforge.net/apps/mediawiki/orion-ssh2/index.php
Ganymed SSH2（2011更新，【推荐】）
ganymed-ssh-2.googlecode.com/
其实这两个的代码和接口是一样的，还有一个Trilead-SSH2也是一样的。

jsch 2013年1月27日 12:55:43
目前shell swing部分没有特色，功能看起来很多，使用端口转发，X11转发，文件传输等等
oschina上收藏的人数也不少

SSH2终端仿真器 JCTerm
这是一个不错的java ssh客户端，直接运行：
http://wiredx.net/jcterm/
【jsch和JCTerm是同一个作者http://www.jcraft.com/】

关于ganymed和jsch的设计比较：
1) 连接速度上，只连接上执行ls，ganymed需要438ms，而jsch需要750ms，差距较大
2) jsch的支持会比较好，特别是有expect4j
3) ganymed的Connection概念对应于jsch的Session，Session概念对应于Channel

最后选择jsch

关于expect4j的概念：
expect本身就是一个可用的库，支持socket或input/output stream的初始化
然后调用send发送信息，调用expect等待接收信息