package com.main.fastserver.ControllerTest;

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

    private final Skill SKILL_1 = Skill.builder().title("skill").description("description").build();

    private final Quote QUOTE_1 = Quote.builder().id(1L).name("test").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(false).build();
    private final Quote QUOTE_2 = Quote.builder().id(2L).name("test2").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(false).build();

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
        String expected = " [{\"id\": 1," +
                "\"name\":\"test\"," +
                "\"mail\":\"test@test.fr\"," +
                "\"tel\":\"00000000\"," +
                "\"description\":\"description\"," +
                "\"skills\":[{\"id\":null," +
                "\"title\":\"skill\"," +
                "\"description\":\"description\"}]," +
                "\"send\":false}," +
                "{\"id\": 2," +
                "\"name\":\"test2\"," +
                "\"mail\":\"test@test.fr\"," +
                "\"tel\":\"00000000\"," +
                "\"description\":\"description\"," +
                "\"skills\":[{\"id\":null," +
                "\"title\":\"skill\"," +
                "\"description\":\"description\"}]," +
                "\"send\":false}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldCreateQuote() throws Exception {
        when(quoteService.createQuote(any())).thenReturn(QUOTE_1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/quotes")
                .contentType(APPLICATION_JSON)
                .content("{\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"00000000\",\"description\":\"description\",\"skills\":[{\"id\":null}]}")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = "{\"id\":1,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"00000000\",\"description\":\"description\",\"skills\":[{\"id\":null,\"title\":\"skill\",\"description\":\"description\"}],\"send\":false}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldNotCreateQuotePreconditionFailed() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/quotes")
                .contentType(APPLICATION_JSON)
                .content("{\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"00000000\",\"description\":\"description\",\"skills\":null}")
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldUpdateQuote() throws Exception{
        when(quoteService.updateQuote(any())).thenReturn(QUOTE_1);
        when(quoteService.findById(any())).thenReturn(Optional.of(QUOTE_1));
        String bodyContent = "{\"id\":1,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"0606060606\",\"description\":\"ceci est un test\",\"skills\":[{\"id\":null,\"title\":\"skill\",\"description\":\"description\"}],\"send\":false}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = "{\"id\":1,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"0606060606\",\"description\":\"ceci est un test\",\"skills\":[{\"id\":null,\"title\":\"skill\",\"description\":\"description\"}],\"send\":false}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldNotUpdateQuoteNotFound() throws Exception{
        String bodyContent = "{\"id\":2,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"0606060606\",\"description\":\"ceci est un test\",\"skills\":[{\"id\":null,\"title\":\"skill\",\"description\":\"description\"}]}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotUpdateQuotePreconditionFailed() throws Exception{
        String bodyContent = "{\"id\":1,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"0606060606\",\"description\":\"ceci est un test\",\"skills\":null}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldValidateQuote() throws Exception{
        when(quoteService.findById(any())).thenReturn(Optional.of(QUOTE_1));
        QUOTE_1.setSend(true);
        when(quoteService.validateQuote(any())).thenReturn(QUOTE_1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/1/validate")
                .accept(APPLICATION_JSON);
        String expected = "{\"id\":1,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"00000000\",\"description\":\"description\",\"skills\":[{\"id\":null,\"title\":\"skill\",\"description\":\"description\"}],\"send\":true}";
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldNotValidateQuoteNotFound() throws Exception{
        String bodyContent = "{\"id\":2,\"name\":\"test\",\"mail\":\"test@test.fr\",\"tel\":\"0606060606\",\"description\":\"ceci est un test\",\"skills\":[{\"id\":null,\"title\":\"skill\",\"description\":\"description\"}]}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quotes/2/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isNotFound());
    }
}
