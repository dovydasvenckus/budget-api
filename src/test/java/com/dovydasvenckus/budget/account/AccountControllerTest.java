package com.dovydasvenckus.budget.account;

import com.dovydasvenckus.budget.BudgetApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static com.dovydasvenckus.budget.account.AccountType.ASSET;
import static com.dovydasvenckus.budget.account.AccountType.INCOME;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetApplication.class)
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AccountService accountService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldCreateNewAccount() throws Exception {
        AccountDto accountDto = new AccountDto("Income", INCOME);
        when(accountService.createAccount(refEq(accountDto))).thenReturn(accountDto);

        mockMvc.perform(post("/api/accounts")
                .accept(APPLICATION_JSON_UTF8)
                .contentType(APPLICATION_JSON_UTF8)
                .content("{ \"name\": \"Income\", \"type\": \"INCOME\" }"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value("Income"))
                .andExpect(jsonPath("$.type").value("INCOME"));
    }

    @Test
    public void shouldReturnEmptyResponseWhenThereAreNoAccounts() throws Exception {
        when(accountService.getAccounts()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/accounts")
                .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"));
    }

    @Test
    public void shouldReturnAccountWhenPresent() throws Exception {
        when(accountService.getAccounts()).thenReturn(Arrays.asList(new AccountDto("Assets", ASSET)));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value("Assets"))
                .andExpect(jsonPath("$.[0].type").value("ASSET"));
    }
}
