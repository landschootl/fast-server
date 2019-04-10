package com.main.fastserver.ControllerTest;

import com.main.fastserver.Controller.SubDomainController;
import com.main.fastserver.Entity.SubDomain;
import com.main.fastserver.Service.SubDomainService;
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
public class SubDomainControllerTest {

    @Mock
    private SubDomainService subDomainService;

    @InjectMocks
    private SubDomainController subDomainController;

    private MockMvc mvc;

    private final SubDomain SUBDOMAIN_1 = SubDomain.builder().title("Cloud").skills(new ArrayList<>()).build();
    private final SubDomain SUBDOMAIN_2 = SubDomain.builder().title("Front").skills(new ArrayList<>()).build();

    @Before
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(subDomainController).build();
    }

    @Test
    public void shouldGetSubdomains() throws Exception {
        List<SubDomain> subDomains = Arrays.asList(SUBDOMAIN_1, SUBDOMAIN_2);
        when(subDomainService.findAll()).thenReturn(subDomains);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/subdomains")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = " [{\"id\": null," +
                "\"title\":\"Cloud\"," +
                "\"skills\":[]}," +
                "{\"id\":null," +
                "\"title\":\"Front\"," +
                "\"skills\":[]}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

}
