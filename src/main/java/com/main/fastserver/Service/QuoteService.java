package com.main.fastserver.Service;

import com.main.fastserver.Entity.Quote;
import com.main.fastserver.Repository.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class QuoteService {

    private final static Logger LOG = LoggerFactory.getLogger(DomainService.class);

    @Autowired
    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public List<Quote> collectAll() {
        Iterable<Quote> quoteIterable = quoteRepository.findAll();
        List<Quote> quotes = new ArrayList<>();
        Iterator<Quote> quoteIterator = quoteIterable.iterator();
        while(quoteIterator.hasNext()) {
            quotes.add(quoteIterator.next());
        }
        return quotes;
    }

    public void persisteQuote(Quote quote) {
        quoteRepository.save(quote);
    }

}
