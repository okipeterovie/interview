package com.interviewtest.line.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.interviewtest.line.dto.OutletsDto;
import com.interviewtest.line.dto.UserProfileDto;
import com.interviewtest.line.entity.Outlets;
import com.interviewtest.line.entity.UserProfile;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.managers.UserManagement;
import com.interviewtest.line.repository.OutletsRepository;
import com.interviewtest.line.services.OutletsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OutletsControllerTests {

    @MockBean
    OutletsService outletsService;

    @MockBean
    OutletsRepository outletsRepository;

    @MockBean
    UserManagement userManagement;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/outlets/find/outlets/1")
    void testGetOutletsById() throws Exception {

        Outlets outlets = new Outlets();

        UsersAccount user = new UsersAccount();

        user.setId(1l);
        user.setEmail("email");
        user.setPassword("password");

        outlets.setId(1l);
        outlets.setUsersAccount(user);
        outlets.setStreetAddress("street");
        outlets.setEmail("email");
        outlets.setOutletName("outlet name");
        outlets.setPhone("outlet phone");

//		doReturn(Optional.of(outlets)).when(outletsService.getOutletsRepository()).findById(1l);

        when(outletsRepository.findById(1l)).thenReturn(Optional.of(outlets));

        mockMvc.perform(get("/api/outlets/find/outlets/{id}", 1L)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.usersAccountId", is(1)))
                .andExpect(jsonPath("$.data.email", is("email")));
    }

    @Test
    @DisplayName("GET /api/outlets/find/outlets")
    void testGetAllOutlets() throws Exception {

        Outlets outlets = new Outlets();
        Outlets outlets1 = new Outlets();

        UsersAccount user = new UsersAccount();
        UsersAccount user1 = new UsersAccount();

        user.setId(1l);
        user.setEmail("email");
        user.setPassword("password");

        outlets.setId(1l);
        outlets.setUsersAccount(user);
        outlets.setStreetAddress("street");
        outlets.setEmail("email");
        outlets.setOutletName("outlet name");
        outlets.setPhone("outlet phone");

        user1.setId(2l);
        user1.setEmail("email2");
        user1.setPassword("password2");

        outlets1.setId(2l);
        outlets1.setUsersAccount(user1);
        outlets1.setStreetAddress("street2");
        outlets1.setEmail("email2");
        outlets1.setOutletName("outlet name2");
        outlets1.setPhone("outlet phone2");

//		doReturn(Optional.of(outlets)).when(outletsService.getOutletsRepository()).findById(1l);

        when(outletsRepository.findAllByDeletedOrderById(false)).thenReturn(Lists.newArrayList(outlets, outlets1));

        mockMvc.perform(get("/api/outlets/find/all")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].usersAccountId", is(1)))
                .andExpect(jsonPath("$.data[0].email", is("email")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].usersAccountId", is(2)))
                .andExpect(jsonPath("$.data[1].email", is("email2")));
    }

    @Test
    @DisplayName("POST /api/outlets/save/outlet")
    void testSaveOutlets() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        OutletsDto outlets = new OutletsDto();

        UsersAccount user = new UsersAccount();

        user.setId(1l);
        user.setEmail("email");
        user.setPassword("password");

        outlets.setId(1l);
        outlets.setStreetAddress("street");
        outlets.setEmail(user.getEmail());
        outlets.setOutletName("outlet name");
        outlets.setPhone("outlet phone");

        Outlets outlets1 = new Outlets();

        UsersAccount user1 = new UsersAccount();
        user1.setId(1l);
        user1.setEmail("email");
        user1.setPassword("password");

        outlets1.setId(1l);
        outlets1.setStreetAddress("street");
        outlets1.setEmail(user.getEmail());
        outlets1.setOutletName("outlet name");
        outlets1.setPhone("outlet phone");

        when(outletsService.persistOutlets(outlets)).thenReturn(outlets1);

        mockMvc.perform(post("/api/outlets/save/outlet")
                .content(objectMapper.writeValueAsString(outlets))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.usersAccountId", is(1)))
                .andExpect(jsonPath("$.data.email", is("email")));
    }

    @Test
    @DisplayName("POST /api/outlets/create-user-profile-for-outlet")
    void testCreateProfileAccountForOutlets() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        UserProfile userProfile = new UserProfile();

        UserProfileDto userProfileDto = new UserProfileDto();


        UsersAccount user = new UsersAccount();

        user.setId(1l);
        user.setEmail("email");
        user.setPassword("password");

        when(userManagement.findByUsername("email")).thenReturn(user);

        userProfile.setUsersAccount(user);
        userProfile.setId(1l);
        userProfile.setEmail("email");
        userProfile.setPhone("phone");
        userProfile.setPosition("position");
        userProfile.setFirstName("firstName");
        userProfile.setLastName("lastName");
        userProfile.setStreetAddress("streetAddress");

        userProfileDto.setId(1l);
        userProfileDto.setEmail("email");
        userProfileDto.setPhone("phone");
        userProfileDto.setPosition("position");
        userProfileDto.setFirstName("firstName");
        userProfileDto.setLastName("lastName");
        userProfileDto.setStreetAddress("streetAddress");

        when(outletsService.persistOutletsUserProfile(userProfileDto)).thenReturn(userProfile);

        mockMvc.perform(post("/api/outlets/create-user-profile-for-outlet")
                .content(objectMapper.writeValueAsString(userProfileDto))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.email", is("email")));
    }

}
