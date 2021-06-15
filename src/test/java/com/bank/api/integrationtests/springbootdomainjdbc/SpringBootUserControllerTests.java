package com.bank.api.integrationtests.springbootdomainjdbc;

import com.bank.api.db.jdbc.connections.ConnectionDB;
import com.bank.api.db.jdbc.connections.DBConnectionSettings;
import com.bank.api.db.jdbc.connections.H2Connection;
import com.bank.api.db.jdbc.tablecreator.TableCreator;
import com.bank.api.domain.dto.Passport;
import com.bank.api.domain.dto.PassportType;
import com.bank.api.domain.dto.User;
import com.bank.api.main.springbootapp.Application;
import com.bank.api.main.springbootapp.ApplicationConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class SpringBootUserControllerTests {

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
                "(1, 11, 0, 'RUB', 1),\n" +
                "(2, 12, 0, 'RUB', 1);\n" +
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
        //connectionDB.close();
    }

    @Test
    void environmentTest(){
        assertTrue(true);
    }

    @Test
    void getAll_NotJson_UsersJson() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void get_notJson_UserJson() throws Exception{
        this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void create_notUserInDb_return201code() throws Exception {
        String user = new ObjectMapper().writeValueAsString(
                new User(10, "Arthur", "Davletkaliev", " ",
                        new Passport(0, 1, PassportType.KZ)));
        this.mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void get_methodNotResolved_return500Code() throws Exception{
        this.mockMvc.perform(post("/users/1/delete")).andDo(print()).andExpect(status().isInternalServerError());
    }
}
