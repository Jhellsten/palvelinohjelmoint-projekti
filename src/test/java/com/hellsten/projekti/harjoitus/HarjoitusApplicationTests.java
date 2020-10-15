package com.hellsten.projekti.harjoitus;

import com.hellsten.projekti.harjoitus.web.AuthenticationController;
import com.hellsten.projekti.harjoitus.web.ItemsController;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HarjoitusApplicationTests {

	@Autowired
	private ItemsController items;

	@Autowired
	private AuthenticationController auth;



	@Test
	public void itemControllerContexLoads() throws Exception {
		assertThat(items).isNotNull();
	}

	@Test
	public void authControllerContexLoads() throws Exception {
		assertThat(auth).isNotNull();
	}

}
