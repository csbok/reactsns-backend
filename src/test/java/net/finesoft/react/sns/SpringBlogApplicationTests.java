package net.finesoft.react.sns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import net.finesoft.react.sns.SpringBlogApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBlogApplication.class)
@WebAppConfiguration
public class SpringBlogApplicationTests {

	@Test
	public void contextLoads() {
	}

}
