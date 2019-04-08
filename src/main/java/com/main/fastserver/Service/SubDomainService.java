package com.main.fastserver.Service;

import com.main.fastserver.Entity.Sub_domain;
import com.main.fastserver.Repository.SubDomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for subdomain
 */
@Service
public class SubDomainService {

    private final static Logger LOG = LoggerFactory.getLogger(DomainService.class);

    @Autowired
    private SubDomainRepository subDomainRepository;

    /**
     * Returns all domains in the database
     * @return domains list present in database
     */
    public List<Sub_domain> findAll() {
        return subDomainRepository.collectAll();
    }

}
