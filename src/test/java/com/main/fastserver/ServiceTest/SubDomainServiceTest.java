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

    private final SubDomain SUBDOMAIN_1 = SubDomain.builder().title("test").skills(null).build();
    private final SubDomain SUBDOMAIN_2 = SubDomain.builder().title("test2").skills(null).build();
    private final SubDomain SUBDOMAIN_3 = SubDomain.builder().title("test3").skills(null).build();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllSubdomains() {
        List<SubDomain> subDomains = Arrays.asList(SUBDOMAIN_1, SUBDOMAIN_2, SUBDOMAIN_3);
        when(subDomainRepository.collectAll()).thenReturn(subDomains);
        List<SubDomain> subdomainsFound = subDomainService.findAll();
        assertTrue(subDomains.equals(subdomainsFound));
    }
}
