package com.davidson.subdomain;

import com.davidson.subdomain.SubDomain;
import com.davidson.subdomain.SubDomainService;
import com.davidson.subdomain.SubDomainController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private Gson gson = new GsonBuilder().create();

    private final SubDomain SUBDOMAIN_1 = SubDomain.builder().id(1L).title("Cloud").skills(new ArrayList<>()).build();
    private final SubDomain SUBDOMAIN_2 = SubDomain.builder().id(2L).title("Front").skills(new ArrayList<>()).build();

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
        String expected = gson.toJson(new SubDomain[] {SUBDOMAIN_1, SUBDOMAIN_2});
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }


}
