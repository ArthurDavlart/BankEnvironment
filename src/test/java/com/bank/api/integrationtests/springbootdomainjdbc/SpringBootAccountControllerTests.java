package com.bank.api.integrationtests.springbootdomainjdbc;

import com.bank.api.db.jdbc.connections.ConnectionDB;
import com.bank.api.db.jdbc.tablecreator.TableCreator;
import com.bank.api.domain.dto.*;
import com.bank.api.main.springbootapp.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class SpringBootAccountControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConnectionDB connectionDB;

    @BeforeEach
    void init() throws SQLException {
        TableCreator tableCreator = new TableCreator(connectionDB);
        tableCreator.createAllTable();

        String sqlFillDb = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, 11, 1000, 'RUB', 1),\n" +
                "(2, 12, 2000, 'RUB', 1);\n" +
                "\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, 111, 1),\n" +
                "(2, 112, 1),\n" +
                "(3, 121, 2),\n" +
                "(4, 122, 2);";

        connectionDB.get().prepareStatement(sqlFillDb).execute();
    }

    @AfterEach
    void destroy() throws SQLException {
        connectionDB.close();
    }

    @Test
    void get_AccountWithNumber11InDb_returnJsonAccount() throws Exception {
        this.mockMvc.perform(get("/accounts/11")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void get_AccountWithNumber21NotInDb_expected404Error() throws Exception {
        this.mockMvc.perform(get("/accounts/21")).andDo(print()).andExpect(status().is(404));
    }

    @Test
    void geAll_AccountsInDb_returnJsonAccounts() throws Exception {
        this.mockMvc.perform(get("/accounts")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getBalance_AccountsInDb_returnJsonAccounts() throws Exception {
        this.mockMvc.perform(get("/accounts/11/balance")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void setBalance_Account11With1000Rub_Account11With1500() throws Exception {
        Deposit deposit = new Deposit();
        deposit.setCurrency(Currency.RUB);
        deposit.setQuantity(new BigDecimal(500));
        this.mockMvc.perform(
                post("/accounts/11/replenishment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"RUB\", \"quantity\": 500}")
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void setBalance_Account11With1000Rub_returnBadRequestCodeBecauseDepositCurrencyNotEqualAccountCurrency() throws Exception {
        this.mockMvc.perform(
                post("/accounts/11/replenishment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currency\": \"EUR\", \"quantity\": 500}")
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void setBalance_Account11With1000Rub_returnBadRequestCodeBecauseDepositQuantityLess0() throws Exception {
        this.mockMvc.perform(
                post("/accounts/11/replenishment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currency\": \"RUB\", \"quantity\": -1000}")
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void setBalance_Account21NotInDB_returnNotFoundRequestCode() throws Exception {
        this.mockMvc.perform(
                post("/accounts/21/replenishment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currency\": \"RUB\", \"quantity\": 500}"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void create_Account1NotInDB_return201RequestCode() throws Exception {

        this.mockMvc.perform(post("/user/1/accounts/create"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void create_Account1NotInDBAndUser5NotInDb_return404RequestCode() throws Exception {

        this.mockMvc.perform(post("/user/5/accounts/create"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
