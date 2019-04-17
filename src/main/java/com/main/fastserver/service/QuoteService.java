package com.main.fastserver.service;

import com.main.fastserver.entity.Quote;
import com.main.fastserver.repository.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * service for quote
 */
@Service
public class QuoteService {

    private final static Logger LOG = LoggerFactory.getLogger(DomainService.class);

    @Autowired
    private QuoteRepository quoteRepository;

    /**
     * Collect all quote present in the database
     * @return List of quote
     */
    public List<Quote> findAll() {
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
    public Quote createQuote(Quote quote) {
        quote.setId(null);
        return quoteRepository.save(quote);
    }

    /**
     * Search quote by id
     * @param id
     * @return optional with quote if the quote is present in the database
     * or optional null, is the quote is not present
     */
    public Optional<Quote> findById(Long id) {
        return quoteRepository.findById(id);
    }

    /**
     * Update the quote
     * @param quote
     * @return the quote updated with is id
     */
    public Quote updateQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

}
