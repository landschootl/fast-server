package com.main.fastserver.Service;

import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Repositories.DomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DomainService {

    private final static Logger LOG = LoggerFactory.getLogger(DomainService.class);

    @Autowired
    private final DomainRepository domainRepository;

    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    public Domain findByTitle(String title) {
        return domainRepository.findByTitle(title);
    }
}
