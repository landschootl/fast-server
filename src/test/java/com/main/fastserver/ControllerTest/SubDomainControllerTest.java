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

    private List<SubDomain> subDomains;

    private final SubDomain SUBDOMAIN_1 = new SubDomain("Cloud", new ArrayList<>());
    private final SubDomain SUBDOMAIN_2 = new SubDomain("Front", new ArrayList<>());

    @Before
    public void init() {
        subDomains = Arrays.asList(SUBDOMAIN_1, SUBDOMAIN_2);
        mvc = MockMvcBuilders.standaloneSetup(subDomainController).build();
    }

    @Test
    public void shouldGetSubdomains() throws Exception {
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
        System.out.println(result.getResponse().getContentAsString());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

}
