package com.main.fastserver.Service;

import com.main.fastserver.Entity.Sub_domain;
import com.main.fastserver.Repository.SubDomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubDomainService {

    private final static Logger LOG = LoggerFactory.getLogger(DomainService.class);

    @Autowired
    private final SubDomainRepository subDomainRepository;

    public SubDomainService(SubDomainRepository subDomainRepository) {
        this.subDomainRepository = subDomainRepository;
    }

    public List<Sub_domain> collectAll() {
        return subDomainRepository.collectAll();
    }

}
