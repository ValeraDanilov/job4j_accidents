package ru.job4j.accidents.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;
import ru.job4j.accidents.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccidentControllerTest {

    @Mock
    private AccidentService accidentService;

    @Mock
    private AccidentTypeService accidentTypeService;

    @Mock
    private RuleService ruleService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccidentController accidentController;

    @Mock
    private HttpServletRequest req;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accidentController).build();
    }

    @Test
    @WithMockUser(username = "admin")
    public void testSearchItem() throws Exception {
        when(accidentService.findByAccidentId(anyInt())).thenReturn(Optional.of(new Accident()));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/formAccidentId/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("accident"))
                .andExpect(model().attributeExists("accidents", "chooses", "types", "rules", "isAdmin"));
    }

    @Test
    public void testSave() {
        Accident accident = new Accident();
        accident.setId(1);

        AccidentType accidentType = new AccidentType();
        accidentType.setId(1);

        when(accidentTypeService.findByAccidentTypeId(1)).thenReturn(Optional.of(accidentType));

        when(req.getParameterValues("rIds")).thenReturn(new String[]{"1", "2"});

        Rule rule1 = new Rule();
        rule1.setId(1);
        Rule rule2 = new Rule();
        rule2.setId(2);

        when(ruleService.findByRuleId(1)).thenReturn(Optional.of(rule1));
        when(ruleService.findByRuleId(2)).thenReturn(Optional.of(rule2));

        String result = accidentController.save(accident, 1, req);

        assertEquals("redirect:/index", result);

        verify(accidentService, times(1)).create(accident);
    }

    @Test
    public void testUpdateItem() {
        Accident accident = new Accident();
        accident.setId(1);

        AccidentType accidentType = new AccidentType();
        accidentType.setId(1);

        when(accidentTypeService.findByAccidentTypeId(1)).thenReturn(Optional.of(accidentType));

        when(req.getParameterValues("rIds")).thenReturn(new String[]{"1", "2"});

        Rule rule1 = new Rule();
        rule1.setId(1);
        Rule rule2 = new Rule();
        rule2.setId(2);

        when(ruleService.findByRuleId(1)).thenReturn(Optional.of(rule1));
        when(ruleService.findByRuleId(2)).thenReturn(Optional.of(rule2));

        String result = accidentController.updateItem(accident, 1, req);

        assertEquals("redirect:/formAccidentId/1", result);

        verify(accidentService, times(1)).update(accident);
    }

    @Test
    public void testDeleteAccident() throws Exception {
        when(accidentService.findByAccidentId(anyInt())).thenReturn(Optional.of(new Accident()));

        mockMvc.perform(get("/formDeleteAccident/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        verify(accidentService, times(1)).delete(any(Accident.class));
    }
}
