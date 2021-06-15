package com.bank.api.integrationtests.springbootdomainjdbc;

import com.bank.api.db.jdbc.connections.ConnectionDB;
import com.bank.api.db.jdbc.tablecreator.TableCreator;
import com.bank.api.main.springbootapp.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class SpringBootCardControllerTests {
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
    void getAll_cardsInDb_returnJsonCards() throws Exception {
        this.mockMvc.perform(get("/cards")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void get_cardInDb_returnJsonCards() throws Exception {
        this.mockMvc.perform(get("/cards/111")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void get_card444NotInDB_return404code() throws Exception {
        this.mockMvc.perform(get("/cards/444")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void create_account11InDB_return201code() throws Exception {
        this.mockMvc.perform(post("/cards/account/11/create")).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    void create_account444NotInDB_return404code() throws Exception {
        this.mockMvc.perform(post("/cards/account/444/create")).andDo(print()).andExpect(status().isNotFound());
    }
}
