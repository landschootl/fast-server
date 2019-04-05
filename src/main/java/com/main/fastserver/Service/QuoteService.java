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

/**
 * Service for quote
 */
@Service
public class QuoteService {

    private final static Logger LOG = LoggerFactory.getLogger(DomainService.class);

    @Autowired
    private final QuoteRepository quoteRepository;

    /**
     * Constructor
     * @param quoteRepository
     */
    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    /**
     * Collect all quote present in the database
     * @return List of quote
     */
    public List<Quote> collectAll() {
        Iterable<Quote> quoteIterable = quoteRepository.findAll();
        List<Quote> quotes = new ArrayList<>();
        Iterator<Quote> quoteIterator = quoteIterable.iterator();
        while(quoteIterator.hasNext()) {
            quotes.add(quoteIterator.next());
        }
        return quotes;
    }

    /**
     * Save quote parameter in the database
     * @param quote to be saved
     */
    public Quote persistQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    public Quote findById(long id) {
        return quoteRepository.findById(id);
    }

    public void updateQuote(Quote quote) {
        quoteRepository.save(quote);
    }


}
