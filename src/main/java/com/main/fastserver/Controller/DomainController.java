package com.main.fastserver.Controller;


import com.main.fastserver.Service.DomainService;
import com.main.fastserver.Entity.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for domain
 */
@RestController
@RequestMapping("/api")
public class DomainController {

    @Autowired
    private DomainService domainService;

    /**
     * End-point for get the domain with the title equal to the parameter or get all the domains present
     * in the database if there is no parameter
     * @param title of the field sought
     * @return the domain with the title equal tot the parameter ou list domains present in the database
     */
    @GetMapping("/domains")
    public ResponseEntity findAllOrFindByTitle(@RequestParam(required = false) String title) {
        if(title != null) {
            Domain domain = domainService.findByTitle(title);
            return ResponseEntity.ok(domain);
        }else{
            return ResponseEntity.ok(domainService.findAll());
        }
    }
}
