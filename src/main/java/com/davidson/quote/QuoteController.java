package com.davidson.quote;

import com.davidson.domain.DomainService;
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
        if (quote.getSkills() == null || quote.getSkills().isEmpty()){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        Quote result =  quoteService.createQuote(quote);
        return ResponseEntity.ok(result);
    }

    /**
     * Update the quote with is id
     * @param id of the quote we want to update
     * @param quote data to be updated
     * @return not found if id parameter is not defined or ok if the quote is present
     */
    @RequestMapping(value = "/quotes/{quoteid}", method = RequestMethod.PUT)
    public ResponseEntity updateQuote(@PathVariable("quoteid") Long id, @RequestBody Quote quote) {
        if (quote.getSkills() == null || quote.getSkills().isEmpty()){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        Optional<Quote> currentQuote = quoteService.findById(id);
        if(!currentQuote.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        quote.setId(id);
        quoteService.updateQuote(quote);
        return ResponseEntity.ok(quote);
    }

    /**
     * End-point for validate status send for quote
     * @param id of the quote to be validated
     * @return quote that has the id
     */
    @RequestMapping(value = "/quotes/{quoteid}/validate", method = RequestMethod.PUT)
    public ResponseEntity validateQuote(@PathVariable("quoteid") Long id) {
        Optional<Quote> optionalQuote = quoteService.findById(id);
        if(!optionalQuote.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Quote quote = optionalQuote.get();
        quote.setId(id);
        quote = quoteService.validateQuote(quote);
        return ResponseEntity.ok(quote);
    }
}
