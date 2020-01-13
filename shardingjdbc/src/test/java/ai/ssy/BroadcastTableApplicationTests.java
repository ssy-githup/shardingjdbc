package ai.ssy;

import ai.ssy.model.Province;
import ai.ssy.service.ProvinceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/***
* @FunctionName:
* @Description: 广播表的测试类
* @author: ssy
* @date:
*/
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
