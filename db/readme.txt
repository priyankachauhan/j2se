2011-11-13
DBUtils是开源中国社区推荐的类ORM数据库操作工具
http://commons.apache.org/dbutils/

概念：
ResultSetHandler 负责将ResultSet转变成各种形式的对象
QueryRunner是每次都会调用到的实例，【线程安全】【根据oschina，可以只有一个实例】

apache DBCP和Pool
数据库连接池，DBCP依赖于Pool，Tomcat的数据源也是使用DBCP

注：如果使用Spring+DBUtils，则可以用Spring的DataSource

如果使用Oracle，则QueryRunner初始化用参数true：new QueryRunner(true);