2012年2月20日, 11:37:05

Java NIO非堵塞技术实际是采取Reactor模式，或者说是Observer模式为我们监察I/O端口，
如果有内容进来，会自动通知我们。

关于Java NIO的一些看法: http://www.iteye.com/topic/1111690 
有想法，不错的讨论，LZ大战阿里系

概念
** Channel
对应于Linux的文件描述符file descriptor。
有SocketChannel对应于socket对应的文件描述符。
使用channel对象的register方法将channel注册到某个selector监听中
注意，对于一个channel，一个selector只能映射一种事件集。

Channel的read方法和write方法用于读写数据。
注意：按照目前的测试结果http://stackoverflow.com/questions/158121/why-do-socketchannel-writes-always-complete-for-the-full-amount-even-on-non-bloc 
write方法好像要么全部写成功，要么写不成功而返回0! 一般返回0是因为缓冲区满了，此时应该注册OP_WRITE并返回到selector.select()

一般来说，一个channel对象对应于一台server或client。

** Selector
相当于一个观察者observer，由它负责监看注册给它的若干channel和对应的事件
（OP_ACCEPT、OP_READ、OP_WRITE、OP_CONNECT），
当这些channel有事件发生时，observer会通知我们，传回一组selectionKey，
里面就包含了发生的事件的信息，包括哪个channel发生了什么事件。

JDK6和Linux2.6会默认自动启用epoll模式，性能很大提升。

其中4个OP_是可以用“|”组成一个事件集的。
	SelectionKey
这个对象代表着一个<Channel, OP_事件集>对的所有信息。
有2种途径获得该对象：
1）	Channel对象的register(selector, 事件集)将channel添加到selectort的监控中，返回SelectionKey对象。（少用）
2）	在调用selector的阻塞方法select()返回后（表示有事件发生），此时调用selector.selectedKey()方法返回一个包含若干SelectionKey对象的集合。
通过SelectionKey对象，可以获得其对应的channel对象，调用其isAcceptable、isReadable、isWritable等方法，可以查看该SelectionKey对应的事件。
selectionKey对象还可以attach一个object，这个功能挺实用的。

** ByteBuffer
Channel读写时存放数据的对象，要求数据以字节流的形式存放。
问题：如果bytebuffer太小怎么办？
