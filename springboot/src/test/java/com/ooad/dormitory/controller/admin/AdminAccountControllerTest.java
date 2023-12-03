package com.ooad.dormitory.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.ooad.dormitory.entity.AdminAccount;
import com.ooad.dormitory.mapper.AdminAccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void createAdminAccountTest() throws Exception {
        String accountName = "admin";
        String password = "password";

        // Step 1: 创建管理员账户
        mockMvc.perform(post("/admin/adminAccount/create")
                        .param("accountName", accountName)
                        .param("password", password))
                .andExpect(status().isOk());

        // Step 2: 通过 GET 请求验证账户是否创建成功
        MvcResult result = mockMvc.perform(get("/admin/adminAccount/get"))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        // 使用 JsonPath 解析 JSON 响应
        JsonPath jsonPath = JsonPath.compile("$.[?(@.accountName == '" + accountName + "')]");
        DocumentContext jsonContext = JsonPath.parse(responseContent);
        List<?> accountList = jsonContext.read(jsonPath);

        assertFalse(accountList.isEmpty()); // 确认找到了匹配的账户
        assertEquals(accountName, jsonContext.read(jsonPath + ".accountName")); // 验证账户名
        assertEquals(password, jsonContext.read(jsonPath + ".password")); // 验证密码

    }


    @Test
    void deleteAdminAccount() throws Exception {
        mockMvc.perform(delete("/admin/adminAccount/delete")
                        .param("accountId", "1"))
                .andExpect(status().isOk());

        verify(adminAccountMapper).deleteById(1);
    }

    @Test
    void updateAdminAccountTest() throws Exception {
        Integer accountId = 1;
        String accountName = "updatedAdmin";
        String password = "updatedPassword";

        mockMvc.perform(put("/admin/adminAccount/update")
                        .param("accountId", accountId.toString())
                        .param("accountName", accountName)
                        .param("password", password))
                .andExpect(status().isOk());

        ArgumentCaptor<AdminAccount> captor = ArgumentCaptor.forClass(AdminAccount.class);
        verify(adminAccountMapper).updateById(captor.capture());

        AdminAccount capturedAccount = captor.getValue();
        assertEquals(accountId, capturedAccount.getAccountId()); // 验证账户ID是否正确
        assertEquals(accountName, capturedAccount.getAccountName()); // 验证账户名是否正确
        assertEquals(password, capturedAccount.getPassword()); // 验证密码是否正确
    }


    @Test
    void getAdminAccount() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/adminAccount/get"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result);
    }
}
