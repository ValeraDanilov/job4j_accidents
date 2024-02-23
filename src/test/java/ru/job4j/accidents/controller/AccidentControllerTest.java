package ru.job4j.accidents.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void accidents() throws Exception {
        this.mockMvc
                .perform(get("/accident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident"));

    }

    @Test
    @WithMockUser
    public void formUpdateItem() throws Exception {
        this.mockMvc
                .perform(get("/formAccidentId/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident"));

    }
}