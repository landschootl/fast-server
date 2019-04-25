package com.davidson.subdomain;

import com.davidson.domain.DomainService;
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
    public List<SubDomain> findAll() {
        return subDomainRepository.collectAll();
    }

    /**
     * Delete subdomain in database
     * @param subDomain to be deleted
     */
    public void deleteSubDomain(SubDomain subDomain){
        subDomainRepository.delete(subDomain);
    }

}
