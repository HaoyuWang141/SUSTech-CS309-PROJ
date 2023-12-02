package com.ooad.dormitory.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ooad.dormitory.entity.AdminAccount;
import com.ooad.dormitory.mapper.AdminAccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminAccountMapper adminAccountMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // 可以在这里设置一些每个测试之前的初始化逻辑
    }

    @AfterEach
    void tearDown() {
        // 可以在这里设置一些每个测试之后的清理逻辑
    }

    @Test
    void createAdminAccount() throws Exception {
        List<AdminAccount> adminAccounts = Arrays.asList(
                new AdminAccount(null, "admin1", "password1"),
                new AdminAccount(null, "admin2", "password2"),
                new AdminAccount(null, "admin3", "password3")
        );

        mockMvc.perform(post("/admin/adminAccount/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminAccounts)))
                .andExpect(status().isOk());

        adminAccounts.forEach(verify(adminAccountMapper)::insert);
    }

    @Test
    void deleteAdminAccount() throws Exception {
        mockMvc.perform(delete("/admin/adminAccount/delete")
                        .param("accountId", "1"))
                .andExpect(status().isOk());

        verify(adminAccountMapper).deleteById(1);
    }

    @Test
    void updateAdminAccount() throws Exception {
        AdminAccount adminAccount = new AdminAccount(1, "adminUpdated", "newPassword");

        mockMvc.perform(put("/admin/adminAccount/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminAccount)))
                .andExpect(status().isOk());

        verify(adminAccountMapper).updateById(adminAccount);
    }

    @Test
    void getAdminAccount() throws Exception {
        List<AdminAccount> adminAccounts = Arrays.asList(
                new AdminAccount(1, "admin1", "password1"),
                new AdminAccount(2, "admin2", "password2")
        );

        given(adminAccountMapper.selectList(null)).willReturn(adminAccounts);

        mockMvc.perform(get("/admin/adminAccount/get"))
                .andExpect(status().isOk());

        verify(adminAccountMapper).selectList(null);
    }
}
