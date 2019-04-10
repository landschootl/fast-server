package com.main.fastserver.ServiceTest;

import com.main.fastserver.Entity.Quote;
import com.main.fastserver.Repository.QuoteRepository;
import com.main.fastserver.Service.QuoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceTest {

    @Mock
    QuoteRepository quoteRepositoryMock;

    @InjectMocks
    QuoteService quoteService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private final Long QUOTE_ID = 1L;
    private final Quote QUOTE_1 = Quote.builder().name("test").mail("test@test.fr").tel("00000000").description("description").skills(null).build();
    private final Quote QUOTE_2 = Quote.builder().name("test2").mail("test@test.fr").tel("00000000").description("description").skills(null).build();
    private final Quote QUOTE_3 = Quote.builder().name("test3").mail("test@test.fr").tel("00000000").description("description").skills(null).build();


    @Test
    public void shouldGetAllQuotes() {
        List<Quote> quotes = Arrays.asList(QUOTE_1, QUOTE_2, QUOTE_3);
        when(quoteRepositoryMock.findAll()).thenReturn(quotes);
        List<Quote> quotesFound = quoteService.findAll();
        assertEquals(quotes, quotesFound);
    }

    @Test
    public void shouldGetQuoteById() {
        when(quoteRepositoryMock.findById(QUOTE_ID)).thenReturn(Optional.of(QUOTE_1));
        Quote quoteFound = quoteService.findById(QUOTE_ID).get();
        assertEquals(QUOTE_1, quoteFound);
    }

    @Test
    public void shouldCreateQuote() {
        QUOTE_1.setId(1L);
        when(quoteRepositoryMock.save(Mockito.any())).thenReturn(QUOTE_1);
        Quote quoteCreate = quoteService.createQuote(QUOTE_1);
        assertEquals(QUOTE_1, quoteCreate);
    }

    @Test
    public void shouldUpdateQuote() {
        QUOTE_1.setId(1L);
        when(quoteRepositoryMock.save(Mockito.any())).thenReturn(QUOTE_1);
        Quote quoteUpdate = quoteService.updateQuote(QUOTE_1);
        assertEquals(QUOTE_1, quoteUpdate);
    }
}