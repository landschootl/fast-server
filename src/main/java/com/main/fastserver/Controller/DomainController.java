package com.main.fastserver.Controller;


import com.main.fastserver.Service.DomainService;
import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @GetMapping("/api/domains")
    public List<Domain> findByTitle(@RequestParam(required = false) String title) {
        if(title != null) {
            return domainService.findByTitle(title);
        }else{
            return domainService.collectAll();
        }
    }
}
