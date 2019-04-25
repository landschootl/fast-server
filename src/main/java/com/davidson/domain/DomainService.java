package com.davidson.domain;

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
    public List<Domain> findAll() {
        return domainRepository.collectAll();
    }

    /**
     * Create domain in database
     * @param domain to be created
     * @return domain create with id
     */
    public Domain createDomain(Domain domain) {
        domain.setId(null);
        return domainRepository.save(domain);
    }

    /**
     * Delete domain in database
     * @param domain to be deleted
     */
    public void deleteDomain(Domain domain) {
        domainRepository.delete(domain);
    }

    /**
     * Update domain in database
     * @param domain to be updated
     * @return domain update
     */
    public Domain updateDomain(Domain domain) {
        return domainRepository.save(domain);
    }
}
