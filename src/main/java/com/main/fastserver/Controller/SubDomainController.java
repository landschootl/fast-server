package com.main.fastserver.Controller;

import com.main.fastserver.Entity.SubDomain;
import com.main.fastserver.Service.SubDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for subdomain
 */
@RestController
@RequestMapping("/api")
public class SubDomainController {

    @Autowired
    private SubDomainService subDomainService;

    /**
     * End-point for get all subdomain present in the database
     * @return subdomain list and display response in format JSON
     */
    @GetMapping("/subdomains")
    public List<SubDomain> collectAll() {
        return subDomainService.findAll();
    }

}
