package com.main.fastserver.Service;

import com.main.fastserver.Entity.Domain;
import com.main.fastserver.Repository.DomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for Domain
 */

@Service
public class DomainService {

    private final static Logger LOG = LoggerFactory.getLogger(DomainService.class);

    @Autowired
    private DomainRepository domainRepository;

    /**
     * Constructor
     * @param domainRepository
     */
    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    public DomainService() {}
    /**
     * Searches the domain by title
     * @param title of the domain sought
     * @return Domain with equal title
     */
    public Domain findByTitle(String title) {
        return domainRepository.findByTitle(title);
    }

    /**
     * Returns all domains in the database
     * @return domains list present in database
     */
    public List<Domain> collectAll() {
        return domainRepository.collectAll();
    }
}
