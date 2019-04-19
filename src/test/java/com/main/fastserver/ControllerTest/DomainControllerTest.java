package com.main.fastserver.ControllerTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.fastserver.Controller.DomainController;
import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Service.DomainService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DomainControllerTest {

    @Mock
    private DomainService domainService;

    @InjectMocks
    private DomainController domainController;

    private MockMvc mvc;

    private Gson gson = new GsonBuilder().create();

    private final Domain DOMAIN_1 = Domain.builder().id(1L).title("ECOSYSTEM").icon("icon").subdomains(new ArrayList<>()).build();
    private final Domain DOMAIN_2 = Domain.builder().id(2L).title("METHOD").icon("icon").subdomains(new ArrayList<>()).build();

    @Before
    public void init(){
        mvc = MockMvcBuilders.standaloneSetup(domainController).build();
    }

    @Test
    public void shouldGetDomainsOrGetDomainsByTitle() throws Exception {
        List<Domain> domains = Arrays.asList(DOMAIN_1, DOMAIN_2);
        when(domainService.findAll()).thenReturn(domains);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/domains")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = gson.toJson(new Domain[] {DOMAIN_1, DOMAIN_2});
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
        when(domainService.findByTitle("METHOD")).thenReturn(DOMAIN_2);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders.get("/api/domains?title=METHOD")
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult result2 = mvc.perform(requestBuilder2).andReturn();
        String expected2 = gson.toJson(DOMAIN_2);
        JSONAssert.assertEquals(expected2, result2.getResponse().getContentAsString(), true);
    }
}