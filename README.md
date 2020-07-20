```shell script
#Framework description
1. The framework is to solve the problem of distributed transaction in distributed system. Its principle is to improve a set of distributed transaction framework based on Alibaba's Seata idea and spring transaction idea
   When the amount of log data is large, the data is based on pure memory operation, which helps to improve the performance of the framework
# Instructions for use
1.Please refer to the mitbook test project (based on order business) for details
#Design principle
1.At present, no schematic diagram is provided. If you understand the Seata framework, the idea of this framework is relatively simple, because the framework is based on the Seata framework
#Support database
1.All databases on the market are supported, as long as they are compliant with JDBC specification
#Technology selection
1.springboot,aop,redis,and so on
#Test address
One http://localhost :8066/order/saveOrder?orderNo=12222&amp;userId=1000&amp;productId=1&amp;productNum=1
```