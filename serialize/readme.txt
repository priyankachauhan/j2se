【概念】
序列化：将Object转换成字节序
反序列化：将字节序转换成Object
字节序：可以用于网络传输的二进制字节

【自动序列化】
实现Serializable接口
必须保证private static final long serialVersionUID相等
缺点：序列化之后长度太长，因为包含了类名等信息
原因：只要用了writeObject这个方法，都会带上类名

【手动序列化】
实现Externalizable接口
