package com.main.fastserver.ControllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
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


    private List<Domain> domains;

    private final Domain DOMAIN_1 = new Domain("ECOSYSTEM", "icon", new ArrayList<>());
    private final Domain DOMAIN_2 = new Domain("METHOD", "icon", new ArrayList<>());

    @Before
    public void init(){
        domains = Arrays.asList(DOMAIN_1, DOMAIN_2);
        mvc = MockMvcBuilders.standaloneSetup(domainController).build();
    }

    @Test
    public void shouldGetDomainsOrGetDomainsByTitle() throws Exception {
        when(domainService.findAll()).thenReturn(domains);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/domains")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = " [{\"id\": null," +
                "\"title\":\"ECOSYSTEM\"," +
                "\"icon\":\"icon\"," +
                "\"subdomains\":[]}," +
                "{\"id\":null," +
                "\"title\":\"METHOD\"," +
                "\"icon\":\"icon\"," +
                "\"subdomains\":[]}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
        when(domainService.findByTitle("METHOD")).thenReturn(DOMAIN_2);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders.get("/api/domains?title=METHOD")
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result2 = mvc.perform(requestBuilder2).andReturn();

        String expected2 = " {\"id\":null," +
                "\"title\":\"METHOD\"," +
                "\"icon\":\"icon\"," +
                "\"subdomains\":[]}";
        JSONAssert.assertEquals(expected2, result2.getResponse().getContentAsString(), true);
    }
}
