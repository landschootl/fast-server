package com.main.fastserver.ServiceTest;

import com.main.fastserver.Entity.Quote;
import com.main.fastserver.Repository.QuoteRepository;
import com.main.fastserver.Service.QuoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceTest {

    @Mock
    QuoteRepository quoteRepositoryMock;

    @InjectMocks
    QuoteService quoteService;
    private final long QUOTE_ID = 1;
    private final Quote QUOTE_1 = new Quote("test", "test@test.fr", "00000000", "description", new ArrayList<>());
    private final Quote QUOTE_2 = new Quote("test2", "test@test.fr", "00000000", "description", new ArrayList<>());
    private final Quote QUOTE_3 = new Quote("test3", "test@test.fr", "00000000", "description", new ArrayList<>());


    @Test
    public void collectAllTest() {
        List<Quote> quotes = Arrays.asList(QUOTE_1, QUOTE_2, QUOTE_3);
        when(quoteRepositoryMock.findAll()).thenReturn(quotes);
        List<Quote> quotesFound = quoteService.collectAll();
        assertEquals(quotes, quotesFound);
    }

    @Test
    public void findByIdTest() {
        QUOTE_2.setId(QUOTE_ID);
        when(quoteRepositoryMock.findById(QUOTE_1.getId())).thenReturn(QUOTE_1);
        Quote quoteFound = quoteService.findById(QUOTE_1.getId());
        assertEquals(QUOTE_1, quoteFound);
    }


}