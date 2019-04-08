package com.main.fastserver.ServiceTest;


import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Repository.DomainRepository;
import com.main.fastserver.Service.DomainService;
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

    private final Domain DOMAIN_1 = new Domain("ECOSYSTEM", "icon", new ArrayList<>());
    private final Domain DOMAIN_2 = new Domain("METHOD", "icon", new ArrayList<>());
    private final Domain DOMAIN_3 = new Domain("QUALITE", "icon", new ArrayList<>());

    @Test
    public void collectAllTest() {
        List<Domain> domains = Arrays.asList(DOMAIN_1, DOMAIN_2, DOMAIN_3);
        when(domainRepositoryMock.collectAll()).thenReturn(domains);
        List<Domain> domainsFound = domainService.findAll();
        assertTrue(domains.equals(domainsFound));
    }

    @Test
    public void findByTitle() {
        when(domainRepositoryMock.findByTitle(DOMAIN_1.getTitle())).thenReturn(DOMAIN_1);
        Domain domainFound = domainService.findByTitle(DOMAIN_1.getTitle());
        assertEquals(DOMAIN_1, domainFound);
    }

}
