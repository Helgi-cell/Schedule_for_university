package com.epam.brest.kafkaweb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = {})
//@Transactional()
@DirtiesContext
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
class KafkaWebApplicationTests {


	@Test
	void contextLoads() {
	}

}
