package com.main.fastserver.ServiceTest;

import com.main.fastserver.Entity.SubDomain;
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

    @Mock
    SubDomainRepository subDomainRepository;

    @InjectMocks
    SubDomainService subDomainService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private final SubDomain SUBDOMAIN_1 = new SubDomain("test", new ArrayList<>());
    private final SubDomain SUBDOMAIN_2 = new SubDomain("test2", new ArrayList<>());
    private final SubDomain SUBDOMAIN_3 = new SubDomain("test3", new ArrayList<>());


    @Test
    public void shouldGetAllSubdomains() {
        List<SubDomain> subDomains = Arrays.asList(SUBDOMAIN_1, SUBDOMAIN_2, SUBDOMAIN_3);
        when(subDomainRepository.collectAll()).thenReturn(subDomains);
        List<SubDomain> subdomainsFound = subDomainService.findAll();
        assertTrue(subDomains.equals(subdomainsFound));
    }
}
