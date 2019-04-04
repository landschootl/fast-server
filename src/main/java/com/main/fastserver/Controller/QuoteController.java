package com.main.fastserver.Controller;

import com.main.fastserver.Entity.Quote;
import com.main.fastserver.Service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @GetMapping("/api/quotes")
    public ResponseEntity collectAll() {
        return ResponseEntity.ok(quoteService.collectAll());
    }

    @RequestMapping(value = "/api/quotes", method = RequestMethod.POST)
    public ResponseEntity persistQuote(@RequestBody Quote quote) {
        quoteService.persisteQuote(quote);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
