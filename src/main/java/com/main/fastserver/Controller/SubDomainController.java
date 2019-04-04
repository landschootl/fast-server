package com.main.fastserver.Controller;

import com.main.fastserver.Entity.Sub_domain;
import com.main.fastserver.Service.SubDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class SubDomainController {

    @Autowired
    private SubDomainService subDomainService;

    @GetMapping("/api/subdomains")
    public List<Sub_domain> collectAll() {
        return subDomainService.collectAll();
    }

}
