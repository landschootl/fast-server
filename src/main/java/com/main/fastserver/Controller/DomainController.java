package com.main.fastserver.Controller;


import com.main.fastserver.Service.DomainService;
import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Repositories.DomainRepository;
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

    @Autowired
    private DomainRepository domainRepository;

    @GetMapping("/api/domain")
    public Domain findByTitle(@RequestParam String title) {
        Domain result = domainService.findByTitle(title);
        return result;
    }

    @GetMapping("/api/domains")
    public List<Domain> collectAll() {
        return domainRepository.collectAll();
    }
}
