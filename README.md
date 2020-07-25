```shell script
#框架描述
 1.该框架是解决分布式系统中的分布式事务问题,其原理是借鉴与阿里巴巴的seata思想以及spring事务的思想而改进的一套分布式事务框架,
   seata是日志存储于mysql中,而该框架是把日志数据存放在redis中,当日志数据量比较大的时候数据基于纯内存操作,有助于提升框架的性能,
   其中在在代码层面上设置了redis的key的过期时间,以防redis数据过多导致内存溢出或者导致频繁的 full gc
#使用说明
 1.详细请看mitbook-test工程
#设计原理
 1.目前不提供原理图,如果了解seata框架的话,本框架思想也是比较简单的,因为该框架是基于seata框架的
#支持数据库
 1.市面上的数据库都支持,只要是符合jdbc规范的数据库都支持
#技术选型
 1.springboot,aop,redis等
#测试地址
 1.http://localhost:8066/order/saveOrder?orderNo=12222&userId=1000&productId=1&productNum=1
#注意
 1.未经本人允许请勿用于商业
 2.该框架请求数超过100次会自动限流
```