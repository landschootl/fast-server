package com.main.fastserver.ControllerTest;

import com.main.fastserver.Controller.QuoteController;
import com.main.fastserver.Entity.Quote;
import com.main.fastserver.Entity.Skill;
import com.main.fastserver.Service.QuoteService;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class QuoteControllerTest {

    @Mock
    private QuoteService quoteService;

    @InjectMocks
    private QuoteController quoteController;

    private MockMvc mvc;

    private final Quote QUOTE_1 = new Quote("test", "test@test.fr", "00000000", "description", null);
    private final Quote QUOTE_2 = new Quote("test2", "test@test.fr", "00000000", "description", null);

    @Before
    public void init() {
        Skill skill = new Skill("skill", "description");
        List<Skill> skills = Arrays.asList(skill);
        QUOTE_1.setSkills(skills);
        QUOTE_2.setSkills(skills);
        mvc = MockMvcBuilders.standaloneSetup(quoteController).build();
    }

    @Test
    public void shouldGetQuotes() throws Exception{
        List<Quote> quotes = Arrays.asList(QUOTE_1, QUOTE_2);
        when(quoteService.findAll()).thenReturn(quotes);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quotes")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        String expected = " [{\"id\": null," +
                "\"name\":\"test\"," +
                "\"mail\":\"test@test.fr\"," +
                "\"tel\":\"00000000\"," +
                "\"description\":\"description\"," +
                "\"skills\":[{\"id\":null," +
                "\"title\":\"skill\"," +
                "\"description\":\"description\"}]}," +
                "{\"id\": null," +
                "\"name\":\"test2\"," +
                "\"mail\":\"test@test.fr\"," +
                "\"tel\":\"00000000\"," +
                "\"description\":\"description\"," +
                "\"skills\":[{\"id\":null," +
                "\"title\":\"skill\"," +
                "\"description\":\"description\"}]}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldCreateQuote() throws Exception {
        QUOTE_1.setId(1L);
        when(quoteService.createQuote(any())).thenReturn(QUOTE_1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/quotes")
                .contentType(APPLICATION_JSON)
                .content("{\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"00000000\",\"description\":\"description\",\"skills\":[{\"id\":null}]}")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":1,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"00000000\",\"description\":\"description\",\"skills\":[{\"id\":null,\"title\":\"skill\",\"description\":\"description\"}]}";
        System.out.println("REPONSE : " +result.getResponse().getContentAsString() + "\n" + expected);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldUpdateQuote() throws Exception{
        QUOTE_1.setId(1L);
        when(quoteService.updateQuote(any())).thenReturn(QUOTE_1);
        when(quoteService.findById(any())).thenReturn(Optional.of(QUOTE_1));

        String bodyContent = "{\"id\":1,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"0606060606\",\"description\":\"ceci est un test\",\"skills\":null}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":1,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"0606060606\",\"description\":\"ceci est un test\",\"skills\":null}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldNotGetQuoteNotFound() throws Exception{
        QUOTE_1.setId(1L);
        String bodyContent = "{\"id\":2,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"0606060606\",\"description\":\"ceci est un test\",\"skills\":null}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isNotFound());
    }


}
