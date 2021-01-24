package com.interviewtest.line.controllers;

import com.google.common.collect.Lists;
import com.interviewtest.line.entity.Outlets;
import com.interviewtest.line.entity.UserProfile;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.entity.WalletMaster;
import com.interviewtest.line.managers.UserManagement;
import com.interviewtest.line.repository.OutletsRepository;
import com.interviewtest.line.repository.WalletMasterRepository;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WalletMasterControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    WalletMasterRepository walletMasterRepository;

    @Test
    @DisplayName("GET /api/wallet-master/find/all")
    void testGetAllOutlets() throws Exception {

        WalletMaster walletMaster = new WalletMaster();
        UserProfile userProfile = new UserProfile();
        UsersAccount usersAccount = new UsersAccount();
        Outlets outlets = new Outlets();

        WalletMaster walletMaster1 = new WalletMaster();
        UserProfile userProfile1 = new UserProfile();
        UsersAccount usersAccount1 = new UsersAccount();
        Outlets outlets1 = new Outlets();

        usersAccount.setId(1l);
        usersAccount.setEmail("email1");

        userProfile.setId(1l);
        userProfile.setEmail("email1");
        userProfile.setFirstName("firstName1");
        userProfile.setLastName("lastName1");
        userProfile.setPhone("phone1");
        userProfile.setPosition("position1");
        userProfile.setStreetAddress("streetAddress1");
        userProfile.setUsersAccount(usersAccount);

        outlets.setId(1l);
        outlets.setOutletName("outletName1");

        walletMaster.setId(1l);
        walletMaster.setTotalAmountAvailable(2500000d);
        walletMaster.setUserProfile(userProfile);
        walletMaster.setOutlets(outlets);

        usersAccount1.setId(2l);
        usersAccount1.setEmail("email2");

        userProfile1.setId(2l);
        userProfile1.setEmail("email2@email.com");
        userProfile1.setFirstName("firstName2");
        userProfile1.setLastName("lastName2");
        userProfile1.setPhone("phone2");
        userProfile1.setPosition("position2");
        userProfile1.setStreetAddress("streetAddress2");
        userProfile1.setUsersAccount(usersAccount1);

        outlets1.setId(2l);
        outlets1.setOutletName("outletName2");

        walletMaster1.setId(2l);
        walletMaster1.setTotalAmountAvailable(250d);
        walletMaster1.setUserProfile(userProfile1);
        walletMaster1.setOutlets(outlets1);

//		doReturn(Optional.of(outlets)).when(outletsService.getOutletsRepository()).findById(1l);

        when(walletMasterRepository.findAll()).thenReturn(Lists.newArrayList(walletMaster, walletMaster1));

        mockMvc.perform(get("/api/wallet-master/find/all")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].outletsId", is(1)))
                .andExpect(jsonPath("$.data[1].id", is(2)));
    }
}
