package com.interviewtest.line;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.interviewtest.line.controller.OutletsController;
import com.interviewtest.line.controller.WalletMasterController;
import com.interviewtest.line.dto.UserDto;
import com.interviewtest.line.entity.Outlets;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.enumeration.UserType;
import com.interviewtest.line.managers.UserManagement;
import com.interviewtest.line.remita.RemitaController;
import com.interviewtest.line.remita.RemitaService;
import com.interviewtest.line.repository.OutletsRepository;
import com.interviewtest.line.repository.UserRepository;
import com.interviewtest.line.services.OutletsService;
import com.interviewtest.line.services.WalletMasterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;


import java.util.logging.Level;
import java.util.logging.Logger;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	private static final Logger logger = Logger.getLogger(ApplicationTests.class.getName());

//	@Autowired
//	WebApplicationContext webApplicationContext;


	@MockBean
	OutletsService outletsService;

	@Autowired
	private MockMvc mockMvc;


	@Test
	void contextLoads() {
	}

//	mockMvc = MockMvcBuilders.standaloneSetup(new Controller()).build();


	@Test
	@DisplayName("GET /api/outlets/find/all success")
	void testGetAllOutletsSuccess() throws Exception {

		// Execute the GET request
		mockMvc.perform(get("/api/outlets/find/all"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /api/outlets/find/outlets/1")
	void testGetOutletsById() throws Exception {
		mockMvc.perform(get("/api/outlets/find/outlets/{id}", 1L)
				.contentType("application/json"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /api/outlets/save/outlet")
	void testSaveOutlets() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		Outlets outlets = new Outlets();

		UsersAccount user = new UsersAccount();

		user.setId(1l);
		user.setEmail("email");
		user.setPassword("password");

		outlets.setId(1l);
		outlets.setUsersAccount(user);
		outlets.setStreetAddress("street");
		outlets.setEmail(user.getEmail());
		outlets.setOutletName("outlet name");
		outlets.setPhone("outlet phone");

		mockMvc.perform(post("/api/outlets/save/outlet")
				.content(objectMapper.writeValueAsString(outlets))
				.contentType("application/json"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /api/outlets/save/outlet")
	void testCreateProfileForOutlets() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		Outlets outlets = new Outlets();

		UsersAccount user = new UsersAccount();

		user.setId(1l);
		user.setEmail("email");
		user.setPassword("password");

		outlets.setId(1l);
		outlets.setUsersAccount(user);
		outlets.setStreetAddress("street");
		outlets.setEmail(user.getEmail());
		outlets.setOutletName("outlet name");
		outlets.setPhone("outlet phone");

		mockMvc.perform(post("/api/outlets/save/outlet")
				.content(objectMapper.writeValueAsString(outlets))
				.contentType("application/json"))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
