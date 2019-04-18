package com.main.fastserver.ControllerTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.fastserver.Controller.QuoteController;
import com.main.fastserver.Entity.Quote;
import com.main.fastserver.Entity.Skill;
import com.main.fastserver.Service.QuoteService;
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

    private final GsonBuilder builder = new GsonBuilder();
    private final Gson gson = builder.create();

    private final Skill SKILL_1 = Skill.builder().id(3L).title("skill").description("description").build();

    private final Quote QUOTE_1 = Quote.builder().id(1L).name("test").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(false).build();
    private final Quote QUOTE_2 = Quote.builder().id(2L).name("test2").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(false).build();
    private final Quote QUOTE_CREATE = Quote.builder().name("test").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(false).build();
    private final Quote QUOTE_UPDATE = Quote.builder().id(1L).name("test").mail("test@test.fr").tel("06060606").description("desc").skills(Arrays.asList(SKILL_1)).send(false).build();
    private final Quote QUOTE_VALIDATE = Quote.builder().id(1L).name("test").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(true).build();


    @Before
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(quoteController).build();
    }

    @Test
    public void shouldGetQuotes() throws Exception{
        List<Quote> quotes = Arrays.asList(QUOTE_1, QUOTE_2);
        when(quoteService.findAll()).thenReturn(quotes);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quotes")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = gson.toJson(new Quote[] {QUOTE_1, QUOTE_2});
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldCreateQuote() throws Exception {
        when(quoteService.createQuote(QUOTE_CREATE)).thenReturn(QUOTE_1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/quotes")
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(QUOTE_CREATE))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = gson.toJson(QUOTE_1);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldNotCreateQuotePreconditionFailed() throws Exception{
        QUOTE_1.setSkills(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/quotes")
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(QUOTE_1))
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldUpdateQuote() throws Exception{
        when(quoteService.updateQuote(QUOTE_UPDATE)).thenReturn(QUOTE_1);
        when(quoteService.findById(any())).thenReturn(Optional.of(QUOTE_1));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(QUOTE_UPDATE))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = gson.toJson(QUOTE_UPDATE);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldNotUpdateQuoteNotFound() throws Exception{
        when(quoteService.findById(1L)).thenReturn(Optional.of(QUOTE_1));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(QUOTE_UPDATE))
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotUpdateQuotePreconditionFailed() throws Exception{
        QUOTE_1.setSkills(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(QUOTE_1))
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldValidateQuote() throws Exception{
        when(quoteService.findById(any())).thenReturn(Optional.of(QUOTE_1));
        when(quoteService.validateQuote(any())).thenReturn(QUOTE_VALIDATE);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/1/validate")
                .accept(APPLICATION_JSON);
        String expected = gson.toJson(QUOTE_VALIDATE);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldNotValidateQuoteNotFound() throws Exception{
        when(quoteService.findById(1L)).thenReturn(Optional.of(QUOTE_1));
        String bodyContent = gson.toJson(QUOTE_1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/2/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isNotFound());
    }
}
