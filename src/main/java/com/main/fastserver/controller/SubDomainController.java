package com.main.fastserver.controller;

import com.main.fastserver.entity.SubDomain;
import com.main.fastserver.service.SubDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller for subdomain
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
