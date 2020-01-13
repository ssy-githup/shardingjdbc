# shardingSphere
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

## 3. Hint路由分片
下载地址：https://github.com/ssy-githup/shardingjdbc/archive/v1.2.zip
新增 **HintApplicationTests** hint分片测试类；**application-sharding-hint.properties**文件和sql文件中新增部分表；

### 3.2 测试使用在test包下测试类（HintApplicationTests）进行测试
配置文件中对应的配置类： config包下的 HintShardingKeyAlgorithm

```csharp
# 分库配置
sharding.jdbc.config.sharding.default-database-strategy.inline.sharding-column=user_id
sharding.jdbc.config.sharding.default-database-strategy.inline.algorithm-expression=db$->{user_id % 2}

# t_order强制分片配置
sharding.jdbc.config.sharding.tables.t_order.actual-data-nodes=db$->{0..1}.t_order$->{0..1}
#映射到  sharedingSpaher的配置类
sharding.jdbc.config.sharding.tables.t_order.database-strategy.hint.algorithm-class-name=ai.ssy.config.HintShardingKeyAlgorithm
sharding.jdbc.config.sharding.tables.t_order.table-strategy.hint.algorithm-class-name=ai.ssy.config.HintShardingKeyAlgorithm

sharding.jdbc.config.props.sql.show=true
```
2.3 结果展示
对应的日志信息：

```csharp
2020-01-13 11:25:20.976  INFO 11056 --- [           main] ShardingSphere-SQL                       : Rule Type: sharding
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT * FROM t_order;
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : SQLStatement: SelectStatement(super=DQLStatement(super=io.shardingsphere.core.parsing.parser.sql.dql.select.SelectStatement@49433c98), containStar=true, firstSelectItemStartPosition=7, selectListLastPosition=9, groupByLastPosition=0, items=[StarSelectItem(owner=Optional.absent())], groupByItems=[], orderByItems=[], limit=null, subQueryStatement=null, subQueryStatements=[], subQueryConditions=[])
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : Actual SQL: db0 ::: SELECT * FROM t_order0_0;
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : Actual SQL: db0 ::: SELECT * FROM t_order0_1;
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : Actual SQL: db0 ::: SELECT * FROM t_order1_0;
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : Actual SQL: db0 ::: SELECT * FROM t_order1_1;
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : Actual SQL: db1 ::: SELECT * FROM t_order0_0;
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : Actual SQL: db1 ::: SELECT * FROM t_order0_1;
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : Actual SQL: db1 ::: SELECT * FROM t_order1_0;
2020-01-13 11:25:20.977  INFO 11056 --- [           main] ShardingSphere-SQL                       : Actual SQL: db1 ::: SELECT * FROM t_order1_1;
[Order{id=1, userId=0, orderId=0}, Order{id=2, userId=0, orderId=0}]
```

## 4. shardingSpahere读写分离
下载地址：https://github.com/ssy-githup/shardingjdbc/archive/v1.3.zip
新增 **MaterSlaveApplicationTests** 读写分离测试类；**application-sharding-hint.properties**文件和sql文件中新增部分表；

### 4.1 测试使用在test包下测试类（HintApplicationTests）进行测试

```csharp
# 分库配置
# 读写分离配置
sharding.jdbc.config.masterslave.name=dataSource
sharding.jdbc.config.masterslave.load-balance-algorithm-type=round_robin
#配置master的数据源名称
sharding.jdbc.config.masterslave.master-data-source-name=master
#配置slave的数据源名称
sharding.jdbc.config.masterslave.slave-data-source-names=slave

# 打印SQL
sharding.jdbc.config.props.sql.show=true
```
### 4.2 结果展示
对应的日志信息：

```csharp
2020-01-13 14:05:49.563  INFO 12748 --- [           main] ShardingSphere-SQL                       : Rule Type: master-slave
2020-01-13 14:05:49.563  INFO 12748 --- [           main] ShardingSphere-SQL                       : SQL: INSERT INTO t_order(id,user_id, order_id) VALUES(?,?, ?) ::: DataSources: master
请求参数为=200
2020-01-13 14:05:49.604  INFO 12748 --- [           main] ShardingSphere-SQL                       : Rule Type: master-slave
2020-01-13 14:05:49.604  INFO 12748 --- [           main] ShardingSphere-SQL                       : SQL: select * from t_order where id =? ::: DataSources: slave
```
读写分离需要用到mysql数据库的读写分离；测试的时候手动修改从库的数据，可以看到读写使用的是不同数据库
