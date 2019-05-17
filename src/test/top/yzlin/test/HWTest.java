package top.yzlin.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yzlin.homework.action.LoginAction;
import top.yzlin.homework.config.MainConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={MainConfig.class})
public class HWTest {
    @Autowired
    private LoginAction loginAction;

    @Test
    public void hibernateTest() throws NoSuchMethodException {
        System.out.println(loginAction);
    }
}
