package com.main.fastserver.Controller;

import com.main.fastserver.Entity.Quote;
import com.main.fastserver.Service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Quote
 */
@RestController
@RequestMapping("/")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    /**
     * End-point for get all quote present in the database
     * @return quote list and display response in format JSON
     */
    @GetMapping("/api/quotes")
    public ResponseEntity collectAll() {
        return ResponseEntity.ok(quoteService.collectAll());
    }

    /**
     * End-point for create a quote
     * @param quote create
     * @return 201 created
     */
    @RequestMapping(value = "/api/quotes", method = RequestMethod.POST)
    public ResponseEntity persistQuote(@RequestBody Quote quote) {
        quoteService.persistQuote(quote);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value = "/api/quotes/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateQuote(@PathVariable("id") long id, @RequestBody Quote quote) {
        Quote currentQuote = quoteService.findById(id);

        if(currentQuote == null) {
            return ResponseEntity.notFound().build();
        }
        currentQuote.setName(quote.getName());
        currentQuote.setMail(quote.getMail());
        currentQuote.setDescription(quote.getDescription());
        currentQuote.setTel(quote.getTel());
        currentQuote.setSkills(quote.getSkills());
        quoteService.updateQuote(currentQuote);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
