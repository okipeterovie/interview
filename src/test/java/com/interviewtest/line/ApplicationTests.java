package com.interviewtest.line;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.logging.Logger;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	private static final Logger logger = Logger.getLogger(ApplicationTests.class.getName());

	@Test
	void contextLoads() {

	}

}
