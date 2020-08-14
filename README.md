# *Framework description*
 >+ The framework is to solve the problem of distributed transactions in distributed systems. 
    The framework ensures the strong consistency of distributed transactions. The transaction 
    log data is stored in redis,In order to ensure the high availability of redis, we can deploy 
    redis as a redis cluster mode and develop a hybrid persistence mechanism. We can deploy redis 
    as a log When the amount of data is large, the data is based on pure memory operation, which 
    helps to improve the performance of the framework. In the code level, the expiration of redis 
    key is set time to prevent memory overflow or frequent full GC caused by too much redis data.
    If the data is stored in the redis process, if the storage fails, the Trigger the retrying logic 
    to ensure the success of data synchronization, but this also introduces a new problem. If the 
    data is retried all the time, the framework will always be in the retrying logic will affect 
    the performance of the system, so the logic in this aspect will be optimized later
   
# *Use details*
 >+ See the mitbook-test project for details
 
# Design principle
 >+ At present, we don't provide the explanation of the principle. We can read the source code to 
    understand the principle. The design documents will be further improved
 
# *Support DB*
 >+ All the databases on the market are supported, as long as they are compliant with the JDBC specification
 
# *Technology selection*
 >+ springboot,aop,redis and so on
 
# Test address
 >+ *<http://localhost:8066/order/saveOrder?orderNo=12222&userId=1000&productId=1&productNum=1>*
 
# *Be careful*
 >+ Do not use it for business without my permission
 >+ At present, the performance of this framework is not very good. In the future, RPC or netty will be used to 
    solve the performance problems
 >+ There is an order deduction problem in the mitbook-test project,so don't worry about it. This is not the distributed 
    framework problem, but the order deduction inventory problem, so it is not handled
 >+ If you don't understand the English of log printing, please read it
    :*https://github.com/mitbook/mitbook-mountain/commit/6e136e7f3b1313b68c94761cad16c05e68212e3d*
 
# *Integrating the use of third-party frameworks*
 1. Introducing Maven dependency
 ``` java
 <dependency>
    <groupId>com.mitbook</groupId>
    <artifactId>mitbook-stater</artifactId>
    <version>1.0</version>
  </dependency>
  ```
  2. Add @EnableGlobalTransactional annotation to the startup class to solve the distributed transaction problem
  
# *Code contribution description*
 >+ If source enthusiasts have better ideas, welcome to disturb, I sincerely thank you for your contribution
 >+ If you find a bug, please contact me in time. I will contact you at the first time to help you solve the problem
 >+ This framework is only used for learning, not for business. If you need to apply business, please contact me. Thank you
 
# *Proposal*
 >+ It is recommended to use redis cluster architecture mode to ensure high availability of redis 
   (mixed persistence of redis can be enabled to ensure high availability of redis)

# *Quick start*
 ![1](/doc/images/1.png)
 ![2](/doc/images/2.png)
 ![3](/doc/images/3.png)
 ![4](/doc/images/4.png)
 ![5](/doc/images/5.png)
 ![6](/doc/images/6.png)
 ![6](/doc/images/7.png)
 ![8](/doc/images/8.png)
 ![9](/doc/images/9.png)
 ![10](/doc/images/10.png)
 ![11](/doc/images/11.png)
 ![12](/doc/images/12.png)
 ![13](/doc/images/13.png)
 ![14](/doc/images/14.png)
 
 
 
 
 
 
 
