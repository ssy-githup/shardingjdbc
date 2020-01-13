# shardingjdbc
shardingjdbc标准分片策略
## 1.准备工作
jdk1.8  idea 
在本地新建数据库 db0，db1；条件允许可以使用两个真实节点![在这里插入图片描述](https://img-blog.csdnimg.cn/20200110164652724.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTMwMDk0,size_16,color_FFFFFF,t_70)

### 1.2 测试使用在test包下测试类进行测试
测试 t_order,t_order_item根据user_id来分片
```powershell
# 分库配置
sharding.jdbc.config.sharding.default-database-strategy.inline.sharding-column=user_id
sharding.jdbc.config.sharding.default-database-strategy.inline.algorithm-expression=db$->{user_id % 2}
sharding.jdbc.config.sharding.binding-tables=t_order,t_order_item

# t_order分表配置
sharding.jdbc.config.sharding.tables.t_order.actual-data-nodes=db$->{0..1}.t_order$->{0..1}
# 分片键设置
sharding.jdbc.config.sharding.tables.t_order.table-strategy.inline.sharding-column=order_id
sharding.jdbc.config.sharding.tables.t_order.table-strategy.inline.algorithm-expression=t_order$->{order_id % 2}
```


```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingJdbcApp.class)
public class StandardShardingApplicationTests {
    @Autowired
    OrderService orderService;
    @Test
    public void contextLoads() {
        Order order = OrderGenerator.generate();
        OrderItem orderItem = ItemGenerator.generate();
        orderService.save(order, orderItem);
    }
}
```
### 1.3 广播表测试
```powershell
# 广播表
sharding.jdbc.config.sharding.broadcast-tables=t_province
```
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingJdbcApp.class)
public class BroadcastTableApplicationTests {
    @Autowired
    private ProvinceService provinceService;
    @Test
    public void test() {
        Province pro = new Province();
        pro.setId(222);
        pro.setName("北京");
        provinceService.save(pro);
    }
}
```
### 1.4结果展示
t_order,t_order_item会依据分片键进行分库插入
t_province广播表会在两个库都插入数据

***
## 2.  shardingSphere复合分片策略
### 2.1 准备工作
下载代码：https://github.com/ssy-githup/shardingjdbc/archive/v1.1.zip
新增 ComplexShardingApplicationTests复合分片测试类；application-sharding-complex.properties文件
sql文件中新增部分表；

### 2.2 测试使用在test包下测试类（ComplexShardingApplicationTests）进行测试
配置文件对应 config包下的 ComplexShardingAlgorithm 配置类
```powershell
# 分库配置
sharding.jdbc.config.sharding.default-database-strategy.inline.sharding-column=user_id
sharding.jdbc.config.sharding.default-database-strategy.inline.algorithm-expression=db$->{user_id % 2}
sharding.jdbc.config.sharding.binding-tables=t_order,t_order_item

# t_order分表配置   complex  复合分片
sharding.jdbc.config.sharding.tables.t_order.actual-data-nodes=db$->{0..1}.t_order$->{0..1}_$->{0..1}
sharding.jdbc.config.sharding.tables.t_order.table-strategy.complex.sharding-columns=user_id,order_id
sharding.jdbc.config.sharding.tables.t_order.table-strategy.complex.algorithm-class-name=ai.ssy.config.ComplexShardingAlgorithm

# t_order_item分表配置
sharding.jdbc.config.sharding.tables.t_order_item.actual-data-nodes=db$->{0..1}.t_order_item$->{0..1}_$->{0..1}
sharding.jdbc.config.sharding.tables.t_order_item.table-strategy.complex.sharding-columns=user_id,order_id
sharding.jdbc.config.sharding.tables.t_order_item.table-strategy.complex.algorithm-class-name=ai.ssy.config.ComplexShardingAlgorithm
```
### 2.3 结果展示

查看数据库

```sql
SELECT * from `db1`.t_order1_0;
SELECT* from `db1`.t_order_item1_0
```
