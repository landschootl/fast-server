package com.main.fastserver.Controller;

import com.main.fastserver.Entity.Quote;
import com.main.fastserver.Service.DomainService;
import com.main.fastserver.Service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for Quote
 */
@RestController
@RequestMapping("/api/")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    private final static Logger LOG = LoggerFactory.getLogger(DomainService.class);

    /**
     * End-point for get all quote present in the database
     * @return quote list and display response in format JSON
     */
    @GetMapping("/quotes")
    public ResponseEntity collectAll() {
        return ResponseEntity.ok(quoteService.findAll());
    }

    /**
     * End-point for create a quote
     * @param quote create
     * @return 201 created
     */
    @RequestMapping(value = "/quotes", method = RequestMethod.POST)
    public ResponseEntity persistQuote(@RequestBody Quote quote) {
        LOG.debug("" + quote.getId());
        quoteService.createQuote(quote);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value = "/quotes/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateQuote(@PathVariable("id") Long id, @RequestBody Quote quote) {
        Optional<Quote> currentQuote = quoteService.findById(id);

        if(!currentQuote.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        quote.setId(id);
        quoteService.updateQuote(quote);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * End-point for validate status send for quote
     * @param id of the quote to be validated
     * @return quote that has the id
     */
    @RequestMapping(value = "/quotes/{quoteid}/validate", method = RequestMethod.PUT)
    public ResponseEntity validateQuote(@PathVariable("quoteid") Long id) {
        Optional<Quote> currentQuote = quoteService.findById(id);
        if(!currentQuote.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Quote quote = currentQuote.get();
        quote.setSend(true);
        quoteService.updateQuote(quote);
        return ResponseEntity.ok(quote);
    }
}
