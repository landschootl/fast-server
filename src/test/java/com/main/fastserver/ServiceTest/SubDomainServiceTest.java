package com.main.fastserver.ServiceTest;

import com.main.fastserver.Entity.Sub_domain;
import com.main.fastserver.Repository.SubDomainRepository;
import com.main.fastserver.Service.SubDomainService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubDomainServiceTest {

    /*@Mock
    SubDomainRepository subDomainRepository;

    @InjectMocks
    SubDomainService subDomainService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private final Sub_domain SUBDOMAIN_1 = new Sub_domain("test", new ArrayList<>());
    private final Sub_domain SUBDOMAIN_2 = new Sub_domain("test2", new ArrayList<>());
    private final Sub_domain SUBDOMAIN_3 = new Sub_domain("test3", new ArrayList<>());


    @Test
    public void findAllTest() {
        List<Sub_domain> subDomains = Arrays.asList(SUBDOMAIN_1, SUBDOMAIN_2, SUBDOMAIN_3);
        when(subDomainRepository.collectAll()).thenReturn(subDomains);
        List<Sub_domain> subdomainsFound = subDomainService.findAll();
        assertTrue(subDomains.equals(subdomainsFound));
    }*/
}
