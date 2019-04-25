package com.davidson.domain;


import com.davidson.domain.Domain;
import com.davidson.domain.DomainService;
import com.davidson.domain.DomainRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DomainServiceTest {

    @Mock
    DomainRepository domainRepositoryMock;

    @InjectMocks
    DomainService domainService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private final Domain DOMAIN_1 = Domain.builder().title("ECOSYSTEM").icon("icon").subdomains(null).build();
    private final Domain DOMAIN_2 = Domain.builder().title("METHOD").icon("icon").subdomains(null).build();
    private final Domain DOMAIN_3 = Domain.builder().title("QUALITE").icon("icon").subdomains(null).build();

    @Test
    public void shouldGetAllDomains() {
        List<Domain> domains = Arrays.asList(DOMAIN_1, DOMAIN_2, DOMAIN_3);
        when(domainRepositoryMock.collectAll()).thenReturn(domains);
        List<Domain> domainsFound = domainService.findAll();
        assertTrue(domains.equals(domainsFound));
    }

    @Test
    public void shouldGetDomainsByTitle() {
        when(domainRepositoryMock.findByTitle(DOMAIN_1.getTitle())).thenReturn(DOMAIN_1);
        Domain domainFound = domainService.findByTitle(DOMAIN_1.getTitle());
        assertEquals(DOMAIN_1, domainFound);
    }

}
